package dk.nsp.epps.errorhandling;

import dk.nsp.epps.ncp.api.ErrorDto;
import dk.nsp.epps.service.exception.CountryAException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@Order(1)
@RestControllerAdvice
public class CountryAExceptionHandler {
    @ExceptionHandler(CountryAException.class)
    public ResponseEntity<ErrorDto> handleException(CountryAException e) {
        HttpStatus httpStatus = e.getHttpStatus();
        //CFB: I'm not sure we want to expose our internal error messages to the OpenNCP error. We should probably just
        // give them some idea that we failed, and a id or timestamp they can give us, and we can look in the code for.
        var details = new ErrorDto(httpStatus.name(), e.getMessage() != null ? e.getMessage() : e.getClass()
            .getSimpleName());

        if (httpStatus == HttpStatus.INTERNAL_SERVER_ERROR) {
            log.error("{}: {} - Returning {}", e.getClass().getSimpleName(), e.getMessage(), httpStatus, e);
        } else if (httpStatus.is5xxServerError()) {
            log.warn("{}: {} - Returning {}", e.getClass().getSimpleName(), e.getMessage(), httpStatus);
        } else {
            log.info("{}: {} - Returning {}", e.getClass().getSimpleName(), e.getMessage(), httpStatus);
        }

        return ResponseEntity.status(httpStatus).body(details);
    }
}
