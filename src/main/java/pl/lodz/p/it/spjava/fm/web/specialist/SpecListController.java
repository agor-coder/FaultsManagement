package pl.lodz.p.it.spjava.fm.web.specialist;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.Conversation;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import pl.lodz.p.it.spjava.fm.dto.SpecialistDTO;
import pl.lodz.p.it.spjava.fm.enpoints.SpecialistEndpoint;

@Named
@ViewScoped
public class SpecListController implements Serializable {

    @Inject
    private SpecialistEndpoint specialistEndpoint;

//    @Inject
//    private EditClientController editClientController;
//    @Inject
//    private Conversation conversation;
    private List<SpecialistDTO> specialistsDTO;

    @PostConstruct
    public void init() {
        specialistsDTO = specialistEndpoint.getAllSpecialists();
    }

    public List<SpecialistDTO> getSpecialistsDTO() {
        return specialistsDTO;
    }

    public void activateSpecialist(SpecialistDTO specialistDTO) {
        specialistEndpoint.activateSpecialist(specialistDTO);
        init();
    }

    public void deactivateSpecialist(SpecialistDTO specialistDTO) {
        specialistEndpoint.deactivateSpecialist(specialistDTO);
        init();
    }

    public void removeSpecialist(SpecialistDTO specialistDTO) {
        specialistEndpoint.removeSpecialist(specialistDTO);
        init();
    }

}
