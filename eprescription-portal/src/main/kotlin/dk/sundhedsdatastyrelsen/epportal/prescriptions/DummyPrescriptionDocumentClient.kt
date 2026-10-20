package dk.sundhedsdatastyrelsen.epportal.prescriptions

import dk.sundhedsdatastyrelsen.epportal.logger
import kotlin.math.absoluteValue

/**
 * Returns example L3 ePrescriptions from `resources/ep-l3-examples`. The ids in the dummy prescription list don't
 * match the examples, so any id gets an example, picked by the id so the same id always gets the same example.
 */
class DummyPrescriptionDocumentClient : PrescriptionDocumentClient {
    private val log = logger()

    override fun retrieveDocument(documentId: String): String {
        log.info("Using DummyPrescriptionDocumentClient: returning an example document for id {}", documentId)
        val example = examples[documentId.hashCode().absoluteValue % examples.size]
        return requireNotNull(javaClass.getResource("/ep-l3-examples/$example.xml")).readText()
    }

    private companion object {
        val examples = listOf("513671704568702L3", "513671704568902L3")
    }
}
