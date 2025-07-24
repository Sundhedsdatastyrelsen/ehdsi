package dk.sundhedsdatastyrelsen.ncpeh.authentication;

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
    private final ThreadLocal<javax.xml.xpath.XPath> xpath;

    public XPathWrapper(Map<String, String> namespaces) {
        this.xpath = ThreadLocal.withInitial(() -> xpath(namespaces));
    }

    public XPathWrapper(XmlNamespaces... namespaces) {
        this(Arrays.stream(namespaces).collect(Collectors.toMap(XmlNamespaces::prefix, XmlNamespaces::uri)));
    }

    private static XPath xpath(Map<String, String> namespaces) {
        var xpath = XPathFactory.newDefaultInstance().newXPath();
        xpath.setNamespaceContext(new NamespaceContext() {
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
        return xpath;
    }

    public String evalString(String expression, Object item) throws XPathExpressionException {
        return xpath.get().evaluate(expression, item);
    }

    public Node evalNode(String expression, Object item) throws XPathExpressionException {
        return (Node) xpath.get().evaluate(expression, item, XPathConstants.NODE);
    }

    public List<Node> evalNodeSet(String expression, Object item) throws XPathExpressionException {
        var nodeList = (NodeList) xpath.get().evaluate(expression, item, XPathConstants.NODESET);
        return IntStream.range(0, nodeList.getLength())
            .mapToObj(nodeList::item)
            .toList();
    }

    public void cleanup() {
        xpath.remove();
    }
}
