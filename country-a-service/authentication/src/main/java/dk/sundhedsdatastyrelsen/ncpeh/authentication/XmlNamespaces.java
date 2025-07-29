package dk.sundhedsdatastyrelsen.ncpeh.authentication;

public enum XmlNamespaces {
    DS("ds", "http://www.w3.org/2000/09/xmldsig#"),
    SAML("saml", "urn:oasis:names:tc:SAML:2.0:assertion"),
    SOAP("soap", "http://schemas.xmlsoap.org/soap/envelope/"),
    WSSE("wsse", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd"),
    WSA("wsa", "http://www.w3.org/2005/08/addressing"),
    WSP("wsp", "http://schemas.xmlsoap.org/ws/2004/09/policy"),
    WST("wst", "http://schemas.xmlsoap.org/ws/2005/02/trust"),
    WST13("wst13", "http://docs.oasis-open.org/ws-sx/ws-trust/200512"),
    WST14("wst14", "http://docs.oasis-open.org/ws-sx/ws-trust/200802"),
    WSU("wsu", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd"),
    XSD("xsd", "http://www.w3.org/2001/XMLSchema"),
    XSI("xsi", "http://www.w3.org/2001/XMLSchema-instance");

    private final String prefix;
    private final String uri;

    XmlNamespaces(String prefix, String uri) {
        this.prefix = prefix;
        this.uri = uri;
    }

    public String prefix() {
        return prefix;
    }

    public String uri() {
        return uri;
    }
}
