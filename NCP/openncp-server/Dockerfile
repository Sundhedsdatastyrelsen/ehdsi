FROM maven:3-eclipse-temurin-11 as nc-builder

WORKDIR /usr/src/app
COPY openncp-national-connector .
RUN --mount=type=cache,target=/root/.m2 mvn package

FROM tomcat:9.0-jdk11-temurin

ARG openncp_version
ARG openncp_repo="https://code.europa.eu/api/v4/projects/347/packages/maven/"

COPY ./common/bin/download_maven_artifact /usr/local/bin/download_maven_artifact

# Download main artifact
RUN download_maven_artifact \
    eu.europa.ec.sante.ehdsi.openncp-protocol-terminators.openncp-ncp-server \
    openncp-ws-server \
    ${openncp_version} \
    war \
    ${openncp_repo} \
    webapps/openncp-ws-server.war

# Unpack the war to allow us to patch the internal configuration files
RUN mkdir -p webapps/openncp-ws-server && \
    cd webapps/openncp-ws-server && \
    jar -xf ../openncp-ws-server.war && \
    rm ../openncp-ws-server.war

# Patch the Axis2 configuration with the correct port number:
RUN sed -i \
    's/<parameter name="port">8443<\/parameter>/<parameter name="port">6443<\/parameter>/g' \
    webapps/openncp-ws-server/WEB-INF/conf/axis2.xml

# Download JAR dependencies
RUN cd webapps/openncp-ws-server/WEB-INF && \
    download_maven_artifact org.slf4j slf4j-jdk14 1.7.36 && \
    download_maven_artifact org.openapitools jackson-databind-nullable 0.2.6 && \
    download_maven_artifact org.mariadb.jdbc mariadb-java-client 3.2.0

# # Download mock services implementations
# RUN download_maven_artifact \
#     eu.europa.ec.sante.ehdsi.openncp-protocol-terminators.openncp-ncp-server \
#     openncp-nc-mock-it \
#     ${openncp_version} \
#     jar \
#     ${openncp_repo} \
#     webapps/openncp-ws-server/WEB-INF/lib/openncp-nc-mock-it.jar

# Install our own national connector implementation
COPY --from=nc-builder /usr/src/app/target/openncp-national-connector-${openncp_version}.jar \
    webapps/openncp-ws-server/WEB-INF/lib/openncp-national-connector.jar

COPY ./openncp-server/server.xml conf/server.xml
COPY ./openncp-server/context.xml conf/context.xml
COPY ./openncp-server/setenv.sh bin/setenv.sh

ENV EPSOS_PROPS_PATH=/opt/openncp-configuration/
ENV LOGGING_LEVEL_ROOT=INFO

EXPOSE 8080
EXPOSE 6443
