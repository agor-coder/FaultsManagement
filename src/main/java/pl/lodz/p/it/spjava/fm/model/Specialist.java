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
@NamedQuery(name = "Specialist.findLogin", query = "SELECT i FROM Specialist i where i.login = :login")
@DiscriminatorValue("Specjalista")
@Getter
@Setter
public class Specialist extends Account implements Serializable {

    @NotNull(message = "{constraint.notnull}")
    @Size(max = 16, message = "{constraint.string.length.toolong}")
    private String department;


    @Override
    public String toString() {
        return "Specialist{" + getSureName() + '}';
    }
    
    

}
