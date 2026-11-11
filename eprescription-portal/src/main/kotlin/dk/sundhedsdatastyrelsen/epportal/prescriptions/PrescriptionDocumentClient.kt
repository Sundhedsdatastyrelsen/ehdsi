package dk.sundhedsdatastyrelsen.epportal.prescriptions

/**
 * Retrieves a prescription document from a foreign NCP. This will wrap the OpenNCP retrieve call; for now it is
 * [DummyPrescriptionDocumentClient].
 */
fun interface PrescriptionDocumentClient {
    /** @return the CDA document with the given document unique id, or null if there is none */
    fun retrieveDocument(documentId: String): String?
}
