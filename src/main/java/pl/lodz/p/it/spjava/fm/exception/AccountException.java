package pl.lodz.p.it.spjava.fm.exception;

import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceException;


public class AccountException extends AppBaseException {

    static final public String KEY_NOT_FOUND = "error.account.not.found";
    static final public String ACCOUNT_NOT_REMOVE = "error.account.not.remove";
    static final public String KEY_DB_CONSTRAINT = "error.account.db.constraint.uniq";
    static final public String PASS_NOT_MATCH = "error.pass.not.match";
    static final public String PASS_THE_SAME = "error.same.password";
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
        AccountException ae = new AccountException(KEY_DB_CONSTRAINT, cause);
        return ae;
    }
    static public AccountException createWithDbCheckConstraintKey() {
        AccountException ae = new AccountException(KEY_DB_CONSTRAINT);
        return ae;
    }
      public static AccountException createWithDbCheckConstraintKeyEmail(PersistenceException ex) {
        AccountException ae = new AccountException(KEY_DB_CONSTRAINT_EMAIL);
        return ae;
    }

    static public AccountException createWithPreviousGivenPasswordDoesNotMatch() {
        AccountException ae = new AccountException(PASS_NOT_MATCH);
        return ae;
    }
    static public AccountException createWithTheSamePasswordDoesNotMatch() {
        AccountException ae = new AccountException(PASS_THE_SAME);
        return ae;
    }
    static public AccountException createAccountExceptionWithTxRetryRollback() {
        AccountException ae = new AccountException(KEY_TX_RETRY_ROLLBACK);
        return ae;
    }

}
