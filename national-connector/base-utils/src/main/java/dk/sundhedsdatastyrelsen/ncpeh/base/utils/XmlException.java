package dk.sundhedsdatastyrelsen.ncpeh.base.utils;

public class XmlException extends RuntimeException {
    private String fullText;

    public String getFullText() {
        return fullText;
    }

    public XmlException(String message) {
        super(message);
    }

    public XmlException(String message, Throwable cause) {
        super(message, cause);
    }

    public XmlException(String message, Throwable cause, String fullText) {
        this(message, cause);
        this.fullText = fullText;
    }

}
