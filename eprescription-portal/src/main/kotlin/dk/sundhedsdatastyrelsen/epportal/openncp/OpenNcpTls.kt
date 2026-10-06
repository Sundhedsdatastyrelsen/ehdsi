package dk.sundhedsdatastyrelsen.epportal.openncp

import java.net.Socket
import java.security.KeyStore
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLEngine
import javax.net.ssl.TrustManagerFactory
import javax.net.ssl.X509ExtendedTrustManager
import javax.net.ssl.X509TrustManager

object OpenNcpTls {
    /**
     * @param truststore trusted CAs, or null for the JVM default.
     * @param dangerouslyDisableHostnameVerification if true, the server certificate is accepted regardless of which
     *   host it is issued to. The chain is still validated against [truststore]. Only for development.
     */
    fun sslContext(truststore: KeyStore?, dangerouslyDisableHostnameVerification: Boolean): SSLContext {
        val tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm())
        tmf.init(truststore)
        val trustManagers = if (dangerouslyDisableHostnameVerification) {
            val delegate = tmf.trustManagers.filterIsInstance<X509TrustManager>().single()
            arrayOf(NoHostnameVerificationTrustManager(delegate))
        } else {
            tmf.trustManagers
        }
        return SSLContext.getInstance("TLSv1.3").apply { init(null, trustManagers, null) }
    }

    /**
     * Validates the chain with [delegate], but not the hostname. java.net.http.HttpClient always enables hostname
     * verification in the SSLParameters, which the JDK trust manager performs in its socket/engine variants. Those
     * are deliberately not called here.
     */
    internal class NoHostnameVerificationTrustManager(
        private val delegate: X509TrustManager,
    ) : X509ExtendedTrustManager() {
        override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) =
            delegate.checkServerTrusted(chain, authType)

        override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String, socket: Socket?) =
            checkServerTrusted(chain, authType)

        override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String, engine: SSLEngine?) =
            checkServerTrusted(chain, authType)

        override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) =
            throw CertificateException("Client certificates are not accepted")

        override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String, socket: Socket?) =
            checkClientTrusted(chain, authType)

        override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String, engine: SSLEngine?) =
            checkClientTrusted(chain, authType)

        override fun getAcceptedIssuers(): Array<X509Certificate> = delegate.acceptedIssuers
    }
}
