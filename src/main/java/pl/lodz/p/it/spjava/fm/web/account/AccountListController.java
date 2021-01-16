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
import pl.lodz.p.it.spjava.fm.ejb.enpoints.AccountEndpoint;
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
        accountsDTO = accountEndpoint.getAllAccountsAndMakeDTOList();
    }

    public List<AccountDTO> getAccountsDTO() {
        return accountsDTO;
    }

    public void activateAccount(AccountDTO accountDTO) throws AppBaseException {//obsłużyć
        accountEndpoint.activateAccount(accountDTO);
        init();
    }

    public void deactivateAccount(AccountDTO accountDTO) throws AppBaseException {//obsłużyć
        accountEndpoint.deactivateAccount(accountDTO);
        init();
    }

    public void removeAccount(AccountDTO accountDTO)  {//obsłużyć
        try{
        accountEndpoint.removeAccount(accountDTO);
        init();
        }catch (AppBaseException abe) {
            Logger.getLogger(EditAccountController.class.getName())
                    .log(Level.SEVERE, "Zgłoszenie w metodzie akcji edytujKonto wyjatku typu: ", abe);
            ContextUtils.emitInternationalizedMessage(null, abe.getMessage());
        }
    }

    public String editAccount(AccountDTO accountDTO) {
        conversation.begin();
        editAccountController.setEditAccountDTOAndGetAccountEntityToEnpoint(accountDTO);

        return "editAccount";
    }

    public String changePassword(AccountDTO accountDTO) {
        conversation.begin();
        editAccountController.setEditAccountDTOAndGetAccountEntityToEnpoint(accountDTO);

        return "changePassword";
        //No init(), @ViewScoped 

    }
}
