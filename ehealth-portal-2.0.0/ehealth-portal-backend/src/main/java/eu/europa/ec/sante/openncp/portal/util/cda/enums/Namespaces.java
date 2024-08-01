package eu.europa.ec.sante.openncp.portal.util.cda.enums;

public enum Namespaces {

    HL7("hl7", "urn:hl7-org:v3"),
    HL7_PHARMACY("pharm", "urn:hl7-org:pharm"),
    XSI("xsi", "http://www.w3.org/2001/XMLSchema-instance");

    private final String prefix;
    private final String uri;

    Namespaces(String prefix, String uri) {
        this.prefix = prefix;
        this.uri = uri;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getUri() {
        return uri;
    }
}
