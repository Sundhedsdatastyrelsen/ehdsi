package dk.sundhedsdatastyrelsen.epportal.openncp

import assertk.assertThat
import assertk.assertions.hasSize
import assertk.assertions.isEmpty
import assertk.assertions.isEqualTo
import assertk.assertions.isNull
import dk.sundhedsdatastyrelsen.epportal.TestUtils
import dk.sundhedsdatastyrelsen.epportal.utils.XmlUtils
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.time.LocalDateTime

class QueryDocumentsResponseTest {
    private fun fixture(name: String) = XmlUtils.parse(TestUtils.resourceAsStream("openncp/$name")!!)

    @Test
    fun `parses prescriptions listed twice once, pairing L3 and L1 by formatCode`() {
        val items = parseQueryDocumentsResponse(fixture("query-documents-response.xml"))
        assertThat(items).hasSize(3)

        val l3 = items[0].l3
        assertThat(l3.documentUniqueId).isEqualTo("1.2.246.10.1.2025.1^L3")
        assertThat(l3.registryObjectId).isEqualTo("1.2.246.10.1.2025.1^L3")
        assertThat(l3.repositoryUniqueId).isEqualTo("2.16.17.710.850.1000.990.1.1")
        assertThat(l3.homeCommunityId).isEqualTo("2.16.17.710.850.1000.990.1")
        assertThat(l3.formatCode).isEqualTo("urn:epsos:ep:pre:2010")
        assertThat(l3.title).isEqualTo("ePrescription")
        assertThat(l3.description).isEqualTo("Amoxicillin 500 mg capsules")
        assertThat(l3.authorName).isEqualTo("Dr. Liisa Lääkäri")
        assertThat(l3.effectiveTime).isEqualTo(LocalDateTime.of(2025, 4, 10, 10, 33, 2))
        assertThat(l3.atcCode).isEqualTo("J01CA04")
        assertThat(l3.atcName).isEqualTo("amoxicillin")
        assertThat(l3.doseFormCode).isEqualTo("10210000")
        assertThat(l3.doseFormName).isEqualTo("Capsule, hard")
        assertThat(l3.strength).isEqualTo("500 mg")
        assertThat(l3.substitution).isEqualTo("G")
        assertThat(l3.dispensable).isEqualTo(true)

        assertThat(items[0].l1.documentUniqueId).isEqualTo("1.2.246.10.1.2025.1^L1")
        assertThat(items[0].l1.formatCode).isEqualTo("urn:ihe:iti:xds-sd:pdf:2008")
        assertThat(items[0].l1.dispensable).isEqualTo(true)
    }

    @Test
    fun `missing elements are null`() {
        val item = parseQueryDocumentsResponse(fixture("query-documents-response.xml"))[1]
        assertThat(item.l1.documentUniqueId).isEqualTo("1.2.246.10.1.2025.2^L1")
        val l3 = item.l3
        assertThat(l3.documentUniqueId).isEqualTo("1.2.246.10.1.2025.2^L3")
        assertThat(l3.effectiveTime).isEqualTo(LocalDateTime.of(2025, 3, 1, 8, 0, 0))
        assertThat(l3.description).isNull()
        assertThat(l3.authorName).isNull()
        assertThat(l3.atcCode).isNull()
        assertThat(l3.substitution).isNull()
        assertThat(l3.dispensable).isEqualTo(false)
    }

    @Test
    fun `a prescription without ATC code has null ATC fields and is otherwise parsed`() {
        val item = parseQueryDocumentsResponse(fixture("query-documents-response.xml"))[2]
        assertThat(item.l1.documentUniqueId).isEqualTo("1.2.246.10.1.2025.3^L1")
        val l3 = item.l3
        assertThat(l3.atcCode).isNull()
        assertThat(l3.atcName).isNull()
        assertThat(l3.documentUniqueId).isEqualTo("1.2.246.10.1.2025.3^L3")
        assertThat(l3.doseFormCode).isEqualTo("10221000")
        assertThat(l3.doseFormName).isEqualTo("Film-coated tablet")
        assertThat(l3.strength).isEqualTo("20 mg")
    }

    @Test
    fun `parses a real connector response, listing each prescription once`() {
        val items = parseQueryDocumentsResponse(fixture("query-documents-response-2.xml"))
        assertThat(items).hasSize(1)

        val l3 = items[0].l3
        assertThat(l3.documentUniqueId).isEqualTo("1.2.208.176.7.2.3^531314369290021L3")
        assertThat(l3.repositoryUniqueId).isEqualTo("1.2.208.176.7.2.3")
        assertThat(l3.homeCommunityId).isEqualTo("urn:oid:1.2.208")
        assertThat(l3.formatCode).isEqualTo("urn:epsos:ep:pre:2010")
        assertThat(l3.title).isEqualTo("ePrescription")
        assertThat(l3.description).isEqualTo("Cipramil (Citalopram)")
        assertThat(l3.authorName).isEqualTo("Helle Bonde")
        assertThat(l3.effectiveTime).isEqualTo(LocalDateTime.of(2026, 11, 2, 10, 19, 29))
        assertThat(l3.atcCode).isEqualTo("N06AB04")
        assertThat(l3.atcName).isEqualTo("Citalopram")
        assertThat(l3.doseFormCode).isEqualTo("TABFILM")
        assertThat(l3.doseFormName).isEqualTo("filmovertrukne tabletter")
        assertThat(l3.strength).isEqualTo("20 mg")
        assertThat(l3.substitution).isEqualTo("G")
        assertThat(l3.dispensable).isEqualTo(true)

        assertThat(items[0].l1.documentUniqueId).isEqualTo("1.2.208.176.7.2.3^531314369290021L1")
        assertThat(items[0].l1.formatCode).isEqualTo("urn:ihe:iti:xds-sd:pdf:2008")
    }

    @Test
    fun `an empty response means no prescriptions`() {
        val doc = XmlUtils.parse(
            """<soap:Envelope xmlns:soap="http://www.w3.org/2003/05/soap-envelope"><soap:Body>
                <ns2:queryDocumentsResponse xmlns:ns2="http://api.client.core.openncp.sante.ec.europa.eu"/>
                </soap:Body></soap:Envelope>""".byteInputStream(),
        )
        assertThat(parseQueryDocumentsResponse(doc)).isEmpty()
    }

    @Test
    fun `a response of the wrong kind is an error`() {
        assertThrows<OpenNcpException> { parseQueryDocumentsResponse(fixture("query-patient-response.xml")) }
    }
}
