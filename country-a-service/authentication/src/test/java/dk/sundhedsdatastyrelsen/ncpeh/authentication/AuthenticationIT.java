package dk.sundhedsdatastyrelsen.ncpeh.authentication;

import org.apache.ws.security.WSSConfig;
import org.apache.ws.security.transform.STRTransform;
import org.junit.jupiter.api.Test;

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
import java.nio.file.Path;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AuthenticationIT {
    static AuthenticationService.Config config() {
        var keystorePath = System.getenv("KEYSTORE_PATH");
        assertThat("KEYSTORE_PATH env var should be set", keystorePath, notNullValue());
        var keyAlias = System.getenv("KEY_ALIAS");
        assertThat("KEY_ALIAS env var should be set", keyAlias, notNullValue());
        var password = System.getenv("KEYSTORE_PASSWORD");
        assertThat("KEYSTORE_PASSWORD env var should be set", password, notNullValue());
        return new AuthenticationService.Config(
            URI.create("https://test1-cnsp.ekstern-test.nspop.dk:8443/sts/services/DKNCPBST2EHDSIIdws"),
            Path.of(keystorePath),
            keyAlias,
            password,
            "https://ehdsi-idp.testkald.nspop.dk"
        );
    }

    @Test
    void exchangeToken() throws AuthenticationException {
        var service = new AuthenticationService(config());
        var idwsToken = service.xcaSoapHeaderToIdwsToken(TestUtils.resource("openncp_soap_header.xml"), "https://fmk");

        assertThat(idwsToken.audience(), is("https://fmk"));
        assertThat(idwsToken.assertion(), notNullValue());
    }

    @Test
    void exchangeTokenWithError() {
        var service = new AuthenticationService(config());
        assertThrows(
            AuthenticationException.SosiStsException.class,
            () -> service.xcaSoapHeaderToIdwsToken(TestUtils.resource("openncp_soap_header_bad.xml"), "https://fmk"));
    }

    public static void main(String[] args) throws Exception {
        // The token we receive from STS is a holder-of-key token.
        // There's a specification of what's required published by Digitaliseringsstyrelsen. They also have a reference
        // implementation.
        // Root of them: https://digst.dk/it-loesninger/standarder/oio-identity-based-web-services-oio-idws/
        // Follow the links there to the specification and the reference.

        // wss4j is necessary because we need a STRTransform in the header, which is not supported natively. And in
        // order to register that with the built in signing, we have to call the init function.
        WSSConfig.init();
        var service = new AuthenticationService(config());
        // Create a valid bootstrap token, see the test above.
        var idwsToken = service.xcaSoapHeaderToIdwsToken(
            TestUtils.resource("openncp_soap_header.xml"),
            "https://fmk");

        var fmkKeystorePathRaw = System.getenv("FMK_KEYSTORE_PATH");
        assertThat("FMK_KEYSTORE_PATH env var should be set", fmkKeystorePathRaw, notNullValue());
        var fmkKeystorePath = Path.of(fmkKeystorePathRaw);
        var fmkAlias = System.getenv("FMK_KEY_ALIAS");
        assertThat("FMK_KEY_ALIAS env var should be set", fmkAlias, notNullValue());
        var fmkPassword = System.getenv("FMK_KEYSTORE_PASSWORD");
        assertThat("FMK_KEYSTORE_PASSWORD env var should be set", fmkPassword, notNullValue());

        // Create a valid request to one of the endpoints that should work. It should be GetPrescriptions.
        var requestDocument = XmlUtils.newDocument();
        var envelope = XmlUtils.appendChild(requestDocument, XmlNamespace.SOAP, "Envelope");
        XmlUtils.declareNamespaces(
            envelope, XmlNamespace.SOAP, XmlNamespace.DS, XmlNamespace.SAML, XmlNamespace.WSSE, XmlNamespace.WSSE11, XmlNamespace.XSI,
            XmlNamespace.WSU, XmlNamespace.XSD, XmlNamespace.WSA);
        var header = XmlUtils.appendChild(envelope, XmlNamespace.SOAP, "Header");
        var body = XmlUtils.appendChild(envelope, XmlNamespace.SOAP, "Body");
        XmlUtils.setIdAttribute(body, XmlNamespace.WSU, "Id", "body");

        // Can probably create the body some better way. This is OK for now.
        var dkmaNamespace = new XmlNamespace("dkma", "http://www.dkma.dk/medicinecard/xml.schema/2015/06/01");
        var presReq = XmlUtils.appendChild(body, dkmaNamespace, "GetPrescriptionRequest");
        var personIdentifier = XmlUtils.appendChild(presReq, dkmaNamespace, "PersonIdentifier", "1111111118");
        personIdentifier.setAttribute("source", "CPR");
        XmlUtils.appendChild(presReq, dkmaNamespace, "IncludeOpenPrescriptions");
        XmlUtils.appendChild(presReq, dkmaNamespace, "IncludeEffectuations", "false");

        var actionEl = XmlUtils.appendChild(header, XmlNamespace.WSA, "Action", "http://www.dkma.dk/medicinecard/xml.schema/2015/06/01/E6#GetPrescription");
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

        var importedToken = requestDocument.importNode(idwsToken.assertion(), true);
        security.appendChild(importedToken);

        // Signing

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

        // Make the internal representation of the DOM consistent before signing, by
        // e.g. merging adjacent text-nodes.
        requestDocument.normalizeDocument();

        var sigFactory = XMLSignatureFactory.getInstance("DOM");

        // Define the transforms to be applied to each referenced element
        // - EXCLUSIVE: Applies exclusive canonicalization to normalize whitespace and namespace handling
        var excTransform = sigFactory.newTransform(CanonicalizationMethod.EXCLUSIVE, (TransformParameterSpec) null);
        var sha256Digest = sigFactory.newDigestMethod(DigestMethod.SHA256, null);
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

        // Add the reference to the original key
        var keyInfoFactory = sigFactory.getKeyInfoFactory();
        var secRef = requestDocument.createElementNS(XmlNamespace.WSSE.uri(), "SecurityTokenReference");
        secRef.setPrefix(XmlNamespace.WSSE.prefix());
        XmlUtils.setAttribute(secRef, XmlNamespace.WSSE11, "TokenType", "http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLV2.0");
        var inSigKeyIdentifier = XmlUtils.appendChild(secRef, XmlNamespace.WSSE, "KeyIdentifier", importedTokenId);
        inSigKeyIdentifier.setAttribute("ValueType", "http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLID");
        var keyInfo = keyInfoFactory.newKeyInfo(List.of(new DOMStructure(secRef)));

        var cert = CertificateUtils.loadCertificateFromKeystore(
            fmkKeystorePath,
            fmkAlias,
            fmkPassword);
        var signContext = new DOMSignContext(cert.privateKey(), security);

        // Create and apply the XML signature
        var signature = sigFactory.newXMLSignature(signedInfo, keyInfo);
        signature.sign(signContext);
        // Have to sign it.
        //
        // If the sender has obtained a SAML holder-of-key Assertion vouching for the
        //signing key (see next section) it SHOULD be included in the security header
        // Authentication assertions MUST be signed by the senders message by including
        //first a <wsse:SecurityTokenReference> in <wsse:Security> header block,
        //and then referencing the STR from the message signature using a
        //<ds:Reference> element. The security token reference MUST include a
        //<wsse:KeyIdentifier> with a ValueType of http://docs.oasis-
        //open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLID and specify
        //the ID of the SAML assertion. The <ds:Reference> element MUST use a
        //transform algorithm set to “http://docs.oasis-
        //open.org/wss/2004/01/oasis-200401-wsssoap-message-security-
        //1.0#STR-Transform”.
        // In this profile, a holder-of-key Assertion MUST in the
        //<SubjectConfirmationData> element include a key that can be used to verify
        //the message signature. Thus, the same key used for message authentication and
        //integrity is used to confirm the right to use the assertion for message
        //authorization purposes.
        //The message signature (i.e. the <ds:Signature> element) MUST refer to the
        //token with the subject confirmation key within the <ds:KeyInfo> element
        // Also need to do this: Include any security tokens (SAML Assertions and/or
        //BinarySecurityTokens containing X.509 certificates) in the security
        //header block. Ensure that they have unique id attributes so they
        //can be referenced (e.g. saml2:ID or wsu:Id).
        //Create a <wsse:SecurityTokenReference> element (including
        //a wsu:Id attribute) for each embedded SAML assertion. Add a
        //TokenType attribute stating the type of token
        //(http://docs.oasis-open.org/wss/oasis-wss-saml-
        //token-profile-1.1#SAMLV2.0) and a <wsse:KeyIdentifier>
        //sub-element containing the ID of the assertion.

        var reqBody = XmlUtils.writeDocumentToString(requestDocument);
        System.out.println(reqBody);

        // Put it into a soap envelope and send it to fmk
        // endpoint: https://test1.fmk.netic.dk/idws_xua/fmk_xua_146_E6
        // previous endpoint: https://test2-cnsp.ekstern-test.nspop.dk:8443/decoupling
        try (var httpClient = HttpClient.newBuilder().build()) {
            var request = HttpRequest.newBuilder(URI.create("https://test1.fmk.netic.dk/idws_xua/fmk_xua_146_E6"))
                .header("Content-Type", "application/soap+xml; charset=utf-8")
                .header("SOAPAction", "http://www.dkma.dk/medicinecard/xml.schema/2015/06/01/E6#GetPrescription")
                .POST(HttpRequest.BodyPublishers.ofString(reqBody))
                .build();
            var res = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.printf("Code: %d. Body: %s", res.statusCode(), res.body());
        }
    }
}
