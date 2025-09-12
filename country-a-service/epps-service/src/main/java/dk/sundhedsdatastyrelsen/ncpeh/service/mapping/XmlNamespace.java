package dk.sundhedsdatastyrelsen.ncpeh.service.mapping;

import dk.sundhedsdatastyrelsen.ncpeh.shared.XmlNamespaceContract;

public record XmlNamespace(String prefix, String uri) implements XmlNamespaceContract {
    // FSK
    public static final XmlNamespace HL7 = new XmlNamespace("hl7", "urn:hl7-org:v3");
    public static final XmlNamespace SDTC = new XmlNamespace("sdtc", "urn:hl7-org:sdtc");

}
