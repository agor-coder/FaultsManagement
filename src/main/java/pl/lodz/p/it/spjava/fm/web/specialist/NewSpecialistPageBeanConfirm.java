package pl.lodz.p.it.spjava.fm.web.specialist;


import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import pl.lodz.p.it.spjava.fm.dto.SpecialistDTO;

@Named
@RequestScoped
public class NewSpecialistPageBeanConfirm {
    
  @Inject
  private NewSpecialistController specialistController;
    

//    public SpecialistDTO  getSpecialist() {
//        return specialistController. getNewSpecialistCreate();
//    }
//    
//   
//
//    public String processConfirm() {
//        SpecialistDTO specialistDTO=getSpecialist();
//        specialistController.addSpecialist(specialistDTO);
//        return "main";
//    }
}
