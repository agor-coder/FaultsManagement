package pl.lodz.p.it.spjava.fm.ejb.enpoints;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.SessionSynchronization;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import pl.lodz.p.it.spjava.fm.dto.AccountDTO;
import pl.lodz.p.it.spjava.fm.ejb.interceptor.LoggingInterceptor;
import pl.lodz.p.it.spjava.fm.ejb.managers.AccountManager;
import pl.lodz.p.it.spjava.fm.exception.AccountException;
import pl.lodz.p.it.spjava.fm.exception.AppBaseException;
import pl.lodz.p.it.spjava.fm.model.Account;
import pl.lodz.p.it.spjava.fm.utils.DTOConverter;

@Stateful
@Interceptors(LoggingInterceptor.class)
@TransactionAttribute(TransactionAttributeType.NEVER)
public class AccountEndpoint extends AbstractEndpoint implements SessionSynchronization {

    @EJB
    private AccountManager accountManager;

    private Account endpointAccount;

    public void setEndpointAccountFromDTOToEdit(AccountDTO accDTO) throws AppBaseException {
            endpointAccount = accountManager.find(accDTO.getId());
            System.out.println(endpointAccount + "od enpointa");
        if (null == endpointAccount) {
            throw AccountException.createAccountExceptionWithAccountNotFound();
        }
    }

    public void setEndpointAccountFromDTO(AccountDTO accDTO) throws AppBaseException {
        endpointAccount = accountManager.find(accDTO.getId());
        if (null == endpointAccount) {
            throw AccountException.createAccountExceptionWithAccountNotFound();
        }
    }

    public AccountDTO getAccountToEdit(AccountDTO accountDTO) {
        Account tmp = accountManager.find(accountDTO.getId());
        return DTOConverter.tworzKontoDTOzEncji(tmp);
    }

    public List<AccountDTO> getAllAccountsAndMakeDTOList() {
        List<Account> listAccount = accountManager.findAll();
        List<AccountDTO> listAccountDTO = new ArrayList<>();
        listAccount.stream().map(account -> DTOConverter.makeAccountDTOFromEntity(account))
                .sorted(Comparator.comparing(AccountDTO::getLogin))
                .forEachOrdered(accountDTO -> {
                    listAccountDTO.add(accountDTO);
                });
        return listAccountDTO;
    }

    public void activateAccount(AccountDTO accountDTO) throws AppBaseException {
        setEndpointAccountFromDTO(accountDTO);
        accountManager.markActive(endpointAccount, true);
    }

    public void deactivateAccount(AccountDTO accountDTO) throws AppBaseException {
        setEndpointAccountFromDTO(accountDTO);
        accountManager.markActive(endpointAccount, false);
    }

    public void removeAccount(AccountDTO accountDTO) throws AppBaseException {
        setEndpointAccountFromDTO(accountDTO);
        accountManager.remove(endpointAccount);
    }

    public void saveAccountAfterEdit(AccountDTO editAccountDTO) throws AppBaseException {
        writeEditableDataFromDTOToEntity(editAccountDTO, endpointAccount);
        accountManager.editAccount(endpointAccount);
    }

    private void writeEditableDataFromDTOToEntity(AccountDTO accountDTO, Account account) {
        account.setFirstName(accountDTO.getFirstName());
        account.setSureName(accountDTO.getSureName());
        account.setEmail(accountDTO.getEmail());
//        account.setDepartment(SpecialistDTO.getDepartment());
        account.setPhone(accountDTO.getPhone());
        account.setActive(accountDTO.isActive());
        account.setConfirmed(accountDTO.isConfirmed());
    }

}
