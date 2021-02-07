package pl.lodz.p.it.spjava.fm.model;

import java.io.Serializable;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.validation.constraints.NotNull;

@Entity
@NamedQuery(name = "Specialist.findLogin", query = "SELECT i FROM Specialist i where i.login = :login")
@DiscriminatorValue("Specjalista")
public class Specialist extends Account implements Serializable {

    @NotNull(message = "{constraint.notnull}")
    private String department;

    @NotNull(message = "{constraint.notnull}")
    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    @Override
    public String toString() {
        return "Specialist{" + getSureName() + '}';
    }
    
    

}
