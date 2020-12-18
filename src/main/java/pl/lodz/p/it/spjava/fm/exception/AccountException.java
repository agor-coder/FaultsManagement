
package pl.lodz.p.it.spjava.fm.exception;


public class AccountException extends AppBaseException {
    
    static final public String KEY_NOT_FOUND = "error.account.not.found";
    
    
    private AccountException(String message) {
        super(message);
    }

    public static AccountException createAccountExceptionWithAccountNotFound() {
         AccountException ae = new  AccountException(KEY_NOT_FOUND);
         return ae;
    }
    
    
    
}
