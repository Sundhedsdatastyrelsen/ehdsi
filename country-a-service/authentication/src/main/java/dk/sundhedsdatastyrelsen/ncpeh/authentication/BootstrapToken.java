package dk.sundhedsdatastyrelsen.ncpeh.authentication;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import javax.xml.crypto.MarshalException;
import javax.xml.crypto.dsig.CanonicalizationMethod;
import javax.xml.crypto.dsig.DigestMethod;
import javax.xml.crypto.dsig.Reference;
import javax.xml.crypto.dsig.SignatureMethod;
import javax.xml.crypto.dsig.Transform;
import javax.xml.crypto.dsig.XMLSignatureException;
import javax.xml.crypto.dsig.XMLSignatureFactory;
import javax.xml.crypto.dsig.dom.DOMSignContext;
import javax.xml.crypto.dsig.spec.C14NMethodParameterSpec;
import javax.xml.crypto.dsig.spec.TransformParameterSpec;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateEncodingException;
import java.time.Clock;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

public class BootstrapToken {
    private static Clock clock = Clock.systemUTC();

    private BootstrapToken() {}

    static {
        // avoid <CR><LF> in signature values
        System.setProperty("com.sun.org.apache.xml.internal.security.ignoreLineBreaks", "true");
    }

    /**
     * Set clock. Only for test use!
     */
    protected static void setClock(Clock clock) {
        BootstrapToken.clock = clock;
    }

    /**
     * Create a OIO SAML bootstrap token XML element, meant for exchanging with an STS to get an IDWS token.
     */
    public static Element createBootstrapToken(BootstrapTokenParams bst, String issuer, CertificateAndKey idpCertificate) throws ParserConfigurationException, CertificateEncodingException, AuthenticationException {
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

        XmlUtils.appendChild(assertion, XmlNamespaces.SAML, "Issuer", issuer);
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

        for (var attr : bst.attributes()) {
            switch (attr) {
                case BootstrapTokenParams.SamlAttribute.Raw(var node): {
                    assertion.appendChild(doc.importNode(node, true));
                    break;
                }
                case BootstrapTokenParams.SamlAttribute.New(var name, var friendlyName, var vals): {
                    var attrEl = XmlUtils.appendChild(assertion, XmlNamespaces.SAML, "Attribute");
                    attrEl.setAttribute("FriendlyName", friendlyName);
                    attrEl.setAttribute("Name", name);
                    for (var val : vals) {
                        var valEl = XmlUtils.appendChild(attrEl, XmlNamespaces.SAML, "AttributeValue", val);
                        XmlUtils.setAttribute(valEl, XmlNamespaces.XSI, "type", "xs:string");
                    }
                    break;
                }
            }
        }

        signAssertion(assertion, idpCertificate);
        return assertion;
    }

    public static Document createBootstrapExchangeRequest(BootstrapTokenParams bst, String issuer, CertificateAndKey idpCertificate, CertificateAndKey spCertificate) throws CertificateEncodingException, ParserConfigurationException, AuthenticationException {
        return createBootstrapExchangeRequest(bst.audience(), createBootstrapToken(bst, issuer, idpCertificate), spCertificate);
    }

    /**
     * Create an unsigned bootstrap-to-IDWS SOAP request for the STS.
     *
     * @param audience       where do we want access (e.g. "https://fmk")
     * @param bootstrapToken the bootstrap token
     */
    public static Document createBootstrapExchangeRequest(String audience, Element bootstrapToken, CertificateAndKey spCertificate)
        throws ParserConfigurationException, AuthenticationException {

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

        signSoapRequest(security, spCertificate);
        return doc;
    }

    private static void signAssertion(Element assertion, CertificateAndKey idpCertificate) throws AuthenticationException {
        var id = "#" + assertion.getAttribute("ID");
        // "Subject" is the second child element, put the signature before that.
        var subject = assertion.getChildNodes().item(1);
        sign(assertion, subject, List.of(id), idpCertificate);
    }

    public static void signSoapRequest(Element security, CertificateAndKey certificate) throws AuthenticationException {
        sign(security, null, List.of("#body", "#ts", "#messageID", "#action"), certificate);
    }

    private static void sign(Element rootElement, Node nextSibling, List<String> referenceUris, CertificateAndKey certificate) throws AuthenticationException {
        try {
            var sigFactory = XMLSignatureFactory.getInstance("DOM");
            rootElement.getOwnerDocument().normalizeDocument();
            var transforms = List.of(
                sigFactory.newTransform(Transform.ENVELOPED, (TransformParameterSpec) null),
                sigFactory.newTransform(CanonicalizationMethod.EXCLUSIVE, (TransformParameterSpec) null));

            List<Reference> references = new ArrayList<>();
            for (var id : referenceUris) {
                references.add(sigFactory.newReference(
                    id,
                    sigFactory.newDigestMethod(DigestMethod.SHA1, null),
                    transforms,
                    null,
                    null));
            }

            var signedInfo = sigFactory.newSignedInfo(
                sigFactory.newCanonicalizationMethod(CanonicalizationMethod.EXCLUSIVE, (C14NMethodParameterSpec) null),
                sigFactory.newSignatureMethod(SignatureMethod.RSA_SHA1, null), // SOSI team has been notified that SHA1 should be updated. Probable change to SHA256.
                references
            );

            var keyInfoFactory = sigFactory.getKeyInfoFactory();
            var keyInfo = keyInfoFactory.newKeyInfo(List.of(keyInfoFactory.newX509Data(List.of(certificate.certificate()))));
//
//            var securityElement = (Element) soapEnvelope.getElementsByTagNameNS(
//                XmlNamespaces.WSSE.uri(),
//                "Security"
//            ).item(0);

            var signContext = new DOMSignContext(certificate.privateKey(), rootElement);
            if (nextSibling != null) {
                signContext.setNextSibling(nextSibling);
            }
            var signature = sigFactory.newXMLSignature(signedInfo, keyInfo);
            signature.sign(signContext);
        } catch (MarshalException | XMLSignatureException | NoSuchAlgorithmException | InvalidAlgorithmParameterException e) {
            throw new AuthenticationException("Signing failed", e);
        }
    }
}
