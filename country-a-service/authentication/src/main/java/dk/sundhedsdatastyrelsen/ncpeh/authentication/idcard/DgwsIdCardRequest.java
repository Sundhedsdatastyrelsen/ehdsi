package dk.sundhedsdatastyrelsen.ncpeh.authentication.idcard;

import dk.sundhedsdatastyrelsen.ncpeh.authentication.AuthenticationException;
import dk.sundhedsdatastyrelsen.ncpeh.authentication.CertificateAndKey;
import dk.sundhedsdatastyrelsen.ncpeh.authentication.CertificateUtils;
import dk.sundhedsdatastyrelsen.ncpeh.authentication.NspDgwsIdentity;
import dk.sundhedsdatastyrelsen.ncpeh.base.utils.XmlNamespace;
import dk.sundhedsdatastyrelsen.ncpeh.base.utils.XmlUtils;
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
import java.util.Map;
import java.util.UUID;

public class DgwsIdCardRequest {
    private final Document soapBody;

    public record Configuration(String cvr, String issuer, String itProvider, String careProviderName) {}

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
    ///
    /// @throws AuthenticationException if document creation or signing did not work
    public static DgwsIdCardRequest of(CertificateAndKey certificate, Instant now, Configuration configuration) {
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

        XmlUtils.appendChild(assertion, XmlNamespace.SAML, "Issuer", configuration.issuer());

        // subject
        var subject = XmlUtils.appendChild(assertion, XmlNamespace.SAML, "Subject");
        var nameId = XmlUtils.appendChild(subject, XmlNamespace.SAML, "NameID", configuration.cvr());
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
            //  Id (= navn) på det it-system, medarbejderen
            //benytter til Den Gode Webservice-kommunikation. Skal også angives i ”Name
            //ID”, når korttypen er et ”system”.
            appendSamlAttribute(systemLogAttribute, "medcom:ITSystemName", configuration.itProvider());
            var cvrEl = appendSamlAttribute(systemLogAttribute, "medcom:CareProviderID", configuration.cvr());
            cvrEl.setAttribute("NameFormat", "medcom:cvrnumber");
            appendSamlAttribute(systemLogAttribute, "medcom:CareProviderName", configuration.careProviderName);

            var issuer = XmlUtils.appendChild(requestSecurityToken, XmlNamespace.WST, "Issuer");
            XmlUtils.appendChild(issuer, XmlNamespace.WSA, "Address", configuration.issuer);

            CertificateUtils.signXml(assertion, null, List.of("#IDCard"), certificate);
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

    /// Should only be used in development and in tests.
    /// We are both the idp and the service consumer, so we sign twice.
    ///
    /// The request was built by looking at a request from Seal, a library published by sosi. That request can be found
    /// in the test-resources folder, with the name "seal-to-sts-person-request.xml". It is helpful to follow
    /// along in that file when reading this one.
    ///
    /// @throws AuthenticationException if document creation or signing fails
    public static DgwsIdCardRequest ofEmployeeIdentity(NspDgwsIdentity.ReplaceWithIdws identity, Instant now, Configuration configuration) {
        var certificate = identity.systemCertificate();

        var truncatedNow = now.truncatedTo(ChronoUnit.SECONDS);
        var document = XmlUtils.newDocument();
        var envelope = XmlUtils.appendChild(document, XmlNamespace.SOAP, "Envelope");
        XmlUtils.declareNamespaces(
            // In the example, wst refers to what we call wst13.
            envelope, XmlNamespace.SOAP, XmlNamespace.DS, XmlNamespace.SAML,
            XmlNamespace.WSA, XmlNamespace.WSSE, XmlNamespace.WST13, XmlNamespace.WSU,
            XmlNamespace.WST14, XmlNamespace.WSP, XmlNamespace.AUTH, XmlNamespace.XSI
        );

        var header = XmlUtils.appendChild(envelope, XmlNamespace.SOAP, "Header");
        var action = XmlUtils.appendChild(header, XmlNamespace.WSA, "Action", "http://docs.oasis-open.org/ws-sx/ws-trust/200512/RST/Issue");
        XmlUtils.setIdAttribute(action, XmlNamespace.WSU, "Id", "action");
        var messageId = XmlUtils.appendChild(header, XmlNamespace.WSA, "MessageID", "urn:uuid:" + UUID.randomUUID());
        XmlUtils.setIdAttribute(messageId, XmlNamespace.WSU, "Id", "messageId");
        var security = XmlUtils.appendChild(header, XmlNamespace.WSSE, "Security");
        security.setAttribute("mustUnderstand", "1");
        XmlUtils.setIdAttribute(security, XmlNamespace.WSU, "Id", "security");
        var ts = XmlUtils.appendChild(security, XmlNamespace.WSU, "Timestamp");
        XmlUtils.setIdAttribute(ts, XmlNamespace.WSU, "Id", "ts");
        XmlUtils.appendChild(
            ts, XmlNamespace.WSU, "Created", DateTimeFormatter.ISO_INSTANT.format(truncatedNow));

        var body = XmlUtils.appendChild(envelope, XmlNamespace.SOAP, "Body");
        XmlUtils.setIdAttribute(body, XmlNamespace.WSU, "Id", "body");
        try {
            populateEmployeeBody(body, identity, truncatedNow, configuration);

            CertificateUtils.signXml(security, null, List.of("#body", "#ts", "#messageId", "#action"), certificate);
//            System.out.println(XmlUtils.writeDocumentToString(document));
            return new DgwsIdCardRequest(document);
        } catch (CertificateEncodingException | AuthenticationException e) {
            throw new AuthenticationException("Could not generate personal DGWS request.", e);
        }
    }

    private static void populateEmployeeBody(Element body, NspDgwsIdentity.ReplaceWithIdws identity, Instant now, Configuration configuration) throws CertificateEncodingException {
        var subjectNameId = identity.nameId().toString();

        var requestSecurityToken = XmlUtils.appendChild(body, XmlNamespace.WST13, "RequestSecurityToken");
        requestSecurityToken.setAttribute("Context", "urn:uuid:" + UUID.randomUUID());

        XmlUtils.appendChild(requestSecurityToken, XmlNamespace.WST13, "TokenType", "http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLV2.0");
        XmlUtils.appendChild(requestSecurityToken, XmlNamespace.WST13, "RequestType", "http://docs.oasis-open.org/ws-sx/ws-trust/200512/Issue");

        // <ActAs>

        var actAs = XmlUtils.appendChild(requestSecurityToken, XmlNamespace.WST14, "ActAs");
        var assertion = XmlUtils.appendChild(actAs, XmlNamespace.SAML, "Assertion");
        assertion.setAttribute("ID", "act-as-assertion");
        assertion.setIdAttribute("ID", true);
        assertion.setAttribute("IssueInstant", DateTimeFormatter.ISO_INSTANT.format(now));
        assertion.setAttribute("Version", "2.0");

        XmlUtils.appendChild(assertion, XmlNamespace.SAML, "Issuer", "https://idp.test.nspop.dk");
        var subject = XmlUtils.appendChild(assertion, XmlNamespace.SAML, "Subject");
        var nameId = XmlUtils.appendChild(subject, XmlNamespace.SAML, "NameID", "urn:uuid:" + subjectNameId);
        nameId.setAttribute("Format", "urn:oasis:names:tc:SAML:2.0:nameid-format:persistent");
        var subjectConfirmation = XmlUtils.appendChild(subject, XmlNamespace.SAML, "SubjectConfirmation");
        subjectConfirmation.setAttribute("Method", "urn:oasis:names:tc:SAML:2.0:cm:holder-of-key");
        var scd = XmlUtils.appendChild(subjectConfirmation, XmlNamespace.SAML, "SubjectConfirmationData");

        XmlUtils.setAttribute(scd, XmlNamespace.XSI, "type", "saml:KeyInfoConfirmationDataType");
        XmlUtils.appendChild(
            XmlUtils.appendChild(
                XmlUtils.appendChild(scd, XmlNamespace.DS, "KeyInfo"),
                XmlNamespace.DS,
                "X509Data"),
            XmlNamespace.DS,
            "X509Certificate",
            // This certificate is the same as the header certificate in the example.
            Base64.getEncoder().encodeToString(identity.systemCertificate().certificate().getEncoded()));

        var conditions = XmlUtils.appendChild(assertion, XmlNamespace.SAML, "Conditions");
        conditions.setAttribute("NotOnOrAfter", DateTimeFormatter.ISO_INSTANT.format(now.plusSeconds(60)));
        XmlUtils.appendChild(XmlUtils.appendChild(conditions, XmlNamespace.SAML, "AudienceRestriction"), XmlNamespace.SAML, "Audience", "https://sts.sosi.dk/");

        var attributeStatement = XmlUtils.appendChild(assertion, XmlNamespace.SAML, "AttributeStatement");
        var attributes = List.of(
            Map.of("name", "https://data.gov.dk/model/core/specVersion", "value", "OIO-SAML-3.0"),
            Map.of("name", "https://healthcare.data.gov.dk/model/core/specVersion", "value", "OIO-SAML-H-3.0"),
            Map.of("name", "https://data.gov.dk/concept/core/nsis/loa", "value", "High"),
            Map.of("name", "https://data.gov.dk/model/core/eid/professional/uuid/persistent", "value", "urn:uuid:" + subjectNameId),
            Map.of("name", "https://data.gov.dk/model/core/eid/professional/cvr", "value", configuration.cvr()),
            Map.of("name", "https://data.gov.dk/model/core/eid/professional/orgName", "value", configuration.careProviderName()));
        for (var attribute : attributes) {
            var a = XmlUtils.appendChild(attributeStatement, XmlNamespace.SAML, "Attribute");
            a.setAttribute("Name", attribute.get("name"));
            a.setAttribute("NameFormat", "urn:oasis:names:tc:SAML:2.0:attrname-format:uri");
            var av = XmlUtils.appendChild(a, XmlNamespace.SAML, "AttributeValue", attribute.get("value"));
            XmlUtils.setAttribute(av, XmlNamespace.XSI, "type", "string");
        }

        // </ActAs>
        // <AppliesTo>

        XmlUtils.appendChild(
            XmlUtils.appendChild(
                XmlUtils.appendChild(requestSecurityToken, XmlNamespace.WSP, "AppliesTo"),
                XmlNamespace.WSA,
                "EndpointReference"),
            XmlNamespace.WSA,
            "Address",
            "https://audience.nspop.dk/dgws");

        // </AppliesTo>
        // <Claims>

        var claims = XmlUtils.appendChild(requestSecurityToken, XmlNamespace.WST13, "Claims");
        claims.setAttribute("Dialect", "http://docs.oasis-open.org/wsfed/authorization/200706/authclaims");
        var claim1 = XmlUtils.appendChild(claims, XmlNamespace.AUTH, "ClaimType");
        claim1.setAttribute("Uri", "medcom:ITSystemName");
        XmlUtils.appendChild(claim1, XmlNamespace.AUTH, "Value", configuration.itProvider());
        var claim2 = XmlUtils.appendChild(claims, XmlNamespace.AUTH, "ClaimType");
        claim2.setAttribute("Uri", "sosi:SubjectNameID");
        XmlUtils.appendChild(claim2, XmlNamespace.AUTH, "Value", "urn:uuid:" + subjectNameId);

        // </Claims>

        // This certificate is a different one in the example.
        CertificateUtils.signXml(assertion, subject, List.of("#act-as-assertion"), identity.idpCertificate());
    }
}
