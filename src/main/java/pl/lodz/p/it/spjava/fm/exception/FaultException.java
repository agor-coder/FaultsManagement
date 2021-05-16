package pl.lodz.p.it.spjava.fm.exception;

import javax.persistence.OptimisticLockException;

public class FaultException extends AppBaseException {

    static final String KEY_NOT_FOUND = "error.fault.not.found";
    static final String STATUS_CHANGED_ALREADY = "error.status.changed.already";
    static final String FAULT_LIMIT = "error.fault.limit";
    static final String THE_SAME_SPEC = "fault.same.spec";
    static final String KEY_DB_CONSTRAINT = "error.account.db.constraint.uniq";
    static final String KEY_OPTIMISTIC_LOCK_REPEAT = "error.optimisticlock.repeat";
    static final String KEY_OPTIMISTIC_LOCK_FAULT = "error.optimisticlock.fault";
    static final String FAULT_IS_ASSIGNED = "fault.is.assigned";

    private FaultException(String message) {
        super(message);
    }

    private FaultException(String message, Throwable cause) {
        super(message, cause);
    }

    public static FaultException createFaultExceptionWithFaultLimit() {
        FaultException fe = new FaultException(FAULT_LIMIT);
        return fe;
    }

    public static FaultException createFaultExceptionWithSameSpecialist() {
        FaultException fe = new FaultException(THE_SAME_SPEC);
        return fe;
    }

    public static FaultException createFaultExceptionWithStatusChangedAlready() {
        FaultException fe = new FaultException(STATUS_CHANGED_ALREADY);
        return fe;
    }

    public static FaultException createFaultExceptionWithFaultNotFound() {
        FaultException fe = new FaultException(KEY_NOT_FOUND);
        return fe;
    }

    public static FaultException createFaultExceptionWithOptimisticLockKey(OptimisticLockException cause) {
        FaultException fe = new FaultException(KEY_OPTIMISTIC_LOCK_FAULT, cause);
        return fe;
    }

    public static FaultException createFaultExceptionWithOptimisticLockKey() {
        FaultException fe = new FaultException(KEY_OPTIMISTIC_LOCK_FAULT);
        return fe;
    }

    static public FaultException createWithDbCheckConstraintKey(Throwable cause) {
        FaultException fe = new FaultException(KEY_DB_CONSTRAINT, cause);
        return fe;
    }

    static public FaultException createWithDbOptimisticLockRepeatKey() {
        FaultException fe = new FaultException(KEY_OPTIMISTIC_LOCK_REPEAT);
        return fe;
    }

    static public FaultException createFaultExceptionWithTxRetryRollback() {
        FaultException ke = new FaultException(KEY_TX_RETRY_ROLLBACK);
        return ke;
    }
    
     public static FaultException createFaultExceptionWithIsAssigned() {
        FaultException fe = new FaultException(FAULT_IS_ASSIGNED);
        return fe;
    }

}
