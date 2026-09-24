package dk.sundhedsdatastyrelsen.epportal.openncp

import com.sksamuel.hoplite.Secret
import dk.sundhedsdatastyrelsen.epportal.TestUtils
import dk.sundhedsdatastyrelsen.epportal.patient.PatientId
import dk.sundhedsdatastyrelsen.epportal.utils.XPathWrapper
import dk.sundhedsdatastyrelsen.epportal.utils.XmlNamespace
import dk.sundhedsdatastyrelsen.epportal.utils.XmlUtils
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.net.URI
import java.security.cert.X509Certificate
import java.time.OffsetDateTime
import javax.xml.crypto.dsig.XMLSignatureFactory
import javax.xml.crypto.dsig.dom.DOMValidateContext
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertTrue

fun testOpenNcpConfig(endpoint: URI = URI("http://localhost:1/unused")) = OpenNcp.Config(
    endpoint = endpoint,
    signing = OpenNcp.SigningConfig(
        keystore = OpenNcp.StoreConfig("src/test/resources/openncp/test-signing.p12", Secret("changeit")),
        alias = "test",
    ),
    hcp = OpenNcp.HcpConfig(
        nameId = "testpharmacist",
        subjectId = "Test Farmaceut",
        organization = "Testapoteket",
        organizationId = "urn:oid:1.2.208.176.1.1",
        locality = "København",
    ),
)

class QueryPatientSoapTest {
    private val xpath = XPathWrapper(
        XmlNamespace.SOAP12, XmlNamespace.WSSE, XmlNamespace.WSA, XmlNamespace.SAML, XmlNamespace.DS, XmlNamespace.HL7,
        XmlNamespace.OPENNCP_CLIENT,
    )
    private val factory = HcpAssertionFactory.fromConfig(testOpenNcpConfig())
    private val ids = listOf(PatientId("1.2.208.176.1.2", "0101011234"), PatientId("1.2.3", "x<&>y"))

    /** Serialize and parse again, as the receiver sees it. */
    private fun envelope() =
        XmlUtils.parse(
            XmlUtils.serialize(OpenNcpPatientSearchClient.buildQueryPatientEnvelope(factory.create(), "DK", ids))
                .inputStream(),
        )

    private fun fixture(name: String) = XmlUtils.parse(TestUtils.resourceAsStream("openncp/$name")!!)

    @Test
    fun `envelope carries the assertion in the security header and the query in the body`() {
        val doc = envelope()
        assertEquals("urn:ehdsi:queryPatient", xpath.evalString("/soap12:Envelope/soap12:Header/wsa:Action", doc))
        assertTrue(xpath.evalString("/soap12:Envelope/soap12:Header/wsa:MessageID", doc).startsWith("urn:uuid:"))
        val assertion = xpath.evalElement("/soap12:Envelope/soap12:Header/wsse:Security/saml:Assertion", doc)!!
        assertEquals("urn:ehdsi:assertions:hcp", xpath.evalString("saml:Issuer/@NameQualifier", assertion))

        val arg0 = xpath.evalElement("/soap12:Envelope/soap12:Body/ncpc:queryPatient/arg0", doc)!!
        assertEquals("DK", xpath.evalString("countryCode", arg0))
        assertEquals(listOf("1.2.208.176.1.2", "1.2.3"), xpath.evalStrings("patientDemographics/patientId/root", arg0))
        assertEquals(listOf("0101011234", "x<&>y"), xpath.evalStrings("patientDemographics/patientId/extension", arg0))
    }

    @Test
    fun `assertion has the fields the connector requires, with the signature right after Issuer`() {
        val assertion = xpath.evalElement("//saml:Assertion", envelope())!!
        val children = xpath.evalElements("*", assertion).map { it.localName }
        assertEquals(listOf("Issuer", "Signature", "Subject", "Conditions", "AuthnStatement", "AttributeStatement"), children)
        assertEquals(
            "urn:oasis:names:tc:SAML:2.0:cm:sender-vouches",
            xpath.evalString("saml:Subject/saml:SubjectConfirmation/@Method", assertion),
        )

        fun attr(name: String) = "saml:AttributeStatement/saml:Attribute[@Name='$name']/saml:AttributeValue"
        assertEquals("2262", xpath.evalString(attr("urn:oasis:names:tc:xacml:2.0:subject:role") + "/hl7:Role/@code", assertion))
        assertEquals(
            "TREATMENT",
            xpath.evalString(attr("urn:oasis:names:tc:xspa:1.0:subject:purposeofuse") + "/hl7:PurposeOfUse/@code", assertion),
        )
        assertEquals("Pharmacy", xpath.evalString(attr("urn:ehdsi:names:subject:healthcare-facility-type"), assertion))
        assertEquals("Test Farmaceut", xpath.evalString(attr("urn:oasis:names:tc:xspa:1.0:subject:subject-id"), assertion))
        assertEquals("København", xpath.evalString(attr("urn:oasis:names:tc:xspa:1.0:environment:locality"), assertion))
        assertEquals(
            "urn:oid:1.2.208.176.1.1",
            xpath.evalString(attr("urn:oasis:names:tc:xspa:1.0:subject:organization-id"), assertion),
        )
    }

    @Test
    fun `assertion signature survives serialization and verifies with the certificate in KeyInfo`() {
        val assertion = xpath.evalElement("//saml:Assertion", envelope())!!
        assertion.setIdAttribute("ID", true)
        val signature = xpath.evalElement("ds:Signature", assertion)!!
        val certB64 = xpath.evalString("ds:KeyInfo/ds:X509Data/ds:X509Certificate", signature)
        val cert = java.security.cert.CertificateFactory.getInstance("X.509")
            .generateCertificate(java.util.Base64.getMimeDecoder().decode(certB64).inputStream()) as X509Certificate

        val ctx = DOMValidateContext(cert.publicKey, signature)
        assertTrue(XMLSignatureFactory.getInstance("DOM").unmarshalXMLSignature(ctx).validate(ctx))
    }

    @Test
    fun `parses patients from the response`() {
        val patients = OpenNcpPatientSearchClient.parseQueryPatientResponse(fixture("query-patient-response.xml"))
        assertEquals(2, patients.size)
        val maija = patients[0]
        assertEquals("Maija", maija.givenName)
        assertEquals("Meikäläinen", maija.familyName)
        assertEquals(OffsetDateTime.parse("1975-03-12T00:00:00+01:00"), maija.birthDate)
        assertEquals("F", maija.gender)
        assertEquals("Mannerheimintie 1", maija.addressStreet)
        assertEquals("00100", maija.addressPostalCode)
        assertEquals("Helsinki", maija.addressCity)
        assertEquals("FI", maija.addressCountry)
        assertEquals("+358 40 1234567", maija.phone)
        assertEquals("maija@example.org", maija.email)
        assertEquals(listOf(PatientId("1.2.246.556.12001.4.1000.990.1", "120375-123A")), maija.patientIds)

        val matti = patients[1]
        assertEquals(OffsetDateTime.parse("1980-01-01T00:00:00Z"), matti.birthDate)
        assertEquals(null, matti.email)
    }

    @Test
    fun `an empty response means no patients`() {
        assertEquals(emptyList(), OpenNcpPatientSearchClient.parseQueryPatientResponse(fixture("query-patient-empty.xml")))
    }

    @Test
    fun `a SOAP fault is an error`() {
        val e = assertThrows<OpenNcpException> {
            OpenNcpPatientSearchClient.parseQueryPatientResponse(fixture("soap-fault.xml"), 500)
        }
        assertContains(e.message!!, "No HCP assertion found")
    }

    @Test
    fun `a non-2xx status without a fault is an error`() {
        assertThrows<OpenNcpException> {
            OpenNcpPatientSearchClient.parseQueryPatientResponse(fixture("query-patient-empty.xml"), 503)
        }
    }
}
