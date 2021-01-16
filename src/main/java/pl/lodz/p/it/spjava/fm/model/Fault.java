package pl.lodz.p.it.spjava.fm.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@NamedQuery(name = "Fault.findOfLogin", query = "SELECT i FROM Fault i where i.specialist.login = 'login0'")
@NamedQuery(name = "Fault.countOfSpecialist", query = "SELECT COUNT (i) FROM Fault i where i.specialist = :specialist")
@Entity
public class Fault extends AbstractEntity implements Serializable {

    public Fault() {
    }

    @Id
    @Column(updatable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotNull(message = "{constraint.notnull}")
    @Size(min = 2, max = 256, message = "{constraint.string.length.notinrange}")
    @Column(nullable = false, length = 256)
    private String faultDescribe;

    @Column(nullable = false)
    private FaultStatus status = FaultStatus.NOT_ASSIGNED;

    @JoinColumn(nullable = false, updatable = false)
    @ManyToOne
    private TechArea techArea;

    @JoinColumn(nullable = true)
    @ManyToOne
    private Specialist specialist;

    @JoinColumn(nullable = false, updatable = false)
    @ManyToOne
    private Notifier whoNotified;

    @JoinColumn(nullable = true)
    @ManyToOne
    private Assigner whoAssigned;

    public FaultStatus getStatus() {
        return status;
    }

    public void setStatus(FaultStatus status) {
        this.status = status;
    }

    public TechArea getTechArea() {
        return techArea;
    }

    public void setTechArea(TechArea techArea) {
        this.techArea = techArea;
    }

    public String getFaultDescribe() {
        return faultDescribe;
    }

    public void setFaultDescribe(String faultDescribe) {
        this.faultDescribe = faultDescribe;
    }

    public Specialist getSpecialist() {
        return specialist;
    }

    public void setSpecialist(Specialist specialist) {
        this.specialist = specialist;
    }

    public Notifier getWhoNotified() {
        return whoNotified;
    }

    public void setWhoNotified(Notifier whoNotified) {
        this.whoNotified = whoNotified;
    }

    public Assigner getWhoAssigned() {
        return whoAssigned;
    }

    public void setWhoAssigned(Assigner whoAssigned) {
        this.whoAssigned = whoAssigned;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    protected Object getBusinessKey() {
        return id;
    }

    public static enum FaultStatus {
        NOT_ASSIGNED, ASSIGNED,  END
    };

}
