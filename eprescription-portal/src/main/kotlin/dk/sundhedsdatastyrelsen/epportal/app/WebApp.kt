package dk.sundhedsdatastyrelsen.epportal.app

import dk.sundhedsdatastyrelsen.epportal.logger
import dk.sundhedsdatastyrelsen.epportal.requestLogger
import freemarker.template.Configuration
import freemarker.template.TemplateExceptionHandler
import io.javalin.Javalin
import io.javalin.http.BadRequestResponse
import io.javalin.http.HttpStatus
import io.javalin.http.staticfiles.Location
import io.javalin.json.JavalinJackson
import io.javalin.rendering.template.JavalinFreemarker
import java.time.format.DateTimeFormatter

/**
 * Initialize FreeMarker template engine
 */
private fun createTemplateEngine(): Configuration {
    val configuration = Configuration(Configuration.VERSION_2_3_32).apply {
        setClassLoaderForTemplateLoading(this::class.java.classLoader, "/templates")
        defaultEncoding = "UTF-8"
        templateExceptionHandler = TemplateExceptionHandler.RETHROW_HANDLER
        logTemplateExceptions = false
    }

    return configuration
}

private fun authProvider(): AuthProvider = LocalAuth()

val dkDateFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")

object WebApp {
    fun createApp(auth: AuthProvider = authProvider()): Javalin {
        val app = Javalin.create { config ->
            config.startup.showJavalinBanner = false
            config.fileRenderer(JavalinFreemarker(createTemplateEngine()))
            config.staticFiles.add { staticFiles ->
                staticFiles.hostedPath = "/"
                staticFiles.directory = "/public"
                staticFiles.location = Location.CLASSPATH
            }
            config.jsonMapper(JavalinJackson())

            config.jetty.modifyServletContextHandler { handler ->
                // Enable sessions
                handler.sessionHandler.maxInactiveInterval = 30 * 60 // 30 minutes in seconds

                auth.install(handler) // SAML adds its filters/servlet; LocalAuth no-op
            }

            config.requestLogger.http(requestLogger(log))

            auth.registerRoutes(config.routes)

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

            config.routes.error(404) { ctx ->
                ctx.redirect("/")
            }
        }

        return app
    }

    data class Config(val port: Int)

    fun startServer(config: Config) {
        val app = createApp()
        app.start(config.port)
    }

    val log = logger()
}
