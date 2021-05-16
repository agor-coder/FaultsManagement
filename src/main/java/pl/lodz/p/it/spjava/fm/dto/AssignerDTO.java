package pl.lodz.p.it.spjava.fm.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

public class AssignerDTO extends AccountDTO {

    @NotNull(message = "{constraint.notnull}")
    @Size(max = 16, message = "{constraint.string.length.toolong}")
    @Pattern(regexp = "^[A-z0-9]*$", message = "{constraint.string.incorrectchar}")
    @Getter
    @Setter
    private String department;

    public AssignerDTO() {
    }

    public AssignerDTO(Long id, String login, boolean active, boolean confirmed, String firstName, String sureName, String email,
            String phone, String typ, String department) {
        super(id, login, active, confirmed, firstName, sureName, email, phone, typ);
        this.department = department;
    }

    @Override
    public String toString() {
        return super.getSureName() + " - " + department;
    }
}
