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
        writeEditableDataFromDTOToEntity(specialistDTO, specialist);

        specialistFacade.create(specialist);
    }

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

    private void writeEditableDataFromDTOToEntity(SpecialistDTO SpecialistDTO, Specialist specialist) {
        specialist.setFirstName(SpecialistDTO.getFirstName());
        specialist.setSureName(SpecialistDTO.getSureName());
        specialist.setEmail(SpecialistDTO.getEmail());
        specialist.setDepartment(SpecialistDTO.getDepartment());
        specialist.setPhone(SpecialistDTO.getPhone());
        specialist.setActive(SpecialistDTO.isActive());
    }

}
