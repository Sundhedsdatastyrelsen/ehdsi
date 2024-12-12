package dk.sundhedsdatastyrelsen.ncpeh.service.exception;

public class DataRequirementException extends RuntimeException {
    public DataRequirementException(String message) {
        super(message);
    }

    public DataRequirementException(String message, Exception cause) {
        super(message, cause);
    }
}
