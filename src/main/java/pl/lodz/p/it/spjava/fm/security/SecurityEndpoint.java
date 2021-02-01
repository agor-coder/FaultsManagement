package pl.lodz.p.it.spjava.fm.security;


import javax.annotation.security.RunAs;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import pl.lodz.p.it.spjava.fm.ejb.managers.AccountManager;
import pl.lodz.p.it.spjava.fm.model.Account;


@Stateless
@LocalBean
@TransactionAttribute(TransactionAttributeType.NEVER)
@RunAs("AUTHENTICATOR") 
public class SecurityEndpoint {

    @Inject
    private AccountManager accountManager;

    
    public Account findAccountWithAuthConditions(String login, String hashPass) {
        return accountManager.findLoginAndHashpassActiveAndConfirmed(login, hashPass);
    }

}
