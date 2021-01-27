package pl.lodz.p.it.spjava.fm.ejb.facade;

import java.sql.SQLIntegrityConstraintViolationException;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.eclipse.persistence.exceptions.DatabaseException;
import pl.lodz.p.it.spjava.fm.exception.AccountException;
import pl.lodz.p.it.spjava.fm.exception.AppBaseException;
import pl.lodz.p.it.spjava.fm.model.Account;
import pl.lodz.p.it.spjava.fm.model.Assigner;


@Stateless
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class AccountFacade extends AbstractFacade<Account> {

    @PersistenceContext(unitName = "FaultsManagementPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AccountFacade() {
        super(Account.class);
    }

    public void setActive(Account entity, boolean active) {
        em.find(entity.getClass(), entity.getId()).setActive(active);
        // entity.setActive(active);
    }

    @Override
    public void edit(Account entity) throws AppBaseException {
        try {
            super.edit(entity);
            em.flush();
        } catch (OptimisticLockException oe) {
            throw AccountException.createAccountExceptionWithOptimisticLockKey(oe);
        } catch (PersistenceException ex) {
            if (ex.getCause() instanceof DatabaseException && ex.getCause().getCause() instanceof SQLIntegrityConstraintViolationException) {
                throw AccountException.createWithDbCheckConstraintKeyEmail(ex);
            } else {
                throw ex;
            }
        }
    }

    @Override
    public void create(Account entity) throws AppBaseException {
        try {
            super.create(entity);
            em.flush();
        } catch (PersistenceException ex) {
            if (ex.getCause() instanceof DatabaseException && ex.getCause().getCause() instanceof SQLIntegrityConstraintViolationException) {
                throw AccountException.createWithDbCheckConstraintKey(ex);
            } else {
                throw ex;
            }
        }
    }
    @Override
    public void remove(Account entity) throws AppBaseException {
        try {
            super.remove(entity);
            em.flush();
        } catch (PersistenceException ex) {
            if (ex.getCause() instanceof DatabaseException && ex.getCause().getCause() instanceof SQLIntegrityConstraintViolationException) {
                throw AccountException.createAccountExceptionWithAccountNotRemove();
            } else {
                throw ex;
            }
        }
    }
    public Account findLogin(String login){
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Account> query = cb.createQuery(Account.class);
        Root<Account> from = query.from(Account.class);
        query = query.select(from);
        query = query.where(cb.equal(from.get("login"), login)); //Przykład wskazania atrybutu encji poprzez nazwę
        TypedQuery<Account> tq = em.createQuery(query);

        return tq.getSingleResult();
    }

    public void changeMyPasword(Account entity, String newPass) {
         em.find(entity.getClass(), entity.getId()).setPassword(newPass);
    }
  

}
