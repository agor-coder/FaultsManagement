package pl.lodz.p.it.spjava.fm.ejb.enpoints;

import java.util.ArrayList;
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
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import pl.lodz.p.it.spjava.fm.dto.SpecialistDTO;
import pl.lodz.p.it.spjava.fm.ejb.facade.SpecialistFacade;
import pl.lodz.p.it.spjava.fm.ejb.facade.SpecialistFacade_Serializable;
import pl.lodz.p.it.spjava.fm.ejb.interceptor.LoggingInterceptor;
import pl.lodz.p.it.spjava.fm.ejb.managers.SpecialistManager;
import pl.lodz.p.it.spjava.fm.exception.AccountException;
import pl.lodz.p.it.spjava.fm.exception.AppBaseException;
import pl.lodz.p.it.spjava.fm.exception.SpecialistException;
import pl.lodz.p.it.spjava.fm.model.Specialist;
import pl.lodz.p.it.spjava.fm.utils.DTOConverter;

@Stateful
@Interceptors(LoggingInterceptor.class)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class SpecialistEndpoint  extends AbstractEndpoint implements SessionSynchronization{

    @Inject
    private SpecialistManager specialistManager;

    @EJB
    private SpecialistFacade specialistFacade;
    @EJB
    private SpecialistFacade_Serializable specialistFacade_Serializable;

    @Resource(name = "txRetryLimit")
    private int txRetryLimit;

    private Specialist endpointSpecialist;
    

    private Specialist getEndpointSpecialistToChange(SpecialistDTO specDTO) throws AppBaseException {
        Specialist tmp = specialistFacade_Serializable.find(specDTO.getId());
        if (null == tmp) {
            throw AccountException.createAccountExceptionWithAccountNotFound();
        }
        return tmp;

    }
    public void setEndpointSpecialist(SpecialistDTO specDTO) throws AppBaseException {
        endpointSpecialist = specialistFacade_Serializable.find(specDTO.getId());
        if (null == endpointSpecialist) {
            throw AccountException.createAccountExceptionWithAccountNotFound();
        }

    }

    @TransactionAttribute(TransactionAttributeType.NEVER)
    public void addSpecialist(SpecialistDTO specialistDTO) throws AppBaseException {
        Specialist specialist = new Specialist();
        specialist.setLogin(specialistDTO.getLogin());
        specialist.setPassword(specialistDTO.getPassword());
        writeEditableDataFromDTOToEntity(specialistDTO, specialist);

        boolean rollbackTX;
        int retryTXCounter = 1;
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
            }

        } while (rollbackTX && retryTXCounter <= txRetryLimit);

        if (rollbackTX && retryTXCounter > txRetryLimit) {
//            throw SpecialistException.createSpecialistExceptionWithTxRetryRollback();
            throw SpecialistException.createWithDbCheckConstraintKey(specialist);
        }
    }

    public List<SpecialistDTO> getAllSpecialistsAndMakeDTOList() {
        List<Specialist> listSpecialist = specialistFacade.findAll();
        List<SpecialistDTO> listSpecialistDTO = new ArrayList<>();
        listSpecialist.stream().map(specialist -> DTOConverter.makeSpecialistDTOFromEntity(specialist))
                .forEachOrdered(specialistDTO -> {
                    listSpecialistDTO.add(specialistDTO);
                });
        return listSpecialistDTO;
    }

    public void activateSpecialist(SpecialistDTO specialistDTO) throws AppBaseException {
        markActive(specialistDTO, true);
    }

    public void deactivateSpecialist(SpecialistDTO specialistDTO) throws AppBaseException {
        markActive(specialistDTO, false);
    }

    public void markActive(SpecialistDTO specialistDTO, boolean active) throws AppBaseException {
        endpointSpecialist = getEndpointSpecialistToChange(specialistDTO);
        endpointSpecialist.setActive(active);
    }

    public void removeSpecialist(SpecialistDTO specialistDTO) throws AppBaseException {
        Specialist tmpSpec = getEndpointSpecialistToChange(specialistDTO);
        specialistFacade.remove(tmpSpec);
    }

//    public SpecialistDTO getEditedSpecialist(SpecialistDTO specialistDTO) throws AppBaseException {
//        Specialist tmpSpec = getEndpointSpecialist(specialistDTO);
//        return DTOConverter.makeSpecialistDTOFromEntity(tmpSpec);
//    }

//@TransactionAttribute(TransactionAttributeType.NEVER)
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
    }

}
