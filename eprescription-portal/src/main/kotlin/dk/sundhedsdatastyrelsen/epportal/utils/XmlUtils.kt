package dk.sundhedsdatastyrelsen.epportal.utils

import org.w3c.dom.Document
import org.w3c.dom.Element
import org.w3c.dom.Node
import java.io.ByteArrayOutputStream
import java.io.InputStream
import javax.xml.XMLConstants
import javax.xml.parsers.DocumentBuilder
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.transform.OutputKeys
import javax.xml.transform.TransformerFactory
import javax.xml.transform.dom.DOMSource
import javax.xml.transform.stream.StreamResult

object XmlUtils {
    private fun documentBuilder(): DocumentBuilder =
        DocumentBuilderFactory.newDefaultNSInstance().apply {
            // We never need DTDs, and refusing them rules out XXE.
            setFeature("http://apache.org/xml/features/disallow-doctype-decl", true)
            setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true)
        }.newDocumentBuilder()

    /**
     * Parses XML InputStream into a Document.
     *
     * @param xml the XML InputStream to parse
     * @return the parsed Document
     */
    fun parse(xml: InputStream): Document {
        xml.use {
            return documentBuilder().parse(xml)
        }
    }

    fun newDocument(): Document = documentBuilder().newDocument()

    /**
     * Serializes a node as UTF-8, without indentation. Whitespace must not be added, as that would break signatures.
     */
    fun serialize(node: Node): ByteArray {
        val transformer = TransformerFactory.newDefaultInstance().newTransformer().apply {
            setOutputProperty(OutputKeys.ENCODING, "UTF-8")
            setOutputProperty(OutputKeys.INDENT, "no")
        }
        val out = ByteArrayOutputStream()
        transformer.transform(DOMSource(node), StreamResult(out))
        return out.toByteArray()
    }
}

/**
 * Creates a namespaced child element and appends it. Namespaces are declared explicitly (see [createElementIn]).
 */
fun Element.appendElement(ns: XmlNamespace, localName: String, text: String? = null): Element {
    val child = ownerDocument.createElementIn(this, ns, localName)
    if (text != null) child.textContent = text
    appendChild(child)
    return child
}

/** Creates a namespaced root element on an empty document. */
fun Document.appendRoot(ns: XmlNamespace, localName: String): Element {
    val root = createElementIn(null, ns, localName)
    appendChild(root)
    return root
}

/**
 * createElementNS doesn't add xmlns attributes; the serializer adds them on output. Canonicalization of the in-memory
 * DOM (when signing) then differs from canonicalization of the parsed output (when verifying), so we declare
 * namespaces as attributes whenever they aren't already in scope.
 */
private fun Document.createElementIn(parent: Element?, ns: XmlNamespace, localName: String): Element {
    val element = createElementNS(ns.uri, if (ns.prefix == null) localName else "${ns.prefix}:$localName")
    val inScope = parent?.lookupNamespaceURI(ns.prefix)
    if (inScope != ns.uri) {
        val attr = if (ns.prefix == null) "xmlns" else "xmlns:${ns.prefix}"
        element.setAttributeNS(XMLConstants.XMLNS_ATTRIBUTE_NS_URI, attr, ns.uri.orEmpty())
    }
    return element
}
