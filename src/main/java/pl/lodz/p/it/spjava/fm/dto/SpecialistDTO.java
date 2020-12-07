package pl.lodz.p.it.spjava.fm.dto;

import javax.validation.constraints.NotNull;

public class SpecialistDTO extends AccountDTO {

    private static final int MAX_FAULTS_IN_PROGRESS = 3;

    @NotNull(message = "{constraint.notnull}")
    private String department;

    public SpecialistDTO() {
    }

    public SpecialistDTO(Long id, String login, String firstName, String sureName,
            String email, String phone, String typ, String department) {
        super(id, login, firstName, sureName, email, phone, typ);
        this.department = department;
    }

    @NotNull(message = "{constraint.notnull}")
    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

}
