package pl.lodz.p.it.spjava.fm.web.notifier;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import pl.lodz.p.it.spjava.fm.dto.NotifierDTO;
import pl.lodz.p.it.spjava.fm.ejb.endpoints.AccountEndpoint;
import pl.lodz.p.it.spjava.fm.exception.AppBaseException;
import pl.lodz.p.it.spjava.fm.web.utils.ContextUtils;

@Named
@ConversationScoped
public class NewNotifierController implements Serializable {

    private static final Logger LOG = Logger.getLogger(NewNotifierController.class.getName());

    @Inject
    private Conversation conversation;

    @EJB
    private AccountEndpoint accountEndpoint;

//do formularza new
    private final NotifierDTO newNotifierDTO = new NotifierDTO("Rod", "Stewart", "rod@op.pl", "5388", "MistrzER4");
    // private final NotifierDTO newNotifierDTO = new NotifierDTO();

    private String passwordRepeat;

    private boolean success;

    public boolean isSuccess() {
        return success;
    }

    public String getPasswordRepeat() {
        return passwordRepeat;
    }

    public void setPasswordRepeat(String passwordRepeat) {
        this.passwordRepeat = passwordRepeat;
    }

    public NotifierDTO getNewNotifierDTO() {
        return newNotifierDTO;
    }

    public String confirmNotifier() {
        if (!passwordRepeat.equals(newNotifierDTO.getPassword())) {
            ContextUtils.emitInternationalizedMessage("NewNotifier:passwordRepeat", "account.password.different");
            return "";
        }
        conversation.begin();
        return "newNotifierConfirm";
    }

    public String confirmRegisterNotifier() {
        if (!passwordRepeat.equals(newNotifierDTO.getPassword())) {
            ContextUtils.emitInternationalizedMessage("RegisterNotifier:passwordRepeat", "account.password.different");
            return "";
        }
        conversation.begin();
        return "registerNotifierConfirm";
    }

    public String addNotifier() {
        if (null == newNotifierDTO) {
            throw new IllegalArgumentException("Proba zatwierdzenia danych bez wypelnienia formularza");
        }
        try {
            accountEndpoint.addNotifier(newNotifierDTO);
            success = true;
            return "";
        } catch (AppBaseException abe) {
            LOG.log(Level.SEVERE, "Zgłoszenie w metodzie akcji addNotifier wyjatku typu: ", abe);
            ContextUtils.emitInternationalizedMessage("login", abe.getMessage());
            return null;
        }
    }

    public String regNotifier() {
        if (null == newNotifierDTO) {
            throw new IllegalArgumentException("Proba zatwierdzenia danych bez wypelnienia formularza");
        }
        try {
            accountEndpoint.regNotifier(newNotifierDTO);
            success = true;
            return "";
        } catch (AppBaseException abe) {
            LOG.log(Level.SEVERE, "Zgłoszenie w metodzie akcji regNotifier wyjatku typu: ", abe);
            ContextUtils.emitInternationalizedMessage("login", abe.getMessage());
            return null;
        }
    }

    public String cancel() {
        conversation.end();
        return "newNotifier";
    }

    public String cancelReg() {
        conversation.end();
        return "registerNotifier";
    }
    public String mainPage() {
        conversation.end();
        return "main";
    }

}
