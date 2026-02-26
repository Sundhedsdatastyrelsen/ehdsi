package dk.sundhedsdatastyrelsen.ncpeh.base.utils;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
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

    /**
     * Parses XML string into a Document.
     *
     * @param xml the XML string to parse
     * @return the parsed Document
     * @throws XmlException if parsing fails
     */
    public static Document parse(String xml) {
        return parse(new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8)));
    }

    /**
     * Parses XML InputStream into a Document.
     *
     * @param xml the XML InputStream to parse
     * @return the parsed Document
     * @throws XmlException if parsing fails
     */
    public static Document parse(InputStream xml) {
        try (xml) {
            var builder = DocumentBuilderFactory.newDefaultNSInstance().newDocumentBuilder();
            return builder.parse(xml);
        } catch (SAXException e) {
            throw new XmlException("Parse error", e);
        } catch (ParserConfigurationException e) {
            throw new IllegalStateException("should not happen", e);
        } catch (IOException e) {
            throw new XmlException("Couldn't read document", e);
        }
    }

    /**
     * Create a new, empty Document object.
     *
     * @throws XmlException if document cannot be created
     */
    public static Document newDocument() {
        try {
            return DocumentBuilderFactory.newDefaultNSInstance().newDocumentBuilder().newDocument();
        } catch (ParserConfigurationException e) {
            throw new XmlException("Couldn't create new document", e);
        }
    }

    /**
     * Writes Document to a Writer without indentation.
     *
     * @param doc    the Document to write
     * @param writer the target Writer
     * @throws XmlException if writing fails
     */
    public static void writeDocument(Document doc, Writer writer) {
        writeDocument(doc, writer, false);
    }

    /**
     * Converts Document to String without indentation.
     *
     * @param doc the Document to convert
     * @return the XML as String
     * @throws XmlException if writing fails
     */
    public static String writeDocumentToString(Document doc) {
        var writer = new StringWriter();
        writeDocument(doc, writer);
        return writer.toString();
    }

    /**
     * Converts Document to String with indentation.
     *
     * @param doc the Document to convert
     * @return the formatted XML as String
     * @throws XmlException if writing fails
     */
    public static String writeDocumentToStringPretty(Document doc) {
        var writer = new StringWriter();
        writeDocument(doc, writer, true);
        return writer.toString();
    }

    private static void writeDocument(Document doc, Result result, boolean shouldIndent) {
        try {
            var transformer = TransformerFactory.newDefaultInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, shouldIndent ? "yes" : "no");
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.transform(new DOMSource(doc), result);
        } catch (TransformerException e) {
            throw new XmlException("Couldn't write document", e);
        }
    }

    private static void writeDocument(Document doc, Writer writer, boolean shouldIndent) {
        writeDocument(doc, new StreamResult(writer), shouldIndent);
    }

    /**
     * Creates and appends a namespaced child element to a Document.
     *
     * @param parent the parent Document
     * @param ns     the XML namespace
     * @param name   the element name
     * @return the created Element
     */
    public static Element appendChild(Document parent, XmlNamespace ns, String name) {
        var child = parent.createElementNS(ns.uri(), name);
        child.setPrefix(ns.prefix());
        parent.appendChild(child);
        return child;
    }

    /**
     * Creates and appends a namespaced child element to an Element.
     *
     * @param parent the parent Element
     * @param ns     the XML namespace
     * @param name   the element name
     * @return the created Element
     */
    public static Element appendChild(Element parent, XmlNamespace ns, String name) {
        var child = parent.getOwnerDocument().createElementNS(ns.uri(), name);
        child.setPrefix(ns.prefix());
        parent.appendChild(child);
        return child;
    }

    /**
     * Creates and appends a namespaced child element with text content to an Element.
     *
     * @param parent    the parent Element
     * @param ns        the XML namespace
     * @param name      the element name
     * @param textValue the text content
     * @return the created Element
     */
    public static Element appendChild(Element parent, XmlNamespace ns, String name, String textValue) {
        var child = parent.getOwnerDocument().createElementNS(ns.uri(), name);
        child.setPrefix(ns.prefix());
        child.setTextContent(textValue);
        parent.appendChild(child);
        return child;
    }

    /**
     * Declares XML namespaces on an Element by setting the xmlns:prefix attributes.
     *
     * @param element    the Element to declare namespaces on
     * @param namespaces the namespaces to declare
     */
    public static void declareNamespaces(Element element, XmlNamespace... namespaces) {
        for (var ns : namespaces) {
            element.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:" + ns.prefix(), ns.uri());
        }
    }

    /**
     * Sets an attribute with namespace on an Element and registers it as an ID attribute.
     *
     * @param elm   the Element to set the ID attribute on
     * @param ns    the XML namespace
     * @param name  the attribute name
     * @param value the attribute value
     */
    public static void setIdAttribute(Element elm, XmlNamespace ns, String name, String value) {
        elm.setAttributeNS(ns.uri(), ns.prefix() + ":" + name, value);
        elm.setIdAttributeNS(ns.uri(), name, true);
    }

    /**
     * Sets a namespaced attribute on an Element.
     *
     * @param elm       the Element to set the attribute on
     * @param ns        the XML namespace
     * @param localName the local attribute name
     * @param value     the attribute value
     */
    public static void setAttribute(Element elm, XmlNamespace ns, String localName, String value) {
        elm.setAttributeNS(ns.uri(), ns.prefix() + ":" + localName, value);
    }

}
