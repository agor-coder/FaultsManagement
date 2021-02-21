package pl.lodz.p.it.spjava.fm.web.account;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import pl.lodz.p.it.spjava.fm.dto.AccountDTO;
import pl.lodz.p.it.spjava.fm.ejb.endpoints.AccountEndpoint;
import pl.lodz.p.it.spjava.fm.exception.AppBaseException;
import pl.lodz.p.it.spjava.fm.utils.AccountUtils;
import pl.lodz.p.it.spjava.fm.web.utils.ContextUtils;

@Named
@ViewScoped
public class AccountDataController implements Serializable {

    @EJB
    private AccountEndpoint accountEndpoint;
    private AccountDTO accountDTO;

    @PostConstruct
    private void init() {
        try {
            accountDTO = accountEndpoint.getMyAccountDTO();
        } catch (AppBaseException abe) {
            Logger.getLogger(EditAccountController.class.getName())
                    .log(Level.SEVERE, "Zgłoszenie w metodzie akcji initAccountData wyjatku typu: ", abe);
            ContextUtils.emitInternationalizedMessage(null, abe.getMessage());

        }
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
