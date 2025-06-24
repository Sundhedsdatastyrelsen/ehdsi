package dk.sundhedsdatastyrelsen.ncpeh.authentication.service;

import dk.sundhedsdatastyrelsen.ncpeh.authentication.builder.AssertionXmlBuilder;
import dk.sundhedsdatastyrelsen.ncpeh.authentication.builder.SoapEnvelopeBuilder;
import dk.sundhedsdatastyrelsen.ncpeh.authentication.model.Assertion;
import dk.sundhedsdatastyrelsen.ncpeh.authentication.model.AuthenticationException;
import dk.sundhedsdatastyrelsen.ncpeh.authentication.parser.CertParser;
import dk.sundhedsdatastyrelsen.ncpeh.authentication.parser.SoapHeaderParser;
import dk.sundhedsdatastyrelsen.ncpeh.authentication.transformer.AssertionTransformer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Service for parsing SOAP headers and constructing NCP-BST assertions.
 * This service encapsulates the authentication logic and can be easily
 * injected into other modules.
 */
@Slf4j
@Service
public class AuthenticationService {

    private final SoapHeaderParser soapHeaderParser;
    private final CertParser certParser;
    private final AssertionXmlBuilder assertionXmlBuilder;
    private final SoapEnvelopeBuilder soapEnvelopeBuilder;

    public AuthenticationService() {
        this.soapHeaderParser = new SoapHeaderParser();
        this.certParser = new CertParser();
        this.assertionXmlBuilder = new AssertionXmlBuilder();
        this.soapEnvelopeBuilder = new SoapEnvelopeBuilder();
    }

    /**
     * Parses a SOAP header file and constructs an Assertion from the parsed data.
     *
     * @param soapHeaderFile the file containing the SOAP header
     * @return the parsed Assertion
     * @throws AuthenticationException if parsing fails
     */
    public Assertion parseAssertion(File soapHeaderFile) throws AuthenticationException {
        try {
            log.debug("Parsing SOAP header file: {}", soapHeaderFile.getPath());

            Assertion assertion = soapHeaderParser.parse(soapHeaderFile);
            log.debug("Successfully parsed SOAP header, assertion ID: {}", assertion.getId());

            return assertion;
        } catch (Exception e) {
            log.error("Failed to parse SOAP header", e);
            throw new AuthenticationException("Failed to parse SOAP header", e);
        }
    }

    /**
     * Parses a SOAP header from a string and constructs an Assertion.
     *
     * @param soapHeaderContent the SOAP header content as a string
     * @return the parsed Assertion
     * @throws AuthenticationException if parsing fails
     */
    public Assertion parseAssertion(String soapHeaderContent) throws AuthenticationException {
        try {
            log.debug("Parsing SOAP header content");

            // Create a temporary file to use the existing parser
            Path tempFile = Files.createTempFile("soap-header-", ".xml");
            Files.write(tempFile, soapHeaderContent.getBytes());

            try {
                return parseAssertion(tempFile.toFile());
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
     * Transforms an assertion to NCP-BST format with the specified patient ID.
     *
     * @param originalAssertion the original assertion to transform
     * @param patientId the patient ID to use in the transformed assertion
     * @return the transformed assertion
     */
    public Assertion transformToNcpBst(Assertion originalAssertion, String patientId) {
        log.debug("Transforming assertion to NCP-BST format for patient: {}", patientId);
        return AssertionTransformer.transformToNcpBst(originalAssertion, patientId);
    }

    /**
     * Builds an assertion XML from an Assertion.
     *
     * @param assertion the assertion to convert to XML
     * @return the assertion XML as a string
     * @throws AuthenticationException if XML construction fails
     */
    public String buildAssertionXml(Assertion assertion) throws AuthenticationException {
        try {
            log.debug("Building assertion XML for assertion ID: {}", assertion.getId());
            String xml = assertionXmlBuilder.build(assertion);
            log.debug("Successfully built assertion XML");
            return xml;
        } catch (Exception e) {
            log.error("Failed to build assertion XML", e);
            throw new AuthenticationException("Failed to build assertion XML", e);
        }
    }

    /**
     * Builds a complete SOAP envelope containing the assertion.
     *
     * @param assertion the assertion to include in the SOAP envelope
     * @return the SOAP envelope XML as a string
     * @throws AuthenticationException if SOAP envelope construction fails
     */
    public String buildSoapEnvelope(Assertion assertion) throws AuthenticationException {
        try {
            log.debug("Building SOAP envelope for assertion ID: {}", assertion.getId());
            String xml = soapEnvelopeBuilder.build(assertion);
            log.debug("Successfully built SOAP envelope");
            return xml;
        } catch (Exception e) {
            log.error("Failed to build SOAP envelope", e);
            throw new AuthenticationException("Failed to build SOAP envelope", e);
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
     * Complete workflow: parse SOAP header, transform to NCP-BST, and build assertion XML.
     *
     * @param soapHeaderFile the file containing the SOAP header
     * @param patientId the patient ID to use in the transformed assertion
     * @return the assertion XML as a string
     * @throws AuthenticationException if any step fails
     */
    public String processSoapHeaderToAssertion(File soapHeaderFile, String patientId) throws AuthenticationException {
        Assertion originalAssertion = parseAssertion(soapHeaderFile);
        Assertion ncpAssertion = transformToNcpBst(originalAssertion, patientId);
        return buildAssertionXml(ncpAssertion);
    }

    /**
     * Complete workflow: parse SOAP header, transform to NCP-BST, and build SOAP envelope.
     *
     * @param soapHeaderFile the file containing the SOAP header
     * @param patientId the patient ID to use in the transformed assertion
     * @return the SOAP envelope XML as a string
     * @throws AuthenticationException if any step fails
     */
    public String processSoapHeaderToEnvelope(File soapHeaderFile, String patientId) throws AuthenticationException {
        Assertion originalAssertion = parseAssertion(soapHeaderFile);
        Assertion ncpAssertion = transformToNcpBst(originalAssertion, patientId);
        return buildSoapEnvelope(ncpAssertion);
    }
}
