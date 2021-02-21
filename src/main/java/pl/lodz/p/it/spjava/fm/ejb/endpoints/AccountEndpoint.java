package pl.lodz.p.it.spjava.fm.ejb.endpoints;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.SessionSynchronization;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import pl.lodz.p.it.spjava.fm.dto.AccountDTO;
import pl.lodz.p.it.spjava.fm.dto.AppAdminDTO;
import pl.lodz.p.it.spjava.fm.dto.AssignerDTO;
import pl.lodz.p.it.spjava.fm.dto.NotifierDTO;
import pl.lodz.p.it.spjava.fm.dto.SpecialistDTO;
import pl.lodz.p.it.spjava.fm.ejb.interceptor.LoggingInterceptor;
import pl.lodz.p.it.spjava.fm.ejb.managers.AccountManager;
import pl.lodz.p.it.spjava.fm.exception.AccountException;
import pl.lodz.p.it.spjava.fm.exception.AppBaseException;
import pl.lodz.p.it.spjava.fm.exception.AppBasePersistenceException;
import pl.lodz.p.it.spjava.fm.model.Account;
import pl.lodz.p.it.spjava.fm.model.AppAdmin;
import pl.lodz.p.it.spjava.fm.model.Assigner;
import pl.lodz.p.it.spjava.fm.model.Notifier;
import pl.lodz.p.it.spjava.fm.model.Specialist;
import pl.lodz.p.it.spjava.fm.security.HashGenerator;
import pl.lodz.p.it.spjava.fm.utils.DTOConverter;
import pl.lodz.p.it.spjava.fm.web.utils.ContextUtils;

@Stateful
@Interceptors(LoggingInterceptor.class)
@TransactionAttribute(TransactionAttributeType.NEVER)
@RolesAllowed({"AppAdmin"})
public class AccountEndpoint extends AbstractEndpoint implements SessionSynchronization {

    @EJB
    private AccountManager accountManager;
    @Resource(name = "txRetryLimit")
    private int txRetryLimit;

    private Account endpointAccount;

    @Inject
    private HashGenerator hashGenerator;

    private void setEndpointAccountFromDTOToEdit(AccountDTO accDTO) throws AppBaseException {
        endpointAccount = accountManager.find(accDTO.getId());
        if (null == endpointAccount) {
            throw AccountException.createAccountExceptionWithAccountNotFound();
        }

    }

    public AccountDTO getAccountToEdit(AccountDTO accountDTO) throws AppBaseException {
        setEndpointAccountFromDTOToEdit(accountDTO);
        return DTOConverter.makeAccountDTOfromUserEntity(endpointAccount);
    }

    public List<AccountDTO> getAllAccountsAndMakeDTOList() {
        List<Account> listAccount = accountManager.findAll();
        List<AccountDTO> listAccountDTO = new ArrayList<>();
        listAccount.stream().map(account -> DTOConverter.createAccountDTOFromEntity(account))
                .sorted(Comparator.comparing(AccountDTO::getLogin))
                .forEachOrdered(accountDTO -> {
                    listAccountDTO.add(accountDTO);
                });
        return listAccountDTO;
    }

    public void activateAccount(AccountDTO accountDTO) throws AppBaseException {
        setEndpointAccountFromDTOToEdit(accountDTO);
        accountManager.markActive(endpointAccount, true);
    }

    public void deactivateAccount(AccountDTO accountDTO) throws AppBaseException {
        setEndpointAccountFromDTOToEdit(accountDTO);
        accountManager.markActive(endpointAccount, false);
    }

    public void removeAccount(AccountDTO accountDTO) throws AppBaseException {
//        if (accountDTO.getLogin().equals(ContextUtils.getUserName())) {
//            throw AccountException.createAccountExceptionWithAccountNotRemove();
//        }
        setEndpointAccountFromDTOToEdit(accountDTO);
        accountManager.remove(endpointAccount);
    }

    public void saveAdminAfterEdit(AccountDTO adminDTO) throws AppBaseException {
        writeEditableDataFromDTOToEntity(adminDTO, endpointAccount);
        ((AppAdmin) endpointAccount).setAlarmPhone(((AppAdminDTO) adminDTO).getAlarmPhone());
        editAccountWithTXRetry();
    }

    public void saveSpecialistAfterEdit(AccountDTO specialistDTO) throws AppBaseException {
        writeEditableDataFromDTOToEntity(specialistDTO, endpointAccount);
        ((Specialist) endpointAccount).setDepartment(((SpecialistDTO) specialistDTO).getDepartment());
       editAccountWithTXRetry();
    }

    public void saveAssignerAfterEdit(AccountDTO assignerDTO) throws AppBaseException {
        writeEditableDataFromDTOToEntity(assignerDTO, endpointAccount);
        ((Assigner) endpointAccount).setDepartment(((AssignerDTO) assignerDTO).getDepartment());
        editAccountWithTXRetry();
    }

    public void saveNotifierAfterEdit(AccountDTO notifierDTO) throws AppBaseException {
        writeEditableDataFromDTOToEntity(notifierDTO, endpointAccount);
        ((Notifier) endpointAccount).setEmplacement(((NotifierDTO) notifierDTO).getEmplacement());
        editAccountWithTXRetry();
    }

    private void editAccountWithTXRetry() throws  AppBaseException {
        boolean rollbackTX;
        int retryTXCounter = 1;
        do {
            try {
                accountManager.editAccount(endpointAccount);
                rollbackTX = accountManager.isLastTransactionRollback();
            } catch (AppBasePersistenceException ex) {
                Logger.getGlobal().log(Level.SEVERE, "Próba " + retryTXCounter
                        + " wykonania metody biznesowej zakończona wyjątkiem klasy:"
                        + ex.getClass().getName());
                rollbackTX = true;
                retryTXCounter++;
            }
        } while (rollbackTX && retryTXCounter <= txRetryLimit);

        if (rollbackTX && retryTXCounter > txRetryLimit) {
            throw AccountException.createAccountExceptionWithTxRetryRollback();
        }
    }

    private void writeEditableDataFromDTOToEntity(AccountDTO accountDTO, Account account) {
        account.setFirstName(accountDTO.getFirstName());
        account.setSureName(accountDTO.getSureName());
        account.setEmail(accountDTO.getEmail());
        account.setPhone(accountDTO.getPhone());
        account.setActive(accountDTO.isActive());
        account.setConfirmed(accountDTO.isConfirmed());
    }

    public void changePassword(AccountDTO editAccountDTO) throws AppBaseException {
        endpointAccount.setPassword(hashGenerator.generateHash(editAccountDTO.getPassword()));
        accountManager.editAccount(endpointAccount);

    }

    @RolesAllowed({"AppAdmin", "Notifier", "Assigner", "Specialist"})
    public void changeMyPasword(String oldPass, String newPass) throws AppBaseException {
        endpointAccount = getMyAccount();
        if (!endpointAccount.getPassword().equals(hashGenerator.generateHash(oldPass))) {
            throw AccountException.createWithPreviousGivenPasswordDoesNotMatch();
        }
        if (endpointAccount.getPassword().equals(hashGenerator.generateHash(newPass))) {
            throw AccountException.createWithTheSamePasswordDoesNotMatch();
        }
        String newHash = hashGenerator.generateHash(newPass);
        accountManager.changeMyPasword(endpointAccount, newHash);

    }

    public void addAdmin(AppAdminDTO adminDTO) throws AppBaseException {
        AppAdmin adm = new AppAdmin();
        writeAccountDataFromDTOToNewEntity(adminDTO, adm);
        adm.setAlarmPhone(adminDTO.getAlarmPhone());
        boolean rollbackTX;
        int retryTXCounter = 1;
        do {
            try {
                accountManager.createAccount(adm);
                rollbackTX = accountManager.isLastTransactionRollback();
            } catch (AppBasePersistenceException ex) {
                Logger.getGlobal().log(Level.SEVERE, "Próba " + retryTXCounter
                        + " wykonania metody biznesowej zakończona wyjątkiem klasy:"
                        + ex.getClass().getName());
                rollbackTX = true;
                retryTXCounter++;
            }
        } while (rollbackTX && retryTXCounter <= txRetryLimit);

        if (rollbackTX && retryTXCounter > txRetryLimit) {
            throw AccountException.createAccountExceptionWithTxRetryRollback();
        }
    }

    public void addSpecialist(SpecialistDTO specialistDTO) throws AppBaseException {
        Specialist specialist = new Specialist();
        writeAccountDataFromDTOToNewEntity(specialistDTO, specialist);
        specialist.setDepartment(specialistDTO.getDepartment());
        boolean rollbackTX;
        int retryTXCounter = 1;
        do {
            try {
                accountManager.createAccount(specialist);
                rollbackTX = accountManager.isLastTransactionRollback();
            } catch (AppBasePersistenceException ex) {
                Logger.getGlobal().log(Level.SEVERE, "Próba " + retryTXCounter
                        + " wykonania metody biznesowej zakończona wyjątkiem klasy:"
                        + ex.getClass().getName());
                rollbackTX = true;
                retryTXCounter++;
            }
        } while (rollbackTX && retryTXCounter <= txRetryLimit);

        if (rollbackTX && retryTXCounter > txRetryLimit) {
            throw AccountException.createAccountExceptionWithTxRetryRollback();
        }
    }

    public void addAssigner(AssignerDTO assignerDTO) throws AppBaseException {
        Assigner assigner = new Assigner();
        writeAccountDataFromDTOToNewEntity(assignerDTO, assigner);
        assigner.setDepartment(assignerDTO.getDepartment());
        boolean rollbackTX;
        int retryTXCounter = 1;
        do {
            try {
                accountManager.createAccount(assigner);
                rollbackTX = accountManager.isLastTransactionRollback();
            } catch (AppBasePersistenceException ex) {
                Logger.getGlobal().log(Level.SEVERE, "Próba " + retryTXCounter
                        + " wykonania metody biznesowej zakończona wyjątkiem klasy:"
                        + ex.getClass().getName());
                rollbackTX = true;
                retryTXCounter++;
            }
        } while (rollbackTX && retryTXCounter <= txRetryLimit);

        if (rollbackTX && retryTXCounter > txRetryLimit) {
            throw AccountException.createAccountExceptionWithTxRetryRollback();
        }
    }

    public void addNotifier(NotifierDTO notifierDTO) throws AppBaseException {
        Notifier notifier = new Notifier();
        writeAccountDataFromDTOToNewEntity(notifierDTO, notifier);
        notifier.setEmplacement(notifierDTO.getEmplacement());
        createNotifierWithTXRetry(notifier);
    }

    @PermitAll
    public void regNotifier(NotifierDTO notifierDTO) throws AppBaseException {
        Notifier notifier = new Notifier();
        writeAccountDataFromDTOToNewEntity(notifierDTO, notifier);
        notifier.setConfirmed(false);
        notifier.setActive(false);
        notifier.setEmplacement(notifierDTO.getEmplacement());
        createNotifierWithTXRetry(notifier);
    }

    private void createNotifierWithTXRetry(Notifier notifier) throws AccountException, AppBaseException {
        boolean rollbackTX;
        int retryTXCounter = 1;
        do {
            try {
                accountManager.createAccount(notifier);
                rollbackTX = accountManager.isLastTransactionRollback();
            } catch (AppBasePersistenceException ex) {
                Logger.getGlobal().log(Level.SEVERE, "Próba " + retryTXCounter
                        + " wykonania metody biznesowej zakończona wyjątkiem klasy:"
                        + ex.getClass().getName());
                rollbackTX = true;
                retryTXCounter++;
            }
        } while (rollbackTX && retryTXCounter <= txRetryLimit);

        if (rollbackTX && retryTXCounter > txRetryLimit) {
            throw AccountException.createAccountExceptionWithTxRetryRollback();
        }
    }

    private void writeAccountDataFromDTOToNewEntity(AccountDTO accountDTO, Account account) {
        account.setLogin(accountDTO.getLogin());
        writeEditableDataFromDTOToEntity(accountDTO, account);
        account.setPassword(hashGenerator.generateHash(accountDTO.getPassword()));
    }

    @RolesAllowed({"AppAdmin", "Notifier", "Assigner", "Specialist"})
    public Account getMyAccount() throws AppBaseException {
        return accountManager.findLogin(ContextUtils.getUserName());
    }

    @RolesAllowed({"AppAdmin", "Notifier", "Assigner", "Specialist"})
    public AccountDTO getMyAccountDTO() throws AppBaseException {
        return DTOConverter.makeAccountDTOfromUserEntity(getMyAccount());
    }

}
