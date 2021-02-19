package pl.lodz.p.it.spjava.fm.web.fault;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import pl.lodz.p.it.spjava.fm.dto.FaultDTO;
import pl.lodz.p.it.spjava.fm.ejb.endpoints.FaultEndpoint;
import pl.lodz.p.it.spjava.fm.exception.AppBaseException;
import pl.lodz.p.it.spjava.fm.web.utils.ContextUtils;

@Named
@ViewScoped
public class MyFaultListController implements Serializable {

    @EJB
    private FaultEndpoint faultEndpoint;


    private List<FaultDTO> myFaultsDTO;

    @PostConstruct
    public void init() {
        myFaultsDTO = faultEndpoint.getSpecFaultsDTO();
    }

    public List<FaultDTO> getMyFaultsDTO() {
        return myFaultsDTO;
    }

   
    public void setStatusEND(FaultDTO faultDTO) {
        try {
            faultEndpoint.setStatusEND(faultDTO);
            init();
        } catch (AppBaseException abe) {
            Logger.getLogger(MyFaultListController.class
                    .getName())
                    .log(Level.SEVERE, "Zgłoszenie w metodzie akcji setStatusEND wyjatku typu: ", abe);
            ContextUtils.emitInternationalizedMessage(null, abe.getMessage());
        }
    }

}
