package dk.sundhedsdatastyrelsen.ncpeh.authentication.transformer;

import dk.sundhedsdatastyrelsen.ncpeh.authentication.model.Assertion;

import java.util.List;

public class AssertionTransformer {

    /**
     * Transforms an assertion to NCP-BST format with the specified patient ID
     */
    public static Assertion transformToNcpBst(Assertion originalAssertion, String patientId) {
        return Assertion.builder()
            .id(originalAssertion.getId())
            .issueInstant(originalAssertion.getIssueInstant())
            .version(originalAssertion.getVersion())
            .issuer("https://t-ncp.sundhedsdatastyrelsen.dk") // Hardcoded for NCP-BST
            .signature(Assertion.Signature.builder()
                .signatureMethodAlgorithm("http://www.w3.org/2000/09/xmldsig#rsa-sha256")
                .digestMethodAlgorithm("http://www.w3.org/2000/09/xmldsig#sha256")
                .digestValue(originalAssertion.getSignature().getDigestValue())
                .signatureValue(originalAssertion.getSignature().getSignatureValue())
                .certificate(originalAssertion.getSignature().getCertificate())
                .build())
            .subject(Assertion.Subject.builder()
                .nameIdFormat("urn:oasis:names:tc:SAML:1.1:nameid-format:unspecified")
                .nameIdValue(patientId) // Use provided patientId instead of original
                .confirmationMethod("urn:oasis:names:tc:SAML:2.0:cm:holder-of-key")
                .certificate(originalAssertion.getSignature().getCertificate())
                .build())
            .conditions(Assertion.Conditions.builder()
                .notBefore(originalAssertion.getConditions().getNotBefore())
                .notOnOrAfter(originalAssertion.getConditions().getNotOnOrAfter())
                .audience("https://sts.sosi.dk/") // Hardcoded for NCP-BST
                .build())
            .attributes(buildNcpBstAttributes(originalAssertion, patientId))
            .build();
    }

    private static List<Assertion.Attribute> buildNcpBstAttributes(Assertion originalAssertion, String patientId) {
        List<Assertion.Attribute> attributes = new java.util.ArrayList<>();

        // Map required attributes from original assertion
        for (Assertion.Attribute attr : originalAssertion.getAttributes()) {
            // Only include mandatory attributes for minimal mode
            if (isMandatoryAttribute(attr.getFriendlyName())) {
                attributes.add(Assertion.Attribute.builder()
                    .friendlyName(attr.getFriendlyName())
                    .name(attr.getName())
                    .values(attr.getValues())
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
            .values(List.of("DK")) // Changed from DE to DK for Danish NCP
            .build());

        return attributes;
    }

    private static boolean isMandatoryAttribute(String friendlyName) {
        return friendlyName != null && (
            friendlyName.equals("XSPA Subject") ||
            friendlyName.equals("XSPA Role") ||
            friendlyName.equals("Hl7 Permissions") ||
            friendlyName.equals("EHDSI OnBehalfOf") ||
            friendlyName.equals("XSPA Organization") ||
            friendlyName.equals("XSPA Organization ID") ||
            friendlyName.equals("eHealth DSI Healthcare Facility Type") ||
            friendlyName.equals("XSPA Purpose Of Use") ||
            friendlyName.equals("XSPA Locality")
        );
    }
}
