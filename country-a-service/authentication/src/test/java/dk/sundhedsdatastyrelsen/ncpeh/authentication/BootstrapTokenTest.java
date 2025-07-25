package dk.sundhedsdatastyrelsen.ncpeh.authentication;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.stream.IntStream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class BootstrapTokenTest {
    static CertificateAndKey testCert() throws Exception {
        try (var keystore = BootstrapTokenTest.class.getClassLoader().getResourceAsStream("test-signer.p12")) {
            assertThat(keystore, notNullValue());
            return CertificateUtils.loadCertificateFromKeystore(keystore, "test-signer", "test123");
        }
    }

    private static final String ASSERTION_STATEMENT = """
        <saml:AttributeStatement xmlns:saml="urn:oasis:names:tc:SAML:2.0:assertion" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
            <saml:Attribute FriendlyName="XSPA Subject" Name="urn:oasis:names:tc:xspa:1.0:subject:subject-id" NameFormat="">
                <saml:AttributeValue xsi:type="xs:string">Georg Friedrich Bernhard Riemann</saml:AttributeValue>
            </saml:Attribute>
            <saml:Attribute FriendlyName="XSPA Role" Name="urn:oasis:names:tc:xacml:2.0:subject:role">
                <saml:AttributeValue>
                    <Role xmlns="urn:hl7-org:v3" code="221" codeSystem="2.16.840.1.113883.2.9.6.2.7" codeSystemName="ISCO" displayName="Medical Doctors" xsi:type="CE" />
                </saml:AttributeValue>
            </saml:Attribute>
            <saml:Attribute FriendlyName="XSPA Organization Id" Name="urn:oasis:names:tc:xspa:1.0:subject:organization-id" NameFormat="">
                <saml:AttributeValue xsi:type="xs:string">95444960</saml:AttributeValue>
            </saml:Attribute>
            <saml:Attribute FriendlyName="EHDSI Healthcare Facility Type" Name="urn:ehdsi:names:subject:healthcare-facility-type" NameFormat="">
                <saml:AttributeValue xsi:type="xs:string">Hospital</saml:AttributeValue>
            </saml:Attribute>
            <saml:Attribute FriendlyName="XSPA Purpose of Use" Name="urn:oasis:names:tc:xspa:1.0:subject:purposeofuse">
                <saml:AttributeValue>
                    <PurposeOfUse xmlns="urn:hl7-org:v3" code="TREATMENT"
                        codeSystem="urn:oasis:names:tc:xspa:1.0" xsi:type="CE" />
                </saml:AttributeValue>
            </saml:Attribute>
            <saml:Attribute FriendlyName="XSPA Locality" Name="urn:oasis:names:tc:xspa:1.0:environment:locality" NameFormat="">
                <saml:AttributeValue xsi:type="xs:string">University of Göttingen</saml:AttributeValue>
            </saml:Attribute>
            <saml:Attribute FriendlyName="XUA Patient Id" Name="urn:oasis:names:tc:xacml:2.0:resource:resource-id" NameFormat="">
                <saml:AttributeValue xsi:type="xs:string">
                    0501077860^^^&amp;1.2.208.176.1.2&amp;ISO</saml:AttributeValue>
            </saml:Attribute>
            <saml:Attribute FriendlyName="IDWS XUA SpecVersion" Name="urn:dk:healthcare:saml:SpecVersion" NameFormat="">
                <saml:AttributeValue xsi:type="xs:string">eHDSI-IDWS-XUA-1.0</saml:AttributeValue>
            </saml:Attribute>
            <saml:Attribute FriendlyName="IDWS XUA IssuancePolicy" Name="urn:dk:healthcare:saml:IssuancePolicy" NameFormat="">
                <saml:AttributeValue xsi:type="xs:string">urn:dk:ncp:eHDSI-default</saml:AttributeValue>
            </saml:Attribute>
            <saml:Attribute FriendlyName="EHDSI Country of Treatment" Name="urn:dk:healthcare:saml:CountryOfTreatment" NameFormat="">
                <saml:AttributeValue xsi:type="xs:string">DE</saml:AttributeValue>
            </saml:Attribute>
            <saml:Attribute FriendlyName="NSIS AssuranceLevel" Name="https://data.gov.dk/concept/core/nsis/loa" NameFormat="">
                <saml:AttributeValue xsi:type="xs:string">Substantial</saml:AttributeValue>
            </saml:Attribute>
            <saml:Attribute FriendlyName="XSPA permissions" Name="urn:oasis:names:tc:xspa:1.0:subject:hl7:permission" NameFormat="">
                <saml:AttributeValue xsi:type="xs:string">urn:oasis:names:tc:xspa:1.0:subject:hl7:permission:PRD-004</saml:AttributeValue>
                <saml:AttributeValue xsi:type="xs:string">urn:oasis:names:tc:xspa:1.0:subject:hl7:permission:PRD-010</saml:AttributeValue>
            </saml:Attribute>
            <saml:Attribute FriendlyName="EHDSI OnBehalfOf" Name="urn:ehdsi:names:subject:on-behalf-of">
                <saml:AttributeValue>
                    <Role xmlns="urn:hl7-org:v3" code="42" codeSystem="2.16.840.1.113883.2.9.6.2.7" codeSystemName="ISCO" displayName="Astronaut" xsi:type="CE" />
                </saml:AttributeValue>
            </saml:Attribute>
            <saml:Attribute FriendlyName="XSPA Organization" Name="urn:oasis:names:tc:xspa:1.0:subject:organization" NameFormat="">
                <saml:AttributeValue xsi:type="xs:string">Testorganisation 95444960</saml:AttributeValue>
            </saml:Attribute>
        </saml:AttributeStatement>
        """;

    @Test
    void canGenerateTokenAndTokenRequest() throws Exception {
        var cert = testCert();
        var hcpAssertions = XmlUtils.parse(ASSERTION_STATEMENT).getChildNodes();
        var attrs = IntStream.range(0, hcpAssertions.getLength())
            .mapToObj(hcpAssertions::item)
            .map(BootstrapTokenParams.SamlAttribute.Raw::new)
            .map(x -> (BootstrapTokenParams.SamlAttribute) x)
            .toList();

        var bstInput = BootstrapTokenParams.builder()
            .certificate(cert.certificate())
            .audience("https://fmk")
            .attributes(attrs)
            .nameId("1234567890")
            .nameIdFormat("urn:oasis:names:tc:SAML:1.1:nameid-format:unspecified")
            .build();
        var bst = BootstrapToken.createBootstrapToken(bstInput, "https://ehdsi-idp.testkald.nspop.dk", cert);
        var bstRequest = BootstrapToken.createBootstrapExchangeRequest("https://fmk", bst, cert);
        var xml = XmlUtils.writeDocumentToStringPretty(bstRequest);

        assertThat(
            xml, stringContainsInOrder(
                "<wsse:Security mustUnderstand=\"1\" wsu:Id=\"security\">",
                "<wst13:RequestType>http://docs.oasis-open.org/ws-sx/ws-trust/200512/Issue</wst13:RequestType>",
                "<saml:Audience>https://fmk</saml:Audience>"
            ));
        System.out.println(xml);

        var xml2 = XmlUtils.writeDocumentToStringPretty(BootstrapToken.createBootstrapExchangeRequest(bstInput, "https://ehdsi-idp.testkald.nspop.dk", cert, cert));
        assertThat(xml2, hasLength(xml.length()));
    }

    @Test
    void fromHcpAssertion() throws Exception {
        var cert = testCert();
        var bstParams = SoapHeader.fromHcpAssertion(SoapHeader.hcpAssertion(soapHeader()), cert.certificate(), "https://fmk");
        var bstRequest = BootstrapToken.createBootstrapExchangeRequest(bstParams, "https://ehdsi-idp.testkald.nspop.dk", cert, cert);

        var xml = XmlUtils.writeDocumentToStringPretty(bstRequest);
        assertThat(
            xml, stringContainsInOrder(
                "<wsse:Security mustUnderstand=\"1\" wsu:Id=\"security\">",
                "<wst13:RequestType>http://docs.oasis-open.org/ws-sx/ws-trust/200512/Issue</wst13:RequestType>",
                "<saml:Audience>https://fmk</saml:Audience>"
            ));
        System.out.println(xml);
    }

    private static String soapHeader() {
        try (var is = BootstrapTokenTest.class.getClassLoader().getResourceAsStream("SoapHeader.xml")) {
            assertThat(is, notNullValue());
            return new String(is.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    /**
     * Write a request to the file system for test purposes.  To be invoked manually.
     */
    public static void main(String... args) throws Exception {
        var password = System.getenv("KEYSTORE_PASSWORD");
        assertThat(password, notNullValue());
        var cert = CertificateUtils.loadCertificateFromKeystore(Path.of("config/epps-sosi-sts-client.p12"), "epps-sosi-sts-client", password);
        var bstParams = SoapHeader.fromHcpAssertion(SoapHeader.hcpAssertion(soapHeader()), cert.certificate(), "https://fmk");
        var bstRequest = BootstrapToken.createBootstrapExchangeRequest(bstParams, "https://ehdsi-idp.testkald.nspop.dk", cert, cert);

        Files.createDirectories(Path.of("temp"));
        try (var w = Files.newBufferedWriter(Path.of("temp", "request.xml"), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)) {
            XmlUtils.writeDocument(bstRequest, w);
        }
    }
}
