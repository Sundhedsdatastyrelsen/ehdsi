package dk.sundhedsdatastyrelsen.ncpeh.authentication.tools;

import dk.sundhedsdatastyrelsen.ncpeh.authentication.AuthenticationService;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.nio.file.Files;

/**
 * Main class demonstrating the authentication workflow.
 * This shows how to parse SOAP headers, transform assertions, build XML, and create SOAP envelopes.
 */

@Slf4j
public class AuthenticationMain {

    public static void main(String[] args) {
        String patientId = args.length > 1 ? args[1] : "0501077860"; // Test CPR
        String targetService = args.length > 2 ? args[2] : "https://fmk";

        log.info("Starting authentication workflow with:");
        log.info("  Certificate password: [HIDDEN]");
        log.info("  Patient ID: {}", patientId);
        log.info("  Target service: {}", targetService);

        try {
            File soapHeaderFile = new File("/home/jls/repos/work/sds/ehdsi/country-a-service/authentication/src/main/resources/soap-headers/SoapHeader.xml");
            String soapheader = Files.readString(soapHeaderFile.toPath());
            AuthenticationService service = new AuthenticationService();
            service.createSosiRequestBody(soapheader, patientId);

        } catch (Exception e) {
            log.error("Authentication workflow failed", e);
            System.exit(1);
        }
    }
}
