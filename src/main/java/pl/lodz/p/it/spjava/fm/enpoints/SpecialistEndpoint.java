package pl.lodz.p.it.spjava.fm.enpoints;

import java.util.List;
import javax.ejb.Stateful;
import javax.inject.Inject;
import pl.lodz.p.it.spjava.fm.dto.SpecialistDTO;
import pl.lodz.p.it.spjava.fm.facade.SpecialistFacade;
import pl.lodz.p.it.spjava.fm.model.Specialist;

@Stateful
public class SpecialistEndpoint {

    @Inject
    private SpecialistFacade specialistFacade;

    public void addSpecialist(SpecialistDTO specialistDTO) {
        Specialist specialist=new Specialist();
        specialist.setLogin(specialistDTO.getLogin());
        specialist.setPassword(specialistDTO.getPassword());
        specialist.setFirstName(specialistDTO.getFirstName());
        specialist.setSureName(specialistDTO.getSureName());
        specialist.setEmail(specialistDTO.getEmail());
        specialist.setPhone(specialistDTO.getPhone());
        specialist.setDepartment(specialistDTO.getDepartment());
       specialist.setActive(specialistDTO.isActive());
        
        specialistFacade.create(specialist);
    }

    public void removeSpecialist(Specialist specialist) {
        specialistFacade.remove(specialist);
    }

    public List<Specialist> getSpecialists() {
        return specialistFacade.findAll();
    }

}
