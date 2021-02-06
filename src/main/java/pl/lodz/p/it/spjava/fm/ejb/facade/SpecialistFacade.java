package pl.lodz.p.it.spjava.fm.ejb.facade;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import static javax.persistence.LockModeType.OPTIMISTIC_FORCE_INCREMENT;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import pl.lodz.p.it.spjava.fm.ejb.interceptor.LoggingInterceptor;
import pl.lodz.p.it.spjava.fm.model.Specialist;

@Stateless
@Interceptors(LoggingInterceptor.class)
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class SpecialistFacade extends AbstractFacade<Specialist> {

    @PersistenceContext(unitName = "FaultsManagementPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SpecialistFacade() {
        super(Specialist.class);
    }

    @RolesAllowed("Assigner")
    public void lockSpecialist(Specialist entity) {
        em.lock(entity, OPTIMISTIC_FORCE_INCREMENT);
        // em.flush();    

    }

    public Specialist findLogin(String login) {
        TypedQuery q = getEntityManager().createNamedQuery("Specialist.findLogin", Specialist.class);
        q.setParameter("login", login);
        return (Specialist) q.getSingleResult();
    }

}
