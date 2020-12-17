
package pl.lodz.p.it.spjava.fm.ejb.facade;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import pl.lodz.p.it.spjava.fm.model.FaultAssigner;

/**
 *
 * @author A
 */
@Stateless
public class FaultAssignerFacade extends AbstractFacade<FaultAssigner> {

    @PersistenceContext(unitName = "FaultsManagementPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FaultAssignerFacade() {
        super(FaultAssigner.class);
    }
    
}
