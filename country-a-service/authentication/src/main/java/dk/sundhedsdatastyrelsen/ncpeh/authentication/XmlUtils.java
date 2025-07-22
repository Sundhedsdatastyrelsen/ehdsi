package dk.sundhedsdatastyrelsen.ncpeh.authentication;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.namespace.NamespaceContext;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Map;

public class XmlUtils {
    private XmlUtils() {
    }

    public static Document parse(String xml) throws AuthenticationException {
        try {
            return parse(new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8)));
        } catch (IOException e) {
            throw new IllegalStateException("should not happen", e);
        }
    }

    public static Document parse(InputStream xml) throws AuthenticationException, IOException {
        try (xml) {
            var builder = DocumentBuilderFactory.newDefaultNSInstance().newDocumentBuilder();
            return builder.parse(xml);
        } catch (SAXException e) {
            throw new AuthenticationException("Parse error", e);
        } catch (ParserConfigurationException e) {
            throw new IllegalStateException("should not happen", e);
        }

    }

    /**
     * Create an XPath instance with the given namespace mapping.
     */
    public static XPath xpath(Map<String, String> namespaces) {
        var xPathFactory = XPathFactory.newInstance();
        var xPath = xPathFactory.newXPath();
        xPath.setNamespaceContext(new NamespaceContext() {
            @SuppressWarnings("java:S116")
            private final Map<String, String> _namespaces = namespaces;

            @Override
            public String getNamespaceURI(String prefix) {
                return _namespaces.get(prefix);
            }

            @Override
            public String getPrefix(String uri) {
                for (var entry : _namespaces.entrySet()) {
                    if (entry.getValue().equals(uri)) {
                        return entry.getKey();
                    }
                }
                return null;
            }

            @Override
            public Iterator<String> getPrefixes(String uri) {
                return _namespaces.entrySet().stream()
                    .filter(entry -> entry.getValue().equals(uri))
                    .map(Map.Entry::getKey)
                    .iterator();
            }
        });
        return xPath;
    }
}
