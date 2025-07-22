package dk.sundhedsdatastyrelsen.ncpeh.authentication;

import lombok.extern.slf4j.Slf4j;

import javax.naming.InvalidNameException;
import javax.naming.ldap.LdapName;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Base64;

/**
 * A collection of functions for working with X509 certificates.
 */
@Slf4j
public class CertificateUtils {
    private CertificateUtils() {
    }

    public record CertificateWithPrivateKey(
        X509Certificate certificate,
        PrivateKey privateKey
    ) {
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
    public static CertificateWithPrivateKey loadCertificateFromKeystore(InputStream is, String keyAlias, String password) throws AuthenticationException {
        try {
            var ks = KeyStore.getInstance("PKCS12");
            ks.load(is, password.toCharArray());
            return new CertificateWithPrivateKey(
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

    public static CertificateWithPrivateKey loadCertificateFromKeystore(Path keystoreUri, String keyAlias, String password) throws AuthenticationException {
        try (var is = new BufferedInputStream(Files.newInputStream(keystoreUri))) {
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
}
