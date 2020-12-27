
package pl.lodz.p.it.spjava.fm.ejb.facade;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import pl.lodz.p.it.spjava.fm.model.Account;
import pl.lodz.p.it.spjava.fm.model.Specialist;

/**
 *
 * @author A
 */
@Stateless
public class AccountFacade extends AbstractFacade<Account> {

    @PersistenceContext(unitName = "FaultsManagementPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AccountFacade() {
        super(Account.class);
    }
    
     public void setActive(Account entity, boolean active) {
       em.find(entity.getClass(), entity.getId()).setActive(active);
       // entity.setActive(active);
    }
    
}
