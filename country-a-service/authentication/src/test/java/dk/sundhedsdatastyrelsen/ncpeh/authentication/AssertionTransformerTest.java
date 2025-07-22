package dk.sundhedsdatastyrelsen.ncpeh.authentication;

import org.junit.jupiter.api.Test;

import java.util.List;

import static dk.sundhedsdatastyrelsen.ncpeh.authentication.FunMatcher.where;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class AssertionTransformerTest {

    @Test
    void shouldTransformToNcpBstWithExpectedFields() {
        // Given
        var patientId = "1234567890";
        var countryCode = "DK";

        var input = Assertion.builder()
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
        var result = AssertionTransformer.transformToNcpBst(input, patientId, countryCode);

        // Then
        assertThat(result.issuer(), is("https://t-ncp.sundhedsdatastyrelsen.dk"));
        assertThat(result.subject().nameIdValue(), is(patientId));
        assertThat(result.conditions().audience(), is("https://sts.sosi.dk/"));

        assertThat(result.attributes(), hasItem(allOf(
            where(Assertion.Attribute::friendlyName, is("XUA Patient Id")),
            where(Assertion.Attribute::values, hasItem(patientId))
        )));

        assertThat(result.attributes(), hasItem(allOf(
            where(Assertion.Attribute::friendlyName, is("EHDSI Country of Treatment")),
            where(Assertion.Attribute::values, hasItem(countryCode))
        )));

        assertThat(result.attributes(), hasItem(allOf(
            where(Assertion.Attribute::friendlyName, is("XSPA Role")),
            where(Assertion.Attribute::values, hasItem(containsString("Medical Doctors")))
        )));

        assertThat(result.attributes(), hasItem(allOf(
            where(Assertion.Attribute::friendlyName, is("XSPA Purpose Of Use")),
            where(Assertion.Attribute::values, hasItem(containsString("TREATMENT")))
        )));
    }

    private Assertion.Attribute buildAttr(String friendlyName, String name, List<String> values) {
        return Assertion.Attribute.builder()
            .friendlyName(friendlyName)
            .name(name)
            .values(values)
            .build();
    }
}
