package dk.sundhedsdatastyrelsen.ncpeh.authentication;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;

public class XmlUtils {
    private XmlUtils() {}

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

    public static void writeDocument(Document doc, Writer writer) throws TransformerException {
        var transformer = TransformerFactory.newDefaultInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "no");
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
        transformer.setOutputProperty(OutputKeys.METHOD, "xml");
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.transform(new DOMSource(doc), new StreamResult(writer));
    }

    public static String writeDocumentToString(Document doc) throws TransformerException {
        var writer = new StringWriter();
        writeDocument(doc, writer);
        return writer.toString();
    }

    public static String writeDocumentToStringPretty(Document doc) throws TransformerException {
        var transformer = TransformerFactory.newDefaultInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
        transformer.setOutputProperty(OutputKeys.METHOD, "xml");
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        var writer = new StringWriter();
        transformer.transform(new DOMSource(doc), new StreamResult(writer));
        return writer.toString();
    }

    public static Element appendChild(Document parent, XmlNamespaces ns, String name) {
        var child = parent.createElementNS(ns.uri(), name);
        child.setPrefix(ns.prefix());
        parent.appendChild(child);
        return child;
    }

    public static Element appendChild(Element parent, XmlNamespaces ns, String name) {
        var child = parent.getOwnerDocument().createElementNS(ns.uri(), name);
        child.setPrefix(ns.prefix());
        parent.appendChild(child);
        return child;
    }

    public static Element appendChild(Element parent, XmlNamespaces ns, String name, String textValue) {
        var child = parent.getOwnerDocument().createElementNS(ns.uri(), name);
        child.setPrefix(ns.prefix());
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

    public static void setAttribute(Element elm, XmlNamespaces ns, String localName, String value) {
        elm.setAttributeNS(ns.uri(), ns.prefix() + ":" + localName, value);
    }
}
