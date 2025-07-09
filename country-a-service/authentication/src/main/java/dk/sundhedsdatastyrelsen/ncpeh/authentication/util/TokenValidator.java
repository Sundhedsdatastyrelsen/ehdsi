package dk.sundhedsdatastyrelsen.ncpeh.authentication.util;


import org.w3c.dom.*;

import javax.xml.crypto.dsig.*;
import javax.xml.crypto.dsig.dom.DOMValidateContext;
import javax.xml.parsers.*;
import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.PublicKey;
import java.security.cert.X509Certificate;

public class TokenValidator {

    private static final String P12_PATH = "/home/jls/repos/work/sds/ehdsi/country-a-service/authentication/src/main/resources/epps-sosi-sts-client.p12";
    private static final String P12_PASSWORD = "R_d9ZzEQ93ry"; // Replace with real password

    public static void main(String[] args) throws Exception {
        System.setProperty("org.jcp.xml.dsig.secureValidation", "false");
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true);
        DocumentBuilder builder = dbf.newDocumentBuilder();
        Document doc = builder.parse(new FileInputStream("/home/jls/repos/work/sds/ehdsi/country-a-service/authentication/src/main/resources/envelope/self_signed_soap_envelope.xml"));

        // Register ID attributes for signature resolution
        setIdAttribute(doc, "wsu:Id", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd");
        setIdAttribute(doc, "ID", ""); // for SAML assertion

        NodeList signatures = doc.getElementsByTagNameNS(XMLSignature.XMLNS, "Signature");
        if (signatures.getLength() != 2) {
            throw new RuntimeException("Expected 2 signatures, found " + signatures.getLength());
        }

        Element headerSignature = (Element) signatures.item(0);
        Element assertionSignature = (Element) signatures.item(1);

        X509Certificate cert = loadCertificateFromP12(P12_PATH, P12_PASSWORD);

        boolean headerValid = validateSignature(headerSignature, cert.getPublicKey());
        boolean assertionValid = validateSignature(assertionSignature, cert.getPublicKey());

        System.out.println("Header Signature Valid: " + headerValid);
        System.out.println("Assertion Signature Valid: " + assertionValid);
    }

    private static void setIdAttribute(Document doc, String attrName, String ns) {
        NodeList elements = doc.getElementsByTagNameNS("*", "*");
        for (int i = 0; i < elements.getLength(); i++) {
            Element elem = (Element) elements.item(i);
            if (elem.hasAttributeNS(ns, attrName) || elem.hasAttribute(attrName)) {
                elem.setIdAttribute(attrName, true);
            }
        }
    }

    private static boolean validateSignature(Element signatureElement, PublicKey publicKey) throws Exception {
        XMLSignatureFactory factory = XMLSignatureFactory.getInstance("DOM");
        DOMValidateContext context = new DOMValidateContext(publicKey, signatureElement);
        XMLSignature signature = factory.unmarshalXMLSignature(context);
        boolean valid = signature.validate(context);

        if (!valid) {
            System.out.println("Invalid signature: " + signatureElement.getAttribute("Id"));
            for (Object obj : signature.getSignedInfo().getReferences()) {
                Reference ref = (Reference) obj;
                System.out.println("  - Reference URI: " + ref.getURI() + " valid? " + ref.validate(context));
            }
        }

        return valid;
    }

    private static X509Certificate loadCertificateFromP12(String path, String password) throws Exception {
        KeyStore keystore = KeyStore.getInstance("PKCS12");
        try (FileInputStream fis = new FileInputStream(path)) {
            keystore.load(fis, password.toCharArray());
        }
        String alias = keystore.aliases().nextElement();
        return (X509Certificate) keystore.getCertificate(alias);
    }

    private static String extractCertBase64(Element container) {
        NodeList certs = container.getElementsByTagNameNS(XMLSignature.XMLNS, "X509Certificate");
        if (certs.getLength() > 0) {
            return certs.item(0).getTextContent().replaceAll("\\s+", "");
        }
        return null;
    }
}

