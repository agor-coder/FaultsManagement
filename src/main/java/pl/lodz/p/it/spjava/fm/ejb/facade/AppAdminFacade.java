
package pl.lodz.p.it.spjava.fm.ejb.facade;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import pl.lodz.p.it.spjava.fm.model.AppAdmin;

/**
 *
 * @author A
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class AppAdminFacade extends AbstractFacade<AppAdmin> {

    @PersistenceContext(unitName = "FaultsManagementPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AppAdminFacade() {
        super(AppAdmin.class);
    }
    
}
