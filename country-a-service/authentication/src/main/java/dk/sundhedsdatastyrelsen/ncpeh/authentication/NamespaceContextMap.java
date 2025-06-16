package dk.sundhedsdatastyrelsen.ncpeh.authentication;

import javax.xml.namespace.NamespaceContext;
import java.util.Iterator;
import java.util.Map;

public class NamespaceContextMap implements NamespaceContext {

    private final Map<String, String> prefixMap;

    public NamespaceContextMap(Map<String, String> prefixMap) {
        this.prefixMap = prefixMap;
    }

    @Override
    public String getNamespaceURI(String prefix) {
        return prefixMap.getOrDefault(prefix, "");
    }

    @Override
    public String getPrefix(String namespaceURI) {
        for (Map.Entry<String, String> entry : prefixMap.entrySet()) {
            if (entry.getValue().equals(namespaceURI)) {
                return entry.getKey();
            }
        }
        return null;
    }

    @Override
    public Iterator getPrefixes(String namespaceURI) {
        return prefixMap.keySet().iterator();
    }
}
