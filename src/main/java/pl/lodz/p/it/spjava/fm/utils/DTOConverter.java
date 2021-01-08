package pl.lodz.p.it.spjava.fm.utils;

import pl.lodz.p.it.spjava.fm.dto.AccountDTO;
import pl.lodz.p.it.spjava.fm.dto.AssignerDTO;
import pl.lodz.p.it.spjava.fm.dto.NotifierDTO;
import pl.lodz.p.it.spjava.fm.dto.SpecialistDTO;
import pl.lodz.p.it.spjava.fm.model.Account;
import pl.lodz.p.it.spjava.fm.model.Assigner;
import pl.lodz.p.it.spjava.fm.model.Notifier;
import pl.lodz.p.it.spjava.fm.model.Specialist;

public class DTOConverter {

    public static SpecialistDTO makeSpecialistDTOFromEntity(Specialist specialist) {
        return null == specialist ? null : new SpecialistDTO(specialist.getId(), specialist.getLogin(), specialist.isActive(),
                specialist.isConfirmed(),specialist.getFirstName(), specialist.getSureName(), specialist.getEmail(), 
                specialist.getPhone(),specialist.getType(), specialist.getDepartment());
    }

    private static NotifierDTO makeNotifierDTOFromEntity(Notifier notifier) {
        return null == notifier ? null : new NotifierDTO(notifier.getId(), notifier.getLogin(), notifier.isActive(),
                notifier.isConfirmed(), notifier.getFirstName(), notifier.getSureName(), notifier.getEmail(), notifier.getPhone(),
                notifier.getType(), notifier.getEmplacement());
    }

    private static AssignerDTO makeAssignerDTOFromEntity(Assigner assigner) {
        return null == assigner ? null : new AssignerDTO(assigner.getId(), assigner.getLogin(), assigner.isActive(),
                assigner.isConfirmed(),assigner.getFirstName(), assigner.getSureName(), assigner.getEmail(), assigner.getPhone(),
                assigner.getType(), assigner.getDepartment());
    }

    public static AccountDTO makeAccountDTOFromEntity(Account account) {
        return null == account ? null : new AccountDTO(account.getId(), account.getLogin(), account.isActive(),
                account.isConfirmed(),account.getFirstName(), account.getSureName(), account.getEmail(), account.getPhone(),
                account.getType());
    }

    public static AccountDTO tworzKontoDTOzEncji(Account account) {
        if (account instanceof Specialist) {
            return makeSpecialistDTOFromEntity((Specialist) account);
        }
        if (account instanceof Notifier) {
            return makeNotifierDTOFromEntity((Notifier) account);
        }
        if (account instanceof Assigner) {
            return makeAssignerDTOFromEntity((Assigner) account);
        }
        return null;
    }

}
