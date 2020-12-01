package pl.lodz.p.it.spjava.fm.model;

import java.io.Serializable;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

@Entity
@DiscriminatorValue("NOTIFIER")
public class Notifier extends Account implements Serializable {

    @NotNull(message = "{constraint.notnull}")
    private String emplacement;

    public String getEmplacement() {
        return emplacement;
    }

    public void setEmplacement(String emplacement) {
        this.emplacement = emplacement;
    }


}
