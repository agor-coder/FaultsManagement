package pl.lodz.p.it.spjava.fm.web.account;

import pl.lodz.p.it.spjava.fm.web.specialist.*;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import pl.lodz.p.it.spjava.fm.dto.AccountDTO;
import pl.lodz.p.it.spjava.fm.ejb.enpoints.AccountEndpoint;
import pl.lodz.p.it.spjava.fm.exception.AppBaseException;
import pl.lodz.p.it.spjava.fm.web.utils.ContextUtils;

@Named
@ConversationScoped
public class EditAccountController implements Serializable {

    @Inject
    private Conversation conversation;

    @EJB
    private AccountEndpoint accountEndpoint;

    private AccountDTO editAccountDTO ;

    public AccountDTO getEditAccountDTO() {
        return editAccountDTO;
    }

  

    public void setEditAccountDTO(AccountDTO accountDTO) {
        this.editAccountDTO = accountDTO;
    }

    public void getAccountEntityToChange(AccountDTO accountDTO) {
        try {
            accountEndpoint.setEndpointAccountFromDTO(accountDTO);
        } catch (AppBaseException abe) {
            Logger.getLogger(NewSpecialistController.class.getName())
                    .log(Level.SEVERE, "Zgłoszenie w metodzie akcji edytujSpecjalistę wyjatku typu: ", abe);
            ContextUtils.emitInternationalizedMessage(null, abe.getMessage());
        }
    }

//    public String saveEditSpecialistDTO() {
//        if (null == editSpecialistDTO) {
//            throw new IllegalArgumentException("Proba zatwierdzenia danych bez wypelnienia formularza");
//        }
//        try {
//            specialistEndpoint.saveSpecialistAfterEdit(editSpecialistDTO);
//            return cancelOrEdit();
//        } catch (AppBaseException abe) {
//            Logger.getLogger(NewSpecialistController.class.getName())
//                    .log(Level.SEVERE, "Zgłoszenie w metodzie akcji edytujSpecjalistę wyjatku typu: ", abe);
//            ContextUtils.emitInternationalizedMessage(null, abe.getMessage());
//            return null;
//        }
//    }

    public String cancelOrEdit() {
        conversation.end();
        return "accountList";
    }
}
