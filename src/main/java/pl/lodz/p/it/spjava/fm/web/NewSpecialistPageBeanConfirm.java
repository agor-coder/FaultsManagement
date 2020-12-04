package pl.lodz.p.it.spjava.fm.web;


import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import pl.lodz.p.it.spjava.fm.model.Specialist;

@Named
@RequestScoped
public class NewSpecialistPageBeanConfirm {
    
  @Inject
  private SpecialistController specialistController;
    

    public Specialist  getSpecialist() {
        return specialistController. getNewSpecialist();
    }
    
   

    public String processConfirm() {
        System.out.println("processConfirm " + getSpecialist());
        specialistController.addSpecialist();
        return "main";
    }
}
