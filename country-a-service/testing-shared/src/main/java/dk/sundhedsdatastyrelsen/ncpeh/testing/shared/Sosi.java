package dk.sundhedsdatastyrelsen.ncpeh.testing.shared;

import dk.sundhedsdatastyrelsen.ncpeh.authentication.AuthenticationService;
import dk.sundhedsdatastyrelsen.ncpeh.authentication.CertificateUtils;
import dk.sundhedsdatastyrelsen.ncpeh.authentication.EuropeanHcpIdwsToken;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;

import java.io.ByteArrayInputStream;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.regex.Pattern;

/// Only for use in tests.
public class Sosi {
    private static AuthenticationService authService;
    private static String soapHeader;
    private static final URI sosiUri = URI.create("https://test2-cnsp.ekstern-test.nspop.dk:8443/sts/services/DKNCPBST2EHDSIIdws");
    public static final URI sosiOrganisationDgwsUri = URI.create("http://test2.ekstern-test.nspop.dk:8080/sts/services/NewSecurityTokenService");
    public static final URI sosiPersonalDgwsUri = URI.create("http://test2.ekstern-test.nspop.dk:8080/sts/services/BST2SOSI");

    public static EuropeanHcpIdwsToken getToken() {
        return Sosi.getToken("2262");
    }

    @SneakyThrows
    public static EuropeanHcpIdwsToken getToken(String role) {
        if (authService == null) {
            var base64 = System.getenv("CERT_BASE_64");
            var alias = System.getenv("CERT_ALIAS");
            var password = System.getenv("CERT_PASSWORD");
            if (StringUtils.isEmpty(base64) || StringUtils.isEmpty(alias) || StringUtils.isEmpty(password)) {
                throw new IllegalArgumentException("CERT_BASE_64, CERT_ALIAS, and CERT_PASSWORD must be set to run the test.");
            }
            var signingKey = CertificateUtils.loadCertificateFromKeystore(
                new ByteArrayInputStream(Base64.getDecoder().decode(base64)),
                alias,
                password);

            authService = new AuthenticationService(
                sosiUri,
                signingKey,
                "https://ehdsi-idp.testkald.nspop.dk");
        }
        if (soapHeader == null) {
            try (var is = Sosi.class.getClassLoader().getResourceAsStream("openncp_soap_header.xml")) {
                soapHeader = new String(is.readAllBytes(), StandardCharsets.UTF_8);
            }
        }
        return authService.xcaSoapHeaderToIdwsToken(changeRoleInHeader(soapHeader, role), "https://fmk");
    }

    private static String changeRoleInHeader(String soapHeader, String desiredRole) {
        // - replace <Role.../> element with one accepted by FMK
        var rolePattern = Pattern.compile("<Role[^>]+>");
        var roleToInput = String.format("<Role xmlns=\"urn:hl7-org:v3\" code=\"%s\" codeSystem=\"2.16.840.1.113883.2.9.6.2.7\" codeSystemName=\"ISCO\" displayName=\"%s\"/>", desiredRole, roleToDisplayName(desiredRole));

        var withHardcodedRole = rolePattern.matcher(soapHeader).replaceFirst(roleToInput);
        return withHardcodedRole;

//        // - replace Attribute with name urn:ehdsi:names:subject:healthcare-facility-type
//        // to "Pharmacy"
//        var facilityTypePattern = Pattern.compile("<saml2:Attribute\\b[^>]*\\bName\\s*=\\s*\"urn:ehdsi:names:subject:healthcare-facility-type\"[^>]*>.*?</saml2:Attribute>");
//        var fmkAcceptsThisFacilityTypeElement = "<saml2:Attribute FriendlyName=\"eHealth DSI Healthcare Facility Type\" Name=\"urn:ehdsi:names:subject:healthcare-facility-type\" NameFormat=\"urn:oasis:names:tc:SAML:2.0:attrname-format:uri\"><saml2:AttributeValue xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:type=\"xsd:string\">Pharmacy</saml2:AttributeValue></saml2:Attribute>";
//        return facilityTypePattern.matcher(withHardcodedRole).replaceFirst(fmkAcceptsThisFacilityTypeElement);
    }

    private static String roleToDisplayName(String role) {
        return switch (role) {
            case "221" -> "Medical Doctors";
            case "2262" -> "Pharmacists";
            case "3213" -> "Pharmaceutical technicians and assistants";
            default -> throw new RuntimeException("Unsupported role to display name: " + role);
        };
    }
}
