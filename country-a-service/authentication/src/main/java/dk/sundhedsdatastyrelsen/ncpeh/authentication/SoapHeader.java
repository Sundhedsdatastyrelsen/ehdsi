package dk.sundhedsdatastyrelsen.ncpeh.authentication;

import lombok.extern.slf4j.Slf4j;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
public class SoapHeader {
    private static final String NS_SAML2 = "urn:oasis:names:tc:SAML:2.0:assertion";

    private SoapHeader() {
    }

    public static Assertion extractAssertion(Document soapHeader) throws AuthenticationException {
        try {
            var xpath = XmlUtils.xpath(Map.of(
                "soapenv", "http://www.w3.org/2003/05/soap-envelope",
                "wsse", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd",
                "saml2", NS_SAML2,
                "ds", "http://www.w3.org/2000/09/xmldsig#"
            ));

            var assertionEl = (Element) xpath.evaluate("//saml2:Assertion", soapHeader, XPathConstants.NODE);

            var ab = Assertion.builder()
                .id(assertionEl.getAttribute("ID"))
                .issueInstant(assertionEl.getAttribute("IssueInstant"))
                .version(assertionEl.getAttribute("Version"))
                .issuer(xpath.evaluate("saml2:Issuer", assertionEl));

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
                .nameIdFormat(xpath.evaluate("saml2:Subject/saml2:NameID/@Format", assertionEl))
                .nameIdValue(xpath.evaluate("saml2:Subject/saml2:NameID", assertionEl))
                .confirmationMethod(xpath.evaluate("saml2:Subject/saml2:SubjectConfirmation/@Method", assertionEl))
                .certificate(xpath.evaluate("//ds:X509Certificate", soapHeader)) // Use same certificate for subject
                .build();
            ab.subject(subject);

            // Conditions
            var conditions = Assertion.Conditions.builder()
                .notBefore(xpath.evaluate("saml2:Conditions/@NotBefore", assertionEl))
                .notOnOrAfter(xpath.evaluate("saml2:Conditions/@NotOnOrAfter", assertionEl))
                .audience(xpath.evaluate("saml2:Conditions/saml2:AudienceRestriction/saml2:Audience", assertionEl))
                .build();
            ab.conditions(conditions);

            var attributeNodes = (NodeList) xpath.evaluate("saml2:AttributeStatement/saml2:Attribute", assertionEl, XPathConstants.NODESET);

            var attributes = new ArrayList<Assertion.Attribute>();
            for (var i = 0; i < attributeNodes.getLength(); i++) {
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
        } catch (XPathExpressionException e) {
            throw new AuthenticationException("Error when parsing SOAP header", e);
        }
    }

    private static List<String> attributeValues(Element attribute) {
        List<String> values = new ArrayList<>();
        var valueNodes = attribute.getElementsByTagNameNS(NS_SAML2, "AttributeValue");

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
