FROM maven:3.9-eclipse-temurin-11 AS builder

ARG OPENNCP_BRANCH="8.2.1"

WORKDIR /usr/src/app

RUN git clone --branch ${OPENNCP_BRANCH} --depth 1 https://code.europa.eu/ehdsi/ehealth.git && \
    cd ehealth

WORKDIR /usr/src/app/ehealth

# Use sed to replace the old URL with the new URL
RUN sed -i 's|https://gazelle.ihe.net/nexus/content/repositories/public/|https://nexus.ihe-catalyst.net/repository/maven-public/|g' pom.xml

# Replace deprecated groupid for oracle driver with the current one to avoid dependency resolution errors
RUN sed -i 's|<groupId>com.oracle</groupId>|<groupId>com.oracle.database.jdbc</groupId>|g' pom.xml \
    openncp-tools/openncp-configuration-utility/pom.xml \
    openncp-tools/openncp-tsam-exporter/pom.xml \
    openncp-tools/openncp-tsam-sync/pom.xml

RUN --mount=type=cache,target=/root/.m2 mvn -DskipTests install

WORKDIR /usr/src/app

COPY openncp-national-connector openncp-national-connector
COPY ncp_a/pom.xml openncp-server/pom.xml

RUN --mount=type=cache,target=/root/.m2 \
    cd openncp-national-connector && \
    mvn install && \
    cd ../openncp-server && \
    mvn install

# Base Tomcat image for most services
FROM tomcat:9-jdk11-temurin AS tomcat-base

RUN apt-get update \
    && apt-get install -y \
    gettext \
    curl \
    && rm -rf /var/lib/apt/lists/*

# https://github.com/hexops-graveyard/dockerfile#do-not-use-a-uid-below-10000
RUN groupadd -g 10001 tomcat && \
    useradd -r -u 10001 -g tomcat tomcat

# Add our openncp configuration.
COPY ./openncp-configuration/tm.properties /opt/openncp-configuration/tm.properties
COPY ./openncp-configuration/tsam.properties /opt/openncp-configuration/tsam.properties
# Copy the files we use directly from ehealth.
COPY --from=builder /usr/src/app/ehealth/openncp-docker/openncp-configuration/audit-backup /opt/openncp-configuration/audit-backup
COPY --from=builder /usr/src/app/ehealth/openncp-docker/openncp-configuration/audits /opt/openncp-configuration/audits
COPY --from=builder /usr/src/app/ehealth/openncp-docker/openncp-configuration/EADC_resources /opt/openncp-configuration/EADC_resources
COPY --from=builder /usr/src/app/ehealth/openncp-docker/openncp-configuration/forms /opt/openncp-configuration/forms
COPY --from=builder /usr/src/app/ehealth/openncp-docker/openncp-configuration/integration /opt/openncp-configuration/integration
COPY --from=builder /usr/src/app/ehealth/openncp-docker/openncp-configuration/TM_resources /opt/openncp-configuration/TM_resources
COPY --from=builder /usr/src/app/ehealth/openncp-docker/openncp-configuration/validation/gazelle.ehdsi.properties /opt/openncp-configuration/gazelle.ehdsi.template.properties

# We save our atna configuration in another folder.
COPY ./atna-resources/ /opt/atna-resources
# Copy the files we use directly from ehealth.
COPY --from=builder /usr/src/app/ehealth/openncp-docker/openncp-configuration/ATNA_resources/atnacodes.xml /opt/atna-resources/atnacodes.xml
COPY --from=builder /usr/src/app/ehealth/openncp-docker/openncp-configuration/ATNA_resources/entities.xml /opt/atna-resources/entities.xml
COPY --from=builder /usr/src/app/ehealth/openncp-docker/openncp-configuration/ATNA_resources/IheActors.xml /opt/atna-resources/IheActors.xml

# Download OpenTelemetry Java agent
RUN curl -L https://github.com/open-telemetry/opentelemetry-java-instrumentation/releases/download/v2.17.0/opentelemetry-javaagent.jar -o /opt/opentelemetry-javaagent.jar

# Make it possible to use environment variables in server.xml:
RUN echo \
    "org.apache.tomcat.util.digester.PROPERTY_SOURCE=org.apache.tomcat.util.digester.EnvironmentPropertySource" \
    >> conf/catalina.properties

RUN chown -R tomcat:tomcat . /opt/openncp-configuration /opt/atna-resources /opt/opentelemetry-javaagent.jar

# This env var needs to be present at runtime for the openncp containers to function
ENV EPSOS_PROPS_PATH=/opt/openncp-configuration/

# TSAM Exporter
FROM eclipse-temurin:11-jre-jammy AS tsam-exporter

WORKDIR /usr/local/openncp-tsam-exporter
COPY --from=builder /usr/src/app/ehealth/openncp-docker/target/utilities/openncp-tsamexporter.jar .

COPY ./openncp-tsam-exporter/entrypoint.sh .
COPY ./openncp-tsam-exporter/application.yml .

ENTRYPOINT ["./entrypoint.sh"]
CMD ["java", "-jar", "openncp-tsamexporter.jar"]

# Node A
FROM tomcat-base AS node-a

COPY --from=builder \
    /usr/src/app/openncp-server/target/openncp-server-*.war \
    webapps/openncp-ws-server.war

COPY --from=builder \
    /usr/src/app/ehealth/openncp-docker/target/classes/lib \
    lib/

COPY --from=builder \
    /usr/src/app/ehealth/openncp-docker/ncp_a/config/context.xml \
    conf/

COPY ./ncp_a/config/server.xml conf/
COPY ./ncp_a/entrypoint.sh bin/

RUN chown -R tomcat:tomcat .

# Take the logging down a notch by default
ENV LOGGING_LEVEL_ROOT=INFO

USER tomcat
ENTRYPOINT ["entrypoint.sh"]
CMD ["catalina.sh", "run"]

# Node B
FROM tomcat-base AS node-b

COPY --from=builder /usr/src/app/ehealth/openncp-docker/target/classes/webapp/openncp-client-connector.war /usr/local/tomcat/webapps/
COPY --from=builder /usr/src/app/ehealth/openncp-docker/target/classes/lib /usr/local/tomcat/lib/
COPY --from=builder \
    /usr/src/app/ehealth/openncp-docker/ncp_b/config/context.xml \
    conf/

COPY ./ncp_b/config/server.xml conf/
COPY ./ncp_b/entrypoint.sh bin/

RUN chown -R tomcat:tomcat .

USER tomcat
ENTRYPOINT ["entrypoint.sh"]
CMD ["catalina.sh", "run"]

# TRC-STS
FROM tomcat-base AS trc-sts

COPY --from=builder /usr/src/app/ehealth/openncp-docker/target/classes/webapp/TRC-STS.war webapps/
COPY --from=builder /usr/src/app/ehealth/openncp-docker/target/classes/lib lib/

COPY ./openncp-trc-sts/config conf/
COPY ./openncp-trc-sts/entrypoint.sh bin/

RUN chown -R tomcat:tomcat .

USER tomcat
ENTRYPOINT ["entrypoint.sh"]
CMD ["catalina.sh", "run"]

# OpenATNA
FROM tomcat-base AS openatna

COPY --from=builder /usr/src/app/ehealth/openncp-docker/target/classes/webapp/openatna-web.war webapps/
COPY --from=builder /usr/src/app/ehealth/openncp-docker/target/classes/lib lib/
COPY --from=builder \
    /usr/src/app/ehealth/openncp-docker/openncp-openatna/config/context.xml \
    conf/

COPY ./openncp-openatna/config/server.xml conf/
COPY ./openncp-openatna/entrypoint.sh bin/

RUN chown -R tomcat:tomcat .

USER tomcat
ENTRYPOINT ["entrypoint.sh"]
CMD ["catalina.sh", "run"]

# Translations and Mappings
FROM tomcat-base AS translations-and-mappings

COPY --from=builder /usr/src/app/ehealth/openncp-docker/target/classes/webapp/translations-and-mappings-ws.war webapps/
COPY --from=builder /usr/src/app/ehealth/openncp-docker/target/classes/lib lib/

COPY ./openncp-translations-and-mappings/config conf/
COPY ./openncp-translations-and-mappings/entrypoint.sh bin/

RUN chown -R tomcat:tomcat .

USER tomcat
ENTRYPOINT ["entrypoint.sh"]
CMD ["catalina.sh", "run"]

# TSAM Sync
FROM eclipse-temurin:11-jre-jammy AS tsam-synchronizer

RUN apt-get update \
    && apt-get install -y \
    default-mysql-client \
    && rm -rf /var/lib/apt/lists/*

WORKDIR /usr/local/openncp-tsam-sync
COPY --from=builder /usr/src/app/ehealth/openncp-docker/target/utilities/openncp-tsam-sync.jar .
COPY ./tsam-synchronizer/application.yml .
COPY ./tsam-synchronizer/entrypoint.sh /usr/local/bin/

ENTRYPOINT ["entrypoint.sh"]
CMD ["java", "-jar", "openncp-tsam-sync.jar"]

# Web Manager Frontend
FROM node:24-alpine AS web-manager-frontend-builder

WORKDIR /app

COPY --from=builder /usr/src/app/ehealth/openncp-web-manager/openncp-web-manager-frontend/package*.json ./

# There is no package-lock.json so we cannot use npm ci.
# vue-ctk-date-time-picker@2.6.0 is broken so we need to downgrade explicitly
RUN npm install && npm install vue-ctk-date-time-picker@2.5.0

COPY --from=builder /usr/src/app/ehealth/openncp-web-manager/openncp-web-manager-frontend .

ARG VUE_APP_SERVER_URL="/openncp-gateway-backend"
RUN npm run build

# Web Manager
FROM tomcat-base AS web-manager

COPY --from=builder /usr/src/app/ehealth/openncp-docker/target/classes/webapp/openncp-gateway-backend.war webapps/
COPY --from=builder /usr/src/app/ehealth/openncp-docker/target/classes/lib lib/
COPY --from=web-manager-frontend-builder /app/dist webapps/openncp-web-manager

COPY openncp-web-manager/config conf/
COPY openncp-web-manager/application-docker.yml /opt/config/
COPY openncp-web-manager/entrypoint.sh bin/

RUN chown -R tomcat:tomcat .

USER tomcat
ENTRYPOINT ["entrypoint.sh"]
CMD ["catalina.sh", "run"]
