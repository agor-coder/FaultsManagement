package pl.lodz.p.it.spjava.fm.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class FaultDTO {

    private Long id;

    @NotNull(message = "{constraint.notnull}")
    @Size(min = 2, max = 256, message = "{constraint.string.length.notinrange}")
    private String faultDescribe;

    private FaultStatus status = FaultStatus.NOTIFIED;

    private TechAreaDTO techArea;

    private SpecialistDTO specialist;

    private NotifierDTO ktoZlozyl;

    private AssignerDTO ktoPrzydzielil;

    public FaultDTO() {
    }

    public FaultDTO(Long id, String faultDescribe, TechAreaDTO techArea,
            SpecialistDTO specialist, NotifierDTO ktoZlozyl, AssignerDTO ktoPrzydzielil) {
        this.id = id;
        this.faultDescribe = faultDescribe;
        this.techArea = techArea;
        this.specialist = specialist;
        this.ktoZlozyl = ktoZlozyl;
        this.ktoPrzydzielil = ktoPrzydzielil;
    }

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

    public NotifierDTO getKtoZlozyl() {
        return ktoZlozyl;
    }

    public void setKtoZlozyl(NotifierDTO ktoZlozyl) {
        this.ktoZlozyl = ktoZlozyl;
    }

    public AssignerDTO getKtoPrzydzielil() {
        return ktoPrzydzielil;
    }

    public void setKtoPrzydzielil(AssignerDTO ktoPrzydzielil) {
        this.ktoPrzydzielil = ktoPrzydzielil;
    }

    public Long getId() {
        return id;
    }

    public static enum FaultStatus {
        NOTIFIED, ASSIGNED, IN_PROGRESS, END
    };

}
