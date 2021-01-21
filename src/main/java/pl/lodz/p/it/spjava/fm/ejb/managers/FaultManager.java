package pl.lodz.p.it.spjava.fm.ejb.managers;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.SessionSynchronization;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import pl.lodz.p.it.spjava.fm.ejb.facade.FaultFacade;
import pl.lodz.p.it.spjava.fm.ejb.facade.NotifierFacade;
import pl.lodz.p.it.spjava.fm.ejb.facade.TechAreaFacade;
import pl.lodz.p.it.spjava.fm.ejb.interceptor.LoggingInterceptor;
import pl.lodz.p.it.spjava.fm.exception.AppBaseException;
import pl.lodz.p.it.spjava.fm.model.Assigner;
import pl.lodz.p.it.spjava.fm.model.Fault;
import pl.lodz.p.it.spjava.fm.model.Specialist;
import pl.lodz.p.it.spjava.fm.model.TechArea;

@Stateful
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@Interceptors(LoggingInterceptor.class)
public class FaultManager extends AbstractManager implements SessionSynchronization {

    @EJB
    private FaultFacade faultFacade;
    @EJB
    private NotifierFacade notifierFacade;
    @EJB
    private TechAreaFacade areaFacade;

    public Fault find(Long id) {
        return faultFacade.find(id);
    }

    public List<Fault> findAll() {
        return faultFacade.findAll();
    }

    public void setStatus(Fault fault, String status) throws AppBaseException {
        faultFacade.setStatus(fault, status);
    }

    public void assignSpecialist(Specialist specialist, Fault fault, Assigner assigner) throws AppBaseException {
        faultFacade.assignSpecialist(specialist, fault, assigner);
    }

    public void editFault(Fault fault, Specialist spec) throws AppBaseException {
        faultFacade.edit(fault);
    }

    public int countOfSpecialist(Specialist specialist) {
        return faultFacade.countOfSpecialist(specialist);
    }

    public List<Fault> findSpecialistFaults(String login) {
        return faultFacade.findSpecialistFaults(login);
    }

    public void createFault(Fault fault, Long idTecharea) throws AppBaseException {
        TechArea area = areaFacade.find(idTecharea);
        fault.setTechArea(area);
        fault.setWhoNotified(notifierFacade.findLogin("login6"));// PiszpanZ
        faultFacade.create(fault);
    }
}
