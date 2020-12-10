package pl.lodz.p.it.spjava.fm.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class AppAdminDTO extends AccountDTO {

    @NotNull(message = "{constraint.notnull}")
    @Size(max = 4, message = "{constraint.string.length.toolong}")
    private String alarmPhone;

    public AppAdminDTO() {
    }

    public AppAdminDTO(Long id, String login, boolean active,String firstName, String sureName, String email, String phone, String typ, String alarmPhone) {
        super(id, login, active, firstName, sureName, email, phone, typ);
        this.alarmPhone = alarmPhone;
    }

    public String getAlarmPhone() {
        return alarmPhone;
    }

    public void setAlarmPhone(String alarmPhone) {
        this.alarmPhone = alarmPhone;
    }

}
