package dk.sundhedsdatastyrelsen.epportal.prescriptions

import dk.sundhedsdatastyrelsen.epportal.openncp.parseQueryDocumentsResponse
import dk.sundhedsdatastyrelsen.epportal.patient.PatientId
import dk.sundhedsdatastyrelsen.epportal.utils.XmlUtils

/**
 * For when the application is not connected to OpenNCP.
 */
class DummyPrescriptionListClient : PrescriptionListClient {
    override fun listDocuments(countryCode: String, patientId: PatientId): List<PrescriptionListItem> {
        return parseQueryDocumentsResponse(
            XmlUtils.parse(
                Thread.currentThread().contextClassLoader.getResourceAsStream(
                    "query-documents-dummy.xml"
                )!!
            )
        )
    }
}
