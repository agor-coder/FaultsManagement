package pl.lodz.p.it.spjava.fm.ejb.managers;

import java.util.List;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.SessionSynchronization;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.ExcludeClassInterceptors;
import javax.interceptor.Interceptors;
import pl.lodz.p.it.spjava.fm.ejb.facade.AccountFacade;
import pl.lodz.p.it.spjava.fm.ejb.interceptor.LoggingInterceptor;
import pl.lodz.p.it.spjava.fm.ejb.interceptor.PerformanceInterceptor;
import pl.lodz.p.it.spjava.fm.exception.AppBaseException;
import pl.lodz.p.it.spjava.fm.model.Account;
import pl.lodz.p.it.spjava.fm.model.AppAdmin;
import pl.lodz.p.it.spjava.fm.model.Assigner;
import pl.lodz.p.it.spjava.fm.model.Notifier;
import pl.lodz.p.it.spjava.fm.model.Specialist;

@Stateful
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@Interceptors(LoggingInterceptor.class)
@RolesAllowed({"AppAdmin"})
public class AccountManager extends AbstractManager implements SessionSynchronization {

    @EJB
    private AccountFacade accountFacade;

    @Interceptors(PerformanceInterceptor.class)
    public void editAccount(Account account) throws AppBaseException {
        accountFacade.edit(account);
    }

    public void remove(Account account) throws AppBaseException {
        accountFacade.remove(account);
    }

    public void markActive(Account account, boolean active) {
        accountFacade.setActive(account, active);
    }

    public Account find(Long id) {
        return accountFacade.find(id);
    }

    public List<Account> findAll() {
        return accountFacade.findAll();
    }

    public void createAccount(AppAdmin adm) throws AppBaseException {
        accountFacade.create(adm);
    }

    public void createAccount(Specialist specialist) throws AppBaseException {
        accountFacade.create(specialist);
    }

    public void createAccount(Assigner assigner) throws AppBaseException {
        accountFacade.create(assigner);
    }

    @PermitAll
    public void createAccount(Notifier notifier) throws AppBaseException {
        accountFacade.create(notifier);
    }

    @RolesAllowed({"AppAdmin", "Notifier", "Specialist", "Assigner"})
    public Account findLogin(String myLogin) throws AppBaseException{
        return accountFacade.findLogin(myLogin);
    }

    @RolesAllowed({"AppAdmin", "Notifier", "Specialist", "Assigner"})
    public void changeMyPasword(Account account, String newPass) {
        accountFacade.changeMyPasword(account, newPass);
    }

    @ExcludeClassInterceptors
    @RolesAllowed("AUTHENTICATOR")
    public Account findLoginAndHashpassActiveAndConfirmed(String login, String hashPass) {
        return accountFacade.findLoginAndHashpassActiveAndConfirmed(login, hashPass);
    }
}
