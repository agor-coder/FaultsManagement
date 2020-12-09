package pl.lodz.p.it.spjava.fm.web.specialist;

import java.io.Serializable;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import pl.lodz.p.it.spjava.fm.dto.SpecialistDTO;
import pl.lodz.p.it.spjava.fm.enpoints.SpecialistEndpoint;

@Named
@ConversationScoped
public class NewSpecialistController implements Serializable {

    @Inject
    private Conversation conversation;
    @Inject
    private SpecialistEndpoint specialistEndpoint;

    private SpecialistDTO newSpecialistDTO = new SpecialistDTO();

    public SpecialistDTO getNewSpecialistDTO() {
        return newSpecialistDTO;
    }


    public String confirmSpecialist() {
//        if (!passwordRepeat.equals(newClient.getPassword()))
//        {
//            FacesContext.getCurrentInstance().addMessage("NewClient:passwordRepeat", new FacesMessage("The passwords are different!"));
//
//            return "";
//        }
        conversation.begin();
        conversation.setTimeout(180000);
        return "newSpecialistConfirm";
    }

    public String addSpecialist() {
        if (null == newSpecialistDTO) {
            throw new IllegalArgumentException("Proba zatwierdzenia danych bez wypelnienia formularza");
        }
        specialistEndpoint.addSpecialist(newSpecialistDTO);
        conversation.end();
        return "main";
    }

}
