package dk.sundhedsdatastyrelsen.ncpeh.authentication;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class BootstrapTokenTest {
    CertificateUtils.CertificateWithPrivateKey testCert() throws Exception {
        try (var keystore = getClass().getClassLoader().getResourceAsStream("test-signer.p12")) {
            assertThat(keystore, notNullValue());
            return CertificateUtils.loadCertificateFromKeystore(keystore, "test-signer", "test123");
        }
    }

    @Test
    void canGenerateTokenAndTokenRequest() throws Exception {
        var bstInput = BootstrapTokenParams.builderWithDefaults()
            .certificate(testCert().certificate())
            .audience("https://fmk")
            .nameId("1234567890")
            .build();
        var bst = BootstrapToken.createBootstrapToken(bstInput);
        var bstRequest = BootstrapToken.createBootstrapExchangeRequest("https://fmk", bst);
        var xml = XmlUtils.writeDocumentToStringPretty(bstRequest);

        assertThat(xml, stringContainsInOrder(
            "<wsse:Security mustUnderstand=\"1\" wsu:Id=\"security\">",
            "<wst13:RequestType>http://docs.oasis-open.org/ws-sx/ws-trust/200512/Issue</wst13:RequestType>",
            "<saml:Audience>https://fmk</saml:Audience>"
        ));
        System.out.println(xml);

        var xml2 = XmlUtils.writeDocumentToStringPretty(BootstrapToken.createBootstrapExchangeRequest(bstInput));
        assertThat(xml2, hasLength(xml.length()));
    }

    @Test
    void fromHcpAssertion() throws Exception {
        var bstParams = SoapHeader.fromHcpAssertion(SoapHeader.hcpAssertion(soapHeader()), testCert().certificate(), "https://fmk");
        var bstRequest = BootstrapToken.createBootstrapExchangeRequest(bstParams);

        var xml = XmlUtils.writeDocumentToStringPretty(bstRequest);
        assertThat(xml, stringContainsInOrder(
            "<wsse:Security mustUnderstand=\"1\" wsu:Id=\"security\">",
            "<wst13:RequestType>http://docs.oasis-open.org/ws-sx/ws-trust/200512/Issue</wst13:RequestType>",
            "<saml:Audience>https://fmk</saml:Audience>"
        ));
        System.out.println(xml);
    }

    private String soapHeader() {
        try (var is = BootstrapTokenTest.class.getClassLoader().getResourceAsStream("SoapHeader.xml")) {
            assertThat(is, notNullValue() );
            return new String(is.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}
