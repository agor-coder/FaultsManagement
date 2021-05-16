package pl.lodz.p.it.spjava.fm.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

public class AccountDTO {

    protected Long id;

    @NotNull(message = "{constraint.notnull}")
    @Size(min = 5, max = 32, message = "{constraint.string.length.notinrange}")
    @Pattern(regexp = "^[_a-zA-Z0-9-]*$", message = "{constraint.string.incorrectchar}")
    @Getter
    @Setter
    protected String login;

    @NotNull(message = "{constraint.notnull}")
    @Size(min = 2, max = 32, message = "{constraint.string.length.notinrange}")
    @Getter
    @Setter
    protected String password;

    @NotNull(message = "{constraint.notnull}")
    @Getter
    @Setter
    protected boolean active = true;

    @NotNull(message = "{constraint.notnull}")
    @Getter
    @Setter
    protected boolean confirmed = true;

    @NotNull(message = "{constraint.notnull}")
    @Size(min = 3, max = 32, message = "{constraint.string.length.notinrange}")
    @Pattern(regexp = "^[\\pL \\'-]*$", message = "{constraint.string.incorrectchar}")
    @Getter
    @Setter
    private String firstName;

    @NotNull(message = "{constraint.notnull}")
    @Size(min = 3, max = 32, message = "{constraint.string.length.notinrange}")
    @Pattern(regexp = "^[\\pL \\'-]*$", message = "{constraint.string.incorrectchar}")
    @Getter
    @Setter
    private String sureName;

    @NotNull(message = "{constraint.notnull}")
    @Size(min = 6, max = 64, message = "{constraint.string.length.notinrange}")
    @Pattern(regexp = "^[_a-z0-9-]+(\\.[_a-z0-9-]+)*@[a-z0-9-]+(\\.[a-z0-9-]+)*(\\.[a-z]{2,4})$", message = "{constraint.string.incorrectemail}")
    @Getter
    @Setter
    private String email;

    @Size(max = 9, message = "{constraint.string.length.toolong}")
    @Pattern(regexp = "^[0-9]*$", message = "{constraint.string.incorrectchar}")
    @Getter
    @Setter
    private String phone;

    private String type;

    public AccountDTO() {
    }

    public AccountDTO(Long id, String login, boolean active, boolean confirmed, String firstName, String sureName, String email, String phone, String type) {
        this.id = id;
        this.login = login;
        this.active = active;
        this.confirmed = confirmed;
        this.firstName = firstName;
        this.sureName = sureName;
        this.email = email;
        this.phone = phone;
        this.type = type;
    }

    public AccountDTO(String firstName, String sureName, String email, String phone) {
        this.password = password;
        this.firstName = firstName;
        this.sureName = sureName;
        this.email = email;
        this.phone = phone;
    }

    public String getType() {
        return type;
    }

    public Long getId() {
        return id;
    }

}
