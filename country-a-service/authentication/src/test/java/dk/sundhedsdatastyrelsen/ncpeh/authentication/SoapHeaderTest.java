package dk.sundhedsdatastyrelsen.ncpeh.authentication;

import org.junit.jupiter.api.Test;

import static dk.sundhedsdatastyrelsen.ncpeh.authentication.FunMatcher.where;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class SoapHeaderTest {

    @Test
    void shouldParseAssertionFromSoapHeader() throws Exception {
        // Load the test SOAP header from resources
        var is = getClass().getClassLoader().getResourceAsStream("SoapHeader.xml");
        assertThat("SOAP header file should be present in test resources", is, is(notNullValue()));
        var soapHeader = XmlUtils.parse(is);

        // Parse the assertion
        var assertion = SoapHeader.extractAssertion(soapHeader);

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
