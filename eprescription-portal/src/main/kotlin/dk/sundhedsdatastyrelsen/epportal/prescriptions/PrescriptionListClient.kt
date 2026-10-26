package dk.sundhedsdatastyrelsen.epportal.prescriptions

import dk.sundhedsdatastyrelsen.epportal.patient.PatientId

/**
 * List prescriptions for a patient.
 */
fun interface PrescriptionListClient {
    /** @return a list of prescription documents that can be retrieved */
    fun listDocuments(countryCode: String, patientId: PatientId): List<PrescriptionListItem>
}
