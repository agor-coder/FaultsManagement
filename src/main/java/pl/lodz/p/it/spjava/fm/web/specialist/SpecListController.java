package pl.lodz.p.it.spjava.fm.web.specialist;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import pl.lodz.p.it.spjava.fm.dto.FaultDTO;
import pl.lodz.p.it.spjava.fm.dto.SpecialistDTO;
import pl.lodz.p.it.spjava.fm.ejb.endpoints.FaultEndpoint;
import pl.lodz.p.it.spjava.fm.ejb.endpoints.SpecialistEndpoint;
import pl.lodz.p.it.spjava.fm.exception.AppBaseException;
import pl.lodz.p.it.spjava.fm.web.utils.ContextUtils;

@Named
@ConversationScoped
public class SpecListController implements Serializable {

    @EJB
    private SpecialistEndpoint specialistEndpoint;
    @EJB
    private FaultEndpoint faultEndpoint;

    @Inject
    private Conversation conversation;

    private FaultDTO faultDTO;
    private List<SpecialistDTO> specialistsDTO;

    @PostConstruct
    public void init() {
        specialistsDTO = specialistEndpoint.getAllSpecialistsDTO();
    }

    public FaultDTO getFaultDTO() {
        return faultDTO;
    }

    public List<SpecialistDTO> getSpecialistsDTO() {
        return specialistsDTO;
    }

    public void setFaultDTOAndfaultEndpoint(FaultDTO fDTO) throws AppBaseException {
        faultDTO = fDTO;
        faultEndpoint.setEndpointFaultFromDTOToEdit(faultDTO);
    }

    public String assignSpecialist(SpecialistDTO specialistDTO) {
        try {
            faultEndpoint.assignSpecialist(specialistDTO);
            return cancelFaultList();
        } catch (AppBaseException abe) {
            Logger.getLogger(SpecListController.class.getName())
                    .log(Level.SEVERE, "Zgłoszenie w metodzie akcji assignSpecialist wyjatku typu: ", abe);
            ContextUtils.emitMessage("specialist:  "+specialistDTO.getSureName());
            ContextUtils.emitInternationalizedMessage(null, abe.getMessage());
            return null;
        }
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
