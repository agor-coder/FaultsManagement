package pl.lodz.p.it.spjava.fm.ejb.managers;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.SessionSynchronization;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import pl.lodz.p.it.spjava.fm.ejb.facade.FaultFacade;
import pl.lodz.p.it.spjava.fm.ejb.interceptor.LoggingInterceptor;
import pl.lodz.p.it.spjava.fm.exception.AppBaseException;
import pl.lodz.p.it.spjava.fm.model.Fault;
import pl.lodz.p.it.spjava.fm.model.Specialist;

@Stateful
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@Interceptors(LoggingInterceptor.class)
public class FaultManager extends AbstractManager implements SessionSynchronization {

    @EJB
    private FaultFacade faultFacade;


    public Fault find(Long id) {
        return faultFacade.find(id);
    }

    public List<Fault> findAll() {
        return faultFacade.findAll();
    }

    public void setStatus(Fault fault, String status)throws AppBaseException {
       faultFacade.setStatus(fault, status);
    }

    public void assignSpecialist(Specialist specialist, Fault fault)throws AppBaseException {
      faultFacade.assignSpecialist(specialist,fault);
    }
}
