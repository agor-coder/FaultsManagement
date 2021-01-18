package pl.lodz.p.it.spjava.fm.ejb.managers;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.SessionSynchronization;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import pl.lodz.p.it.spjava.fm.ejb.facade.TechAreaFacade;
import pl.lodz.p.it.spjava.fm.ejb.interceptor.LoggingInterceptor;
import pl.lodz.p.it.spjava.fm.model.TechArea;

@Stateful
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@Interceptors(LoggingInterceptor.class)
public class TechAreaManager extends AbstractManager implements SessionSynchronization {

    @EJB
    private TechAreaFacade areaFacade;

    public TechArea find(Long id) {
        return areaFacade.find(id);
    }

    public List<TechArea> findAll() {
        return areaFacade.findAll();
    }

}
