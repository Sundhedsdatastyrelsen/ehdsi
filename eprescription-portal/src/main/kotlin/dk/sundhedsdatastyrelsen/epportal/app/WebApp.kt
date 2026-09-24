package dk.sundhedsdatastyrelsen.epportal.app

import dk.sundhedsdatastyrelsen.epportal.Config as AppConfig
import dk.sundhedsdatastyrelsen.epportal.ism.SearchMaskRepository
import dk.sundhedsdatastyrelsen.epportal.logger
import dk.sundhedsdatastyrelsen.epportal.openncp.OpenNcpPatientSearchClient
import dk.sundhedsdatastyrelsen.epportal.patient.DummyPatientSearchClient
import dk.sundhedsdatastyrelsen.epportal.patient.PatientSearchClient
import dk.sundhedsdatastyrelsen.epportal.requestLogger
import freemarker.template.Configuration
import freemarker.template.TemplateExceptionHandler
import io.javalin.Javalin
import io.javalin.http.BadRequestResponse
import io.javalin.http.Context
import io.javalin.http.ForbiddenResponse
import io.javalin.http.HttpStatus
import io.javalin.http.UnauthorizedResponse
import io.javalin.http.staticfiles.Location
import io.javalin.json.JavalinJackson
import io.javalin.rendering.template.JavalinFreemarker
import org.eclipse.jetty.http.HttpCookie
import java.io.File
import java.net.URI

private val DEV_MODE = (System.getProperty("epportal.devMode") == "true").also {
    if (it) {
        logger().warn("Running in development mode with hot-reloading enabled.")
    }
}

/**
 * Initialize FreeMarker template engine
 */
private fun createTemplateEngine(): Configuration {
    val configuration = Configuration(Configuration.VERSION_2_3_32).apply {
        if (DEV_MODE) {
            // Enable hot-reloading of templates
            setDirectoryForTemplateLoading(File("src/main/resources/templates"))
            templateUpdateDelayMilliseconds = 0
        } else {
            setClassLoaderForTemplateLoading(this::class.java.classLoader, "/templates")
        }
        defaultEncoding = "UTF-8"
        templateExceptionHandler = TemplateExceptionHandler.RETHROW_HANDLER
        logTemplateExceptions = false
    }

    return configuration
}

private fun authProvider(): AuthProvider = LocalAuth()

private val safeMethods = setOf("GET", "HEAD", "OPTIONS")

/**
 * CSRF protection: rejects state-changing requests that a browser marks as coming from another origin, so another
 * site can't make a logged-in user's browser submit to us. Modern browsers send Sec-Fetch-Site; older ones are
 * checked on Origin instead. Requests with neither header don't come from a browser, so they are let through.
 *
 * A future SAML assertion consumer endpoint receives a cross-site POST from the IdP and must be exempted.
 */
private fun rejectCrossOriginRequests(ctx: Context) {
    if (ctx.method().name in safeMethods) return
    val secFetchSite = ctx.header("Sec-Fetch-Site")
    val origin = ctx.header("Origin")
    val crossOrigin = when {
        secFetchSite != null -> secFetchSite != "same-origin" && secFetchSite != "none"
        origin != null -> runCatching { URI(origin).authority }.getOrNull() != ctx.host()
        else -> false
    }
    if (crossOrigin) throw ForbiddenResponse("Cross-origin request rejected")
}

object WebApp {
    fun createApp(
        auth: AuthProvider = authProvider(),
        searchMasks: SearchMaskRepository,
        patientSearch: PatientSearchClient,
    ): Javalin {
        val app = Javalin.create { config ->
            config.startup.showJavalinBanner = false
            config.fileRenderer(JavalinFreemarker(createTemplateEngine()))
            config.staticFiles.add { staticFiles ->
                staticFiles.hostedPath = "/"
                if (DEV_MODE) {
                    // Enable hot-reloading of static files
                    staticFiles.directory = "src/main/resources/public"
                    staticFiles.location = Location.EXTERNAL
                } else {
                    staticFiles.directory = "/public"
                    staticFiles.location = Location.CLASSPATH
                }
            }
            config.jsonMapper(JavalinJackson())

            config.jetty.modifyServletContextHandler { handler ->
                // Enable sessions
                handler.sessionHandler.maxInactiveInterval = 30 * 60 // 30 minutes in seconds
                // Defence in depth against CSRF, see rejectCrossOriginRequests for the main protection
                handler.sessionHandler.sameSite = HttpCookie.SameSite.LAX

                auth.install(handler) // SAML adds its filters/servlet; LocalAuth no-op
            }

            config.requestLogger.http(requestLogger(log))

            config.routes.before(::rejectCrossOriginRequests)

            // htmx swaps error responses into the page too, so a fragment request from an expired session would
            // replace part of the page with the 401 body. HX-Redirect makes htmx navigate to the login page instead.
            config.routes.exception(UnauthorizedResponse::class.java) { e, ctx ->
                if (ctx.header("HX-Request") == "true") {
                    ctx.header("HX-Redirect", "/?error=not-authorized")
                }
                ctx.status(e.status).result(e.message.orEmpty())
            }

            auth.registerRoutes(config.routes)
            FindPatient.registerRoutes(config.routes, auth, searchMasks, patientSearch)

            // Serve the main index page (static)
            config.routes.get("/") { ctx ->
                ctx.render("frontpage.ftlh", mapOf("error" to ctx.queryParam("error")))
            }

            config.routes.get("/form") { ctx ->
                val principal = auth.current(ctx)
                if (principal == null) {
                    ctx.redirect("/?error=not-authorized", HttpStatus.SEE_OTHER)
                    return@get
                }
                val dataModel = mapOf(
                    // Add the dash to the CPR.
                    "userCpr" to principal.cpr.replaceRange(6, 6, "-"),
                )

                ctx.render("form.ftlh", dataModel)
            }

            config.routes.post("/form/update") { ctx ->
                val principal = auth.require(ctx)
                try {
                    ctx.redirect("/form")
                } catch (e: Exception) {
                    if (e is BadRequestResponse) throw e
                    log.error("An error occurred when user tried to opt in or out", e);
                    ctx.redirect("/form?error=form-update-failed")
                }
            }

            config.routes.get("/logout") { ctx ->
                auth.logout(ctx)  // SAML does session + persistent logout; both clear tracking cookie
                ctx.redirect("/")
            }
        }

        return app
    }

    data class Config(val port: Int)

    fun startServer(config: AppConfig) {
        val searchMasks = SearchMaskRepository.load(config.findPatient)
        val patientSearch = if (config.openncp != null) {
            log.info("Patient search via OpenNCP client connector at {}", config.openncp.endpoint)
            OpenNcpPatientSearchClient.create(config.openncp)
        } else {
            log.warn("OpenNCP is not configured; patient search returns fabricated data")
            DummyPatientSearchClient()
        }
        val app = createApp(searchMasks = searchMasks, patientSearch = patientSearch)
        app.start(config.webApp.port)
    }

    val log = logger()
}
