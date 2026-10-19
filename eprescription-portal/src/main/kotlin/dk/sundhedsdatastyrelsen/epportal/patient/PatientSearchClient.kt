package dk.sundhedsdatastyrelsen.epportal.patient

/**
 * Looks up a patient in a foreign NCP.
 */
fun interface PatientSearchClient {
    fun queryPatient(countryCode: String, ids: List<PatientId>): List<PatientDemographics>
}
