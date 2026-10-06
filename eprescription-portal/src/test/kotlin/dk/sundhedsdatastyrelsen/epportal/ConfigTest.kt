package dk.sundhedsdatastyrelsen.epportal

import com.sksamuel.hoplite.ConfigException
import dk.sundhedsdatastyrelsen.epportal.app.AuthMode
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import java.nio.file.Path
import kotlin.io.path.writeText
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class ConfigTest {
    @Test
    fun `the base config loads on its own`() {
        val config = Config.load()
        assertFalse(config.webApp.hotReloading)
        assertEquals(AuthMode.DEV, config.auth.mode)
    }

    @Test
    fun `the dev profile enables hot reloading`() {
        assertTrue(Config.load(listOf("dev")).webApp.hotReloading)
    }

    @Test
    fun `profiles take precedence over the base config, the last one most`(@TempDir dir: Path) {
        dir.writeBaseConfig()
        dir.resolve("application-a.toml").writeText("[webapp]\nport = 1\nhotReloading = true")
        dir.resolve("application-b.toml").writeText("[webapp]\nport = 2")

        val config = Config.load(listOf("a", "b"), dir)
        assertEquals(2, config.webApp.port)
        assertTrue(config.webApp.hotReloading)
        assertEquals(listOf("DK"), config.findPatient.countries)
    }

    @Test
    fun `a profile without a file is an error`(@TempDir dir: Path) {
        dir.writeBaseConfig()
        assertFailsWith<ConfigException> { Config.load(listOf("missing"), dir) }
    }

    @Test
    fun `an unknown key is an error`(@TempDir dir: Path) {
        dir.writeBaseConfig()
        dir.resolve("application-typo.toml").writeText("[webapp]\nhotReloadnig = true")
        val e = assertFailsWith<ConfigException> { Config.load(listOf("typo"), dir) }
        assertContains(e.message.orEmpty(), "hotReloadnig")
    }

    @Test
    fun `env vars are read where referenced`(@TempDir dir: Path) {
        dir.writeBaseConfig(ismDirectory = "\${HOME}/ism")
        assertEquals(System.getenv("HOME") + "/ism", Config.load(dir = dir).findPatient.ismDirectory)
    }

    @Test
    fun `dev auth requires its settings`(@TempDir dir: Path) {
        dir.resolve("application.toml").writeText(
            """
            [webapp]
            port = 8080
            [auth]
            mode = "DEV"
            [findPatient]
            countries = ["DK"]
            ismDirectory = "ism"
            """.trimIndent()
        )
        val e = assertFailsWith<ConfigException> { Config.load(dir = dir) }
        assertContains(e.message.orEmpty(), "auth.dev is required")
    }

    private fun Path.writeBaseConfig(ismDirectory: String = "ism") {
        resolve("application.toml").writeText(
            """
            [webapp]
            port = 8080
            [auth]
            mode = "DEV"
            [auth.dev]
            defaultCpr = "0101019999"
            [findPatient]
            countries = ["DK"]
            ismDirectory = "$ismDirectory"
            """.trimIndent()
        )
    }
}
