FROM tomcat:9.0-jdk17-temurin

ARG openncp_version
ARG openncp_repo="https://code.europa.eu/api/v4/projects/347/packages/maven/"

COPY ./common/bin/download_maven_artifact /usr/local/bin/download_maven_artifact

# Prepare "tomcat" user
RUN groupadd -g 1000 tomcat && \
  useradd -r -u 1000 -g tomcat tomcat && \
  chown -R tomcat:tomcat /usr/local/tomcat/bin /usr/local/tomcat/webapps /usr/local/tomcat/conf /usr/local/tomcat/logs

# Download main artifact
RUN download_maven_artifact \
    eu.europa.ec.sante.ehdsi.openncp-protocol-terminators.openncp-ncp-client \
    openncp-client-connector \
    ${openncp_version} \
    war \
    ${openncp_repo} \
    webapps/openncp-client-connector.war

# Unpack the war to allow us to patch the internal configuration files
RUN mkdir -p webapps/openncp-client-connector && \
    cd webapps/openncp-client-connector && \
    jar -xf ../openncp-client-connector.war && \
    rm ../openncp-client-connector.war

# Patch the Axis2 configuration with the correct port numbers:
RUN sed -i \
    's/<parameter name="port">8443<\/parameter>/<parameter name="port">7443<\/parameter>/g' \
    webapps/openncp-client-connector/WEB-INF/conf/axis2.xml && \
    sed -i \
    's/<parameter name="port">8080<\/parameter>/<parameter name="port">8081<\/parameter>/g' \
    webapps/openncp-client-connector/WEB-INF/conf/axis2.xml


# # Download JAR dependencies
# RUN cd webapps/openncp-client-connector/WEB-INF && \
#     download_maven_artifact org.openapitools jackson-databind-nullable 0.2.6

RUN download_maven_artifact org.slf4j slf4j-jdk14 1.7.36 && \
    download_maven_artifact org.mariadb.jdbc mariadb-java-client 3.2.0

# Download "translations-and-mappings-ws" (introduced in 7.1.0)
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
# #     webapps/openncp-client-connector/WEB-INF/lib/openncp-nc-mock-it.jar

# # Install our own national connector implementation
# COPY --from=nc-builder /usr/src/app/target/openncp-national-connector-${openncp_version}.jar \
#     webapps/openncp-client-connector/WEB-INF/lib/openncp-national-connector.jar

COPY ./openncp-client/server.xml conf/server.xml
COPY ./openncp-client/context.xml conf/context.xml
COPY ./openncp-client/setenv.sh bin/setenv.sh

ENV EPSOS_PROPS_PATH=/opt/openncp-configuration/
ENV LOGGING_LEVEL_ROOT=INFO

EXPOSE 8081
EXPOSE 7443

USER tomcat
