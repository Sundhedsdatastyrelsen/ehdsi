package dk.sundhedsdatastyrelsen.epportal

import io.javalin.http.Context
import io.javalin.http.RequestLogger
import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 * Get an SLF4J logger named after the caller class.
 */
fun logger(): Logger =
    LoggerFactory.getLogger(
        StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE).callerClass
    )

fun requestLogger(logger: Logger): RequestLogger = RequestLogger { ctx: Context, ms: Float ->
    logger.info(
        """{requestMethod: {}, requestPath: {}, responseCode: {}, durationMs: {}}""",
        ctx.method().name,
        ctx.path(),
        ctx.statusCode(),
        ms
    )
}
