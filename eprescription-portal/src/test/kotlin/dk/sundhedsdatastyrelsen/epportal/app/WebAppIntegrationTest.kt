package dk.sundhedsdatastyrelsen.epportal.app

import dk.sundhedsdatastyrelsen.epportal.TestUtils
import dk.sundhedsdatastyrelsen.epportal.patient.DummyPatientSearchClient
import dk.sundhedsdatastyrelsen.epportal.prescriptions.DummyPrescriptionDocumentClient
import dk.sundhedsdatastyrelsen.epportal.testMasks
import dk.sundhedsdatastyrelsen.epportal.withoutConnectionReuse
import io.javalin.Javalin
import okhttp3.OkHttpClient
import okhttp3.Request
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import java.nio.file.Path
import kotlin.test.assertContains
import kotlin.test.assertFalse
import kotlin.time.Clock

class WebAppIntegrationTest {

    private lateinit var client: OkHttpClient
    private var baseUrl: String = ""
    private lateinit var app: Javalin

    @BeforeEach
    fun setup(@TempDir tempDir: Path) {
        // Use a fresh SQLite DB
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
            DummyPrescriptionDocumentClient(),
            TestUtils.cdaDisplay,
            Clock.System
        )
        app.start(port)
        baseUrl = "http://localhost:$port"
    }

    @AfterEach
    fun tearDown() {
        app.stop()
    }

    @Test
    fun `frontpage is served`() {
        val resp = client.newCall(Request.Builder().url("$baseUrl/").get().build()).execute()
        assertEquals(200, resp.code)
        val body = resp.body.string()
        assertContains(body, "ePrescription Portal")
        assertContains(body, "href=\"/login\"")
        assertContains(body, "Log på")
        assertFalse(body.contains("href=\"/find-patient\""))
        assertFalse(body.contains("Find patient"))
        assertFalse(body.contains("href=\"/logout\""))
        assertFalse(body.contains("Log af"))
    }

    @Test
    fun `login redirects to home with an error message if not logged in`() {
        var resp = client.newCall(Request.Builder().url("$baseUrl/login").get().build()).execute()
        assertEquals(303, resp.code)
        val redirectTo = resp.headers["Location"];
        assertEquals("/?error=login-not-enabled", redirectTo)

        resp = client.newCall(Request.Builder().url(baseUrl + redirectTo!!).get().build()).execute()
        val body = resp.body.string()
        assertContains(body, "Det er ikke muligt at logge på");
    }

    @Test
    fun `frontpage renders with principal`() {
        // Dev login creates session and redirects to /
        var resp = client.newCall(Request.Builder().url("$baseUrl/dev-login?cpr=1212121234").get().build()).execute()
        assertEquals(302, resp.code)
        val cookies = resp.headers("Set-Cookie")

        // Now call / with session cookie
        val req = Request.Builder()
            .url("$baseUrl/")
            .get()
            .header("Cookie", cookies.joinToString("; ") { it.substringBefore(';') })
            .build()
        resp = client.newCall(req).execute()
        assertEquals(200, resp.code)
        val body = resp.body.string()
        assertContains(body, "121212-1234")
        assertContains(body, "href=\"/find-patient\"")
        assertContains(body, "Find patient")
        assertContains(body, "href=\"/logout\"")
        assertContains(body, "Log af")
        assertFalse(body.contains("href=\"/login\""))
    }

    @Test
    fun `login redirects to home if already logged in`() {
        var resp = client.newCall(Request.Builder().url("$baseUrl/dev-login?cpr=1212121234").get().build()).execute()
        val cookies = resp.headers("Set-Cookie")
        val cookieHeader = cookies.joinToString("; ") { it.substringBefore(';') }

        resp = client.newCall(Request.Builder().url("$baseUrl/login").get().header("Cookie", cookieHeader).build())
            .execute()
        assertEquals(303, resp.code)
        assertEquals("/", resp.headers["Location"])
    }

    @Test
    fun `login errors are shown to the user`() {
        // Login
        var resp = client.newCall(Request.Builder().url("$baseUrl/dev-login?cpr=0909090009").get().build()).execute()
        val cookies = resp.headers("Set-Cookie")
        val cookieHeader = cookies.joinToString("; ") { it.substringBefore(';') }

        val req2 =
            Request.Builder().url("$baseUrl/?error=login-failed").get().header("Cookie", cookieHeader).build()
        resp = client.newCall(req2).execute()
        var body = resp.body.string()
        assertContains(body, "Der er sket en fejl")

        val req3 =
            Request.Builder().url("$baseUrl/?error=not-authorized").get().header("Cookie", cookieHeader).build()
        resp = client.newCall(req3).execute()
        body = resp.body.string()
        assertContains(body, "Du skal logge på.")
    }

    @Test
    fun `principal display escapes characters`() {
        // login. %3C is <
        var resp =
            client.newCall(Request.Builder().url("$baseUrl/dev-login?cpr=0101010001&name=John%3Cscript").get().build())
                .execute()
        val cookies = resp.headers("Set-Cookie")

        // get form
        val req = Request.Builder()
            .url("$baseUrl/")
            .get()
            .header("Cookie", cookies.joinToString("; ") { it.substringBefore(';') })
            .build()
        resp = client.newCall(req).execute()

        // check result
        assertEquals(200, resp.code)
        val body = resp.body.string()
        assertContains(body, "John&lt;script")
        assertFalse(body.contains("John<script"))
    }

    @Test
    fun `logout invalidates session and redirects`() {
        // Login first
        var resp = client.newCall(Request.Builder().url("$baseUrl/dev-login?cpr=0101010001").get().build()).execute()
        val cookies = resp.headers("Set-Cookie")
        val cookieHeader = cookies.joinToString("; ") { it.substringBefore(';') }

        resp = client.newCall(Request.Builder().url("$baseUrl/logout").get().header("Cookie", cookieHeader).build())
            .execute()
        assertEquals(302, resp.code)
        assertEquals("/", resp.headers["Location"])

        // Test that session is invalidated.
        resp =
            client.newCall(Request.Builder().url("$baseUrl/find-patient").get().header("Cookie", cookieHeader).build())
                .execute()
        assertEquals(303, resp.code)
    }
}
