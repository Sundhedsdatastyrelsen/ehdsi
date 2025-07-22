package dk.sundhedsdatastyrelsen.ncpeh.authentication;

import lombok.extern.slf4j.Slf4j;

import javax.naming.InvalidNameException;
import javax.naming.ldap.LdapName;
import java.io.ByteArrayInputStream;
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

    /**
     * Convert a base64 string into an X509 certificate.
     * Whitespace is ignored.
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
