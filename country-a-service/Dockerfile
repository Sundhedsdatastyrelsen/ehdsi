# This docker file contains the instructions for building and then running the backend application.

# Build image
FROM maven:3.9-eclipse-temurin-21 AS builder
WORKDIR /usr/src/build
COPY ./ /usr/src/build
RUN --mount=type=cache,target=/root/.m2 mvn -B clean package


# Finished backend image
FROM eclipse-temurin:21-jre

# Attempt to fix "Using fallback font LiberationSans for base font Courier" warning
RUN apt-get update \
    && apt-get install -y \
    ttf-mscorefonts-installer \
    && rm -rf /var/lib/apt/lists/*

# refresh system font cache
RUN fc-cache -f -v

# https://github.com/hexops-graveyard/dockerfile#do-not-use-a-uid-below-10000
RUN groupadd -g 10001 app && \
    useradd -r -u 10001 -g app app

WORKDIR /app

COPY --from=builder /usr/src/build/epps-application/target/epps-application.jar .
RUN chown -R app:app .

USER app
CMD ["java", "-Duser.timezone=UTC", "-jar", "epps-application.jar"]
EXPOSE 8080
