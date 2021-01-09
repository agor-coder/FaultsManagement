package pl.lodz.p.it.spjava.fm.ejb.enpoints;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.EJBTransactionRolledbackException;
import javax.ejb.SessionSynchronization;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import pl.lodz.p.it.spjava.fm.dto.SpecialistDTO;
import pl.lodz.p.it.spjava.fm.ejb.interceptor.LoggingInterceptor;
import pl.lodz.p.it.spjava.fm.ejb.managers.SpecialistManager;
import pl.lodz.p.it.spjava.fm.exception.AccountException;
import pl.lodz.p.it.spjava.fm.exception.AppBaseException;
import pl.lodz.p.it.spjava.fm.exception.SpecialistException;
import pl.lodz.p.it.spjava.fm.model.Specialist;
import pl.lodz.p.it.spjava.fm.utils.DTOConverter;

@Stateful
@Interceptors(LoggingInterceptor.class)
@TransactionAttribute(TransactionAttributeType.NEVER)
public class SpecialistEndpoint extends AbstractEndpoint implements SessionSynchronization {

    @EJB
    private SpecialistManager specialistManager;

    @Resource(name = "txRetryLimit")
    private int txRetryLimit;

    private Specialist endpointSpecialist;

    public void setEndpointSpecialistFromDTO(SpecialistDTO specDTO) throws AppBaseException {
        endpointSpecialist = specialistManager.find(specDTO.getId());
        if (null == endpointSpecialist) {
            throw AccountException.createAccountExceptionWithAccountNotFound();
        }
    }

    public void addSpecialist(SpecialistDTO specialistDTO) throws AppBaseException {
        Specialist specialist = new Specialist();
        specialist.setLogin(specialistDTO.getLogin());
        specialist.setPassword(specialistDTO.getPassword());
        writeEditableDataFromDTOToEntity(specialistDTO, specialist);

        boolean rollbackTX;
        int retryTXCounter = 1;
        Throwable cause = null;
        do {
            try {
                specialistManager.createSpecialist(specialist);
                rollbackTX = specialistManager.isLastTransactionRollback();
            } catch (AppBaseException | EJBTransactionRolledbackException ex) {
                Logger.getGlobal().log(Level.SEVERE, "Próba " + retryTXCounter
                        + " wykonania metody biznesowej zakończona wyjątkiem klasy:"
                        + ex.getClass().getName());
                rollbackTX = true;
                retryTXCounter++;
                cause = ex.getCause();
            }

        } while (rollbackTX && retryTXCounter <= txRetryLimit);

        if (rollbackTX && retryTXCounter > txRetryLimit) {
//            throw SpecialistException.createSpecialistExceptionWithTxRetryRollback();
            throw SpecialistException.createWithDbCheckConstraintKey(specialist, cause);
        }
    }

    public List<SpecialistDTO> getAllSpecialistsAndMakeDTOList() {
        List<Specialist> listSpecialist = specialistManager.findAll();
        List<SpecialistDTO> listSpecialistDTO = new ArrayList<>();
        listSpecialist.stream().map(specialist -> DTOConverter.createSpecialistDTOFromEntity(specialist))
                .sorted(Comparator.comparing(SpecialistDTO::getSureName))
                .forEachOrdered(specialistDTO -> {
                    listSpecialistDTO.add(specialistDTO);
                });
        return listSpecialistDTO;
    }

    
    public void activateSpecialist(SpecialistDTO specialistDTO) throws AppBaseException {
        setEndpointSpecialistFromDTO(specialistDTO);
        specialistManager.markActive(endpointSpecialist, true);
    }

    
    public void deactivateSpecialist(SpecialistDTO specialistDTO) throws AppBaseException {
        setEndpointSpecialistFromDTO(specialistDTO);
        specialistManager.markActive(endpointSpecialist, false);
    }

    public void removeSpecialist(SpecialistDTO specialistDTO) throws AppBaseException {
        setEndpointSpecialistFromDTO(specialistDTO);
        specialistManager.remove(endpointSpecialist);
    }

    public void saveSpecialistAfterEdit(SpecialistDTO specialistDTO) throws AppBaseException {
        writeEditableDataFromDTOToEntity(specialistDTO, endpointSpecialist);
        specialistManager.editSpecialist(endpointSpecialist);
    }

    private void writeEditableDataFromDTOToEntity(SpecialistDTO SpecialistDTO, Specialist specialist) {
        specialist.setFirstName(SpecialistDTO.getFirstName());
        specialist.setSureName(SpecialistDTO.getSureName());
        specialist.setEmail(SpecialistDTO.getEmail());
        specialist.setDepartment(SpecialistDTO.getDepartment());
        specialist.setPhone(SpecialistDTO.getPhone());
        specialist.setActive(SpecialistDTO.isActive());
        specialist.setConfirmed(SpecialistDTO.isConfirmed());
    }

}
