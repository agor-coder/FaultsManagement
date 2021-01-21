package pl.lodz.p.it.spjava.fm.ejb.managers;

import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.SessionSynchronization;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import pl.lodz.p.it.spjava.fm.ejb.facade.AssignerFacade;
import pl.lodz.p.it.spjava.fm.ejb.facade.FaultFacade;
import pl.lodz.p.it.spjava.fm.ejb.facade.NotifierFacade;
import pl.lodz.p.it.spjava.fm.ejb.facade.SpecialistFacade;
import pl.lodz.p.it.spjava.fm.ejb.facade.TechAreaFacade;
import pl.lodz.p.it.spjava.fm.ejb.interceptor.LoggingInterceptor;
import pl.lodz.p.it.spjava.fm.exception.AppBaseException;
import pl.lodz.p.it.spjava.fm.exception.FaultException;
import pl.lodz.p.it.spjava.fm.model.Assigner;
import pl.lodz.p.it.spjava.fm.model.Fault;
import pl.lodz.p.it.spjava.fm.model.Notifier;
import pl.lodz.p.it.spjava.fm.model.Specialist;
import pl.lodz.p.it.spjava.fm.model.TechArea;
import pl.lodz.p.it.spjava.fm.web.utils.ContextUtils;

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
    @EJB
    private SpecialistFacade specFacade;
    @EJB
    private AssignerFacade assignerFacade;
    @Resource(name = "faultLimit")
    private int faultLimit;

    public Fault find(Long id) {
        return faultFacade.find(id);
    }

    public List<Fault> findAll() {
        return faultFacade.findAll();
    }

    public void setStatus(Fault fault, String status) throws AppBaseException {
        faultFacade.setStatus(fault, status);
    }

    public void assignSpecialist(Fault fault, Long Id) throws AppBaseException {
        Specialist spec = specFacade.find(Id);
        int specialistFaultsNumber = countOfSpecialist(spec);

        if (specialistFaultsNumber < faultLimit) {
            Assigner assigner = assignerFacade.find(-4L);//zrobić
            fault.setSpecialist(spec);
            fault.setWhoAssigned(assigner);
            fault.setStatus(Fault.FaultStatus.ASSIGNED);
        } else {
            throw FaultException.faultExceptionWithFaultLimit();
        }
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
//        String notifierLogin = ContextUtils.getUserName();
//        Notifier notifier = notifierFacade.findLogin(notifierLogin);
//        fault.setWhoNotified(notifier);//pobierz konto
        fault.setWhoNotified(notifierFacade.findLogin("login6"));// PiszpanZ
        faultFacade.create(fault);
    }
}
