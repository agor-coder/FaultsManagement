package pl.lodz.p.it.spjava.fm.web.account;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import pl.lodz.p.it.spjava.fm.dto.AccountDTO;
import pl.lodz.p.it.spjava.fm.ejb.endpoints.AccountEndpoint;
import pl.lodz.p.it.spjava.fm.utils.AccountUtils;

@Named
@ViewScoped
public class AccountDataController implements Serializable {

    @EJB
    private AccountEndpoint accountEndpoint;
    private AccountDTO accountDTO;

    @PostConstruct
    private void init() {
        accountDTO = accountEndpoint.getMyAccountDTO();
    }

    public AccountDTO getAccountDTO() {
        return accountDTO;
    }

    public boolean isAppAdmin() {
        return AccountUtils.isAppAdmin(accountDTO);
    }

    public boolean isSpecialist() {
        return AccountUtils.isSpecialist(accountDTO);
    }

    public boolean isNotifier() {
        return AccountUtils.isNotifier(accountDTO);
    }

    public boolean isAssigner() {
        return AccountUtils.isAssigner(accountDTO);
    }
}
