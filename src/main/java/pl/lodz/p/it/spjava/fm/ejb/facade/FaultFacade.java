package pl.lodz.p.it.spjava.fm.ejb.facade;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import org.eclipse.persistence.exceptions.DatabaseException;
import pl.lodz.p.it.spjava.fm.exception.AppBaseException;
import pl.lodz.p.it.spjava.fm.exception.FaultException;
import pl.lodz.p.it.spjava.fm.exception.SpecialistException;
import pl.lodz.p.it.spjava.fm.model.Fault;
import pl.lodz.p.it.spjava.fm.model.Specialist;

@Stateless
public class FaultFacade extends AbstractFacade<Fault> {

    @PersistenceContext(unitName = "FaultsManagementPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FaultFacade() {
        super(Fault.class);
    }

    @RolesAllowed({"Specialist", "Assigner"})
    public void setStatus(Fault entity, String name) throws AppBaseException {
        if (!entity.getStatus().name().equals(name)) {
            em.find(entity.getClass(), entity.getId()).setStatus(Fault.FaultStatus.valueOf(name));
        } else {
            throw FaultException.faultExceptionWithStatusChangedAlready();
        }
    }

    @Override
    @RolesAllowed("Assigner")
    public void edit(Fault entity) throws AppBaseException {
        try {
            super.edit(entity);
            em.flush();
        } catch (OptimisticLockException oe) {
            throw FaultException.faultExceptionWithOptimisticLockKey(oe);
        } catch (PersistenceException ex) {
            if (ex.getCause() instanceof DatabaseException && ex.getCause().getCause() instanceof SQLIntegrityConstraintViolationException) {
                throw FaultException.createWithDbCheckConstraintKey(ex);
            } else {
                throw ex;
            }
        }
    }

    @RolesAllowed("Assigner")
    public int countOfSpecialist(Specialist spec) throws AppBaseException {
        try {
            Query q = getEntityManager().createNamedQuery("Fault.countOfSpecialist");
            q.setParameter("specialist", spec);
            q.setParameter("status", Fault.FaultStatus.ASSIGNED);
            return Integer.valueOf(q.getSingleResult().toString());
        } catch (OptimisticLockException oe) {
            throw SpecialistException.createSpecialistExceptionWithOptimisticLockKey(oe);
        }
    }

    @RolesAllowed("Specialist")
    public List<Fault> findSpecialistFaults(String login) {
        TypedQuery tq = getEntityManager().createNamedQuery("Fault.findSpecOfLogin", Fault.class);
        tq.setParameter("login", login);
        return tq.getResultList();
    }

    public List<Fault> findNotifierFaults(String login) {
        TypedQuery tq = getEntityManager().createNamedQuery("Fault.findNotifOfLogin", Fault.class);
        tq.setParameter("login", login);
        return tq.getResultList();
    }

}
