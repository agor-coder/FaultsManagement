package pl.lodz.p.it.spjava.fm.web.area;

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
import pl.lodz.p.it.spjava.fm.dto.TechAreaDTO;
import pl.lodz.p.it.spjava.fm.ejb.endpoints.TechAreaEndpoint;
import pl.lodz.p.it.spjava.fm.exception.AppBaseException;
import pl.lodz.p.it.spjava.fm.web.utils.ContextUtils;

@Named
@ViewScoped
public class AreaListController implements Serializable {

    @Inject
    private Conversation conversation;
    @EJB
    private TechAreaEndpoint areaEndpoint;
    @Inject
    private EditAreaController editAreaController;

    private List<TechAreaDTO> areasDTO;

    @PostConstruct
    public void init() {
        areasDTO = areaEndpoint.getAllAreasDTO();

    }

    public List<TechAreaDTO> getAreasDTO() {
        return areasDTO;
    }

    public String remove(TechAreaDTO areaDTO) {
        try {
            areaEndpoint.remove(areaDTO);
            init();
        } catch (AppBaseException abe) {
            Logger.getLogger(EditAreaController.class.getName())
                    .log(Level.SEVERE, "Zgłoszenie w metodzie akcji removeArea wyjatku typu: ", abe);
            ContextUtils.emitInternationalizedMessage(null, abe.getMessage());
            return "";
        }
        return "areaList";
    }

    public String editArea(TechAreaDTO areaDTO) {
        conversation.begin();
        editAreaController.setAreaDTOAndGetAreaEntityToEnpoint(areaDTO);
        return "editArea";
    }

    public String createArea() {
        conversation.begin();
        return "newArea";
    }
}
