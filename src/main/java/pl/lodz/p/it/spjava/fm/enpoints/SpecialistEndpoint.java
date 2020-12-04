package pl.lodz.p.it.spjava.fm.enpoints;

import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import pl.lodz.p.it.spjava.fm.facade.SpecialistFacade;
import pl.lodz.p.it.spjava.fm.model.Specialist;

@Stateless
public class SpecialistEndpoint {

    @Inject
    private SpecialistFacade specialistFacade;

    public void addSpecialist(Specialist specialist) {
        specialistFacade.create(specialist);
    }

    public void removeSpecialist(Specialist specialist) {
        specialistFacade.remove(specialist);
    }

    public List<Specialist> getSpecialists() {
        return specialistFacade.findAll();
    }

}
