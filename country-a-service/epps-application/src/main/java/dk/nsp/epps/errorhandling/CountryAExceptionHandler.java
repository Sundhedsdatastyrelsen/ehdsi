package dk.nsp.epps.errorhandling;

import dk.nsp.epps.ncp.api.ErrorDto;
import dk.nsp.epps.service.exception.CountryAException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.UUID;

@Slf4j
@Order(1)
@RestControllerAdvice
public class CountryAExceptionHandler {
    private final static String PROTOTYPE_ERROR_MESSAGE = "{}: {} - (Error id: {}) - Returning {}";

    @ExceptionHandler(CountryAException.class)
    public ResponseEntity<ErrorDto> handleException(CountryAException e) {
        HttpStatus httpStatus = e.getHttpStatus();

        var errorUuid = UUID.randomUUID();

        var details = new ErrorDto(httpStatus.name(), String.format("Error id: %s", errorUuid));

        if (httpStatus == HttpStatus.INTERNAL_SERVER_ERROR) {
            log.error(PROTOTYPE_ERROR_MESSAGE, e.getClass()
                .getSimpleName(), e.getMessage(), errorUuid, httpStatus, e);
        } else if (httpStatus.is5xxServerError()) {
            log.warn(PROTOTYPE_ERROR_MESSAGE, e.getClass()
                .getSimpleName(), e.getMessage(), errorUuid, httpStatus);
        } else {
            log.info(PROTOTYPE_ERROR_MESSAGE, e.getClass()
                .getSimpleName(), e.getMessage(), errorUuid, httpStatus);
        }

        return ResponseEntity.status(httpStatus).body(details);
    }
}
