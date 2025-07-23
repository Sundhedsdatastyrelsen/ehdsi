package dk.sundhedsdatastyrelsen.ncpeh.authentication;

import lombok.NonNull;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.security.PrivateKey;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import java.time.Clock;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.UUID;

public class BootstrapToken {
    private static final String ISSUER_URI = "https://ehdsi-idp.testkald.nspop.dk";

    private final X509Certificate certificate;
    private final PrivateKey privateKey;
    private final Clock clock;

    public BootstrapToken(@NonNull X509Certificate certificate, PrivateKey privateKey, @NonNull Clock clock) {
        this.certificate = certificate;
        this.privateKey = privateKey;
        this.clock = clock;
    }

    public BootstrapToken(@NonNull X509Certificate certificate, PrivateKey privateKey) {
        this(certificate, privateKey, Clock.systemUTC());
    }

    /**
     * Create a OIO SAML bootstrap token, meant for exchanging with an STS to get an IDWS token.
     *
     * @param audience where do we want access (e.g. "https://fmk")
     * @param nameId   ??? TODO
     */
    public Element createBootstrapToken(String audience, String nameId) throws ParserConfigurationException, CertificateEncodingException {
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

        XmlUtils.appendChild(assertion, XmlNamespaces.SAML, "Issuer", ISSUER_URI);
        var subject = XmlUtils.appendChild(assertion, XmlNamespaces.SAML, "Subject");

        var nameID = XmlUtils.appendChild(subject, XmlNamespaces.SAML, "NameID", nameId);
        nameID.setAttribute("Format", "urn:oasis:names:tc:SAML:1.1:nameid-format:unspecified");

        var subjectConfirmation = XmlUtils.appendChild(subject, XmlNamespaces.SAML, "SubjectConfirmation");
        subjectConfirmation.setAttribute("Method", "urn:oasis:names:tc:SAML:2.0:cm:holder-of-key");
        var subjectConfirmationData = XmlUtils.appendChild(subjectConfirmation, XmlNamespaces.SAML, "SubjectConfirmationData");
        var keyInfo = XmlUtils.appendChild(subjectConfirmationData, XmlNamespaces.DS, "KeyInfo");
        var certB64 = Base64.getEncoder().encodeToString(certificate.getEncoded());
        XmlUtils.appendChild(
            XmlUtils.appendChild(keyInfo, XmlNamespaces.DS, "X509Data"),
            XmlNamespaces.DS,
            "X509Certificate",
            certB64);

        var conditions = XmlUtils.appendChild(assertion, XmlNamespaces.SAML, "Conditions");
        conditions.setAttribute(
            "NotBefore", DateTimeFormatter
                .ISO_INSTANT.format(now));
        conditions.setAttribute(
            "NotOnOrAfter", DateTimeFormatter
                .ISO_INSTANT.format(now.plus(2, ChronoUnit.HOURS)));

        XmlUtils.appendChild(
            XmlUtils.appendChild(conditions, XmlNamespaces.SAML, "AudienceRestriction"),
            XmlNamespaces.SAML,
            "Audience",
            audience);

        return assertion;
    }

    /**
     * Create an unsigned bootstrap-to-IDWS SOAP request for the STS.
     *
     * @param audience       where do we want access (e.g. "https://fmk")
     * @param bootstrapToken the bootstrap token
     */
    public Element createBootstrapExchangeRequest(String audience, Element bootstrapToken)
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
}
