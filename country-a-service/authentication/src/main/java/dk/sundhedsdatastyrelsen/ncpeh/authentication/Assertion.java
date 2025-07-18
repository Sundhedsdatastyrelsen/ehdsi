package dk.sundhedsdatastyrelsen.ncpeh.authentication;

import lombok.Builder;

import java.util.List;

@Builder
public record Assertion(
    // Assertion attributes
    String id,
    String issueInstant,
    String version,

    // Issuer
    String issuer,

    // Signature information
    Signature signature,

    // Subject information
    Subject subject,

    // Conditions
    Conditions conditions,

    // Attributes
    List<Attribute> attributes
) {

    @Builder
    public record Signature(
        String signatureMethodAlgorithm,
        String digestMethodAlgorithm,
        String digestValue,
        String signatureValue,
        String certificate) {
    }

    @Builder
    public record Subject(
        String nameIdFormat,
        String nameIdValue,
        String confirmationMethod,
        String certificate // For SubjectConfirmationData
    ) {
    }

    @Builder
    public record Conditions(
        String notBefore,
        String notOnOrAfter,
        String audience
    ) {
    }

    @Builder
    public record Attribute(
        String friendlyName,
        String name,
        List<String> values
    ) {
    }
}
