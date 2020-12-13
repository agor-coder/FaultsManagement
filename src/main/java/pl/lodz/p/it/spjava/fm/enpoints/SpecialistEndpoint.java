package pl.lodz.p.it.spjava.fm.enpoints;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import pl.lodz.p.it.spjava.fm.dto.SpecialistDTO;
import pl.lodz.p.it.spjava.fm.facade.SpecialistFacade;
import pl.lodz.p.it.spjava.fm.model.Specialist;
import pl.lodz.p.it.spjava.fm.utils.DTOConverter;

@Stateful
public class SpecialistEndpoint {

    @EJB
    private SpecialistFacade specialistFacade;

    private Specialist endpointSpecialist;

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
        listSpecialist.stream().map(specialist -> DTOConverter.makeSpecialistDTOFromEntity(specialist))
                .forEachOrdered(specialistDTO -> {
                    listSpecialistDTO.add(specialistDTO);
                });
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

    public SpecialistDTO getEditedSpecialist(SpecialistDTO specialistDTO) {
        endpointSpecialist = specialistFacade.find(specialistDTO.getId());
        System.out.println(endpointSpecialist);
        return DTOConverter.makeSpecialistDTOFromEntity(endpointSpecialist);
    }

    public void saveSpecialistAfterEdit(SpecialistDTO specialistDTO) {
        endpointSpecialist = specialistFacade.find(specialistDTO.getId());
        System.out.println(endpointSpecialist);
        if (null == endpointSpecialist) {
            throw new IllegalArgumentException("Brak konta do edycji");
        }
        writeEditableDataFromDTOToEntity(specialistDTO, endpointSpecialist);
        specialistFacade.edit(endpointSpecialist);
    }

    private void writeEditableDataFromDTOToEntity(SpecialistDTO editSpecialistDTO, Specialist specialist) {
        specialist.setFirstName(editSpecialistDTO.getFirstName());
        specialist.setSureName(editSpecialistDTO.getSureName());
        specialist.setEmail(editSpecialistDTO.getEmail());
        specialist.setDepartment(editSpecialistDTO.getDepartment());
        specialist.setPhone(editSpecialistDTO.getPhone());
        specialist.setActive(editSpecialistDTO.isActive());
    }

}
