package pl.lodz.p.it.spjava.fm.web.fault;

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
import pl.lodz.p.it.spjava.fm.dto.TechAreaDTO;
import pl.lodz.p.it.spjava.fm.ejb.endpoints.FaultEndpoint;
import pl.lodz.p.it.spjava.fm.ejb.endpoints.TechAreaEndpoint;
import pl.lodz.p.it.spjava.fm.exception.AppBaseException;
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
    private TechAreaEndpoint areaEndpoint;

    private List<TechAreaDTO> areasDTO;
    private long techAreaId;
    private TechAreaDTO areaDTO;
    private boolean success;

    @PostConstruct
    public void init() {
        areasDTO = areaEndpoint.getAllAreasDTO();
    }

    private final FaultDTO newFaultDTO = new FaultDTO();

    public List<TechAreaDTO> getAreasDTO() {
        return areasDTO;
    }

    public FaultDTO getNewFaultDTO() {
        return newFaultDTO;
    }

    public TechAreaDTO getAreaDTO() {
        return areaDTO;
    }

    public long getTechAreaId() {
        return techAreaId;
    }

    public void setTechAreaId(long techAreaId) {
        this.techAreaId = techAreaId;
    }

    public boolean isSuccess() {
        return success;
    }

    public String confirmFault() {
        for (TechAreaDTO a : areasDTO) {
            if (techAreaId == a.getId()) {
                areaDTO = a;
            }
        }
        conversation.begin();
        return "newFaultConfirm";
    }

    public String addFault() {
        if (null == newFaultDTO) {
            throw new IllegalArgumentException("Proba zatwierdzenia danych bez wypelnienia formularza");
        }
        try {
            faultEndpoint.addFault(newFaultDTO, techAreaId);
            success = true;
            return "";
        } catch (AppBaseException abe) {
            LOG.log(Level.SEVERE, "Zgłoszenie w metodzie akcji addFault wyjatku typu: ", abe);
            ContextUtils.emitInternationalizedMessage("login", abe.getMessage());
            return null;
        }
    }

    public String cancel() {
        conversation.end();
        return "newFault";
    }

}
