package dk.sundhedsdatastyrelsen.epportal.app

import dk.sundhedsdatastyrelsen.epportal.TestUtils
import dk.sundhedsdatastyrelsen.epportal.ism.SearchMaskRepository
import dk.sundhedsdatastyrelsen.epportal.patient.DummyPatientSearchClient
import dk.sundhedsdatastyrelsen.epportal.prescriptions.PrescriptionDocumentClient
import dk.sundhedsdatastyrelsen.epportal.withoutConnectionReuse
import io.javalin.Javalin
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertContains
import kotlin.test.assertNotNull

class ShowPrescriptionIntegrationTest {
    private lateinit var client: OkHttpClient
    private lateinit var baseUrl: String
    private lateinit var app: Javalin

    private val documents = PrescriptionDocumentClient { id ->
        if (id == "513671704568702L3") TestUtils.resource("ep-l3-examples/$id.xml")!!.readText() else null
    }

    @BeforeEach
    fun setup() {
        client = OkHttpClient.Builder()
            .followRedirects(false)
            .followSslRedirects(false)
            .withoutConnectionReuse()
            .build()

        val masks = SearchMaskRepository.load(FindPatient.Config(listOf("DK"), "config/ism"))
        val port = TestUtils.randomFreePort()
        app = WebApp.createApp(
            WebApp.Config(port), LocalAuth("0101019999"), masks, DummyPatientSearchClient(), documents,
            TestUtils.cdaDisplay,
        )
        app.start(port)
        baseUrl = "http://localhost:$port"
    }

    @AfterEach
    fun tearDown() {
        app.stop()
    }

    private fun loginCookieHeader(): String {
        val resp = client.newCall(Request.Builder().url("$baseUrl/dev-login").get().build()).execute()
        return resp.headers("Set-Cookie").joinToString("; ") { it.substringBefore(';') }
    }

    private fun get(path: String, cookie: String? = null): Response =
        client.newCall(
            Request.Builder().url("$baseUrl$path").get().apply { if (cookie != null) header("Cookie", cookie) }.build()
        ).execute()

    @Test
    fun `the prescription page frames the sandboxed display`() {
        val resp = get("/prescriptions/513671704568702L3", loginCookieHeader())

        assertEquals(200, resp.code)
        val body = resp.body.string()
        assertContains(body, """src="/prescriptions/513671704568702L3/display"""")
        assertContains(body, """sandbox="allow-same-origin"""")
    }

    @Test
    fun `the display renders the prescription with a restrictive CSP`() {
        val resp = get("/prescriptions/513671704568702L3/display", loginCookieHeader())

        assertEquals(200, resp.code)
        val csp = assertNotNull(resp.header("Content-Security-Policy"))
        assertContains(csp, "default-src 'none'")
        assertContains(csp, "frame-ancestors 'self'")
        assertContains(resp.body.string(), "Etindros 21")
    }

    @Test
    fun `an unknown prescription is not found`() {
        assertEquals(404, get("/prescriptions/unknown/display", loginCookieHeader()).code)
    }

    @Test
    fun `the pages require login`() {
        assertEquals(303, get("/prescriptions/513671704568702L3").code)
        assertEquals(401, get("/prescriptions/513671704568702L3/display").code)
    }
}
