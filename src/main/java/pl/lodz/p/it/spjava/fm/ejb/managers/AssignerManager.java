package pl.lodz.p.it.spjava.fm.ejb.managers;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.SessionSynchronization;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import pl.lodz.p.it.spjava.fm.ejb.facade.AssignerFacade;
import pl.lodz.p.it.spjava.fm.ejb.interceptor.LoggingInterceptor;
import pl.lodz.p.it.spjava.fm.ejb.interceptor.PerformanceInterceptor;
import pl.lodz.p.it.spjava.fm.exception.AppBaseException;
import pl.lodz.p.it.spjava.fm.model.Assigner;

@Stateful
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@Interceptors(LoggingInterceptor.class)
public class AssignerManager extends AbstractManager implements SessionSynchronization {

    @EJB
    private AssignerFacade assignerFacade;

    @Interceptors(PerformanceInterceptor.class)
    public void createAssigner(Assigner assigner) throws AppBaseException {
        assignerFacade.create(assigner);
    }

    @Interceptors(PerformanceInterceptor.class)
    public void editAssigner(Assigner assigner) throws AppBaseException {
        assignerFacade.edit(assigner);
    }

    public Assigner find(Long id) {
        return assignerFacade.find(id);
    }

    public List<Assigner> findAll() {
        return assignerFacade.findAll();
    }
}
