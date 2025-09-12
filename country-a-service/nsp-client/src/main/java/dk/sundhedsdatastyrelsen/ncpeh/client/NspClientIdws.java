package dk.sundhedsdatastyrelsen.ncpeh.client;

import dk.sundhedsdatastyrelsen.ncpeh.authentication.EuropeanHcpIdwsToken;
import dk.sundhedsdatastyrelsen.ncpeh.authentication.XmlNamespace;
import dk.sundhedsdatastyrelsen.ncpeh.shared.XPathWrapper;
import dk.sundhedsdatastyrelsen.ncpeh.shared.XmlUtils;
import org.apache.ws.security.WSSConfig;
import org.apache.ws.security.transform.STRTransform;
import org.slf4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.crypto.MarshalException;
import javax.xml.crypto.dom.DOMStructure;
import javax.xml.crypto.dsig.CanonicalizationMethod;
import javax.xml.crypto.dsig.DigestMethod;
import javax.xml.crypto.dsig.SignatureMethod;
import javax.xml.crypto.dsig.XMLSignatureException;
import javax.xml.crypto.dsig.XMLSignatureFactory;
import javax.xml.crypto.dsig.dom.DOMSignContext;
import javax.xml.crypto.dsig.spec.C14NMethodParameterSpec;
import javax.xml.crypto.dsig.spec.TransformParameterSpec;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

public class NspClientIdws {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(NspClientIdws.class);
    private static final XPathWrapper xpath = new XPathWrapper(XmlNamespace.SOAP);

    static {
        // WSS init is idempotent, but not threadsafe, so we make sure we only do it once.
        // Loading keys from pkcs12 keystores after this initialization has been called will fail in the version of
        // wss4j we're using (1.6.4). So we need to use jks. Once we can update wss4j, we should test if it works with
        // pkcs12.
        WSSConfig.init();
    }

    private NspClientIdws() {
    }

    /// Send an IDWS SOAP request to an NSP service.
    public static Element request(
        URI uri,
        Element soapBody,
        String soapAction,
        EuropeanHcpIdwsToken token,
        PrivateKey signingKey,
        Element... extraHeaders
    ) throws Exception {
        var envelope = createEnvelope(soapBody, soapAction, token, signingKey, extraHeaders);
        var reqBody = XmlUtils.writeDocumentToString(envelope);

        // Send it to the endpoint.
        try (var httpClient = HttpClient.newBuilder().build()) {
            var request = HttpRequest.newBuilder(uri)
                .header("Content-Type", "application/soap+xml; charset=utf-8")
                .header("SOAPAction", soapAction)
                .POST(HttpRequest.BodyPublishers.ofString(reqBody))
                .build();
            var res = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            var fullText = res.body();

            // Check if response is MIME multipart
            // Some NSP services respond with multipart. DDV is one of them.
            if (fullText.contains("--uuid:")) {
                fullText = extractSoapFromMime(fullText);
            }

            var responseDoc = XmlUtils.parse(fullText);

            if (res.statusCode() >= 400) {
                throw new NspClientException(String.format("Request failed with message: %s", xpath.evalString("/soap:Envelope/soap:Body/soap:Fault/faultstring", responseDoc)));
            }
            return (Element) xpath.evalEl("/soap:Envelope/soap:Body", responseDoc).getFirstChild();
        }
    }

    /// Wrap the body in an envelope and sign it.
    ///
    /// Quotes are references to the SOAP profile maintained by Digitaliseringsstyrelsen.
    ///
    /// [Root page for the profiles](https://digst.dk/it-loesninger/standarder/oio-identity-based-web-services-oio-idws/).
    ///
    /// [The specific profile](https://digst.dk/media/exmdt52l/oio-idws-soap-profile-v11.pdf).
    private static Document createEnvelope(Element soapBody, String soapAction, EuropeanHcpIdwsToken token, PrivateKey signingKey, Element[] extraHeaders) throws NoSuchAlgorithmException, InvalidAlgorithmParameterException, MarshalException, XMLSignatureException {
        // Structure

        var requestDocument = XmlUtils.newDocument();
        var envelope = XmlUtils.appendChild(requestDocument, XmlNamespace.SOAP, "Envelope");
        XmlUtils.declareNamespaces(
            envelope, XmlNamespace.SOAP, XmlNamespace.DS, XmlNamespace.SAML, XmlNamespace.WSSE, XmlNamespace.WSSE11, XmlNamespace.XSI,
            XmlNamespace.WSU, XmlNamespace.XSD, XmlNamespace.WSA);
        var header = XmlUtils.appendChild(envelope, XmlNamespace.SOAP, "Header");
        var body = XmlUtils.appendChild(envelope, XmlNamespace.SOAP, "Body");
        XmlUtils.setIdAttribute(body, XmlNamespace.WSU, "Id", "body");

        // Add the passed in body.
        body.appendChild(requestDocument.importNode(soapBody, true));

        // Header

        // Add the required header attributes.
        // I found out that these were required by calling an endpoint in FMK.
        var actionEl = XmlUtils.appendChild(header, XmlNamespace.WSA, "Action", soapAction);
        XmlUtils.setIdAttribute(actionEl, XmlNamespace.WSU, "Id", "action");

        var msgIdEl = XmlUtils.appendChild(header, XmlNamespace.WSA, "MessageID", UUID.randomUUID().toString());
        XmlUtils.setIdAttribute(msgIdEl, XmlNamespace.WSU, "Id", "mid");

        // Add any extra headers
        Arrays.stream(extraHeaders).map(eh -> requestDocument.importNode(eh, true)).forEach(header::appendChild);

        // Security

        // Add the security section. This needs the token we got from STS, a few other fields, and a signature.
        var security = XmlUtils.appendChild(header, XmlNamespace.WSSE, "Security");
        security.setAttribute("mustUnderstand", "1");

        // > The value of the <wsu:Created> element SHOULD be within an appropriate
        // > offset from local time. Absent other guidance, a value of 5 minutes MAY be used.
        var ts = XmlUtils.appendChild(security, XmlNamespace.WSU, "Timestamp");
        XmlUtils.setIdAttribute(ts, XmlNamespace.WSU, "Id", "ts");
        XmlUtils.appendChild(
            ts, XmlNamespace.WSU, "Created", OffsetDateTime.now().truncatedTo(ChronoUnit.SECONDS)
                .format(DateTimeFormatter.ISO_INSTANT));

        // > If the sender has obtained a SAML holder-of-key Assertion vouching for the
        // > signing key (see next section) it SHOULD be included in the security header
        // > Include any security tokens (SAML Assertions and/or
        // > BinarySecurityTokens containing X.509 certificates) in the security
        // > header block. Ensure that they have unique id attributes so they
        // > can be referenced (e.g. saml2:ID or wsu:Id).
        var importedToken = requestDocument.importNode(token.assertion(), true);
        security.appendChild(importedToken);

        // > The sender MUST create and include a single <ds:Signature> element in the
        // > <wsse:Security> header block and this signature MUST reference:
        // > • The SOAP <Body> element.
        // > • All security tokens embedded directly under the <wsse:Security>
        // >   element via a <wsse:SecurityTokenReference> (see below), and
        // > • All SOAP header blocks in the message defined in this profile. The
        // >   signature MAY reference other elements including header blocks not
        // >   mentioned in this profile.
        // This is the SecurityTokenReference we go "via"
        var secTokenRef = XmlUtils.appendChild(security, XmlNamespace.WSSE, "SecurityTokenReference");
        var importedTokenId = importedToken.getAttributes()
            .getNamedItem("ID")
            .getTextContent();
        XmlUtils.setIdAttribute(secTokenRef, XmlNamespace.WSU, "Id", "str1");
        XmlUtils.setAttribute(
            secTokenRef,
            XmlNamespace.WSSE11,
            "TokenType",
            "http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLV2.0");
        var keyIdentifier = XmlUtils.appendChild(
            secTokenRef, XmlNamespace.WSSE, "KeyIdentifier", importedTokenId);
        keyIdentifier.setAttribute("ValueType", "http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLID");

        // Signing

        // Make the internal representation of the DOM consistent before signing, by
        // e.g. merging adjacent text-nodes.
        requestDocument.normalizeDocument();

        var sigFactory = XMLSignatureFactory.getInstance("DOM");

        // When signing, we need to reference what parts of the document we're including in the signature.
        // Define the transforms to be applied to each referenced element
        // - EXCLUSIVE: Applies exclusive canonicalization to normalize whitespace and namespace handling
        var excTransform = sigFactory.newTransform(CanonicalizationMethod.EXCLUSIVE, (TransformParameterSpec) null);
        var sha256Digest = sigFactory.newDigestMethod(DigestMethod.SHA256, null);

        // > Authentication assertions MUST be signed by the senders message by including
        // > first a <wsse:SecurityTokenReference> in <wsse:Security> header block,
        // > and then referencing the STR from the message signature using a
        // > <ds:Reference> element. The security token reference MUST include a
        // > <wsse:KeyIdentifier> with a ValueType of http://docs.oasis-
        // > open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLID and specify
        // > the ID of the SAML assertion. The <ds:Reference> element MUST use a
        // > transform algorithm set to “http://docs.oasis-
        // > open.org/wss/2004/01/oasis-200401-wsssoap-message-security-
        // > 1.0#STR-Transform”.
        // STRTransform requires its child to be added to the document and passed as a parameter. I dug this up by reading the code.
        var strTransformChild = requestDocument.createElementNS(XmlNamespace.WSSE.uri(), "TransformationParameters");
        strTransformChild.setPrefix(XmlNamespace.WSSE.prefix());
        var strTransformCanonicalization = XmlUtils.appendChild(strTransformChild, XmlNamespace.DS, "CanonicalizationMethod");
        strTransformCanonicalization.setAttribute("Algorithm", CanonicalizationMethod.EXCLUSIVE);
        var strTransform = sigFactory.newTransform(STRTransform.TRANSFORM_URI, new DOMStructure(strTransformChild));

        // Each reference specifies which part of the document is included in the signature.
        var references = List.of(
            sigFactory.newReference("#action", sha256Digest, List.of(excTransform), null, null),
            sigFactory.newReference("#mid", sha256Digest, List.of(excTransform), null, null),
            sigFactory.newReference("#ts", sha256Digest, List.of(excTransform), null, null),
            sigFactory.newReference("#body", sha256Digest, List.of(excTransform), null, null),
            sigFactory.newReference("#str1", sha256Digest, List.of(strTransform), null, null)
        );

        // Add information on what is signed and how it is signed.
        var signedInfo = sigFactory.newSignedInfo(
            sigFactory.newCanonicalizationMethod(CanonicalizationMethod.EXCLUSIVE, (C14NMethodParameterSpec) null),
            sigFactory.newSignatureMethod(SignatureMethod.RSA_SHA256, null),
            references
        );

        // > In this profile, a holder-of-key Assertion MUST in the
        // > <SubjectConfirmationData> element include a key that can be used to verify
        // > the message signature. Thus, the same key used for message authentication and
        // > integrity is used to confirm the right to use the assertion for message
        // > authorization purposes.
        // > The message signature (i.e. the <ds:Signature> element) MUST refer to the
        // > token with the subject confirmation key within the <ds:KeyInfo> element
        //
        // > Create a <wsse:SecurityTokenReference> element (including
        // > a wsu:Id attribute) for each embedded SAML assertion. Add a
        // > TokenType attribute stating the type of token
        // > (http://docs.oasis-open.org/wss/oasis-wss-saml-
        // > token-profile-1.1#SAMLV2.0) and a <wsse:KeyIdentifier>
        // > sub-element containing the ID of the assertion.
        // Add the reference to the original key
        var keyInfoFactory = sigFactory.getKeyInfoFactory();
        var secRef = requestDocument.createElementNS(XmlNamespace.WSSE.uri(), "SecurityTokenReference");
        secRef.setPrefix(XmlNamespace.WSSE.prefix());
        XmlUtils.setAttribute(
            secRef,
            XmlNamespace.WSSE11,
            "TokenType",
            "http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLV2.0");
        var inSigKeyIdentifier = XmlUtils.appendChild(secRef, XmlNamespace.WSSE, "KeyIdentifier", importedTokenId);
        inSigKeyIdentifier.setAttribute("ValueType", "http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLID");
        var keyInfo = keyInfoFactory.newKeyInfo(List.of(new DOMStructure(secRef)));

        var signContext = new DOMSignContext(signingKey, security);

        // Create and apply the XML signature
        var signature = sigFactory.newXMLSignature(signedInfo, keyInfo);
        signature.sign(signContext);
        return requestDocument;
    }

    /// Extract SOAP message from MIME multipart response.
    /// This is a copy of the one in NspClient.java.
    private static String extractSoapFromMime(String mimeResponse) {
        // Find the boundary marker
        var boundaryPattern = Pattern.compile("--(uuid:[^\\r\\n]+)");
        var boundaryMatcher = boundaryPattern.matcher(mimeResponse);
        if (!boundaryMatcher.find()) {
            throw new NspClientException("Could not find MIME boundary in response");
        }
        var boundary = boundaryMatcher.group(1);

        // Extract content between boundaries
        var contentPattern = Pattern.compile(
            "--" + Pattern.quote(boundary) + "\\r?\\n" +
                "([\\s\\S]*?)" +
                "--" + Pattern.quote(boundary) + "--",
            Pattern.MULTILINE
        );

        var contentMatcher = contentPattern.matcher(mimeResponse);
        if (contentMatcher.find()) {
            var part = contentMatcher.group(1);
            // Skip headers and get content after double newline
            var contentStart = part.indexOf("\r\n\r\n");
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
}
