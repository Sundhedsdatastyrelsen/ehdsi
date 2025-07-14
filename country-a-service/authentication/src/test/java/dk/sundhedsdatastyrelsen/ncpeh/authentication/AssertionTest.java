package dk.sundhedsdatastyrelsen.ncpeh.authentication;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AssertionTest {

    @Test
    void shouldBuildCompleteAssertionCorrectly() {
        Assertion assertion = Assertion.builder()
            .id("assertion-123")
            .issueInstant("2025-07-13T12:00:00Z")
            .version("2.0")
            .issuer("http://issuer.example.com")
            .signature(Assertion.Signature.builder()
                .signatureMethodAlgorithm("RSA_SHA256")
                .digestMethodAlgorithm("SHA256")
                .digestValue("digest123")
                .signatureValue("signatureABC")
                .certificate("certXYZ")
                .build())
            .subject(Assertion.Subject.builder()
                .nameIdFormat("urn:oasis:names:tc:SAML:1.1:nameid-format:unspecified")
                .nameIdValue("subject-user")
                .confirmationMethod("urn:oasis:names:tc:SAML:2.0:cm:holder-of-key")
                .certificate("subject-cert")
                .build())
            .conditions(Assertion.Conditions.builder()
                .notBefore("2025-07-13T12:00:00Z")
                .notOnOrAfter("2025-07-13T14:00:00Z")
                .audience("http://audience.example.com")
                .build())
            .attributes(List.of(
                Assertion.Attribute.builder()
                    .friendlyName("XSPA Subject")
                    .name("urn:oasis:names:tc:xspa:1.0:subject:subject-id")
                    .values(List.of("user123"))
                    .build(),
                Assertion.Attribute.builder()
                    .friendlyName("XSPA Role")
                    .name("urn:oasis:names:tc:xacml:2.0:subject:role")
                    .values(List.of("doctor"))
                    .build()
            ))
            .build();

        assertEquals("assertion-123", assertion.getId());
        assertEquals("2.0", assertion.getVersion());
        assertEquals("http://issuer.example.com", assertion.getIssuer());

        assertNotNull(assertion.getSignature());
        assertEquals("RSA_SHA256", assertion.getSignature().getSignatureMethodAlgorithm());

        assertNotNull(assertion.getSubject());
        assertEquals("subject-user", assertion.getSubject().getNameIdValue());

        assertNotNull(assertion.getConditions());
        assertEquals("http://audience.example.com", assertion.getConditions().getAudience());

        assertNotNull(assertion.getAttributes());
        assertEquals(2, assertion.getAttributes().size());
        assertEquals("XSPA Subject", assertion.getAttributes().get(0).getFriendlyName());
        assertTrue(assertion.getAttributes().get(1).getValues().contains("doctor"));
    }
}
