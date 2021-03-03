package pl.lodz.p.it.spjava.fm.exception;

import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceException;

public class AccountException extends AppBaseException {

    static final String KEY_NOT_FOUND = "error.account.not.found";
    static final String ACCOUNT_NOT_REMOVE = "error.account.not.remove";
    static final String OWN_ACCOUNT_NOT_REMOVE = "error.own.account.not.remove";
    static final String KEY_DB_CONSTRAINT = "error.account.db.constraint.uniq";
    static final String PASS_NOT_MATCH = "error.pass.not.match";
    static final String PASS_THE_SAME = "error.same.password";
    static final String KEY_DB_CONSTRAINT_EMAIL = "error.account.db.constraint.uniq.email";
    static final String ASSIGNER_NOT_FOUND = "error.assigner.not.found";

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

    public static AccountException createAccountExceptionWithAssignerNotFound() {
        AccountException ae = new AccountException(ASSIGNER_NOT_FOUND);
        return ae;
    }

    public static AccountException createAccountExceptionWithAccountNotRemove() {
        AccountException ae = new AccountException(ACCOUNT_NOT_REMOVE);
        return ae;
    }

    public static AccountException createAccountExceptionWithOwnAccountNotRemove() {
        AccountException ae = new AccountException(OWN_ACCOUNT_NOT_REMOVE);
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

    static public AccountException createWithDbCheckConstraintKey(Throwable cause) {
        AccountException ae = new AccountException(KEY_DB_CONSTRAINT, cause);
        return ae;
    }

    public static AccountException createWithDbCheckConstraintKey() {
        AccountException ae = new AccountException(KEY_DB_CONSTRAINT);
        return ae;
    }

    public static AccountException createWithDbCheckConstraintKeyEmail(PersistenceException ex) {
        AccountException ae = new AccountException(KEY_DB_CONSTRAINT_EMAIL);
        return ae;
    }

    public static AccountException createWithPreviousGivenPasswordDoesNotMatch() {
        AccountException ae = new AccountException(PASS_NOT_MATCH);
        return ae;
    }

    public static AccountException createWithTheSamePasswordDoesNotMatch() {
        AccountException ae = new AccountException(PASS_THE_SAME);
        return ae;
    }

    public static AccountException createAccountExceptionWithTxRetryRollback() {
        AccountException ae = new AccountException(KEY_TX_RETRY_ROLLBACK);
        return ae;
    }

}
