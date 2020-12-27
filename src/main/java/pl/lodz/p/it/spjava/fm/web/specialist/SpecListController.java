package pl.lodz.p.it.spjava.fm.web.specialist;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import pl.lodz.p.it.spjava.fm.dto.SpecialistDTO;
import pl.lodz.p.it.spjava.fm.ejb.enpoints.SpecialistEndpoint;
import pl.lodz.p.it.spjava.fm.exception.AppBaseException;

@Named
@ViewScoped
public class SpecListController implements Serializable {

    @EJB
    private SpecialistEndpoint specialistEndpoint;

    @Inject
    private EditSpecialistController editSpecialistController;

    @Inject
    private Conversation conversation;

    private SpecialistDTO editedSpecialistDTO;
    private List<SpecialistDTO> specialistsDTO;

    @PostConstruct
    public void init() {
        specialistsDTO = specialistEndpoint.getAllSpecialistsAndMakeDTOList();
    }

    public List<SpecialistDTO> getSpecialistsDTO() {
        return specialistsDTO;
    }


    public void activateSpecialist(SpecialistDTO specialistDTO)throws AppBaseException {//obsłużyć
        specialistEndpoint.activateSpecialist(specialistDTO);
        init();
    }

    public void deactivateSpecialist(SpecialistDTO specialistDTO)throws AppBaseException {//obsłużyć
        specialistEndpoint.deactivateSpecialist(specialistDTO);
        init();
    }

    public void removeSpecialist(SpecialistDTO specialistDTO) throws AppBaseException {//obsłużyć
        specialistEndpoint.removeSpecialist(specialistDTO);
        init();
    }

    public String editSpecialist(SpecialistDTO specialistDTO) {
        conversation.begin();
//        editedSpecialistDTO = specialistEndpoint.getEditedSpecialist(specialistDTO);
        editSpecialistController.setEditSpecialistDTO(specialistDTO);
        editSpecialistController.getSpecialistEntityToChange(specialistDTO);

        return "editSpecialist";
        //No init(), @ViewScoped 

    }
}
