package pl.lodz.p.it.spjava.fm.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "type")
public abstract class Account extends AbstractEntity implements Serializable {

    public Account() {
    }

    @Id
    @Column(updatable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    protected Long id;

    @NotNull(message = "{constraint.notnull}")
    @Size(min = 5, max = 32, message = "{constraint.string.length.notinrange}")
    @Pattern(regexp = "^[_a-zA-Z0-9-]*$", message = "{constraint.string.length.tooshort}")
    @Column(length = 32, nullable = false, unique = true, updatable = false)
    @Getter
    @Setter
    protected String login;

    @NotNull(message = "{constraint.notnull}")
    @Size(min = 64, max = 64, message = "{constraint.string.length.notinrange}")
    @Column(length = 64, nullable = false)
    @Getter
    @Setter
    protected String password;

    @NotNull(message = "{constraint.notnull}")
    @Column(nullable = false)
    @Getter
    @Setter
    protected boolean active;

    @NotNull(message = "{constraint.notnull}")
    @Column(nullable = false)
    @Getter
    @Setter
    protected boolean confirmed;

    @NotNull(message = "{constraint.notnull}")
    @Size(min = 3, max = 32, message = "{constraint.string.length.notinrange}")
    @Column(length = 32, nullable = false)
    @Getter
    @Setter
    private String firstName;

    @NotNull(message = "{constraint.notnull}")
    @Size(min = 3, max = 32, message = "{constraint.string.length.notinrange}")
    @Column(length = 32, nullable = false)
    @Getter
    @Setter
    private String sureName;

    @NotNull(message = "{constraint.notnull}")
    @Size(min = 6, max = 64, message = "{constraint.string.length.notinrange}")
    @Pattern(regexp = "^[_a-z0-9-]+(\\.[_a-z0-9-]+)*@[a-z0-9-]+(\\.[a-z0-9-]+)*(\\.[a-z]{2,4})$", message = "{constraint.string.incorrectemail}")
    @Column(length = 64, nullable = false, unique = true)
    @Getter
    @Setter
    private String email;

    @Size(max = 9, message = "{constraint.string.length.toolong}")
    @Column(length = 9, nullable = true)
    @Getter
    @Setter
    private String phone;

    @Column(name = "type", updatable = false)
    @Getter
    private String type;


    @Override
    public Long getId() {
        return id;
    }

    @Override
    protected Object getBusinessKey() {
        return login;
    }
}
