package pl.lodz.p.it.spjava.fm.ejb.facade;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.ExcludeClassInterceptors;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.eclipse.persistence.exceptions.DatabaseException;
import pl.lodz.p.it.spjava.fm.exception.AccountException;
import pl.lodz.p.it.spjava.fm.exception.AppBaseException;
import pl.lodz.p.it.spjava.fm.model.Account;
import pl.lodz.p.it.spjava.fm.model.Account_;


@Stateless
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class AccountFacade extends AbstractFacade<Account> {
    
    private static final Logger LOG = Logger.getLogger(AccountFacade.class.getName());

    @PersistenceContext(unitName = "FaultsManagementPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AccountFacade() {
        super(Account.class);
    }

    
     @ExcludeClassInterceptors //Nie chcemy ujawniać w dziennikach skrótu hasła
    @RolesAllowed("AUTHENTICATOR") //"Zwykłe" role nie mają tu dostępu. Musi pośredniczyć odpowiedni endpoint opisany jako @RunAs("AUTHENTICATOR").
    public Account znajdzLoginISkrotHaslaWsrodAktywnychIPotwierdzonychKont(String login, String skrotHasla) {
        if (null == login || null == skrotHasla || login.isEmpty() || skrotHasla.isEmpty()) {
            return null;
        }
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Account> query = cb.createQuery(Account.class);
        Root<Account> from = query.from(Account.class);
        Predicate criteria = cb.conjunction();
        criteria = cb.and(criteria, cb.equal(from.get(Account_.login), login));
        criteria = cb.and(criteria, cb.equal(from.get(Account_.password), skrotHasla));
        criteria = cb.and(criteria, cb.isTrue(from.get(Account_.active)));
        criteria = cb.and(criteria, cb.isTrue(from.get(Account_.confirmed)));
        query = query.select(from);
        query = query.where(criteria);
        TypedQuery<Account> tq = em.createQuery(query);

        try {
            return tq.getSingleResult();
        } catch (NoResultException | NonUniqueResultException ex) {
            LOG.log(Level.SEVERE, "Authentication for login: {0} failed with: {1}", new Object[]{login, ex});
        }
        return null;

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
