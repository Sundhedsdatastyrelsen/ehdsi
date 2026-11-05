package dk.sundhedsdatastyrelsen.epportal.openncp

import dk.sundhedsdatastyrelsen.epportal.patient.PatientDemographics
import dk.sundhedsdatastyrelsen.epportal.patient.PatientId
import dk.sundhedsdatastyrelsen.epportal.utils.XPathWrapper
import dk.sundhedsdatastyrelsen.epportal.utils.XmlNamespace
import org.w3c.dom.Document
import org.w3c.dom.Element
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeParseException

private val xpath = XPathWrapper(XmlNamespace.SOAP12, XmlNamespace.OPENNCP_CLIENT)

fun parseQueryPatientResponse(document: Document, countryCode: String): List<PatientDemographics> {
    val response = xpath.evalElement("/soap12:Envelope/soap12:Body/ncpc:queryPatientResponse", document)
        ?: throw OpenNcpException("queryPatient: no queryPatientResponse in the response")
    return xpath.evalElements("return", response).map { toPatientDemographics(it, countryCode) }
}

private fun toPatientDemographics(e: Element, countryCode: String): PatientDemographics {
    fun text(name: String): String? = xpath.evalString(name, e).ifEmpty { null }
    return PatientDemographics(
        countryCode = countryCode,
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
internal fun parseDateTime(s: String): OffsetDateTime =
    try {
        OffsetDateTime.parse(s)
    } catch (_: DateTimeParseException) {
        LocalDateTime.parse(s).atOffset(ZoneOffset.UTC)
    }
