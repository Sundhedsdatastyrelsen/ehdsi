package dk.sundhedsdatastyrelsen.ncpeh.authentication.parser;

import javax.xml.namespace.NamespaceContext;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class SoapNamespaceContext implements NamespaceContext {
    
    private final Map<String, String> namespaces;
    
    public SoapNamespaceContext() {
        namespaces = new HashMap<>();
        namespaces.put("soapenv", "http://www.w3.org/2003/05/soap-envelope");
        namespaces.put("wsse", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd");
        namespaces.put("saml2", "urn:oasis:names:tc:SAML:2.0:assertion");
        namespaces.put("ds", "http://www.w3.org/2000/09/xmldsig#");
    }
    
    @Override
    public String getNamespaceURI(String prefix) {
        return namespaces.get(prefix);
    }
    
    @Override
    public String getPrefix(String uri) {
        for (Map.Entry<String, String> entry : namespaces.entrySet()) {
            if (entry.getValue().equals(uri)) {
                return entry.getKey();
            }
        }
        return null;
    }
    
    @Override
    public Iterator<String> getPrefixes(String uri) {
        return namespaces.entrySet().stream()
            .filter(entry -> entry.getValue().equals(uri))
            .map(Map.Entry::getKey)
            .iterator();
    }
} 