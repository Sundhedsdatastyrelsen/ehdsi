package dk.sundhedsdatastyrelsen.ncpeh.client;

public class NspClientException extends RuntimeException {
    public NspClientException(String message) {
        super(message);
    }

    public NspClientException(String message, Exception cause) {
        super(message, cause);
    }
}
