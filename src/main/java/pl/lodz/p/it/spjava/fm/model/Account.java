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
import javax.persistence.SecondaryTable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@SecondaryTable(name = "PERSON")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "typ")
public abstract class Account extends AbstractEntity implements Serializable {

    public Account() {
    }

    @Id
    @Column(updatable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    protected Long id;

    @NotNull(message = "{constraint.notnull}")
    //@Size(min=3,max=32,message="{constraint.string.length.notinrange}")
    //@Pattern(regexp="^[_a-zA-Z0-9-]*$",message="{constraint.string.incorrectchar}")
    @Column(length = 32, nullable = false, unique = true, updatable = false)
    protected String login;

    @NotNull(message = "{constraint.notnull}")
    @Size(min = 1, message = "{constraint.string.length.tooshort}")
    @Column(length = 50, nullable = false)
    protected String password;

    @NotNull(message = "{constraint.notnull}")
    @Column(nullable = false)
    protected boolean active = true;

    @NotNull(message = "{constraint.notnull}")
    @Size(min = 3, max = 32, message = "{constraint.string.length.notinrange}")
    @Column(table = "PERSON", length = 32, nullable = false)
    private String firstName;

    @NotNull(message = "{constraint.notnull}")
    @Size(min = 3, max = 32, message = "{constraint.string.length.notinrange}")
    @Column(table = "PERSON", length = 32, nullable = false)
    private String sureName;

    @NotNull(message = "{constraint.notnull}")
    @Size(min = 6, max = 64, message = "{constraint.string.length.notinrange}")
    //@Pattern(regexp="^[_a-z0-9-]+(\\.[_a-z0-9-]+)*@[a-z0-9-]+(\\.[a-z0-9-]+)*(\\.[a-z]{2,4})$",message="{constraint.string.incorrectemail}")
    @Column(table = "PERSON", length = 64, nullable = false)
    private String email;

    @Size(max = 12, message = "{constraint.string.length.toolong}")
    @Column(table = "PERSON", length = 12, unique = true, nullable = true)
    private String phone;

    @Column(name = "typ", updatable = false)
    private String typ;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSureName() {
        return sureName;
    }

    public void setSureName(String sureName) {
        this.sureName = sureName;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @Override
    public Long getId() {
        return id;
    }

    public String getTyp() {
        return typ;
    }

    @Override
    protected Object getBusinessKey() {
        return login;
    }
}
