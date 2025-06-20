package dk.sundhedsdatastyrelsen.ncpeh.authentication;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class AuthenticationServiceTest {

    private AuthenticationService authenticationService;
    
    @TempDir
    Path tempDir;

    @BeforeEach
    void setUp() {
        authenticationService = new AuthenticationService();
    }

    @Test
    void testParseAndConstructToken_WithValidSoapHeader() throws Exception {
        // Create a minimal SOAP header file for testing
        String soapHeaderContent = createMinimalSoapHeader();
        File soapHeaderFile = createTempSoapHeaderFile(soapHeaderContent);
        
        String patientId = "test-patient-123";
        
        // Test the service
        Token token = authenticationService.parseAndConstructToken(soapHeaderFile, patientId);
        
        // Verify the token was constructed correctly
        assertNotNull(token);
        assertEquals("test-assertion-id", token.getId());
        assertEquals("https://t-ncp.sundhedsdatastyrelsen.dk", token.getIssuer());
        assertEquals(patientId, token.getSubject().getNameIdValue());
        assertNotNull(token.getSignature());
        assertNotNull(token.getAttributes());
        
        // Verify that required attributes are present
        boolean hasPatientIdAttribute = token.getAttributes().stream()
            .anyMatch(attr -> "XUA Patient Id".equals(attr.getFriendlyName()) && 
                             attr.getValues().contains(patientId));
        assertTrue(hasPatientIdAttribute, "Token should contain patient ID attribute");
    }

    @Test
    void testParseAndConstructToken_WithStringContent() throws Exception {
        String soapHeaderContent = createMinimalSoapHeader();
        String patientId = "test-patient-456";
        
        Token token = authenticationService.parseAndConstructToken(soapHeaderContent, patientId);
        
        assertNotNull(token);
        assertEquals(patientId, token.getSubject().getNameIdValue());
    }

    @Test
    void testBuildAssertionXml() throws Exception {
        // Create a minimal SOAP header file for testing
        String soapHeaderContent = createMinimalSoapHeader();
        File soapHeaderFile = createTempSoapHeaderFile(soapHeaderContent);
        
        Token token = authenticationService.parseAndConstructToken(soapHeaderFile, "test-patient");
        String xml = authenticationService.buildAssertionXml(token);
        
        assertNotNull(xml);
        assertTrue(xml.contains("<Assertion"));
        assertTrue(xml.contains("test-assertion-id"));
        assertTrue(xml.contains("https://t-ncp.sundhedsdatastyrelsen.dk"));
    }

    @Test
    void testProcessSoapHeaderToAssertion() throws Exception {
        String soapHeaderContent = createMinimalSoapHeader();
        File soapHeaderFile = createTempSoapHeaderFile(soapHeaderContent);
        
        String xml = authenticationService.processSoapHeaderToAssertion(soapHeaderFile, "test-patient");
        
        assertNotNull(xml);
        assertTrue(xml.contains("<Assertion"));
        assertTrue(xml.contains("test-patient"));
    }

    @Test
    void testExtractCountryCode() {
        // This is a minimal test - in a real scenario you'd use a real certificate
        String fakeCertificate = "fake-base64-certificate";
        String countryCode = authenticationService.extractCountryCode(fakeCertificate);
        
        // Should return null for invalid certificate
        assertNull(countryCode);
    }

    @Test
    void testParseSoapHeader_WithInvalidFile() {
        File nonExistentFile = new File("non-existent-file.xml");
        
        assertThrows(AuthenticationException.class, () -> {
            authenticationService.parseSoapHeader(nonExistentFile);
        });
    }

    private String createMinimalSoapHeader() {
        return """
            <soap12:Header xmlns:soap12="http://www.w3.org/2003/05/soap-envelope" xmlns:wsse="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd">
                <wsse:Security>
                    <saml:Assertion xmlns:saml="urn:oasis:names:tc:SAML:2.0:assertion" ID="test-assertion-id" IssueInstant="2024-01-01T12:00:00Z" Version="2.0">
                        <saml:Issuer>urn:test:issuer</saml:Issuer>
                        <ds:Signature xmlns:ds="http://www.w3.org/2000/09/xmldsig#">
                            <ds:SignedInfo>
                                <ds:SignatureMethod Algorithm="http://www.w3.org/2000/09/xmldsig#rsa-sha256"/>
                                <ds:DigestMethod Algorithm="http://www.w3.org/2000/09/xmldsig#sha256"/>
                                <ds:DigestValue>test-digest-value</ds:DigestValue>
                            </ds:SignedInfo>
                            <ds:SignatureValue>test-signature-value</ds:SignatureValue>
                            <ds:KeyInfo>
                                <ds:X509Data>
                                    <ds:X509Certificate>test-certificate</ds:X509Certificate>
                                </ds:X509Data>
                            </ds:KeyInfo>
                        </ds:Signature>
                        <saml:Subject>
                            <saml:NameID Format="urn:oasis:names:tc:SAML:1.1:nameid-format:unspecified">test-subject</saml:NameID>
                            <saml:SubjectConfirmation Method="urn:oasis:names:tc:SAML:2.0:cm:holder-of-key"/>
                        </saml:Subject>
                        <saml:Conditions NotBefore="2024-01-01T12:00:00Z" NotOnOrAfter="2024-01-01T13:00:00Z"/>
                        <saml:AttributeStatement>
                            <saml:Attribute FriendlyName="XSPA Subject" Name="urn:oasis:names:tc:xspa:1.0:subject:subject-id">
                                <saml:AttributeValue>Test User</saml:AttributeValue>
                            </saml:Attribute>
                            <saml:Attribute FriendlyName="XSPA Role" Name="urn:oasis:names:tc:xacml:2.0:subject:role">
                                <saml:AttributeValue>Medical Doctor</saml:AttributeValue>
                            </saml:Attribute>
                            <saml:Attribute FriendlyName="XSPA Organization Id" Name="urn:oasis:names:tc:xspa:1.0:subject:organization-id">
                                <saml:AttributeValue>test-org-id</saml:AttributeValue>
                            </saml:Attribute>
                            <saml:Attribute FriendlyName="eHealth DSI Healthcare Facility Type" Name="urn:ehdsi:names:subject:healthcare-facility-type">
                                <saml:AttributeValue>Hospital</saml:AttributeValue>
                            </saml:Attribute>
                            <saml:Attribute FriendlyName="XSPA Purpose Of Use" Name="urn:oasis:names:tc:xspa:1.0:subject:purposeofuse">
                                <saml:AttributeValue>TREATMENT</saml:AttributeValue>
                            </saml:Attribute>
                            <saml:Attribute FriendlyName="XSPA Locality" Name="urn:oasis:names:tc:xspa:1.0:environment:locality">
                                <saml:AttributeValue>Test Location</saml:AttributeValue>
                            </saml:Attribute>
                        </saml:AttributeStatement>
                    </saml:Assertion>
                </wsse:Security>
            </soap12:Header>
            """;
    }

    private File createTempSoapHeaderFile(String content) throws IOException {
        Path tempFile = tempDir.resolve("test-soap-header.xml");
        Files.write(tempFile, content.getBytes());
        return tempFile.toFile();
    }
} 