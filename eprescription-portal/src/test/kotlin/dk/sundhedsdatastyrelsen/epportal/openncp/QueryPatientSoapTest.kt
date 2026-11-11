package dk.sundhedsdatastyrelsen.epportal.openncp

import com.sksamuel.hoplite.Secret
import dk.sundhedsdatastyrelsen.epportal.TestUtils
import dk.sundhedsdatastyrelsen.epportal.patient.PatientId
import dk.sundhedsdatastyrelsen.epportal.utils.XPathWrapper
import dk.sundhedsdatastyrelsen.epportal.utils.XmlNamespace
import dk.sundhedsdatastyrelsen.epportal.utils.XmlUtils
import org.junit.jupiter.api.Test
import java.net.URI
import java.security.cert.X509Certificate
import java.time.OffsetDateTime
import javax.xml.crypto.dsig.XMLSignatureFactory
import javax.xml.crypto.dsig.dom.DOMValidateContext
import kotlin.test.assertEquals
import kotlin.test.assertTrue

fun testOpenNcpConfig(endpoint: URI = URI("http://localhost:1/unused")) = OpenNcp.Config(
    endpoint = endpoint,
    issuer = "urn:idp:DK:countryB",
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
    private val config = testOpenNcpConfig()
    private val factory = HcpAssertionFactory(config.hcp, config.issuer, OpenNcp.loadSigningKey(config.signing))

    /** Serialize and parse again, as the receiver sees it. */
    private fun envelope() =
        XmlUtils.parse(
            XmlUtils.writeDocumentToString(
                OpenNcpClient.envelope("urn:ehdsi:queryPatient", listOf(factory.create())) {},
            ),
        )

    private fun fixture(name: String) = XmlUtils.parse(TestUtils.resourceAsStream("openncp/$name")!!)

    @Test
    fun `envelope carries the action and the assertion in the header`() {
        val doc = envelope()
        assertEquals("urn:ehdsi:queryPatient", xpath.evalString("/soap12:Envelope/soap12:Header/wsa:Action", doc))
        assertTrue(xpath.evalString("/soap12:Envelope/soap12:Header/wsa:MessageID", doc).startsWith("urn:uuid:"))
        val assertion = xpath.evalElement("/soap12:Envelope/soap12:Header/wsse:Security/saml:Assertion", doc)!!
        assertEquals("urn:ehdsi:assertions:hcp", xpath.evalString("saml:Issuer/@NameQualifier", assertion))
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
        val patients = parseQueryPatientResponse(fixture("query-patient-response.xml"), "FI")
        assertEquals(2, patients.size)
        val maija = patients[0]
        assertEquals("FI", maija.countryCode)
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
        assertEquals(emptyList(), parseQueryPatientResponse(fixture("query-patient-empty.xml"), "FI"))
    }
}
