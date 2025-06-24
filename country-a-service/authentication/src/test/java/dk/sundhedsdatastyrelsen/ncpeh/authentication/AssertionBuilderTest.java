package dk.sundhedsdatastyrelsen.ncpeh.authentication;

import dk.sundhedsdatastyrelsen.ncpeh.authentication.builder.AssertionBuilder;
import dk.sundhedsdatastyrelsen.ncpeh.authentication.model.Token;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AssertionBuilderTest {

    @Test
    void testBuildAssertionXml() throws Exception {
        // Create a test token with realistic values matching NCP-BST-maximal.xml
        Token token = Token.builder()
            .id("_c45e0c11-46ba-418d-bc9e-e0120b29b96f")
            .issueInstant("2025-01-09T12:39:41Z")
            .version("2.0")
            .issuer("https://t-ncp.sundhedsdatastyrelsen.dk")
            .signature(Token.Signature.builder()
                .signatureMethodAlgorithm("http://www.w3.org/2000/09/xmldsig#rsa-sha256")
                .digestMethodAlgorithm("http://www.w3.org/2000/09/xmldsig#sha256")
                .digestValue("l2bk8S+38yc2CF0wnLWhr4PhqbE=")
                .signatureValue("P6q4D4Mw5eOtpLHmZ3LDPRmNVRRFpG8x6vxGmbJ9kO/TTrynNq2/IOvvzDYiAW19b/GhT4hQcjThKbL8P6q4D4Mw5eOtpLHmZ3LDPRmNVRRFpG8x6vxGmbJ9kO/TTrynNq2/IOvvzKl+Yv5bucyMtPb7J/MbKrNjtsHdlJZ6HIopxkNpSA6fRxml87zM3Pp8eN2xoFlcX8SC0ny3KM/II4nSGszbm4XEMb/8X1njgBmE/kDTNWXxbwmahhIkwgBzsRBKVjUfQHw77hJhjvqg7EZ1u0HscIh9SpUsbyf/9/l0dEPajNi+LAIDqn5ikeXuAbJkCNDFJSEr8xJhciil3AQKqkq3TA9Mx7mY430c1jGq4o3h21TfxSz+5dbaeePwstLrR84/CioLj9B0uPMqk++hJgFpuA+X3VCnxOGMfxO02W/mFUTWyoUPOnj9hsUJkCPuYJnLNP7xq6IjhF1YTSPj47cxmBy2tN0iKBmprl3MbV9SfysPLbaggC6J9muu9s7XY2pLCMIn+HZSW/FfgFMhCoSzCvx2ANtqZWPlSTfvLULiDt9tVtft1")
                .certificate("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")
                .build())
            .subject(Token.Subject.builder()
                .nameIdFormat("urn:oasis:names:tc:SAML:1.1:nameid-format:unspecified")
                .nameIdValue("17486903-5ed0-4842-82e7-1f85cda996de")
                .confirmationMethod("urn:oasis:names:tc:SAML:2.0:cm:holder-of-key")
                .certificate("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")
                .build())
            .conditions(Token.Conditions.builder()
                .notOnOrAfter("2025-01-10T00:39:41Z")
                .audience("https://sts.sosi.dk/")
                .build())
            .attributes(List.of(
                Token.Attribute.builder()
                    .friendlyName("XSPA Subject")
                    .name("urn:oasis:names:tc:xspa:1.0:subject:subject-id")
                    .values(List.of("Femke Musterfrau"))
                    .build(),
                Token.Attribute.builder()
                    .friendlyName("XSPA Role")
                    .name("urn:oasis:names:tc:xacml:2.0:subject:role")
                    .values(List.of("2269"))
                    .build(),
                Token.Attribute.builder()
                    .friendlyName("Hl7 Permissions")
                    .name("urn:oasis:names:tc:xspa:1.0:subject:hl7:permission")
                    .values(List.of("urn:oasis:names:tc:xspa:1.0:subject:hl7:permission:PRD-004", "urn:oasis:names:tc:xspa:1.0:subject:hl7:permission:PRD-010"))
                    .build(),
                Token.Attribute.builder()
                    .friendlyName("EHDSI OnBehalfOf")
                    .name("urn:ehdsi:names:subject:on-behalf-of")
                    .values(List.of("2221"))
                    .build(),
                Token.Attribute.builder()
                    .friendlyName("XSPA Organization")
                    .name("urn:oasis:names:tc:xspa:1.0:subject:organization")
                    .values(List.of("Klinikverbund Oberbayern"))
                    .build(),
                Token.Attribute.builder()
                    .friendlyName("XSPA Organization Id")
                    .name("urn:oasis:names:tc:xspa:1.0:subject:organization-id")
                    .values(List.of("20155.1"))
                    .build(),
                Token.Attribute.builder()
                    .friendlyName("eHealth DSI Healthcare Facility Type")
                    .name("urn:ehdsi:names:subject:healthcare-facility-type")
                    .values(List.of("Resident Physician"))
                    .build(),
                Token.Attribute.builder()
                    .friendlyName("XSPA Purpose Of Use")
                    .name("urn:oasis:names:tc:xspa:1.0:subject:purposeofuse")
                    .values(List.of("TREATMENT"))
                    .build(),
                Token.Attribute.builder()
                    .friendlyName("XSPA Locality")
                    .name("urn:oasis:names:tc:xspa:1.0:environment:locality")
                    .values(List.of("Klinik am Berg, 83242 Reit im Winkl"))
                    .build(),
                Token.Attribute.builder()
                    .friendlyName("XUA Patient Id")
                    .name("urn:oasis:names:tc:xacml:2.0:resource:resource-id")
                    .values(List.of("0205756078^^^&1.2.208.176.1.2&ISO"))
                    .build(),
                Token.Attribute.builder()
                    .friendlyName("NSIS AssuranceLevel")
                    .name("https://data.gov.dk/concept/core/nsis/loa")
                    .values(List.of("3"))
                    .build(),
                Token.Attribute.builder()
                    .friendlyName("IDWS XUA SpecVersion")
                    .name("urn:dk:healthcare:saml:SpecVersion")
                    .values(List.of("eHDSI-IDWS-XUA-1.0"))
                    .build(),
                Token.Attribute.builder()
                    .friendlyName("IDWS XUA IssuancePolicy")
                    .name("urn:dk:healthcare:saml:IssuancePolicy")
                    .values(List.of("urn:dk:healthcare:ncp:eHDSI-strict"))
                    .build(),
                Token.Attribute.builder()
                    .friendlyName("EHDSI Country of Treatment")
                    .name("urn:dk:healthcare:saml:CountryOfTreatment")
                    .values(List.of("DE"))
                    .build()
            ))
            .build();

        // Build the assertion XML
        AssertionBuilder builder = new AssertionBuilder();
        String xml = builder.buildAssertionXml(token);

        // Verify the XML contains expected elements for NCP-BST format
        assertNotNull(xml);
        assertTrue(xml.contains("<Assertion"));
        assertTrue(xml.contains("ID=\"_c45e0c11-46ba-418d-bc9e-e0120b29b96f\""));
        assertTrue(xml.contains("IssueInstant=\"2025-01-09T12:39:41Z\""));
        assertTrue(xml.contains("Version=\"2.0\""));
        assertTrue(xml.contains("xmlns=\"urn:oasis:names:tc:SAML:2.0:assertion\""));
        assertTrue(xml.contains("xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\""));
        
        // Verify signature structure
        assertTrue(xml.contains("<Signature"));
        assertTrue(xml.contains("xmlns=\"http://www.w3.org/2000/09/xmldsig#\""));
        assertTrue(xml.contains("Algorithm=\"http://www.w3.org/2000/09/xmldsig#rsa-sha256\""));
        assertTrue(xml.contains("Algorithm=\"http://www.w3.org/2000/09/xmldsig#sha256\""));
        assertTrue(xml.contains("l2bk8S+38yc2CF0wnLWhr4PhqbE="));
        
        // Verify subject structure
        assertTrue(xml.contains("<Subject>"));
        assertTrue(xml.contains("<NameID"));
        assertTrue(xml.contains("Format=\"urn:oasis:names:tc:SAML:1.1:nameid-format:unspecified\""));
        assertTrue(xml.contains("17486903-5ed0-4842-82e7-1f85cda996de"));
        assertTrue(xml.contains("xsi:type=\"KeyInfoConfirmationDataType\""));
        
        // Verify conditions structure
        assertTrue(xml.contains("<Conditions>"));
        assertTrue(xml.contains("NotOnOrAfter=\"2025-01-10T00:39:41Z\""));
        assertTrue(xml.contains("<AudienceRestriction>"));
        assertTrue(xml.contains("https://sts.sosi.dk/"));
        
        // Verify attribute structure with actual values from token
        assertTrue(xml.contains("<AttributeStatement>"));
        assertTrue(xml.contains("FriendlyName=\"XSPA Subject\""));
        assertTrue(xml.contains("Femke Musterfrau"));
        assertTrue(xml.contains("FriendlyName=\"XSPA Role\""));
        assertTrue(xml.contains("code=\"2269\"")); // Verify the actual value is used
        assertTrue(xml.contains("FriendlyName=\"XSPA Purpose Of Use\""));
        assertTrue(xml.contains("code=\"TREATMENT\"")); // Verify the actual value is used
        assertTrue(xml.contains("FriendlyName=\"EHDSI OnBehalfOf\""));
        assertTrue(xml.contains("code=\"2221\"")); // Verify the actual value is used
        assertTrue(xml.contains("FriendlyName=\"XUA Patient Id\""));
        assertTrue(xml.contains("0205756078^^^&amp;1.2.208.176.1.2&amp;ISO")); // Verify the actual value is used
        
        // Verify complex attribute handling
        assertTrue(xml.contains("<Role"));
        assertTrue(xml.contains("xmlns=\"urn:hl7-org:v3\""));
        assertTrue(xml.contains("code=\"2269\""));
        assertTrue(xml.contains("displayName=\"Health professionals not elsewhere classified\""));
        assertTrue(xml.contains("<PurposeOfUse"));
        assertTrue(xml.contains("code=\"TREATMENT\""));
        
        System.out.println("Generated XML:");
        System.out.println(xml);
    }
} 