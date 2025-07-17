package dk.sundhedsdatastyrelsen.ncpeh.authentication;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

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

        assertThat(assertion.getId(), is("assertion-123"));
        assertThat(assertion.getVersion(), is("2.0"));
        assertThat(assertion.getIssuer(), is("http://issuer.example.com"));

        assertThat(assertion.getSignature(), is(notNullValue()));
        assertThat(assertion.getSignature().getSignatureMethodAlgorithm(), is("RSA_SHA256"));

        assertThat(assertion.getSubject(), is(notNullValue()));
        assertThat(assertion.getSubject().getNameIdValue(), is("subject-user"));

        assertThat(assertion.getConditions(), is(notNullValue()));
        assertThat(assertion.getConditions().getAudience(), is("http://audience.example.com"));

        assertThat(assertion.getAttributes(), hasSize(2));
        assertThat(
                assertion.getAttributes(),
                contains(
                        hasProperty("friendlyName", is("XSPA Subject")),
                        hasProperty("values", hasItem("doctor"))
                ));
        assertThat(assertion.getAttributes().get(0).getFriendlyName(), is("XSPA Subject"));
        assertThat(assertion.getAttributes().get(1).getValues(), hasItem("doctor"));
    }
}
