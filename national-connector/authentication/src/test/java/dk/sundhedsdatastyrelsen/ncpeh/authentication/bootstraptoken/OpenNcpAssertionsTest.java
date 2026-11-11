package dk.sundhedsdatastyrelsen.ncpeh.authentication.bootstraptoken;

import dk.sundhedsdatastyrelsen.ncpeh.authentication.AuthenticationException;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Element;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

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

    @Test
    void missingTrcAssertionIsRejected() {
        var header = soapHeader();
        // The TRC assertion is the last one in the header.
        var trcStart = header.lastIndexOf("<saml2:Assertion ");
        var trcEnd = header.lastIndexOf("</saml2:Assertion>") + "</saml2:Assertion>".length();
        var headerWithoutTrc = header.substring(0, trcStart) + header.substring(trcEnd);
        assertThat(headerWithoutTrc, not(containsString("urn:ehdsi:assertions:trc")));
        assertThat(headerWithoutTrc, containsString("urn:ehdsi:assertions:hcp"));

        assertThrows(AuthenticationException.class, () -> OpenNcpAssertions.fromSoapHeader(headerWithoutTrc));
    }
}
