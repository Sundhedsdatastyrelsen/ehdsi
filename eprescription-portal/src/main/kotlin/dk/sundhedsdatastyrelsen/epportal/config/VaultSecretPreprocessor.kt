package dk.sundhedsdatastyrelsen.epportal.config

import com.sksamuel.hoplite.*
import com.sksamuel.hoplite.fp.invalid
import com.sksamuel.hoplite.fp.valid
import com.sksamuel.hoplite.preprocessor.TraversingPrimitivePreprocessor
import dk.sundhedsdatastyrelsen.epportal.logger
import java.io.FileInputStream
import java.nio.file.Paths
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManagerFactory

class SecretException(message: String) : RuntimeException(message)

class VaultSecretPreprocessor : TraversingPrimitivePreprocessor() {
    private val logger = logger()
    private val vaultUsername: String? = env("VAULT_USER")
    private val vaultPassword: String? = env("VAULT_PASSWORD")
    private val vaultUrl: String? = env("VAULT_URL")
    private val vaultRegex = "vault:/(.+?)".toRegex()
    private val pathKeyRegex = "(.+):(.+)".toRegex()

    private val sslContext: SSLContext? = createSslContext()

    private fun env(envVar: String): String? {
        val e = System.getenv(envVar)
        if (e == "" || e == null) {
            logger.warn("$envVar was not set.")
        }
        return e
    }

    private fun envOrProp(envVar: String, propName: String): String? =
        System.getenv(envVar) ?: System.getProperty(propName)

    private fun createSslContext(): SSLContext? {
        val truststorePath = envOrProp("VAULT_TRUSTSTORE_PATH", "vault.truststore.path")
        val truststorePassword = envOrProp("VAULT_TRUSTSTORE_PASSWORD", "vault.truststore.password")

        if (truststorePath == null) {
            // No truststore configured, use default JVM truststore
            return null
        }

        if (truststorePassword == null) {
            throw IllegalStateException("VAULT_TRUSTSTORE_PATH is set but VAULT_TRUSTSTORE_PASSWORD is not set")
        }

        val truststoreFile = Paths.get(truststorePath).toFile()
        if (!truststoreFile.exists()) {
            throw IllegalStateException("Vault truststore file not found: $truststorePath")
        }

        val truststore = java.security.KeyStore.getInstance("PKCS12")
        FileInputStream(truststoreFile).use { fis ->
            truststore.load(fis, truststorePassword.toCharArray())
        }

        val trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm())
        trustManagerFactory.init(truststore)

        val sslContext = SSLContext.getInstance("TLS")
        sslContext.init(null, trustManagerFactory.trustManagers, null)

        return sslContext
    }

    private val vaultClient = if (vaultUrl != null && vaultUsername != null && vaultPassword != null) VaultClient(
        vaultUrl,
        vaultUsername,
        vaultPassword,
        sslContext
    ) else null

    // path -> key -> value
    private val secretsCache: MutableMap<String, Map<String, String>> = mutableMapOf()

    private fun fetchSecretsFromVault(path: String): Map<String, String> {
        if (secretsCache.containsKey(path)) {
            return secretsCache.getValue(path)
        }

        if(vaultClient == null) {
            return emptyMap()
        }

        val secrets = vaultClient.fetchSecrets(path)
        if (secrets != null) {
            secretsCache[path] = secrets
            return secrets
        } else {
            throw SecretException("Secrets not found on path")
        }
    }

    private fun getSecret(node: StringNode, path: String, key: String): ConfigResult<Node> {
        try {
            val secrets = fetchSecretsFromVault(path)

            val secretValue = secrets[key]

            if (secretValue != null) {
                return node.copy(value = secretValue)
                    .withMeta(CommonMetadata.Secret, true)
                    .withMeta(CommonMetadata.UnprocessedValue, node.value)
                    .withMeta(CommonMetadata.RemoteLookup, "Vault '$path' '$key'")
                    .valid()
            }
        } catch (e: SecretException) {
            return ConfigFailure.PreprocessorFailure("Problem getting '$key' in path '$path' in Vault", e).invalid()
        }
        return ConfigFailure.PreprocessorFailure("Secret '$key' not found in Vault path '$path'", RuntimeException())
            .invalid()
    }


    override fun handle(
        node: PrimitiveNode,
        context: DecoderContext
    ): ConfigResult<Node> {
        return when (node) {
            is StringNode -> {
                when (val match = vaultRegex.matchEntire(node.value)) {
                    null -> node.valid()
                    else -> {
                        when (val match2 = pathKeyRegex.matchEntire(match.groupValues[1])) {
                            null -> ConfigFailure.PreprocessorWarning("Must specify vault key at '${match.groupValues[0]}'")
                                .invalid()

                            else -> {
                                getSecret(node, match2.groupValues[1], match2.groupValues[2])
                            }
                        }
                    }
                }
            }

            else -> node.valid()
        }
    }
}
