package pl.lodz.p.it.spjava.fm.ejb.facade;

import java.util.List;
import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import pl.lodz.p.it.spjava.fm.exception.AppBaseException;
import pl.lodz.p.it.spjava.fm.exception.FaultException;
import pl.lodz.p.it.spjava.fm.model.Assigner;
import pl.lodz.p.it.spjava.fm.model.Fault;
import pl.lodz.p.it.spjava.fm.model.Specialist;

/**
 *
 * @author A
 */
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

    public void setStatus(Fault entity, String name) throws AppBaseException {
        try {
            em.find(entity.getClass(), entity.getId()).setStatus(Fault.FaultStatus.valueOf(name));
        } catch (OptimisticLockException oe) {
            throw FaultException.faultExceptionWithOptimisticLockKey(oe);
        }
    }

    public void assignSpecialist(Specialist specialist, Fault entity, Assigner assigner) throws AppBaseException {
        try {
            Fault tmp = em.find(entity.getClass(), entity.getId());
            tmp.setSpecialist(specialist);
            tmp.setWhoAssigned(assigner);
            tmp.setStatus(Fault.FaultStatus.ASSIGNED);
        } catch (OptimisticLockException oe) {
            throw FaultException.faultExceptionWithOptimisticLockKey(oe);
        }
    }
    
     public int countOfSpecialist(Specialist spec) {
        Query q = getEntityManager().createNamedQuery("Fault.countOfSpecialist");
        q.setParameter("specialist", spec);
        return Integer.valueOf(q.getSingleResult().toString());
    }
}
