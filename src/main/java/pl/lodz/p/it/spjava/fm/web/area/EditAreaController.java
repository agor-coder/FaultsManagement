package pl.lodz.p.it.spjava.fm.web.area;

import java.io.Serializable;
import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import pl.lodz.p.it.spjava.fm.dto.TechAreaDTO;
import pl.lodz.p.it.spjava.fm.ejb.enpoints.TechAreaEndpoint;

@Named
@ConversationScoped
public class EditAreaController implements Serializable {

    @Inject
    private Conversation conversation;

    @EJB
    private TechAreaEndpoint areaEndpoint;

    private TechAreaDTO editAreaDTO;

    public TechAreaDTO getEditAreaDTO() {
        return editAreaDTO;
    }

//    void setEditAreaDTOAndGetAreaEntityToEnpoint(TechAreaDTO areaDTO) {
//        try {
//            editAreaDTO = areaEndpoint.getAreaDTOToEdit(areaDTO);
//        } catch (AppBaseException abe) {
//            Logger.getLogger(EditAreaController.class.getName())
//                    .log(Level.SEVERE, "Zgłoszenie w metodzie akcji setEditFaultDTOAndGetFaultEntityToEnpoint wyjatku typu: ", abe);
//            ContextUtils.emitInternationalizedMessage(null, abe.getMessage());
//        }
   // }

//    public String cancelOrEdit() {
//        conversation.end();
//        return "faultList";
//    }
}
