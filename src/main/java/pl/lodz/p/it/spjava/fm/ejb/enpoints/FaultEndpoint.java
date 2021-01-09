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
import pl.lodz.p.it.spjava.fm.ejb.interceptor.LoggingInterceptor;
import pl.lodz.p.it.spjava.fm.ejb.managers.FaultManager;
import pl.lodz.p.it.spjava.fm.model.Fault;
import pl.lodz.p.it.spjava.fm.utils.DTOConverter;

@Stateful
@Interceptors(LoggingInterceptor.class)
@TransactionAttribute(TransactionAttributeType.NEVER)
public class FaultEndpoint extends AbstractEndpoint implements SessionSynchronization {

    @EJB
    private FaultManager faultManager;



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



  

}
