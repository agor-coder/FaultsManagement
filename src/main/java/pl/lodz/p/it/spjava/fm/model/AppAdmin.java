package pl.lodz.p.it.spjava.fm.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Entity
@NamedQuery(name = "AppAdmin.findAll", query = "SELECT a FROM AppAdmin a")
@DiscriminatorValue("Administrator")
@Getter
@Setter
public class AppAdmin extends Account {

    @NotNull(message = "{constraint.notnull}")
    @Size(max = 4, message = "{constraint.string.length.toolong}")
    @Column(length = 4, nullable = true)
    private String alarmPhone;

   

}
