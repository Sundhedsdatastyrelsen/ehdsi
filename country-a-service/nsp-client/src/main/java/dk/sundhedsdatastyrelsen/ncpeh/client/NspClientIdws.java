package dk.sundhedsdatastyrelsen.ncpeh.client;

import dk.sosi.seal.SOSIFactory;
import dk.sosi.seal.model.Reply;
import dk.sosi.seal.pki.SOSITestFederation;
import dk.sundhedsdatastyrelsen.ncpeh.authentication.EuropeanHcpIdwsToken;
import dk.sundhedsdatastyrelsen.ncpeh.authentication.XmlNamespace;
import dk.sundhedsdatastyrelsen.ncpeh.authentication.XmlUtils;
import org.apache.ws.security.transform.STRTransform;
import org.slf4j.Logger;
import org.w3c.dom.Element;

import javax.xml.crypto.dom.DOMStructure;
import javax.xml.crypto.dsig.CanonicalizationMethod;
import javax.xml.crypto.dsig.DigestMethod;
import javax.xml.crypto.dsig.SignatureMethod;
import javax.xml.crypto.dsig.XMLSignatureFactory;
import javax.xml.crypto.dsig.dom.DOMSignContext;
import javax.xml.crypto.dsig.spec.C14NMethodParameterSpec;
import javax.xml.crypto.dsig.spec.TransformParameterSpec;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.security.PrivateKey;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Properties;
import java.util.UUID;
import java.util.regex.Pattern;

public class NspClientIdws {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(NspClientIdws.class);

    private static final SOSIFactory sosiFactory =
        new SOSIFactory(new SOSITestFederation(new Properties()), new Properties());

    private NspClientIdws() {
    }

    /**
     * Send a SOAP request to an NSP service.
     */
    public static Reply request(
        URI uri,
        Element soapBody,
        String soapAction,
        EuropeanHcpIdwsToken token,
        PrivateKey signingKey,
        Element... extraHeaders
    ) throws Exception {
        // Create a valid request to one of the endpoints that should work. It should be GetPrescriptions.
        var requestDocument = XmlUtils.newDocument();
        var envelope = XmlUtils.appendChild(requestDocument, XmlNamespace.SOAP, "Envelope");
        XmlUtils.declareNamespaces(
            envelope, XmlNamespace.SOAP, XmlNamespace.DS, XmlNamespace.SAML, XmlNamespace.WSSE, XmlNamespace.WSSE11, XmlNamespace.XSI,
            XmlNamespace.WSU, XmlNamespace.XSD, XmlNamespace.WSA);
        var header = XmlUtils.appendChild(envelope, XmlNamespace.SOAP, "Header");
        var body = XmlUtils.appendChild(envelope, XmlNamespace.SOAP, "Body");
        XmlUtils.setIdAttribute(body, XmlNamespace.WSU, "Id", "body");
        body.appendChild(requestDocument.importNode(soapBody, true));

        var actionEl = XmlUtils.appendChild(header, XmlNamespace.WSA, "Action", soapAction);
        XmlUtils.setIdAttribute(actionEl, XmlNamespace.WSU, "Id", "action");

        var msgIdEl = XmlUtils.appendChild(header, XmlNamespace.WSA, "MessageID", UUID.randomUUID().toString());
        XmlUtils.setIdAttribute(msgIdEl, XmlNamespace.WSU, "Id", "mid");

        var security = XmlUtils.appendChild(header, XmlNamespace.WSSE, "Security");
        security.setAttribute("mustUnderstand", "1");


        var ts = XmlUtils.appendChild(security, XmlNamespace.WSU, "Timestamp");
        XmlUtils.setIdAttribute(ts, XmlNamespace.WSU, "Id", "ts");
        // > The value of the <wsu:Created> element SHOULD be within an appropriate
        // > offset from local time. Absent other guidance, a value of 5 minutes MAY be used.
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

        // This is the core of the XML signature that describes what and how it was signed
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

        var reqBody = XmlUtils.writeDocumentToString(requestDocument);

        // Put it all into a soap envelope and send it to the endpoint.
        try (var httpClient = HttpClient.newBuilder().build()) {
            var request = HttpRequest.newBuilder(uri)
                .header("Content-Type", "application/soap+xml; charset=utf-8")
                .header("SOAPAction", soapAction)
                .POST(HttpRequest.BodyPublishers.ofString(reqBody))
                .build();
            var res = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            var fullText = res.body();

            // Check if response is MIME multipart
            if (fullText.contains("--uuid:")) {
                fullText = extractSoapFromMime(fullText);
            }

            var reply = sosiFactory.deserializeReply(fullText);
            if (res.statusCode() >= 400) {
                throw new NspClientException(String.format("Request failed with message: %s", reply.getFaultString()));
            }
            return reply;
        }
    }

    /**
     * Extract SOAP message from MIME multipart response.
     */
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
