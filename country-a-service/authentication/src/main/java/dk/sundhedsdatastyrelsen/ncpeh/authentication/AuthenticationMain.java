package dk.sundhedsdatastyrelsen.ncpeh.authentication;

import dk.sundhedsdatastyrelsen.ncpeh.authentication.model.Token;
import dk.sundhedsdatastyrelsen.ncpeh.authentication.service.AuthenticationService;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.List;

/**
 * Main class demonstrating the authentication workflow.
 * This shows how to parse SOAP headers, construct tokens, build WS-Trust requests,
 * sign them with a certificate, send them to the STS, and parse the response.
 */
@Slf4j
public class AuthenticationMain {

    public static void main(String[] args) {
        if (args.length < 1) {
            System.err.println("Usage: java AuthenticationMain <certificate-password> [patient-id] [target-service]");
            System.err.println("  certificate-password: Password for the signing certificate");
            System.err.println("  patient-id: Patient ID to use in the token (default: 1234567890)");
            System.err.println("  target-service: Target service URL (default: https://fmk)");
            System.exit(1);
        }

        String certificatePassword = args[0];
        String patientId = args.length > 1 ? args[1] : "1234567890";
        String targetService = args.length > 2 ? args[2] : "https://fmk";

        log.info("Starting authentication workflow with:");
        log.info("  Certificate password: [HIDDEN]");
        log.info("  Patient ID: {}", patientId);
        log.info("  Target service: {}", targetService);

        try {
            AuthenticationService authService = new AuthenticationService();

            // Example 1: Parse SOAP header and construct token
            log.info("\n=== Example 1: Parse SOAP header and construct token ===");
            File soapHeaderFile = new File("/home/jls/repos/work/sds/ehdsi/country-a-service/authentication/src/main/resources/soap-headers/SoapHeader.xml");
            Token token = authService.parseAndConstructToken(soapHeaderFile, patientId);
            log.info("Successfully constructed token with ID: {}", token.getId());
            log.info("Token subject: {}", token.getSubject().getNameIdValue());
            log.info("Token issuer: {}", token.getIssuer());


            // Example 2: Build assertion XML
            log.info("\n=== Example 2: Build assertion XML ===");
            String assertionXml = authService.buildAssertionXml(token);
            log.info("Successfully built assertion XML ({} characters)", assertionXml.length());
            log.debug("Assertion XML: {}", assertionXml);

            // Build SOAP envelope
            log.info("\n=== Example 3: Build SOAP envelope ===");
            String soapEnvelope = authService.buildSoapEnvelope(token);
            log.info("Successfully built SOAP envelope ({} characters)", soapEnvelope.length());
            log.debug("SOAP envelope: {}", soapEnvelope);


        } catch (Exception e) {
            log.error("Authentication workflow failed", e);
            System.exit(1);
        }
    }
}
