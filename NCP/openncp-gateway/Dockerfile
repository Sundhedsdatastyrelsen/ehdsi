FROM node:16-buster as builder

ARG openncp_version
ARG VUE_APP_SERVER_URL="/openncp-gateway-backend"
WORKDIR /usr/src/app

# Download ehealth repo and extract only openncp-gateway-frontend.
RUN curl --fail --silent --show-error --location --output ehealth.tar.gz \
    https://code.europa.eu/ehdsi/ehealth/-/archive/${openncp_version}/ehealth-${openncp_version}.tar.gz && \
    tar --strip-components 3 -xvzf ehealth.tar.gz \
    ehealth-${openncp_version}/openncp-gateway/openncp-gateway-frontend && \
    rm ehealth.tar.gz

RUN npm install --legacy-peer-deps && npm run build

FROM tomcat:9.0-jdk11-temurin

ARG openncp_version
ARG openncp_repo="https://code.europa.eu/api/v4/projects/347/packages/maven/"

WORKDIR /usr/local/tomcat

COPY ./common/bin/download_maven_artifact /usr/local/bin/download_maven_artifact

# Download JAR dependencies
RUN download_maven_artifact com.zaxxer HikariCP 4.0.3 && \
    download_maven_artifact org.slf4j slf4j-api 2.0.9 && \
    download_maven_artifact org.slf4j slf4j-jdk14 2.0.9 && \
    download_maven_artifact org.mariadb.jdbc mariadb-java-client 3.2.0 && \
    download_maven_artifact org.hibernate hibernate-core 5.6.15.Final && \
    download_maven_artifact org.hibernate.common hibernate-commons-annotations 6.0.6.Final

COPY ./openncp-gateway/server.xml conf/server.xml
COPY ./openncp-gateway/context.xml conf/context.xml
COPY ./openncp-gateway/setenv.sh bin/setenv.sh

# Download EHDSI artifacts: TRC-STS, OpenATNA, Gateway-backend.
RUN download_maven_artifact \
    eu.europa.ec.sante.ehdsi \
    openncp-trc-sts \
    ${openncp_version} \
    war \
    ${openncp_repo} \
    webapps/TRC-STS.war && \
    download_maven_artifact \
    eu.europa.ec.sante.ehdsi.openncp-openatna \
    openatna-web \
    ${openncp_version} \
    war \
    ${openncp_repo} \
    webapps/openatna-web.war && \
    download_maven_artifact \
    eu.europa.ec.sante.ehdsi \
    openncp-gateway-backend \
    ${openncp_version} \
    war \
    ${openncp_repo} \
    webapps/openncp-gateway-backend.war

# Install Gateway-frontend from builder image
COPY --from=builder /usr/src/app/dist webapps/openncp-gateway/

ENV EPSOS_PROPS_PATH=/opt/openncp-configuration/
ENV LOGGING_LEVEL_ROOT=INFO

EXPOSE 8082
EXPOSE 8443
EXPOSE 2862
EXPOSE 2861
