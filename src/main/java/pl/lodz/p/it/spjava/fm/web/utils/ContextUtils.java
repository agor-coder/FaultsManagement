package pl.lodz.p.it.spjava.fm.web.utils;

import java.security.Principal;
import java.util.ResourceBundle;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import pl.lodz.p.it.spjava.fm.exception.AppBaseException;

@ApplicationScoped
@Named
public class ContextUtils {

    public ContextUtils() {
    }

    public static ExternalContext getContext() {
        return FacesContext.getCurrentInstance().getExternalContext();
    }

    public static Object getApplicationAttribute(String attributeName) {
        return getContext().getApplicationMap().get(attributeName);
    }

    public static Object getSessionAttribute(String attributeName) {
        return getContext().getSessionMap().get(attributeName);
    }

    public static Object getRequestAttribute(String attributeName) {
        return getContext().getRequestMap().get(attributeName);
    }

    public static String getContextParameter(String paramName) {
        return getContext().getInitParameter(paramName);
    }

    public String invalidateSession() {
        ((HttpSession) getContext().getSession(true)).invalidate();
        return "main";
    }

    public static String getSessionID() {
        HttpSession session = (HttpSession) getContext().getSession(true);
        return session.getId();
    }

    public static String getUserName() {
        Principal p = FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal();
        return (null == p ? "" : p.getName());
    }

    public static ResourceBundle getDefaultBundle() {
        String bundlePath = getContextParameter("resourceBundle.path");
        if (null == bundlePath) {
            return null;
        } else {
            return ResourceBundle.getBundle(bundlePath, FacesContext.getCurrentInstance().getViewRoot().getLocale());
        }
    }

    public static boolean isInternationalizationKeyExist(String key) {
        return ContextUtils.getDefaultBundle().getString(key) != null && !"".equals(ContextUtils.getDefaultBundle().getString(key));
    }

    public static void emitInternationalizedMessage(String id, String key) {
        if (ContextUtils.isInternationalizationKeyExist(key)) {
            FacesMessage msg = new FacesMessage(ContextUtils.getDefaultBundle().getString(key));
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext.getCurrentInstance().addMessage(id, msg);
        }
    }

    public static void emitInternationalizedMessageOfException(AppBaseException ex) {
        FacesContext context = FacesContext.getCurrentInstance();
        FacesMessage message = new FacesMessage();
        String bundle = context.getExternalContext().getInitParameter("resourceBundle.path");
        if (bundle != null && ex.getMessage() != null) {
            String localizedMessage = ResourceBundle.getBundle(bundle, context.getViewRoot().getLocale()).getString(ex.getMessage());
            message.setSummary(localizedMessage);
        }
        message.setSeverity(FacesMessage.SEVERITY_ERROR);
        context.addMessage(null, message);
    }

    public static void emitSuccessMessage(String id) {
        emitInternationalizedMessage(id, "general.success.message");
    }
    public static void emitMessage(String text) {
       FacesContext context = FacesContext.getCurrentInstance();
        FacesMessage message = new FacesMessage();
        message.setSummary(text);
        context.addMessage(null, message);
    }
}
