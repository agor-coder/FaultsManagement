package pl.lodz.p.it.spjava.fm.web.specialist;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import pl.lodz.p.it.spjava.fm.dto.SpecialistDTO;
import pl.lodz.p.it.spjava.fm.model.Specialist;

@Named
@RequestScoped
public class NewSpecialistPageBean {

    @Inject
    private SpecialistController specialistController;
    
    private final SpecialistDTO specialist = new SpecialistDTO();

    public SpecialistDTO getSpecialist() {
        return specialist;
    }

   

    public String process() {
        System.out.println("process " + specialist);
        specialistController.setNewSpecialistCreate(specialist);
        return "newSpecialistConfirm";
    }

  
}
