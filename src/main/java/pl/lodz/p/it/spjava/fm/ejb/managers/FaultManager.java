package pl.lodz.p.it.spjava.fm.ejb.managers;

import java.util.List;
import javax.annotation.Resource;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.EJBTransactionRolledbackException;
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
import pl.lodz.p.it.spjava.fm.exception.LockSpecialistException;
import pl.lodz.p.it.spjava.fm.exception.SpecialistException;
import pl.lodz.p.it.spjava.fm.model.Assigner;
import pl.lodz.p.it.spjava.fm.model.Fault;
import pl.lodz.p.it.spjava.fm.model.Notifier;
import pl.lodz.p.it.spjava.fm.model.Specialist;
import pl.lodz.p.it.spjava.fm.model.TechArea;
import pl.lodz.p.it.spjava.fm.web.utils.ContextUtils;

@Stateful
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@Interceptors(LoggingInterceptor.class)
@RolesAllowed("Assigner")
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

    @RolesAllowed({"Specialist", "Assigner"})
    public Fault find(Long id) {
        return faultFacade.find(id);
    }

    public List<Fault> findAll() {
        return faultFacade.findAll();
    }

    @RolesAllowed({"Specialist", "Assigner"})
    public void setStatus(Fault fault, String status) throws AppBaseException {
        faultFacade.setStatus(fault, status);
    }

    public void assignSpecialist(Fault fault, Long Id) throws AppBaseException {
        Specialist spec = specFacade.find(Id);
        if (!spec.isActive() || !spec.isConfirmed()) {
            throw SpecialistException.specExceptionWithNotActive();
        }
        if (null != fault.getSpecialist() && fault.getSpecialist().equals(spec)) {
            throw FaultException.faultExceptionWithSameSpecialist();
        }

        int specialistFaultsNumber = countOfSpecialist(spec);
        if (specialistFaultsNumber < faultLimit) {
            try {
                specFacade.lockSpecialist(spec);
                String assignerLogin = ContextUtils.getUserName();
                Assigner assigner = assignerFacade.findAssignerLogin(assignerLogin);
                fault.setWhoAssigned(assigner);
                fault.setSpecialist(spec);
                fault.setStatus(Fault.FaultStatus.ASSIGNED);
            } catch (EJBTransactionRolledbackException tre) {
                throw LockSpecialistException.createLockExceptionWithOptimistickForceIncrement();
            }
        } else {
            throw FaultException.faultExceptionWithFaultLimit();
        }
        faultFacade.edit(fault);
    }

    public void assignSpecialist2(Fault fault, Long Id) throws AppBaseException {
        Specialist spec = specFacade.find(Id);
        if (!spec.isActive() || !spec.isConfirmed()) {
            throw SpecialistException.specExceptionWithNotActive();
        }
        if (null != fault.getSpecialist() && fault.getSpecialist().equals(spec)) {
            throw FaultException.faultExceptionWithSameSpecialist();
        }

        int specialistFaultsNumber = countOfSpecialist(spec);
        if (specialistFaultsNumber < faultLimit) {
            try {
                specFacade.lockSpecialistAndWait(spec);
                String assignerLogin = ContextUtils.getUserName();
                Assigner assigner = assignerFacade.findAssignerLogin(assignerLogin);
                fault.setWhoAssigned(assigner);
                fault.setSpecialist(spec);
                fault.setStatus(Fault.FaultStatus.ASSIGNED);
            } catch (EJBTransactionRolledbackException tre) {
                throw LockSpecialistException.createLockExceptionWithOptimistickForceIncrement();
            }
        } else {
            throw FaultException.faultExceptionWithFaultLimit();
        }
        faultFacade.edit(fault);
    }

    private int countOfSpecialist(Specialist specialist) throws AppBaseException {
        return faultFacade.countOfSpecialist(specialist);
    }

    @RolesAllowed("Specialist")
    public List<Fault> findSpecialistFaults(String login) {
        return faultFacade.findSpecialistFaults(login);
    }

    @RolesAllowed("Notifier")
    public List<Fault> findNotifierFaults(String login) {
        return faultFacade.findNotifierFaults(login);
    }

    @RolesAllowed("Notifier")
    public void createFault(Fault fault, Long idTecharea) throws AppBaseException {
        TechArea area = areaFacade.find(idTecharea);
        fault.setTechArea(area);
        String notifierLogin = ContextUtils.getUserName();
        Notifier notifier = notifierFacade.findLogin(notifierLogin);
        fault.setWhoNotified(notifier);
        faultFacade.create(fault);
    }

}
