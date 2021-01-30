package pl.lodz.p.it.spjava.fm.web.fault;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import pl.lodz.p.it.spjava.fm.dto.FaultDTO;
import pl.lodz.p.it.spjava.fm.ejb.enpoints.FaultEndpoint;

@Named
@ViewScoped
public class NotifierFaultListController implements Serializable {

    @EJB
    private FaultEndpoint faultEndpoint;

    private List<FaultDTO> myFaultsDTO;

    @PostConstruct
    public void init() {
       myFaultsDTO = faultEndpoint.getNotifierFaultsDTO();
    }

    public List<FaultDTO> getMyFaultsDTO() {
        return myFaultsDTO;
    }
 
   
    }


