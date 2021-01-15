package pl.lodz.p.it.spjava.fm.web.specialist;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import pl.lodz.p.it.spjava.fm.dto.FaultDTO;
import pl.lodz.p.it.spjava.fm.dto.SpecialistDTO;
import pl.lodz.p.it.spjava.fm.ejb.enpoints.FaultEndpoint;
import pl.lodz.p.it.spjava.fm.ejb.enpoints.SpecialistEndpoint;
import pl.lodz.p.it.spjava.fm.exception.AppBaseException;

@Named
@ConversationScoped
public class SpecListController implements Serializable {

    @EJB
    private SpecialistEndpoint specialistEndpoint;
    @EJB
    private FaultEndpoint faultEndpoint;

    @Inject
    private EditSpecialistController editSpecialistController;

    @Inject
    private Conversation conversation;

    private FaultDTO faultDTO;
    private List<SpecialistDTO> specialistsDTO;

    @PostConstruct
    public void init() {
        specialistsDTO = specialistEndpoint.getAllSpecialistsAndMakeDTOList();
    }

    public FaultDTO getFaultDTO() {
        return faultDTO;
    }

    public List<SpecialistDTO> getSpecialistsDTO() {
        return specialistsDTO;
    }

    public void activateSpecialist(SpecialistDTO specialistDTO) throws AppBaseException {//obsłużyć
        specialistEndpoint.activateSpecialist(specialistDTO);
        init();
    }

    public void deactivateSpecialist(SpecialistDTO specialistDTO) throws AppBaseException {//obsłużyć
        specialistEndpoint.deactivateSpecialist(specialistDTO);
        init();
    }

    public void removeSpecialist(SpecialistDTO specialistDTO) throws AppBaseException {//obsłużyć
        specialistEndpoint.removeSpecialist(specialistDTO);
        init();
    }

    public String editSpecialist(SpecialistDTO specialistDTO) {
        conversation.begin();
        editSpecialistController.setEditSpecialistDTO(specialistDTO);
        editSpecialistController.getSpecialistEntityToChange(specialistDTO);

        return "editSpecialist";

    }

    public void setFaultDTO(FaultDTO fDTO) {
        faultDTO = fDTO;
        System.out.println("usterka ustawiona" + faultDTO.getWhoNotified());
    }

    public String assignSpecialist(SpecialistDTO specialistDTO) throws AppBaseException {//obsłużyć
        System.out.println(specialistDTO);
        System.out.println(faultDTO);
        faultEndpoint.assignSpecialist(specialistDTO, faultDTO);
        return cancelFaultList();
    }

    public String cancelMain() {
        conversation.end();
        return "main";
    }

    public String cancelFaultList() {
        conversation.end();
        return "faultList";
    }
}
