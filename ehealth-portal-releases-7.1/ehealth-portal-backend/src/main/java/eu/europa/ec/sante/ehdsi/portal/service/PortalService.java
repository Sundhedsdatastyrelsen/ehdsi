package eu.europa.ec.sante.ehdsi.portal.service;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public abstract class PortalService {

    public HttpSession getSession() {

        var requestAttributes = RequestContextHolder.currentRequestAttributes();
        ServletRequestAttributes attributes = (ServletRequestAttributes) requestAttributes;
        HttpServletRequest request = attributes.getRequest();
        return request.getSession(true);
    }
}
