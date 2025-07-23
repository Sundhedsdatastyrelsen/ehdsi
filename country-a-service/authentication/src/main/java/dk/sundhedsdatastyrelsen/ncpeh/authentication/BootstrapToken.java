package dk.sundhedsdatastyrelsen.ncpeh.authentication;

import lombok.NonNull;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.security.PrivateKey;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.UUID;

public class BootstrapToken {
    private static final String ISSUER_URI = "https://ehdsi-idp.testkald.nspop.dk";

    private final X509Certificate certificate;
    private final PrivateKey privateKey;
    private final Clock clock;

    public BootstrapToken(@NonNull X509Certificate certificate, @NonNull PrivateKey privateKey, @NonNull Clock clock) {
        this.certificate = certificate;
        this.privateKey = privateKey;
        this.clock = clock;
    }

    public BootstrapToken(@NonNull X509Certificate certificate, @NonNull PrivateKey privateKey) {
        this(certificate, privateKey, Clock.systemUTC());
    }

    public static final DateTimeFormatter ISO_WITH_MILLIS_FORMATTER = DateTimeFormatter
        .ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").withZone(ZoneId.of("UTC"));

    public static final DateTimeFormatter ISO_WITHOUT_MILLIS_FORMATTER = DateTimeFormatter
        .ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'").withZone(ZoneId.of("UTC"));

    public static Element appendChild(Document parent, XmlNamespaces nsPrefix, String name) {
        var child = parent.createElementNS(nsPrefix.uri(), name);
        child.setPrefix(nsPrefix.prefix());
        parent.appendChild(child);
        return child;
    }

    public static Element appendChild(Element parent, XmlNamespaces nsPrefix, String name) {
        var child = parent.getOwnerDocument().createElementNS(nsPrefix.uri(), name);
        child.setPrefix(nsPrefix.prefix());
        parent.appendChild(child);
        return child;
    }

    public static Element appendChild(Element parent, XmlNamespaces nsPrefix, String name, String textValue) {
        var child = parent.getOwnerDocument().createElementNS(nsPrefix.uri(), name);
        child.setPrefix(nsPrefix.prefix());
        child.setTextContent(textValue);
        parent.appendChild(child);
        return child;
    }

    public static Element appendChild(Element parent, String nsUrl, String name, String textValue) {
        var child = parent.getOwnerDocument().createElementNS(nsUrl, name);
        child.setTextContent(textValue);
        parent.appendChild(child);
        return child;
    }

    public static void declareNamespaces(Element element, XmlNamespaces... namespaces) {
        for (var ns : namespaces) {
            element.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:" + ns.prefix(), ns.uri());
        }
    }

    public static void setAttribute(Element elm, XmlNamespaces ns, String name, String value) {
        elm.setAttributeNS(ns.uri(), ns.prefix() + ":" + name, value);
    }

    public static void setIdAttribute(Element elm, XmlNamespaces ns, String name, String value) {
        setAttribute(elm, ns, name, value);
        elm.setIdAttributeNS(ns.uri(), name, true);
    }

    public Element createBootstrapToken(String audience, String nameId) throws ParserConfigurationException, CertificateEncodingException {
        var now = Instant.now(clock);

        var dbf = DocumentBuilderFactory.newDefaultNSInstance();
        var doc = dbf.newDocumentBuilder().newDocument();

        var assertion = appendChild(doc, XmlNamespaces.SAML, "Assertion");
        var assertionId = "_" + UUID.randomUUID();
        declareNamespaces(assertion, XmlNamespaces.XSD, XmlNamespaces.XSI);
        assertion.setAttribute("IssueInstant", ISO_WITHOUT_MILLIS_FORMATTER.format(now));
        assertion.setAttribute("Version", "2.0");
        assertion.setAttribute("ID", assertionId);
        assertion.setIdAttribute("ID", true);

        appendChild(assertion, XmlNamespaces.SAML, "Issuer", ISSUER_URI);
        var subject = appendChild(assertion, XmlNamespaces.SAML, "Subject");

        var nameID = appendChild(subject, XmlNamespaces.SAML, "NameID", nameId);
        nameID.setAttribute("Format", "urn:oasis:names:tc:SAML:1.1:nameid-format:unspecified");

        var subjectConfirmation = appendChild(subject, XmlNamespaces.SAML, "SubjectConfirmation");
        subjectConfirmation.setAttribute("Method", "urn:oasis:names:tc:SAML:2.0:cm:holder-of-key");
        var subjectConfirmationData = appendChild(subjectConfirmation, XmlNamespaces.SAML, "SubjectConfirmationData");
        var keyInfo = appendChild(subjectConfirmationData, XmlNamespaces.DS, "KeyInfo");
        var certB64 = Base64.getEncoder().encodeToString(certificate.getEncoded());
        appendChild(
            appendChild(keyInfo, XmlNamespaces.DS, "X509Data"),
            XmlNamespaces.DS,
            "X509Certificate",
            certB64);

        var conditions = appendChild(assertion, XmlNamespaces.SAML, "Conditions");
        conditions.setAttribute("NotBefore", ISO_WITHOUT_MILLIS_FORMATTER.format(now));
        conditions.setAttribute("NotOnOrAfter", ISO_WITHOUT_MILLIS_FORMATTER.format(now.plus(2, ChronoUnit.HOURS)));

        appendChild(
            appendChild(conditions, XmlNamespaces.SAML, "AudienceRestriction"),
            XmlNamespaces.SAML,
            "Audience",
            audience);

        return assertion;
    }

    public Element createBootstrapExchangeRequest(String audience, Element bootstrapToken)
        throws ParserConfigurationException {

        var dbf = DocumentBuilderFactory.newDefaultNSInstance();
        var doc = dbf.newDocumentBuilder().newDocument();

        var envelope = appendChild(doc, XmlNamespaces.SOAP, "Envelope");
        declareNamespaces(
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

        var soapHeader = appendChild(envelope, XmlNamespaces.SOAP, "Header");
        var action = appendChild(soapHeader, XmlNamespaces.WSA, "Action", "http://docs.oasis-open.org/ws-sx/ws-trust/200512/RST/Issue");
        setIdAttribute(action, XmlNamespaces.WSU, "Id", "action");

        var messageID = appendChild(soapHeader, XmlNamespaces.WSA, "MessageID", "urn:uuid:" + UUID.randomUUID());
        setIdAttribute(messageID, XmlNamespaces.WSU, "Id", "messageID");

        var security = appendChild(soapHeader, XmlNamespaces.WSSE, "Security");
        setIdAttribute(security, XmlNamespaces.WSU, "Id", "security");
        security.setAttribute("mustUnderstand", "1");

        var timestamp = appendChild(security, XmlNamespaces.WSU, "Timestamp");
        setIdAttribute(timestamp, XmlNamespaces.WSU, "Id", "ts");
        appendChild(timestamp, XmlNamespaces.WSU, "Created", ISO_WITH_MILLIS_FORMATTER.format(Instant.now(clock)));

        var soapBody = appendChild(envelope, XmlNamespaces.SOAP, "Body");
        setIdAttribute(soapBody, XmlNamespaces.WSU, "Id", "body");

        var requestSecurityToken = appendChild(soapBody, XmlNamespaces.WST13, "RequestSecurityToken");
        requestSecurityToken.setAttribute("Context", "urn:uuid:" + UUID.randomUUID());
        appendChild(requestSecurityToken, XmlNamespaces.WST13, "TokenType", "http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLV2.0");
        appendChild(requestSecurityToken, XmlNamespaces.WST13, "RequestType", "http://docs.oasis-open.org/ws-sx/ws-trust/200512/Issue");

        var actAs = appendChild(requestSecurityToken, XmlNamespaces.WST14, "ActAs");
        actAs.appendChild(doc.importNode(bootstrapToken, true));

        appendChild(
            appendChild(
                appendChild(requestSecurityToken, XmlNamespaces.WSP, "AppliesTo"),
                XmlNamespaces.WSA, "EndpointReference"),
            XmlNamespaces.WSA, "Address", audience);

        return envelope;
    }
}
