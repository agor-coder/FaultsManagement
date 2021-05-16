package pl.lodz.p.it.spjava.fm.dto;

import java.text.SimpleDateFormat;
import java.util.Date;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

public class TechAreaDTO {

    @Getter
    private Long id;

    @NotNull(message = "{constraint.notnull}")
    @Size(min = 3, max = 32, message = "{constraint.string.length.notinrange}")
    @Getter
    @Setter
    private String areaName;

    @Getter
    private Date createTimeStamp;
    
    @Getter
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


    public String getCreateTimeStampSDF() {
        return sdf.format(createTimeStamp);
    }

    public String getModificationTimeStampSDF() {
        return null == modificationTimeStamp ? getCreateTimeStampSDF() : sdf.format(modificationTimeStamp);

    }

    @Override
    public String toString() {
        return areaName;
    }

}
