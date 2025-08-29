package dk.sundhedsdatastyrelsen.ncpeh.client;

import dk.sundhedsdatastyrelsen.ncpeh.authentication.AuthenticationException;
import dk.sundhedsdatastyrelsen.ncpeh.authentication.CertificateAndKey;
import dk.sundhedsdatastyrelsen.ncpeh.authentication.XPathWrapper;
import dk.sundhedsdatastyrelsen.ncpeh.authentication.XmlNamespace;
import dk.sundhedsdatastyrelsen.ncpeh.authentication.XmlUtils;
import org.slf4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateEncodingException;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NspClientDgws {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(NspClientDgws.class);
    private static final XPathWrapper xpath = new XPathWrapper(XmlNamespace.SOAP, XmlNamespace.WST);

    // TODO validate these values are what they should be
    private static final String ID_CARD_CVR = "33257872";
    private static final String ID_CARD_ISSUER = "NCPeH-DK";
    private static final String ID_CARD_IT_PROVIDER = "Service Consumer Test";
    private static final String ID_CARD_CARE_PROVIDER_NAME = "Sundhedsdatastyrelsen";

    /**
     * Send a SOAP request to an NSP service.
     */
    public static Element request(URI uri, Element soapBody, String soapAction, NspDgwsIdentity caller, Element... extraHeaders) throws Exception {
        var idCard = getSignedOrganizationCard(
            caller.stsUri(),
            caller.systemCertificate(),
            Instant.now().truncatedTo(ChronoUnit.SECONDS));
        var envelope = makeRequest(soapBody, idCard, Instant.now().truncatedTo(ChronoUnit.SECONDS), extraHeaders);
        var requestString = XmlUtils.writeDocumentToString(envelope);
        try (var httpClient = HttpClient.newBuilder().version(HttpClient.Version.HTTP_1_1).build()) {
            var request = HttpRequest.newBuilder(uri)
                .header("Content-Type", "text/xml;charset=utf-8")
                .header("SOAPAction", soapAction)
                .POST(HttpRequest.BodyPublishers.ofString(requestString))
                .build();
            var res = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            var fullText = res.body();

            // Check if response is MIME multipart. Some services (DDV is one) return as multipart.
            if (fullText.contains("--uuid:")) {
                fullText = extractSoapFromMime(fullText);
            }

            var responseDoc = XmlUtils.parse(fullText);
            if (res.statusCode() >= 400) {
                throw new NspClientException(String.format("Request failed with message: %s", xpath.evalString("/soap:Envelope/soap:Body/soap:Fault/faultstring", responseDoc)));
            }
            return xpath.evalEl("/soap:Envelope/soap:Body/*[1]", responseDoc);
        }
    }

    /**
     * Extract SOAP message from MIME multipart response.
     */
    private static String extractSoapFromMime(String mimeResponse) {
        // Find the boundary marker
        Pattern boundaryPattern = Pattern.compile("--(uuid:[^\\r\\n]+)");
        Matcher boundaryMatcher = boundaryPattern.matcher(mimeResponse);
        if (!boundaryMatcher.find()) {
            throw new NspClientException("Could not find MIME boundary in response");
        }
        String boundary = boundaryMatcher.group(1);

        // Extract content between boundaries
        Pattern contentPattern = Pattern.compile(
            "--" + Pattern.quote(boundary) + "\\r?\\n" +
                "([\\s\\S]*?)" +
                "--" + Pattern.quote(boundary) + "--",
            Pattern.MULTILINE
        );

        Matcher contentMatcher = contentPattern.matcher(mimeResponse);
        if (contentMatcher.find()) {
            String part = contentMatcher.group(1);
            // Skip headers and get content after double newline
            int contentStart = part.indexOf("\r\n\r\n");
            if (contentStart == -1) {
                contentStart = part.indexOf("\n\n");
            }
            if (contentStart == -1) {
                throw new NspClientException("Could not find content in MIME part");
            }
            return part.substring(contentStart + 2).trim();
        }

        throw new NspClientException("Could not extract content from MIME response");
    }

    private static Document makeRequest(Element soapBody, Element idCardAssertion, Instant now, Element[] extraHeaders) {
        // Structure

        var requestDocument = XmlUtils.newDocument();
        var envelope = XmlUtils.appendChild(requestDocument, XmlNamespace.SOAP, "Envelope");
        // The CPR service at least requires MEDCOM_LEGACY.
        XmlUtils.declareNamespaces(
            envelope, XmlNamespace.SOAP, XmlNamespace.DS, XmlNamespace.MEDCOM_LEGACY, XmlNamespace.SAML, XmlNamespace.SOSI,
            XmlNamespace.WSSE, XmlNamespace.WSU);
        var header = XmlUtils.appendChild(envelope, XmlNamespace.SOAP, "Header");
        var body = XmlUtils.appendChild(envelope, XmlNamespace.SOAP, "Body");

        // Add the passed in body.
        body.appendChild(requestDocument.importNode(soapBody, true));

        // Header
        // Security

        var security = XmlUtils.appendChild(header, XmlNamespace.WSSE, "Security");

        var ts = XmlUtils.appendChild(security, XmlNamespace.WSU, "Timestamp");
        XmlUtils.appendChild(ts, XmlNamespace.WSU, "Created", DateTimeFormatter.ISO_INSTANT.format(now));

        security.appendChild(requestDocument.importNode(idCardAssertion, true));

        // Add the medcom header
        var medcomHeader = XmlUtils.appendChild(header, XmlNamespace.MEDCOM_LEGACY, "Header");
        XmlUtils.appendChild(medcomHeader, XmlNamespace.MEDCOM_LEGACY, "SecurityLevel", "3");
        var medcomLinking = XmlUtils.appendChild(medcomHeader, XmlNamespace.MEDCOM_LEGACY, "Linking");
        var flowId = UUID.randomUUID();
        var messageId = UUID.randomUUID();
        XmlUtils.appendChild(medcomLinking, XmlNamespace.MEDCOM_LEGACY, "FlowID", messageId.toString());
        XmlUtils.appendChild(medcomLinking, XmlNamespace.MEDCOM_LEGACY, "MessageID", flowId.toString());
        XmlUtils.appendChild(medcomHeader, XmlNamespace.MEDCOM_LEGACY, "RequireNonRepudiationReceipt", "no");

        // Add any extra headers
        Arrays.stream(extraHeaders).map(eh -> requestDocument.importNode(eh, true)).forEach(header::appendChild);

        return requestDocument;
    }

    /// @hidden public to allow testing
    public static Element getSignedOrganizationCard(URI uri, CertificateAndKey certificate, Instant now) throws AuthenticationException, TransformerException, IOException, InterruptedException, XPathExpressionException, NoSuchAlgorithmException, CertificateEncodingException {
        // Structure and header

        var requestDocument = XmlUtils.newDocument();
        var envelope = XmlUtils.appendChild(requestDocument, XmlNamespace.SOAP, "Envelope");
        XmlUtils.declareNamespaces(
            envelope, XmlNamespace.SOAP, XmlNamespace.DS, XmlNamespace.MEDCOM, XmlNamespace.SAML, XmlNamespace.SOSI,
            XmlNamespace.WSA, XmlNamespace.WSSE, XmlNamespace.WST, XmlNamespace.WSU);
        var header = XmlUtils.appendChild(envelope, XmlNamespace.SOAP, "Header");
        var security = XmlUtils.appendChild(header, XmlNamespace.WSSE, "Security");
        var ts = XmlUtils.appendChild(security, XmlNamespace.WSU, "Timestamp");
        XmlUtils.appendChild(
            ts, XmlNamespace.WSU, "Created", OffsetDateTime.now().truncatedTo(ChronoUnit.SECONDS)
                .format(DateTimeFormatter.ISO_INSTANT));

        // Body. Start by setting up the assertion

        var body = XmlUtils.appendChild(envelope, XmlNamespace.SOAP, "Body");
        var requestSecurityToken = XmlUtils.appendChild(body, XmlNamespace.WST, "RequestSecurityToken");
        requestSecurityToken.setAttribute("Context", "www.sosi.dk");
        XmlUtils.appendChild(requestSecurityToken, XmlNamespace.WST, "TokenType", "urn:oasis:names:tc:SAML:2.0:assertion:");
        XmlUtils.appendChild(requestSecurityToken, XmlNamespace.WST, "RequestType", "http://schemas.xmlsoap.org/ws/2005/02/trust/Issue");
        var claims = XmlUtils.appendChild(requestSecurityToken, XmlNamespace.WST, "Claims");
        var assertion = XmlUtils.appendChild(claims, XmlNamespace.SAML, "Assertion");
        assertion.setAttribute("IssueInstant", DateTimeFormatter.ISO_INSTANT.format(now));
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
        conditions.setAttribute("NotBefore", DateTimeFormatter.ISO_INSTANT.format(now));
        conditions.setAttribute(
            "NotOnOrAfter", DateTimeFormatter.ISO_INSTANT.format(now.plusSeconds(
                // one day
                86400)));

        var idCardAttribute = XmlUtils.appendChild(assertion, XmlNamespace.SAML, "AttributeStatement");
        idCardAttribute.setAttribute("id", "IDCardData");
        // In Trifork's unsealed, it's just a random uuid.
        // And in seal, they use SecureRandom and generate a base64 encoded version of 16 bytes from that.
        appendSamlAttribute(idCardAttribute, "sosi:IDCardID", UUID.randomUUID().toString());
        appendSamlAttribute(idCardAttribute, "sosi:IDCardVersion", "1.0.1");
        appendSamlAttribute(idCardAttribute, "sosi:IDCardType", "system");
        appendSamlAttribute(idCardAttribute, "sosi:AuthenticationLevel", "3");
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
        var requestString = XmlUtils.writeDocumentToString(requestDocument);

        try (var httpClient = HttpClient.newBuilder().version(HttpClient.Version.HTTP_1_1).build()) {
            var request = HttpRequest.newBuilder(uri)
                .header("Content-Type", "application/soap+xml; charset=utf-8")
                .POST(HttpRequest.BodyPublishers.ofString(requestString))
                .build();
            var res = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            var fullText = res.body();
            var responseDoc = XmlUtils.parse(fullText);
            var faultCode = xpath.evalString("/soap:Envelope/soap:Body/soap:Fault/faultstring", responseDoc);
            if (faultCode != null && !faultCode.isEmpty()) {
                throw new NspClientException(String.format("Request failed with message: %s", faultCode));
            }
            if (res.statusCode() >= 400) {
                throw new NspClientException("An unknown error occurred while requesting an organization token for NSP");
            }
            return xpath.evalEl(
                "/soap:Envelope/soap:Body/wst:RequestSecurityTokenResponse/wst:RequestedSecurityToken/*[1]",
                responseDoc);
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
