
package pl.lodz.p.it.spjava.fm.facade;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import pl.lodz.p.it.spjava.fm.model.AppAdmin;

/**
 *
 * @author A
 */
@Stateless
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
