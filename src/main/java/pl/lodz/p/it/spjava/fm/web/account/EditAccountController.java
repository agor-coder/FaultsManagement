package pl.lodz.p.it.spjava.fm.web.account;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import pl.lodz.p.it.spjava.fm.dto.AccountDTO;
import pl.lodz.p.it.spjava.fm.ejb.enpoints.AccountEndpoint;
import pl.lodz.p.it.spjava.fm.exception.AppBaseException;
import pl.lodz.p.it.spjava.fm.utils.AccountUtils;
import pl.lodz.p.it.spjava.fm.web.utils.ContextUtils;

@Named
@ConversationScoped
public class EditAccountController implements Serializable {

    @Inject
    private Conversation conversation;

    @EJB
    private AccountEndpoint accountEndpoint;

    private AccountDTO editAccountDTO;

    public AccountDTO getEditAccountDTO() {
        return editAccountDTO;
    }

    void setEditAccountDTOAndGetAccountEntityToEnpoint(AccountDTO accountDTO) {
        setEditAccountDTO(accountDTO);
        getAccountEntityToChange(editAccountDTO);
    }

    public void setEditAccountDTO(AccountDTO accountDTO) {
        editAccountDTO = accountEndpoint.getAccountToEdit(accountDTO);
        System.out.println(editAccountDTO + "od setedit");
    }

    public void getAccountEntityToChange(AccountDTO accountDTO) {
        try {
            accountEndpoint.setEndpointAccountFromDTOToEdit(editAccountDTO);
        } catch (AppBaseException abe) {
            Logger.getLogger(EditAccountController.class.getName())
                    .log(Level.SEVERE, "Zgłoszenie w metodzie akcji edytujKonto wyjatku typu: ", abe);
            ContextUtils.emitInternationalizedMessage(null, abe.getMessage());
        }
    }

    public String saveEditAccountDTO() {
        if (null == editAccountDTO) {
            throw new IllegalArgumentException("Proba zatwierdzenia danych bez wypelnienia formularza");
        }
        try {
            accountEndpoint.saveAccountAfterEdit(editAccountDTO);
            return cancelOrEdit();
        } catch (AppBaseException abe) {
            Logger.getLogger(EditAccountController.class.getName())
                    .log(Level.SEVERE, "Zgłoszenie w metodzie akcji edytujKonto wyjatku typu: ", abe);
            ContextUtils.emitInternationalizedMessage(null, abe.getMessage());
            return null;
        }
    }

    public boolean isSpecialist() {
        return AccountUtils.isSpecialist(editAccountDTO);
    }

    public boolean isNotifier() {
        return AccountUtils.isNotifier(editAccountDTO);
    }

    public boolean isFaultassigner() {
        return AccountUtils.isFaultAssigner(editAccountDTO);
    }

    public String cancelOrEdit() {
        conversation.end();
        return "accountList";
    }

}
