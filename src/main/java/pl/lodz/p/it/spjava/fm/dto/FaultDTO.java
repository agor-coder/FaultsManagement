package pl.lodz.p.it.spjava.fm.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class FaultDTO {

    private Long id;

    @NotNull(message = "{constraint.notnull}")
    @Size(min = 2, max = 256, message = "{constraint.string.length.notinrange}")
    private String faultDescribe;

    private FaultStatus status = FaultStatus.NOT_ASSIGNED;

    private TechAreaDTO techArea;

    private SpecialistDTO specialist;

    private NotifierDTO whoNotified;

    private AssignerDTO whoAssigned;

    public FaultDTO() {
    }

    public FaultDTO(Long id, String faultDescribe,  TechAreaDTO techArea,
            SpecialistDTO specialist, NotifierDTO whoNotified, AssignerDTO whoAssigned) {
        this.id = id;
        this.faultDescribe = faultDescribe;
        this.techArea = techArea;
        this.status = status;
        this.specialist = specialist;
        this.whoNotified = whoNotified;
        this.whoAssigned = whoAssigned;
    }
//do formularza "zgłoś" - konstruktor

    public FaultStatus getStatus() {
        return status;
    }

    public void setStatus(FaultStatus status) {
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

    public static enum FaultStatus {
        NOT_ASSIGNED, ASSIGNED, END
    };

}
