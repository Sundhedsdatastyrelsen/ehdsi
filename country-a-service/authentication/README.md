# Authentication Module

This module handles SOAP header parsing, token construction, WS-Trust interactions with a Security Token Service (STS), and XML signing for the eHDSI Country A Service.

## Overview

The authentication module provides a complete workflow for:
1. Parsing SOAP headers from incoming requests
2. Constructing NCP-BST tokens from parsed data
3. Building WS-Trust requests to send to the STS
4. Signing WS-Trust requests with a certificate
5. Communicating with the STS to exchange tokens
6. Parsing maximal token responses from the STS

## Components

### Core Classes

- **`AuthenticationService`**: Main service class that orchestrates the complete authentication workflow
- **`Token`**: Data model representing a SAML assertion with nested classes for Signature, Subject, Conditions, and Attributes
- **`SoapHeaderParser`**: Parses SOAP headers and extracts SAML assertion data
- **`AssertionBuilder`**: Builds minimal SAML assertion XML from Token objects
- **`WsTrustRequestBuilder`**: Builds WS-Trust requests to send to the STS
- **`MaximalTokenParser`**: Parses maximal token responses from the STS
- **`XmlSigner`**: Signs WS-Trust requests using a PKCS12 certificate
- **`StsClient`**: HTTP client for communicating with the STS

### Supporting Classes

- **`ParsedData`**: Intermediate data structure for parsed SOAP header information
- **`CertParser`**: Extracts country codes from certificates
- **`AuthenticationException`**: Custom exception for authentication-related errors

## Authentication Flow

### 1. Parse SOAP Header
```java
AuthenticationService authService = new AuthenticationService();
Token token = authService.parseAndConstructToken(soapHeaderFile, patientId);
```

### 2. Build WS-Trust Request
```java
String wsTrustRequest = authService.buildWsTrustRequest(token, targetService);
```

### 3. Sign WS-Trust Request
```java
String signedRequest = authService.signWsTrustRequest(wsTrustRequest, certificatePassword);
```

### 4. Send to STS
```java
String stsResponse = authService.sendWsTrustRequest(token, targetService, certificatePassword);
```

### 5. Parse STS Response
```java
MaximalTokenParser.MaximalTokenData maximalData = authService.parseMaximalToken(stsResponse);
Token stsToken = maximalData.getAssertion();
```

## Complete Workflow

The `AuthenticationService` provides convenience methods for complete workflows:

### SOAP Header to STS Token
```java
MaximalTokenParser.MaximalTokenData maximalData = authService.processSoapHeaderToStsToken(
    soapHeaderFile, patientId, targetService, certificatePassword
);
```

### SOAP Header to Signed WS-Trust Request
```java
String signedRequest = authService.signWsTrustRequest(
    authService.parseAndConstructToken(soapHeaderFile, patientId),
    targetService,
    certificatePassword
);
```

## Certificate Configuration

The module uses a PKCS12 certificate for signing WS-Trust requests:

- **Certificate file**: `src/main/resources/signing-test-ncpehealth.p12`
- **Password**: Provided at runtime (not hardcoded)
- **Usage**: Signing WS-Trust requests before sending to STS

## STS Configuration

- **Endpoint**: `http://test1-cnsp.ekstern-test.nspop.dk:8080`
- **Protocol**: WS-Trust 1.3
- **Content-Type**: `text/xml; charset=utf-8`
- **SOAP Action**: `http://docs.oasis-open.org/ws-sx/ws-trust/200512/RST/Issue`

## Usage Examples

### Basic Token Construction
```java
// Parse SOAP header and construct token
File soapHeaderFile = new File("soap-header.xml");
Token token = authService.parseAndConstructToken(soapHeaderFile, "1234567890");

// Build assertion XML
String assertionXml = authService.buildAssertionXml(token);
```

### WS-Trust Request with Signing
```java
// Build and sign WS-Trust request
String signedRequest = authService.signWsTrustRequest(token, "https://fmk", "cert-password");

// Send to STS
String stsResponse = authService.sendWsTrustRequest(token, "https://fmk", "cert-password");
```

### Parse STS Response
```java
// Parse maximal token response
MaximalTokenParser.MaximalTokenData maximalData = authService.parseMaximalToken(stsResponse);
Token stsToken = maximalData.getAssertion();

// Use the token in requests to other services
```

## Running the Demo

The `AuthenticationMain` class demonstrates the complete workflow:

```bash
# Run with certificate password
java AuthenticationMain "your-certificate-password"

# Run with custom patient ID and target service
java AuthenticationMain "your-certificate-password" "9876543210" "https://fmk"
```

## Resource Files

- **`soap-headers/soapHeader.xml`**: Example SOAP header for testing
- **`maximal.xml`**: Example maximal token response from STS
- **`NCP-BST-maximal.xml`**: Example NCP-BST token assertion
- **`DRGFMKRequest.xml`**: Example request to FMK using a token
- **`signing-test-ncpehealth.p12`**: PKCS12 certificate for signing

## Error Handling

The module uses `AuthenticationException` for all authentication-related errors. Methods that can fail will throw this exception with descriptive error messages.

## Security Considerations

- Certificate passwords are never hardcoded and must be provided at runtime
- All XML signing uses industry-standard algorithms (RSA-SHA256)
- STS communication uses HTTPS (in production)
- Token validation includes signature verification and expiration checks

## Integration

This module is designed to be easily integrated into other parts of the eHDSI Country A Service:

```java
@Autowired
private AuthenticationService authenticationService;

// Use in your service methods
public void processRequest(File soapHeader, String patientId) {
    MaximalTokenParser.MaximalTokenData tokenData = 
        authenticationService.processSoapHeaderToStsToken(
            soapHeader, patientId, "https://fmk", certificatePassword
        );
    // Use tokenData.getAssertion() in subsequent requests
}
``` 