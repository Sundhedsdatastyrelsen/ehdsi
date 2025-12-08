package dk.sundhedsdatastyrelsen.ncpeh.base.utils;

public class PublicException extends RuntimeException {
    private final int httpCode;

    public int getHttpCode() {
        return httpCode;
    }

    /// The http code will be 500.
    public PublicException(String message) {
        this(500, message, null);
    }

    /// The http code will be 500.
    public PublicException(Throwable cause) {
        this(500, cause.getMessage(), cause);
    }

    /// The http code will be 500.
    public PublicException(String message, Throwable cause) {
        this(500, message, cause);
    }

    /// @param httpCode The http status code. Defaults to 500.
    public PublicException(int httpCode, String message) {
        this(httpCode, message, null);
    }

    /// @param httpCode The http status code. Defaults to 500.
    public PublicException(int httpCode, Throwable cause) {
        this(httpCode, cause.getMessage(), null);
    }

    /// @param httpCode The http status code. Defaults to 500.
    public PublicException(int httpCode, String message, Throwable cause) {
        super(message, cause);
        this.httpCode = httpCode;
    }
}
