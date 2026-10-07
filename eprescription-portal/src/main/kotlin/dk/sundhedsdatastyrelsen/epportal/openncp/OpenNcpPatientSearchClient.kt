package dk.sundhedsdatastyrelsen.epportal.openncp

import dk.sundhedsdatastyrelsen.epportal.logger
import dk.sundhedsdatastyrelsen.epportal.patient.PatientDemographics
import dk.sundhedsdatastyrelsen.epportal.patient.PatientId
import dk.sundhedsdatastyrelsen.epportal.patient.PatientSearchClient
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
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeParseException
import java.util.UUID
import kotlin.time.measureTimedValue

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
            .POST(HttpRequest.BodyPublishers.ofString(XmlUtils.writeDocumentToString(envelope)))
            .build()

        val (response, responseDuration) = measureTimedValue {
            httpClient.send(request, HttpResponse.BodyHandlers.ofInputStream())
        }
        val document = try {
            XmlUtils.parse(response.body())
        } catch (e: SAXException) {
            throw OpenNcpException("queryPatient: unparseable response with HTTP status ${response.statusCode()}", e)
        }
        val patients = parseQueryPatientResponse(document, response.statusCode())
        log.info(
            "queryPatient country={} ids={} status={} results={} durationMs={}",
            countryCode, ids.size, response.statusCode(), patients.size, responseDuration.inWholeMilliseconds,
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
            if (config.dangerouslyDisableTlsHostnameVerification) {
                logger().warn("TLS hostname verification is disabled for {}. Never do this in production.", config.endpoint)
            }
            val httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .apply {
                    if (config.truststore != null || config.dangerouslyDisableTlsHostnameVerification) {
                        sslContext(
                            OpenNcpTls.sslContext(
                                config.truststore?.let(OpenNcp::loadKeyStore),
                                config.dangerouslyDisableTlsHostnameVerification,
                            ),
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
            val envelope = XmlUtils.appendChild(doc, SOAP12, "Envelope")
            XmlUtils.declareNamespaces(envelope, SOAP12, XmlNamespace.WSA, XmlNamespace.WSSE, NCPC)
            val header = XmlUtils.appendChild(envelope, SOAP12, "Header")
            // OpenNCP's evidence emitter (MessageInspector) reads these; without them it logs an error.
            XmlUtils.appendChild(header, XmlNamespace.WSA, "Action", QUERY_PATIENT_ACTION)
            XmlUtils.appendChild(header, XmlNamespace.WSA, "MessageID", "urn:uuid:" + UUID.randomUUID())
            XmlUtils.appendChild(header, XmlNamespace.WSSE, "Security")
                .appendChild(doc.importNode(assertion, true))
            val body = XmlUtils.appendChild(envelope, SOAP12, "Body")
            val queryPatient = XmlUtils.appendChild(body, NCPC, "queryPatient")
            val arg0 = XmlUtils.appendChild(queryPatient, UNQUALIFIED, "arg0")
            XmlUtils.appendChild(arg0, UNQUALIFIED, "countryCode", countryCode)
            val demographics = XmlUtils.appendChild(arg0, UNQUALIFIED, "patientDemographics")
            ids.forEach { id ->
                val patientId = XmlUtils.appendChild(demographics, UNQUALIFIED, "patientId")
                XmlUtils.appendChild(patientId, UNQUALIFIED, "root", id.root)
                XmlUtils.appendChild(patientId, UNQUALIFIED, "extension", id.extension)
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
