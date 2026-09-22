package dk.sundhedsdatastyrelsen.epportal.patient

import java.time.OffsetDateTime

/**
 * Mirrors the `return` element of the OpenNCP `queryPatient` SOAP response.
 */
data class PatientDemographics(
    val givenName: String?,
    val familyName: String?,
    val birthDate: OffsetDateTime?,
    val gender: String?,
    val addressStreet: String?,
    val addressPostalCode: String?,
    val addressCity: String?,
    val addressCountry: String?,
    val phone: String?,
    val email: String?,
    val patientIds: List<PatientId>,
)

data class PatientId(
    val root: String,
    val extension: String,
)
