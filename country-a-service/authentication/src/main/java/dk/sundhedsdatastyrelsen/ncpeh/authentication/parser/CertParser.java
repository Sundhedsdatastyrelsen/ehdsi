package dk.sundhedsdatastyrelsen.ncpeh.authentication.parser;

import lombok.extern.slf4j.Slf4j;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.naming.ldap.LdapName;
import javax.naming.ldap.Rdn;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Base64;

@Slf4j
public class CertParser {

    public String parse(String soapHeader) {
        try {
            String base64Certificate = extractCertificateFromSoap(soapHeader);
            if (base64Certificate == null) return null;
            byte[] certBytes = Base64.getDecoder().decode(base64Certificate.replaceAll("\\s", ""));
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            X509Certificate cert = (X509Certificate) cf.generateCertificate(new ByteArrayInputStream(certBytes));
            return getCountryCodeFromCert(cert, "C");
        } catch (Exception e) {
            log.error("Failed to parse certificate", e);
        }
        return null;
    }

    private String extractCertificateFromSoap(String soapXml) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true); // Handle xmlns:ds
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new ByteArrayInputStream(soapXml.getBytes()));

            NodeList certNodes = doc.getElementsByTagNameNS("http://www.w3.org/2000/09/xmldsig#", "X509Certificate");
            if (certNodes.getLength() > 0) {
                return certNodes.item(0).getTextContent().replaceAll("\\s+", "");
            } else {
                System.err.println("X509Certificate element not found.");
            }
        } catch (Exception e) {
            log.error("Failed to extract certificate from SOAP XML", e);
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
            log.error("Failed to extract country code from certificate", e);
        }
        return null;
    }
}
