package dk.sundhedsdatastyrelsen.ncpeh.authentication;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import javax.naming.InvalidNameException;
import javax.naming.ldap.LdapName;
import javax.xml.crypto.MarshalException;
import javax.xml.crypto.dsig.CanonicalizationMethod;
import javax.xml.crypto.dsig.DigestMethod;
import javax.xml.crypto.dsig.Reference;
import javax.xml.crypto.dsig.SignatureMethod;
import javax.xml.crypto.dsig.Transform;
import javax.xml.crypto.dsig.XMLSignatureException;
import javax.xml.crypto.dsig.XMLSignatureFactory;
import javax.xml.crypto.dsig.dom.DOMSignContext;
import javax.xml.crypto.dsig.spec.C14NMethodParameterSpec;
import javax.xml.crypto.dsig.spec.TransformParameterSpec;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

/**
 * A collection of functions for working with X509 certificates.
 */
@Slf4j
public class CertificateUtils {
    private CertificateUtils() {
    }

    /**
     * Extract a certificate/key pair from a JKS.  The password is both used for unlocking the JKS and for unlocking the
     * key in the keystore (they must be the same).
     *
     * @param is       stream to read the keystore file from.
     * @param keyAlias the alias of the certificate pair in the JKS
     * @param password password for JKS and certificate pair
     * @throws AuthenticationException if the keystore cannot be loaded
     */
    @NonNull
    public static CertificateAndKey loadCertificateFromKeystore(InputStream is, String keyAlias, String password) throws AuthenticationException {
        try {
            // keystore type is changed once we load the keystore, so it doesn't matter what we put in this constructor.
            var ks = KeyStore.getInstance("jks");
            ks.load(is, password.toCharArray());
            return new CertificateAndKey(
                (X509Certificate) ks.getCertificate(keyAlias),
                (PrivateKey) ks.getKey(keyAlias, password.toCharArray())
            );
        } catch (IOException | CertificateException | KeyStoreException | NoSuchAlgorithmException |
                 UnrecoverableKeyException e) {
            if (e instanceof UnrecoverableKeyException || e.getCause() instanceof UnrecoverableKeyException) {
                throw new AuthenticationException("Invalid password for keystore", e);
            }
            throw new AuthenticationException("Cannot load keystore", e);
        }
    }

    public static CertificateAndKey loadCertificateFromKeystore(Path keystore, String keyAlias, String password) throws AuthenticationException {
        log.info("Loading certificate with alias {} from keystore {}", keyAlias, keystore);
        try (var is = new BufferedInputStream(Files.newInputStream(keystore))) {
            return loadCertificateFromKeystore(is, keyAlias, password);
        } catch (IOException e) {
            throw new AuthenticationException("Could not open keystore URI", e);
        }
    }

    /**
     * Convert a base64 string into an X509 certificate.
     * Whitespace is ignored.
     *
     * @throws AuthenticationException if the input is not a valid X509 certificate.
     */
    public static X509Certificate fromBase64(String certB64) throws AuthenticationException {
        var sanitized = certB64.replaceAll("\\s+", "");
        var certBytes = Base64.getDecoder().decode(sanitized);
        try {
            return (X509Certificate) CertificateFactory.getInstance("X.509")
                .generateCertificate(new ByteArrayInputStream(certBytes));
        } catch (CertificateException e) {
            throw new AuthenticationException("Cannot parse base64 string into an X509 certificate", e);
        }
    }

    /**
     * Extract the country code from a certificate by locating the "C=..." distinguished name.
     */
    public static String extractCountryCode(X509Certificate cert) throws AuthenticationException {
        try {
            var ldapName = new LdapName(cert.getSubjectX500Principal().getName());
            for (var rdn : ldapName.getRdns()) {
                if (rdn.getType().equals("C")) {
                    return rdn.getValue().toString();
                }
            }
        } catch (InvalidNameException e) {
            throw new AuthenticationException("Invalid certificate format", e);
        }
        throw new AuthenticationException("No country code in certificate");
    }

    /// Signs an XML element with the specified certificate and key.
    ///
    /// The signature is inserted into the document at the specified location, either as a child
    /// of the root element or as a sibling to the specified nextSibling node.
    ///
    /// The elements matching the `referenceUris` should have their ID attributes marked as IdAttributes,
    /// otherwise validation might fail.
    ///
    /// @param rootElement   the root element of the XML document to be signed
     /// @param nextSibling   the node before which the signature should be inserted, or null to append as child of rootElement
     /// @param referenceUris list of element IDs to be included in the signature (typically "#id" format)
     /// @param certificate   the certificate and private key pair used for signing
     /// @throws AuthenticationException  if signing fails due to cryptographic or XML processing errors
     /// @throws IllegalArgumentException if any of the parameters are invalid
    public static void signXml(Element rootElement, Node nextSibling, List<String> referenceUris, CertificateAndKey certificate) throws AuthenticationException {
        try {
            // Make the internal representation of the DOM consistent before signing, by
            // e.g. merging adjacent text-nodes.
            rootElement.getOwnerDocument().normalizeDocument();
            var sigFactory = XMLSignatureFactory.getInstance("DOM");

            // Define the transforms to be applied to each referenced element
            // - ENVELOPED: Removes the signature element itself from the signed content
            // - EXCLUSIVE: Applies exclusive canonicalization to normalize whitespace and namespace handling
            var transforms = List.of(
                sigFactory.newTransform(Transform.ENVELOPED, (TransformParameterSpec) null),
                sigFactory.newTransform(CanonicalizationMethod.EXCLUSIVE, (TransformParameterSpec) null));

            // Each reference specifies which part of the document is included in the signature.
            List<Reference> references = new ArrayList<>();
            for (var id : referenceUris) {
                references.add(sigFactory.newReference(
                    id,
                    sigFactory.newDigestMethod(DigestMethod.SHA256, null),
                    transforms,
                    null,
                    null));
            }

            // This is the core of the XML signature that describes what and how it was signed
            var signedInfo = sigFactory.newSignedInfo(
                sigFactory.newCanonicalizationMethod(CanonicalizationMethod.EXCLUSIVE, (C14NMethodParameterSpec) null),
                sigFactory.newSignatureMethod(SignatureMethod.RSA_SHA256, null),
                references
            );

            var keyInfoFactory = sigFactory.getKeyInfoFactory();
            var keyInfo = keyInfoFactory.newKeyInfo(List.of(keyInfoFactory.newX509Data(List.of(certificate.certificate()))));

            var signContext = new DOMSignContext(certificate.privateKey(), rootElement);
            if (nextSibling != null) {
                // Insert signature before the specified sibling node
                signContext.setNextSibling(nextSibling);
            }
            // Otherwise, the signature will be appended as a child of the root element

            // Create and apply the XML signature
            var signature = sigFactory.newXMLSignature(signedInfo, keyInfo);
            signature.sign(signContext);
        } catch (MarshalException | XMLSignatureException | NoSuchAlgorithmException |
                 InvalidAlgorithmParameterException e) {
            throw new AuthenticationException("Signing failed", e);
        }
    }
}
