package dk.sundhedsdatastyrelsen.ncpeh.errorhandling;

import dk.sundhedsdatastyrelsen.ncpeh.ncp.api.ErrorDto;
import dk.sundhedsdatastyrelsen.ncpeh.service.exception.DataRequirementException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@Order(1)
@RestControllerAdvice
public class DataRequirementExceptionHandler {
    @ExceptionHandler(DataRequirementException.class)
    public ResponseEntity<ErrorDto> handleException(DataRequirementException e) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        var details = new ErrorDto(httpStatus.name(), e.getMessage() != null ? e.getMessage() : e.getClass().getSimpleName());
        log.info("{}: {} - Returning {}", e.getClass().getSimpleName(), e.getMessage(), httpStatus);

        return ResponseEntity.status(httpStatus).body(details);
    }
}
