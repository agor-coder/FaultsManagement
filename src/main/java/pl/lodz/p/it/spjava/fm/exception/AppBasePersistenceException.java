package pl.lodz.p.it.spjava.fm.exception;

public class AppBasePersistenceException extends AppBaseException {

  

    private AppBasePersistenceException(String message) {
        super(message);
    }

    private AppBasePersistenceException(String message, Throwable cause) {
        super(message, cause);
    }

    public static AppBasePersistenceException createPersistenceException() {
       AppBasePersistenceException abpe = new  AppBasePersistenceException("persistence");
        return abpe;
    }
}
