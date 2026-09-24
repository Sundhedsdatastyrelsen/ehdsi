package dk.sundhedsdatastyrelsen.epportal.openncp

import dk.sundhedsdatastyrelsen.epportal.Config
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import java.nio.file.Path
import java.time.Duration
import kotlin.io.path.readText
import kotlin.io.path.writeText
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class OpenNcpConfigTest {
    @Test
    fun `openncp is optional`() {
        assertNull(Config.load(localOverride = null).openncp)
    }

    @Test
    fun `the commented-out example in application toml loads`(@TempDir dir: Path) {
        val uncommented = Path.of("config", "application.toml").readText()
            .lines()
            .joinToString("\n") { line ->
                // Uncomment config lines (#key or #[section]), but not explanatory comments ("# ..." or "## ...").
                if (line.startsWith("#") && !line.startsWith("# ") && !line.startsWith("##") && line.length > 1) {
                    line.removePrefix("#")
                } else {
                    line
                }
            }
        val file = dir.resolve("application.toml").apply { writeText(uncommented) }

        val openncp = assertNotNull(Config.load(file, localOverride = null).openncp)
        assertEquals(6443, openncp.endpoint.port)
        assertEquals("changeit", openncp.truststore?.password?.value)
        assertEquals("tomcat", openncp.signing.alias)
        assertEquals("2262", openncp.hcp.roleCode)
        assertEquals(Duration.ofHours(1), openncp.hcp.validity)
    }
}
