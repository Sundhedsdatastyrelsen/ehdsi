package dk.sundhedsdatastyrelsen.ncpeh.authentication.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Assertion {

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
        private String notBefore;
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
} 