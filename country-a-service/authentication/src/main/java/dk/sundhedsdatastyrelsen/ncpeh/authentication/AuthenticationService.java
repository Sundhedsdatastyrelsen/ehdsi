package dk.sundhedsdatastyrelsen.ncpeh.authentication;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Service for parsing SOAP headers and constructing NCP-BST assertions.
 * This service encapsulates the authentication logic and can be easily
 * injected into other modules.
 */
@Slf4j
public class AuthenticationService {
    private final AuthenticationConfig config;

    // Default config constructor
    public AuthenticationService() {
        this.config = new AuthenticationConfig();
    }

    // Injected config (test-friendly)
    public AuthenticationService(AuthenticationConfig config) {
        this.config = config;
    }

    public String createSosiRequestBody(String soapHeader, String patientID) throws AuthenticationException {
        SAMLSigner samlSigner = new SAMLSigner(config);
        Assertion ncpAssertion = SoapHeaderParser.parse(soapHeader);
        String countryCode = CertParser.parse(soapHeader);
        Assertion bootstrapToken = AssertionTransformer.transformToNcpBst(ncpAssertion, patientID, countryCode);
        String requestTemplate = fillTemplate(config.templatePath(), buildTemplateMapFromAssertion(bootstrapToken));
        return samlSigner.sign(requestTemplate);
   }

    private Map<String, String> buildTemplateMapFromAssertion(Assertion assertion) {
        Map<String, String> map = new HashMap<>();

        // Pre-fill all placeholders with empty strings to avoid missing replacements
        map.put("XSPA_SUBJECT", "");
        map.put("PATIENT_ID", "");
        map.put("COUNTRY_OF_TREATMENT", "");
        map.put("XSPA_ROLE", "");
        map.put("XSPA_PURPOSE_OF_USE", "");
        map.put("XSPA_ORGANIZATION_ID", "");
        map.put("XSPA_ORGANIZATION", "");
        map.put("FACILITY_TYPE", "");
        map.put("XSPA_LOCALITY", "");
        map.put("XSPA_PERMISSIONS", "");

        for (Assertion.Attribute attr : assertion.attributes()) {
            String friendlyName = attr.friendlyName();
            List<String> values = attr.values();

            if (friendlyName == null || values == null || values.isEmpty()) continue;

            switch (friendlyName) {
                case "XSPA Subject" -> map.put("XSPA_SUBJECT", escape(values.getFirst()));
                case "XUA Patient Id" -> map.put("PATIENT_ID", escape(values.getFirst()));
                case "EHDSI Country of Treatment" -> map.put("COUNTRY_OF_TREATMENT", escape(values.getFirst()));
                case "XSPA Role" -> map.put("XSPA_ROLE", values.getFirst()); // raw XML fragment, no escape
                case "XSPA Purpose Of Use", "XSPA Purpose of Use" -> map.put("XSPA_PURPOSE_OF_USE", values.getFirst()); // raw XML
                case "XSPA Organization ID" -> map.put("XSPA_ORGANIZATION_ID", escape(values.getFirst()));
                case "XSPA Organization" -> map.put("XSPA_ORGANIZATION", escape(values.getFirst()));
                case "eHealth DSI Healthcare Facility Type", "EHDSI Healthcare Facility Type" -> map.put("FACILITY_TYPE", escape(values.getFirst()));
                case "XSPA Locality" -> map.put("XSPA_LOCALITY", escape(values.getFirst()));
                case "Hl7 Permissions", "XSPA permissions" -> {
                    String permissionXml = values.stream()
                        .map(p -> "<saml:AttributeValue xsi:type=\"xs:string\">" + escape(p) + "</saml:AttributeValue>")
                        .collect(Collectors.joining());
                    map.put("XSPA_PERMISSIONS", permissionXml);
                }
                default -> log.debug("Unknown attribute: {}", friendlyName);
            }
        }
        return map;
    }

    private String escape(String value) {
        if (value == null) return "";
        return value.replace("&", "&amp;")
            .replace("<", "&lt;")
            .replace(">", "&gt;")
            .replace("\"", "&quot;")
            .replace("'", "&apos;");
    }

    private String fillTemplate(URI templatePath, Map<String, String> values) throws AuthenticationException {
        String result;
        try (var is = templatePath.toURL().openStream()) {
            result = new String(is.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new AuthenticationException("Cannot open template file", e);
        }
        for (var entry : values.entrySet()) {
            result = result.replace("{{" + entry.getKey() + "}}", entry.getValue());
        }
        return result;
    }
}
