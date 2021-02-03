package pl.lodz.p.it.spjava.fm.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;


@MappedSuperclass
public abstract class AbstractEntity {

    protected static final long serialVersionUID = 1L;

    protected abstract Object getId();
  
    protected abstract Object getBusinessKey();

    @Version
    @Column(nullable = false)
    private int version;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "creation_timestamp")
    private Date creationTimestamp;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "modify_timestamp")
    private Date modificationTimestamp;

    public Date getCreationTimestamp() {
        return creationTimestamp;
    }

    public Date getModificationTimestamp() {
        return modificationTimestamp;
    }

    public int getVersion() {
        return version;
    }

    
    @PreUpdate
    private void updateTimestamp() {
        modificationTimestamp = new Date();
    }

    @PrePersist
    private void creationTimestamp() {
        creationTimestamp = new Date();
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "[id=" + getId() + ", key=" + getBusinessKey() + ", version=" + version + "]";
    }

    @Override
    public boolean equals(Object obj) {
        if (null == obj) {
            return false;
        }

        if (this.getClass().isAssignableFrom(obj.getClass())) {
            return this.getBusinessKey().equals(((AbstractEntity) obj).getBusinessKey());
        } else {
            return false;
        }

    }

    @Override
    public int hashCode() {
        return getBusinessKey().hashCode();
    }
}
