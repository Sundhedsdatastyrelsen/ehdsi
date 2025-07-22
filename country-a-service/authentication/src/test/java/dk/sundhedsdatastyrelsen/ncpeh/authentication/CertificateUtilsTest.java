package dk.sundhedsdatastyrelsen.ncpeh.authentication;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class CertificateUtilsTest {

    @Test
    void shouldExtractCountryCodeFromSoapHeaderCertificate() throws Exception {
        // Load test SOAP header from resources
        var soap = getClass().getClassLoader().getResourceAsStream("SoapHeader.xml");
        assertThat(soap, is(notNullValue()));
        var soapHeader = XmlUtils.parse(soap);
        var assertion = SoapHeader.extractAssertion(soapHeader);
        var cert = CertificateUtils.fromBase64(assertion.signature().certificate());

        var countryCode = CertificateUtils.extractCountryCode(cert);
        assertThat(countryCode, is("DK"));
    }
}
