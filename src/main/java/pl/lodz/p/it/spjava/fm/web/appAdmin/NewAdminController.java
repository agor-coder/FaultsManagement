package pl.lodz.p.it.spjava.fm.web.appAdmin;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import pl.lodz.p.it.spjava.fm.dto.AppAdminDTO;
import pl.lodz.p.it.spjava.fm.ejb.endpoints.AccountEndpoint;
import pl.lodz.p.it.spjava.fm.exception.AppBaseException;
import pl.lodz.p.it.spjava.fm.web.utils.ContextUtils;

@Named
@ConversationScoped
public class NewAdminController implements Serializable {

    private static final Logger LOG = Logger.getLogger(NewAdminController.class.getName());

    @Inject
    private Conversation conversation;

    @EJB
    private AccountEndpoint accountEndpoint;

    private final AppAdminDTO newAdminDTO = new AppAdminDTO();

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

    public AppAdminDTO getNewAdminDTO() {
        return newAdminDTO;
    }

    public String confirmAdmin() {
        if (!passwordRepeat.equals(newAdminDTO.getPassword())) {
            ContextUtils.emitInternationalizedMessage("NewAdmin:passwordRepeat", "account.password.different");
            return "";
        }
        conversation.begin();
        return "newAdminConfirm";
    }

    public String addAdmin() {
        if (null == newAdminDTO) {
            throw new IllegalArgumentException("Proba zatwierdzenia danych bez wypelnienia formularza");
        }
        try {
            accountEndpoint.addAdmin(newAdminDTO);
             success = true;
            return "";
        } catch (AppBaseException abe) {
            LOG.log(Level.SEVERE, "Zgłoszenie w metodzie akcji addAdmin wyjatku typu: ", abe);
            ContextUtils.emitInternationalizedMessage("login", abe.getMessage());
            return null;
        }
    }

    public String cancel() {
        conversation.end();
        return "newAdmin";
    }

}
