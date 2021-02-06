package pl.lodz.p.it.spjava.fm.ejb.managers;

import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.SessionSynchronization;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import pl.lodz.p.it.spjava.fm.ejb.facade.TechAreaFacade;
import pl.lodz.p.it.spjava.fm.ejb.interceptor.LoggingInterceptor;
import pl.lodz.p.it.spjava.fm.exception.AppBaseException;
import pl.lodz.p.it.spjava.fm.model.TechArea;

@Stateful
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@Interceptors(LoggingInterceptor.class)
@RolesAllowed( "Assigner")
public class TechAreaManager extends AbstractManager implements SessionSynchronization {

    @EJB
    private TechAreaFacade areaFacade;

    
     
    public TechArea find(Long id) {
        return areaFacade.find(id);
    }

    @RolesAllowed({"Notifier", "Assigner"})
    public List<TechArea> findAll() {
        return areaFacade.findAll();
    }

    public void remove(TechArea area) throws AppBaseException {
        areaFacade.remove(area);
    }

   
    public void editArea(TechArea area) throws AppBaseException {
        areaFacade.edit(area);
    }

     
    public void createArea(TechArea techArea) throws AppBaseException {
        areaFacade.create(techArea);
    }

}
