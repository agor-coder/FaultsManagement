package pl.lodz.p.it.spjava.fm.dto;

import javax.validation.constraints.NotNull;


public class NotifierDTO extends AccountDTO {

    @NotNull(message = "{constraint.notnull}")
    private String emplacement;

    
    
    public String getEmplacement() {
        return emplacement;
    }

    public void setEmplacement(String emplacement) {
        this.emplacement = emplacement;
    }


}
