package pl.lodz.p.it.spjava.fm.web.fault;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import pl.lodz.p.it.spjava.fm.dto.FaultDTO;
import pl.lodz.p.it.spjava.fm.ejb.enpoints.FaultEndpoint;
import pl.lodz.p.it.spjava.fm.exception.AppBaseException;
import pl.lodz.p.it.spjava.fm.web.account.EditAccountController;
import pl.lodz.p.it.spjava.fm.web.utils.ContextUtils;

@Named
@ConversationScoped
public class EditFaultController implements Serializable {

    @Inject
    private Conversation conversation;

    @EJB
    private FaultEndpoint faultEndpoint;

    private FaultDTO editFaultDTO;

    public FaultDTO getEditFaultDTO() {
        return editFaultDTO;
    }

    void setEditFaultDTOAndGetFaultEntityToEnpoint(FaultDTO faultDTO) {
        try {
            editFaultDTO = faultEndpoint.getFaultDTOToEdit(faultDTO);
        } catch (AppBaseException abe) {
            Logger.getLogger(EditFaultController.class.getName())
                    .log(Level.SEVERE, "Zgłoszenie w metodzie akcji setEditFaultDTOAndGetFaultEntityToEnpoint wyjatku typu: ", abe);
            ContextUtils.emitInternationalizedMessage(null, abe.getMessage());
        }
    }

    public String cancelOrEdit() {
        conversation.end();
        return "faultList";
    }
}
