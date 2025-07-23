package dk.sundhedsdatastyrelsen.ncpeh.authentication;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.xml.namespace.NamespaceContext;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
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
            @Override
            public String getNamespaceURI(String prefix) {
                return namespaces.get(prefix);
            }

            @Override
            public String getPrefix(String uri) {
                for (var entry : namespaces.entrySet()) {
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
        });
        return xPath;
    }

    public static String writeDocumentToString(Document doc) throws TransformerException {
        var transformer = TransformerFactory.newDefaultInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "no");
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
        transformer.setOutputProperty(OutputKeys.METHOD, "xml");
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        var writer = new StringWriter();
        transformer.transform(new DOMSource(doc), new StreamResult(writer));
        return writer.toString();
    }

    public static Element appendChild(Document parent, XmlNamespaces nsPrefix, String name) {
        var child = parent.createElementNS(nsPrefix.uri(), name);
        child.setPrefix(nsPrefix.prefix());
        parent.appendChild(child);
        return child;
    }

    public static Element appendChild(Element parent, XmlNamespaces nsPrefix, String name) {
        var child = parent.getOwnerDocument().createElementNS(nsPrefix.uri(), name);
        child.setPrefix(nsPrefix.prefix());
        parent.appendChild(child);
        return child;
    }

    public static Element appendChild(Element parent, XmlNamespaces nsPrefix, String name, String textValue) {
        var child = parent.getOwnerDocument().createElementNS(nsPrefix.uri(), name);
        child.setPrefix(nsPrefix.prefix());
        child.setTextContent(textValue);
        parent.appendChild(child);
        return child;
    }

    public static void declareNamespaces(Element element, XmlNamespaces... namespaces) {
        for (var ns : namespaces) {
            element.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:" + ns.prefix(), ns.uri());
        }
    }

    public static void setIdAttribute(Element elm, XmlNamespaces ns, String name, String value) {
        elm.setAttributeNS(ns.uri(), ns.prefix() + ":" + name, value);
        elm.setIdAttributeNS(ns.uri(), name, true);
    }
}
