package dk.sundhedsdatastyrelsen.epportal.app.auth

import dk.sundhedsdatastyrelsen.epportal.app.SamlAuth
import org.eclipse.jetty.ee10.servlet.ServletContextHandler
import kotlin.test.Test
import kotlin.test.assertTrue

class SamlAuthWiringTest {

    @Test
    fun `install registers filters and servlet`() {
        val handler = ServletContextHandler("/", ServletContextHandler.SESSIONS)
        val saml = SamlAuth()

        saml.install(handler)

        // There should be at least one filter (SameSiteFilter) and others
        val filters = handler.servletHandler.filterMappings
        assertTrue(filters != null && filters.isNotEmpty())

        // Dispatcher servlet mapping for /saml/* should exist
        val servlets = handler.servletHandler.servletMappings
        val hasSamlMapping = servlets.any { mapping -> mapping.pathSpecs?.any { it.startsWith("/saml/") } == true }
        assertTrue(hasSamlMapping)
    }
}
