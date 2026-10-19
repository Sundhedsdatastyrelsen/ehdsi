package dk.sundhedsdatastyrelsen.epportal.app

import dk.sundhedsdatastyrelsen.epportal.TestUtils
import dk.sundhedsdatastyrelsen.epportal.patient.DummyPatientSearchClient
import dk.sundhedsdatastyrelsen.epportal.testMasks
import dk.sundhedsdatastyrelsen.epportal.withoutConnectionReuse
import io.javalin.Javalin
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.UUID
import kotlin.test.assertContains
import kotlin.time.Clock

class PrescriptionsIntegrationTest {

    private lateinit var client: OkHttpClient
    private var baseUrl: String = ""
    private lateinit var app: Javalin

    @BeforeEach
    fun setup() {
        client = OkHttpClient.Builder()
            .followRedirects(false)
            .followSslRedirects(false)
            .withoutConnectionReuse()
            .build()

        val port = TestUtils.randomFreePort()
        app = WebApp.createApp(
            WebApp.Config(port),
            LocalAuth("0101019999"),
            testMasks(),
            DummyPatientSearchClient(),
            Clock.System
        )
        app.start(port)
        baseUrl = "http://localhost:$port"
    }

    @AfterEach
    fun tearDown() {
        app.stop()
    }

    private fun loginCookieHeader(cpr: String = "0101019999"): String {
        val resp = client.newCall(Request.Builder().url("$baseUrl/dev-login?cpr=$cpr").get().build()).execute()
        val cookies = resp.headers("Set-Cookie")
        return cookies.joinToString("; ") { it.substringBefore(';') }
    }

    /** Searches for a patient and returns the opaque id from the link to their prescriptions. */
    private fun searchForOpaqueId(cookieHeader: String): String {
        val form = FormBody.Builder().add("country", "DK").add("id.0", "010101-1234").build()
        val req = Request.Builder()
            .url("$baseUrl/find-patient/search")
            .post(form)
            .header("Cookie", cookieHeader)
            .build()
        val resp = client.newCall(req).execute()
        assertEquals(200, resp.code)
        val body = resp.body.string()
        return Regex("/prescriptions\\?opaque-id=([0-9a-f-]+)").find(body)!!.groupValues[1]
    }

    @Test
    fun `prescriptions page redirects to home with an error message if not logged in`() {
        val resp = client.newCall(Request.Builder().url("$baseUrl/prescriptions").get().build()).execute()
        assertEquals(303, resp.code)
        assertEquals("/?error=not-authorized", resp.headers["Location"])
    }

    private fun getPrescriptions(cookieHeader: String, query: String): Response {
        val req = Request.Builder()
            .url("$baseUrl/prescriptions$query")
            .get()
            .header("Cookie", cookieHeader)
            .build()
        return client.newCall(req).execute()
    }

    @Test
    fun `prescriptions page renders the example prescription list when logged in`() {
        val cookieHeader = loginCookieHeader()
        val opaqueId = searchForOpaqueId(cookieHeader)

        val resp = getPrescriptions(cookieHeader, "?opaque-id=$opaqueId")
        assertEquals(200, resp.code)
        val body = resp.body.string()
        assertContains(body, "Diazepam")
        assertContains(body, "N02BE01")
    }

    @Test
    fun `prescriptions page without an opaque id is a 400`() {
        val resp = getPrescriptions(loginCookieHeader(), "")
        assertEquals(400, resp.code)
    }

    @Test
    fun `prescriptions page with a malformed opaque id is a 400`() {
        val resp = getPrescriptions(loginCookieHeader(), "?opaque-id=not-a-uuid")
        assertEquals(400, resp.code)
    }

    @Test
    fun `prescriptions page with another user's opaque id is a 404`() {
        val opaqueId = searchForOpaqueId(loginCookieHeader("1212121234"))
        val resp = getPrescriptions(loginCookieHeader("0101010001"), "?opaque-id=$opaqueId")
        assertEquals(404, resp.code)
    }

    @Test
    fun `prescriptions page with an unknown opaque id is a 404`() {
        val resp = getPrescriptions(loginCookieHeader(), "?opaque-id=${UUID.randomUUID()}")
        assertEquals(404, resp.code)
        assertContains(resp.body.string(), "please search again")
    }
}
