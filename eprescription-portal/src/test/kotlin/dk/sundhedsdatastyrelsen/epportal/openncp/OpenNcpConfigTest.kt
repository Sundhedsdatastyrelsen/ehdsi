package dk.sundhedsdatastyrelsen.epportal.openncp

import dk.sundhedsdatastyrelsen.epportal.Config
import org.junit.jupiter.api.Test
import java.time.Duration
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class OpenNcpConfigTest {
    @Test
    fun `openncp is optional`() {
        assertNull(Config.load().openncp)
    }

    @Test
    fun `the dev profile configures openncp for the local NCP stack`() {
        val openncp = assertNotNull(Config.load(listOf("dev")).openncp)
        assertEquals(6443, openncp.endpoint.port)
        assertEquals("changeit", openncp.truststore?.password?.value)
        assertEquals("tomcat", openncp.signing.alias)
        assertEquals("2262", openncp.hcp.roleCode)
        assertEquals(Duration.ofHours(1), openncp.hcp.validity)
        // The keystores are in ../NCP, relative to where ./gradlew run runs
        OpenNcp.loadKeyStore(assertNotNull(openncp.truststore))
        OpenNcp.loadKeyStore(openncp.signing.keystore)
    }
}
