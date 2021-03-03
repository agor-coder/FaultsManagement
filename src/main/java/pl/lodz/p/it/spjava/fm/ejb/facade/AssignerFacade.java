package pl.lodz.p.it.spjava.fm.ejb.facade;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import pl.lodz.p.it.spjava.fm.ejb.interceptor.LoggingInterceptor;
import pl.lodz.p.it.spjava.fm.exception.AccountException;
import pl.lodz.p.it.spjava.fm.exception.AppBaseException;
import pl.lodz.p.it.spjava.fm.model.Assigner;

@Stateless
@TransactionAttribute(TransactionAttributeType.MANDATORY)
@Interceptors(LoggingInterceptor.class)
public class AssignerFacade extends AbstractFacade<Assigner> {

    @PersistenceContext(unitName = "FaultsManagementPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AssignerFacade() {
        super(Assigner.class);
    }

    @RolesAllowed("Assigner")
    public Assigner findAssignerLogin(String login) throws AppBaseException {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Assigner> query = cb.createQuery(Assigner.class);
        Root<Assigner> from = query.from(Assigner.class);
        query = query.select(from);
        query = query.where(cb.equal(from.get("login"), login));
        TypedQuery<Assigner> tq = em.createQuery(query);

        try {
            return tq.getSingleResult();
        } catch (NoResultException ex) {
            throw AccountException.createAccountExceptionWithAssignerNotFound();
        }
    }

}
