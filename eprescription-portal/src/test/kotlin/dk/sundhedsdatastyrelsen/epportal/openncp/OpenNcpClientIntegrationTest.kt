package dk.sundhedsdatastyrelsen.epportal.openncp

import dk.sundhedsdatastyrelsen.epportal.TestUtils
import dk.sundhedsdatastyrelsen.epportal.patient.PatientId
import dk.sundhedsdatastyrelsen.epportal.utils.XPathWrapper
import dk.sundhedsdatastyrelsen.epportal.utils.XmlNamespace
import dk.sundhedsdatastyrelsen.epportal.utils.XmlUtils
import io.javalin.Javalin
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.net.URI
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

/** Runs [OpenNcpClient] over HTTP against a stub standing in for the client connector's ClientService. */
class OpenNcpClientIntegrationTest {
    private data class Captured(val contentType: String?, val body: ByteArray)

    private lateinit var stub: Javalin
    private lateinit var client: OpenNcpClient
    private val requests = mutableListOf<Captured>()
    private var responseStatus = 200
    /** Null means a plain-text body, as from a proxy. */
    private var responseFixture: String? = "query-patient-response.xml"

    @BeforeEach
    fun setup() {
        stub = Javalin.create { config ->
            config.routes.post("/services/ClientService") { ctx ->
                requests += Captured(ctx.header("Content-Type"), ctx.bodyAsBytes())
                ctx.status(responseStatus)
                val fixture = responseFixture
                if (fixture == null) {
                    ctx.contentType("text/plain").result("Bad Gateway")
                } else {
                    ctx.contentType("application/soap+xml; charset=UTF-8")
                        .result(TestUtils.resourceAsStream("openncp/$fixture")!!)
                }
            }
        }
        val port = TestUtils.randomFreePort()
        stub.start(port)
        client = OpenNcpClient.create(
            testOpenNcpConfig(URI("http://localhost:$port/services/ClientService")),
        )
    }

    @AfterEach
    fun tearDown() {
        stub.stop()
    }

    @Test
    fun `sends a SOAP 1_2 queryPatient request and returns the patients found`() {
        val ids = listOf(PatientId("1.2.246.556.12001.4.1000.990.1", "120375-123A"), PatientId("1.2.3", "x<&>y"))
        val patients = client.queryPatient("FI", ids)

        assertEquals(listOf("Maija", "Matti"), patients.map { it.givenName })
        assertEquals(listOf("FI", "FI"), patients.map { it.countryCode })
        val request = requests.single()
        assertEquals("""application/soap+xml; charset=UTF-8; action="urn:ehdsi:queryPatient"""", request.contentType)
        val doc = XmlUtils.parse(request.body.inputStream())
        val xpath = XPathWrapper(XmlNamespace.SOAP12, XmlNamespace.WSSE, XmlNamespace.SAML, XmlNamespace.OPENNCP_CLIENT)
        assertNotNull(xpath.evalElement("/soap12:Envelope/soap12:Header/wsse:Security/saml:Assertion", doc))
        val arg0 = xpath.evalElement("/soap12:Envelope/soap12:Body/ncpc:queryPatient/arg0", doc)!!
        assertEquals("FI", xpath.evalString("countryCode", arg0))
        assertEquals(ids.map { it.root }, xpath.evalStrings("patientDemographics/patientId/root", arg0))
        assertEquals(ids.map { it.extension }, xpath.evalStrings("patientDemographics/patientId/extension", arg0))
    }

    @Test
    fun `listDocuments sends an HCP assertion and a TRC assertion referring to it`() {
        responseFixture = "query-documents-response.xml"
        val prescriptions = client.listDocuments("FI", PatientId("1.2.246.556.12001.4.1000.990.1", "120375-123A"))

        assertEquals(3, prescriptions.size)
        assertEquals(
            """application/soap+xml; charset=UTF-8; action="urn:ehdsi:queryDocuments"""",
            requests.single().contentType,
        )

        val doc = XmlUtils.parse(requests.single().body.inputStream())
        val xpath = XPathWrapper(XmlNamespace.SOAP12, XmlNamespace.WSSE, XmlNamespace.SAML)
        val security = xpath.evalElement("/soap12:Envelope/soap12:Header/wsse:Security", doc)!!
        val hcpId = xpath.evalString("saml:Assertion[saml:Issuer/@NameQualifier='urn:ehdsi:assertions:hcp']/@ID", security)
        val trc = xpath.evalElement("saml:Assertion[saml:Issuer/@NameQualifier='urn:ehdsi:assertions:trc']", security)!!
        assertEquals(hcpId, xpath.evalString("saml:Advice/saml:AssertionIDRef", trc))
        assertEquals(
            "120375-123A^^^&1.2.246.556.12001.4.1000.990.1&ISO",
            xpath.evalString("saml:AttributeStatement/saml:Attribute/saml:AttributeValue", trc),
        )
    }

    @Test
    fun `queryPatient request body is valid against ClientService_xsd`() {
        client.queryPatient("FI", listOf(PatientId("1.2.246.556.12001.4.1000.990.1", "120375-123A")))
        validateClientServiceBody(XmlUtils.parse(requests.single().body.inputStream()))
    }

    @Test
    fun `queryDocuments request body is valid against ClientService_xsd`() {
        responseFixture = "query-documents-response.xml"
        client.listDocuments("FI", PatientId("1.2.246.556.12001.4.1000.990.1", "120375-123A"))
        validateClientServiceBody(XmlUtils.parse(requests.single().body.inputStream()))
    }

    @Test
    fun `a new assertion is made for each request`() {
        client.queryPatient("FI", emptyList())
        client.queryPatient("FI", emptyList())

        val xpath = XPathWrapper(XmlNamespace.SAML)
        val assertionIds = requests.map { xpath.evalString("//saml:Assertion/@ID", XmlUtils.parse(it.body.inputStream())) }
        assertEquals(2, assertionIds.toSet().size)
    }

    @Test
    fun `a SOAP fault is raised as an exception`() {
        responseStatus = 500
        responseFixture = "soap-fault.xml"
        assertThrows<OpenNcpException> { client.queryPatient("FI", emptyList()) }
    }

    @Test
    fun `an HTTP error with a SOAP body that is not a fault is raised as an exception`() {
        responseStatus = 503
        responseFixture = "query-patient-empty.xml"
        assertThrows<OpenNcpException> { client.queryPatient("FI", emptyList()) }
    }

    @Test
    fun `an HTTP error without a SOAP body is raised as an exception`() {
        responseStatus = 502
        responseFixture = null
        assertThrows<OpenNcpException> { client.queryPatient("FI", emptyList()) }
    }
}
