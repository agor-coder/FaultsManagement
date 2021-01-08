
package pl.lodz.p.it.spjava.fm.ejb.facade;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import pl.lodz.p.it.spjava.fm.model.Assigner;

/**
 *
 * @author A
 */
@Stateless
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
    
}
