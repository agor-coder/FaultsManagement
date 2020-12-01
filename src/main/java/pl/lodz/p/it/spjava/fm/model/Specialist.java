package pl.lodz.p.it.spjava.fm.model;

import java.io.Serializable;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

@Entity
@DiscriminatorValue("SPECIALIST")
public class Specialist extends Account implements Serializable {

    private static final int MAX_FAULTS_IN_PROGRESS = 3;

    @NotNull(message = "{constraint.notnull}")
    private String department;

    @NotNull(message = "{constraint.notnull}")
    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

}
