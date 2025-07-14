package dk.sundhedsdatastyrelsen.ncpeh.authentication;

import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import static org.junit.jupiter.api.Assertions.*;

class CertParserTest {

    @Test
    void shouldExtractCountryCodeFromSoapHeaderCertificate() throws IOException {
        // Load test SOAP header from resources
        String soapXml = Files.readString(Paths.get("src/test/resources/soap-headers/SoapHeader.xml"), StandardCharsets.UTF_8);

        // Parse country code
        String countryCode = CertParser.parse(soapXml);

        // Assert expected value (adjust "DK" to match your test cert)
        assertEquals("DK", countryCode);
    }

    @Test
    void shouldReturnNullForMalformedXml() {
        String brokenSoap = "<Envelope><BadXml></Envelope>";
        String result = CertParser.parse(brokenSoap);
        assertNull(result);
    }
}
