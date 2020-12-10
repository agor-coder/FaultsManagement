package pl.lodz.p.it.spjava.fm.web.specialist;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.Conversation;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import pl.lodz.p.it.spjava.fm.dto.SpecialistDTO;
import pl.lodz.p.it.spjava.fm.enpoints.SpecialistEndpoint;

@Named
@ViewScoped
public class SpecListController implements Serializable {

    @Inject
    private SpecialistEndpoint specialistEndpoint;

//    @Inject
//    private EditClientController editClientController;
//    @Inject
//    private Conversation conversation;

    private List<SpecialistDTO> specialistsDTO;

    @PostConstruct
    public void init() {
        specialistsDTO = specialistEndpoint.getAllSpecialists();
    }

     public List<SpecialistDTO> getSpecialistsDTO() {
        return specialistsDTO;
    }

//    public void removeSpecialist(Specialist specialist) {
//        clientStore.removeClient(client);
//        init();
//    }    
//    public void activateClient(Client client) {
//        clientStore.activateClient(client);
//        init();
//    }    
//
//    public void deactivateClient(Client client) {
//        clientStore.deactivateClient(client);
//        init();
//    }    
//
//    public String editClient(Client client) {
//        conversation.begin();
//        conversation.setTimeout(180000);
//        editClientController.setEditedClient(client);
//        return "editClient";
//        //Nie wywolujemy init(), poniewaz przejscie do EditClient i powrot do lisyty oznacza zmiane widoku,
//        //a SpecListController jest @ViewScoped wiec zostanie utworzony na nowo
//       
//    }

   
}
