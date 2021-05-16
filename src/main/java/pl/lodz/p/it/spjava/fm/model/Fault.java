package pl.lodz.p.it.spjava.fm.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@NamedQueries({
    @NamedQuery(name = "Fault.findSpecOfLogin", query = "SELECT i FROM Fault i where i.specialist.login = :login"),
    @NamedQuery(name = "Fault.findNotifOfLogin", query = "SELECT i FROM Fault i where i.whoNotified.login = :login"),
    @NamedQuery(name = "Fault.countOfSpecialist", query = "SELECT COUNT (i) FROM Fault i where i.specialist = :specialist AND i.status=:status"),})
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
    @Getter
    @Setter
    private String faultDescribe;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Getter
    @Setter
    private FaultStatus status = FaultStatus.NOT_ASSIGNED;

    @JoinColumn(nullable = false, updatable = false)
    @ManyToOne
    @Getter
    @Setter
    private TechArea techArea;

    @JoinColumn(nullable = true)
    @ManyToOne
    @Getter
    @Setter
    private Specialist specialist;

    @JoinColumn(nullable = false, updatable = false)
    @ManyToOne
    @Getter
    @Setter
    private Notifier whoNotified;

    @JoinColumn(nullable = true)
    @ManyToOne
    @Getter
    @Setter
    private Assigner whoAssigned;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    protected Object getBusinessKey() {
        return id;
    }

    public enum FaultStatus {
        NOT_ASSIGNED("Nie przydzielona"),
        ASSIGNED("Przydzielona"),
        END("Zakończona");

        private final String description;

        FaultStatus(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }
    };

    @Override
    public String toString() {
        return faultDescribe;
    }

}
