package pl.lodz.p.it.spjava.fm.web.specialist;

import java.io.Serializable;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import pl.lodz.p.it.spjava.fm.dto.SpecialistDTO;
import pl.lodz.p.it.spjava.fm.ejb.enpoints.SpecialistEndpoint;
import pl.lodz.p.it.spjava.fm.exception.AppBaseException;

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

    public String saveEditSpecialistDTO()throws AppBaseException {
        if (null == editSpecialistDTO) {
            throw new IllegalArgumentException("Proba zatwierdzenia danych bez wypelnienia formularza");
        }
        specialistEndpoint.saveSpecialistAfterEdit(editSpecialistDTO);
        return cancelOrEdit();
    }

    public String cancelOrEdit() {
        conversation.end();
        return "specList";
    }
}
