package pl.lodz.p.it.spjava.fm.ejb.facade;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceContext;
import pl.lodz.p.it.spjava.fm.ejb.interceptor.LoggingInterceptor;
import pl.lodz.p.it.spjava.fm.exception.AppBaseException;
import pl.lodz.p.it.spjava.fm.exception.SpecialistException;
import pl.lodz.p.it.spjava.fm.model.Specialist;

@Stateless
@Interceptors(LoggingInterceptor.class)
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class SpecialistFacade_Serializable extends AbstractFacade<Specialist>  {

    @PersistenceContext(unitName = "FaultsManagementPU_Serializable")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SpecialistFacade_Serializable() {
        super(Specialist.class);
    }

    @Override
    public void edit(Specialist entity) throws AppBaseException {
        try {
            super.edit(entity);
            em.flush();
        } catch (OptimisticLockException oe) {
            throw SpecialistException.createSpecialistExceptionWithOptimisticLockKey(entity, oe);
        }
    }
}

