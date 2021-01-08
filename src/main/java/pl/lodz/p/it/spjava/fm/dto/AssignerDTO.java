package pl.lodz.p.it.spjava.fm.dto;

import javax.validation.constraints.NotNull;

public class AssignerDTO extends AccountDTO {

    @NotNull(message = "{constraint.notnull}")
    private String department;

    public AssignerDTO() {
    }

    public AssignerDTO(Long id, String login, boolean active, boolean confirmed, String firstName, String sureName, String email,
            String phone, String typ, String department) {
        super(id, login, active, confirmed, firstName, sureName, email, phone, typ);
        this.department = department;
    }
    
    //do formularza newSpec
    public AssignerDTO(String firstName, String sureName,
            String email, String phone, String department) {
        super(firstName, sureName, email, phone);
        this.department = department;

    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

}
