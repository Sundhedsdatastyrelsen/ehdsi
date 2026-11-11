package dk.sundhedsdatastyrelsen.epportal.prescriptions

import dk.sundhedsdatastyrelsen.epportal.patient.PatientId

/** Retrieves a prescription document from a foreign NCP. */
fun interface PrescriptionDocumentClient {
    /** @return the CDA document with the given document unique id, or null if there is none */
    fun retrieveDocument(params: Params): String?

    data class Params(
        val countryCode: String,
        val patientId: PatientId,
        val homeCommunityId: String,
        val repositoryUniqueId: String,
        val documentId: String,
    )
}
