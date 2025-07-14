package dk.sundhedsdatastyrelsen.ncpeh.authentication;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class AssertionTransformerTest {

    @Test
    void shouldTransformToNcpBstWithExpectedFields() {
        // Given
        String patientId = "1234567890";
        String countryCode = "DK";

        Assertion input = Assertion.builder()
            .id("test-id")
            .issueInstant("2025-07-13T12:00:00Z")
            .version("2.0")
            .issuer("http://original-issuer.com")
            .signature(Assertion.Signature.builder()
                .digestValue("digest")
                .signatureValue("sig")
                .certificate("cert")
                .build())
            .subject(Assertion.Subject.builder()
                .nameIdValue("original-subject")
                .build())
            .conditions(Assertion.Conditions.builder()
                .notBefore("2025-07-13T12:00:00Z")
                .notOnOrAfter("2025-07-13T14:00:00Z")
                .audience("http://original-audience.com")
                .build())
            .attributes(List.of(
                buildAttr("XSPA Subject", "urn:xspa:subject", List.of("user@example.com")),
                buildAttr("XSPA Role", "urn:xspa:role", List.of("original-role")),
                buildAttr("XSPA Purpose Of Use", "urn:xspa:purpose", List.of("original-purpose")),
                buildAttr("XSPA Organization", "urn:xspa:org", List.of("OrgName")),
                buildAttr("XSPA Organization ID", "urn:xspa:orgid", List.of("Org123")),
                buildAttr("XSPA Locality", "urn:xspa:loc", List.of("Copenhagen")),
                buildAttr("eHealth DSI Healthcare Facility Type", "urn:xspa:facility", List.of("Hospital")),
                buildAttr("Hl7 Permissions", "urn:xspa:permission", List.of("PRD-004", "PRD-010"))
            ))
            .build();

        // When
        Assertion result = AssertionTransformer.transformToNcpBst(input, patientId, countryCode);

        // Then
        assertEquals("https://t-ncp.sundhedsdatastyrelsen.dk", result.getIssuer());
        assertEquals(patientId, result.getSubject().getNameIdValue());
        assertEquals("https://sts.sosi.dk/", result.getConditions().getAudience());

        assertTrue(result.getAttributes().stream().anyMatch(a ->
            a.getFriendlyName().equals("XUA Patient Id") &&
                a.getValues().contains(patientId)
        ));

        assertTrue(result.getAttributes().stream().anyMatch(a ->
            a.getFriendlyName().equals("EHDSI Country of Treatment") &&
                a.getValues().contains(countryCode)
        ));

        assertTrue(result.getAttributes().stream().anyMatch(a ->
            a.getFriendlyName().equals("XSPA Role") &&
                a.getValues().get(0).contains("Medical Doctors")
        ));

        assertTrue(result.getAttributes().stream().anyMatch(a ->
            a.getFriendlyName().equals("XSPA Purpose Of Use") &&
                a.getValues().get(0).contains("TREATMENT")
        ));
    }

    private Assertion.Attribute buildAttr(String friendlyName, String name, List<String> values) {
        return Assertion.Attribute.builder()
            .friendlyName(friendlyName)
            .name(name)
            .values(values)
            .build();
    }
}
