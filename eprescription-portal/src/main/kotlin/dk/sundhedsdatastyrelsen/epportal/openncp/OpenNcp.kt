package dk.sundhedsdatastyrelsen.epportal.openncp

import com.sksamuel.hoplite.Secret
import java.io.File
import java.net.URI
import java.security.KeyStore
import java.time.Duration

/**
 * Configuration of the integration with the OpenNCP client connector (the `openncp-client` service in NCP/).
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
        /**
         * The name the server certificate must be issued to, when it differs from the endpoint's host (e.g. when
         * connecting to localhost or a docker service name). Defaults to the endpoint's host.
         */
        val tlsHostname: String? = null,
        /** The key that signs the HCP assertion. Its certificate must be trusted by the client connector. */
        val signing: SigningConfig,
        val hcp: HcpConfig,
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
        val issuer: String = "urn:idp:DK:countryB",
        val nameId: String,
        /** XSPA subject-id: the HCP's name. */
        val subjectId: String,
        val roleCode: String = "2262",
        val roleDisplayName: String = "Pharmacists",
        val organization: String,
        val organizationId: String,
        /** One of Hospital, Resident Physician, Pharmacy, Other. */
        val facilityType: String = "Pharmacy",
        val locality: String,
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

    /** Loads a PKCS12 or JKS keystore (the type is detected from the file). */
    fun loadKeyStore(config: StoreConfig): KeyStore =
        KeyStore.getInstance(File(config.path), config.password.value.toCharArray())
}

class OpenNcpException(message: String, cause: Throwable? = null) : RuntimeException(message, cause)
