package pl.lodz.p.it.spjava.fm.dto;

import java.text.SimpleDateFormat;
import java.util.Date;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

public class FaultDTO {

    @Getter
    private Long id;

    @NotNull(message = "{constraint.notnull}")
    @Size(min = 2, max = 256, message = "{constraint.string.length.notinrange}")
    @Getter
    @Setter
    private String faultDescribe;

    @Getter
    @Setter
    private FaultStatusDTO status = FaultStatusDTO.NOT_ASSIGNED;

    @Getter
    @Setter
    private TechAreaDTO techArea;

    @Getter
    @Setter
    private SpecialistDTO specialist;

    @Getter
    @Setter
    private NotifierDTO whoNotified;

    @Getter
    @Setter
    private AssignerDTO whoAssigned;

    @Getter
    private Date createTimeStamp;

    @Getter
    private Date modificationTimeStamp;
    private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

    public FaultDTO() {

    }

    public FaultDTO(Date createTimeStamp, Long id, String faultDescribe, FaultStatusDTO status, TechAreaDTO techArea,
            SpecialistDTO specialist, NotifierDTO whoNotified, AssignerDTO whoAssigned, Date modificationTimeStamp) {

        this.createTimeStamp = createTimeStamp;
        this.id = id;
        this.faultDescribe = faultDescribe;
        this.techArea = techArea;
        this.status = status;
        this.specialist = specialist;
        this.whoNotified = whoNotified;
        this.whoAssigned = whoAssigned;
        this.modificationTimeStamp = modificationTimeStamp;
    }


    public String getCreateTimeStampSDF() {
        return sdf.format(createTimeStamp);
    }

    public String getModificationTimeStampSDF() {
        return null == modificationTimeStamp ? getCreateTimeStampSDF() : sdf.format(modificationTimeStamp);

    }

    public boolean isAssigned() {
        return status.name().equals("ASSIGNED");
    }

    public boolean isNotAssigned() {
        return status.name().equals("NOT_ASSIGNED");
    }

    public boolean isEnd() {
        return status.name().equals("END");
    }

    @Override
    public String toString() {
        return "Fault: " + createTimeStamp + " ";
    }

    public enum FaultStatusDTO {
        NOT_ASSIGNED("Nie przydzielona"),
        ASSIGNED("Przydzielona"),
        END("Zakończona");

        private final String description;

        FaultStatusDTO(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }
    };

}
