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
import pl.lodz.p.it.spjava.fm.ejb.endpoints.AccountEndpoint;
import pl.lodz.p.it.spjava.fm.exception.AppBaseException;
import pl.lodz.p.it.spjava.fm.model.Account;
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
    private boolean success;

    public boolean isSuccess() {
        return success;
    }

    public AccountDTO getEditAccountDTO() {
        return editAccountDTO;
    }

    public String getPasswordRepeat() {
        return passwordRepeat;
    }

    public void setPasswordRepeat(String passwordRepeat) {
        this.passwordRepeat = passwordRepeat;
    }

    public void setEditAccountDTOAndGetAccountEntityToEnpoint(AccountDTO accountDTO) throws AppBaseException {
        editAccountDTO = accountEndpoint.getAccountToEdit(accountDTO);
    }

    public String saveEditedAdminDTO() {
        if (null == editAccountDTO) {
            throw new IllegalArgumentException("Proba zatwierdzenia danych bez wypelnienia formularza");
        }
        try {
            accountEndpoint.saveAdminAfterEdit(editAccountDTO);
            return success();
        } catch (AppBaseException abe) {
            Logger.getLogger(EditAccountController.class.getName())
                    .log(Level.SEVERE, "Zgłoszenie w metodzie akcji saveEditedAdminDTO wyjatku typu: ", abe);
            ContextUtils.emitInternationalizedMessage(null, abe.getMessage());
            return null;
        }
    }

    public String saveEditedSpecialistDTO() {
        if (null == editAccountDTO) {
            throw new IllegalArgumentException("Proba zatwierdzenia danych bez wypelnienia formularza");
        }
        try {
            accountEndpoint.saveSpecialistAfterEdit(editAccountDTO);
            return success();
        } catch (AppBaseException abe) {
            Logger.getLogger(EditAccountController.class.getName())
                    .log(Level.SEVERE, "Zgłoszenie w metodzie akcji saveEditedSpecialistDTO wyjatku typu: ", abe);
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
            return success();
        } catch (AppBaseException abe) {
            Logger.getLogger(EditAccountController.class.getName())
                    .log(Level.SEVERE, "Zgłoszenie w metodzie akcji saveEditedAssignerDTO wyjatku typu: ", abe);
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
            return success();
        } catch (AppBaseException abe) {
            Logger.getLogger(EditAccountController.class.getName())
                    .log(Level.SEVERE, "Zgłoszenie w metodzie akcji saveEditedNotifierDTO wyjatku typu: ", abe);
            ContextUtils.emitInternationalizedMessage(null, abe.getMessage());
            return "";
        }
    }

    public String changePassword() throws AppBaseException {
        if (!passwordRepeat.equals(editAccountDTO.getPassword())) {
            ContextUtils.emitInternationalizedMessage("changePassword:passwordRepeat", "account.password.different");
            return "";
        }
        try {
            accountEndpoint.changePassword(editAccountDTO);
            return success();
        } catch (AppBaseException abe) {
            Logger.getLogger(EditAccountController.class.getName())
                    .log(Level.SEVERE, "Zgłoszenie w metodzie akcji changePassword wyjatku typu: ", abe);
            ContextUtils.emitInternationalizedMessage(null, abe.getMessage());
            return "";
        }
    }

    public boolean isAppAdmin() {
        return AccountUtils.isAppAdmin(editAccountDTO);
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

    public String getUserName() {
        return ContextUtils.getUserName();
    }

    public String getFirstLastName() throws AppBaseException {
        Account us = accountEndpoint.getMyAccount();
        return us.getFirstName() + " " + us.getSureName();
    }

    public String cancelOrEdit() {
        conversation.end();
        return "accountList";
    }

    private String success() {
        success = true;
        return "";
    }

}
