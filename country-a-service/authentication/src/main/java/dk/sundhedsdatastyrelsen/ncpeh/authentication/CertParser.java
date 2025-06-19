package dk.sundhedsdatastyrelsen.ncpeh.authentication;

import javax.naming.ldap.LdapName;
import javax.naming.ldap.Rdn;
import java.io.ByteArrayInputStream;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Base64;

public class CertParser {

    public String parse(String base64Certificate) {
        try {
            byte[] certBytes = Base64.getDecoder().decode(base64Certificate.replaceAll("\\s", ""));
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            X509Certificate cert = (X509Certificate) cf.generateCertificate(new ByteArrayInputStream(certBytes));
            return getCountryCodeFromCert(cert, "C");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getCountryCodeFromCert(X509Certificate cert, String rdnType) {
        try {
            LdapName ldapName = new LdapName(cert.getSubjectX500Principal().getName());
            for (Rdn rdn : ldapName.getRdns()) {
                if(rdn.getType().equals(rdnType)) {
                    return rdn.getValue().toString();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
