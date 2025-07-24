package dk.sundhedsdatastyrelsen.ncpeh.authentication;

import lombok.extern.slf4j.Slf4j;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class SoapHeader {
    private static final XPathWrapper xpath = new XPathWrapper(
        XmlNamespaces.WSSE,
        XmlNamespaces.DS,
        XmlNamespaces.SAML);

    private SoapHeader() {}

    public static Element hcpAssertion(String soapHeader) throws AuthenticationException {
        var doc = XmlUtils.parse(soapHeader);
        try {
            return (Element) xpath.evalNode("//saml:Assertion", doc);
        } catch (XPathExpressionException e) {
            throw new AuthenticationException("Error when parsing HCP assertion", e);
        }
    }

    /**
     * Extract parameters for generating a bootstrap token from a european HCP assertion
     */
    public static BootstrapTokenParams fromHcpAssertion(Element hcpAssertion, X509Certificate certificate, String audience) throws AuthenticationException {
        try {
            var attrs = xpath.evalNodeSet("saml:AttributeStatement/saml:Attribute", hcpAssertion);
            List<BootstrapTokenParams.Attribute> attributes = new ArrayList<>();
            for (var a : attrs) {
                var attr = (Element) a;
                attributes.add(new BootstrapTokenParams.Attribute(
                    attr.getAttribute("Name"),
                    attr.getAttribute("FriendlyName"),
                    attributeValues(attr)
                ));
            }

            return BootstrapTokenParams.builderWithDefaults()
                .certificate(certificate)
                .audience(audience)
                .nameIdFormat(xpath.evalString("saml:Subject/saml:NameID/@Format", hcpAssertion))
                .nameId(xpath.evalString("saml:Subject/saml:NameID", hcpAssertion))
                .attributes(attributes)
                .build();
        } catch (XPathExpressionException e) {
            throw new AuthenticationException("Error when parsing HCP assertion", e);
        }
    }

    private static List<BootstrapTokenParams.AttributeValue> attributeValues(Element attr) throws XPathExpressionException {
            return xpath.evalNodeSet("saml:AttributeValue/* | saml:AttributeValue/text()[normalize-space()]", attr).stream()
                .map(node -> (BootstrapTokenParams.AttributeValue) (node.getNodeType() == Node.TEXT_NODE
                    ? new BootstrapTokenParams.AttributeValue.Text(node.getNodeValue().trim())
                    : new BootstrapTokenParams.AttributeValue.XmlNode(node)))
                .toList();
    }

    @Deprecated
    public static Assertion extractAssertion(Document soapHeader) throws AuthenticationException {
        try {
            var xpath = XmlUtils.xpath(
                XmlNamespaces.WSSE,
                XmlNamespaces.DS,
                XmlNamespaces.SAML
            );

            var assertionEl = (Element) xpath.evaluate("//saml:Assertion", soapHeader, XPathConstants.NODE);

            var ab = Assertion.builder()
                .id(assertionEl.getAttribute("ID"))
                .issueInstant(assertionEl.getAttribute("IssueInstant"))
                .version(assertionEl.getAttribute("Version"))
                .issuer(xpath.evaluate("saml:Issuer", assertionEl));

            // Signature
            var signature = Assertion.Signature.builder()
                .signatureMethodAlgorithm(xpath.evaluate("//ds:SignatureMethod/@Algorithm", soapHeader))
                .digestMethodAlgorithm(xpath.evaluate("//ds:DigestMethod/@Algorithm", soapHeader))
                .digestValue(xpath.evaluate("//ds:DigestValue", soapHeader))
                .signatureValue(xpath.evaluate("//ds:SignatureValue", soapHeader))
                .certificate(xpath.evaluate("//ds:X509Certificate", soapHeader))
                .build();
            ab.signature(signature);

            // Subject
            var subject = Assertion.Subject.builder()
                .nameIdFormat(xpath.evaluate("saml:Subject/saml:NameID/@Format", assertionEl))
                .nameIdValue(xpath.evaluate("saml:Subject/saml:NameID", assertionEl))
                .confirmationMethod(xpath.evaluate("saml:Subject/saml:SubjectConfirmation/@Method", assertionEl))
                .certificate(xpath.evaluate("//ds:X509Certificate", soapHeader)) // Use same certificate for subject
                .build();
            ab.subject(subject);

            // Conditions
            var conditions = Assertion.Conditions.builder()
                .notBefore(xpath.evaluate("saml:Conditions/@NotBefore", assertionEl))
                .notOnOrAfter(xpath.evaluate("saml:Conditions/@NotOnOrAfter", assertionEl))
                .audience(xpath.evaluate("saml:Conditions/saml:AudienceRestriction/saml:Audience", assertionEl))
                .build();
            ab.conditions(conditions);

            var attributeNodes = (NodeList) xpath.evaluate("saml:AttributeStatement/saml:Attribute", assertionEl, XPathConstants.NODESET);

            var attributes = new ArrayList<Assertion.Attribute>();
            for (var i = 0; i < attributeNodes.getLength(); i++) {
                var el = (Element) attributeNodes.item(i);
                var attribute = Assertion.Attribute.builder()
                    .friendlyName(el.getAttribute("FriendlyName"))
                    .name(el.getAttribute("Name"))
                    .values(attributeValuesOld(el))
                    .build();
                attributes.add(attribute);
            }

            ab.attributes(attributes);

            return ab.build();
        } catch (XPathExpressionException e) {
            throw new AuthenticationException("Error when parsing SOAP header", e);
        }
    }

    @Deprecated
    private static List<String> attributeValuesOld(Element attribute) {
        List<String> values = new ArrayList<>();
        var valueNodes = attribute.getElementsByTagNameNS(XmlNamespaces.SAML.uri(), "AttributeValue");

        for (var j = 0; j < valueNodes.getLength(); j++) {
            var valueNode = valueNodes.item(j);
            var value = valueNode.getTextContent();
            if (value != null && !value.isBlank()) {
                values.add(value.trim());
            }
        }
        return values;
    }
}
