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
import pl.lodz.p.it.spjava.fm.ejb.facade.AssignerFacade;
import pl.lodz.p.it.spjava.fm.ejb.interceptor.LoggingInterceptor;
import pl.lodz.p.it.spjava.fm.ejb.managers.FaultManager;
import pl.lodz.p.it.spjava.fm.ejb.managers.SpecialistManager;
import pl.lodz.p.it.spjava.fm.exception.AppBaseException;
import pl.lodz.p.it.spjava.fm.exception.FaultException;
import pl.lodz.p.it.spjava.fm.model.Assigner;
import pl.lodz.p.it.spjava.fm.model.Fault;
import pl.lodz.p.it.spjava.fm.model.Specialist;
import pl.lodz.p.it.spjava.fm.utils.DTOConverter;

@Stateful
@Interceptors(LoggingInterceptor.class)
@TransactionAttribute(TransactionAttributeType.NEVER)
public class FaultEndpoint extends AbstractEndpoint implements SessionSynchronization {

    @EJB
    private FaultManager faultManager;
    @EJB
    private SpecialistManager specialistManager;
    @EJB
    private AssignerFacade assignerFacade;

    private Fault endpointFault;
    private Specialist endpointSpecialist;

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

    public void assignSpecialist(SpecialistDTO specialistDTO, FaultDTO faultDTO) throws AppBaseException {
        // Assigner assigner = accountEndpoint.getAssignerAccount();//po uwierzytelnieniu sprawdzić
        Assigner assigner = assignerFacade.find(-4L);
        endpointSpecialist = specialistManager.find(specialistDTO.getId());
        endpointFault.setSpecialist(endpointSpecialist);
        endpointFault.setWhoAssigned(assigner);
        endpointFault.setStatus(Fault.FaultStatus.ASSIGNED);
        faultManager.editFault(endpointFault);
    }
}
