package pl.lodz.p.it.spjava.fm.exception;

import javax.persistence.OptimisticLockException;


public class AccountException extends AppBaseException {

    static final public String KEY_NOT_FOUND = "error.account.not.found";
    static final public String KEY_OPTIMISTIC_LOCK = "error.optimisticlock";
    static final public String KEY_DB_CONSTRAINT = "error.account.db.constraint.uniq";

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

}
