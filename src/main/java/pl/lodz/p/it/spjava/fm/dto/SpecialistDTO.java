package pl.lodz.p.it.spjava.fm.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class SpecialistDTO extends AccountDTO {

    @NotNull(message = "{constraint.notnull}")
    @Size(max = 16, message = "{constraint.string.length.toolong}")
    @Pattern(regexp = "^[A-z0-9]*$", message = "{constraint.string.incorrectchar}")
    private String department;

    public SpecialistDTO() {
    }

    public SpecialistDTO(Long id, String login, boolean active,boolean confirmed, String firstName, String sureName,
            String email, String phone, String typ, String department) {
        super(id, login, active,confirmed, firstName, sureName, email, phone, typ);
        this.department = department;
    }

    //do formularza newSpec
    public SpecialistDTO(String firstName, String sureName,
            String email, String phone, String department) {
        super(firstName, sureName, email, phone);
        this.department = department;

    }

    @NotNull(message = "{constraint.notnull}")
    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    @Override
    public String toString() {
        return super.getSureName() + " - " + department;
    }
}
