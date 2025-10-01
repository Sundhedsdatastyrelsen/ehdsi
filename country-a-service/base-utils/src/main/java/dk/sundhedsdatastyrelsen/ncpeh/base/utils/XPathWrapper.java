package dk.sundhedsdatastyrelsen.ncpeh.base.utils;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.namespace.NamespaceContext;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Thread-safe convenience wrapper around javax.xml.XPath.
 */
public class XPathWrapper {
    private final NamespaceContext nsCtx;

    public XPathWrapper(Map<String, String> namespaces) {
        this.nsCtx = new NamespaceContext() {
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
        };
    }

    public XPathWrapper(XmlNamespace... namespaces) {
        this(Arrays.stream(namespaces)
            .collect(Collectors.toMap(XmlNamespace::prefix, XmlNamespace::uri)));
    }

    private XPath xpath() {
        // XPathFactory and XPath are both not thread-safe, so we create a new one for each invocation.
        // I've (HBG) benchmarked it against a more complicated optimized solution with ThreadLocals, and the
        // difference was very small (1-2%) for the individual XPathWrapper method invocations, and immeasurable in
        // a larger context (e.g. bootstrap token generation). So I'm going with this simple version which doesn't have
        // space leak issues. Note: XPathFactory.newInstance() is significantly slower because it uses reflection.
        var xpath = XPathFactory.newDefaultInstance().newXPath();
        xpath.setNamespaceContext(this.nsCtx);
        return xpath;
    }

    /**
     * Locates and evaluates the content of a node to a string. Trims the string after extraction.
     * @param expression The XPath expression linking to the node to be evaluated
     * @param item The XML object that needs to be traversed
     * @return The node value as a string (using the XPath function evaluate)
     * @throws XPathExpressionException if the expression cannot be evaluated by XPath
     */
    public String evalString(String expression, Object item) throws XPathExpressionException {
        return xpath().evaluate(expression, item).trim();
    }

    /**
     * Locates and returns a node in an XML.
     * @param expression The XPath expression describing the location of the node
     * @param item The XML object that needs to be traversed
     * @return The node located
     * @throws XPathExpressionException if the expression cannot be evaluated by XPath
     */
    public Node evalNode(String expression, Object item) throws XPathExpressionException {
        return (Node) xpath().evaluate(expression, item, XPathConstants.NODE);
    }

    /**
     * Locates and returns a node, cast as an Element, in an XML.
     * @param expression The XPath expression describing the location of the node
     * @param item The XML object that needs to be traversed
     * @return The node located
     * @throws XPathExpressionException if the expression cannot be evaluated by XPath
     */
    public Element evalElement(String expression, Object item) throws XPathExpressionException {
        return (Element) evalNode(expression, item);
    }

    /**
     * Locates and return an XPath NodeSet as a List of Nodes
     * @param expression The XPath expression describing the location of the nodes
     * @param item The XML object that needs to be traversed
     * @return A NodeSet mapped to an unmodifiable list
     * @throws XPathExpressionException if the expression cannot be evaluated by XPath
     */
    public List<Node> evalNodes(String expression, Object item) throws XPathExpressionException {
        var nodeList = (NodeList) xpath().evaluate(expression, item, XPathConstants.NODESET);
        return IntStream.range(0, nodeList.getLength())
            .mapToObj(nodeList::item)
            .toList();
    }

    /**
     * Locates and returns the text content values of an XPath Nodeset as a list of strings.
     * The text content is acquired using XPath Node objects getTextContent method.
     * The text content is trimmed.
     * @param expression The XPath expression describing the location of the nodes
     * @param item The XML object that needs to be traversed
     * @return A NodeSets text content as an unmodifiable list
     * @throws XPathExpressionException if the expression cannot be evaluated by XPath
     */
    public List<String> evalStrings(String expression, Object item) throws XPathExpressionException {
        return evalNodes(expression, item).stream().map(Node::getTextContent).map(String::trim).toList();
    }
}
