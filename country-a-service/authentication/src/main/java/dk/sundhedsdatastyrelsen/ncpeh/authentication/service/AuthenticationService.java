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
public class AuthenticationService {

    private static final SoapHeaderParser soapHeaderParser = new SoapHeaderParser();
    private static final CertParser certParser = new CertParser();
    private static final AssertionXmlBuilder assertionXmlBuilder = new AssertionXmlBuilder();
    private static final SoapEnvelopeBuilder soapEnvelopeBuilder = new SoapEnvelopeBuilder();


   public static void createSosiRequestBody(String soapHeader, String patientID) throws Exception {

        Assertion ncpAssertion = soapHeaderParser.parse(soapHeader);
        String countryCode = certParser.parse(soapHeader);
        Assertion bootstrapToken = AssertionTransformer.transformToNcpBst(ncpAssertion, patientID, countryCode);
        log.info("Parsed NCP assertion for patient");
        // TODO: create an assertion xml
        // TODO: Create SoapEnvelope

   }
}
