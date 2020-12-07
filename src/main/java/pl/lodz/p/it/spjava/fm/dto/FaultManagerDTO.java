package pl.lodz.p.it.spjava.fm.dto;

import javax.validation.constraints.NotNull;

public class FaultManagerDTO extends AccountDTO {

    @NotNull(message = "{constraint.notnull}")
    private String department;

    public FaultManagerDTO() {
    }

    public FaultManagerDTO(Long id, String login, String firstName, String sureName, String email,
            String phone, String typ, String department) {
        super(id, login, firstName, sureName, email, phone, typ);
        this.department = department;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

}
