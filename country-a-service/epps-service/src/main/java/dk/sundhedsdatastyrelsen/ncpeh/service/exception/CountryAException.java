package dk.sundhedsdatastyrelsen.ncpeh.service.exception;

import lombok.Getter;

public class CountryAException extends RuntimeException {
    @Getter
    private final int httpStatus;

    public CountryAException(int httpStatus, String message) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public CountryAException(int httpStatus, String message, Exception cause) {
        super(message, cause);
        this.httpStatus = httpStatus;
    }

    public CountryAException(int httpStatus, Exception cause) {
        this(httpStatus, cause.getMessage(), cause);
    }
}
