package dk.sundhedsdatastyrelsen.epportal

import okhttp3.ConnectionPool
import okhttp3.OkHttpClient
import java.io.InputStream
import java.net.ServerSocket
import java.net.URL
import java.util.concurrent.TimeUnit

object TestUtils {
    fun randomFreePort(): Int = ServerSocket(0).use { it.localPort }

    fun resource(name: String): URL? = Thread.currentThread().contextClassLoader.getResource(name)

    fun resourceAsStream(name: String): InputStream? = Thread.currentThread().contextClassLoader.getResourceAsStream(name)
}

/**
 * Disable OkHttp connection reuse for a test client.
 */
fun OkHttpClient.Builder.withoutConnectionReuse(): OkHttpClient.Builder =
    connectionPool(ConnectionPool(0, 1, TimeUnit.NANOSECONDS))
