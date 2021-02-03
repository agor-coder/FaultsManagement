package pl.lodz.p.it.spjava.fm.ejb.facade;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import static javax.persistence.LockModeType.OPTIMISTIC_FORCE_INCREMENT;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import pl.lodz.p.it.spjava.fm.ejb.interceptor.LoggingInterceptor;
import pl.lodz.p.it.spjava.fm.exception.AppBaseException;
import pl.lodz.p.it.spjava.fm.model.Specialist;

@Stateless
@Interceptors(LoggingInterceptor.class)
@TransactionAttribute(TransactionAttributeType.MANDATORY)
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

 

    public Specialist findSpec(Long id) throws AppBaseException {
        
            Specialist spec = super.find(id);
            em.lock(spec, OPTIMISTIC_FORCE_INCREMENT);
           // em.flush(); 
            return spec;
       
    }

    public Specialist findLogin(String login) {
        TypedQuery q = getEntityManager().createNamedQuery("Specialist.findLogin", Specialist.class);
        q.setParameter("login", login);
        return (Specialist) q.getSingleResult();
    }


}
