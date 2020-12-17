package pl.lodz.p.it.spjava.fm.ejb.enpoints;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.EJBTransactionRolledbackException;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import pl.lodz.p.it.spjava.fm.dto.SpecialistDTO;
import pl.lodz.p.it.spjava.fm.ejb.facade.SpecialistFacade;
import pl.lodz.p.it.spjava.fm.ejb.interceptor.LoggingInterceptor;
import pl.lodz.p.it.spjava.fm.ejb.mangers.SpecialistManager;
import pl.lodz.p.it.spjava.fm.exception.AppBaseException;
import pl.lodz.p.it.spjava.fm.exception.SpecialistException;
import pl.lodz.p.it.spjava.fm.model.Specialist;
import pl.lodz.p.it.spjava.fm.utils.DTOConverter;

@Stateful
@Interceptors(LoggingInterceptor.class)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class SpecialistEndpoint {

    @Inject
    private SpecialistManager specialistManager;

    @EJB
    private SpecialistFacade specialistFacade;

    @Resource(name = "txRetryLimit")
    private int txRetryLimit;

    private Specialist endpointSpecialist;

    @TransactionAttribute(TransactionAttributeType.NEVER)
    public void addSpecialist(SpecialistDTO specialistDTO) throws AppBaseException {
        Specialist specialist = new Specialist();
        specialist.setLogin(specialistDTO.getLogin());
        specialist.setPassword(specialistDTO.getPassword());
        writeEditableDataFromDTOToEntity(specialistDTO, specialist);
      
        boolean rollbackTX;
        int retryTXCounter = txRetryLimit;
        do {
            try {
                specialistManager.createSpecialist(specialist);
                rollbackTX = specialistManager.isLastTransactionRollback();
            } catch (AppBaseException | EJBTransactionRolledbackException ex) {
                Logger.getGlobal().log(Level.SEVERE, "Próba " + retryTXCounter
                        + " wykonania metody biznesowej zakończona wyjątkiem klasy:"
                        + ex.getClass().getName());
                rollbackTX = true;
            }

        } while (rollbackTX && --retryTXCounter > 0);

        if (rollbackTX && retryTXCounter == 0) {
//            throw SpecialistException.createSpecialistExceptionWithTxRetryRollback();
            throw SpecialistException.createSpecialistExceptionWithTxRetryRollback();
        }
    }

    public List<SpecialistDTO> getAllSpecialists() {
        List<Specialist> listSpecialist = specialistFacade.findAll();
        List<SpecialistDTO> listSpecialistDTO = new ArrayList<>();
        listSpecialist.stream().map(specialist -> DTOConverter.makeSpecialistDTOFromEntity(specialist))
                .forEachOrdered(specialistDTO -> {
                    listSpecialistDTO.add(specialistDTO);
                });
        return listSpecialistDTO;
    }

    public void activateSpecialist(SpecialistDTO specialistDTO) {
        markActive(specialistDTO, true);
    }

    public void deactivateSpecialist(SpecialistDTO specialistDTO) {
        markActive(specialistDTO, false);
    }

    public void markActive(SpecialistDTO specialistDTO, boolean active) {
        Specialist tmpSpec = specialistFacade.find(specialistDTO.getId());
        tmpSpec.setActive(active);
    }

    public void removeSpecialist(SpecialistDTO specialistDTO) throws AppBaseException {
        Specialist tmpSpec = specialistFacade.find(specialistDTO.getId());
        specialistFacade.remove(tmpSpec);
    }

    public SpecialistDTO getEditedSpecialist(SpecialistDTO specialistDTO) {
        endpointSpecialist = specialistFacade.find(specialistDTO.getId());
        System.out.println(endpointSpecialist);
        return DTOConverter.makeSpecialistDTOFromEntity(endpointSpecialist);
    }

    public void saveSpecialistAfterEdit(SpecialistDTO specialistDTO) throws AppBaseException {
        endpointSpecialist = specialistFacade.find(specialistDTO.getId());
        System.out.println(endpointSpecialist);
        if (null == endpointSpecialist) {
            throw new IllegalArgumentException("Brak konta do edycji");
        }
        writeEditableDataFromDTOToEntity(specialistDTO, endpointSpecialist);
        specialistFacade.edit(endpointSpecialist);
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
