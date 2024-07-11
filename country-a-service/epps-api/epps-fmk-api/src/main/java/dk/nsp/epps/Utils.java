package dk.nsp.epps;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.function.Consumer;

public final class Utils {
    private Utils() {}

    /**
     * Utility function to make it possible to in-line initialize builder-less nested classes.
     * Basically a poor man's version of kotlin's apply method.
     */
    public static <T> T apply(T value, Consumer<T> initializer) {
        initializer.accept(value);
        return value;
    }

    /**
     * Returns a resource as input stream.
     * See: {@link java.lang.ClassLoader#getResourceAsStream(java.lang.String)}
     */
    public static InputStream resourceAsStream(String name) {
        return Utils.class.getClassLoader().getResourceAsStream(name);
    }

    /**
     * Read XML document from an input stream. Closes the input stream.
     * @param xml The input stream containing well-formed XML
     * @return The XML document corresponding to the input
     */
    public static Document readXmlDocument(InputStream xml) throws SAXException, IOException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true);
        try (xml) {
            var db = dbf.newDocumentBuilder();
            return db.parse(xml);
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Read XML from an input stream. Closes the input stream.
     * @param xml The input stream containing well-formed XML
     * @return The XML element corresponding to the input
     */
    public static Element readXml(InputStream xml) throws IOException, SAXException {
        return readXmlDocument(xml).getDocumentElement();
    }

    /**
     * Read XML from a string.
     * @param xml The string containing well-formed XML
     * @return The XML document corresponding to the input
     */
    public static Document readXmlDocument(String xml) throws SAXException {
        try {
            return readXmlDocument(new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8)));
        } catch (IOException e) {
            // shouldn't happen
            throw new RuntimeException(e);
        }
    }

    /**
     * Read XML from a string.
     * @param xml The string containing well-formed XML
     * @return The XML element corresponding to the input
     */
    public static Element readXml(String xml) throws SAXException {
        try {
            return readXml(new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8)));
        } catch (IOException e) {
            // shouldn't happen
            throw new RuntimeException(e);
        }
    }
}
