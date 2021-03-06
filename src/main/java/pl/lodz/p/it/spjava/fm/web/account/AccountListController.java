package pl.lodz.p.it.spjava.fm.web.account;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import pl.lodz.p.it.spjava.fm.dto.AccountDTO;
import pl.lodz.p.it.spjava.fm.ejb.endpoints.AccountEndpoint;
import pl.lodz.p.it.spjava.fm.web.utils.ContextUtils;
import pl.lodz.p.it.spjava.fm.exception.AppBaseException;

@Named
@ViewScoped
public class AccountListController implements Serializable {

    @EJB
    private AccountEndpoint accountEndpoint;

    @Inject
    private EditAccountController editAccountController;

    @Inject
    private Conversation conversation;

    private List<AccountDTO> accountsDTO;

    @PostConstruct
    public void init() {
        accountsDTO = accountEndpoint.getAllAccountsDTO();
    }

    public List<AccountDTO> getAccountsDTO() {
        return accountsDTO;
    }

    public String activateAccount(AccountDTO accountDTO) {
        try {
            accountEndpoint.activateAccount(accountDTO);
            init();
        } catch (AppBaseException abe) {
            Logger.getLogger(EditAccountController.class.getName())
                    .log(Level.SEVERE, "Zgłoszenie w metodzie akcji activateAccount wyjatku typu: ", abe);
            ContextUtils.emitInternationalizedMessage(null, abe.getMessage());
             return "";
        }
        return "accountList";
    }

    public String deactivateAccount(AccountDTO accountDTO) {
        try {
            accountEndpoint.deactivateAccount(accountDTO);
            init();
        } catch (AppBaseException abe) {
            Logger.getLogger(EditAccountController.class.getName())
                    .log(Level.SEVERE, "Zgłoszenie w metodzie akcji deactivateAccount wyjatku typu: ", abe);
            ContextUtils.emitInternationalizedMessage(null, abe.getMessage());
            return "";
        }
        return "accountList";
    }

    public String removeAccount(AccountDTO accountDTO) {
        try {
            accountEndpoint.removeAccount(accountDTO);
        } catch (AppBaseException abe) {
            Logger.getLogger(EditAccountController.class.getName())
                    .log(Level.SEVERE, "Zgłoszenie w metodzie akcji removeAccount wyjatku typu: ", abe);
            ContextUtils.emitInternationalizedMessage(null, abe.getMessage());
            return "";
        }
        return "accountList";
    }

    public String editAccount(AccountDTO accountDTO) {
        try {
            editAccountController.setEditAccountDTOAndGetAccountEntityToEnpoint(accountDTO);
        } catch (AppBaseException abe) {
            Logger.getLogger(EditAccountController.class.getName())
                    .log(Level.SEVERE, "Zgłoszenie w metodzie akcji editAccount wyjatku typu: ", abe);
            ContextUtils.emitInternationalizedMessage(null, abe.getMessage());
            return "";
        }
        conversation.begin();
        return "editAccount";
    }

    public String changePassword(AccountDTO accountDTO) {
        try {
            editAccountController.setEditAccountDTOAndGetAccountEntityToEnpoint(accountDTO);
        } catch (AppBaseException abe) {
            Logger.getLogger(EditAccountController.class.getName())
                    .log(Level.SEVERE, "Zgłoszenie w metodzie akcji changePassword wyjatku typu: ", abe);
            ContextUtils.emitInternationalizedMessage(null, abe.getMessage());
            return "";
        }
        conversation.begin();
        return "changePassword";
        //No init(), @ViewScoped 
    }
}
