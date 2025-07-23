package dk.sundhedsdatastyrelsen.ncpeh.authentication;

import org.junit.jupiter.api.Test;

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
        var xml = XmlUtils.writeDocumentToStringPretty(bstRequest.getOwnerDocument());

        assertThat(xml, stringContainsInOrder(
            "<wsse:Security mustUnderstand=\"1\" wsu:Id=\"security\">",
            "<wst13:RequestType>http://docs.oasis-open.org/ws-sx/ws-trust/200512/Issue</wst13:RequestType>",
            "<saml:Audience>https://fmk</saml:Audience>"
        ));
        System.out.println(xml);

        var xml2 = XmlUtils.writeDocumentToStringPretty(BootstrapToken.createBootstrapExchangeRequest(bstInput).getOwnerDocument());
        assertThat(xml2, hasLength(xml.length()));
    }
}
