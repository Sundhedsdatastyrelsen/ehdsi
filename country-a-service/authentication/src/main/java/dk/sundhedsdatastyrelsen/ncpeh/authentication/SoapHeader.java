package dk.sundhedsdatastyrelsen.ncpeh.authentication;

import lombok.extern.slf4j.Slf4j;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class SoapHeader {
    static {
        new XPathWrapper(
            XmlNamespaces.WSSE,
            XmlNamespaces.DS,
            XmlNamespaces.SAML);
    }

    private SoapHeader() {}

    /**
     * TODO: We need intermediate model of HCP, TRC, CountryCode
     * that we extract from soapHeader.
     * The SoapHeader.xml we have checked in right now *is not* relevant because it has no TRC, and would not become
     * an IDWS anyway (for now).
     *
     * From TRC we extract patient id.
     *
     * Country code we get from the certificate in HCP.
     *
     * So the flow will be:
     *
     *                           String soapHeader
     *                                   |
     *                                   |
     *                                   v
     *  IntermediateModel(Element hcpAssertion, Element trcAssertion, String countryOfTreatment)
     *             |
     *             |
     *             |
     *             v                   /------- X509Certificate cert
     *    BootstrapTokenParams  <------
     *                                 \------  String audience
     *             |
     *             |
     *             v
     *                                    /--- String issuer
     *        Element bootstrapToken  <---
     *                                    \--- CertificateAndKey idpCert
     */

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
