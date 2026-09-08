FROM eclipse-temurin:25-jdk AS builder
# Pre-download Gradle by executing the wrapper script
COPY gradlew /build/gradlew
COPY gradle/wrapper/ /build/gradle/wrapper/
WORKDIR /build
RUN ./gradlew --no-daemon --version
# Bring over the local Maven repo for Gradle's mavenLocal()
COPY gradle /build/gradle
COPY build.gradle.kts build.gradle.kts
# Download deps
RUN ./gradlew --no-daemon dependencies
COPY settings.gradle.kts settings.gradle.kts
COPY src src
# Test and build
RUN ./gradlew --no-daemon build installDist -x test

FROM eclipse-temurin:25-jre
# Install curl for health check
RUN apt-get update && apt-get install -y curl && rm -rf /var/lib/apt/lists/ \
    && useradd -m -u 10001 app
USER app
WORKDIR /home/app

# Download OpenTelemetry Java agent
ADD --chown=app:app https://github.com/open-telemetry/opentelemetry-java-instrumentation/releases/download/v2.17.0/opentelemetry-javaagent.jar ./opentelemetry-javaagent.jar

COPY --from=builder /build/build/install/ep-portal .
EXPOSE 8080
HEALTHCHECK --interval=30s --timeout=3s --start-period=30s --retries=3 \
    CMD curl -f http://localhost:8080/saml/metadata | grep -q "<?xml" || exit 1
ENV JAVA_OPTS=""
CMD ["./bin/ep-portal"]
