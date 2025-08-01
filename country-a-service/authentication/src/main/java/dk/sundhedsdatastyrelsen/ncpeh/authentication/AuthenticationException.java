package dk.sundhedsdatastyrelsen.ncpeh.authentication;

/**
 * Exception thrown when authentication-related operations fail.
 */
public class AuthenticationException extends Exception {

    public AuthenticationException(String message) {
        super(message);
    }

    public AuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }

    public AuthenticationException(Throwable cause) {
        super(cause);
    }

    public static class SosiStsException extends AuthenticationException {
        public SosiStsException(String faultCode, String faultString, String faultActor) {
            super("%s: %s, actor: %s".formatted(faultCode, faultString, faultActor));
        }
    }
}
