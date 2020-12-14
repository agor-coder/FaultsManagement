package pl.lodz.p.it.spjava.fm.exception;

import pl.lodz.p.it.spjava.fm.model.Specialist;


public class SpecialistException extends AppBaseException {

    static final public String KEY_DB_CONSTRAINT = "error.account.db.constraint.uniq";

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

  

    static public SpecialistException createSpecialistExceptionWithTxRetryRollback() {
        SpecialistException ke = new SpecialistException(KEY_TX_RETRY_ROLLBACK);
        return ke;
    }

    static public SpecialistException createWithDbCheckConstraintKey(Specialist specialist, Throwable cause) {
        SpecialistException se = new SpecialistException(KEY_DB_CONSTRAINT, cause);
        se.specialist = specialist;
        return se;
    }
}
