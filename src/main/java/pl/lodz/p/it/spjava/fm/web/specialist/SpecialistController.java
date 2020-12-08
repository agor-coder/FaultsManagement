package pl.lodz.p.it.spjava.fm.web.specialist;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import pl.lodz.p.it.spjava.fm.dto.SpecialistDTO;
import pl.lodz.p.it.spjava.fm.enpoints.SpecialistEndpoint;
import pl.lodz.p.it.spjava.fm.model.Specialist;

@SessionScoped
public class SpecialistController implements Serializable {

    @Inject
    private SpecialistEndpoint specialistEndpoint;
    
    private SpecialistDTO newSpecialistCreate;

    public SpecialistDTO getNewSpecialistCreate() {
        return newSpecialistCreate;
    }

    public void setNewSpecialistCreate(SpecialistDTO newSpecialistCreate) {
        this.newSpecialistCreate = newSpecialistCreate;
    }

    

    public void addSpecialist(SpecialistDTO specialistDTO) {
        specialistEndpoint.addSpecialist(newSpecialistCreate);
        newSpecialistCreate = null;
    }

}
