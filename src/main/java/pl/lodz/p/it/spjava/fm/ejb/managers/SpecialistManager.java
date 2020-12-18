
package pl.lodz.p.it.spjava.fm.ejb.managers;

import javax.ejb.EJB;
import javax.ejb.SessionSynchronization;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import pl.lodz.p.it.spjava.fm.ejb.facade.SpecialistFacade;
import pl.lodz.p.it.spjava.fm.ejb.interceptor.LoggingInterceptor;
import pl.lodz.p.it.spjava.fm.ejb.interceptor.PerformanceInterceptor;
import pl.lodz.p.it.spjava.fm.exception.AppBaseException;
import pl.lodz.p.it.spjava.fm.model.Specialist;

@Stateful
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@Interceptors(LoggingInterceptor.class)
public class SpecialistManager extends AbstractManager  implements SessionSynchronization {
    
    @EJB
    private SpecialistFacade specialistFacade;
    
    @Interceptors(PerformanceInterceptor.class)
     public void createSpecialist(Specialist specialist) throws AppBaseException {
        specialistFacade.create(specialist);
    }
     
    @Interceptors(PerformanceInterceptor.class)
     public void editSpecialist(Specialist specialist) throws AppBaseException {
        specialistFacade.edit(specialist);
    }

    
}
