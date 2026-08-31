package dk.sundhedsdatastyrelsen.epportal.app

import dk.sundhedsdatastyrelsen.epportal.app.WebApp.log
import io.javalin.http.Context
import io.javalin.http.UnauthorizedResponse
import io.javalin.router.JavalinDefaultRoutingApi
import org.eclipse.jetty.ee10.servlet.ServletContextHandler

data class Principal(val cpr: String, val name: String? = null)

interface AuthProvider {
    /** Optional: install filters/servlets (SAML) */
    fun install(handler: ServletContextHandler) {
        // default no-op
    }

    /** Optional: add dev login routes etc. */
    fun registerRoutes(routes: JavalinDefaultRoutingApi) {
        // default no-op
    }

    /** Resolve current principal from request/session */
    fun current(ctx: Context): Principal?

    /** Resolve current principal from request/session, failing with
     * a 401 Not Authenticated response if it is not found. */
    fun require(ctx: Context): Principal =
        current(ctx) ?: throw UnauthorizedResponse("Not authenticated")

    /** Clear auth state */
    fun logout(ctx: Context) {
        ctx.req().session.invalidate()
    }
}

/**
 * Auth provider for local development use.
 */
class LocalAuth(
    private val defaultCpr: String = System.getenv("DEV_CPR") ?: "0101019999",
    private val headerName: String = "X-Dev-CPR",
    private val defaultName: String = "Dev User",
) : AuthProvider {
    private val sessionKey = "principal"

    override fun registerRoutes(routes: JavalinDefaultRoutingApi) {
        log.warn("Beware! Local development authorization enabled.")

        routes.get("/dev-login") { ctx ->
            val cpr = ctx.queryParam("cpr") ?: defaultCpr
            val name = ctx.queryParam("name") ?: defaultName
            ctx.sessionAttribute(sessionKey, Principal(cpr, name))
            ctx.redirect("/form")
        }
        routes.before("/form*") { ctx ->
            if (ctx.sessionAttribute<Principal>(sessionKey) == null) {
                ctx.header(headerName)?.let { cpr ->
                    ctx.sessionAttribute(sessionKey, Principal(cpr, "Dev Header User"))
                }
            }
        }
    }

    override fun current(ctx: Context): Principal? = ctx.sessionAttribute(sessionKey)

    override fun logout(ctx: Context) {
        super.logout(ctx)
    }
}
