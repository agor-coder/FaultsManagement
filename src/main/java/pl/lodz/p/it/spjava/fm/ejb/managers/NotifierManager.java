package pl.lodz.p.it.spjava.fm.ejb.managers;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.SessionSynchronization;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import pl.lodz.p.it.spjava.fm.ejb.facade.NotifierFacade;
import pl.lodz.p.it.spjava.fm.ejb.interceptor.LoggingInterceptor;
import pl.lodz.p.it.spjava.fm.ejb.interceptor.PerformanceInterceptor;
import pl.lodz.p.it.spjava.fm.exception.AppBaseException;
import pl.lodz.p.it.spjava.fm.model.Notifier;

@Stateful
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@Interceptors(LoggingInterceptor.class)
public class NotifierManager extends AbstractManager implements SessionSynchronization {

    @EJB
    private NotifierFacade notifierFacade;

    @Interceptors(PerformanceInterceptor.class)
    public void createNotifier(Notifier notifier) throws AppBaseException {
        notifierFacade.create(notifier);
    }

    @Interceptors(PerformanceInterceptor.class)
    public void editNotifier(Notifier notifier) throws AppBaseException {
        notifierFacade.edit(notifier);
    }

    public Notifier find(Long id) {
        return notifierFacade.find(id);
    }

    public List<Notifier> findAll() {
        return notifierFacade.findAll();
    }
}
