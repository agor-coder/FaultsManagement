package pl.lodz.p.it.spjava.fm.ejb.enpoints;


import java.util.List;
import javax.ejb.EJB;
import javax.ejb.SessionSynchronization;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import pl.lodz.p.it.spjava.fm.dto.TechAreaDTO;
import pl.lodz.p.it.spjava.fm.ejb.interceptor.LoggingInterceptor;
import pl.lodz.p.it.spjava.fm.ejb.managers.TechAreaManager;
import pl.lodz.p.it.spjava.fm.model.TechArea;
import pl.lodz.p.it.spjava.fm.utils.DTOConverter;

@Stateful
@Interceptors(LoggingInterceptor.class)
@TransactionAttribute(TransactionAttributeType.NEVER)
public class TechAreaEndpoint extends AbstractEndpoint implements SessionSynchronization {

    
    @EJB
    private TechAreaManager areaManager;


    public List<TechAreaDTO> getAllAreasDTO() {
        List<TechArea> areasList = areaManager.findAll();
        List<TechAreaDTO>areasListDTO = DTOConverter.createAreaListDTO(areasList);
        return areasListDTO;
    }


}
