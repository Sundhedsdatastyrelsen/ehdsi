package dk.sundhedsdatastyrelsen.epportal.openncp

import assertk.assertThat
import assertk.assertions.isEqualTo
import dk.sundhedsdatastyrelsen.epportal.TestUtils
import dk.sundhedsdatastyrelsen.epportal.utils.XPathWrapper
import dk.sundhedsdatastyrelsen.epportal.utils.XmlNamespace
import dk.sundhedsdatastyrelsen.epportal.utils.XmlUtils
import org.junit.jupiter.api.Test
import org.w3c.dom.Document
import javax.xml.XMLConstants
import javax.xml.transform.dom.DOMSource
import javax.xml.validation.SchemaFactory

private val clientServiceSchema = SchemaFactory.newDefaultInstance().apply {
    setProperty(XMLConstants.ACCESS_EXTERNAL_DTD, "")
    setProperty(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "")
}.newSchema(TestUtils.resource("client-wsdl/ClientService.xsd")!!)

/** Validates the payload in the SOAP body of [envelope] against ClientService.xsd. */
fun validateClientServiceBody(envelope: Document) {
    val payload = XPathWrapper(XmlNamespace.SOAP12).evalElement("/soap12:Envelope/soap12:Body/*", envelope)!!
    clientServiceSchema.newValidator().validate(DOMSource(payload))
}

/** Checks our requests, response fixtures and actions against the connector's ClientService WSDL and XSD. */
class ClientServiceContractTest {
    private fun fixture(name: String) = XmlUtils.parse(TestUtils.resourceAsStream("openncp/$name")!!)

    private fun soapAction(operation: String): String {
        val wsdl = XmlUtils.parse(TestUtils.resourceAsStream("client-wsdl/ClientService.wsdl")!!)
        val xpath = XPathWrapper(
            XmlNamespace("wsdl", "http://schemas.xmlsoap.org/wsdl/"),
            XmlNamespace("wsoap12", "http://schemas.xmlsoap.org/wsdl/soap12/"),
        )
        return xpath.evalString(
            "/wsdl:definitions/wsdl:binding/wsdl:operation[@name='$operation']/wsoap12:operation/@soapAction",
            wsdl,
        )
    }

    @Test
    fun `queryPatient response fixture is valid`() {
        validateClientServiceBody(fixture("query-patient-response.xml"))
    }

    @Test
    fun `empty queryPatient response fixture is valid`() {
        validateClientServiceBody(fixture("query-patient-empty.xml"))
    }

    @Test
    fun `queryDocuments response fixture is valid`() {
        validateClientServiceBody(fixture("query-documents-response.xml"))
    }

    @Test
    fun `real queryDocuments response is valid`() {
        validateClientServiceBody(fixture("query-documents-response-2.xml"))
    }

    @Test
    fun `dummy queryDocuments response is valid`() {
        validateClientServiceBody(XmlUtils.parse(TestUtils.resourceAsStream("query-documents-dummy.xml")!!))
    }

    @Test
    fun `queryPatient action matches the WSDL`() {
        assertThat(soapAction("queryPatient")).isEqualTo(OpenNcpClient.QUERY_PATIENT_ACTION)
    }

    @Test
    fun `queryDocuments action matches the WSDL`() {
        assertThat(soapAction("queryDocuments")).isEqualTo(OpenNcpClient.QUERY_DOCUMENTS_ACTION)
    }
}
