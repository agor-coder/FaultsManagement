package pl.lodz.p.it.spjava.fm.web.fault;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import pl.lodz.p.it.spjava.fm.dto.FaultDTO;
import pl.lodz.p.it.spjava.fm.ejb.enpoints.FaultEndpoint;
import pl.lodz.p.it.spjava.fm.exception.AppBaseException;
import pl.lodz.p.it.spjava.fm.web.account.EditAccountController;
import pl.lodz.p.it.spjava.fm.web.specialist.SpecListController;
import pl.lodz.p.it.spjava.fm.web.utils.ContextUtils;

@Named
@ViewScoped
public class FaultListController implements Serializable {

    @Inject
    private Conversation conversation;
    @EJB
    private FaultEndpoint faultEndpoint;
    @Inject
    private EditFaultController editFaultController;
    @Inject
    private SpecListController specListController;

    private List<FaultDTO> faultsDTO;

    @PostConstruct
    public void init() {
        faultsDTO = faultEndpoint.getAllFaultsAndMakeDTOList();
    }

    public List<FaultDTO> getFaultsDTO() {
        return faultsDTO;
    }

    public String editFault(FaultDTO faultDTO) {
        conversation.begin();
        editFaultController.setEditFaultDTOAndGetFaultEntityToEnpoint(faultDTO);

        return "editFault";
    }

    public String assign(FaultDTO faultDTO) throws AppBaseException {//obsłużyć
        conversation.begin(); 
        specListController.setFaultDTOAndfaultEndpoint(faultDTO);
        return "specList";
    }

    public void setStatusEND(FaultDTO faultDTO) {
        try {
            faultEndpoint.setEndpointFaultFromDTOToEdit(faultDTO);
            faultEndpoint.setStatusEND(faultDTO);
            init();
        } catch (AppBaseException abe) {
            Logger.getLogger(EditAccountController.class.getName())
                    .log(Level.SEVERE, "Zgłoszenie w metodzie akcji setStatus wyjatku typu: ", abe);
            ContextUtils.emitInternationalizedMessage(null, abe.getMessage());
        }
    }

}
