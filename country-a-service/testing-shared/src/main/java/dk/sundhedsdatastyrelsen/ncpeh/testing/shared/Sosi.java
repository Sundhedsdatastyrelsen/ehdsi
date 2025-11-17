package dk.sundhedsdatastyrelsen.ncpeh.testing.shared;

import dk.sundhedsdatastyrelsen.ncpeh.authentication.AuthenticationService;
import dk.sundhedsdatastyrelsen.ncpeh.authentication.AuthenticationServiceCached;
import dk.sundhedsdatastyrelsen.ncpeh.authentication.AuthenticationServiceImpl;
import dk.sundhedsdatastyrelsen.ncpeh.authentication.CertificateUtils;
import dk.sundhedsdatastyrelsen.ncpeh.authentication.EuropeanHcpIdwsToken;
import dk.sundhedsdatastyrelsen.ncpeh.authentication.idcard.DgwsIdCardRequest;
import dk.sundhedsdatastyrelsen.ncpeh.client.NspClientDgws;
import dk.sundhedsdatastyrelsen.ncpeh.base.utils.test.TestUtils;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;

import java.io.ByteArrayInputStream;
import java.net.URI;
import java.util.Base64;
import java.util.regex.Pattern;

/// Only for use in tests.
public class Sosi {
    private static AuthenticationService authService;
    private static String soapHeader;
    private static final URI sosiUri = URI.create("https://test2-cnsp.ekstern-test.nspop.dk:8443/sts/services/DKNCPBST2EHDSIIdws");
    public static final URI sosiOrganisationDgwsUri = URI.create("http://test2.ekstern-test.nspop.dk:8080/sts/services/NewSecurityTokenService");
    public static final URI sosiPersonalDgwsUri = URI.create("http://test2.ekstern-test.nspop.dk:8080/sts/services/BST2SOSI");

    public interface Audience {
        String value();
        Audience FMK = () -> "https://fmk";
        Audience DDV = () -> "https://ddv";
    }

    public static EuropeanHcpIdwsToken getToken(Audience audience) {
        return getToken(audience, "2262");
    }

    @SneakyThrows
    public static EuropeanHcpIdwsToken getToken(Audience audience, String role) {
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

            var idwsConfig = new AuthenticationServiceImpl.IdwsConfiguration(
                sosiUri,
                signingKey,
                "https://ehdsi-idp.testkald.nspop.dk");
            authService = new AuthenticationServiceCached(
                new AuthenticationServiceImpl(idwsConfig, null),
                idwsConfig.issuer());
        }
        if (soapHeader == null) {
            soapHeader = TestUtils.slurp(TestUtils.resource("openncp_soap_header.xml"));
        }
        return authService.xcaSoapHeaderToIdwsToken(changeRoleInHeader(soapHeader, role), audience.value());
    }

    private static String changeRoleInHeader(String soapHeader, String desiredRole) {
        // - replace <Role.../> element with requested one.
        var rolePattern = Pattern.compile("<Role[^>]+>");
        var roleToInput = String.format(
            "<Role xmlns=\"urn:hl7-org:v3\" code=\"%s\" codeSystem=\"2.16.840.1.113883.2.9.6.2.7\" codeSystemName=\"ISCO\" displayName=\"%s\"/>",
            desiredRole,
            roleToDisplayName(desiredRole));
        var withUpdatedRole = rolePattern.matcher(soapHeader).replaceFirst(roleToInput);

        // - replace Attribute with name urn:ehdsi:names:subject:healthcare-facility-type with requested one.
        var facilityTypePattern = Pattern.compile("<saml2:Attribute\\b[^>]*\\bName\\s*=\\s*\"urn:ehdsi:names:subject:healthcare-facility-type\"[^>]*>.*?</saml2:Attribute>");
        var requestedFacilityElement = String.format(
            "<saml2:Attribute FriendlyName=\"eHealth DSI Healthcare Facility Type\" Name=\"urn:ehdsi:names:subject:healthcare-facility-type\" NameFormat=\"urn:oasis:names:tc:SAML:2.0:attrname-format:uri\"><saml2:AttributeValue xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:type=\"xsd:string\">%s</saml2:AttributeValue></saml2:Attribute>",
            roleToWorkplace(desiredRole));
        return facilityTypePattern.matcher(withUpdatedRole).replaceFirst(requestedFacilityElement);
    }

    private static String roleToDisplayName(String role) {
        return switch (role) {
            case "221" -> "Medical Doctors";
            case "2262" -> "Pharmacists";
            case "3213" -> "Pharmaceutical technicians and assistants";
            default -> throw new RuntimeException("Unsupported role to display name in test: " + role);
        };
    }

    private static String roleToWorkplace(String role) {
        return switch (role) {
            case "221" -> "Hospital";
            default -> "Pharmacy";
        };
    }

    public static final NspClientDgws nspClientDgws = new NspClientDgws(
        new AuthenticationServiceCached(
            new AuthenticationServiceImpl(
                null,
                new DgwsIdCardRequest.Configuration(
                    "33257872",
                    "NCPeH-DK",
                    "NCPeH-DK",
                    "Sundhedsdatastyrelsen")),
            null));
}
