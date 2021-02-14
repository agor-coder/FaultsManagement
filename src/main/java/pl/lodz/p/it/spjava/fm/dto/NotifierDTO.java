package pl.lodz.p.it.spjava.fm.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class NotifierDTO extends AccountDTO {

    @NotNull(message = "{constraint.notnull}")
    @Size(max = 16, message = "{constraint.string.length.toolong}")
    private String emplacement;

    public NotifierDTO() {
    }

    public NotifierDTO(Long id, String login, boolean active, boolean confirmed, String firstName, String sureName,
            String email, String phone, String type, String emplacement) {
        super(id, login, active, confirmed, firstName, sureName, email, phone, type);
        this.emplacement = emplacement;
    }

    //do formularza newNotifier
    public NotifierDTO(String firstName, String sureName,
            String email, String phone, String emplacement) {
        super(firstName, sureName, email, phone);
        this.emplacement = emplacement;

    }

    public String getEmplacement() {
        return emplacement;
    }

    public void setEmplacement(String emplacement) {
        this.emplacement = emplacement;
    }

    @Override
    public String toString() {
        return super.getSureName() + " - " + emplacement;
    }

}
