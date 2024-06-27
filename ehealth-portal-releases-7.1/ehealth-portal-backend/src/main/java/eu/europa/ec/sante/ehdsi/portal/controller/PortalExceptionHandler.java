package eu.europa.ec.sante.ehdsi.portal.controller;

import epsos.openncp.protocolterminator.ClientConnectorConsumerException;
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

    @ExceptionHandler({ClientConnectorConsumerException.class})
    public ResponseEntity<Object> handleClientConnectorConsumerException(Exception exception, WebRequest request) {

        logger.error("[Exception] '{}': '{}'", exception.getClass(), exception.getMessage());
        PortalApiError apiError = null;
        if (exception instanceof ClientConnectorConsumerException) {
                apiError = new PortalApiError(
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        ((ClientConnectorConsumerException) exception).getOpenncpErrorCode().getCode(),
                        ((ClientConnectorConsumerException) exception).getContext(),
                        exception.getMessage());
        }


        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }
}
