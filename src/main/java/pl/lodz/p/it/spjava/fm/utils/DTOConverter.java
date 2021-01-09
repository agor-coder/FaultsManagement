package pl.lodz.p.it.spjava.fm.utils;

import pl.lodz.p.it.spjava.fm.dto.AccountDTO;
import pl.lodz.p.it.spjava.fm.dto.AssignerDTO;
import pl.lodz.p.it.spjava.fm.dto.FaultDTO;
import pl.lodz.p.it.spjava.fm.dto.NotifierDTO;
import pl.lodz.p.it.spjava.fm.dto.SpecialistDTO;
import pl.lodz.p.it.spjava.fm.model.Account;
import pl.lodz.p.it.spjava.fm.model.Assigner;
import pl.lodz.p.it.spjava.fm.model.Fault;
import pl.lodz.p.it.spjava.fm.model.Notifier;
import pl.lodz.p.it.spjava.fm.model.Specialist;

public class DTOConverter {

    public static SpecialistDTO createSpecialistDTOFromEntity(Specialist specialist) {
        return null == specialist ? null : new SpecialistDTO(specialist.getId(), specialist.getLogin(), specialist.isActive(),
                specialist.isConfirmed(), specialist.getFirstName(), specialist.getSureName(), specialist.getEmail(),
                specialist.getPhone(), specialist.getType(), specialist.getDepartment());
    }

    private static NotifierDTO createNotifierDTOFromEntity(Notifier notifier) {
        return null == notifier ? null : new NotifierDTO(notifier.getId(), notifier.getLogin(), notifier.isActive(),
                notifier.isConfirmed(), notifier.getFirstName(), notifier.getSureName(), notifier.getEmail(), notifier.getPhone(),
                notifier.getType(), notifier.getEmplacement());
    }

    private static AssignerDTO createAssignerDTOFromEntity(Assigner assigner) {
        return null == assigner ? null : new AssignerDTO(assigner.getId(), assigner.getLogin(), assigner.isActive(),
                assigner.isConfirmed(), assigner.getFirstName(), assigner.getSureName(), assigner.getEmail(), assigner.getPhone(),
                assigner.getType(), assigner.getDepartment());
    }

    public static AccountDTO createAccountDTOFromEntity(Account account) {
        return null == account ? null : new AccountDTO(account.getId(), account.getLogin(), account.isActive(),
                account.isConfirmed(), account.getFirstName(), account.getSureName(), account.getEmail(), account.getPhone(),
                account.getType());
    }

    public static FaultDTO createFaultDTOFromEntity(Fault fault) {
        return null == fault ? null : new FaultDTO(fault.getId(), fault.getFaultDescribe(),
                createSpecialistDTOFromEntity(fault.getSpecialist()),
                createNotifierDTOFromEntity(fault.getWhoNotified()),
                createAssignerDTOFromEntity(fault.getWhoAssigned()));
    }

    public static AccountDTO makeAccountDTOfromUserEntity(Account account) {
        if (account instanceof Specialist) {
            return createSpecialistDTOFromEntity((Specialist) account);
        }
        if (account instanceof Notifier) {
            return createNotifierDTOFromEntity((Notifier) account);
        }
        if (account instanceof Assigner) {
            return createAssignerDTOFromEntity((Assigner) account);
        }
        return null;
    }

}
