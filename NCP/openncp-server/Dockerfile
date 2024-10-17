FROM maven:3.9-eclipse-temurin-11 as builder

ARG OPENNCP_BRANCH="releases/W8"
# ARG OPENNCP_BRANCH="8.0.0"

WORKDIR /usr/src/app

RUN --mount=type=cache,target=/root/.m2 \
    git clone --branch ${OPENNCP_BRANCH} --depth 1 https://code.europa.eu/ehdsi/ehealth.git && \
    cd ehealth && \
    mvn -DskipTests install

COPY openncp-national-connector openncp-national-connector
COPY openncp-server/pom.xml openncp-server/pom.xml

RUN --mount=type=cache,target=/root/.m2 \
    cd openncp-national-connector && \
    mvn install && \
    cd ../openncp-server && \
    mvn install

FROM tomcat:9-jdk11-temurin

RUN apt-get update && \
    apt-get install -y \
    gettext \
    socat \
    && rm -rf /var/lib/apt/lists/*

COPY --from=builder \
    /usr/src/app/openncp-server/target/openncp-server-*.war \
    webapps/openncp-ws-server.war

COPY --from=builder \
    /usr/src/app/ehealth/openncp-docker/target/classes/lib \
    lib/

COPY ./openncp-server/context.xml conf/
COPY ./openncp-server/server.xml conf/
COPY ./openncp-server/setenv.sh bin/setenv.sh

ENV EPSOS_PROPS_PATH=/opt/openncp-configuration/

COPY ./openncp-configuration/ $EPSOS_PROPS_PATH

# Make it possible to use environment variables in server.xml:
RUN echo \
    "org.apache.tomcat.util.digester.PROPERTY_SOURCE=org.apache.tomcat.util.digester.EnvironmentPropertySource" \
    >> conf/catalina.properties

RUN groupadd -g 10001 tomcat && \
  useradd -r -u 10001 -g tomcat tomcat && \
  chown -R tomcat:tomcat . $EPSOS_PROPS_PATH

USER tomcat

ENV LOGGING_LEVEL_ROOT=INFO




















# FROM tomcat:9-jdk11-temurin

# ARG openncp_version
# ARG openncp_repo="https://code.europa.eu/api/v4/projects/347/packages/maven/"

# COPY ./common/bin/download_maven_artifact /usr/local/bin/download_maven_artifact

# # Download main artifact
# RUN download_maven_artifact \
#     eu.europa.ec.sante.ehdsi.openncp-protocol-terminators.openncp-ncp-server \
#     openncp-ws-server \
#     ${openncp_version} \
#     war \
#     ${openncp_repo} \
#     webapps/openncp-ws-server.war

# # Unpack the war to allow us to patch the internal configuration files
# RUN mkdir -p webapps/openncp-ws-server && \
#     cd webapps/openncp-ws-server && \
#     jar -xf ../openncp-ws-server.war && \
#     rm ../openncp-ws-server.war

# # Patch the Axis2 configuration with the correct port number:
# RUN sed -i \
#     's/<parameter name="port">8443<\/parameter>/<parameter name="port">6443<\/parameter>/g' \
#     webapps/openncp-ws-server/WEB-INF/conf/axis2.xml

# # Download JAR dependencies
# RUN cd webapps/openncp-ws-server/WEB-INF && \
#     download_maven_artifact org.openapitools jackson-databind-nullable 0.2.6

# RUN download_maven_artifact org.slf4j slf4j-jdk14 1.7.36 && \
#     download_maven_artifact org.mariadb.jdbc mariadb-java-client 3.2.0

# # Download "translations-and-mappings-ws" (introduced in 7.1.0)
# RUN download_maven_artifact \
#     eu.europa.ec.sante.ehdsi \
#     translations-and-mappings-ws \
#     ${openncp_version} \
#     war \
#     ${openncp_repo} \
#     webapps/translations-and-mappings-ws.war

# # Unpack the war to allow us to patch the internal configuration files
# RUN mkdir -p webapps/translations-and-mappings-ws && \
#     cd webapps/translations-and-mappings-ws && \
#     jar -xf ../translations-and-mappings-ws.war && \
#     rm ../translations-and-mappings-ws.war

# # SnakeYAML bug workaround mentioned in Slack:
# # https://openncp.slack.com/archives/C4QTX0BAT/p1707744795906019
# RUN sed -i \
#     "s/@project.version@-@timestamp@/'@project.version@-@timestamp@'/" \
#     webapps/translations-and-mappings-ws/WEB-INF/classes/application.yml

# # # Download mock services implementations
# # RUN download_maven_artifact \
# #     eu.europa.ec.sante.ehdsi.openncp-protocol-terminators.openncp-ncp-server \
# #     openncp-nc-mock-it \
# #     ${openncp_version} \
# #     jar \
# #     ${openncp_repo} \
# #     webapps/openncp-ws-server/WEB-INF/lib/openncp-nc-mock-it.jar

# # Install our own national connector implementation
# COPY --from=nc-builder /usr/src/app/target/openncp-national-connector-${openncp_version}.jar \
#     webapps/openncp-ws-server/WEB-INF/lib/openncp-national-connector.jar

# COPY ./openncp-server/server.xml conf/server.xml
# COPY ./openncp-server/context.xml conf/context.xml
# COPY ./openncp-server/setenv.sh bin/setenv.sh

# ENV EPSOS_PROPS_PATH=/opt/openncp-configuration/
# ENV LOGGING_LEVEL_ROOT=INFO

# EXPOSE 8080
# EXPOSE 6443
