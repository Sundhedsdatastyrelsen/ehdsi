package eu.europa.ec.sante.ehdsi.portal.controller;

public class PortalException extends Exception {

    public PortalException(String s) {
        super(s);
    }

    public PortalException(String s, Throwable throwable) {
        super(s, throwable);
    }
}
