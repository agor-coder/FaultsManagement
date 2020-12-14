
package pl.lodz.p.it.spjava.fm.ejb.facade;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import pl.lodz.p.it.spjava.fm.model.Fault;

/**
 *
 * @author A
 */
@Stateless
public class FaultFacade extends AbstractFacade<Fault> {

    @PersistenceContext(unitName = "FaultsManagementPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FaultFacade() {
        super(Fault.class);
    }
    
}
