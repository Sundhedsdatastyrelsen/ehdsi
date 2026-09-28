package dk.sundhedsdatastyrelsen.epportal.ism

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.io.File

class IsmParserTest {

    @Test
    fun `parses the DK ISM file`() {
        val mask = File("config/ism/ism-dk.xml").inputStream().use { IsmParser.parse(it) }

        assertEquals("DK", mask.countryCode)
        assertEquals(1, mask.idFields.size)

        val field = mask.idFields[0]
        assertEquals("1.2.208.176.1.2", field.domain)
        assertTrue(field.mandatory)
        val format = field.format!!
        assertTrue(format.matches("010101-1234"))
        assertTrue(format.matches("0101011234"))
        assertFalse(format.matches("12345"))
    }

    @Test
    fun `parses the FI ISM file`() {
        val mask = File("config/ism/ism-fi.xml").inputStream().use { IsmParser.parse(it) }

        assertEquals("FI", mask.countryCode)
        assertEquals(1, mask.idFields.size)

        val field = mask.idFields[0]
        assertNull(field.format)
        assertFalse(field.mandatory)
    }

    @Test
    fun `skips CHOICE identifiers, grouped ids and textField without failing`() {
        val xml = """
            <searchFields xmlns="http://ec.europa.eu/sante/ehncp/ism">
              <country code="XX">
                <patientSearch>
                  <identifier type="CHOICE">
                    <id contextualDescription="a" domain="1.2" format="" label="label.ism.passportNumber"/>
                  </identifier>
                  <identifier type="NORMAL">
                    <ids>
                      <id contextualDescription="b" domain="1.3" format="" label="label.ism.healthCardNumber"/>
                    </ids>
                  </identifier>
                  <textField contextualDescription="c" label="label.ism.familyName"/>
                </patientSearch>
              </country>
            </searchFields>
        """.trimIndent()

        val mask = xml.byteInputStream().use { IsmParser.parse(it) }

        assertEquals("XX", mask.countryCode)
        assertTrue(mask.idFields.isEmpty())
    }

    @Test
    fun `throws when there is no searchFields element`() {
        val xml = """<?xml version="1.0"?><nothing/>"""

        assertThrows(IllegalArgumentException::class.java) {
            xml.byteInputStream().use { IsmParser.parse(it) }
        }
    }
}
