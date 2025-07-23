package dk.sundhedsdatastyrelsen.ncpeh.authentication;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.security.cert.CertificateEncodingException;
import java.time.Clock;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.UUID;

public class BootstrapToken {
    private static Clock clock = Clock.systemUTC();

    private BootstrapToken() {}

    /**
     * Set clock. Only for test use!
     */
    protected static void setClock(Clock clock) {
        BootstrapToken.clock = clock;
    }

    /**
     * Create a OIO SAML bootstrap token XML element, meant for exchanging with an STS to get an IDWS token.
     */
    public static Element createBootstrapToken(BootstrapTokenParams bst) throws ParserConfigurationException, CertificateEncodingException {
        // we don't want higher resolution than seconds
        var now = Instant.now(clock).truncatedTo(ChronoUnit.SECONDS);

        var dbf = DocumentBuilderFactory.newDefaultNSInstance();
        var doc = dbf.newDocumentBuilder().newDocument();

        var assertion = XmlUtils.appendChild(doc, XmlNamespaces.SAML, "Assertion");
        var assertionId = "_" + UUID.randomUUID();
        XmlUtils.declareNamespaces(assertion, XmlNamespaces.XSD, XmlNamespaces.XSI);
        assertion.setAttribute("IssueInstant", DateTimeFormatter.ISO_INSTANT.format(now));
        assertion.setAttribute("Version", "2.0");
        assertion.setAttribute("ID", assertionId);
        assertion.setIdAttribute("ID", true);

        XmlUtils.appendChild(assertion, XmlNamespaces.SAML, "Issuer", bst.issuerUri());
        var subject = XmlUtils.appendChild(assertion, XmlNamespaces.SAML, "Subject");

        var nameID = XmlUtils.appendChild(subject, XmlNamespaces.SAML, "NameID", bst.nameId());
        nameID.setAttribute("Format", bst.nameIdFormat());

        var subjectConfirmation = XmlUtils.appendChild(subject, XmlNamespaces.SAML, "SubjectConfirmation");
        subjectConfirmation.setAttribute("Method", "urn:oasis:names:tc:SAML:2.0:cm:holder-of-key");
        var subjectConfirmationData = XmlUtils.appendChild(subjectConfirmation, XmlNamespaces.SAML, "SubjectConfirmationData");
        var keyInfo = XmlUtils.appendChild(subjectConfirmationData, XmlNamespaces.DS, "KeyInfo");
        var certB64 = Base64.getEncoder().encodeToString(bst.certificate().getEncoded());
        XmlUtils.appendChild(
            XmlUtils.appendChild(keyInfo, XmlNamespaces.DS, "X509Data"),
            XmlNamespaces.DS,
            "X509Certificate",
            certB64);

        var conditions = XmlUtils.appendChild(assertion, XmlNamespaces.SAML, "Conditions");
        conditions.setAttribute(
            "NotBefore", DateTimeFormatter.ISO_INSTANT.format(now));
        conditions.setAttribute(
            "NotOnOrAfter", DateTimeFormatter.ISO_INSTANT.format(now.plus(2, ChronoUnit.HOURS)));

        XmlUtils.appendChild(
            XmlUtils.appendChild(conditions, XmlNamespaces.SAML, "AudienceRestriction"),
            XmlNamespaces.SAML,
            "Audience",
            bst.audience());

        var authnStatement = XmlUtils.appendChild(assertion, XmlNamespaces.SAML, "AuthnStatement");
        authnStatement.setAttribute("AuthnInstant", DateTimeFormatter.ISO_INSTANT.format(now));
        authnStatement.setAttribute("SessionIndex", assertionId);
        XmlUtils.appendChild(
            XmlUtils.appendChild(authnStatement, XmlNamespaces.SAML, "AuthnContext"),
            XmlNamespaces.SAML,
            "AuthnContextClassRef",
            "urn:oasis:names:tc:SAML:2.0:ac:classes:X509");

        var attributeStatement = XmlUtils.appendChild(assertion, XmlNamespaces.SAML, "AttributeStatement");
        for (var attribute : bst.attributes()) {
            if (attribute.values().isEmpty()) {
                continue;
            }
            var attrEl = XmlUtils.appendChild(attributeStatement, XmlNamespaces.SAML, "Attribute");
            attrEl.setAttribute("FriendlyName", attribute.friendlyName());
            attrEl.setAttribute("Name", attribute.name());
            for (var v : attribute.values()) {
                var value = XmlUtils.appendChild(attrEl, XmlNamespaces.SAML, "AttributeValue");
                switch (v) {
                    case BootstrapTokenParams.AttributeValue.XmlNode(var node) -> value.appendChild(node);
                    case BootstrapTokenParams.AttributeValue.Text(var text) -> value.setTextContent(text);
                }
            }
        }

        return assertion;
    }

    public static Element createBootstrapExchangeRequest(BootstrapTokenParams bst) throws CertificateEncodingException, ParserConfigurationException {
        return createBootstrapExchangeRequest(bst.audience(), createBootstrapToken(bst));
    }

    /**
     * Create an unsigned bootstrap-to-IDWS SOAP request for the STS.
     *
     * @param audience       where do we want access (e.g. "https://fmk")
     * @param bootstrapToken the bootstrap token
     */
    public static Element createBootstrapExchangeRequest(String audience, Element bootstrapToken)
        throws ParserConfigurationException {

        var dbf = DocumentBuilderFactory.newDefaultNSInstance();
        var doc = dbf.newDocumentBuilder().newDocument();

        var envelope = XmlUtils.appendChild(doc, XmlNamespaces.SOAP, "Envelope");
        XmlUtils.declareNamespaces(
            envelope,
            XmlNamespaces.SOAP,
            XmlNamespaces.DS,
            XmlNamespaces.SAML,
            XmlNamespaces.XSI,
            XmlNamespaces.WSSE,
            XmlNamespaces.WST,
            XmlNamespaces.WST13,
            XmlNamespaces.WST14,
            XmlNamespaces.WSA,
            XmlNamespaces.WSP,
            XmlNamespaces.WSU);

        var soapHeader = XmlUtils.appendChild(envelope, XmlNamespaces.SOAP, "Header");
        var action = XmlUtils.appendChild(soapHeader, XmlNamespaces.WSA, "Action", "http://docs.oasis-open.org/ws-sx/ws-trust/200512/RST/Issue");
        XmlUtils.setIdAttribute(action, XmlNamespaces.WSU, "Id", "action");

        var messageID = XmlUtils.appendChild(soapHeader, XmlNamespaces.WSA, "MessageID", "urn:uuid:" + UUID.randomUUID());
        XmlUtils.setIdAttribute(messageID, XmlNamespaces.WSU, "Id", "messageID");

        var security = XmlUtils.appendChild(soapHeader, XmlNamespaces.WSSE, "Security");
        XmlUtils.setIdAttribute(security, XmlNamespaces.WSU, "Id", "security");
        security.setAttribute("mustUnderstand", "1");

        var timestamp = XmlUtils.appendChild(security, XmlNamespaces.WSU, "Timestamp");
        XmlUtils.setIdAttribute(timestamp, XmlNamespaces.WSU, "Id", "ts");
        XmlUtils.appendChild(timestamp, XmlNamespaces.WSU, "Created", DateTimeFormatter.ISO_INSTANT.format(Instant.now(clock)));

        var soapBody = XmlUtils.appendChild(envelope, XmlNamespaces.SOAP, "Body");
        XmlUtils.setIdAttribute(soapBody, XmlNamespaces.WSU, "Id", "body");

        var requestSecurityToken = XmlUtils.appendChild(soapBody, XmlNamespaces.WST13, "RequestSecurityToken");
        requestSecurityToken.setAttribute("Context", "urn:uuid:" + UUID.randomUUID());
        XmlUtils.appendChild(requestSecurityToken, XmlNamespaces.WST13, "TokenType", "http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLV2.0");
        XmlUtils.appendChild(requestSecurityToken, XmlNamespaces.WST13, "RequestType", "http://docs.oasis-open.org/ws-sx/ws-trust/200512/Issue");

        var actAs = XmlUtils.appendChild(requestSecurityToken, XmlNamespaces.WST14, "ActAs");
        actAs.appendChild(doc.importNode(bootstrapToken, true));

        XmlUtils.appendChild(
            XmlUtils.appendChild(
                XmlUtils.appendChild(requestSecurityToken, XmlNamespaces.WSP, "AppliesTo"),
                XmlNamespaces.WSA, "EndpointReference"),
            XmlNamespaces.WSA, "Address", audience);

        return envelope;
    }

    public static Document signRequest(Element soapEnvelope) {
        throw new IllegalArgumentException("not implemented yet");
    }
}
