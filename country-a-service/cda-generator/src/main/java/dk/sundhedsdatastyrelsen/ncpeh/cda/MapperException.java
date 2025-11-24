package dk.sundhedsdatastyrelsen.ncpeh.cda;

public class MapperException extends RuntimeException {
    public MapperException(String message) {
        super(message);
    }

    public MapperException(String message, Exception cause) {
        super(message, cause);
    }
}
