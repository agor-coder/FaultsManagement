package pl.lodz.p.it.spjava.fm.ejb.managers;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.SessionSynchronization;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import pl.lodz.p.it.spjava.fm.ejb.facade.AccountFacade;
import pl.lodz.p.it.spjava.fm.ejb.interceptor.LoggingInterceptor;
import pl.lodz.p.it.spjava.fm.ejb.interceptor.PerformanceInterceptor;
import pl.lodz.p.it.spjava.fm.exception.AppBaseException;
import pl.lodz.p.it.spjava.fm.model.Account;

@Stateful
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@Interceptors(LoggingInterceptor.class)
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
}
