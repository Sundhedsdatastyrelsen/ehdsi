package dk.sundhedsdatastyrelsen.epportal.patient

/**
 * Looks up a patient in a foreign NCP. In this iteration this wraps the OpenNCP `queryPatient` SOAP
 * call (or, currently, [DummyPatientSearchClient]) rather than being called directly.
 */
fun interface PatientSearchClient {
    fun queryPatient(countryCode: String, ids: List<PatientId>): List<PatientDemographics>
}
