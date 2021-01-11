package pl.lodz.p.it.spjava.fm.ejb.enpoints;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.EJBTransactionRolledbackException;
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
import pl.lodz.p.it.spjava.fm.model.Account;
import pl.lodz.p.it.spjava.fm.model.AppAdmin;
import pl.lodz.p.it.spjava.fm.model.Assigner;
import pl.lodz.p.it.spjava.fm.model.Notifier;
import pl.lodz.p.it.spjava.fm.model.Specialist;
import pl.lodz.p.it.spjava.fm.security.HashGenerator;
import pl.lodz.p.it.spjava.fm.utils.DTOConverter;

@Stateful
@Interceptors(LoggingInterceptor.class)
@TransactionAttribute(TransactionAttributeType.NEVER)
public class AccountEndpoint extends AbstractEndpoint implements SessionSynchronization {

    @EJB
    private AccountManager accountManager;
    @Resource(name = "txRetryLimit")
    private int txRetryLimit;

    private Account endpointAccount;

    @Inject
    private HashGenerator hashGenerator;

    public void setEndpointAccountFromDTOToEdit(AccountDTO accDTO) throws AppBaseException {
        endpointAccount = accountManager.find(accDTO.getId());
        System.out.println(endpointAccount + "od enpointa");
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
        setEndpointAccountFromDTOToEdit(accountDTO);
        accountManager.remove(endpointAccount);
    }

    public void saveAdminAfterEdit(AccountDTO adminDTO) throws AppBaseException {
        writeEditableDataFromDTOToEntity(adminDTO, endpointAccount);
        ((AppAdmin) endpointAccount).setAlarmPhone(((AppAdminDTO) adminDTO).getAlarmPhone());
        accountManager.editAccount(endpointAccount);
    }

    public void saveSpecialistAfterEdit(AccountDTO specialistDTO) throws AppBaseException {
        writeEditableDataFromDTOToEntity(specialistDTO, endpointAccount);
        ((Specialist) endpointAccount).setDepartment(((SpecialistDTO) specialistDTO).getDepartment());
        accountManager.editAccount(endpointAccount);
    }

    public void saveAssignerAfterEdit(AccountDTO assignerDTO) throws AppBaseException {
        writeEditableDataFromDTOToEntity(assignerDTO, endpointAccount);
        ((Assigner) endpointAccount).setDepartment(((AssignerDTO) assignerDTO).getDepartment());
        accountManager.editAccount(endpointAccount);
    }

    public void saveNotifierAfterEdit(AccountDTO notifierDTO) throws AppBaseException {
        writeEditableDataFromDTOToEntity(notifierDTO, endpointAccount);
        ((Notifier) endpointAccount).setEmplacement(((NotifierDTO) notifierDTO).getEmplacement());
        accountManager.editAccount(endpointAccount);
    }

    private  void writeEditableDataFromDTOToEntity(AccountDTO accountDTO, Account account) {
        account.setFirstName(accountDTO.getFirstName());
        account.setSureName(accountDTO.getSureName());
        account.setEmail(accountDTO.getEmail());
        account.setPhone(accountDTO.getPhone());
        account.setActive(accountDTO.isActive());
        account.setConfirmed(accountDTO.isConfirmed());
    }

    public void changePassword(AccountDTO editAccountDTO) throws AppBaseException {
        endpointAccount.setPassword(editAccountDTO.getPassword());
        //endpointAccount.setPassword(hashGenerator.generateHash(editAccountDTO.getPassword()));
        accountManager.editAccount(endpointAccount);

    }

    public void addAdmin(AppAdminDTO adminDTO) throws AppBaseException {
        AppAdmin adm = new AppAdmin();
        writeDataFromDTOToNewEntity(adminDTO, adm);
        adm.setAlarmPhone(adminDTO.getAlarmPhone());
        boolean rollbackTX;
        int retryTXCounter = 1;
        Throwable cause = null;
        do {
            try {
                accountManager.createAccount(adm);
                rollbackTX = accountManager.isLastTransactionRollback();
            } catch (AppBaseException | EJBTransactionRolledbackException ex) {
                Logger.getGlobal().log(Level.SEVERE, "Próba " + retryTXCounter
                        + " wykonania metody biznesowej zakończona wyjątkiem klasy:"
                        + ex.getClass().getName());
                rollbackTX = true;
                retryTXCounter++;
                cause = ex.getCause();
            }
        } while (rollbackTX && retryTXCounter <= txRetryLimit);

        if (rollbackTX && retryTXCounter > txRetryLimit) {
            throw AccountException.createWithDbCheckConstraintKey(cause);
        }
    }

    public void addSpecialist(SpecialistDTO specialistDTO) throws AppBaseException {
        Specialist specialist = new Specialist();
        writeDataFromDTOToNewEntity(specialistDTO, specialist);
        specialist.setDepartment(specialistDTO.getDepartment());
        boolean rollbackTX;
        int retryTXCounter = 1;
        Throwable cause = null;
        do {
            try {
                accountManager.createAccount(specialist);
                rollbackTX = accountManager.isLastTransactionRollback();
            } catch (AppBaseException | EJBTransactionRolledbackException ex) {
                Logger.getGlobal().log(Level.SEVERE, "Próba " + retryTXCounter
                        + " wykonania metody biznesowej zakończona wyjątkiem klasy:"
                        + ex.getClass().getName());
                rollbackTX = true;
                retryTXCounter++;
                cause = ex.getCause();
            }
        } while (rollbackTX && retryTXCounter <= txRetryLimit);

        if (rollbackTX && retryTXCounter > txRetryLimit) {
            throw AccountException.createWithDbCheckConstraintKey(cause);
        }
    }

    public void addAssigner(AssignerDTO assignerDTO) throws AppBaseException {
        Assigner assigner = new Assigner();
        writeDataFromDTOToNewEntity(assignerDTO, assigner);
        assigner.setDepartment(assignerDTO.getDepartment());
        boolean rollbackTX;
        int retryTXCounter = 1;
        Throwable cause = null;
        do {
            try {
                accountManager.createAccount(assigner);
                rollbackTX = accountManager.isLastTransactionRollback();
            } catch (AppBaseException | EJBTransactionRolledbackException ex) {
                Logger.getGlobal().log(Level.SEVERE, "Próba " + retryTXCounter
                        + " wykonania metody biznesowej zakończona wyjątkiem klasy:"
                        + ex.getClass().getName());
                rollbackTX = true;
                retryTXCounter++;
                cause = ex.getCause();
            }
        } while (rollbackTX && retryTXCounter <= txRetryLimit);

        if (rollbackTX && retryTXCounter > txRetryLimit) {
            throw AccountException.createWithDbCheckConstraintKey(cause);
        }
    }

    public void addNotifier(NotifierDTO notifierDTO) throws AppBaseException {
        Notifier notifier = new Notifier();
        writeDataFromDTOToNewEntity(notifierDTO, notifier);
        notifier.setEmplacement(notifierDTO.getEmplacement());
        boolean rollbackTX;
        int retryTXCounter = 1;
        Throwable cause = null;
        do {
            try {
                accountManager.createAccount(notifier);
                rollbackTX = accountManager.isLastTransactionRollback();
            } catch (AppBaseException | EJBTransactionRolledbackException ex) {
                Logger.getGlobal().log(Level.SEVERE, "Próba " + retryTXCounter
                        + " wykonania metody biznesowej zakończona wyjątkiem klasy:"
                        + ex.getClass().getName());
                rollbackTX = true;
                retryTXCounter++;
                cause = ex.getCause();
            }
        } while (rollbackTX && retryTXCounter <= txRetryLimit);

        if (rollbackTX && retryTXCounter > txRetryLimit) {
            throw AccountException.createWithDbCheckConstraintKey(cause);
        }
    }

    private void writeDataFromDTOToNewEntity(AccountDTO accountDTO, Account account) {
        account.setLogin(accountDTO.getLogin());
        writeEditableDataFromDTOToEntity(accountDTO, account);
        account.setPassword(accountDTO.getPassword());
        //account.setPassword(hashGenerator.generateHash(accountDTO.getPassword()));

    }

}
