package pl.lodz.p.it.spjava.fm.ejb.facade;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import pl.lodz.p.it.spjava.fm.model.Assigner;
import pl.lodz.p.it.spjava.fm.model.Specialist;

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
        TypedQuery q = getEntityManager().createNamedQuery("Assigner.findLogin", Specialist.class);
        q.setParameter("login", login);
        return (Assigner) q.getSingleResult();
    }

}
