package pl.lodz.p.it.spjava.fm.security;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.security.enterprise.credential.Credential;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import javax.security.enterprise.identitystore.IdentityStore;
import pl.lodz.p.it.spjava.fm.model.Account;

@ApplicationScoped
public class JpaIdentityStore implements IdentityStore {

    @Inject
    private SecurityEndpoint securityEndpoint;

    @Override
    public Set<String> getCallerGroups(CredentialValidationResult validationResult) {
        return IdentityStore.super.getCallerGroups(validationResult);
    }

    @Override
    public CredentialValidationResult validate(Credential credential) {
        if (credential instanceof UsernamePasswordCredential) {
            UsernamePasswordCredential usernamePasswordCredential = (UsernamePasswordCredential) credential;
            Account account = securityEndpoint.findAccountWithAuthConditions(usernamePasswordCredential.getCaller(), usernamePasswordCredential.getPasswordAsString());
            return (null != account ? new CredentialValidationResult(account.getLogin(), new HashSet<>(Arrays.asList(account.getType()))) : CredentialValidationResult.INVALID_RESULT);
        }
        return CredentialValidationResult.NOT_VALIDATED_RESULT;
    }

}
