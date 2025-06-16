package dk.sundhedsdatastyrelsen.ncpeh.authentication;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import lombok.NoArgsConstructor;

import java.util.List;

@Slf4j
@Data
@NoArgsConstructor
public class ParsedData {

    private String id;                    // Assertion @ID
    private String issueInstant;          // Assertion @IssueInstant
    private String version;               // Assertion @Version

    private String issuer;                // <saml2:Issuer>

    private Signature signature;          // <ds:Signature> data
    private Subject subject;              // <saml2:Subject> data
    private Conditions conditions;        // <saml2:Conditions>
    private List<Attribute> attributes;   // <saml2:AttributeStatement>/<saml2:Attribute>
    private List<String> permissions;     // extracted from HL7 Permissions attribute

    @Data
    @NoArgsConstructor
    public static class Signature {
        private String signatureMethodAlgorithm;  // <ds:SignatureMethod Algorithm="">
        private String digestMethodAlgorithm;     // <ds:DigestMethod Algorithm="">
        private String digestValue;               // <ds:DigestValue>
        private String signatureValue;            // <ds:SignatureValue>
        private String certificate;               // <ds:X509Certificate>
    }

    @Data
    @NoArgsConstructor
    public static class Subject {
        private String nameIdFormat;              // <saml2:NameID Format="">
        private String nameIdValue;               // <saml2:NameID>
        private String confirmationMethod;        // <saml2:SubjectConfirmation Method="">
    }

    @Data
    @NoArgsConstructor
    public static class Conditions {
        private String notBefore;                // <saml2:Conditions NotBefore="">
        private String notOnOrAfter;             // <saml2:Conditions NotOnOrAfter="">
    }

    @Data
    @NoArgsConstructor
    public static class Attribute {
        private String friendlyName;             // <saml2:Attribute FriendlyName="">
        private String name;                     // <saml2:Attribute Name="">
        private List<String> values;             // <saml2:AttributeValue> (can be multiple)
    }
}
