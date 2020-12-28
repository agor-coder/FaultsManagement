package pl.lodz.p.it.spjava.fm.exception;

import javax.persistence.OptimisticLockException;
import static pl.lodz.p.it.spjava.fm.exception.SpecialistException.KEY_OPTIMISTIC_LOCK;
import pl.lodz.p.it.spjava.fm.model.Specialist;

public class AccountException extends AppBaseException {

    static final public String KEY_NOT_FOUND = "error.account.not.found";
    static final public String KEY_OPTIMISTIC_LOCK = "error.specialist.optimisticlock";

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

}
