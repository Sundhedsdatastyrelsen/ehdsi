package dk.sundhedsdatastyrelsen.ncpeh.optout;

public class OptOutServiceException extends RuntimeException {
    public OptOutServiceException(String message) {
        super(message);
    }

    public OptOutServiceException(Throwable cause) {
        super(cause);
    }

    public OptOutServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
