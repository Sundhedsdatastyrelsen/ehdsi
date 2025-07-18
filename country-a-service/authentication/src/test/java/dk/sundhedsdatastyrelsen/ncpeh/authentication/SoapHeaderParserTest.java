package dk.sundhedsdatastyrelsen.ncpeh.authentication;

import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static dk.sundhedsdatastyrelsen.ncpeh.authentication.FunMatcher.where;
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
        assertThat(
            assertion, allOf(
                where(Assertion::id, is(notNullValue())),
                where(Assertion::issueInstant, is(notNullValue())),
                where(Assertion::version, is("2.0")),
                where(Assertion::issuer, is(notNullValue())),
                where(Assertion::signature, is(notNullValue())),
                where(Assertion::subject, is(notNullValue())),
                where(Assertion::conditions, is(notNullValue())),
                where(Assertion::attributes, is(not(empty())))
            ));

        // Check presence of specific attributes
        assertThat(
            assertion.attributes(), hasItem(
                where(Assertion.Attribute::friendlyName, is("XSPA Subject"))
            ));
    }
}
