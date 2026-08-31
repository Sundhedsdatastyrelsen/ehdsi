package dk.sundhedsdatastyrelsen.epportal.config

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.module.kotlin.convertValue
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import dk.sundhedsdatastyrelsen.epportal.logger
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpResponse
import javax.net.ssl.SSLContext

class VaultClient(val url: String, val username: String, val password: String, val sslContext: SSLContext? = null) {
    private val objectMapper = jacksonObjectMapper().also {
        it.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
    }
    
    private fun createHttpClient(): HttpClient {
        val builder = HttpClient.newBuilder()
        sslContext?.let { builder.sslContext(it) }
        return builder.build()
    }
    
    private val authToken: String by lazy {
        val client = createHttpClient()
        val req = java.net.http.HttpRequest
            .newBuilder()
            .uri(URI.create("${url}/v1/auth/userpass/login/${username}"))
            .header("Content-Type", "application/json")
            .POST(java.net.http.HttpRequest.BodyPublishers.ofString("{\"password\": \"${password}\"}"))
            .build()
        log.info("Logging in to Vault at {}", url)
        val loginResult = client.send(req, HttpResponse.BodyHandlers.ofString())
        check(loginResult.statusCode() == 200) {
            throw IllegalStateException("Got bad response from Vault at ${url}: status: ${loginResult.statusCode()}, body: ${loginResult.body()}")
        }
        objectMapper.readTree(loginResult.body())["auth"]["client_token"].asText()
    }

    fun fetchSecrets(path: String): Map<String, String>? {
        val client = createHttpClient()
        val req = java.net.http.HttpRequest
            .newBuilder()
            .uri(URI.create("${url}/v1/${path}"))
            .header("Content-Type", "application/json")
            .header("X-Vault-Token", authToken)
            .GET()
            .build()
        log.info("Fetching vault secrets at path {}", path)
        val secretResult = client.send(req, HttpResponse.BodyHandlers.ofString())
        return if (secretResult.statusCode() < 300 && secretResult.statusCode() >= 200) { //If it's a success statuscode
            val dataNode = objectMapper.readTree(secretResult.body())["data"]["data"]
            objectMapper.convertValue<Map<String, String>>(dataNode)
        } else if (secretResult.statusCode() == 404) {
            null
        } else {
            throw VaultException("Secret fetching for $path failed")
        }
    }

    val log = logger()
}

class VaultException(message: String) : Exception(message)
