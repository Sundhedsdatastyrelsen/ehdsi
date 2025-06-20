package dk.sundhedsdatastyrelsen.ncpeh.authentication;

import java.io.File;

public class AuthenticationMain {

    public static void main(String[] args) {
        if (args.length < 1) {
            System.err.println("Usage: java AuthenticationMain <soapHeaderFile> [patientId]");
            System.err.println("If patientId is not provided, a default value of '0123456789' will be used.");
            System.exit(1);
        }

        try {
            String soapHeaderFile = args[0];
            String patientId = args.length > 1 ? args[1] : "0123456789";

            AuthenticationService authService = new AuthenticationService();
            
            // Parse SOAP header and construct token
            Token token = authService.parseAndConstructToken(new File(soapHeaderFile), patientId);
            
            // Extract country code from certificate
            String countryCode = authService.extractCountryCode(token.getSignature().getCertificate());
            
            // Build assertion XML
            String outputXML = authService.buildAssertionXml(token);
            
            // Output results
            System.out.println("=== Authentication Processing Results ===");
            System.out.println("Parsed ID: " + token.getId());
            System.out.println("Issuer: " + token.getIssuer());
            System.out.println("Subject: " + token.getSubject().getNameIdValue());
            System.out.println("Country Code: " + countryCode);
            System.out.println("\n=== Generated Assertion XML ===\n");
            System.out.println(outputXML);

        } catch (AuthenticationException e) {
            System.err.println("Authentication error: " + e.getMessage());
            e.printStackTrace();
            System.exit(2);
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
            e.printStackTrace();
            System.exit(3);
        }
    }
}
