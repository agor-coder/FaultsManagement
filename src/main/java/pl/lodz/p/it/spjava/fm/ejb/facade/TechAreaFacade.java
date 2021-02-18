package pl.lodz.p.it.spjava.fm.ejb.facade;

import java.sql.SQLIntegrityConstraintViolationException;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import org.eclipse.persistence.exceptions.DatabaseException;
import pl.lodz.p.it.spjava.fm.ejb.interceptor.LoggingInterceptor;
import pl.lodz.p.it.spjava.fm.exception.AppBaseException;
import pl.lodz.p.it.spjava.fm.exception.AppBasePersistenceException;
import pl.lodz.p.it.spjava.fm.exception.AreaException;
import pl.lodz.p.it.spjava.fm.model.TechArea;

@Stateless
@TransactionAttribute(TransactionAttributeType.MANDATORY)
@Interceptors(LoggingInterceptor.class)
@RolesAllowed("Assigner")
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

    @Override
    public void remove(TechArea entity) throws AppBaseException {
        try {
            super.remove(entity);
            em.flush();
        } catch (PersistenceException ex) {
            if (ex.getCause() instanceof DatabaseException && ex.getCause().getCause() instanceof SQLIntegrityConstraintViolationException) {
                throw AreaException.createAreaExceptionWithAreaNotRemove();
            } else {
                throw AppBasePersistenceException.createPersistenceException();
            }
        }
    }

    @Override
    public void edit(TechArea entity) throws AppBaseException {
        try {
            super.edit(entity);
            em.flush();
        } catch (OptimisticLockException oe) {
            throw AreaException.createAreaExceptionWithOptimisticLockKey(oe);
        } catch (PersistenceException ex) {
            if (ex.getCause() instanceof DatabaseException && ex.getCause().getCause() instanceof SQLIntegrityConstraintViolationException) {
                throw AreaException.createWithDbCheckConstraintKey(ex);
            } else {
                throw AppBasePersistenceException.createPersistenceException();
            }
        }
    }

    @Override
    public void create(TechArea entity) throws AppBaseException {
        try {
            super.create(entity);
            em.flush();
        } catch (PersistenceException ex) {
            if (ex.getCause() instanceof DatabaseException && ex.getCause().getCause() instanceof SQLIntegrityConstraintViolationException) {
                throw AreaException.createWithDbCheckConstraintKey(ex);
            } else {
                throw ex;
            }
        }
    }

}
