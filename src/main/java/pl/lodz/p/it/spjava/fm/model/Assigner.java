package pl.lodz.p.it.spjava.fm.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@DiscriminatorValue("Przydzielajacy")
@NamedQuery(name = "Assigner.findLogin", query = "SELECT i FROM Assigner i where i.login = :login")
@Getter
@Setter
public class Assigner extends Account {

    @NotNull(message = "{constraint.notnull}")
    @Size(max = 16, message = "{constraint.string.length.toolong}")
    private String department;


}
