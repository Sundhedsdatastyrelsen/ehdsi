package dk.sundhedsdatastyrelsen.epportal.openncp

import dk.sundhedsdatastyrelsen.epportal.logger
import dk.sundhedsdatastyrelsen.epportal.patient.PatientDemographics
import dk.sundhedsdatastyrelsen.epportal.patient.PatientId
import dk.sundhedsdatastyrelsen.epportal.patient.PatientSearchClient
import dk.sundhedsdatastyrelsen.epportal.utils.XPathWrapper
import dk.sundhedsdatastyrelsen.epportal.utils.XmlNamespace
import dk.sundhedsdatastyrelsen.epportal.utils.XmlUtils
import dk.sundhedsdatastyrelsen.epportal.utils.appendElement
import dk.sundhedsdatastyrelsen.epportal.utils.appendRoot
import org.w3c.dom.Document
import org.w3c.dom.Element
import org.xml.sax.SAXException
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.time.Duration
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeParseException
import java.util.UUID

/**
 * Patient discovery through the `queryPatient` operation of the OpenNCP client connector's SOAP 1.2 ClientService
 * (see ClientService.wsdl/xsd in OpenNCP's openncp-core-client-api).
 */
class OpenNcpPatientSearchClient(
    private val endpoint: URI,
    private val timeout: Duration,
    private val assertions: HcpAssertionFactory,
    private val httpClient: HttpClient,
) : PatientSearchClient {
    private val log = logger()

    override fun queryPatient(countryCode: String, ids: List<PatientId>): List<PatientDemographics> {
        val envelope = buildQueryPatientEnvelope(assertions.create(), countryCode, ids)
        val request = HttpRequest.newBuilder(endpoint)
            .timeout(timeout)
            .header("Content-Type", """application/soap+xml; charset=UTF-8; action="$QUERY_PATIENT_ACTION"""")
            .POST(HttpRequest.BodyPublishers.ofByteArray(XmlUtils.serialize(envelope)))
            .build()

        val start = System.nanoTime()
        val response = httpClient.send(request, HttpResponse.BodyHandlers.ofInputStream())
        val durationMs = (System.nanoTime() - start) / 1_000_000
        val document = try {
            XmlUtils.parse(response.body())
        } catch (e: SAXException) {
            throw OpenNcpException("queryPatient: unparseable response with HTTP status ${response.statusCode()}", e)
        }
        val patients = parseQueryPatientResponse(document, response.statusCode())
        log.info(
            "queryPatient country={} ids={} status={} results={} durationMs={}",
            countryCode, ids.size, response.statusCode(), patients.size, durationMs,
        )
        return patients
    }

    companion object {
        private const val QUERY_PATIENT_ACTION = "urn:ehdsi:queryPatient"
        private val SOAP12 = XmlNamespace.SOAP12
        private val NCPC = XmlNamespace.OPENNCP_CLIENT

        /** Elements inside the operation wrapper are unqualified, as JAX-WS generates them. */
        private val UNQUALIFIED = XmlNamespace(null, null)

        private val xpath = XPathWrapper(SOAP12, NCPC)

        fun create(config: OpenNcp.Config): OpenNcpPatientSearchClient {
            val httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .apply {
                    if (config.truststore != null || config.tlsHostname != null) {
                        sslContext(
                            OpenNcpTls.sslContext(config.truststore?.let(OpenNcp::loadKeyStore), config.tlsHostname),
                        )
                    }
                }
                .build()
            return OpenNcpPatientSearchClient(
                config.endpoint,
                config.timeout,
                HcpAssertionFactory.fromConfig(config),
                httpClient,
            )
        }

        fun buildQueryPatientEnvelope(assertion: Element, countryCode: String, ids: List<PatientId>): Document {
            val doc = XmlUtils.newDocument()
            val envelope = doc.appendRoot(SOAP12, "Envelope")
            val header = envelope.appendElement(SOAP12, "Header")
            // OpenNCP's evidence emitter (MessageInspector) reads these; without them it logs an error.
            header.appendElement(XmlNamespace.WSA, "Action", QUERY_PATIENT_ACTION)
            header.appendElement(XmlNamespace.WSA, "MessageID", "urn:uuid:" + UUID.randomUUID())
            header.appendElement(XmlNamespace.WSSE, "Security")
                .appendChild(doc.importNode(assertion, true))
            val arg0 = envelope.appendElement(SOAP12, "Body")
                .appendElement(NCPC, "queryPatient")
                .appendElement(UNQUALIFIED, "arg0")
            arg0.appendElement(UNQUALIFIED, "countryCode", countryCode)
            val demographics = arg0.appendElement(UNQUALIFIED, "patientDemographics")
            ids.forEach { id ->
                demographics.appendElement(UNQUALIFIED, "patientId").apply {
                    appendElement(UNQUALIFIED, "root", id.root)
                    appendElement(UNQUALIFIED, "extension", id.extension)
                }
            }
            return doc
        }

        fun parseQueryPatientResponse(document: Document, httpStatus: Int = 200): List<PatientDemographics> {
            xpath.evalElement("/soap12:Envelope/soap12:Body/soap12:Fault", document)?.let { fault ->
                val code = xpath.evalString("soap12:Code/soap12:Value", fault)
                val reason = xpath.evalString("soap12:Reason/soap12:Text", fault)
                throw OpenNcpException("queryPatient SOAP fault (HTTP $httpStatus): $code: $reason")
            }
            val response = xpath.evalElement("/soap12:Envelope/soap12:Body/ncpc:queryPatientResponse", document)
            if (httpStatus !in 200..299 || response == null) {
                throw OpenNcpException("queryPatient: unexpected response with HTTP status $httpStatus")
            }
            return xpath.evalElements("return", response).map(::toPatientDemographics)
        }

        private fun toPatientDemographics(e: Element): PatientDemographics {
            fun text(name: String): String? = xpath.evalString(name, e).ifEmpty { null }
            return PatientDemographics(
                givenName = text("givenName"),
                familyName = text("familyName"),
                birthDate = text("birthDate")?.let(::parseDateTime),
                gender = text("administrativeGender"),
                addressStreet = text("streetAddress"),
                addressPostalCode = text("postalCode"),
                addressCity = text("city"),
                addressCountry = text("country"),
                phone = text("telephone"),
                email = text("email"),
                patientIds = xpath.evalElements("patientId", e).map {
                    PatientId(root = xpath.evalString("root", it), extension = xpath.evalString("extension", it))
                },
            )
        }

        /** xs:dateTime, where the offset is optional. Without one we assume UTC. */
        private fun parseDateTime(s: String): OffsetDateTime =
            try {
                OffsetDateTime.parse(s)
            } catch (_: DateTimeParseException) {
                LocalDateTime.parse(s).atOffset(ZoneOffset.UTC)
            }
    }
}
