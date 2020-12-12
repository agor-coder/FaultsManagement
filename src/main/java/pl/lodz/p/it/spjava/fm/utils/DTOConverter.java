
package pl.lodz.p.it.spjava.fm.utils;

import pl.lodz.p.it.spjava.fm.dto.SpecialistDTO;
import pl.lodz.p.it.spjava.fm.model.Specialist;

public class DTOConverter {

    
    public static SpecialistDTO makeSpecialistDTOFromEntity(Specialist specialist) {
        return null == specialist ? null : new SpecialistDTO(specialist.getId(), specialist.getLogin(), specialist.isActive(),
                specialist.getFirstName(), specialist.getSureName(), specialist.getEmail(), specialist.getPhone(),
                specialist.getTyp(), specialist.getDepartment());
    }
}
