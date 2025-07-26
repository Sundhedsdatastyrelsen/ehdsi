package dk.sundhedsdatastyrelsen.ncpeh.authentication;

import org.junit.jupiter.api.Test;
import org.w3c.dom.Element;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class OpenNcpAssertionsTest {
    private static String soapHeader() {
        try (var is = BootstrapTokenTest.class.getClassLoader().getResourceAsStream("openncp_soap_header.xml")) {
            assertThat(is, notNullValue());
            return new String(is.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    @Test
    void load() throws AuthenticationException {
        var assertions = OpenNcpAssertions.fromSoapHeader(soapHeader());

        assertThat(assertions.countryOfTreatment(), is("DK"));
        assertThat(assertions.hcpAssertion(), notNullValue());
        assertThat(assertions.trcAssertion(), notNullValue());

        assertThat(
            ((Element) assertions.hcpAssertion()
                .getFirstChild()).getAttribute("NameQualifier"), equalTo("urn:ehdsi:assertions:hcp"));
        assertThat(
            ((Element) assertions.trcAssertion()
                .getFirstChild()).getAttribute("NameQualifier"), equalTo("urn:ehdsi:assertions:trc"));
    }
}
