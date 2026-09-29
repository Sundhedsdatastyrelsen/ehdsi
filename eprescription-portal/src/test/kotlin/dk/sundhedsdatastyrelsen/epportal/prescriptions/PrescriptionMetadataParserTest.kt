package dk.sundhedsdatastyrelsen.epportal.prescriptions

import dk.sundhedsdatastyrelsen.epportal.TestUtils
import dk.sundhedsdatastyrelsen.epportal.utils.XmlUtils
import org.junit.jupiter.api.assertThrows
import java.time.LocalDateTime
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class PrescriptionMetadataParserTest {

    private fun fixture(name: String): String =
        TestUtils.resourceAsStream("xds/$name")!!.bufferedReader(Charsets.UTF_8).use { it.readText() }

    @Test
    fun `parses all 17 prescriptions from the example response, paired L1-L3`() {
        val entries = PrescriptionMetadataParser.parse(XmlUtils.parse(fixture("list-prescriptions.xml")))

        assertEquals(17, entries.size)

        // Every entry should have both a coded and a PDF rendition in this example.
        assertTrue(entries.all { it.l3 != null })
        assertTrue(entries.all { it.l1 != null })

        for (entry in entries) {
            val coded = entry.l3!!.documentUniqueId
            val pdf = entry.l1!!.documentUniqueId
            assertTrue(coded.endsWith("L3"), "expected coded doc id to end with L3: $coded")
            assertTrue(pdf.endsWith("L1"), "expected pdf doc id to end with L1: $pdf")
            assertEquals(coded.removeSuffix("L3"), pdf.removeSuffix("L1"))
        }

        val clomipramin = entries.first { it.l3?.atcCode == "N06AA04" }
        assertEquals("eHDSI - ePrescription", clomipramin.l3!!.title)
        assertEquals("mod depression", clomipramin.l3.description)
        assertEquals("Clomipramin", clomipramin.l3.atcName)
        assertEquals("TABFILM", clomipramin.l3.doseFormCode)
        assertEquals("filmovertrukne tabletter", clomipramin.l3.doseFormName)
        assertEquals("10 mg", clomipramin.l3.strength)
        assertEquals("Karl ePPS", clomipramin.l3.authorName)
        assertEquals(LocalDateTime.of(2025, 4, 10, 12, 33, 2), clomipramin.l3.effectiveTime)
        assertEquals("urn:ihe:iti:xdw:2011:eventCode:open", clomipramin.l3.workflowStatus)
        assertEquals("481817151716102L3", clomipramin.l3.documentUniqueId)
        assertEquals("481817151716102L1", clomipramin.l1!!.documentUniqueId)
        assertEquals("1.2.208", clomipramin.l3.repositoryUniqueId)
        assertEquals("urn:epsos:ep:pre:2010", clomipramin.l3.formatCode)
        assertEquals("urn:ihe:iti:xds-sd:pdf:2008", clomipramin.l1.formatCode)

        val withoutAtc = entries.filter { it.l3?.atcCode == "" }
        assertEquals(1, withoutAtc.size)
        assertTrue(withoutAtc.all { it.l3?.atcName == "" })
    }

    @Test
    fun `a prescription with no ATC classification leaves ATC fields empty`() {
        val entries = PrescriptionMetadataParser.parse(XmlUtils.parse(fixture("missing-atc-classification.xml")))

        assertEquals(1, entries.size)
        val entry = entries.single()
        assertNotNull(entry.l3)

        assertEquals("", entry.l3.atcCode)
        assertEquals("", entry.l3.atcName)
        // Other classifications on the same classificationScheme are still parsed.
        assertEquals("TABFILM", entry.l3.doseFormCode)
        assertEquals("20 mg", entry.l3.strength)

        assertEquals("888888888888888L3", entry.l3.documentUniqueId)
    }

    @Test
    fun `parseHl7Timestamp parses the creationTime format`() {
        assertEquals(
            LocalDateTime.of(2025, 4, 10, 12, 33, 2),
            PrescriptionMetadataParser.parseHl7Timestamp("20250410123302")
        )
    }

    @Test
    fun `parseHl7Timestamp handles truncated precision`() {
        // See https://profiles.ihe.net/ITI/TF/Volume3/ch-4.2.html#4.2.3.1.7
        assertEquals(LocalDateTime.of(2025, 4, 10, 0, 0, 0), PrescriptionMetadataParser.parseHl7Timestamp("20250410"))
        assertEquals(LocalDateTime.of(2025, 1, 1, 0, 0, 0), PrescriptionMetadataParser.parseHl7Timestamp("2025"))
    }

    @Test
    fun `parseHl7Timestamp throws on unexpected input`() {
        assertThrows<IllegalArgumentException> { PrescriptionMetadataParser.parseHl7Timestamp("") }
        assertThrows<IllegalArgumentException> { PrescriptionMetadataParser.parseHl7Timestamp("not-a-timestamp") }
        // Offsets are not supposed to be sent. See https://profiles.ihe.net/ITI/TF/Volume3/ch-4.2.html#4.2.3.1.7
        assertThrows<IllegalArgumentException> { PrescriptionMetadataParser.parseHl7Timestamp("20250410123302+0200") }
    }
}
