package pl.lodz.p.it.spjava.fm.exception;

import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceException;


public class AccountException extends AppBaseException {

    static final public String KEY_NOT_FOUND = "error.account.not.found";
    static final public String ACCOUNT_NOT_REMOVE = "error.account.not.remove";
    static final public String KEY_DB_CONSTRAINT = "error.account.db.constraint.uniq";
    static final public String PASS_NOT_MATCH = "error.pass.not.match";
    static final public String KEY_DB_CONSTRAINT_EMAIL = "error.account.db.constraint.uniq.email";

  
   
    private AccountException(String message) {
        super(message);
    }

    private AccountException(String message, Throwable cause) {
        super(message, cause);
    }

    public static AccountException createAccountExceptionWithAccountNotFound() {
        AccountException ae = new AccountException(KEY_NOT_FOUND);
        return ae;
    }
    public static AccountException createAccountExceptionWithAccountNotRemove() {
        AccountException ae = new AccountException(ACCOUNT_NOT_REMOVE);
        return ae;
    }

    public static AccountException createAccountExceptionWithOptimisticLockKey(OptimisticLockException cause) {
        AccountException ae = new AccountException(KEY_OPTIMISTIC_LOCK, cause);
        return ae;
    }

    public static AccountException createAccountExceptionWithOptimisticLockKey() {
        AccountException ae = new AccountException(KEY_OPTIMISTIC_LOCK);
        return ae;
    }
      static public AccountException createWithDbCheckConstraintKey( Throwable cause) {
        AccountException se = new AccountException(KEY_DB_CONSTRAINT, cause);
        return se;
    }
    static public AccountException createWithDbCheckConstraintKey() {
        AccountException se = new AccountException(KEY_DB_CONSTRAINT);
        return se;
    }
      public static AccountException createWithDbCheckConstraintKeyEmail(PersistenceException ex) {
        AccountException se = new AccountException(KEY_DB_CONSTRAINT_EMAIL);
        return se;
    }

    static public AccountException createWithPreviousGivenPasswordDoesNotMatch() {
        AccountException se = new AccountException(PASS_NOT_MATCH);
        return se;
    }

}
