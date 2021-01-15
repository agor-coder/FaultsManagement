package pl.lodz.p.it.spjava.fm.exception;

import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceException;
import pl.lodz.p.it.spjava.fm.model.Fault;


public class FaultException extends AppBaseException {

    static final public String KEY_NOT_FOUND = "error.account.not.found";
    static final public String ACCOUNT_NOT_REMOVE = "error.account.not.remove";
    static final public String KEY_OPTIMISTIC_LOCK = "error.optimisticlock";
    static final public String KEY_DB_CONSTRAINT = "error.account.db.constraint.uniq";

 

    private FaultException(String message) {
        super(message);
    }

    private FaultException(String message, Throwable cause) {
        super(message, cause);
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
      static public FaultException createWithDbCheckConstraintKey( Throwable cause) {
        FaultException fe = new FaultException(KEY_DB_CONSTRAINT, cause);
        return fe;
    }
    static public FaultException createWithDbCheckConstraintKey() {
        FaultException fe = new FaultException(KEY_DB_CONSTRAINT);
        return fe;
    }

}
