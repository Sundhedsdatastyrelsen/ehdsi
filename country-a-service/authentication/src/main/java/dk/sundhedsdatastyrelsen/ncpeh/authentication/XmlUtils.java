package dk.sundhedsdatastyrelsen.ncpeh.authentication;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.xml.crypto.MarshalException;
import javax.xml.crypto.dsig.CanonicalizationMethod;
import javax.xml.crypto.dsig.DigestMethod;
import javax.xml.crypto.dsig.Reference;
import javax.xml.crypto.dsig.SignatureMethod;
import javax.xml.crypto.dsig.Transform;
import javax.xml.crypto.dsig.XMLSignatureException;
import javax.xml.crypto.dsig.XMLSignatureFactory;
import javax.xml.crypto.dsig.dom.DOMSignContext;
import javax.xml.crypto.dsig.spec.C14NMethodParameterSpec;
import javax.xml.crypto.dsig.spec.TransformParameterSpec;
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
import java.io.OutputStream;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class XmlUtils {
    private XmlUtils() {}

    /**
     * Parses XML string into a Document.
     *
     * @param xml the XML string to parse
     * @return the parsed Document
     * @throws AuthenticationException if parsing fails
     */
    public static Document parse(String xml) throws AuthenticationException {
        try {
            return parse(new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8)));
        } catch (IOException e) {
            throw new IllegalStateException("should not happen", e);
        }
    }

    /**
     * Parses XML InputStream into a Document.
     *
     * @param xml the XML InputStream to parse
     * @return the parsed Document
     * @throws AuthenticationException if parsing fails
     * @throws IOException             if I/O error occurs
     */
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
     * Create a new, empty Document object.
     */
    public static Document newDocument() {
        try {
            return DocumentBuilderFactory.newDefaultNSInstance().newDocumentBuilder().newDocument();
        } catch (ParserConfigurationException e) {
            throw new IllegalStateException(e);
        }
    }

    /**
     * Writes Document to a Writer without indentation.
     *
     * @param doc    the Document to write
     * @param writer the target Writer
     * @throws TransformerException if transformation fails
     */
    public static void writeDocument(Document doc, Writer writer) throws TransformerException {
        writeDocument(doc, writer, false);
    }

    /**
     * Writes Document to an OutputStream without indentation.
     *
     * @param doc the Document to write
     * @param os  the output stream
     * @throws TransformerException if transformation fails
     */
    public static void writeDocument(Document doc, OutputStream os) throws TransformerException {
        writeDocument(doc, os, false);
    }

    /**
     * Converts Document to String without indentation.
     *
     * @param doc the Document to convert
     * @return the XML as String
     * @throws TransformerException if transformation fails
     */
    public static String writeDocumentToString(Document doc) throws TransformerException {
        var writer = new StringWriter();
        writeDocument(doc, writer);
        return writer.toString();
    }

    /**
     * Converts Document to String with indentation.
     *
     * @param doc the Document to convert
     * @return the formatted XML as String
     * @throws TransformerException if transformation fails
     */
    public static String writeDocumentToStringPretty(Document doc) throws TransformerException {
        var writer = new StringWriter();
        writeDocument(doc, writer, true);
        return writer.toString();
    }

    private static void writeDocument(Document doc, Result result, boolean shouldIndent) throws TransformerException {
        var transformer = TransformerFactory.newDefaultInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, shouldIndent ? "yes" : "no");
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
        transformer.setOutputProperty(OutputKeys.METHOD, "xml");
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.transform(new DOMSource(doc), result);
    }

    private static void writeDocument(Document doc, Writer writer, boolean shouldIndent) throws TransformerException {
        writeDocument(doc, new StreamResult(writer), shouldIndent);
    }

    private static void writeDocument(Document doc, OutputStream os, boolean shouldIndent) throws TransformerException {
        writeDocument(doc, new StreamResult(os), shouldIndent);
    }

    /**
     * Creates and appends a namespaced child element to a Document.
     *
     * @param parent the parent Document
     * @param ns     the XML namespace
     * @param name   the element name
     * @return the created Element
     */
    public static Element appendChild(Document parent, XmlNamespaces ns, String name) {
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
    public static Element appendChild(Element parent, XmlNamespaces ns, String name) {
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
    public static Element appendChild(Element parent, XmlNamespaces ns, String name, String textValue) {
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
    public static void declareNamespaces(Element element, XmlNamespaces... namespaces) {
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
    public static void setIdAttribute(Element elm, XmlNamespaces ns, String name, String value) {
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
    public static void setAttribute(Element elm, XmlNamespaces ns, String localName, String value) {
        elm.setAttributeNS(ns.uri(), ns.prefix() + ":" + localName, value);
    }

    /// Signs an XML element with the specified certificate and key.
    ///
    /// The signature is inserted into the document at the specified location, either as a child
    /// of the root element or as a sibling to the specified nextSibling node.
    ///
    /// **Note:** The SOSI team has been notified that SHA1 should be updated.
    ///
    /// @param rootElement   the root element of the XML document to be signed
    /// @param nextSibling   the node after which the signature should be inserted, or null to append as child of rootElement
    /// @param referenceUris list of element IDs to be included in the signature (typically "#id" format)
    /// @param certificate   the certificate and private key pair used for signing
    /// @throws AuthenticationException  if signing fails due to cryptographic or XML processing errors
    /// @throws IllegalArgumentException if any of the parameters are invalid
    public static void sign(Element rootElement, Node nextSibling, List<String> referenceUris, CertificateAndKey certificate) throws AuthenticationException {
        try {
            var sigFactory = XMLSignatureFactory.getInstance("DOM");
            rootElement.getOwnerDocument().normalizeDocument();
            var transforms = List.of(
                sigFactory.newTransform(Transform.ENVELOPED, (TransformParameterSpec) null),
                sigFactory.newTransform(CanonicalizationMethod.EXCLUSIVE, (TransformParameterSpec) null));

            List<Reference> references = new ArrayList<>();
            for (var id : referenceUris) {
                references.add(sigFactory.newReference(
                    id,
                    sigFactory.newDigestMethod(DigestMethod.SHA1, null),
                    transforms,
                    null,
                    null));
            }

            var signedInfo = sigFactory.newSignedInfo(
                sigFactory.newCanonicalizationMethod(CanonicalizationMethod.EXCLUSIVE, (C14NMethodParameterSpec) null),
                sigFactory.newSignatureMethod(SignatureMethod.RSA_SHA1, null), // SOSI team has been notified that SHA1 should be updated. Probable change to SHA256.
                references
            );

            var keyInfoFactory = sigFactory.getKeyInfoFactory();
            var keyInfo = keyInfoFactory.newKeyInfo(List.of(keyInfoFactory.newX509Data(List.of(certificate.certificate()))));

            var signContext = new DOMSignContext(certificate.privateKey(), rootElement);
            if (nextSibling != null) {
                signContext.setNextSibling(nextSibling);
            }
            var signature = sigFactory.newXMLSignature(signedInfo, keyInfo);
            signature.sign(signContext);
        } catch (MarshalException | XMLSignatureException | NoSuchAlgorithmException |
                 InvalidAlgorithmParameterException e) {
            throw new AuthenticationException("Signing failed", e);
        }
    }
}
