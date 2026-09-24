package dk.sundhedsdatastyrelsen.epportal.openncp

import java.net.Socket
import java.security.KeyStore
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import javax.naming.ldap.LdapName
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLEngine
import javax.net.ssl.TrustManagerFactory
import javax.net.ssl.X509ExtendedTrustManager
import javax.net.ssl.X509TrustManager

object OpenNcpTls {
    /**
     * @param truststore trusted CAs, or null for the JVM default.
     * @param expectedHostname if set, the server certificate must be issued to this name instead of the endpoint's
     *   host. The chain is still validated as normal.
     */
    fun sslContext(truststore: KeyStore?, expectedHostname: String?): SSLContext {
        val tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm())
        tmf.init(truststore)
        val trustManagers = if (expectedHostname == null) {
            tmf.trustManagers
        } else {
            val delegate = tmf.trustManagers.filterIsInstance<X509TrustManager>().single()
            arrayOf(FixedHostnameTrustManager(delegate, expectedHostname))
        }
        return SSLContext.getInstance("TLS").apply { init(null, trustManagers, null) }
    }

    /**
     * Validates the chain with [delegate], but checks the certificate against a fixed hostname rather than the one
     * connected to. The delegate's socket/engine variants are deliberately not called, as those would also check
     * the connected hostname.
     */
    internal class FixedHostnameTrustManager(
        private val delegate: X509TrustManager,
        private val hostname: String,
    ) : X509ExtendedTrustManager() {
        override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) {
            delegate.checkServerTrusted(chain, authType)
            val names = certificateNames(chain[0])
            if (names.none { it.equals(hostname, ignoreCase = true) }) {
                throw CertificateException("Server certificate is for $names, expected $hostname")
            }
        }

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

    /** DNS subject alternative names, or the subject CN if there are none (as RFC 2818 prescribes). */
    fun certificateNames(cert: X509Certificate): List<String> {
        val dnsNames = cert.subjectAlternativeNames.orEmpty()
            .filter { it[0] == 2 }
            .map { it[1] as String }
        if (dnsNames.isNotEmpty()) return dnsNames
        return LdapName(cert.subjectX500Principal.name).rdns
            .filter { it.type.equals("CN", ignoreCase = true) }
            .map { it.value.toString() }
    }
}
