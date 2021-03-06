package pl.lodz.p.it.spjava.fm.exception;

import javax.ejb.ApplicationException;

@ApplicationException(rollback = true)
abstract public class AppBaseException extends Exception {

   public  static final String KEY_TX_RETRY_ROLLBACK = "error.tx.retry.rollback";
    public  static final  String KEY_OPTIMISTIC_LOCK = "error.optimisticlock";

    protected AppBaseException(String message, Throwable cause) {
        super(message, cause);
    }

    protected AppBaseException(String message) {
        super(message);
    }

}
