package dk.sundhedsdatastyrelsen.epportal.openncp

import com.sksamuel.hoplite.Secret
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import java.security.KeyStore
import java.security.cert.X509Certificate
import javax.net.ssl.SSLEngine
import javax.net.ssl.TrustManagerFactory
import javax.net.ssl.X509TrustManager

class OpenNcpTlsTest {
    private val keyStore = OpenNcp.loadKeyStore(
        OpenNcp.StoreConfig("src/test/resources/openncp/test-signing.p12", Secret("changeit")),
    )
    private val cert = keyStore.getCertificate("test") as X509Certificate

    private fun trustManager(trusted: KeyStore): OpenNcpTls.NoHostnameVerificationTrustManager {
        val tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm()).apply { init(trusted) }
        return OpenNcpTls.NoHostnameVerificationTrustManager(tmf.trustManagers.single() as X509TrustManager)
    }

    private val trustingTestCert = KeyStore.getInstance("PKCS12").apply {
        load(null, null)
        setCertificateEntry("test", cert)
    }

    /** An engine whose SSLParameters require HTTPS hostname verification, as java.net.http.HttpClient sets up. */
    private fun engineFor(host: String): SSLEngine =
        OpenNcpTls.sslContext(trustingTestCert, dangerouslyDisableHostnameVerification = true)
            .createSSLEngine(host, 443)
            .apply { sslParameters = sslParameters.apply { endpointIdentificationAlgorithm = "HTTPS" } }

    @Test
    fun `accepts a trusted certificate issued to another host`() {
        assertDoesNotThrow {
            trustManager(trustingTestCert).checkServerTrusted(arrayOf(cert), "RSA", engineFor("localhost"))
        }
    }

    @Test
    fun `rejects an untrusted certificate`() {
        val empty = KeyStore.getInstance("PKCS12").apply { load(null, null) }
        assertThrows<Exception> {
            trustManager(empty).checkServerTrusted(arrayOf(cert), "RSA", engineFor("localhost"))
        }
    }
}
