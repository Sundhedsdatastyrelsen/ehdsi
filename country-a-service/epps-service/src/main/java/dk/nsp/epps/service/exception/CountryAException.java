package dk.nsp.epps.service.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public class CountryAException extends RuntimeException {
    @Getter
    private final HttpStatus httpStatus;

    public CountryAException(HttpStatus httpStatus, String message) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public CountryAException(HttpStatus httpStatus, String message, Exception cause) {
        super(message, cause);
        this.httpStatus = httpStatus;
    }

    public CountryAException(HttpStatus httpStatus, Exception cause) {
        this(httpStatus, cause.getMessage(), cause);
    }
}
