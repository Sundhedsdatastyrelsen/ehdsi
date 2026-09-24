package dk.sundhedsdatastyrelsen.epportal.patient

/**
 * Looks up a patient in a foreign NCP: [dk.sundhedsdatastyrelsen.epportal.openncp.OpenNcpPatientSearchClient] calls
 * `queryPatient` on the OpenNCP client connector, and [DummyPatientSearchClient] returns fabricated data.
 */
fun interface PatientSearchClient {
    fun queryPatient(countryCode: String, ids: List<PatientId>): List<PatientDemographics>
}
