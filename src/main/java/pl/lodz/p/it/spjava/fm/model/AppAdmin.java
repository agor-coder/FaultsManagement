package pl.lodz.p.it.spjava.fm.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@NamedQuery(name = "AppAdmin.findAll", query = "SELECT a FROM AppAdmin a")
@Entity
@DiscriminatorValue("Administrator")
public class AppAdmin extends Account implements Serializable {

    @NotNull(message = "{constraint.notnull}")
    @Size(max = 4, message = "{constraint.string.length.toolong}")
    @Column(length = 4, nullable = true)
    private String alarmPhone;

    public String getAlarmPhone() {
        return alarmPhone;
    }

    public void setAlarmPhone(String alarmPhone) {
        this.alarmPhone = alarmPhone;
    }

}
