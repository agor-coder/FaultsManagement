package pl.lodz.p.it.spjava.fm.dto;

import javax.validation.constraints.NotNull;

public class FaultAssignerDTO extends AccountDTO {

    @NotNull(message = "{constraint.notnull}")
    private String department;

    public FaultAssignerDTO() {
    }

    public FaultAssignerDTO(Long id, String login, boolean active, String firstName, String sureName, String email,
            String phone, String typ, String department) {
        super(id, login, active, firstName, sureName, email, phone, typ);
        this.department = department;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

}
