package dk.sundhedsdatastyrelsen.ncpeh.authentication;

import dk.sundhedsdatastyrelsen.ncpeh.shared.XmlNamespaceContract;

///  A representation of a namespace along with its prefix for use in XML documents.
public record XmlNamespace(String prefix, String uri) implements XmlNamespaceContract {
    // This is a list of namespaces used in the project. Its purpose is convenience, to encourage consistency,
    // and to avoid scattering the information around the codebase.
    public static final XmlNamespace AUTH = new XmlNamespace("auth", "http://docs.oasis-open.org/wsfed/authorization/200706");
    public static final XmlNamespace DS = new XmlNamespace("ds", "http://www.w3.org/2000/09/xmldsig#");

    // There are two valid DGWS/MEDCOM namespaces for historic reasons, and CPR at least requires the old one.
    // https://svn.medcom.dk/svn/releases/Standarder/DGWS/Dokumentation/Den%20Gode%20Webservice%201.0.1.pdf:
    // > [...] ældre webservices ikke altid har overholdt XML-skema, men anvendt det
    // > førstnævnte namespace (MEDCOM_LEGACY). Derfor anbefales det sidstnævnte namespace
    // > (MEDCOM) til fremtidige webservices [...]
    public static final XmlNamespace MEDCOM = new XmlNamespace("medcom", "http://svn.medcom.dk/svn/releases/Standarder/DGWS/Schemas/medcom-1.0.1.xsd");
    public static final XmlNamespace MEDCOM_LEGACY = new XmlNamespace("medcom-legacy", "http://www.medcom.dk/dgws/2006/04/dgws-1.0.xsd");
    public static final XmlNamespace SAML = new XmlNamespace("saml", "urn:oasis:names:tc:SAML:2.0:assertion");
    public static final XmlNamespace SOAP = new XmlNamespace("soap", "http://schemas.xmlsoap.org/soap/envelope/");
    public static final XmlNamespace SOSI = new XmlNamespace("sosi", "http://www.sosi.dk/sosi/2006/04/sosi-1.0.xsd");
    public static final XmlNamespace WSA = new XmlNamespace("wsa", "http://www.w3.org/2005/08/addressing");
    public static final XmlNamespace WSP = new XmlNamespace("wsp", "http://schemas.xmlsoap.org/ws/2004/09/policy");
    public static final XmlNamespace WSSE = new XmlNamespace("wsse", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd");
    public static final XmlNamespace WSSE11 = new XmlNamespace("wsse11", "http://docs.oasis-open.org/wss/oasis-wss-wssecurity-secext-1.1.xsd");
    public static final XmlNamespace WST = new XmlNamespace("wst", "http://schemas.xmlsoap.org/ws/2005/02/trust");
    public static final XmlNamespace WST13 = new XmlNamespace("wst13", "http://docs.oasis-open.org/ws-sx/ws-trust/200512");
    public static final XmlNamespace WST14 = new XmlNamespace("wst14", "http://docs.oasis-open.org/ws-sx/ws-trust/200802");
    public static final XmlNamespace WSU = new XmlNamespace("wsu", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd");
    public static final XmlNamespace XSD = new XmlNamespace("xsd", "http://www.w3.org/2001/XMLSchema");
    public static final XmlNamespace XSI = new XmlNamespace("xsi", "http://www.w3.org/2001/XMLSchema-instance");
}
