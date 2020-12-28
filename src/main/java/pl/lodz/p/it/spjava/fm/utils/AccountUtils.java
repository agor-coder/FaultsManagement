
package pl.lodz.p.it.spjava.fm.utils;

import pl.lodz.p.it.spjava.fm.dto.AccountDTO;
import pl.lodz.p.it.spjava.fm.dto.FaultAssignerDTO;
import pl.lodz.p.it.spjava.fm.dto.NotifierDTO;
import pl.lodz.p.it.spjava.fm.dto.SpecialistDTO;




public class AccountUtils {
    public static boolean isSpecialist(AccountDTO account) {
        return (account instanceof SpecialistDTO);
    }

    public static boolean isNotifier(AccountDTO account) {
        return (account instanceof NotifierDTO);
    }

    public static boolean isFaultAssigner(AccountDTO account) {
        return (account instanceof FaultAssignerDTO);
    }

  
    
}