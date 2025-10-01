package dk.sundhedsdatastyrelsen.ncpeh.base.utils;

public class XmlException extends Exception {
    public XmlException(String message) {
        super(message);
    }

    public XmlException(String message, Throwable cause) {
        super(message, cause);
    }
}
