package pl.lodz.p.it.spjava.fm.exception;

import javax.persistence.OptimisticLockException;
import pl.lodz.p.it.spjava.fm.model.Fault;
import pl.lodz.p.it.spjava.fm.model.Specialist;

public class SpecialistException extends AppBaseException {

    static final public String KEY_DB_CONSTRAINT = "error.account.db.constraint.uniq";
    static final public String KEY_OPTIMISTIC_LOCK = "error.optimisticlock";
    static final public String NOT_ACTIVE = "fault.assign.not.active.spec";

    private SpecialistException(String message) {
        super(message);
    }

    private SpecialistException(String message, Throwable cause) {
        super(message, cause);
    }

    private Specialist specialist;

    public Specialist getSpecialist() {
        return specialist;
    }

    public static SpecialistException specExceptionWithNotActive() {
        SpecialistException se = new SpecialistException(NOT_ACTIVE);
        return se;
    }

    static public SpecialistException createSpecialistExceptionWithTxRetryRollback() {
        SpecialistException se = new SpecialistException(KEY_TX_RETRY_ROLLBACK);
        return se;
    }

    static public SpecialistException createWithDbCheckConstraintKey(Specialist specialist, Throwable cause) {
        SpecialistException se = new SpecialistException(KEY_DB_CONSTRAINT, cause);
        se.specialist = specialist;
        return se;
    }

    static public SpecialistException createWithDbCheckConstraintKey(Specialist specialist) {
        SpecialistException se = new SpecialistException(KEY_DB_CONSTRAINT);
        se.specialist = specialist;
        return se;
    }

    public static SpecialistException createSpecialistExceptionWithOptimisticLockKey(Specialist specialist, OptimisticLockException cause) {
        SpecialistException se = new SpecialistException(KEY_OPTIMISTIC_LOCK, cause);
        se.specialist = specialist;
        return se;
    }

    public static SpecialistException createSpecialistExceptionWithOptimisticLockKey(OptimisticLockException cause) {
        SpecialistException se = new SpecialistException(KEY_OPTIMISTIC_LOCK);
        return se;
    }
}
