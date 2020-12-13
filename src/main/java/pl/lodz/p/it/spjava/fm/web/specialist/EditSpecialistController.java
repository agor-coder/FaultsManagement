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
public class EditSpecialistController implements Serializable {

    @Inject
    private Conversation conversation;

    @Inject
    private SpecialistEndpoint specialistEndpoint;

    private SpecialistDTO editSpecialistDTO = new SpecialistDTO();

    public SpecialistDTO getEditSpecialistDTO() {
        return editSpecialistDTO;
    }

    public void setEditSpecialistDTO(SpecialistDTO editSpecialistDTO) {
        this.editSpecialistDTO = editSpecialistDTO;
    }

    public String saveEditSpecialistDTO() {
        if (null == editSpecialistDTO) {
            throw new IllegalArgumentException("Proba zatwierdzenia danych bez wypelnienia formularza");
        }

        specialistEndpoint.saveSpecialistAfterEdit(editSpecialistDTO);
        conversation.end();
        return "specList";
    }

    public String cancelEdit() {
        conversation.end();
        return "specList";
    }
}
