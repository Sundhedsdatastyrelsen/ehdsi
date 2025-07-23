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
        var sut = new BootstrapToken(testCert().certificate(), null);

        var bst = sut.createBootstrapToken("https://fmk", "1234567890");
        var bstRequest = sut.createBootstrapExchangeRequest("https://fmk", bst);
        var xml = XmlUtils.writeDocumentToString(bstRequest.getOwnerDocument());

        assertThat(xml, stringContainsInOrder(
            "<wsse:Security mustUnderstand=\"1\" wsu:Id=\"security\">",
            "<wst13:RequestType>http://docs.oasis-open.org/ws-sx/ws-trust/200512/Issue</wst13:RequestType>",
            "<saml:Audience>https://fmk</saml:Audience>"
        ));
        System.out.println(xml);
    }
}
