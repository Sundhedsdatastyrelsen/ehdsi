package dk.nsp.epps.errorhandling;

import dk.nsp.epps.ncp.api.ErrorDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class FallbackExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDto> handleException(Exception e) {
        var httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        var details = new ErrorDto(httpStatus.name(), e.getMessage() != null ? e.getMessage() : e.getClass().getSimpleName());

        log.error("{}: {} - Returning {}", e.getClass().getSimpleName(), e.getMessage(), httpStatus, e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(details);
    }
}
