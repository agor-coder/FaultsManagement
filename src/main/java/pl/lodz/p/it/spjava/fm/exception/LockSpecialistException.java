package pl.lodz.p.it.spjava.fm.exception;

public class LockSpecialistException extends AppBaseException {

    private LockSpecialistException(String message) {
        super(message);
    }

    private LockSpecialistException(String message, Throwable cause) {
        super(message, cause);
    }

    public static LockSpecialistException createLockExceptionWithOptimistickForceIncrement() {
        LockSpecialistException lse = new LockSpecialistException("OptimisticForceIncrement");
        return lse;
    }
}
