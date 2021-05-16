package pl.lodz.p.it.spjava.fm.model;

import java.io.Serializable;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Entity
@DiscriminatorValue("Zglaszajacy")
@NamedQuery(name = "Notifier.findLogin", query = "SELECT i FROM Notifier i where i.login = :login")
@Getter
@Setter
public class Notifier extends Account implements Serializable {

    @NotNull(message = "{constraint.notnull}")
    @Size(max = 32, message = "{constraint.string.length.toolong}")
    private String emplacement;
}
