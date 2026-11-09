package dk.sundhedsdatastyrelsen.epportal.openncp

import com.sksamuel.hoplite.Secret
import java.io.File
import java.net.URI
import java.security.KeyStore
import java.security.PrivateKey
import java.security.cert.X509Certificate
import java.time.Duration
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManagerFactory

/**
 * Configuration of the integration with the OpenNCP client connector (the `openncp-client` service in NCP/).
 *
 * Bibliography:
 *
 * - [1] eHDSI SAML Profile v10: https://webgate.ec.europa.eu/fpfis/wikis/x/AeTzN
 */
object OpenNcp {
    data class Config(
        /**
         * The ClientService SOAP endpoint, e.g. https://openncp-client:6443/openncp-client-connector/services/ClientService.
         * Must be https: the connector's WS-Policy (HttpsToken) rejects requests over http.
         */
        val endpoint: URI,
        val timeout: Duration = Duration.ofSeconds(60),
        /** Only needed for https endpoints whose certificate isn't trusted by the default JVM truststore. */
        val truststore: StoreConfig? = null,
        /** The key that signs the HCP assertion. Its certificate must be trusted by the client connector. */
        val signing: SigningConfig,
        val hcp: HcpConfig = HcpConfig(),
        /**
         * The issuer to write in TRC and HCP assertions. From the eHDSI SAML profile [1]:
         *
         * > address URI that identifies the endpoint of the issuing service (i.e. Uri of NCP-B)
         */
        val issuer: String,
        val trcAssertionDuration: Duration = Duration.ofHours(1),
    )

    data class StoreConfig(
        val path: String,
        val password: Secret,
    )

    data class SigningConfig(
        val keystore: StoreConfig,
        val alias: String,
        /** Defaults to the keystore password. */
        val keyPassword: Secret? = null,
    )

    /**
     * The identity of the health care professional, put in the HCP assertion.
     *
     * TODO: First iteration only. The identity is hard-coded in config instead of coming from the logged-in user.
     */
    data class HcpConfig(
        val nameId: String = "testpharmacist",
        /** XSPA subject-id: the HCP's name. */
        val subjectId: String = "Test Farmaceut",
        val roleCode: String = "2262",
        val roleDisplayName: String = "Pharmacists",
        val organization: String = "Testapoteket",
        val organizationId: String = "urn:oid:1.2.208.176.1.1",
        /** One of Hospital, Resident Physician, Pharmacy, Other. */
        val facilityType: String = "Pharmacy",
        val locality: String = "København",
        val homeCommunityId: String = "1.2.208",
        /** TREATMENT or EMERGENCY. */
        val purposeOfUse: String = "TREATMENT",
        val authnContextClassRef: String = "urn:oasis:names:tc:SAML:2.0:ac:classes:MobileTwoFactorContract",
        /** XSPA HL7 permission codes, e.g. PRD-003, without the urn prefix. */
        val permissions: List<String> = listOf(
            "PRD-003", "PRD-004", "PRD-005", "PRD-006", "PRD-010", "PRD-016", "PPD-032", "PPD-033", "PPD-046",
        ),
        /** Lifetime of the assertion. The connector accepts at most 4 hours. */
        val validity: Duration = Duration.ofHours(1),
    )

    /** Loads a PKCS12 or JKS keystore. */
    fun loadKeyStore(config: StoreConfig): KeyStore =
        KeyStore.getInstance(File(config.path), config.password.value.toCharArray())

    data class SigningKey(val key: PrivateKey, val cert: X509Certificate)

    fun loadSigningKey(config: SigningConfig): SigningKey {
        val keyStore = loadKeyStore(config.keystore)
        val keyPassword = (config.keyPassword ?: config.keystore.password).value.toCharArray()
        val key = keyStore.getKey(config.alias, keyPassword) as? PrivateKey
            ?: throw IllegalStateException("No private key with alias '${config.alias}' in ${config.keystore.path}")
        val cert = keyStore.getCertificate(config.alias) as X509Certificate
        return SigningKey(key, cert)
    }

    /** A TLS context that trusts only the certificates in [truststore]. */
    fun sslContext(truststore: StoreConfig): SSLContext {
        val tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm())
        tmf.init(loadKeyStore(truststore))
        return SSLContext.getInstance("TLSv1.3").apply { init(null, tmf.trustManagers, null) }
    }

    /** Purpose of use is used both in TRC and HCP assertions It comes from the eHDSI SAML profile [1]. */
    const val PURPOSE_OF_USE_CODE_SYSTEM = "3bc18518-d305-46c2-a8d6-94bd59856e9e"
    const val PURPOSE_OF_USE_CODE_SYSTEM_NAME = "eHDSI PurposeOfUse"
}
