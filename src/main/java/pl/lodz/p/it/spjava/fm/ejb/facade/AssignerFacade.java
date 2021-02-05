package pl.lodz.p.it.spjava.fm.ejb.facade;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import pl.lodz.p.it.spjava.fm.model.Assigner;

@Stateless
@TransactionAttribute(TransactionAttributeType.MANDATORY)
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

    public Assigner findLogin(String login) {
        TypedQuery q = getEntityManager().createNamedQuery("Assigner.findLogin", Assigner.class);
        q.setParameter("login", login);
        return (Assigner) q.getSingleResult();
    }
    
    @RolesAllowed("Assigner")
    public Assigner findAssignerLogin(String login){
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Assigner> query = cb.createQuery(Assigner.class);
        Root<Assigner> from = query.from(Assigner.class);
        query = query.select(from);
        query = query.where(cb.equal(from.get("login"), login)); 
        TypedQuery<Assigner> tq = em.createQuery(query);

        return tq.getSingleResult();
    }

}
