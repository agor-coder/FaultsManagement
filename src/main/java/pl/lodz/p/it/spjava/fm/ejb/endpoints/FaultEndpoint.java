package pl.lodz.p.it.spjava.fm.ejb.endpoints;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.SessionSynchronization;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import pl.lodz.p.it.spjava.fm.dto.FaultDTO;
import pl.lodz.p.it.spjava.fm.dto.SpecialistDTO;
import pl.lodz.p.it.spjava.fm.ejb.interceptor.LoggingInterceptor;
import pl.lodz.p.it.spjava.fm.ejb.managers.FaultManager;
import pl.lodz.p.it.spjava.fm.exception.AppBaseException;
import pl.lodz.p.it.spjava.fm.exception.AppBasePersistenceException;
import pl.lodz.p.it.spjava.fm.exception.FaultException;
import pl.lodz.p.it.spjava.fm.exception.LockSpecialistException;
import pl.lodz.p.it.spjava.fm.model.Fault;
import pl.lodz.p.it.spjava.fm.utils.DTOConverter;
import pl.lodz.p.it.spjava.fm.web.utils.ContextUtils;

@Stateful
@Interceptors(LoggingInterceptor.class)
@TransactionAttribute(TransactionAttributeType.NEVER)
public class FaultEndpoint extends AbstractEndpoint implements SessionSynchronization {

    @Resource(name = "txRetryLimit")
    private int txRetryLimit;
    @EJB
    private FaultManager faultManager;

    private Fault endpointFault;

    @RolesAllowed("Assigner")
    public List<FaultDTO> getAllFaultsDTO() {
        List<Fault> faultsList = faultManager.findAll();
        List<FaultDTO> faultsListDTO = DTOConverter.createFaultListDTO(faultsList);
        return faultsListDTO;
    }

    @RolesAllowed("Specialist")
    public List<FaultDTO> getSpecFaultsDTO() {
        String login = ContextUtils.getUserName();
        List<Fault> faultsList = faultManager.findSpecialistFaults(login);
        List<FaultDTO> faultsListDTO = DTOConverter.createFaultListDTO(faultsList);
        return faultsListDTO;
    }

    @RolesAllowed("Notifier")
    public List<FaultDTO> getNotifierFaultsDTO() {
        String login = ContextUtils.getUserName();
        List<Fault> faultsList = faultManager.findNotifierFaults(login);
        List<FaultDTO> faultsListDTO = DTOConverter.createFaultListDTO(faultsList);
        return faultsListDTO;
    }

    @RolesAllowed({"Specialist", "Assigner"})
    public void setStatusEND(FaultDTO faultDTO) throws AppBaseException {
        setEndpointFaultFromDTOToEdit(faultDTO);
        faultManager.setStatus(endpointFault, "END");
    }

    @RolesAllowed({"Specialist", "Assigner"})
    public void setEndpointFaultFromDTOToEdit(FaultDTO faultDTO) throws AppBaseException {
        endpointFault = faultManager.find(faultDTO.getId());
        if (null == endpointFault) {
            throw FaultException.faultExceptionWithFaultNotFound();
        }
    }

    @RolesAllowed("Assigner")
    public void assignSpecialist(SpecialistDTO specialistDTO) throws AppBaseException {
        Long specId = specialistDTO.getId();
        boolean rollbackTX;
        int retryTXCounter = 1;
        do {
            try {
                faultManager.assignSpecialist(endpointFault, specId);
                rollbackTX = faultManager.isLastTransactionRollback();
            } catch (LockSpecialistException ex) {
                Logger.getGlobal().log(Level.SEVERE, "Próba " + retryTXCounter
                        + " wykonania metody biznesowej zakończona wyjątkiem klasy:"
                        + ex.getClass().getName());
                rollbackTX = true;
                retryTXCounter++;
            }
        } while (rollbackTX && retryTXCounter <= txRetryLimit);

        if (rollbackTX && retryTXCounter > txRetryLimit) {
            throw FaultException.createWithDbOptimisticLockRepeatKey();
        }
    }

    @RolesAllowed("Notifier")
    public void addFault(FaultDTO faultDTO, Long id) throws AppBaseException {
        Fault fault = new Fault();
        fault.setFaultDescribe(faultDTO.getFaultDescribe());
        boolean rollbackTX;
        int retryTXCounter = 1;
        do {
            try {
                faultManager.createFault(fault, id);
                rollbackTX = faultManager.isLastTransactionRollback();
            } catch (AppBasePersistenceException ex) {
                Logger.getGlobal().log(Level.SEVERE, "Próba " + retryTXCounter
                        + " wykonania metody biznesowej zakończona wyjątkiem klasy:"
                        + ex.getClass().getName());
                rollbackTX = true;
                retryTXCounter++;
            }
        } while (rollbackTX && retryTXCounter <= txRetryLimit);

        if (rollbackTX && retryTXCounter > txRetryLimit) {
            throw FaultException.createFaultExceptionWithTxRetryRollback();
        }
    }

}
