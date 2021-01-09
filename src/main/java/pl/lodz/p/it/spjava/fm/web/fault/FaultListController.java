package pl.lodz.p.it.spjava.fm.web.fault;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import pl.lodz.p.it.spjava.fm.dto.FaultDTO;
import pl.lodz.p.it.spjava.fm.ejb.enpoints.FaultEndpoint;

@Named
@ViewScoped
public class FaultListController implements Serializable {

    @Inject
    private Conversation conversation;
    @EJB
    private FaultEndpoint faultEndpoint;

    private List<FaultDTO> faultsDTO;

    @PostConstruct
    public void init() {
        faultsDTO = faultEndpoint.getAllFaultsAndMakeDTOList();
    }

    public List<FaultDTO> getFaultsDTO() {
        return faultsDTO;
    }

//    public String editFault(FaultDTO faultDTO) {
//        conversation.begin();
//        editSpecialistController.setEditSpecialistDTO(specialistDTO);
//        editSpecialistController.getSpecialistEntityToChange(specialistDTO);
//
//        return "editSpecialist";
//        //No init(), @ViewScoped 
//
//    }
}
