package pl.lodz.p.it.spjava.fm.web.area;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import pl.lodz.p.it.spjava.fm.dto.TechAreaDTO;
import pl.lodz.p.it.spjava.fm.ejb.enpoints.TechAreaEndpoint;
import pl.lodz.p.it.spjava.fm.exception.AppBaseException;
import pl.lodz.p.it.spjava.fm.web.account.EditAccountController;
import pl.lodz.p.it.spjava.fm.web.utils.ContextUtils;

@Named
@ConversationScoped
public class EditAreaController implements Serializable {

    @Inject
    private Conversation conversation;

    @EJB
    private TechAreaEndpoint areaEndpoint;

    private TechAreaDTO AreaDTO;

    public TechAreaDTO getAreaDTO() {
        return AreaDTO;
    }

    void setAreaDTOAndGetAreaEntityToEnpoint(TechAreaDTO areaDTO) {
        try {
            AreaDTO = areaEndpoint.getTechAreaToEdit(areaDTO);
        } catch (AppBaseException abe) {
            Logger.getLogger(EditAreaController.class.getName())
                    .log(Level.SEVERE, "Zgłoszenie w metodzie akcji setAreaDTOAndGetFaultEntityToEnpoint wyjatku typu: ", abe);
            ContextUtils.emitInternationalizedMessage(null, abe.getMessage());
        }
    }

    public String saveEditedArea() {
        if (null == AreaDTO) {
            throw new IllegalArgumentException("Proba zatwierdzenia danych bez wypelnienia formularza");
        }
        try {
            areaEndpoint.saveAreaAfterEdit(AreaDTO);
            return cancelOrEdit();
        } catch (AppBaseException abe) {
            Logger.getLogger(EditAccountController.class.getName())
                    .log(Level.SEVERE, "Zgłoszenie w metodzie akcji saveEditedAreaDTO wyjatku typu: ", abe);
            ContextUtils.emitInternationalizedMessage(null, abe.getMessage());
            return null;
        }
    }

    public String cancelOrEdit() {
        conversation.end();
        return "areaList";
    }
}
