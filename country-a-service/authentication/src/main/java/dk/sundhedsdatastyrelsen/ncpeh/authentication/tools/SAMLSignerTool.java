package dk.sundhedsdatastyrelsen.ncpeh.authentication.tools;

import org.w3c.dom.*;

import javax.xml.crypto.dsig.spec.TransformParameterSpec;
import javax.xml.parsers.*;
import javax.xml.crypto.dsig.*;
import javax.xml.crypto.dsig.dom.DOMSignContext;
import javax.xml.crypto.dsig.keyinfo.*;
import javax.xml.crypto.dsig.spec.C14NMethodParameterSpec;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.security.*;
import java.security.cert.X509Certificate;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Base64;

// TODO Refactor to work on Assertion class
public class SAMLSignerTool {

    public static void main(String[] args) throws Exception {

        // Use the existing P12 keystore files
        String p12Path = "/home/jls/repos/work/sds/ehdsi/country-a-service/authentication/src/main/resources/epps-sosi-sts-client.p12";
        String p12Password = "R_d9ZzEQ93ry";
        String alias = "epps-sosi-sts-client";

        String inputFile = "/home/jls/repos/work/sds/ehdsi/country-a-service/authentication/src/main/resources/envelope/soap_envelope.xml"; // DRG WORKING REQUEST
        String outputFile = "/home/jls/repos/work/sds/ehdsi/country-a-service/authentication/src/main/resources/envelope/self_signed_soap_envelope.xml"; // SIGNED USING KEYSTORE OUTPUT

        // Load PKCS12 keystore
        KeyStore ks = KeyStore.getInstance("PKCS12");
        try (FileInputStream fis = new FileInputStream(p12Path)) {
            ks.load(fis, p12Password.toCharArray());
        }

        // Get the first available alias if the default one doesn't exist
        if (!ks.containsAlias(alias)) {
            String[] aliases = Collections.list(ks.aliases()).toArray(new String[0]);
            if (aliases.length > 0) {
                alias = aliases[0];
                System.out.println("Using alias: " + alias);
            } else {
                throw new RuntimeException("No certificates found in keystore");
            }
        }

        PrivateKey privateKey = (PrivateKey) ks.getKey(alias, p12Password.toCharArray());
        X509Certificate cert = (X509Certificate) ks.getCertificate(alias);

        if (privateKey == null || cert == null) {
            throw new RuntimeException("Could not load private key or certificate from keystore");
        }

        System.out.println("Loaded certificate: " + cert.getSubjectDN());
        System.out.println("Loaded private key algorithm: " + privateKey.getAlgorithm());

        // Parse the input XML
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true);
        DocumentBuilder builder = dbf.newDocumentBuilder();
        Document doc = builder.parse(new File(inputFile));

        // Remove existing signatures first
        removeExistingSignatures(doc);

        // Update timestamp to current time
        updateTimestamp(doc);

        // Replace all certificates with our certificate
        replaceAllCertificates(doc, cert);

        // Set up ID attributes for elements that need to be signed
        setupIdAttributes(doc);

        // Sign the SAML Assertion first
        signSAMLAssertion(doc, privateKey, cert);

        // Sign the SOAP header elements
        signSOAPHeaderElements(doc, privateKey, cert);

        // Clean up whitespace in signature values and certificates
        //cleanupSignatureOutput(doc);

        // Write the signed document
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "no");
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
        transformer.setOutputProperty(OutputKeys.METHOD, "xml");
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");

        transformer.transform(new DOMSource(doc), new StreamResult(new FileOutputStream(outputFile)));
        System.out.println("Signed SOAP envelope saved to: " + outputFile);
    }

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
                System.out.println("Updated SOAP timestamp to: " + currentTime);
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

            System.out.println("Updated SAML Assertion timestamps - IssueInstant: " + currentTime + ", NotBefore: " + currentTime + ", NotOnOrAfter: " + notOnOrAfter + ", AuthnInstant: " + currentTime);
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

        System.out.println("Replaced " + certNodes.getLength() + " certificates with our certificate");
    }

    private static void setupIdAttributes(Document doc) {
        List<String> idsToSign = List.of("body", "ts", "messageID", "action");
        for (String id : idsToSign) {
            Element el = findElementById(doc, id);
            if (el != null) {
                el.setIdAttributeNS("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd", "Id", true);
            } else {
                System.err.println("Warning: Element with wsu:Id '" + id + "' not found");
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

        System.out.println("SAML Assertion signed successfully");
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
            System.err.println("Warning: No elements found to sign in SOAP header");
            return;
        }

        // Create SignedInfo
        SignedInfo signedInfo = sigFactory.newSignedInfo(
            sigFactory.newCanonicalizationMethod(CanonicalizationMethod.EXCLUSIVE, (C14NMethodParameterSpec) null),
            sigFactory.newSignatureMethod(SignatureMethod.RSA_SHA1, null),
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
            System.err.println("Warning: Security element not found");
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

        System.out.println("SOAP header elements signed successfully");
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

