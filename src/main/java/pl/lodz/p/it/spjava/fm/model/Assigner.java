package pl.lodz.p.it.spjava.fm.model;

import java.io.Serializable;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.validation.constraints.NotNull;

@Entity
@DiscriminatorValue("Przydzielający")
@NamedQuery(name = "Assigner.findLogin", query = "SELECT i FROM Assigner i where i.login = :login")
public class Assigner extends Account implements Serializable {

    @NotNull(message = "{constraint.notnull}")
    private String department;

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

}
