package pl.lodz.p.it.spjava.fm.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;



public abstract class AccountDTO {


    
    protected Long id;

    @NotNull(message = "{constraint.notnull}")
   @Size(min=3,max=32,message="{constraint.string.length.notinrange}")
    @Pattern(regexp="^[_a-zA-Z0-9-]*$",message="{constraint.string.incorrectchar}")
    protected String login;

    @NotNull(message = "{constraint.notnull}")
    @Size(min = 2, message = "{constraint.string.length.tooshort}")
    protected String password;

    @NotNull(message = "{constraint.notnull}")
    protected boolean active;

    @NotNull(message = "{constraint.notnull}")
    @Size(min = 3, max = 32, message = "{constraint.string.length.notinrange}")
    private String firstName;

    @NotNull(message = "{constraint.notnull}")
    @Size(min = 3, max = 32, message = "{constraint.string.length.notinrange}")
    private String sureName;

    @NotNull(message = "{constraint.notnull}")
    @Size(min = 6, max = 64, message = "{constraint.string.length.notinrange}")
    //@Pattern(regexp="^[_a-z0-9-]+(\\.[_a-z0-9-]+)*@[a-z0-9-]+(\\.[a-z0-9-]+)*(\\.[a-z]{2,4})$",message="{constraint.string.incorrectemail}")
    private String email;

    @Size(max = 12, message = "{constraint.string.length.toolong}")
    private String phone;

    
    private String typ;
    
    public AccountDTO() {
    }

    //bez hasła
    public AccountDTO(Long id, String login, boolean active,String firstName, String sureName, String email, String phone, String typ) {
        this.id = id;
        this.login = login;
        this.active=active;
        this.firstName = firstName;
        this.sureName = sureName;
        this.email = email;
        this.phone = phone;
        this.typ = typ;
    }
    
    
    
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

    public String getTyp() {
        return typ;
    }

    public Long getId() {
        return id;
    }
    

}