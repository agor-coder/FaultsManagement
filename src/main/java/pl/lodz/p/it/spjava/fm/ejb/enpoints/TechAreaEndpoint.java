package pl.lodz.p.it.spjava.fm.ejb.enpoints;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.EJBTransactionRolledbackException;
import javax.ejb.SessionSynchronization;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import pl.lodz.p.it.spjava.fm.dto.TechAreaDTO;
import pl.lodz.p.it.spjava.fm.ejb.interceptor.LoggingInterceptor;
import pl.lodz.p.it.spjava.fm.ejb.managers.TechAreaManager;
import pl.lodz.p.it.spjava.fm.exception.AppBaseException;
import pl.lodz.p.it.spjava.fm.exception.AreaException;
import pl.lodz.p.it.spjava.fm.model.TechArea;
import pl.lodz.p.it.spjava.fm.utils.DTOConverter;

@Stateful
@Interceptors(LoggingInterceptor.class)
@TransactionAttribute(TransactionAttributeType.NEVER)
public class TechAreaEndpoint extends AbstractEndpoint implements SessionSynchronization {

    @Resource(name = "txRetryLimit")
    private int txRetryLimit;

    @EJB
    private TechAreaManager areaManager;

    private TechArea endpointArea;

    public List<TechAreaDTO> getAllAreasDTO() {
        List<TechArea> areasList = areaManager.findAll();
        List<TechAreaDTO> areasListDTO = DTOConverter.createAreaListDTO(areasList);
        return areasListDTO;
    }

    public TechAreaDTO getTechAreaToEdit(TechAreaDTO areaDTO) throws AppBaseException {
        setEndpointAreaFromDTOToEdit(areaDTO);
        return DTOConverter.createTechAreaDTOFromEntity(endpointArea);
    }

    public void remove(TechAreaDTO areaDTO) throws AppBaseException {
        setEndpointAreaFromDTOToEdit(areaDTO);
        areaManager.remove(endpointArea);
    }

    private void setEndpointAreaFromDTOToEdit(TechAreaDTO areaDTO) throws AppBaseException {
        endpointArea = areaManager.find(areaDTO.getId());
        if (null == endpointArea) {
            throw AreaException.createAreaExceptionWithAreaNotFound();
        }
    }

    public void saveAreaAfterEdit(TechAreaDTO areaDTO) throws AppBaseException {
        writeEditableDataFromDTOToEntity(areaDTO, endpointArea);
        areaManager.editArea(endpointArea);
    }

    private void writeEditableDataFromDTOToEntity(TechAreaDTO areaDTO, TechArea area) {
        area.setAreaName(areaDTO.getAreaName());

    }

    public void addArea(TechAreaDTO areaDTO) throws AppBaseException {
        TechArea techArea = new TechArea();
        techArea.setAreaName(areaDTO.getAreaName());
        boolean rollbackTX;
        int retryTXCounter = 1;
        Throwable cause = null;
        do {
            try {
                areaManager.createArea(techArea);
                rollbackTX = areaManager.isLastTransactionRollback();
            } catch (AppBaseException | EJBTransactionRolledbackException ex) {
                Logger.getGlobal().log(Level.SEVERE, "Próba " + retryTXCounter
                        + " wykonania metody biznesowej zakończona wyjątkiem klasy:"
                        + ex.getClass().getName());
                rollbackTX = true;
                retryTXCounter++;
                cause = ex.getCause();
            }
        } while (rollbackTX && retryTXCounter <= txRetryLimit);

        if (rollbackTX && retryTXCounter > txRetryLimit) {
            throw AreaException.createWithDbCheckConstraintKey(cause);
        }
    }
}
