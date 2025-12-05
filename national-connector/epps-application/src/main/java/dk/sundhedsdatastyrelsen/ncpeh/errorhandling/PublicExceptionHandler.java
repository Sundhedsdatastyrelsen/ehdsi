package dk.sundhedsdatastyrelsen.ncpeh.errorhandling;

import dk.sundhedsdatastyrelsen.ncpeh.base.utils.PublicException;
import dk.sundhedsdatastyrelsen.ncpeh.ncp.api.ErrorDto;
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
public class PublicExceptionHandler {
    private static final String PROTOTYPE_ERROR_MESSAGE = "{}: {} - (Error id: {}) - Returning {}";

    @ExceptionHandler(PublicException.class)
    public ResponseEntity<ErrorDto> handleException(PublicException e) {
        var httpStatusCode = e.getHttpCode();
        var httpStatus = HttpStatus.valueOf(httpStatusCode);

        var errorUuid = UUID.randomUUID();

        var details = new ErrorDto(
            httpStatus.name(),
            String.format(
                "%s Error id: %s",
                e.getMessage().endsWith(".") ? e.getMessage() : (e.getMessage() + "."),
                errorUuid));

        if (httpStatus == HttpStatus.INTERNAL_SERVER_ERROR) {
            log.error(
                PROTOTYPE_ERROR_MESSAGE, e.getClass()
                    .getSimpleName(), e.getMessage(), errorUuid, httpStatus, e);
        } else if (httpStatus.is5xxServerError()) {
            log.warn(
                PROTOTYPE_ERROR_MESSAGE, e.getClass()
                    .getSimpleName(), e.getMessage(), errorUuid, httpStatus);
        } else {
            log.info(
                PROTOTYPE_ERROR_MESSAGE, e.getClass()
                    .getSimpleName(), e.getMessage(), errorUuid, httpStatus);
        }

        return ResponseEntity.status(httpStatus).body(details);
    }
}
