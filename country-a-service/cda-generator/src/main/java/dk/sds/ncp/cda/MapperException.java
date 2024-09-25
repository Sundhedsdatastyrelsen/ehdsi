package dk.sds.ncp.cda;

public class MapperException extends Exception {
    public MapperException(String message) {
        super(message);
    }

    public MapperException(String message, Exception cause) {
        super(message, cause);
    }
}
