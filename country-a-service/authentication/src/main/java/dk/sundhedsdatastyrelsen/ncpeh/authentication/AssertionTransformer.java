package dk.sundhedsdatastyrelsen.ncpeh.authentication;

import java.util.List;
import java.util.Set;

public class AssertionTransformer {
    private AssertionTransformer() {
    }

    private static final Set<String> mandatoryAttributes = Set.of(
        "XSPA Subject",
        "XSPA Role",
        "Hl7 Permissions",
        "EHDSI OnBehalfOf",
        "XSPA Organization",
        "XSPA Organization ID",
        "eHealth DSI Healthcare Facility Type",
        "XSPA Purpose Of Use",
        "XSPA Locality"
    );

    /**
     * Transforms an assertion to NCP-BST format with the specified patient ID
     */
    public static Assertion transformToNcpBst(Assertion originalAssertion, String patientId, String countryCode) {
        return Assertion.builder()
            .id(originalAssertion.id())
            .issueInstant(originalAssertion.issueInstant())
            .version(originalAssertion.version())
            .issuer("https://t-ncp.sundhedsdatastyrelsen.dk") // Hardcoded for NCP-BST
            .signature(Assertion.Signature.builder()
                .signatureMethodAlgorithm("http://www.w3.org/2000/09/xmldsig#rsa-sha256")
                .digestMethodAlgorithm("http://www.w3.org/2000/09/xmldsig#sha256")
                .digestValue(originalAssertion.signature().digestValue())
                .signatureValue(originalAssertion.signature().signatureValue())
                .certificate(originalAssertion.signature().certificate())
                .build())
            .subject(Assertion.Subject.builder()
                .nameIdFormat("urn:oasis:names:tc:SAML:1.1:nameid-format:unspecified")
                .nameIdValue(patientId) // Use provided patientId instead of original
                .confirmationMethod("urn:oasis:names:tc:SAML:2.0:cm:holder-of-key")
                .certificate(originalAssertion.signature().certificate())
                .build())
            .conditions(Assertion.Conditions.builder()
                .notBefore(originalAssertion.conditions().notBefore())
                .notOnOrAfter(originalAssertion.conditions().notOnOrAfter())
                .audience("https://sts.sosi.dk/") // Hardcoded for NCP-BST
                .build())
            .attributes(buildNcpBstAttributes(originalAssertion, patientId, countryCode))
            .build();
    }

    private static List<Assertion.Attribute> buildNcpBstAttributes(Assertion originalAssertion, String patientId, String countryCode) {
        List<Assertion.Attribute> attributes = new java.util.ArrayList<>();

        // Map required attributes from original assertion
        for (var attr : originalAssertion.attributes()) {
            // Only include mandatory attributes for minimal mode
            if (mandatoryAttributes.contains(attr.friendlyName())) {
                List<String> values = attr.values();

                // TODO: Figure out how to determine role and purpose of use from assertion in soapheader

                if ("XSPA Role".equals(attr.friendlyName())) {
                    values = List.of("<Role xmlns=\"urn:hl7-org:v3\" code=\"221\" codeSystem=\"2.16.840.1.113883.2.9.6.2.7\" " +
                        "codeSystemName=\"ISCO\" displayName=\"Medical Doctors\" xsi:type=\"CE\"/>");
                } else if ("XSPA Purpose Of Use".equals(attr.friendlyName())) {
                    values = List.of("<PurposeOfUse xmlns=\"urn:hl7-org:v3\" code=\"TREATMENT\" " +
                        "codeSystem=\"urn:oasis:names:tc:xspa:1.0\" xsi:type=\"CE\"/>");
                }

                attributes.add(Assertion.Attribute.builder()
                    .friendlyName(attr.friendlyName())
                    .name(attr.name())
                    .values(values)
                    .build());
            }


        }

        // Add NCP-BST specific attributes
        attributes.add(Assertion.Attribute.builder()
            .friendlyName("XUA Patient Id")
            .name("urn:oasis:names:tc:xacml:2.0:resource:resource-id")
            .values(List.of(patientId))
            .build());

        attributes.add(Assertion.Attribute.builder()
            .friendlyName("NSIS AssuranceLevel")
            .name("https://data.gov.dk/concept/core/nsis/loa")
            .values(List.of("3"))
            .build());

        attributes.add(Assertion.Attribute.builder()
            .friendlyName("IDWS XUA SpecVersion")
            .name("urn:dk:healthcare:saml:SpecVersion")
            .values(List.of("eHDSI-IDWS-XUA-1.0"))
            .build());

        attributes.add(Assertion.Attribute.builder()
            .friendlyName("IDWS XUA IssuancePolicy")
            .name("urn:dk:healthcare:saml:IssuancePolicy")
            .values(List.of("urn:dk:healthcare:ncp:eHDSI-strict"))
            .build());

        attributes.add(Assertion.Attribute.builder()
            .friendlyName("EHDSI Country of Treatment")
            .name("urn:dk:healthcare:saml:CountryOfTreatment")
            .values(List.of(countryCode)) // Changed from DE to DK for Danish NCP
            .build());

        return attributes;
    }
}
