package dk.sundhedsdatastyrelsen.ncpeh.authentication;

import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class Assertion {

    // Assertion attributes
    String id;
    String issueInstant;
    String version;

    // Issuer
    String issuer;

    // Signature information
    Signature signature;

    // Subject information
    Subject subject;

    // Conditions
    Conditions conditions;

    // Attributes
    List<Attribute> attributes;

    @Value
    @Builder
    public static class Signature {
        String signatureMethodAlgorithm;
        String digestMethodAlgorithm;
        String digestValue;
        String signatureValue;
        String certificate;
    }

    @Value
    @Builder
    public static class Subject {
        String nameIdFormat;
        String nameIdValue;
        String confirmationMethod;
        String certificate; // For SubjectConfirmationData
    }

    @Value
    @Builder
    public static class Conditions {
        String notBefore;
        String notOnOrAfter;
        String audience;
    }

    @Value
    @Builder
    public static class Attribute {
        String friendlyName;
        String name;
        List<String> values;
    }
}
