package pl.lodz.p.it.spjava.fm.ejb.enpoints;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
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
import pl.lodz.p.it.spjava.fm.exception.FaultException;
import pl.lodz.p.it.spjava.fm.model.Fault;
import pl.lodz.p.it.spjava.fm.model.Specialist;
import pl.lodz.p.it.spjava.fm.utils.DTOConverter;

@Stateful
@Interceptors(LoggingInterceptor.class)
@TransactionAttribute(TransactionAttributeType.NEVER)
public class FaultEndpoint extends AbstractEndpoint implements SessionSynchronization {

    @EJB
    private FaultManager faultManager;

    private Fault endpointFault;

    public List<FaultDTO> getAllFaultsAndMakeDTOList() {
        List<Fault> faultsList = faultManager.findAll();
        List<FaultDTO> faultsListDTO = new ArrayList<>();
        faultsList.stream().map(fault -> DTOConverter.createFaultDTOFromEntity(fault))
                .sorted(Comparator.comparing(FaultDTO::getStatus))
                .forEachOrdered(faultDTO -> {
                    faultsListDTO.add(faultDTO);
                });
        return faultsListDTO;
    }

    public void setStatusEND(FaultDTO faultDTO) throws AppBaseException {
        faultManager.setStatus(endpointFault, "END");
    }

    public void setEndpointFaultFromDTOToEdit(FaultDTO faultDTO) throws AppBaseException {
        endpointFault = faultManager.find(faultDTO.getId());
        if (null == endpointFault) {
            throw FaultException.faultExceptionWithAccountNotFound();
        }
    }

    public FaultDTO getFaultToEdit(FaultDTO faultDTO) {
        Fault tmp = faultManager.find(faultDTO.getId());
        return DTOConverter.createFaultDTOFromEntity(tmp);
    }

//    public void saveFaultAfterEdit(FaultDTO faultDTO) throws AppBaseException {
//        writeDataFromFaultDTOToEntity(faultDTO, endpointFault);
//        faultManager.editFault(endpointFault);
//    }

//    private void writeDataFromFaultDTOToEntity(FaultDTO faultDTO, Fault fault) {
//        fault.setFaultDescribe(faultDTO.getFaultDescribe());
//        fault.setTechArea(faultDTO.getTechArea());
//        fault.setStatus(faultDTO.getStatus());
//        fault.setSpecialist(specialistFromDTO(faultDTO.getSpecialist()));
//        fault.setWhoNotified(faultDTO.getWhoNotified());
//        fault.setWhoAssigned(faultDTO.getWhoAssigned());
//        
//    }

    
}
