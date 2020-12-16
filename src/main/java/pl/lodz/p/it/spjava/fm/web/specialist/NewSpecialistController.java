package pl.lodz.p.it.spjava.fm.web.specialist;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import pl.lodz.p.it.spjava.fm.dto.SpecialistDTO;
import pl.lodz.p.it.spjava.fm.ejb.enpoints.SpecialistEndpoint;
import pl.lodz.p.it.spjava.fm.exception.AppBaseException;
import pl.lodz.p.it.spjava.fm.exception.SpecialistException;
import pl.lodz.p.it.spjava.fm.web.utils.ContextUtils;

@Named
@ConversationScoped
public class NewSpecialistController implements Serializable {

    private static final Logger LOG = Logger.getLogger(NewSpecialistController.class.getName());

    @Inject
    private Conversation conversation;
    @Inject
    private SpecialistEndpoint specialistEndpoint;

//do formularza new
    private final SpecialistDTO newSpecialistDTO = new SpecialistDTO("Phil", "Collins", "phil@op.pl", "5345", "er4");
    // private final SpecialistDTO newSpecialistDTO = new SpecialistDTO();

    private String passwordRepeat;

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
             ContextUtils.emitInternationalizedMessage("NewSpecialist:passwordRepeat","account.password.different");
            return "";
        }
//         if (!(hasloPowtorz.equals(konto.getHaslo()))){
//            ContextUtils.emitInternationalizedMessage("utworzAdministratoraForm:passwordRepeat", "passwords.not.matching");
//            return null;
//        }
        conversation.begin();
        return "newSpecialistConfirm";
    }

    public String addSpecialist() {
        if (null == newSpecialistDTO) {
            throw new IllegalArgumentException("Proba zatwierdzenia danych bez wypelnienia formularza");
        }
        try {
            specialistEndpoint.addSpecialist(newSpecialistDTO);
            conversation.end();
            return "main";
        } catch (SpecialistException se) {
            if (SpecialistException.KEY_DB_CONSTRAINT.equals(se.getMessage())) {
                ContextUtils.emitInternationalizedMessage("login", SpecialistException.KEY_DB_CONSTRAINT);
            } else {
                Logger.getLogger(NewSpecialistController.class.getName()).log(Level.SEVERE, "Zgłoszenie w metodzie akcji utworzKlienta wyjatku: ", se);
            }
            return null;
        } catch (AppBaseException abe) {
            Logger.getLogger(NewSpecialistController.class.getName()).log(Level.SEVERE, "Zgłoszenie w metodzie akcji utworzKlienta wyjatku typu: ", abe.getClass());
            if (ContextUtils.isInternationalizationKeyExist(abe.getMessage())) {
                ContextUtils.emitInternationalizedMessage(null, abe.getMessage());
            }
            return null;
        }
    }

}
