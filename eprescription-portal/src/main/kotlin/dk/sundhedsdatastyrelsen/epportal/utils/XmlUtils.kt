package dk.sundhedsdatastyrelsen.epportal.utils

import org.w3c.dom.Document
import org.w3c.dom.Element
import org.w3c.dom.Node
import java.io.ByteArrayInputStream
import java.io.InputStream
import java.io.StringWriter
import java.io.Writer
import java.nio.charset.StandardCharsets
import javax.xml.XMLConstants
import javax.xml.parsers.DocumentBuilder
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.transform.OutputKeys
import javax.xml.transform.Result
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
     * Parses XML string into a Document.
     *
     * @param xml the XML string to parse
     * @return the parsed Document
     */
    fun parse(xml: String): Document {
        return parse(ByteArrayInputStream(xml.toByteArray(StandardCharsets.UTF_8)))
    }

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

    /**
     * Create a new, empty Document object.
     */
    fun newDocument(): Document {
        return documentBuilder().newDocument()
    }

    /**
     * Writes Document to a Writer without indentation.
     *
     * @param doc    the Document to write
     * @param writer the target Writer
     */
    fun writeDocument(doc: Document, writer: Writer) {
        writeDocument(doc, writer, false)
    }

    /**
     * Converts Document to String without indentation.
     *
     * @param doc the Document to convert
     * @return the XML as String
     */
    fun writeDocumentToString(doc: Document): String {
        val writer = StringWriter()
        writeDocument(doc, writer)
        return writer.toString()
    }

    /**
     * Converts Document to String with indentation.
     *
     * @param doc the Document to convert
     * @return the formatted XML as String
     */
    fun writeDocumentToStringPretty(doc: Document): String {
        val writer = StringWriter()
        writeDocument(doc, writer, true)
        return writer.toString()
    }

    private fun writeDocument(doc: Document, result: Result, shouldIndent: Boolean) {
        TransformerFactory.newDefaultInstance().newTransformer().run {
            setOutputProperty(OutputKeys.INDENT, if (shouldIndent) "yes" else "no")
            setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes")
            setOutputProperty(OutputKeys.METHOD, "xml")
            setOutputProperty(OutputKeys.ENCODING, "UTF-8")
            transform(DOMSource(doc), result)
        }
    }

    private fun writeDocument(doc: Document, writer: Writer, shouldIndent: Boolean) {
        writeDocument(doc, StreamResult(writer), shouldIndent)
    }

    /**
     * Creates and appends a namespaced child element to a Document.
     *
     * @param parent the parent Document
     * @param ns     the XML namespace
     * @param name   the element name
     * @return the created Element
     */
    fun appendChild(parent: Document, ns: XmlNamespace, name: String): Element {
        val child = parent.createElementNS(ns.uri, name)
        child.prefix = ns.prefix
        parent.appendChild(child)
        return child
    }

    /**
     * Creates and appends a namespaced child element to an Element.
     *
     * @param parent the parent Element
     * @param ns     the XML namespace
     * @param name   the element name
     * @return the created Element
     */
    fun appendChild(parent: Element, ns: XmlNamespace, name: String): Element {
        val child = parent.ownerDocument.createElementNS(ns.uri, name)
        child.prefix = ns.prefix
        parent.appendChild(child)
        return child
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
    fun appendChild(parent: Element, ns: XmlNamespace, name: String, textValue: String): Element {
        val child = parent.ownerDocument.createElementNS(ns.uri, name)
        child.prefix = ns.prefix
        child.textContent = textValue
        parent.appendChild(child)
        return child
    }

    /**
     * Declares XML namespaces on an Element by setting the xmlns:prefix attributes.
     *
     * @param element    the Element to declare namespaces on
     * @param namespaces the namespaces to declare
     */
    fun declareNamespaces(element: Element, vararg namespaces: XmlNamespace) {
        for (ns in namespaces) {
            element.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:" + ns.prefix, ns.uri)
        }
    }

    /**
     * Walks the subtree of `source` and, for every `xsi:type` attribute whose value is a
     * prefixed QName (e.g. `xs:string`), ensures that prefix is declared on `target`.
     *
     *
     * This is needed when importing nodes via [Document.importNode]: the import does
     * not carry over ancestor namespace declarations, so a prefix like `xs` that was declared on
     * a grandparent element in the source document becomes undeclared in the new document.  Only
     * `xsi:type` prefixes are copied — not all ancestor namespaces — to avoid accidentally
     * changing other namespace context that would invalidate an existing XML signature.
     *
     * @param source the node whose subtree to inspect (using source's namespace context for lookup)
     * @param target the element to declare missing prefixes on
     */
    fun copyXsiTypePrefixes(source: Node, target: Element) {
        if (source.nodeType == Node.ELEMENT_NODE) {
            val element = source as Element
            val xsiType = element.getAttributeNS("http://www.w3.org/2001/XMLSchema-instance", "type")
            if (xsiType.contains(":")) {
                val prefix = xsiType.substring(0, xsiType.indexOf(':'))
                if (target.getAttributeNodeNS("http://www.w3.org/2000/xmlns/", prefix) == null) {
                    val uri = element.lookupNamespaceURI(prefix)
                    if (uri != null) {
                        target.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:$prefix", uri)
                    }
                }
            }
        }
        val children = source.childNodes
        for (i in 0..<children.length) {
            copyXsiTypePrefixes(children.item(i), target)
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
    fun setIdAttribute(elm: Element, ns: XmlNamespace, name: String, value: String) {
        elm.setAttributeNS(ns.uri, ns.prefix + ":" + name, value)
        elm.setIdAttributeNS(ns.uri, name, true)
    }

    /**
     * Sets a namespaced attribute on an Element.
     *
     * @param elm       the Element to set the attribute on
     * @param ns        the XML namespace
     * @param localName the local attribute name
     * @param value     the attribute value
     */
    fun setAttribute(elm: Element, ns: XmlNamespace, localName: String, value: String) {
        elm.setAttributeNS(ns.uri, ns.prefix + ":" + localName, value)
    }
}
