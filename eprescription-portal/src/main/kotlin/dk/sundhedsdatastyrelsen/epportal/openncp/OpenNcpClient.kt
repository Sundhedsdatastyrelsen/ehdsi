package dk.sundhedsdatastyrelsen.epportal.openncp

import dk.sundhedsdatastyrelsen.epportal.logger
import dk.sundhedsdatastyrelsen.epportal.patient.PatientDemographics
import dk.sundhedsdatastyrelsen.epportal.patient.PatientId
import dk.sundhedsdatastyrelsen.epportal.patient.PatientSearchClient
import dk.sundhedsdatastyrelsen.epportal.prescriptions.PrescriptionListClient
import dk.sundhedsdatastyrelsen.epportal.prescriptions.PrescriptionListItem
import dk.sundhedsdatastyrelsen.epportal.utils.XPathWrapper
import dk.sundhedsdatastyrelsen.epportal.utils.XmlNamespace
import dk.sundhedsdatastyrelsen.epportal.utils.XmlUtils
import org.w3c.dom.Document
import org.w3c.dom.Element
import org.xml.sax.SAXException
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.time.Duration
import java.util.UUID
import kotlin.time.measureTimedValue

/**
 * OpenNCP Client client. The wsdl is in src/test/resources/client-wsdl/ClientService.wsdl.
 */
class OpenNcpClient(
    private val endpoint: URI,
    private val timeout: Duration,
    private val hcp: HcpAssertionFactory,
    private val trc: TrcAssertionFactory,
    private val httpClient: HttpClient,
) : PatientSearchClient, PrescriptionListClient {
    private val log = logger()

    override fun queryPatient(countryCode: String, ids: List<PatientId>): List<PatientDemographics> {
        val envelope = envelope(QUERY_PATIENT_ACTION, listOf(hcp.create())) { body ->
            val queryPatient = XmlUtils.appendChild(body, NCPC, "queryPatient")
            val arg0 = XmlUtils.appendChild(queryPatient, UNQUALIFIED, "arg0")
            XmlUtils.appendChild(arg0, UNQUALIFIED, "countryCode", countryCode)
            val demographics = XmlUtils.appendChild(arg0, UNQUALIFIED, "patientDemographics")
            ids.forEach { id ->
                val patientId = XmlUtils.appendChild(demographics, UNQUALIFIED, "patientId")
                XmlUtils.appendChild(patientId, UNQUALIFIED, "root", id.root)
                XmlUtils.appendChild(patientId, UNQUALIFIED, "extension", id.extension)
            }
        }
        val patients = parseQueryPatientResponse(request(envelope, QUERY_PATIENT_ACTION), countryCode)
        log.info("queryPatient country={} ids={} results={}", countryCode, ids.size, patients.size)
        return patients
    }

    override fun listDocuments(countryCode: String, patientId: PatientId): List<PrescriptionListItem> {
        val hcp = this.hcp.create()
        val trc = this.trc.create(hcp,"${patientId.extension}^^^&${patientId.root}&ISO")
        val envelope = envelope(QUERY_DOCUMENTS_ACTION, listOf(hcp, trc)) { body ->
            val queryDocuments = XmlUtils.appendChild(body, NCPC, "queryDocuments")
            val arg0 = XmlUtils.appendChild(queryDocuments, UNQUALIFIED, "arg0")
            XmlUtils.appendChild(arg0, UNQUALIFIED, "countryCode", countryCode)
            val id = XmlUtils.appendChild(arg0, UNQUALIFIED, "patientId")
            XmlUtils.appendChild(id, UNQUALIFIED, "root", patientId.root)
            XmlUtils.appendChild(id, UNQUALIFIED, "extension", patientId.extension)
            // LOINC 57833-6, the eHDSI class code for ePrescriptions.
            val classCode = XmlUtils.appendChild(arg0, UNQUALIFIED, "classCode")
            XmlUtils.appendChild(classCode, UNQUALIFIED, "nodeRepresentation", "57833-6")
            XmlUtils.appendChild(classCode, UNQUALIFIED, "schema", "2.16.840.1.113883.6.1")
            XmlUtils.appendChild(classCode, UNQUALIFIED, "value", "ePrescription")
        }
        val prescriptions = parseQueryDocumentsResponse(request(envelope, QUERY_DOCUMENTS_ACTION))
        log.info("queryDocuments country={} results={}", countryCode, prescriptions.size)
        return prescriptions
    }

    private fun request(envelope: Document, action: String): Document {
        val request = HttpRequest.newBuilder(endpoint)
            .timeout(timeout)
            .header("Content-Type", """application/soap+xml; charset=UTF-8; action="$action"""")
            .POST(HttpRequest.BodyPublishers.ofString(XmlUtils.writeDocumentToString(envelope)))
            .build()

        val (response, responseDuration) = measureTimedValue {
            httpClient.send(request, HttpResponse.BodyHandlers.ofInputStream())
        }
        val status = response.statusCode()
        log.info("{} status={} durationMs={}", action, status, responseDuration.inWholeMilliseconds)
        val document = try {
            XmlUtils.parse(response.body())
        } catch (e: SAXException) {
            throw OpenNcpException("$action: unparseable response with HTTP status $status", e)
        }
        xpath.evalElement("/soap12:Envelope/soap12:Body/soap12:Fault", document)?.let { fault ->
            val code = xpath.evalString("soap12:Code/soap12:Value", fault)
            val reason = xpath.evalString("soap12:Reason/soap12:Text", fault)
            throw OpenNcpException("$action SOAP fault (HTTP $status): $code: $reason")
        }
        if (status !in 200..299) {
            throw OpenNcpException("$action: unexpected response with HTTP status $status")
        }
        return document
    }

    companion object {
        internal const val QUERY_PATIENT_ACTION = "urn:ehdsi:queryPatient"
        internal const val QUERY_DOCUMENTS_ACTION = "urn:ehdsi:queryDocuments"
        private val SOAP12 = XmlNamespace.SOAP12
        private val NCPC = XmlNamespace.OPENNCP_CLIENT
        private val UNQUALIFIED = XmlNamespace.UNQUALIFIED

        private val xpath = XPathWrapper(SOAP12, NCPC)

        fun create(config: OpenNcp.Config): OpenNcpClient {
            val httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(30))
                .apply { config.truststore?.let { sslContext(OpenNcp.sslContext(it)) } }
                .build()
            val signingKey = OpenNcp.loadSigningKey(config.signing)
            return OpenNcpClient(
                config.endpoint,
                config.timeout,
                HcpAssertionFactory(config.hcp, config.issuer, signingKey),
                TrcAssertionFactory(config.issuer, signingKey, config.trcAssertionDuration),
                httpClient,
            )
        }

        internal fun envelope(action: String, assertions: List<Element>, body: (Element) -> Unit): Document {
            val doc = XmlUtils.newDocument()
            val envelope = XmlUtils.appendChild(doc, SOAP12, "Envelope")
            XmlUtils.declareNamespaces(envelope, SOAP12, XmlNamespace.WSA, XmlNamespace.WSSE, NCPC)
            val header = XmlUtils.appendChild(envelope, SOAP12, "Header")
            // OpenNCP's evidence emitter (MessageInspector) reads these; without them it logs an error.
            XmlUtils.appendChild(header, XmlNamespace.WSA, "Action", action)
            XmlUtils.appendChild(header, XmlNamespace.WSA, "MessageID", "urn:uuid:" + UUID.randomUUID())
            val security = XmlUtils.appendChild(header, XmlNamespace.WSSE, "Security")
            assertions.forEach { security.appendChild(doc.importNode(it, true)) }
            body(XmlUtils.appendChild(envelope, SOAP12, "Body"))
            return doc
        }
    }
}
