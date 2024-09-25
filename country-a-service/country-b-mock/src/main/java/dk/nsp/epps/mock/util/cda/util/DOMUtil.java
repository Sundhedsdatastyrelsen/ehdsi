package dk.nsp.epps.mock.util.cda.util;

import org.jdom2.input.DOMBuilder;

public class DOMUtil {

    private DOMUtil() {
    }

    public static org.jdom2.Document convertDOMtoJDOM(org.w3c.dom.Document input) {
        var builder = new DOMBuilder();
        return builder.build(input);
    }
}
