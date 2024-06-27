package eu.europa.ec.sante.ehdsi.portal.util;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.AbstractList;
import java.util.Collections;
import java.util.List;
import java.util.RandomAccess;

public final class XMLUtil {
    private XMLUtil() {
    }

    public static List<Node> asList(NodeList nodeList) {
        return nodeList.getLength() == 0 ? Collections.emptyList() : new NodeListWrapper(nodeList);
    }

    public static class NodeListWrapper extends AbstractList<Node> implements RandomAccess {
        private final NodeList list;

        NodeListWrapper(NodeList l) {
            list = l;
        }

        public Node get(int index) {
            return list.item(index);
        }

        public int size() {
            return list.getLength();
        }
    }
}
