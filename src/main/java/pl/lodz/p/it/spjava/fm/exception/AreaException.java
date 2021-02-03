package pl.lodz.p.it.spjava.fm.exception;

import javax.persistence.OptimisticLockException;


public class AreaException extends AppBaseException {

    static final public String KEY_NOT_FOUND = "error.area.not.found";
    static final public String AREA_NOT_REMOVE = "error.area.not.remove";
     static final public String KEY_DB_CONSTRAINT = "error.account.db.constraint.uniq";
  

  
   
    private AreaException(String message) {
        super(message);
    }

    private AreaException(String message, Throwable cause) {
        super(message, cause);
    }

    public static AreaException createAreaExceptionWithAreaNotFound() {
        AreaException ae = new AreaException(KEY_NOT_FOUND);
        return ae;
    }
    public static AreaException createAreaExceptionWithAreaNotRemove() {
        AreaException ae = new AreaException(AREA_NOT_REMOVE);
        return ae;
    }

    public static AreaException createAreaExceptionWithOptimisticLockKey(OptimisticLockException cause) {
        AreaException ae = new AreaException(KEY_OPTIMISTIC_LOCK, cause);
        return ae;
    }

    public static AreaException createAreaExceptionWithOptimisticLockKey() {
        AreaException ae = new AreaException(KEY_OPTIMISTIC_LOCK);
        return ae;
    }
      static public AreaException createWithDbCheckConstraintKey( Throwable cause) {
        AreaException se = new AreaException(KEY_DB_CONSTRAINT, cause);
        return se;
    }
    static public AreaException createWithDbCheckConstraintKey() {
        AreaException se = new AreaException(KEY_DB_CONSTRAINT);
        return se;
    }
    


}
