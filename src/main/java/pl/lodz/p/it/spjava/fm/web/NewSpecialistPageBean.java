package pl.lodz.p.it.spjava.fm.web;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import pl.lodz.p.it.spjava.fm.model.Specialist;

@Named
@RequestScoped
public class NewSpecialistPageBean {

    @Inject
    private SpecialistController specialistController;
    
    private final Specialist specialist = new Specialist();

    public Specialist getSpecialist() {
        return specialist;
    }

    public String process() {
        System.out.println("process " + specialist);
        specialistController.setNewSpecialist(specialist);
        return "newSpecialistConfirm";
    }

  
}
