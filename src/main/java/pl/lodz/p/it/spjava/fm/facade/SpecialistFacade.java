package pl.lodz.p.it.spjava.fm.facade;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import pl.lodz.p.it.spjava.fm.model.Specialist;

/**
 *
 * @author A
 */
@Stateless
public class SpecialistFacade extends AbstractFacade<Specialist> {

    @PersistenceContext(unitName = "FaultsManagementPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SpecialistFacade() {
        super(Specialist.class);
    }

    public Specialist findLogin(String login) {
        TypedQuery q = getEntityManager().createNamedQuery("Specialist.findLogin", Specialist.class);
        q.setParameter("login", login);
        return (Specialist) q.getSingleResult();
    }

}
