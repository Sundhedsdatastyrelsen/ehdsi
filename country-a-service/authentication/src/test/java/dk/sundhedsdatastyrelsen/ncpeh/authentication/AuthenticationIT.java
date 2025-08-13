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
        // TODO Notes
        // I was unsure what kind of token we even got from the STS, but it turns out that it's
        // a holder-of-key token.
        // There's a specification of what's required published by Digitaliseringsstyrelsen. They also have a reference
        // implementation.
        // Root of them: https://digst.dk/it-loesninger/standarder/oio-identity-based-web-services-oio-idws/
        // Follow the links there to the specification and the reference.
        // Currently I'm unsure what to use to sign the request, since we don't have a shared secret with FMK.
        // I'll just try using the same one we use for communicating with the STS.

        WSSConfig.init();
        var service = new AuthenticationService(config());
        // Create a valid bootstrap token, see the test above.
        var idwsToken = service.xcaSoapHeaderToIdwsToken(
            TestUtils.resource("openncp_soap_header.xml"),
            "https://fmk");

        var fmkKeystorePath = System.getenv("FMK_KEYSTORE_PATH");
        assertThat("FMK_KEYSTORE_PATH env var should be set", fmkKeystorePath, notNullValue());
        var fmkAlias = System.getenv("FMK_KEY_ALIAS");
        assertThat("FMK_KEY_ALIAS env var should be set", fmkAlias, notNullValue());
        var fmkPassword = System.getenv("FMK_KEYSTORE_PASSWORD");
        assertThat("FMK_KEYSTORE_PASSWORD env var should be set", fmkPassword, notNullValue());

        // Create a valid request to one of the endpoints that should work. It should be GetPrescriptions.
        // Available services:
        //
        //    GetPrescriptions
        //    StartEffectuation
        //    CreateEffectuation
        // TODO Do we need an <wsu:Expires>?
        // TODO Do we need a <wsa:To>?
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
        // TODO probably remove this. This was trying to use the apache wss4j classes to do it, but I don't think that's necessary.
//        var wssHeader = new WSSecHeader(requestDocument);
//        wssHeader.setMustUnderstand(true);
//        var security = wssHeader.insertSecurityHeader();
//        var secBuilder = new WSSecSignature(wssHeader);

        var security = XmlUtils.appendChild(header, XmlNamespace.WSSE, "Security");
        security.setAttribute("mustUnderstand", "1");


        var ts = XmlUtils.appendChild(security, XmlNamespace.WSU, "Timestamp");
        XmlUtils.setIdAttribute(ts, XmlNamespace.WSU, "Id", "ts");
        // > The value of the <wsu:Created> element SHOULD be within an appropriate
        // > offset from local time. Absent other guidance, a value of 5 minutes MAY be used.
        // TODO I'm unsure what that means. Is this correct?
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
        // TODO didn't turn out to be necessary, I think. This was also trying to use wss4j for signing, but I just
        //  needed the STRTransform, and that seems to be registered correctly.
//        var wssSigner = new WSSecSignature(requestDocument);
//        var crypto = CryptoFactory.getInstance();
//        wssSigner.addReferencesToSign(
//            List.of(
//                new WSEncryptionPart("mid"),
//                new WSEncryptionPart("ts")
//            )
//        );
//        var signedDoc = wssSigner.build(crypto);
//        System.out.println(XmlUtils.writeDocumentToStringPretty(signedDoc));
//
//        if (1 == 1)
//            return;

        var sigFactory = XMLSignatureFactory.getInstance("DOM");

        // Define the transforms to be applied to each referenced element
        // - EXCLUSIVE: Applies exclusive canonicalization to normalize whitespace and namespace handling
        var excTransform = sigFactory.newTransform(CanonicalizationMethod.EXCLUSIVE, (TransformParameterSpec) null);
        var sha256Digest = sigFactory.newDigestMethod(DigestMethod.SHA256, null);
        // STRTransform requires its child to be added to the document and passed as a parameter. I dug this up by reading the code.
        // TODO However, I don't know if the resulting signature is correct.
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

        // TODO Unsure what certificate to use here.
        var cert = CertificateUtils.loadCertificateFromKeystore(
            Path.of(fmkKeystorePath),
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

//    private <RequestType, ResponseType> ResponseType makeFmkRequest(
//        JAXBElement<RequestType> request,
//        String soapAction,
//        Class<ResponseType> clazz,
//        Identity caller,
//        PredefinedRequestedRole requestedRole,
//        boolean requiresMedicineCardConsent
//    ) throws JAXBException {
//        log.info("Calling '{}' with a SOAP action '{}'", serviceUri, soapAction);
//        final Reply reply;
//        Element[] extraHeaders;
//        if (requiresMedicineCardConsent) {
//            extraHeaders = new Element[]{ClientUtils.toElement(jaxbContext, getWhitelistingHeader(requestedRole)), ClientUtils.toElement(jaxbContext, getMedicineReviewConsent())};
//        } else {
//            extraHeaders = new Element[]{ClientUtils.toElement(jaxbContext, getWhitelistingHeader(requestedRole))};
//        }
//        try {
//            reply = NspClient.request(
//                serviceUri,
//                ClientUtils.toElement(jaxbContext, request),
//                soapAction,
//                caller,
//                extraHeaders
//            );
//        } catch (Exception e) {
//            throw new NspClientException("FMK request failed", e);
//        }
//        return jaxbContext.createUnmarshaller().unmarshal(reply.getBody(), clazz).getValue();
//    }

    /* previous request:

    <?xml version="1.0" encoding="UTF-8" ?>
    <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:ds="http://www.w3.org/2000/09/xmldsig#" xmlns:medcom="http://www.medcom.dk/dgws/2006/04/dgws-1.0.xsd" xmlns:saml="urn:oasis:names:tc:SAML:2.0:assertion" xmlns:sosi="http://www.sosi.dk/sosi/2006/04/sosi-1.0.xsd" xmlns:wsa="http://schemas.xmlsoap.org/ws/2004/08/addressing" xmlns:wsse="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd" xmlns:wst="http://schemas.xmlsoap.org/ws/2005/02/trust" xmlns:wsu="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" id="Envelope">
      <soapenv:Header>
        <wsse:Security>
          <wsu:Timestamp>
            <wsu:Created>2025-08-05T08:31:03Z</wsu:Created>
          </wsu:Timestamp>
          <saml:Assertion IssueInstant="2025-08-05T08:25:50Z" Version="2.0" id="IDCard" xmlns:ds="http://www.w3.org/2000/09/xmldsig#" xmlns:saml="urn:oasis:names:tc:SAML:2.0:assertion">
            <saml:Issuer>TEST1-NSP-STS</saml:Issuer>
            <saml:Subject>
              <saml:NameID Format="medcom:other">urn:uuid:00798849-effe-4733-bcc4-670093830511</saml:NameID>
              <saml:SubjectConfirmation>
                <saml:ConfirmationMethod>urn:oasis:names:tc:SAML:2.0:cm:holder-of-key</saml:ConfirmationMethod>
                <saml:SubjectConfirmationData>
                  <ds:KeyInfo>
                    <ds:KeyName>OCESSignature</ds:KeyName>
                  </ds:KeyInfo>
                </saml:SubjectConfirmationData>
              </saml:SubjectConfirmation>
            </saml:Subject>
            <saml:Conditions NotBefore="2025-08-05T08:25:50Z" NotOnOrAfter="2025-08-06T08:25:50Z"/>
            <saml:AttributeStatement id="IDCardData">
              <saml:Attribute Name="sosi:IDCardID">
                <saml:AttributeValue>F+Xp5LLkQoBejY+CbZsjug==</saml:AttributeValue>
              </saml:Attribute>
              <saml:Attribute Name="sosi:IDCardVersion">
                <saml:AttributeValue>1.0.1</saml:AttributeValue>
              </saml:Attribute>
              <saml:Attribute Name="sosi:IDCardType">
                <saml:AttributeValue>user</saml:AttributeValue>
              </saml:Attribute>
              <saml:Attribute Name="sosi:AuthenticationLevel">
                <saml:AttributeValue>4</saml:AttributeValue>
              </saml:Attribute>
            </saml:AttributeStatement>
            <saml:AttributeStatement id="UserLog">
              <saml:Attribute Name="medcom:UserCivilRegistrationNumber">
                <saml:AttributeValue>0107828949</saml:AttributeValue>
              </saml:Attribute>
              <saml:Attribute Name="medcom:UserGivenName">
                <saml:AttributeValue>Jeppe</saml:AttributeValue>
              </saml:Attribute>
              <saml:Attribute Name="medcom:UserSurName">
                <saml:AttributeValue>Møller</saml:AttributeValue>
              </saml:Attribute>
              <saml:Attribute Name="medcom:UserRole">
                <saml:AttributeValue>urn:dk:healthcare:unknown</saml:AttributeValue>
              </saml:Attribute>
            </saml:AttributeStatement>
            <saml:AttributeStatement id="SystemLog"><saml:Attribute Name="medcom:ITSystemName"><saml:AttributeValue>Service Consumer Test</saml:AttributeValue></saml:Attribute><saml:Attribute Name="medcom:CareProviderID" NameFormat="medcom:cvrnumber"><saml:AttributeValue>33257872</saml:AttributeValue></saml:Attribute><saml:Attribute Name="medcom:CareProviderName"><saml:AttributeValue>Sundhedsdatastyrelsen</saml:AttributeValue></saml:Attribute></saml:AttributeStatement><ds:Signature id="OCESSignature"><ds:SignedInfo><ds:CanonicalizationMethod Algorithm="http://www.w3.org/2001/10/xml-exc-c14n#"/><ds:SignatureMethod Algorithm="http://www.w3.org/2000/09/xmldsig#rsa-sha1"/><ds:Reference URI="#IDCard"><ds:Transforms><ds:Transform Algorithm="http://www.w3.org/2000/09/xmldsig#enveloped-signature"/><ds:Transform Algorithm="http://www.w3.org/2001/10/xml-exc-c14n#"/></ds:Transforms><ds:DigestMethod Algorithm="http://www.w3.org/2000/09/xmldsig#sha1"/><ds:DigestValue>kiy4gBEu6L/hdYZzLVV1HsH3uUE=</ds:DigestValue></ds:Reference></ds:SignedInfo><ds:SignatureValue>BE7XlSZ7lRqpf6Fq9sdEtzMzJBO04AnReuS7YaUAeCQQZJUEKxA+SuHgqqgnpGstTNa4P7egkWzYgN+g78xHjc83g3uCY0nwOEW0+cB67rlKTXc8z/ItOurIOHbnzyJNjR0giHepRcwZn6q4onMdru2oEbYSPuhQ6ui8ewNa0g4LV0kPiJr0fgtW60vTktth5gVUGwTXr2Q+atLTrk6Mwu6mSi2mVmV2gTXIOaFrBrW35WysXaQ0EJFhU7cErvD/owJLvRQy3SU7R5pCYB7EMyMP9dfG1c4bMy5+ZiW04i9XyZCleD4IRqqHHCkQ3JgXGcDkQh/s3CHW+4yC86J3Zz9LlaKEHytuiqhnp1TNOEGlDvAR0ON/pc2wFK2OTeO+WdE120dFh7v3Sbt44NorRRKFLRf3b+xIrU1ZrWvP4qu9CAewE7LBOfZTvvfjrZB7MxSKHdJsxCHCc8AsmwLqVWzWJZyXVoIPYWBrLnsO18UES0nNTyGEJybm6tANpt8x</ds:SignatureValue><ds:KeyInfo><ds:X509Data><ds:X509Certificate>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</ds:X509Certificate></ds:X509Data></ds:KeyInfo></ds:Signature></saml:Assertion></wsse:Security><medcom:Header><medcom:SecurityLevel>4</medcom:SecurityLevel><medcom:Linking><medcom:FlowID>6eafcb65-6d17-4b04-ab42-42e04e9c2410</medcom:FlowID><medcom:MessageID>f0c924e4-2dfe-4180-91e1-e2931da565d3</medcom:MessageID></medcom:Linking><medcom:RequireNonRepudiationReceipt>no</medcom:RequireNonRepudiationReceipt></medcom:Header><ns7:WhitelistingHeader xmlns:ns7="http://www.sdsd.dk/dgws/2012/06" xmlns="http://www.dkma.dk/medicinecard/xml.schema/2015/06/01" xmlns:ns2="http://www.dkma.dk/medicinecard/xml.schema/2015/06/01/E2" xmlns:ns3="http://www.dkma.dk/medicinecard/xml.schema/2015/06/01/E5" xmlns:ns4="http://www.dkma.dk/medicinecard/xml.schema/2015/06/01/E6" xmlns:ns5="http://www.dkma.dk/medicinecard/xml.schema/2015/06/01/E3" xmlns:ns6="http://www.sdsd.dk/dgws/2010/08"><ns6:SystemOwnerName>Sundhedsdatastyrelsen</ns6:SystemOwnerName><ns6:SystemName>ePPS PoC</ns6:SystemName><ns6:SystemVersion>0.1.0</ns6:SystemVersion><ns6:OrgResponsibleName>Sundhedsdatastyrelsen</ns6:OrgResponsibleName><ns6:OrgUsingName>Sundhedsdatastyrelsen</ns6:OrgUsingName><ns6:OrgUsingID NameFormat="medcom:locationnumber">5790000120512</ns6:OrgUsingID><ns6:RequestedRole>Apoteker</ns6:RequestedRole></ns7:WhitelistingHeader></soapenv:Header><soapenv:Body><GetPrescriptionRequest xmlns="http://www.dkma.dk/medicinecard/xml.schema/2015/06/01" xmlns:ns2="http://www.dkma.dk/medicinecard/xml.schema/2015/06/01/E2" xmlns:ns3="http://www.dkma.dk/medicinecard/xml.schema/2015/06/01/E5" xmlns:ns4="http://www.dkma.dk/medicinecard/xml.schema/2015/06/01/E6" xmlns:ns5="http://www.dkma.dk/medicinecard/xml.schema/2015/06/01/E3" xmlns:ns6="http://www.sdsd.dk/dgws/2010/08" xmlns:ns7="http://www.sdsd.dk/dgws/2012/06"><PersonIdentifier source="CPR">1111111118</PersonIdentifier><IncludeOpenPrescriptions/><IncludeEffectuations>false</IncludeEffectuations></GetPrescriptionRequest></soapenv:Body></soapenv:Envelope>
     */
}
