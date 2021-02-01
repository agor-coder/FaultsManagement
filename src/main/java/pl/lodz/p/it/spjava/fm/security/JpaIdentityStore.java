package pl.lodz.p.it.spjava.fm.security;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.security.enterprise.credential.Credential;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import javax.security.enterprise.identitystore.IdentityStore;
import pl.lodz.p.it.spjava.fm.model.Account;


@ApplicationScoped
public class JpaIdentityStore implements IdentityStore {

    private static final Logger LOG = Logger.getLogger(JpaIdentityStore.class.getName());

    @Inject
    private SecurityEndpoint securityEndpoint;

    @Override
    public Set<String> getCallerGroups(CredentialValidationResult validationResult) {
        // w tym miejscu moglibyśmy potencjalnie manipulować zestawem grup odczytanym przez metodę validate()
        return IdentityStore.super.getCallerGroups(validationResult);
    }

    @Override
    public CredentialValidationResult validate(Credential credential) {
        if (credential instanceof UsernamePasswordCredential) {
            UsernamePasswordCredential usernamePasswordCredential = (UsernamePasswordCredential) credential;
            // Metoda fasady wywołana za pośrednictwem endpointa sprawdza identyczność skrótu hasła oraz stan konta (potwierdzone, aktywne). Niczego nie potrzebujemy tu już robić.
            Account account = securityEndpoint.znajdzKontoSpelniajaceWarunkiUwierzytelnienia(usernamePasswordCredential.getCaller(), usernamePasswordCredential.getPasswordAsString());
            return (null != account ? new CredentialValidationResult(account.getLogin(), new HashSet<>(Arrays.asList(account.getType()))) : CredentialValidationResult.INVALID_RESULT);
        }
        return CredentialValidationResult.NOT_VALIDATED_RESULT;

    }

}
