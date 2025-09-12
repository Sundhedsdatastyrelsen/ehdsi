package dk.sundhedsdatastyrelsen.ncpeh.shared;

public class XmlException extends RuntimeException {
    public XmlException(String message) {
        super(message);
    }

    public XmlException(String message, Throwable cause) {
        super(message, cause);
    }
}
