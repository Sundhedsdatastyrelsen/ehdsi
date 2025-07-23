package dk.sundhedsdatastyrelsen.ncpeh.authentication;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.xml.crypto.MarshalException;
import javax.xml.crypto.dsig.CanonicalizationMethod;
import javax.xml.crypto.dsig.DigestMethod;
import javax.xml.crypto.dsig.Reference;
import javax.xml.crypto.dsig.SignatureMethod;
import javax.xml.crypto.dsig.Transform;
import javax.xml.crypto.dsig.XMLSignature;
import javax.xml.crypto.dsig.XMLSignatureException;
import javax.xml.crypto.dsig.XMLSignatureFactory;
import javax.xml.crypto.dsig.dom.DOMSignContext;
import javax.xml.crypto.dsig.spec.C14NMethodParameterSpec;
import javax.xml.crypto.dsig.spec.TransformParameterSpec;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Slf4j
public class SAMLSigner {
    private final PrivateKey privateKey;
    private final X509Certificate certificate;

    public SAMLSigner(@NonNull X509Certificate certificate, @NonNull PrivateKey privateKey) {
        this.certificate = certificate;
        this.privateKey = privateKey;
    }

    public String sign(String filledXml) throws AuthenticationException {
        try {
            var builder = DocumentBuilderFactory.newDefaultNSInstance().newDocumentBuilder();
            var doc = builder.parse(new ByteArrayInputStream(filledXml.getBytes(StandardCharsets.UTF_8)));

            removeExistingSignatures(doc);
            updateTimestamp(doc);
            replaceAllCertificates(doc, certificate);
            setupIdAttributes(doc);
            signSamlAssertion(doc, privateKey, certificate);
            signSoapHeaderElements(doc, privateKey, certificate);

            return XmlUtils.writeDocumentToString(doc);
        } catch (IOException | ParserConfigurationException | TransformerException e) {
            // Should never happen.
            // - IOException: because we read from string
            // - ParserConfigurationException: static config
            // - TransformerException: we know that the input will be well-formed XML so it should be serializable
            throw new IllegalStateException(e);
        } catch (SAXException e) {
            throw new AuthenticationException("XML parse error", e);
        }
    }

    // Can be removed if we update the template at a later stage
    private static void removeExistingSignatures(Document doc) {
        var signatures = doc.getElementsByTagNameNS(XMLSignature.XMLNS, "Signature");
        for (var i = signatures.getLength() - 1; i >= 0; i--) {
            var signature = signatures.item(i);
            signature.getParentNode().removeChild(signature);
        }
    }

    private static void updateTimestamp(Document doc) {
        // Get current time
        var currentTime = Instant.now().truncatedTo(ChronoUnit.SECONDS)
            .atZone(java.time.ZoneOffset.UTC)
            .format(DateTimeFormatter.ISO_INSTANT);

        // Calculate NotOnOrAfter (2 hours from now)
        var notOnOrAfter = Instant.now().plus(2, ChronoUnit.HOURS)
            .truncatedTo(ChronoUnit.SECONDS)
            .atZone(java.time.ZoneOffset.UTC)
            .format(DateTimeFormatter.ISO_INSTANT);

        // Update SOAP header timestamp
        var timestampNodes = doc.getElementsByTagNameNS(XmlNamespaces.WSU.uri(), "Timestamp");

        if (timestampNodes.getLength() > 0) {
            var timestampElement = (Element) timestampNodes.item(0);

            // Find the Created element
            var createdNodes = timestampElement.getElementsByTagNameNS(
                XmlNamespaces.WSU.uri(),
                "Created"
            );

            if (createdNodes.getLength() > 0) {
                var createdElement = (Element) createdNodes.item(0);
                createdElement.setTextContent(currentTime);
            }
        }

        // Update SAML Assertion timestamps
        var assertion = (Element) doc.getElementsByTagNameNS(XmlNamespaces.SAML.uri(), "Assertion")
            .item(0);
        if (assertion != null) {
            // Update IssueInstant
            assertion.setAttribute("IssueInstant", currentTime);

            // Update Conditions
            var conditionsNodes = assertion.getElementsByTagNameNS(XmlNamespaces.SAML.uri(), "Conditions");
            if (conditionsNodes.getLength() > 0) {
                var conditionsElement = (Element) conditionsNodes.item(0);
                conditionsElement.setAttribute("NotBefore", currentTime);
                conditionsElement.setAttribute("NotOnOrAfter", notOnOrAfter);
            }

            // Update AuthnStatement
            var authnStatementNodes = assertion.getElementsByTagNameNS(XmlNamespaces.SAML.uri(), "AuthnStatement");
            if (authnStatementNodes.getLength() > 0) {
                var authnStatementElement = (Element) authnStatementNodes.item(0);
                authnStatementElement.setAttribute("AuthnInstant", currentTime);
            }

            log.info("Updated SAML Assertion timestamps - IssueInstant: {}, NotBefore: {}, NotOnOrAfter: {}, AuthnInstant: {}", currentTime, currentTime, notOnOrAfter, currentTime);
        }
    }

    private static void replaceAllCertificates(Document doc, X509Certificate cert) {
        // Replace certificates in all X509Certificate elements
        var certNodes = doc.getElementsByTagNameNS(XMLSignature.XMLNS, "X509Certificate");
        byte[] encodedCert;
        try {
            encodedCert = cert.getEncoded();
        } catch (CertificateEncodingException e) {
            throw new IllegalStateException(e);
        }
        var newCertValue = Base64.getEncoder().encodeToString(encodedCert);

        for (var i = 0; i < certNodes.getLength(); i++) {
            var certNode = certNodes.item(i);
            certNode.setTextContent(newCertValue);
        }

        log.debug("Replaced {} certificates in template.", certNodes.getLength());
    }

    private static void setupIdAttributes(Document doc) {
        var idsToSign = List.of("body", "ts", "messageID", "action");
        for (var id : idsToSign) {
            var el = findElementById(doc, id);
            if (el != null) {
                el.setIdAttributeNS(XmlNamespaces.WSU.uri(), "Id", true);
            } else {
                log.warn("Element with wsu:Id '{}' not found", id);
            }
        }
    }

    private static void signSamlAssertion(Document doc, PrivateKey privateKey, X509Certificate cert) throws AuthenticationException {
        // Try multiple approaches to find the SAML assertion
        Element assertion = null;

        // First try with namespace
        var assertionNodes = doc.getElementsByTagNameNS(XmlNamespaces.SAML.uri(), "Assertion");
        if (assertionNodes.getLength() > 0) {
            assertion = (Element) assertionNodes.item(0);
        } else {
            // TODO[HBG] why would we ever need this else branch? can we not assume namespaces?
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
        if (assertion == null) {
            throw new AuthenticationException("Could not find Assertion element");
        }
        // Set ID attribute
        assertion.setIdAttribute("ID", true);
        var assertionId = assertion.getAttribute("ID");

        var sigFactory = XMLSignatureFactory.getInstance("DOM");

        // Create reference to the assertion
        try {
            var ref = sigFactory.newReference(
                "#" + assertionId,
                sigFactory.newDigestMethod(DigestMethod.SHA256, null),
                List.of(
                    sigFactory.newTransform(Transform.ENVELOPED, (TransformParameterSpec) null),
                    sigFactory.newTransform(CanonicalizationMethod.EXCLUSIVE, (TransformParameterSpec) null)
                ),
                null, null
            );

            // Create SignedInfo
            var signedInfo = sigFactory.newSignedInfo(
                sigFactory.newCanonicalizationMethod(CanonicalizationMethod.EXCLUSIVE, (C14NMethodParameterSpec) null),
                sigFactory.newSignatureMethod(SignatureMethod.RSA_SHA256, null),
                Collections.singletonList(ref)
            );

            // Create KeyInfo
            var keyInfoFactory = sigFactory.getKeyInfoFactory();
            var keyInfo = keyInfoFactory.newKeyInfo(
                Collections.singletonList(
                    keyInfoFactory.newX509Data(Collections.singletonList(cert))
                )
            );

            // Find the Issuer element to insert signature after it
            var issuer = (Element) assertion.getElementsByTagNameNS(XmlNamespaces.SAML.uri(), "Issuer")
                .item(0);

            // Create signature context
            var signContext = new DOMSignContext(privateKey, assertion, issuer != null ? issuer.getNextSibling() : null);
            signContext.setDefaultNamespacePrefix("ds");
            signContext.setIdAttributeNS(assertion, null, "ID");

            // Create and sign the signature
            var signature = sigFactory.newXMLSignature(signedInfo, keyInfo, null, "OCESSignature", null);
            signature.sign(signContext);
        } catch (InvalidAlgorithmParameterException | NoSuchAlgorithmException e) {
            throw new IllegalStateException(e);
        } catch (MarshalException | XMLSignatureException e) {
            throw new AuthenticationException("Signing failed", e);
        }
        log.debug("SAML Assertion signed");
    }

    private static void signSoapHeaderElements(Document doc, PrivateKey privateKey, X509Certificate cert) throws AuthenticationException {
        var sigFactory = XMLSignatureFactory.getInstance("DOM");
        var idsToSign = Set.of("body", "ts", "messageID", "action");

        // Create references for all elements to be signed
        List<Reference> references = new ArrayList<>();
        try {
            for (var id : idsToSign) {
                var el = findElementById(doc, id);
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
                throw new AuthenticationException("No elements found to sign in SOAP header");
            }
            var signedInfo = sigFactory.newSignedInfo(
                sigFactory.newCanonicalizationMethod(CanonicalizationMethod.EXCLUSIVE, (C14NMethodParameterSpec) null),
                sigFactory.newSignatureMethod(SignatureMethod.RSA_SHA1, null), // TODO: SOSI team has been notified that SHA1 should be updated. Probable change to SHA256.
                references
            );
            var keyInfoFactory = sigFactory.getKeyInfoFactory();
            var keyInfo = keyInfoFactory.newKeyInfo(
                Collections.singletonList(
                    keyInfoFactory.newX509Data(Collections.singletonList(cert))
                )
            );
            var securityElement = (Element) doc.getElementsByTagNameNS(
                XmlNamespaces.WSSE.uri(),
                "Security"
            ).item(0);
            if (securityElement == null) {
                throw new AuthenticationException("Security element not found");
            }
            var signContext = new DOMSignContext(privateKey, securityElement);
            signContext.setDefaultNamespacePrefix("ds");

            // Set up ID attributes for the sign context
            for (var id : idsToSign) {
                var el = findElementById(doc, id);
                if (el != null) {
                    signContext.setIdAttributeNS(el, XmlNamespaces.WSU.uri(), "Id");
                }
            }

            // Create and sign the signature
            var signature = sigFactory.newXMLSignature(signedInfo, keyInfo);

            // Mutates "doc" in place. "signContext" has a reference to doc via "securityElement".
            signature.sign(signContext);

            log.info("SOAP header elements signed successfully");
        } catch (NoSuchAlgorithmException | InvalidAlgorithmParameterException e) {
            throw new IllegalStateException(e);
        } catch (XMLSignatureException | MarshalException e) {
            throw new AuthenticationException("Something went wrong when signing the SOAP header elements", e);
        }
    }

    private static Element findElementById(Document doc, String id) {
        // Use linear search to find element by attribute wss:Id.
        // The documents are small enough that optimizing this has no real impact.
        var all = doc.getElementsByTagNameNS("*", "*");
        for (var i = 0; i < all.getLength(); i++) {
            var el = (Element) all.item(i);
            if (el.hasAttributeNS(XmlNamespaces.WSU.uri(), "Id") &&
                el.getAttributeNS(XmlNamespaces.WSU.uri(), "Id")
                    .equals(id)) {
                return el;
            }
        }
        return null;
    }
}
