package pl.lodz.p.it.spjava.fm.web.specialist;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import pl.lodz.p.it.spjava.fm.dto.SpecialistDTO;
import pl.lodz.p.it.spjava.fm.ejb.endpoints.AccountEndpoint;
import pl.lodz.p.it.spjava.fm.exception.AppBaseException;
import pl.lodz.p.it.spjava.fm.web.utils.ContextUtils;

@Named
@ConversationScoped
public class NewSpecialistController implements Serializable {

    private static final Logger LOG = Logger.getLogger(NewSpecialistController.class.getName());

    @Inject
    private Conversation conversation;

    @EJB
    private AccountEndpoint accountEndpoint;

    private final SpecialistDTO newSpecialistDTO = new SpecialistDTO();

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

    public SpecialistDTO getNewSpecialistDTO() {
        return newSpecialistDTO;
    }

    public String confirmSpecialist() {
        if (!passwordRepeat.equals(newSpecialistDTO.getPassword())) {
            ContextUtils.emitInternationalizedMessage("NewSpecialist:passwordRepeat", "account.password.different");
            return "";
        }

        conversation.begin();
        return "newSpecialistConfirm";
    }

    public String addSpecialist() {
        if (null == newSpecialistDTO) {
            throw new IllegalArgumentException("Proba zatwierdzenia danych bez wypelnienia formularza");
        }
        try {
            accountEndpoint.addSpecialist(newSpecialistDTO);
            success = true;
            return "";
        } catch (AppBaseException abe) {
            LOG.log(Level.SEVERE, "Zgłoszenie w metodzie akcji addSpecialist wyjatku typu: ", abe);
            ContextUtils.emitInternationalizedMessage("login", abe.getMessage());
            return null;
        }
    }

    public String cancel() {
        conversation.end();
        return "newSpecialist";
    }

}
