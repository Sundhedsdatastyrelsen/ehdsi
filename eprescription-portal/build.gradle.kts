plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.plugin.serialization)
    application
}

group = "dk.sundhedsdatastyrelsen"
version = "0.0.1"

kotlin {
    // Match the JDK used by the Docker build/runtime (eclipse-temurin-25). Pinning the toolchain keeps
    // compileJava and compileKotlin on the same JVM target regardless of the local JDK.
    jvmToolchain(25)
}

repositories {
    mavenLocal()
    mavenCentral()
    maven {
        name = "Shibboleth Releases Repository"
        url = uri("https://build.shibboleth.net/maven/releases/")
    }
}

dependencies {
    // App configuration
    implementation(libs.hoplite.core)
    implementation(libs.hoplite.toml)

    // Web app
    implementation(libs.javalin)
    implementation(libs.javalin.rendering)
    implementation(libs.javalin.ssl.plugin)
    implementation(libs.freemarker)
    implementation(libs.jackson.module.kotlin)
    implementation(libs.jackson.datatype.jsr310)

    // Admin UI OIDC integration. Nimbus directly (no javalin-pac4j wrapper — it does not publish a Javalin-6
    // build; see the note at the top of OidcAdminAuth.kt).
    implementation(libs.nimbus.oauth2.oidc.sdk)

    // Logging
    implementation(libs.slf4j.api)
    implementation(libs.jcl.over.slf4j)
    implementation(libs.logback.classic)

    // Database
    implementation(libs.sqlite.jdbc)
    implementation(libs.jdbi3.core)
    implementation(libs.flyway.core)

    // Support remote JVM debugging (maybe?)
    implementation(libs.kotlin.stdlib)

    // Avoid CVEs:
    implementation(libs.bouncycastle.bcprov.jdk18on)

    // Test dependencies
    testImplementation(kotlin("test"))
    testImplementation(libs.assertk)
    testImplementation(libs.mockito.core)
    testImplementation(libs.mockito.kotlin)
    testImplementation(libs.okhttp)
    testImplementation(libs.kotest.property)
}

application {
    applicationDefaultJvmArgs = listOf("-Dlogback.configurationFile=config/logback.xml")
    mainClass = "dk.sundhedsdatastyrelsen.epportal.ApplicationKt"
}

tasks {
    test {
        // This fixes an issue where the integration tests fail when building via docker compose build
        jvmArgs("-Djava.net.preferIPv4Stack=true")
        useJUnitPlatform()
    }

    jar {
        manifest {
            attributes("Main-Class" to application.mainClass)
        }
    }
}
