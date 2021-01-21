package pl.lodz.p.it.spjava.fm.ejb.facade;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import pl.lodz.p.it.spjava.fm.model.Notifier;

@Stateless
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class NotifierFacade extends AbstractFacade<Notifier> {

    @PersistenceContext(unitName = "FaultsManagementPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public NotifierFacade() {
        super(Notifier.class);
    }

    public Notifier findLogin(String login) {
        TypedQuery q = getEntityManager().createNamedQuery("Notifier.findLogin", Notifier.class);
        q.setParameter("login", login);
        return (Notifier) q.getSingleResult();
    }
}
