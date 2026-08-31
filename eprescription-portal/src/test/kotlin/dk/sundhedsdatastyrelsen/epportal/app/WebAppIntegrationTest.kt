package dk.sundhedsdatastyrelsen.epportal.app

import dk.sundhedsdatastyrelsen.epportal.TestUtils
import io.javalin.Javalin
import dk.sundhedsdatastyrelsen.epportal.withoutConnectionReuse
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import java.nio.file.Path
import kotlin.test.assertContains
import kotlin.test.assertFalse

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

        app = WebApp.createApp(LocalAuth())
        val port = TestUtils.randomFreePort()
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
        assertContains(body, "Frabedelse af deling af data i EU")
    }

    @Test
    fun `form redirects to home with an error message if not logged in`() {
        var resp = client.newCall(Request.Builder().url("$baseUrl/form").get().build()).execute()
        assertEquals(303, resp.code)
        val redirectTo = resp.headers["Location"];
        assertEquals("/?error=not-authorized", redirectTo)

        resp = client.newCall(Request.Builder().url(baseUrl + redirectTo!!).get().build()).execute()
        val body = resp.body.string()
        assertContains(body, "Du skal logge på");
    }

    @Test
    fun `form renders with principal`() {
        // Dev login creates session and redirects to /form
        var resp = client.newCall(Request.Builder().url("$baseUrl/dev-login?cpr=1212121234").get().build()).execute()
        assertEquals(302, resp.code)
        val cookies = resp.headers("Set-Cookie")

        // Now call /form with session cookie
        val req = Request.Builder()
            .url("$baseUrl/form")
            .get()
            .header("Cookie", cookies.joinToString("; ") { it.substringBefore(';') })
            .build()
        resp = client.newCall(req).execute()
        assertEquals(200, resp.code)
        val body = resp.body.string().orEmpty()
        assertTrue(body.contains("121212-1234"))
    }

    @Test
    fun `form update toggles flags and redirects`() {
        // Login
        var resp = client.newCall(Request.Builder().url("$baseUrl/dev-login?cpr=0909090009").get().build()).execute()
        val cookies = resp.headers("Set-Cookie")
        val cookieHeader = cookies.joinToString("; ") { it.substringBefore(';') }

        // Set patientSummary=true, ePrescription=false
        val form1 = FormBody.Builder().add("patientSummary", "true").add("ePrescription", "false").build()
        val req1 = Request.Builder()
            .url("$baseUrl/form/update")
            .post(form1)
            .header("Cookie", cookieHeader)
            .build()
        resp = client.newCall(req1).execute()
        assertEquals(302, resp.code)

        // Follow to /form
        val req2 = Request.Builder().url("$baseUrl/form").get().header("Cookie", cookieHeader).build()
        resp = client.newCall(req2).execute()
        var body = resp.body.string()
        assertContains(body, "<input type=\"hidden\" name=\"patientSummary\" value=\"false\"")
        assertContains(body, "Du oprettede en frabedelse d.")
        assertFalse(body.contains("<input type=\"hidden\" name=\"ePrescription\" value=\"false\""))
        assertContains(body, "Du har ikke oprettet en frabedelse.")

        // Now set both true
        val form2 = FormBody.Builder().add("patientSummary", "true").add("ePrescription", "true").build()
        val req3 = Request.Builder().url("$baseUrl/form/update").post(form2).header("Cookie", cookieHeader).build()
        resp = client.newCall(req3).execute()
        assertEquals(302, resp.code)

        val req4 = Request.Builder().url("$baseUrl/form").get().header("Cookie", cookieHeader).build()
        resp = client.newCall(req4).execute()
        body = resp.body.string()
        assertContains(body, "<input type=\"hidden\" name=\"patientSummary\" value=\"false\"")
        assertContains(body, "<input type=\"hidden\" name=\"ePrescription\" value=\"false\"")

        // Finally set both false (remove row)
        val form3 = FormBody.Builder().add("patientSummary", "false").add("ePrescription", "false").build()
        val req5 = Request.Builder().url("$baseUrl/form/update").post(form3).header("Cookie", cookieHeader).build()
        resp = client.newCall(req5).execute()
        assertEquals(302, resp.code)

        val req6 = Request.Builder().url("$baseUrl/form").get().header("Cookie", cookieHeader).build()
        resp = client.newCall(req6).execute()
        body = resp.body.string()
        assertFalse(body.contains("<input type=\"hidden\" name=\"patientSummary\" value=\"false\""))
        assertFalse(body.contains("<input type=\"hidden\" name=\"ePrescription\" value=\"false\""))
    }

    @Test
    fun `form errors redirect back to form with error flag set`() {
        // Use a local app to overwrite the db.
        val app = WebApp.createApp(LocalAuth())
        try {
            val port = TestUtils.randomFreePort()
            app.start(port)
            val baseUrl = "http://localhost:$port"
            // Login
            var resp =
                client.newCall(Request.Builder().url("$baseUrl/dev-login?cpr=0909090009").get().build()).execute()
            val cookies = resp.headers("Set-Cookie")
            val cookieHeader = cookies.joinToString("; ") { it.substringBefore(';') }

            // Set patientSummary=true
            val form1 = FormBody.Builder().add("patientSummary", "true").build()
            val req1 = Request.Builder()
                .url("$baseUrl/form/update")
                .post(form1)
                .header("Cookie", cookieHeader)
                .build()
            resp = client.newCall(req1).execute()
            assertEquals(302, resp.code)
            assertEquals("/form?error=form-update-failed", resp.headers["Location"])
        } finally {
            app.stop();
        }
    }

    @Test
    fun `form errors are shown to the user`() {
        // Login
        var resp = client.newCall(Request.Builder().url("$baseUrl/dev-login?cpr=0909090009").get().build()).execute()
        val cookies = resp.headers("Set-Cookie")
        val cookieHeader = cookies.joinToString("; ") { it.substringBefore(';') }

        val req2 = Request.Builder().url("$baseUrl/form?error=form-update-failed").get().header("Cookie", cookieHeader).build()
        resp = client.newCall(req2).execute()
        var body = resp.body.string()
        assertContains(body, "Der skete en fejl")
    }

    @Test
    fun `form escapes characters`() {
        // login. %3C is <
        var resp = client.newCall(Request.Builder().url("$baseUrl/dev-login?cpr=0101010001&name=John%3Cscript").get().build()).execute()
        val cookies = resp.headers("Set-Cookie")

        // get form
        val req = Request.Builder()
            .url("$baseUrl/form")
            .get()
            .header("Cookie", cookies.joinToString("; ") { it.substringBefore(';') })
            .build()
        resp = client.newCall(req).execute()

        // check result
        assertEquals(200, resp.code)
        val body = resp.body?.string().orEmpty()
        assertContains(body, "John&lt;script")
        assertFalse(body.contains("John<script"))
    }

    @Test
    fun `logout clears tracking cookie and redirects`() {
        // Login first
        var resp = client.newCall(Request.Builder().url("$baseUrl/dev-login?cpr=0101010001").get().build()).execute()
        val cookies = resp.headers("Set-Cookie")
        val cookieHeader = cookies.joinToString("; ") { it.substringBefore(';') }

        resp = client.newCall(Request.Builder().url("$baseUrl/logout").get().header("Cookie", cookieHeader).build())
            .execute()
        assertEquals(302, resp.code)
        val setCookies = resp.headers("Set-Cookie").joinToString("\n")
        assertTrue(setCookies.contains("sds-epportal-id="))
    }
}
