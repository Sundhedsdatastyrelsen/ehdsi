package eu.europa.ec.sante.openncp.portal.controller;

import eu.europa.ec.sante.openncp.application.client.connector.ClientConnectorException;
import eu.europa.ec.sante.openncp.common.error.OpenNCPErrorCode;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class PortalExceptionHandler extends ResponseEntityExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(PortalExceptionHandler.class);

    @ExceptionHandler({Exception.class, PortalException.class})
    public ResponseEntity<Object> handleAll(Exception exception, WebRequest request) {

        logger.error("[Exception] '{}': '{}'", exception.getClass(), exception.getMessage());
        var portalApiError = new PortalApiError(HttpStatus.INTERNAL_SERVER_ERROR, exception.getLocalizedMessage(), exception.getMessage(),
                "Undefined Error");
        return new ResponseEntity<>(portalApiError, new HttpHeaders(), portalApiError.getStatus());
    }

    @ExceptionHandler({ClientConnectorException.class})
    public ResponseEntity<Object> handleClientConnectorException(Exception exception, WebRequest request) {

        logger.error("[Exception] '{}': '{}'", exception.getClass(), exception.getMessage());
        PortalApiError apiError = null;
        if (exception instanceof ClientConnectorException) {
                apiError = new PortalApiError(
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        ((ClientConnectorException) exception).getOpenncpErrorCode().map(OpenNCPErrorCode::getCode).orElse(StringUtils.EMPTY),
                        ((ClientConnectorException) exception).getContext(),
                        exception.getMessage());
        }

        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }
}
