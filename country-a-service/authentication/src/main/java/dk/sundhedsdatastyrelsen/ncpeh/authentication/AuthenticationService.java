package dk.sundhedsdatastyrelsen.ncpeh.authentication;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Service for parsing SOAP headers and constructing NCP-BST tokens.
 * This service encapsulates the authentication logic and can be easily
 * injected into other modules.
 */
@Slf4j
@Service
public class AuthenticationService {

    private final SoapHeaderParser soapHeaderParser;
    private final CertParser certParser;
    private final AssertionBuilder assertionBuilder;

    public AuthenticationService() {
        this.soapHeaderParser = new SoapHeaderParser();
        this.certParser = new CertParser();
        this.assertionBuilder = new AssertionBuilder();
    }

    /**
     * Parses a SOAP header file and constructs a Token from the parsed data.
     * 
     * @param soapHeaderFile the file containing the SOAP header
     * @param patientId the patient ID to use in the token
     * @return the constructed Token
     * @throws AuthenticationException if parsing or construction fails
     */
    public Token parseAndConstructToken(File soapHeaderFile, String patientId) throws AuthenticationException {
        try {
            log.debug("Parsing SOAP header file: {}", soapHeaderFile.getPath());
            
            ParsedData parsedData = soapHeaderParser.parse(soapHeaderFile);
            log.debug("Successfully parsed SOAP header, assertion ID: {}", parsedData.getId());
            
            Token token = Token.fromParsedData(parsedData, patientId);
            log.debug("Successfully constructed token for patient: {}", patientId);
            
            return token;
        } catch (Exception e) {
            log.error("Failed to parse SOAP header and construct token", e);
            throw new AuthenticationException("Failed to parse SOAP header and construct token", e);
        }
    }

    /**
     * Parses a SOAP header from a string and constructs a Token.
     * 
     * @param soapHeaderContent the SOAP header content as a string
     * @param patientId the patient ID to use in the token
     * @return the constructed Token
     * @throws AuthenticationException if parsing or construction fails
     */
    public Token parseAndConstructToken(String soapHeaderContent, String patientId) throws AuthenticationException {
        try {
            log.debug("Parsing SOAP header content for patient: {}", patientId);
            
            // Create a temporary file to use the existing parser
            Path tempFile = Files.createTempFile("soap-header-", ".xml");
            Files.write(tempFile, soapHeaderContent.getBytes());
            
            try {
                return parseAndConstructToken(tempFile.toFile(), patientId);
            } finally {
                // Clean up temporary file
                Files.deleteIfExists(tempFile);
            }
        } catch (IOException e) {
            log.error("Failed to create temporary file for SOAP header parsing", e);
            throw new AuthenticationException("Failed to process SOAP header content", e);
        }
    }

    /**
     * Builds an assertion XML from a Token.
     * 
     * @param token the token to convert to XML
     * @return the assertion XML as a string
     * @throws AuthenticationException if XML construction fails
     */
    public String buildAssertionXml(Token token) throws AuthenticationException {
        try {
            log.debug("Building assertion XML for token ID: {}", token.getId());
            String xml = assertionBuilder.buildAssertionXml(token);
            log.debug("Successfully built assertion XML");
            return xml;
        } catch (Exception e) {
            log.error("Failed to build assertion XML", e);
            throw new AuthenticationException("Failed to build assertion XML", e);
        }
    }

    /**
     * Parses a SOAP header and returns the parsed data without constructing a token.
     * 
     * @param soapHeaderFile the file containing the SOAP header
     * @return the parsed data
     * @throws AuthenticationException if parsing fails
     */
    public ParsedData parseSoapHeader(File soapHeaderFile) throws AuthenticationException {
        try {
            log.debug("Parsing SOAP header file: {}", soapHeaderFile.getPath());
            ParsedData parsedData = soapHeaderParser.parse(soapHeaderFile);
            log.debug("Successfully parsed SOAP header, assertion ID: {}", parsedData.getId());
            return parsedData;
        } catch (Exception e) {
            log.error("Failed to parse SOAP header", e);
            throw new AuthenticationException("Failed to parse SOAP header", e);
        }
    }

    /**
     * Extracts country code from a certificate.
     * 
     * @param base64Certificate the certificate in base64 format
     * @return the country code, or null if extraction fails
     */
    public String extractCountryCode(String base64Certificate) {
        try {
            log.debug("Extracting country code from certificate");
            String countryCode = certParser.parse(base64Certificate);
            log.debug("Extracted country code: {}", countryCode);
            return countryCode;
        } catch (Exception e) {
            log.error("Failed to extract country code from certificate", e);
            return null;
        }
    }

    /**
     * Complete workflow: parse SOAP header, construct token, and build assertion XML.
     * 
     * @param soapHeaderFile the file containing the SOAP header
     * @param patientId the patient ID to use in the token
     * @return the assertion XML as a string
     * @throws AuthenticationException if any step fails
     */
    public String processSoapHeaderToAssertion(File soapHeaderFile, String patientId) throws AuthenticationException {
        Token token = parseAndConstructToken(soapHeaderFile, patientId);
        return buildAssertionXml(token);
    }

    /**
     * Complete workflow: parse SOAP header content, construct token, and build assertion XML.
     * 
     * @param soapHeaderContent the SOAP header content as a string
     * @param patientId the patient ID to use in the token
     * @return the assertion XML as a string
     * @throws AuthenticationException if any step fails
     */
    public String processSoapHeaderToAssertion(String soapHeaderContent, String patientId) throws AuthenticationException {
        Token token = parseAndConstructToken(soapHeaderContent, patientId);
        return buildAssertionXml(token);
    }
} 