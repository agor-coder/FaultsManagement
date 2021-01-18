package pl.lodz.p.it.spjava.fm.exception;

import javax.persistence.OptimisticLockException;

public class FaultException extends AppBaseException {

    static final public String KEY_NOT_FOUND = "error.account.not.found";
    static final public String ACCOUNT_NOT_REMOVE = "error.account.not.remove";
    static final public String KEY_OPTIMISTIC_LOCK = "error.optimisticlock";
    static final public String KEY_DB_CONSTRAINT = "error.account.db.constraint.uniq";
    static final public String STATUS_CHANGED_ALREADY = "error.status.changed.already";
    static final public String FAULT_LIMIT = "error.fault.limit";

    private FaultException(String message) {
        super(message);
    }

    private FaultException(String message, Throwable cause) {
        super(message, cause);
    }

    public static FaultException faultExceptionWithFaultLimit() {
        FaultException fe = new FaultException(FAULT_LIMIT);
        return fe;
    }

    public static FaultException faultExceptionWithStatusChangedAlready() {
        FaultException fe = new FaultException(STATUS_CHANGED_ALREADY);
        return fe;
    }

    public static FaultException faultExceptionWithFaultNotFound() {
        FaultException fe = new FaultException(KEY_NOT_FOUND);
        return fe;
    }

    public static FaultException faultExceptionWithOptimisticLockKey(OptimisticLockException cause) {
        FaultException fe = new FaultException(KEY_OPTIMISTIC_LOCK, cause);
        return fe;
    }

    public static FaultException faultExceptionWithOptimisticLockKey() {
        FaultException fe = new FaultException(KEY_OPTIMISTIC_LOCK);
        return fe;
    }

    static public FaultException createWithDbCheckConstraintKey(Throwable cause) {
        FaultException fe = new FaultException(KEY_DB_CONSTRAINT, cause);
        return fe;
    }

    static public FaultException createWithDbCheckConstraintKey() {
        FaultException fe = new FaultException(KEY_DB_CONSTRAINT);
        return fe;
    }
    static public FaultException createFaultExceptionWithTxRetryRollback() {
        FaultException ke = new FaultException(KEY_TX_RETRY_ROLLBACK);
        return ke;
    }

}
