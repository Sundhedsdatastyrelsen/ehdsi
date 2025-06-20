package dk.sundhedsdatastyrelsen.ncpeh.authentication;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Token {
    
    // Assertion attributes
    private String id;
    private String issueInstant;
    private String version;
    
    // Issuer
    private String issuer;
    
    // Signature information
    private Signature signature;
    
    // Subject information
    private Subject subject;
    
    // Conditions
    private Conditions conditions;
    
    // Attributes
    private List<Attribute> attributes;
    
    @Data
    @Builder
    public static class Signature {
        private String signatureMethodAlgorithm;
        private String digestMethodAlgorithm;
        private String digestValue;
        private String signatureValue;
        private String certificate;
    }
    
    @Data
    @Builder
    public static class Subject {
        private String nameIdFormat;
        private String nameIdValue;
        private String confirmationMethod;
        private String certificate; // For SubjectConfirmationData
    }
    
    @Data
    @Builder
    public static class Conditions {
        private String notOnOrAfter;
        private String audience;
    }
    
    @Data
    @Builder
    public static class Attribute {
        private String friendlyName;
        private String name;
        private List<String> values;
    }
    
    /**
     * Creates a Token from ParsedData with values mapped to match NCP-BST-minimal structure
     */
    public static Token fromParsedData(ParsedData parsedData, String patientId) {
        return Token.builder()
            .id(parsedData.getId())
            .issueInstant(parsedData.getIssueInstant())
            .version(parsedData.getVersion())
            .issuer("https://t-ncp.sundhedsdatastyrelsen.dk") // Hardcoded for NCP-BST
            .signature(Signature.builder()
                .signatureMethodAlgorithm("http://www.w3.org/2000/09/xmldsig#rsa-sha256")
                .digestMethodAlgorithm("http://www.w3.org/2000/09/xmldsig#sha256")
                .digestValue(parsedData.getSignature().getDigestValue())
                .signatureValue(parsedData.getSignature().getSignatureValue())
                .certificate(parsedData.getSignature().getCertificate())
                .build())
            .subject(Subject.builder()
                .nameIdFormat("urn:oasis:names:tc:SAML:1.1:nameid-format:unspecified")
                .nameIdValue(patientId) // Use provided patientId instead of original
                .confirmationMethod("urn:oasis:names:tc:SAML:2.0:cm:holder-of-key")
                .certificate(parsedData.getSignature().getCertificate())
                .build())
            .conditions(Conditions.builder()
                .notOnOrAfter(parsedData.getConditions().getNotOnOrAfter())
                .audience("https://sts.sosi.dk/") // Hardcoded for NCP-BST
                .build())
            .attributes(buildAttributes(parsedData, patientId))
            .build();
    }
    
    private static List<Attribute> buildAttributes(ParsedData parsedData, String patientId) {
        List<Attribute> attributes = new java.util.ArrayList<>();
        
        // Map required attributes from parsed data
        for (ParsedData.Attribute attr : parsedData.getAttributes()) {
            // Only include mandatory attributes for minimal mode
            if (isMandatoryAttribute(attr.getFriendlyName())) {
                attributes.add(Attribute.builder()
                    .friendlyName(attr.getFriendlyName())
                    .name(attr.getName())
                    .values(attr.getValues())
                    .build());
            }
        }
        
        // Add NCP-BST specific attributes
        attributes.add(Attribute.builder()
            .friendlyName("XUA Patient Id")
            .name("urn:oasis:names:tc:xacml:2.0:resource:resource-id")
            .values(List.of(patientId))
            .build());
            
        attributes.add(Attribute.builder()
            .friendlyName("NSIS AssuranceLevel")
            .name("https://data.gov.dk/concept/core/nsis/loa")
            .values(List.of("3"))
            .build());
            
        attributes.add(Attribute.builder()
            .friendlyName("IDWS XUA SpecVersion")
            .name("urn:dk:healthcare:saml:SpecVersion")
            .values(List.of("eHDSI-IDWS-XUA-1.0"))
            .build());
            
        attributes.add(Attribute.builder()
            .friendlyName("IDWS XUA IssuancePolicy")
            .name("urn:dk:healthcare:saml:IssuancePolicy")
            .values(List.of("urn:dk:healthcare:ncp:eHDSI-strict"))
            .build());
            
        attributes.add(Attribute.builder()
            .friendlyName("EHDSI Country of Treatment")
            .name("urn:dk:healthcare:saml:CountryOfTreatment")
            .values(List.of("DK")) // Changed from DE to DK for Danish NCP
            .build());
        
        return attributes;
    }
    
    private static boolean isMandatoryAttribute(String friendlyName) {
        return switch (friendlyName) {
            case "XSPA Subject", "XSPA Role", "XSPA Organization Id", 
                 "eHealth DSI Healthcare Facility Type", "XSPA Purpose Of Use", 
                 "XSPA Locality" -> true;
            default -> false;
        };
    }
} 