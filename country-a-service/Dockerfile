# This docker file contains the instructions for building and then running the backend application.

# Build image
FROM maven:3.9-eclipse-temurin-21 AS builder
WORKDIR /usr/src/build
COPY ./ /usr/src/build
RUN --mount=type=cache,target=/root/.m2 mvn -B clean package


# Finished backend image
FROM eclipse-temurin:21-jre

# Fix "Using fallback font LiberationSans for base font Courier" warning
RUN apt-get update \
    && apt-get install -y \
    ttf-mscorefonts-installer \
    curl \
    && rm -rf /var/lib/apt/lists/*

# refresh system font cache
RUN fc-cache -f -v

WORKDIR /app

# Download OpenTelemetry Java agent and Pyroscope extension
RUN curl -L https://github.com/open-telemetry/opentelemetry-java-instrumentation/releases/download/v2.17.0/opentelemetry-javaagent.jar -o /app/opentelemetry-javaagent.jar
RUN curl -L https://github.com/grafana/otel-profiling-java/releases/download/v1.0.4/pyroscope-otel.jar -o /app/pyroscope-otel.jar

# https://github.com/hexops-graveyard/dockerfile#do-not-use-a-uid-below-10000
RUN groupadd -g 10001 app && \
    useradd -r -u 10001 -g app app

COPY --from=builder /usr/src/build/epps-application/target/epps-application.jar .
RUN chown -R app:app .

USER app
CMD ["java", "-javaagent:/app/opentelemetry-javaagent.jar", "-Duser.timezone=UTC", "-jar", "epps-application.jar"]
EXPOSE 8080

# Configure Pyroscope profiling
ENV PYROSCOPE_FORMAT=jfr
ENV PYROSCOPE_PROFILING_INTERVAL=10ms
ENV PYROSCOPE_PROFILER_EVENT=itimer
ENV PYROSCOPE_PROFILER_LOCK=10ms
ENV PYROSCOPE_PROFILER_ALLOC=512k
ENV PYROSCOPE_UPLOAD_INTERVAL=15s
ENV OTEL_JAVAAGENT_EXTENSIONS=/app/pyroscope-otel.jar
ENV OTEL_PYROSCOPE_ADD_PROFILE_URL=false
ENV OTEL_PYROSCOPE_ADD_PROFILE_BASELINE_URL=false
ENV OTEL_PYROSCOPE_START_PROFILING=true
