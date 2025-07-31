package dk.sundhedsdatastyrelsen.ncpeh.authentication;

///  A representation of a namespace along with its prefix for use in XML documents.
public record XmlNamespace(String prefix, String uri)
{
    // This is a list of namespaces used in the project. Its purpose is convenience, to encourage consistency,
    // and to avoid scattering the information around the codebase.
    public static final XmlNamespace DS = new XmlNamespace("ds", "http://www.w3.org/2000/09/xmldsig#");
    public static final XmlNamespace SAML = new XmlNamespace("saml", "urn:oasis:names:tc:SAML:2.0:assertion");
    public static final XmlNamespace SOAP = new XmlNamespace("soap", "http://schemas.xmlsoap.org/soap/envelope/");
    public static final XmlNamespace WSA = new XmlNamespace("wsa", "http://www.w3.org/2005/08/addressing");
    public static final XmlNamespace WSP = new XmlNamespace("wsp", "http://schemas.xmlsoap.org/ws/2004/09/policy");
    public static final XmlNamespace WSSE = new XmlNamespace("wsse", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd");
    public static final XmlNamespace WST = new XmlNamespace("wst", "http://schemas.xmlsoap.org/ws/2005/02/trust");
    public static final XmlNamespace WST13 = new XmlNamespace("wst13", "http://docs.oasis-open.org/ws-sx/ws-trust/200512");
    public static final XmlNamespace WST14 = new XmlNamespace("wst14", "http://docs.oasis-open.org/ws-sx/ws-trust/200802");
    public static final XmlNamespace WSU = new XmlNamespace("wsu", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd");
    public static final XmlNamespace XSD = new XmlNamespace("xsd", "http://www.w3.org/2001/XMLSchema");
    public static final XmlNamespace XSI = new XmlNamespace("xsi", "http://www.w3.org/2001/XMLSchema-instance");
}
