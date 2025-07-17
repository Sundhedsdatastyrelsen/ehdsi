package dk.sundhedsdatastyrelsen.ncpeh.authentication;

import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class SoapHeaderParserTest {

    @Test
    void shouldParseAssertionFromSoapHeader() throws Exception {
        // Load the test SOAP header from resources
        InputStream in = getClass().getClassLoader().getResourceAsStream("soap-headers/SoapHeader.xml");
        assertThat("SOAP header file should be present in test resources", in, is(notNullValue()));

        String soapXml = new String(in.readAllBytes(), StandardCharsets.UTF_8);

        // Parse the assertion
        Assertion assertion = SoapHeaderParser.parse(soapXml);

        // Verify some key fields
        assertThat(assertion, allOf(
            hasProperty("id", is(notNullValue())),
            hasProperty("issueInstant", is(notNullValue())),
            hasProperty("version", is("2.0")),
            hasProperty("issuer", is(notNullValue())),
            hasProperty("signature", is(notNullValue())),
            hasProperty("subject", is(notNullValue())),
            hasProperty("conditions", is(notNullValue())),
            hasProperty("attributes", is(not(empty())))
        ));

        // Check presence of specific attributes
        assertThat(assertion.getAttributes(), hasItem(
            hasProperty("friendlyName", is("XSPA Subject"))
        ));
    }
}
