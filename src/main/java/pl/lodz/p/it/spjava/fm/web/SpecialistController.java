package pl.lodz.p.it.spjava.fm.web;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import pl.lodz.p.it.spjava.fm.enpoints.SpecialistEndpoint;
import pl.lodz.p.it.spjava.fm.model.Specialist;

@SessionScoped
public class SpecialistController implements Serializable {

    @Inject
    private SpecialistEndpoint specialistEndpoint;
    
    private Specialist newSpecialist;

    public Specialist getNewSpecialist() {
        return newSpecialist;
    }

    public void setNewSpecialist(Specialist newSpecialist) {
        this.newSpecialist = newSpecialist;
    }

    public void addSpecialist() {
        specialistEndpoint.addSpecialist(newSpecialist);
        newSpecialist = null;
    }

}
