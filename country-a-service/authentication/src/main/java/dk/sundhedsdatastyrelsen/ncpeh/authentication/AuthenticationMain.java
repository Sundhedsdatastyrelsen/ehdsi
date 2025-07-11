package dk.sundhedsdatastyrelsen.ncpeh.authentication;

import dk.sundhedsdatastyrelsen.ncpeh.authentication.model.Assertion;
import dk.sundhedsdatastyrelsen.ncpeh.authentication.parser.SoapHeaderParser;
import dk.sundhedsdatastyrelsen.ncpeh.authentication.service.AuthenticationService;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.nio.file.Files;

/**
 * Main class demonstrating the authentication workflow.
 * This shows how to parse SOAP headers, transform assertions, build XML, and create SOAP envelopes.
 */

// TODO Move to test
@Slf4j
public class AuthenticationMain {

    public static void main(String[] args) {
        if (args.length < 1) {
            System.err.println("Usage: java AuthenticationMain <certificate-password> [patient-id] [target-service]");
            System.err.println("  certificate-password: Password for the signing certificate");
            System.err.println("  patient-id: Patient ID to use in the assertion (default: 0501077860)");
            System.err.println("  target-service: Target service URL (default: https://fmk)");
            System.exit(1);
        }

        String certificatePassword = args[0];
        String patientId = args.length > 1 ? args[1] : "0501077860"; // Test CPR
        String targetService = args.length > 2 ? args[2] : "https://fmk";

        log.info("Starting authentication workflow with:");
        log.info("  Certificate password: [HIDDEN]");
        log.info("  Patient ID: {}", patientId);
        log.info("  Target service: {}", targetService);

        try {
            File soapHeaderFile = new File("/home/jls/repos/work/sds/ehdsi/country-a-service/authentication/src/main/resources/soap-headers/SoapHeader.xml");
            String soapheader = Files.readString(soapHeaderFile.toPath());
            AuthenticationService.createSosiRequestBody(soapheader, patientId);


            // Example 2: Transform to NCP-BST format
            //log.info("\n=== Example 2: Transform to NCP-BST format ===");
            //Assertion ncpAssertion = authService.transformToNcpBst(originalAssertion, patientId); // TODO check if we should remove signature in builder
            //log.info("Successfully transformed assertion for patient: {}", patientId);

            // Example 3: Build assertion XML
            //log.info("\n=== Example 3: Build assertion XML ===");
            //String assertionXml = authService.buildAssertionXml(ncpAssertion);
            //log.info("Successfully built assertion XML ({} characters)", assertionXml.length());
            //log.debug("Assertion XML: {}", assertionXml);

            // Example 4: Build SOAP envelope
            //log.info("\n=== Example 4: Build SOAP envelope ===");
            //String soapEnvelope = authService.buildSoapEnvelope(ncpAssertion);
            //log.info("Successfully built SOAP envelope ({} characters)", soapEnvelope.length());
            //log.debug("SOAP envelope: {}", soapEnvelope);

        } catch (Exception e) {
            log.error("Authentication workflow failed", e);
            System.exit(1);
        }
    }
}
