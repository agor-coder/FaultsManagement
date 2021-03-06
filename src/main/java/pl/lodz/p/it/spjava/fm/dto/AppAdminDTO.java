package pl.lodz.p.it.spjava.fm.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

public class AppAdminDTO extends AccountDTO {

    @NotNull(message = "{constraint.notnull}")
    @Size(min = 4, max = 4, message = "{constraint.string.length.incorrect}")
    @Pattern(regexp = "^[0-9]*$", message = "{constraint.string.incorrectchar}")
    @Getter
    @Setter
    private String alarmPhone;

    public AppAdminDTO() {
    }

    public AppAdminDTO(Long id, String login, boolean active, boolean confirmed, String firstName, String sureName,
            String email, String phone, String typ, String alarmPhone) {
        super(id, login, active, confirmed, firstName, sureName, email, phone, typ);
        this.alarmPhone = alarmPhone;
    }

}
