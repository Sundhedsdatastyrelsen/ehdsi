package dk.sundhedsdatastyrelsen.ncpeh.authentication;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class CertParserTest {

    @Test
    void shouldExtractCountryCodeFromSoapHeaderCertificate() throws IOException {
        // Load test SOAP header from resources
        var soap = getClass().getClassLoader().getResourceAsStream("SoapHeader.xml");
        assertThat(soap, is(notNullValue()));
        var soapXml = new String(soap.readAllBytes(), StandardCharsets.UTF_8);

        // Parse country code
        String countryCode = CertParser.parse(soapXml);

        // Assert expected value (adjust "DK" to match the test cert)
        assertThat(countryCode, is("DK"));
    }

    @Test
    void shouldReturnNullForMalformedXml() {
        String brokenSoap = "<Envelope><BadXml></Envelope>";
        String result = CertParser.parse(brokenSoap);
        assertThat(result, is(nullValue()));
    }
}
