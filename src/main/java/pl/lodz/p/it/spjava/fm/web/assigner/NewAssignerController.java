package pl.lodz.p.it.spjava.fm.web.assigner;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import pl.lodz.p.it.spjava.fm.dto.AssignerDTO;
import pl.lodz.p.it.spjava.fm.ejb.endpoints.AccountEndpoint;
import pl.lodz.p.it.spjava.fm.exception.AppBaseException;
import pl.lodz.p.it.spjava.fm.web.utils.ContextUtils;

@Named
@ConversationScoped
public class NewAssignerController implements Serializable {

    private static final Logger LOG = Logger.getLogger(NewAssignerController.class.getName());

    @Inject
    private Conversation conversation;

    @EJB
    private AccountEndpoint accountEndpoint;

  private final AssignerDTO newAssignerDTO = new AssignerDTO();

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

    public AssignerDTO getNewAssignerDTO() {
        return newAssignerDTO;
    }

    public String confirmAssigner() {
        if (!passwordRepeat.equals(newAssignerDTO.getPassword())) {
            ContextUtils.emitInternationalizedMessage("NewAssigner:passwordRepeat", "account.password.different");
            return "";
        }

        conversation.begin();
        return "newAssignerConfirm";
    }

    public String addAssigner() {
        if (null == newAssignerDTO) {
            throw new IllegalArgumentException("Proba zatwierdzenia danych bez wypelnienia formularza");
        }
        try {
            accountEndpoint.addAssigner(newAssignerDTO);
             success=true;   
            return "";
        } catch (AppBaseException abe) {
            LOG.log(Level.SEVERE, "Zgłoszenie w metodzie akcji addAssigner wyjatku typu: ", abe);
            ContextUtils.emitInternationalizedMessage("login", abe.getMessage());
            return null;
        }
    }

    public String cancel() {
        conversation.end();
        return "newAssigner";
    }

}
