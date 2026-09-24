package dk.sundhedsdatastyrelsen.epportal.openncp

import com.sksamuel.hoplite.Secret
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import java.security.KeyStore
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import javax.net.ssl.TrustManagerFactory
import javax.net.ssl.X509TrustManager
import kotlin.test.assertEquals

class OpenNcpTlsTest {
    private val keyStore = OpenNcp.loadKeyStore(
        OpenNcp.StoreConfig("src/test/resources/openncp/test-signing.p12", Secret("changeit")),
    )
    private val cert = keyStore.getCertificate("test") as X509Certificate

    private fun trustManager(trusted: KeyStore, hostname: String): OpenNcpTls.FixedHostnameTrustManager {
        val tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm()).apply { init(trusted) }
        return OpenNcpTls.FixedHostnameTrustManager(tmf.trustManagers.single() as X509TrustManager, hostname)
    }

    private val trustingTestCert = KeyStore.getInstance("PKCS12").apply {
        load(null, null)
        setCertificateEntry("test", cert)
    }

    @Test
    fun `uses the CN when there are no DNS subject alternative names`() {
        assertEquals(listOf("ep-portal test signing"), OpenNcpTls.certificateNames(cert))
    }

    @Test
    fun `accepts a trusted certificate issued to the expected name`() {
        assertDoesNotThrow { trustManager(trustingTestCert, "EP-Portal Test Signing").checkServerTrusted(arrayOf(cert), "RSA") }
    }

    @Test
    fun `rejects a trusted certificate issued to another name`() {
        assertThrows<CertificateException> {
            trustManager(trustingTestCert, "openncp-client").checkServerTrusted(arrayOf(cert), "RSA")
        }
    }

    @Test
    fun `rejects an untrusted certificate even with the expected name`() {
        val empty = KeyStore.getInstance("PKCS12").apply { load(null, null) }
        assertThrows<Exception> {
            trustManager(empty, "ep-portal test signing").checkServerTrusted(arrayOf(cert), "RSA")
        }
    }
}
