# Authentication Module

This module provides functionality for parsing SOAP headers containing SAML 2.0 assertions and constructing NCP-BST (National Contact Point Bridge Service Token) tokens for the Danish eHDSI (electronic Health Data and Services Infrastructure) system.

## Overview

The authentication module consists of several components:

- **AuthenticationService**: Main service class that encapsulates all authentication logic
- **SoapHeaderParser**: Parses SOAP headers and extracts SAML assertion data
- **Token**: Data model for NCP-BST tokens with transformation logic
- **AssertionBuilder**: Builds SAML assertions in XML format
- **CertParser**: Extracts country codes from X.509 certificates

## Usage

### As a Service (Recommended)

The `AuthenticationService` can be injected into other Spring components:

```java
@Service
public class MyService {
    
    private final AuthenticationService authenticationService;
    
    public MyService(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }
    
    public String processAuthentication(File soapHeaderFile, String patientId) {
        try {
            // Complete workflow: parse SOAP header and generate assertion XML
            return authenticationService.processSoapHeaderToAssertion(soapHeaderFile, patientId);
        } catch (AuthenticationException e) {
            // Handle authentication errors
            throw new RuntimeException("Authentication failed", e);
        }
    }
}
```

### Standalone Usage

You can also use the service directly without Spring:

```java
AuthenticationService authService = new AuthenticationService();

// Parse SOAP header and construct token
Token token = authService.parseAndConstructToken(soapHeaderFile, patientId);

// Build assertion XML
String assertionXml = authService.buildAssertionXml(token);

// Extract country code from certificate
String countryCode = authService.extractCountryCode(token.getSignature().getCertificate());
```

### Command Line Usage

The module includes a main class for command-line usage:

```bash
# Basic usage with default patient ID
java AuthenticationMain src/main/resources/soap-headers/soapHeader.xml

# Specify custom patient ID
java AuthenticationMain src/main/resources/soap-headers/soapHeader.xml "custom-patient-id"
```

## Service Methods

### `parseAndConstructToken(File soapHeaderFile, String patientId)`
Parses a SOAP header file and constructs a Token from the parsed data.

### `parseAndConstructToken(String soapHeaderContent, String patientId)`
Parses a SOAP header from a string and constructs a Token.

### `buildAssertionXml(Token token)`
Builds an assertion XML from a Token.

### `parseSoapHeader(File soapHeaderFile)`
Parses a SOAP header and returns the parsed data without constructing a token.

### `extractCountryCode(String base64Certificate)`
Extracts country code from a certificate.

### `processSoapHeaderToAssertion(File soapHeaderFile, String patientId)`
Complete workflow: parse SOAP header, construct token, and build assertion XML.

### `processSoapHeaderToAssertion(String soapHeaderContent, String patientId)`
Complete workflow: parse SOAP header content, construct token, and build assertion XML.

## Error Handling

The service throws `AuthenticationException` for authentication-related errors:

```java
try {
    Token token = authenticationService.parseAndConstructToken(soapHeaderFile, patientId);
} catch (AuthenticationException e) {
    // Handle authentication-specific errors
    log.error("Authentication failed: {}", e.getMessage());
} catch (Exception e) {
    // Handle other unexpected errors
    log.error("Unexpected error: {}", e.getMessage());
}
```

## Testing

The module includes unit tests demonstrating how to use the service:

```bash
mvn test
```

## Dependencies

- Spring Boot Starter (for service annotations and dependency injection)
- Lombok (for reducing boilerplate code)
- JUnit 5 (for testing)

## Configuration

The service currently uses hardcoded values for:
- Issuer URL: `https://t-ncp.sundhedsdatastyrelsen.dk`
- Audience: `https://sts.sosi.dk/`
- Country of Treatment: `DK`

These values are specific to the Danish NCP implementation and may need to be made configurable for other environments.

## Sample Files

The module includes sample SOAP headers and expected output formats:
- `src/main/resources/soap-headers/soapHeader.xml` - Example input SOAP header
- `src/main/resources/NCP-BST-minimal.xml` - Expected output format
- `src/main/resources/NCP-BST-maximal.xml` - Extended format with more attributes 