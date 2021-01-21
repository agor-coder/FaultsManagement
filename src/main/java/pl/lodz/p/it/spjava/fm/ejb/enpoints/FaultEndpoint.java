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
import pl.lodz.p.it.spjava.fm.dto.FaultDTO;
import pl.lodz.p.it.spjava.fm.dto.SpecialistDTO;
import pl.lodz.p.it.spjava.fm.ejb.interceptor.LoggingInterceptor;
import pl.lodz.p.it.spjava.fm.ejb.managers.FaultManager;
import pl.lodz.p.it.spjava.fm.exception.AppBaseException;
import pl.lodz.p.it.spjava.fm.exception.FaultException;
import pl.lodz.p.it.spjava.fm.model.Fault;
import pl.lodz.p.it.spjava.fm.utils.DTOConverter;

@Stateful
@Interceptors(LoggingInterceptor.class)
@TransactionAttribute(TransactionAttributeType.NEVER)
public class FaultEndpoint extends AbstractEndpoint implements SessionSynchronization {

    @Resource(name = "txRetryLimit")
    private int txRetryLimit;
    @EJB
    private FaultManager faultManager;
    private Fault endpointFault; 

    public List<FaultDTO> getAllFaultsAndMakeDTOList() {
        List<Fault> faultsList = faultManager.findAll();//wszystkie     
        //List<Fault> faultsList = faultManager.findSpecialistFaults("login0");//  (sctx.getCallerPrincipal().getName());//ContextUtils.getUserName();
        List<FaultDTO> faultsListDTO = new ArrayList<>();
        faultsList.stream().map(fault -> DTOConverter.createFaultDTOFromEntity(fault))
                .sorted(Comparator.comparing(FaultDTO::getStatus))
                .forEachOrdered(faultDTO -> {
                    faultsListDTO.add(faultDTO);
                });
        return faultsListDTO;
    }

    public void setStatusEND(FaultDTO faultDTO) throws AppBaseException {
        setEndpointFaultFromDTOToEdit(faultDTO);
        faultManager.setStatus(endpointFault, "END");
    }

    public void setEndpointFaultFromDTOToEdit(FaultDTO faultDTO) throws AppBaseException {
        endpointFault = faultManager.find(faultDTO.getId());
        if (null == endpointFault) {
            throw FaultException.faultExceptionWithFaultNotFound();
        }
    }

    public FaultDTO getFaultDTOToEdit(FaultDTO faultDTO) throws AppBaseException {
        setEndpointFaultFromDTOToEdit(faultDTO);
        return DTOConverter.createFaultDTOFromEntity(endpointFault);
    }

    public void assignSpecialist(SpecialistDTO specialistDTO) throws AppBaseException {
        Long specId = specialistDTO.getId();
        faultManager.assignSpecialist(endpointFault, specId);
    }

    public void addFault(FaultDTO faultDTO) throws AppBaseException {
        Fault fault = new Fault();
        fault.setFaultDescribe(faultDTO.getFaultDescribe());
        Long idTechArea = faultDTO.getTechArea().getId();
        boolean rollbackTX;
        int retryTXCounter = 1;
        do {
            try {
                faultManager.createFault(fault, idTechArea);
                rollbackTX = faultManager.isLastTransactionRollback();
            } catch (AppBaseException | EJBTransactionRolledbackException ex) {
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
