package pl.lodz.p.it.spjava.fm.ejb.facade;

import java.sql.SQLIntegrityConstraintViolationException;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import org.eclipse.persistence.exceptions.DatabaseException;
import pl.lodz.p.it.spjava.fm.ejb.interceptor.LoggingInterceptor;
import pl.lodz.p.it.spjava.fm.exception.AppBaseException;
import pl.lodz.p.it.spjava.fm.exception.SpecialistException;
import pl.lodz.p.it.spjava.fm.model.Specialist;

@Stateless
@Interceptors(LoggingInterceptor.class)
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class SpecialistFacade extends AbstractFacade<Specialist>  {

    @PersistenceContext(unitName = "FaultsManagementPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SpecialistFacade() {
        super(Specialist.class);
    }

    @Override
    public void create(Specialist entity) throws AppBaseException {
        try {
            super.create(entity);
            em.flush();
        } catch (PersistenceException ex) {
            if (ex.getCause() instanceof DatabaseException && ex.getCause().getCause() instanceof SQLIntegrityConstraintViolationException) {
                throw SpecialistException.createWithDbCheckConstraintKey(entity, ex);
            } else {
                throw ex;
            }
        }
    }

    public Specialist findLogin(String login) {
        TypedQuery q = getEntityManager().createNamedQuery("Specialist.findLogin", Specialist.class);
        q.setParameter("login", login);
        return (Specialist) q.getSingleResult();
    }

}
