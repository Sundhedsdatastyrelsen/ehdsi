package dk.sundhedsdatastyrelsen.epportal.app

import dk.sundhedsdatastyrelsen.epportal.TestUtils
import dk.sundhedsdatastyrelsen.epportal.ism.SearchMaskRepository
import dk.sundhedsdatastyrelsen.epportal.patient.DummyPatientSearchClient
import dk.sundhedsdatastyrelsen.epportal.withoutConnectionReuse
import io.javalin.Javalin
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertContains
import kotlin.test.assertFalse

class FindPatientIntegrationTest {

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

        val masks = SearchMaskRepository.load(FindPatient.Config(listOf("DK", "FI"), "config/ism"))
        app = WebApp.createApp(LocalAuth(), masks, DummyPatientSearchClient())
        val port = TestUtils.randomFreePort()
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

    @Test
    fun `unauthenticated find-patient redirects to home with an error`() {
        val resp = client.newCall(Request.Builder().url("$baseUrl/find-patient").get().build()).execute()
        assertEquals(303, resp.code)
        assertEquals("/?error=not-authorized", resp.headers["Location"])
    }

    @Test
    fun `find-patient page lists the configured countries`() {
        val cookieHeader = loginCookieHeader()
        val req = Request.Builder().url("$baseUrl/find-patient").get().header("Cookie", cookieHeader).build()
        val resp = client.newCall(req).execute()
        assertEquals(200, resp.code)
        val body = resp.body.string()
        assertContains(body, "Danmark")
        assertContains(body, "Finland")
    }

    @Test
    fun `find-patient fields fragment for DK contains its field`() {
        val cookieHeader = loginCookieHeader()
        val req = Request.Builder()
            .url("$baseUrl/find-patient/fields?country=DK")
            .get()
            .header("Cookie", cookieHeader)
            .build()
        val resp = client.newCall(req).execute()
        assertEquals(200, resp.code)
        val body = resp.body.string()
        assertContains(body, "Nationalt ID-kortnummer")
        assertContains(body, "name=\"id.0\"")
    }

    @Test
    fun `find-patient fields fragment for unknown country is a 400`() {
        val cookieHeader = loginCookieHeader()
        val req = Request.Builder()
            .url("$baseUrl/find-patient/fields?country=SE")
            .get()
            .header("Cookie", cookieHeader)
            .build()
        val resp = client.newCall(req).execute()
        assertEquals(400, resp.code)
    }

    @Test
    fun `searching DK with a valid id returns the patient and the fields`() {
        val cookieHeader = loginCookieHeader()
        val form = FormBody.Builder().add("country", "DK").add("id.0", "010101-1234").build()
        val req = Request.Builder()
            .url("$baseUrl/find-patient/search")
            .post(form)
            .header("Cookie", cookieHeader)
            .build()
        val resp = client.newCall(req).execute()
        assertEquals(200, resp.code)
        assertEquals("no-store", resp.headers["Cache-Control"])
        val body = resp.body.string()
        assertContains(body, "Testersen")
        assertContains(body, "010101-1234 (1.2.208.176.1.2)")
        assertContains(body, "hx-swap-oob=\"innerMorph\"")
        assertFalse(body.contains("text-red"))
    }

    private fun validate(cookieHeader: String, vararg params: Pair<String, String>): String {
        val form = FormBody.Builder().apply { params.forEach { (k, v) -> add(k, v) } }.build()
        val req = Request.Builder()
            .url("$baseUrl/find-patient/validate")
            .post(form)
            .header("Cookie", cookieHeader)
            .build()
        val resp = client.newCall(req).execute()
        assertEquals(200, resp.code)
        return resp.body.string()
    }

    @Test
    fun `validating the form returns the errors in their slots`() {
        val body = validate(loginCookieHeader(), "country" to "DK", "id.0" to "abc")
        assertContains(body, "id=\"id-0-error\"")
        assertContains(body, "Ugyldigt format")
    }

    @Test
    fun `validating a valid form returns empty error slots`() {
        val body = validate(loginCookieHeader(), "country" to "DK", "id.0" to "010101-1234")
        assertContains(body, "id=\"id-0-error\"")
        assertFalse(body.contains("text-red"))
    }

    @Test
    fun `validating an empty form returns the form-level error`() {
        val body = validate(loginCookieHeader(), "country" to "FI", "id.0" to "")
        assertContains(body, "Udfyld mindst ét felt")
    }

    @Test
    fun `searching DK with an invalid id shows a format error`() {
        val cookieHeader = loginCookieHeader()
        val form = FormBody.Builder().add("country", "DK").add("id.0", "abc").build()
        val req = Request.Builder()
            .url("$baseUrl/find-patient/search")
            .post(form)
            .header("Cookie", cookieHeader)
            .build()
        val resp = client.newCall(req).execute()
        assertEquals(422, resp.code)
        val body = resp.body.string()
        assertContains(body, "Ugyldigt format")
        assertFalse(body.contains("Testersen"))
    }

    @Test
    fun `searching FI with a blank id shows the form-level error`() {
        val cookieHeader = loginCookieHeader()
        val form = FormBody.Builder().add("country", "FI").add("id.0", "").build()
        val req = Request.Builder()
            .url("$baseUrl/find-patient/search")
            .post(form)
            .header("Cookie", cookieHeader)
            .build()
        val resp = client.newCall(req).execute()
        assertEquals(422, resp.code)
        assertContains(resp.body.string(), "Udfyld mindst ét felt")
    }

    @Test
    fun `an invalid input is escaped when the form is re-rendered`() {
        val cookieHeader = loginCookieHeader()
        val form = FormBody.Builder().add("country", "DK").add("id.0", "<script>").build()
        val req = Request.Builder()
            .url("$baseUrl/find-patient/search")
            .post(form)
            .header("Cookie", cookieHeader)
            .build()
        val resp = client.newCall(req).execute()
        val body = resp.body.string()
        assertContains(body, "&lt;script&gt;")
        assertFalse(body.contains("<script>"))
    }
}
