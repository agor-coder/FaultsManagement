
package pl.lodz.p.it.spjava.fm.ejb.facade;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import pl.lodz.p.it.spjava.fm.model.Notifier;

/**
 *
 * @author A
 */
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
    
}
