
package pl.lodz.p.it.spjava.fm.ejb.facade;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import pl.lodz.p.it.spjava.fm.model.FaultManager;

/**
 *
 * @author A
 */
@Stateless
public class FaultManagerFacade extends AbstractFacade<FaultManager> {

    @PersistenceContext(unitName = "FaultsManagementPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FaultManagerFacade() {
        super(FaultManager.class);
    }
    
}
