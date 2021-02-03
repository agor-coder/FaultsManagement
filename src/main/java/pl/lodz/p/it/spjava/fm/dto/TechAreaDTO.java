package pl.lodz.p.it.spjava.fm.dto;

import java.text.SimpleDateFormat;
import java.util.Date;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class TechAreaDTO {

    private Long id;

    @NotNull(message = "{constraint.notnull}")
    @Size(min = 3, max = 32, message = "{constraint.string.length.notinrange}")
    private String areaName;
    private Date createTimeStamp;
    private Date modificationTimeStamp;
    private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

    public TechAreaDTO() {
    }

    public TechAreaDTO(Long id, String areaName, Date createTimeStamp, Date modificationTimestamp) {
        this.id = id;
        this.areaName = areaName;
        this.modificationTimeStamp = modificationTimestamp;
        this.createTimeStamp = createTimeStamp;
    }
  

    public Long getId() {
        return id;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getCreateTimeStamp() {
        return sdf.format(createTimeStamp);
    }

    public String getModificationTimeStamp() {
        return null == modificationTimeStamp ? getCreateTimeStamp() : sdf.format(modificationTimeStamp);
     
    }

    
    @Override
    public String toString() {
        return areaName ;
    }

}
