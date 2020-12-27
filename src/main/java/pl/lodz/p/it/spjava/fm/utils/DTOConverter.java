
package pl.lodz.p.it.spjava.fm.utils;

import pl.lodz.p.it.spjava.fm.dto.AccountDTO;
import pl.lodz.p.it.spjava.fm.dto.SpecialistDTO;
import pl.lodz.p.it.spjava.fm.model.Account;
import pl.lodz.p.it.spjava.fm.model.Specialist;

public class DTOConverter {

    
    public static SpecialistDTO makeSpecialistDTOFromEntity(Specialist specialist) {
        return null == specialist ? null : new SpecialistDTO(specialist.getId(), specialist.getLogin(), specialist.isActive(),
                specialist.getFirstName(), specialist.getSureName(), specialist.getEmail(), specialist.getPhone(),
                specialist.getType(), specialist.getDepartment());
    }

    public static AccountDTO makeAccountDTOFromEntity(Account account) {
       return null == account ? null : new AccountDTO(account.getId(), account.getLogin(), account.isActive(),
                account.getFirstName(), account.getSureName(), account.getEmail(), account.getPhone(),
                account.getType());
    }
}
