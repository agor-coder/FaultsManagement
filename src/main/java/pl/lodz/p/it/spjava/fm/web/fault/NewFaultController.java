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
import pl.lodz.p.it.spjava.fm.dto.TechAreaDTO;
import pl.lodz.p.it.spjava.fm.ejb.enpoints.FaultEndpoint;
import pl.lodz.p.it.spjava.fm.ejb.managers.TechAreaManager;
import pl.lodz.p.it.spjava.fm.exception.AppBaseException;
import pl.lodz.p.it.spjava.fm.utils.DTOConverter;
import pl.lodz.p.it.spjava.fm.web.utils.ContextUtils;

@Named
@ConversationScoped
public class NewFaultController implements Serializable {

    private static final Logger LOG = Logger.getLogger(NewFaultController.class.getName());

    @Inject
    private Conversation conversation;

    @EJB
    private FaultEndpoint faultEndpoint;
    @EJB
    private TechAreaManager areaManager;

    private long techAreaId;

//
////do formularza new
    private final FaultDTO newFaultDTO = new FaultDTO("uszkodzony przetwornik mocy 3PS3");
//    //private final FaultDTO newFaultDTO = new FaultDTO();

    public FaultDTO getNewFaultDTO() {
        return newFaultDTO;
    }

    public long getTechAreaId() {
        return techAreaId;
    }

    public void setTechAreaId(long techAreaId) {
        this.techAreaId = techAreaId;
    }

    

    public String confirmFault() {
        TechAreaDTO techAreaDTO = DTOConverter.createTechAreaDTOFromEntity(areaManager.find(techAreaId));
        newFaultDTO.setTechArea(techAreaDTO);
        conversation.begin();
        return "newFaultConfirm";
    }

    public String addFault() {
        if (null == newFaultDTO) {
            throw new IllegalArgumentException("Proba zatwierdzenia danych bez wypelnienia formularza");
        }
        try {
            faultEndpoint.addFault(newFaultDTO);
            conversation.end();
            return "main";
        } catch (AppBaseException abe) {
            LOG.log(Level.SEVERE, "Zgłoszenie w metodzie akcji utworzSpecjalistę wyjatku typu: ", abe);
            ContextUtils.emitInternationalizedMessage("login", abe.getMessage());
            return null;
        }
    }

    public String cancel() {
        conversation.end();
        return "newFault";
    }

}
