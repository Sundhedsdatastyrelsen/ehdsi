package dk.sundhedsdatastyrelsen.ncpeh.authentication;

import lombok.extern.slf4j.Slf4j;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.crypto.dsig.CanonicalizationMethod;
import javax.xml.crypto.dsig.DigestMethod;
import javax.xml.crypto.dsig.Reference;
import javax.xml.crypto.dsig.SignatureMethod;
import javax.xml.crypto.dsig.SignedInfo;
import javax.xml.crypto.dsig.Transform;
import javax.xml.crypto.dsig.XMLSignature;
import javax.xml.crypto.dsig.XMLSignatureFactory;
import javax.xml.crypto.dsig.dom.DOMSignContext;
import javax.xml.crypto.dsig.keyinfo.KeyInfo;
import javax.xml.crypto.dsig.keyinfo.KeyInfoFactory;
import javax.xml.crypto.dsig.spec.C14NMethodParameterSpec;
import javax.xml.crypto.dsig.spec.TransformParameterSpec;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.cert.X509Certificate;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Slf4j
public class SAMLSigner {

    private final PrivateKey privateKey;
    private final X509Certificate certificate;

    public SAMLSigner(AuthenticationConfig config) throws Exception {
        KeyStore ks = KeyStore.getInstance("PKCS12");
        try (InputStream fis = getClass().getClassLoader().getResourceAsStream(config.getKeyStorePath())) {
            if (fis == null) {
                throw new FileNotFoundException("Keystore not found in resources " + config.getKeyStorePath());
            }
            ks.load(fis, config.getKeyStorePassword().toCharArray());
        }

        String alias = config.getKeyAlias();
        if (!ks.containsAlias(alias)) {
            alias = Collections.list(ks.aliases()).stream().findFirst()
                .orElseThrow(() -> new IllegalStateException("No alias found in keystore"));
        }

        this.privateKey = (PrivateKey) ks.getKey(alias, config.getKeyStorePassword().toCharArray());
        this.certificate = (X509Certificate) ks.getCertificate(alias);

        if (this.privateKey == null || this.certificate == null) {
            throw new IllegalStateException("Failed to load key or certificate from keystore");
        }
    }

    public String sign(String filledXml) throws Exception {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true);
        DocumentBuilder builder = dbf.newDocumentBuilder();
        Document doc = builder.parse(new ByteArrayInputStream(filledXml.getBytes(StandardCharsets.UTF_8)));

        removeExistingSignatures(doc);
        updateTimestamp(doc);
        replaceAllCertificates(doc, certificate);
        setupIdAttributes(doc);
        signSAMLAssertion(doc, privateKey, certificate);
        signSOAPHeaderElements(doc, privateKey, certificate);

        return writeDocumentToString(doc);
    }

    private static String writeDocumentToString(Document doc) throws TransformerException {
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "no");
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
        transformer.setOutputProperty(OutputKeys.METHOD, "xml");
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        StringWriter writer = new StringWriter();
        transformer.transform(new DOMSource(doc), new StreamResult(writer));
        return writer.toString();
    }

    // Can be removed if we update the template at a later stage
    private static void removeExistingSignatures(Document doc) {
        NodeList signatures = doc.getElementsByTagNameNS(XMLSignature.XMLNS, "Signature");
        for (int i = signatures.getLength() - 1; i >= 0; i--) {
            Node signature = signatures.item(i);
            signature.getParentNode().removeChild(signature);
        }
    }

    private static void updateTimestamp(Document doc) {
        // Get current time
        String currentTime = Instant.now().truncatedTo(ChronoUnit.SECONDS)
            .atZone(java.time.ZoneOffset.UTC)
            .format(DateTimeFormatter.ISO_INSTANT);

        // Calculate NotOnOrAfter (2 hours from now)
        String notOnOrAfter = Instant.now().plus(2, ChronoUnit.HOURS)
            .truncatedTo(ChronoUnit.SECONDS)
            .atZone(java.time.ZoneOffset.UTC)
            .format(DateTimeFormatter.ISO_INSTANT);

        // Update SOAP header timestamp
        NodeList timestampNodes = doc.getElementsByTagNameNS(
            "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd",
            "Timestamp"
        );

        if (timestampNodes.getLength() > 0) {
            Element timestampElement = (Element) timestampNodes.item(0);

            // Find the Created element
            NodeList createdNodes = timestampElement.getElementsByTagNameNS(
                "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd",
                "Created"
            );

            if (createdNodes.getLength() > 0) {
                Element createdElement = (Element) createdNodes.item(0);
                createdElement.setTextContent(currentTime);
            }
        }

        // Update SAML Assertion timestamps
        Element assertion = (Element) doc.getElementsByTagNameNS("urn:oasis:names:tc:SAML:2.0:assertion", "Assertion").item(0);
        if (assertion != null) {
            // Update IssueInstant
            assertion.setAttribute("IssueInstant", currentTime);

            // Update Conditions
            NodeList conditionsNodes = assertion.getElementsByTagNameNS("urn:oasis:names:tc:SAML:2.0:assertion", "Conditions");
            if (conditionsNodes.getLength() > 0) {
                Element conditionsElement = (Element) conditionsNodes.item(0);
                conditionsElement.setAttribute("NotBefore", currentTime);
                conditionsElement.setAttribute("NotOnOrAfter", notOnOrAfter);
            }

            // Update AuthnStatement
            NodeList authnStatementNodes = assertion.getElementsByTagNameNS("urn:oasis:names:tc:SAML:2.0:assertion", "AuthnStatement");
            if (authnStatementNodes.getLength() > 0) {
                Element authnStatementElement = (Element) authnStatementNodes.item(0);
                authnStatementElement.setAttribute("AuthnInstant", currentTime);
            }

            log.info("Updated SAML Assertion timestamps - IssueInstant: " + currentTime + ", NotBefore: " + currentTime + ", NotOnOrAfter: " + notOnOrAfter + ", AuthnInstant: " + currentTime);
        }
    }

    private static void replaceAllCertificates(Document doc, X509Certificate cert) throws Exception {
        // Replace certificates in all X509Certificate elements
        NodeList certNodes = doc.getElementsByTagNameNS(XMLSignature.XMLNS, "X509Certificate");
        String newCertValue = Base64.getEncoder().encodeToString(cert.getEncoded());

        for (int i = 0; i < certNodes.getLength(); i++) {
            Node certNode = certNodes.item(i);
            certNode.setTextContent(newCertValue);
        }

        log.info("Replaced " + certNodes.getLength() + " certificates in template.");
    }

    private static void setupIdAttributes(Document doc) {
        List<String> idsToSign = List.of("body", "ts", "messageID", "action");
        for (String id : idsToSign) {
            Element el = findElementById(doc, id);
            if (el != null) {
                el.setIdAttributeNS("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd", "Id", true);
            } else {
                log.warn("Element with wsu:Id '{}' not found", id);
            }
        }
    }

    private static void signSAMLAssertion(Document doc, PrivateKey privateKey, X509Certificate cert) throws Exception {
        // Try multiple approaches to find the SAML assertion
        Element assertion = null;

        // First try with namespace
        NodeList assertionNodes = doc.getElementsByTagNameNS("urn:oasis:names:tc:SAML:2.0:assertion", "Assertion");
        if (assertionNodes.getLength() > 0) {
            assertion = (Element) assertionNodes.item(0);
        } else {
            // Try without namespace
            assertionNodes = doc.getElementsByTagName("Assertion");
            if (assertionNodes.getLength() > 0) {
                assertion = (Element) assertionNodes.item(0);
            } else {
                // Try with saml: prefix
                assertionNodes = doc.getElementsByTagName("saml:Assertion");
                if (assertionNodes.getLength() > 0) {
                    assertion = (Element) assertionNodes.item(0);
                }
            }
        }

        // Set ID attribute
        assertion.setIdAttribute("ID", true);
        String assertionId = assertion.getAttribute("ID");

        XMLSignatureFactory sigFactory = XMLSignatureFactory.getInstance("DOM");

        // Create reference to the assertion
        Reference ref = sigFactory.newReference(
            "#" + assertionId,
            sigFactory.newDigestMethod(DigestMethod.SHA256, null),
            List.of(
                sigFactory.newTransform(Transform.ENVELOPED, (TransformParameterSpec) null),
                sigFactory.newTransform(CanonicalizationMethod.EXCLUSIVE, (TransformParameterSpec) null)
            ),
            null, null
        );

        // Create SignedInfo
        SignedInfo signedInfo = sigFactory.newSignedInfo(
            sigFactory.newCanonicalizationMethod(CanonicalizationMethod.EXCLUSIVE, (C14NMethodParameterSpec) null),
            sigFactory.newSignatureMethod(SignatureMethod.RSA_SHA256, null),
            Collections.singletonList(ref)
        );

        // Create KeyInfo
        KeyInfoFactory keyInfoFactory = sigFactory.getKeyInfoFactory();
        KeyInfo keyInfo = keyInfoFactory.newKeyInfo(
            Collections.singletonList(
                keyInfoFactory.newX509Data(Collections.singletonList(cert))
            )
        );

        // Find the Issuer element to insert signature after it
        Element issuer = (Element) assertion.getElementsByTagNameNS("urn:oasis:names:tc:SAML:2.0:assertion", "Issuer").item(0);

        // Create signature context
        DOMSignContext signContext = new DOMSignContext(privateKey, assertion, issuer != null ? issuer.getNextSibling() : null);
        signContext.setDefaultNamespacePrefix("ds");
        signContext.setIdAttributeNS(assertion, null, "ID");

        // Create and sign the signature
        XMLSignature signature = sigFactory.newXMLSignature(signedInfo, keyInfo, null, "OCESSignature", null);
        signature.sign(signContext);

        log.info("SAML Assertion signed");
    }

    private static void signSOAPHeaderElements(Document doc, PrivateKey privateKey, X509Certificate cert) throws Exception {
        XMLSignatureFactory sigFactory = XMLSignatureFactory.getInstance("DOM");

        List<String> idsToSign = List.of("body", "ts", "messageID", "action");

        // Create references for all elements to be signed
        List<Reference> references = new ArrayList<>();
        for (String id : idsToSign) {
            Element el = findElementById(doc, id);
            if (el != null) {
                references.add(sigFactory.newReference(
                    "#" + id,
                    sigFactory.newDigestMethod(DigestMethod.SHA1, null),
                    List.of(sigFactory.newTransform(CanonicalizationMethod.EXCLUSIVE, (TransformParameterSpec) null)),
                    null, null
                ));
            }
        }

        if (references.isEmpty()) {
            log.error("No elements found to sign in SOAP header"); // TODO: Consider throwing an exception - smarter error handling
            return;
        }

        // Create SignedInfo
        SignedInfo signedInfo = sigFactory.newSignedInfo(
            sigFactory.newCanonicalizationMethod(CanonicalizationMethod.EXCLUSIVE, (C14NMethodParameterSpec) null),
            sigFactory.newSignatureMethod(SignatureMethod.RSA_SHA1, null), // TODO: SOSI team has been notified that SHA1 should be updated. Probable change to SHA256.
            references
        );

        // Create KeyInfo
        KeyInfoFactory keyInfoFactory = sigFactory.getKeyInfoFactory();
        KeyInfo keyInfo = keyInfoFactory.newKeyInfo(
            Collections.singletonList(
                keyInfoFactory.newX509Data(Collections.singletonList(cert))
            )
        );

        // Find the Security element
        Element securityElement = (Element) doc.getElementsByTagNameNS(
            "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd",
            "Security"
        ).item(0);

        if (securityElement == null) {
            // TODO: Smarter error handling
            log.warn("Security element not found");
            return;
        }

        // Create signature context
        DOMSignContext signContext = new DOMSignContext(privateKey, securityElement);
        signContext.setDefaultNamespacePrefix("ds");

        // Set up ID attributes for the sign context
        for (String id : idsToSign) {
            Element el = findElementById(doc, id);
            if (el != null) {
                signContext.setIdAttributeNS(el, "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd", "Id");
            }
        }

        // Create and sign the signature
        XMLSignature signature = sigFactory.newXMLSignature(signedInfo, keyInfo);
        signature.sign(signContext);

        log.info("SOAP header elements signed successfully");
    }

    private static Element findElementById(Document doc, String id) {
        NodeList all = doc.getElementsByTagNameNS("*", "*");
        for (int i = 0; i < all.getLength(); i++) {
            Element el = (Element) all.item(i);
            if (el.hasAttributeNS("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd", "Id") &&
                el.getAttributeNS("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd", "Id").equals(id)) {
                return el;
            }
        }
        return null;
    }
}
