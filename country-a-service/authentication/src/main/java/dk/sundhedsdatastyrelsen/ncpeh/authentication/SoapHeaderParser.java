package dk.sundhedsdatastyrelsen.ncpeh.authentication;

import lombok.extern.slf4j.Slf4j;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.namespace.NamespaceContext;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Slf4j
public class SoapHeaderParser {
    private static final String NS_SAML2 = "urn:oasis:names:tc:SAML:2.0:assertion";

    private SoapHeaderParser() {
    }

    public static Assertion parse(String soapHeaderXml) throws ParserConfigurationException, IOException, SAXException, XPathExpressionException {
        var builder = DocumentBuilderFactory.newDefaultNSInstance().newDocumentBuilder();
        var doc = builder.parse(new ByteArrayInputStream(soapHeaderXml.getBytes(StandardCharsets.UTF_8)));

        var xpath = xpath();

        Node assertionNode = (Node) xpath.evaluate("//saml2:Assertion", doc, XPathConstants.NODE);
        if (assertionNode == null) throw new IllegalArgumentException("No <saml2:Assertion> found");

        Element assertionEl = (Element) assertionNode;

        var ab = Assertion.builder()
            .id(assertionEl.getAttribute("ID"))
            .issueInstant(assertionEl.getAttribute("IssueInstant"))
            .version(assertionEl.getAttribute("Version"))
            .issuer(xpath.evaluate("saml2:Issuer", assertionNode));

        // Signature
        var signature = Assertion.Signature.builder()
            .signatureMethodAlgorithm(xpath.evaluate("//ds:SignatureMethod/@Algorithm", doc))
            .digestMethodAlgorithm(xpath.evaluate("//ds:DigestMethod/@Algorithm", doc))
            .digestValue(xpath.evaluate("//ds:DigestValue", doc))
            .signatureValue(xpath.evaluate("//ds:SignatureValue", doc))
            .certificate(xpath.evaluate("//ds:X509Certificate", doc))
            .build();
        ab.signature(signature);

        // Subject
        var subject = Assertion.Subject.builder()
            .nameIdFormat(xpath.evaluate("saml2:Subject/saml2:NameID/@Format", assertionNode))
            .nameIdValue(xpath.evaluate("saml2:Subject/saml2:NameID", assertionNode))
            .confirmationMethod(xpath.evaluate("saml2:Subject/saml2:SubjectConfirmation/@Method", assertionNode))
            .certificate(xpath.evaluate("//ds:X509Certificate", doc)) // Use same certificate for subject
            .build();
        ab.subject(subject);

        // Conditions
        var conditions = Assertion.Conditions.builder()
            .notBefore(xpath.evaluate("saml2:Conditions/@NotBefore", assertionNode))
            .notOnOrAfter(xpath.evaluate("saml2:Conditions/@NotOnOrAfter", assertionNode))
            .audience(xpath.evaluate("saml2:Conditions/saml2:AudienceRestriction/saml2:Audience", assertionNode))
            .build();
        ab.conditions(conditions);

        var attributeNodes = (NodeList) xpath.evaluate("saml2:AttributeStatement/saml2:Attribute", assertionNode, XPathConstants.NODESET);

        var attributes = new ArrayList<Assertion.Attribute>();
        for (int i = 0; i < attributeNodes.getLength(); i++) {
            var el = (Element) attributeNodes.item(i);
            var attribute = Assertion.Attribute.builder()
                .friendlyName(el.getAttribute("FriendlyName"))
                .name(el.getAttribute("Name"))
                .values(attributeValues(el))
                .build();
            attributes.add(attribute);
        }

        ab.attributes(attributes);

        return ab.build();
    }

    private static List<String> attributeValues(Element attribute) {
        List<String> values = new ArrayList<>();
        NodeList valueNodes = attribute.getElementsByTagNameNS(NS_SAML2, "AttributeValue");

        for (int j = 0; j < valueNodes.getLength(); j++) {
            Node valueNode = valueNodes.item(j);
            String value = valueNode.getTextContent();
            if (value != null && !value.isBlank()) {
                values.add(value.trim());
            }
        }
        return values;
    }

    private static XPath xpath() {
        var xPathFactory = XPathFactory.newInstance();
        var xPath = xPathFactory.newXPath();
        xPath.setNamespaceContext(new NamespaceContext() {
            private final Map<String, String> namespaces = Map.of(
                "soapenv", "http://www.w3.org/2003/05/soap-envelope",
                "wsse", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd",
                "saml2", NS_SAML2,
                "ds", "http://www.w3.org/2000/09/xmldsig#"

            );
            @Override
            public String getNamespaceURI(String prefix) {
                return namespaces.get(prefix);
            }

            @Override
            public String getPrefix(String uri) {
                for (Map.Entry<String, String> entry : namespaces.entrySet()) {
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
        return xPath;
    }

}
