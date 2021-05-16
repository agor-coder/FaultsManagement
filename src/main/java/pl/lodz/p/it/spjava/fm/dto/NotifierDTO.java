package pl.lodz.p.it.spjava.fm.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

public class NotifierDTO extends AccountDTO {

    @NotNull(message = "{constraint.notnull}")
    @Size(max = 32, message = "{constraint.string.length.toolong}")
    @Pattern(regexp = "^[0-9\\pL \\'-]*$", message = "{constraint.string.incorrectchar}")
    @Getter
    @Setter
    private String emplacement;

    public NotifierDTO() {
    }

    public NotifierDTO(Long id, String login, boolean active, boolean confirmed, String firstName, String sureName,
            String email, String phone, String type, String emplacement) {
        super(id, login, active, confirmed, firstName, sureName, email, phone, type);
        this.emplacement = emplacement;
    }

    @Override
    public String toString() {
        return super.getSureName() + " - " + emplacement;
    }

}
