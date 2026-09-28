package dk.sundhedsdatastyrelsen.epportal.utils

import org.w3c.dom.Element
import org.w3c.dom.Node
import org.w3c.dom.NodeList
import javax.xml.namespace.NamespaceContext
import javax.xml.xpath.XPath
import javax.xml.xpath.XPathConstants
import javax.xml.xpath.XPathFactory

/**
 * Thread-safe convenience wrapper around javax.xml.XPath.
 *
 * Copied and kotlinised from the similar class in national-connector. XmlException is removed, since it existed only
 * to wrap checked exceptions.
 */
class XPathWrapper(namespaces: Map<String, String>) {
    private val nsCtx: NamespaceContext = object : NamespaceContext {
        override fun getNamespaceURI(prefix: String): String? =
            namespaces[prefix]

        override fun getPrefix(uri: String): String? =
            namespaces.entries.find { it.value == uri }?.key

        override fun getPrefixes(uri: String): Iterator<String> =
            namespaces.entries.filter { it.value == uri }.map { it.key }.iterator()
    }

    constructor(vararg namespaces: XmlNamespace) : this(
        namespaces.groupBy({ it.prefix!! }, { it.uri!! }).mapValues { it.value[0] }
    )

    private fun xpath(): XPath {
        // XPathFactory and XPath are both not thread-safe, so we create a new one for each invocation.
        // I've (HBG) benchmarked it against a more complicated optimized solution with ThreadLocals, and the
        // difference was very small (1-2%) for the individual XPathWrapper method invocations, and immeasurable in
        // a larger context (e.g. bootstrap token generation). So I'm going with this simple version which doesn't have
        // space leak issues. Note: XPathFactory.newInstance() is significantly slower because it uses reflection.
        return XPathFactory.newDefaultInstance().newXPath().apply { namespaceContext = nsCtx }
    }

    /**
     * Locates and evaluates the content of a node to a string. Trims the string after extraction.
     *
     * @param expression The XPath expression linking to the node to be evaluated
     * @param item       The XML object that needs to be traversed
     * @return The node value as a string (using the XPath function evaluate)
     */
    fun evalString(expression: String, item: Any): String =
        xpath().evaluate(expression, item).trim()

    /**
     * Locates and returns a node in an XML.
     *
     * @param expression The XPath expression describing the location of the node
     * @param item       The XML object that needs to be traversed
     * @return The node located
     */
    fun evalNode(expression: String, item: Any): Node? =
        xpath().evaluate(expression, item, XPathConstants.NODE) as Node?

    /**
     * Locates and returns a node, cast as an Element, in an XML.
     *
     * @param expression The XPath expression describing the location of the node
     * @param item       The XML object that needs to be traversed
     * @return The node located
     */
    fun evalElement(expression: String, item: Any): Element? =
        evalNode(expression, item) as Element?

    /**
     * Locates and return an XPath NodeSet as a List of Nodes
     *
     * @param expression The XPath expression describing the location of the nodes
     * @param item       The XML object that needs to be traversed
     * @return A NodeSet mapped to an unmodifiable list
     */
    fun evalNodes(expression: String, item: Any): List<Node> =
        (xpath().evaluate(expression, item, XPathConstants.NODESET) as NodeList)
            .let { nodeList -> (0..<nodeList.length).map { nodeList.item(it) } }

    /**
     * Locates and returns an XPath NodeSet, cast as Elements, as a List
     *
     * @param expression The XPath expression describing the location of the nodes
     * @param item       The XML object that needs to be traversed
     * @return A NodeSet mapped to an unmodifiable list
     * @throws ClassCastException if the expression selects a node that is not an Element
     */
    fun evalElements(expression: String, item: Any): List<Element> =
        evalNodes(expression, item).map { it as Element }

    /**
     * Locates and returns the text content values of an XPath Nodeset as a list of strings.
     * The text content is acquired using XPath Node objects getTextContent method.
     * The text content is trimmed.
     *
     * @param expression The XPath expression describing the location of the nodes
     * @param item       The XML object that needs to be traversed
     * @return A NodeSets text content as an unmodifiable list
     */
    fun evalStrings(expression: String, item: Any): List<String> =
        evalNodes(expression, item).map { it.textContent.trim() }
}
