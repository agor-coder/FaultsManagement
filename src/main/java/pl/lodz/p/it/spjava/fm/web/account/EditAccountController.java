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

    private String passwordRepeat;

    public AccountDTO getEditAccountDTO() {
        return editAccountDTO;
    }

    public String getPasswordRepeat() {
        return passwordRepeat;
    }

    public void setPasswordRepeat(String passwordRepeat) {
        this.passwordRepeat = passwordRepeat;
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

    public String saveEditedSpecialistDTO() {
        if (null == editAccountDTO) {
            throw new IllegalArgumentException("Proba zatwierdzenia danych bez wypelnienia formularza");
        }
        try {
            accountEndpoint.saveSpecialistAfterEdit(editAccountDTO);
            return cancelOrEdit();
        } catch (AppBaseException abe) {
            Logger.getLogger(EditAccountController.class.getName())
                    .log(Level.SEVERE, "Zgłoszenie w metodzie akcji edytujKonto wyjatku typu: ", abe);
            ContextUtils.emitInternationalizedMessage(null, abe.getMessage());
            return null;
        }
    }

    public String saveEditedAssignerDTO() {
        if (null == editAccountDTO) {
            throw new IllegalArgumentException("Proba zatwierdzenia danych bez wypelnienia formularza");
        }
        try {
            accountEndpoint.saveAssignerAfterEdit(editAccountDTO);
            return cancelOrEdit();
        } catch (AppBaseException abe) {
            Logger.getLogger(EditAccountController.class.getName())
                    .log(Level.SEVERE, "Zgłoszenie w metodzie akcji edytujKonto wyjatku typu: ", abe);
            ContextUtils.emitInternationalizedMessage(null, abe.getMessage());
            return null;
        }
    }

    public String saveEditedNotifierDTO() {
        if (null == editAccountDTO) {
            throw new IllegalArgumentException("Proba zatwierdzenia danych bez wypelnienia formularza");
        }
        try {
            accountEndpoint.saveNotifierAfterEdit(editAccountDTO);
            return cancelOrEdit();
        } catch (AppBaseException abe) {
            Logger.getLogger(EditAccountController.class.getName())
                    .log(Level.SEVERE, "Zgłoszenie w metodzie akcji edytujKonto wyjatku typu: ", abe);
            ContextUtils.emitInternationalizedMessage(null, abe.getMessage());
            return null;
        }
    }

    public String changePassword() throws AppBaseException {
        if (!passwordRepeat.equals(editAccountDTO.getPassword())) {
            ContextUtils.emitInternationalizedMessage("changePassword:passwordRepeat", "account.password.different");
            return "";
        }
        try {
            accountEndpoint.changePassword(editAccountDTO);
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

    public boolean isAssigner() {
        return AccountUtils.isAssigner(editAccountDTO);
    }

    public String cancelOrEdit() {
        conversation.end();
        return "accountList";
    }

}
