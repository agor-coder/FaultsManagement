package pl.lodz.p.it.spjava.fm.web.account;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import pl.lodz.p.it.spjava.fm.ejb.enpoints.AccountEndpoint;
import pl.lodz.p.it.spjava.fm.exception.AppBaseException;
import pl.lodz.p.it.spjava.fm.web.utils.ContextUtils;

@Named
@ConversationScoped
public class ChangeMyPasswordController implements Serializable {

    @EJB
    private AccountEndpoint accountEndpoint;
    @Inject
    private Conversation conversation;

    private String password = "";
    private String passwordRepeat = "";
    private String passwordOld = "";
    private boolean success;

    public boolean isSuccess() {
        return success;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordRepeat() {
        return passwordRepeat;
    }

    public void setPasswordRepeat(String passwordRepeat) {
        this.passwordRepeat = passwordRepeat;
    }

    public String getPasswordOld() {
        return passwordOld;
    }

    public void setPasswordOld(String passwordOld) {
        this.passwordOld = passwordOld;
    }

    public String changeMyPassword() {
        if (!(passwordRepeat.equals(password))) {
            ContextUtils.emitInternationalizedMessage("changeMyPassword:passwordRepeat", "error.passwords.not.matching");
            return "";
        }
        try {
            conversation.begin();
            accountEndpoint.changeMyPasword(passwordOld, password);
            success = true;
            conversation.end();
            return "";
        } catch (AppBaseException abe) {
            Logger.getLogger(ChangeMyPasswordController.class.getName())
                    .log(Level.SEVERE, "Zgłoszenie w metodzie akcji changeMyPassword wyjatku typu: ", abe);
            ContextUtils.emitInternationalizedMessage("changeMyPassword:passwordOld", abe.getMessage());
            conversation.end();
            return "";
        }
    }
}
