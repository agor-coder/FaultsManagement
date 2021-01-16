package pl.lodz.p.it.spjava.fm.dto;

import java.text.SimpleDateFormat;
import java.util.Date;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class FaultDTO {

    private Long id;

    @NotNull(message = "{constraint.notnull}")
    @Size(min = 2, max = 256, message = "{constraint.string.length.notinrange}")
    private String faultDescribe;

    private FaultStatusDTO status = FaultStatusDTO.NOT_ASSIGNED;
    private TechAreaDTO techArea;
    private SpecialistDTO specialist;
    private NotifierDTO whoNotified;
    private AssignerDTO whoAssigned;
    private Date createTimeStamp;
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
//do formularza "zgłoś" - konstruktor

    public FaultStatusDTO getStatus() {
        return status;
    }

    public void setStatus(FaultStatusDTO status) {
        this.status = status;
    }

    public TechAreaDTO getTechArea() {
        return techArea;
    }

    public void setTechArea(TechAreaDTO techArea) {
        this.techArea = techArea;
    }

    public String getFaultDescribe() {
        return faultDescribe;
    }

    public void setFaultDescribe(String faultDescribe) {
        this.faultDescribe = faultDescribe;
    }

    public SpecialistDTO getSpecialist() {
        return specialist;
    }

    public void setSpecialist(SpecialistDTO specialist) {
        this.specialist = specialist;
    }

    public NotifierDTO getWhoNotified() {
        return whoNotified;
    }

    public void setWhoNotified(NotifierDTO whoNotified) {
        this.whoNotified = whoNotified;
    }

    public AssignerDTO getWhoAssigned() {
        return whoAssigned;
    }

    public void setWhoAssigned(AssignerDTO whoAssigned) {
        this.whoAssigned = whoAssigned;
    }

    public Long getId() {
        return id;
    }

    public String getCreateTimeStamp() {
        return sdf.format(createTimeStamp);
    }

    public String getModificationTimeStamp() {
        return null == modificationTimeStamp ? getCreateTimeStamp() : sdf.format(modificationTimeStamp);

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

    public static enum FaultStatusDTO {
        NOT_ASSIGNED, ASSIGNED, END
    };

}
