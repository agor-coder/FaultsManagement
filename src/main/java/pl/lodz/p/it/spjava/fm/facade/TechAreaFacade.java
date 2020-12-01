
package pl.lodz.p.it.spjava.fm.facade;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import pl.lodz.p.it.spjava.fm.model.TechArea;

/**
 *
 * @author A
 */
@Stateless
public class TechAreaFacade extends AbstractFacade<TechArea> {

    @PersistenceContext(unitName = "FaultsManagementPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TechAreaFacade() {
        super(TechArea.class);
    }
    
}
