package dk.sundhedsdatastyrelsen.ncpeh.authentication.idcard;

import dk.sundhedsdatastyrelsen.ncpeh.authentication.AuthenticationException;
import dk.sundhedsdatastyrelsen.ncpeh.authentication.CertificateAndKey;
import dk.sundhedsdatastyrelsen.ncpeh.authentication.XmlNamespace;
import dk.sundhedsdatastyrelsen.ncpeh.authentication.XmlUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateEncodingException;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

public class DgwsIdCardRequest {
    // TODO validate these values are what they should be
    private static final String ID_CARD_CVR = "33257872";
    private static final String ID_CARD_ISSUER = "NCPeH-DK";
    private static final String ID_CARD_IT_PROVIDER = "Service Consumer Test";
    private static final String ID_CARD_CARE_PROVIDER_NAME = "Sundhedsdatastyrelsen";
    private final Document soapBody;

    private DgwsIdCardRequest(Document soapBody) {this.soapBody = soapBody;}

    public Document soapBody() {
        return soapBody;
    }

    /// Create an organization ID card request for Sosi STS to exchange to an assertion we can use to call Nsp services
    /// that only need an organization identity.
    ///
    /// The request was built by looking at a request from Seal, a library published by sosi. That request can be found
    /// in the test-resources folder, with the name "seal-to-sts-organization-request.xml". It is helpful to follow
    /// along in that file when reading this one.
    public static DgwsIdCardRequest of(CertificateAndKey certificate, Instant now) throws AuthenticationException {
        // Structure and header

        var truncatedNow = now.truncatedTo(ChronoUnit.SECONDS);
        var requestDocument = XmlUtils.newDocument();
        var envelope = XmlUtils.appendChild(requestDocument, XmlNamespace.SOAP, "Envelope");
        XmlUtils.declareNamespaces(
            envelope, XmlNamespace.SOAP, XmlNamespace.DS, XmlNamespace.MEDCOM, XmlNamespace.SAML, XmlNamespace.SOSI,
            XmlNamespace.WSA, XmlNamespace.WSSE, XmlNamespace.WST, XmlNamespace.WSU);
        var header = XmlUtils.appendChild(envelope, XmlNamespace.SOAP, "Header");
        var security = XmlUtils.appendChild(header, XmlNamespace.WSSE, "Security");
        var ts = XmlUtils.appendChild(security, XmlNamespace.WSU, "Timestamp");
        XmlUtils.appendChild(ts, XmlNamespace.WSU, "Created", DateTimeFormatter.ISO_INSTANT.format(truncatedNow));

        // Body. Start by setting up the assertion

        var body = XmlUtils.appendChild(envelope, XmlNamespace.SOAP, "Body");
        var requestSecurityToken = XmlUtils.appendChild(body, XmlNamespace.WST, "RequestSecurityToken");
        requestSecurityToken.setAttribute("Context", "www.sosi.dk");
        XmlUtils.appendChild(requestSecurityToken, XmlNamespace.WST, "TokenType", "urn:oasis:names:tc:SAML:2.0:assertion:");
        XmlUtils.appendChild(requestSecurityToken, XmlNamespace.WST, "RequestType", "http://schemas.xmlsoap.org/ws/2005/02/trust/Issue");
        var claims = XmlUtils.appendChild(requestSecurityToken, XmlNamespace.WST, "Claims");
        var assertion = XmlUtils.appendChild(claims, XmlNamespace.SAML, "Assertion");
        assertion.setAttribute("IssueInstant", DateTimeFormatter.ISO_INSTANT.format(truncatedNow));
        assertion.setAttribute("Version", "2.0");
        assertion.setAttribute("id", "IDCard");
        assertion.setIdAttribute("id", true);

        XmlUtils.appendChild(assertion, XmlNamespace.SAML, "Issuer", ID_CARD_ISSUER);

        // subject
        var subject = XmlUtils.appendChild(assertion, XmlNamespace.SAML, "Subject");
        var nameId = XmlUtils.appendChild(subject, XmlNamespace.SAML, "NameID", ID_CARD_CVR);
        nameId.setAttribute("Format", "medcom:cvrnumber");
        var subjectConfirmation = XmlUtils.appendChild(subject, XmlNamespace.SAML, "SubjectConfirmation");
        XmlUtils.appendChild(subjectConfirmation, XmlNamespace.SAML, "ConfirmationMethod", "urn:oasis:names:tc:SAML:2.0:cm:holder-of-key");
        var confirmationData = XmlUtils.appendChild(subjectConfirmation, XmlNamespace.SAML, "SubjectConfirmationData");
        var keyInfo = XmlUtils.appendChild(confirmationData, XmlNamespace.DS, "KeyInfo");
        XmlUtils.appendChild(keyInfo, XmlNamespace.DS, "KeyName", "OCESSignature");

        var conditions = XmlUtils.appendChild(assertion, XmlNamespace.SAML, "Conditions");
        conditions.setAttribute("NotBefore", DateTimeFormatter.ISO_INSTANT.format(truncatedNow));
        conditions.setAttribute(
            "NotOnOrAfter",
            // one day
            DateTimeFormatter.ISO_INSTANT.format(truncatedNow.plusSeconds(86400)));

        var idCardAttribute = XmlUtils.appendChild(assertion, XmlNamespace.SAML, "AttributeStatement");
        idCardAttribute.setAttribute("id", "IDCardData");
        // In Trifork's unsealed, it's just a random uuid.
        // And in seal, they use SecureRandom and generate a base64 encoded version of 16 bytes from that.
        appendSamlAttribute(idCardAttribute, "sosi:IDCardID", UUID.randomUUID().toString());
        appendSamlAttribute(idCardAttribute, "sosi:IDCardVersion", "1.0.1");
        appendSamlAttribute(idCardAttribute, "sosi:IDCardType", "system");
        appendSamlAttribute(idCardAttribute, "sosi:AuthenticationLevel", "3");

        try {
            // Create a hash of the certificate.
            var certHash = MessageDigest.getInstance("SHA-256").digest(certificate.certificate().getEncoded());
            appendSamlAttribute(idCardAttribute, "sosi:OCESCertHash", Base64.getEncoder().encodeToString(certHash));

            var systemLogAttribute = XmlUtils.appendChild(assertion, XmlNamespace.SAML, "AttributeStatement");
            systemLogAttribute.setAttribute("id", "SystemLog");
            appendSamlAttribute(systemLogAttribute, "medcom:ITSystemName", ID_CARD_IT_PROVIDER);
            var cvrEl = appendSamlAttribute(systemLogAttribute, "medcom:CareProviderID", ID_CARD_CVR);
            cvrEl.setAttribute("NameFormat", "medcom:cvrnumber");
            appendSamlAttribute(systemLogAttribute, "medcom:CareProviderName", ID_CARD_CARE_PROVIDER_NAME);

            var issuer = XmlUtils.appendChild(requestSecurityToken, XmlNamespace.WST, "Issuer");
            XmlUtils.appendChild(issuer, XmlNamespace.WSA, "Address", ID_CARD_ISSUER);

            XmlUtils.sign(assertion, null, List.of("#IDCard"), certificate);
            return new DgwsIdCardRequest(requestDocument);
        } catch (NoSuchAlgorithmException | CertificateEncodingException e) {
            throw new AuthenticationException("Could not generate DGWS ID card request for STS.", e);
        }
    }

    /// @return the created attribute element
    private static Element appendSamlAttribute(Element el, String name, String value) {
        var att = XmlUtils.appendChild(el, XmlNamespace.SAML, "Attribute");
        att.setAttribute("Name", name);
        XmlUtils.appendChild(att, XmlNamespace.SAML, "AttributeValue", value);
        return att;
    }

}
