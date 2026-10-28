package dk.sundhedsdatastyrelsen.epportal.app

import dk.sundhedsdatastyrelsen.epportal.app.WebApp.log
import io.javalin.http.Context
import io.javalin.http.HttpStatus
import io.javalin.http.UnauthorizedResponse
import io.javalin.router.JavalinDefaultRoutingApi
import org.eclipse.jetty.ee10.servlet.ServletContextHandler

data class Principal(val cpr: String, val name: String? = null)

interface AuthProvider {
    /** Optional: install filters/servlets (SAML) */
    fun install(handler: ServletContextHandler) {
        // default no-op
    }

    /** Add /login, /logout and more routes */
    fun registerRoutes(routes: JavalinDefaultRoutingApi)

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

enum class AuthMode { DEV }

data class AuthConfig(
    val mode: AuthMode,
    val dev: DevAuthConfig? = null,
) {
    init {
        require(mode != AuthMode.DEV || dev != null) { "auth.dev is required when auth.mode is dev" }
    }

    fun provider(): AuthProvider = when (mode) {
        AuthMode.DEV -> LocalAuth(dev!!.defaultCpr)
    }
}

data class DevAuthConfig(
    /** The CPR `/dev-login` logs in as when no `cpr` query parameter is given. */
    val defaultCpr: String,
)

/**
 * Auth provider for local development use.
 */
class LocalAuth(
    private val defaultCpr: String,
    private val headerName: String = "X-Dev-CPR",
    private val defaultName: String = "Dev User",
) : AuthProvider {
    private val sessionKey = "principal"

    override fun registerRoutes(routes: JavalinDefaultRoutingApi) {
        log.warn("Beware! Local development authorization enabled.")

        routes.get("/login") {ctx ->
            val principal = current(ctx)
            if (principal == null) {
                ctx.redirect("/?error=login-not-enabled", HttpStatus.SEE_OTHER)
                return@get
            }
            ctx.redirect("/", HttpStatus.SEE_OTHER)
        }

        routes.get("/logout") { ctx ->
            logout(ctx)  // SAML does session + persistent logout; both clear tracking cookie
            ctx.redirect("/")
        }

        routes.get("/dev-login") { ctx ->
            val cpr = ctx.queryParam("cpr") ?: defaultCpr
            val name = ctx.queryParam("name") ?: defaultName
            ctx.sessionAttribute(sessionKey, Principal(cpr, name))
            ctx.redirect("/")
        }
        routes.before(::devHeaderAuthHook)
    }

    /** Lets a request authenticate via the [headerName] header instead of `/dev-login`, e.g. from curl. */
    private fun devHeaderAuthHook(ctx: Context) {
        if (ctx.sessionAttribute<Principal>(sessionKey) == null) {
            ctx.header(headerName)?.let { cpr ->
                ctx.sessionAttribute(sessionKey, Principal(cpr, "Dev Header User"))
            }
        }
    }

    override fun current(ctx: Context): Principal? = ctx.sessionAttribute(sessionKey)

    override fun logout(ctx: Context) {
        super.logout(ctx)
    }
}
