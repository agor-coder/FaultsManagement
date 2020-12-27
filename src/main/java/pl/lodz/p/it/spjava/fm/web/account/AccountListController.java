package pl.lodz.p.it.spjava.fm.web.account;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import pl.lodz.p.it.spjava.fm.dto.AccountDTO;
import pl.lodz.p.it.spjava.fm.ejb.enpoints.AccountEndpoint;
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

    private AccountDTO editedAccountDTO;
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

    public void removeAccount(AccountDTO accountDTO) throws AppBaseException {//obsłużyć
        accountEndpoint.removeAccount(accountDTO);
        init();
    }

    public String editAccount(AccountDTO accountDTO) {
        conversation.begin();   
        editAccountController.setEditAccountDTO(accountDTO);
        editAccountController.getAccountEntityToChange(accountDTO);

        return "editAccount";
        //No init(), @ViewScoped 

    }
}
