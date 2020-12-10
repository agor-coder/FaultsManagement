package pl.lodz.p.it.spjava.fm.enpoints;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import pl.lodz.p.it.spjava.fm.dto.SpecialistDTO;
import pl.lodz.p.it.spjava.fm.facade.SpecialistFacade;
import pl.lodz.p.it.spjava.fm.model.Specialist;

@Stateful
public class SpecialistEndpoint {

    @EJB
    private SpecialistFacade specialistFacade;

    public void addSpecialist(SpecialistDTO specialistDTO) {
        Specialist specialist = new Specialist();
        specialist.setLogin(specialistDTO.getLogin());
        specialist.setPassword(specialistDTO.getPassword());
        specialist.setFirstName(specialistDTO.getFirstName());
        specialist.setSureName(specialistDTO.getSureName());
        specialist.setEmail(specialistDTO.getEmail());
        specialist.setPhone(specialistDTO.getPhone());
        specialist.setActive(specialistDTO.isActive());
        specialist.setDepartment(specialistDTO.getDepartment());

        specialistFacade.create(specialist);
    }

//    public void removeSpecialist(Specialist specialist) {
//        specialistFacade.remove(specialist);
//    }
    public List<SpecialistDTO> getAllSpecialists() {
        List<Specialist> listSpecialist = specialistFacade.findAll();
        List<SpecialistDTO> listSpecialistDTO = new ArrayList<>();
        for (Specialist specialist : listSpecialist) {
            SpecialistDTO specialistDTO = new SpecialistDTO(specialist.getId(), specialist.getLogin(), specialist.isActive(),
                    specialist.getFirstName(), specialist.getSureName(), specialist.getEmail(), specialist.getPhone(),
                    specialist.getTyp(), specialist.getDepartment());
            listSpecialistDTO.add(specialistDTO);
        }
        return listSpecialistDTO;
    }

    public void activateSpecialist(SpecialistDTO specialistDTO) {
        markActive(specialistDTO, true);
    }

    public void deactivateSpecialist(SpecialistDTO specialistDTO) {
        markActive(specialistDTO, false);
    }

    public void markActive(SpecialistDTO specialistDTO, boolean active) {
        Specialist tmpSpec = specialistFacade.find(specialistDTO.getId());
        tmpSpec.setActive(active);
    }

    public void removeSpecialist(SpecialistDTO specialistDTO) {
        Specialist tmpSpec = specialistFacade.find(specialistDTO.getId());
        specialistFacade.remove(tmpSpec);
    }

}
