package eu.europa.ec.sante.ehdsi.portal.controller;


import org.springframework.http.HttpStatus;

import java.util.Collections;
import java.util.List;

public class PortalApiError {

    private final HttpStatus status;
    private final String errorCode;
    private final String message;
    private final List<String> errors;

    public PortalApiError(HttpStatus status, String errorCode, String message, List<String> errors) {
        super();
        this.errorCode = errorCode;
        this.status = status;
        this.message = message;
        this.errors = errors;
    }

    public PortalApiError(HttpStatus status, String errorCode, String message, String error) {
        super();
        this.errorCode = errorCode;
        this.status = status;
        this.message = message;
        errors = Collections.singletonList(error);
    }

    public String getErrorCode() {
        return errorCode;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public List<String> getErrors() {
        return errors;
    }
}
