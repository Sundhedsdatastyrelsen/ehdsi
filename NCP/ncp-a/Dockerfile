FROM tomcat:9.0-jdk11-temurin

ARG openncp_version="6.5.0"
ARG openncp_repo="https://code.europa.eu/api/v4/projects/347/packages/maven/"

COPY ./bin/download_maven_artifact /usr/local/bin/download_maven_artifact

# Download JAR dependencies
RUN download_maven_artifact com.zaxxer HikariCP 4.0.3 && \
    download_maven_artifact org.slf4j slf4j-api 2.0.9 && \
    download_maven_artifact org.slf4j slf4j-jdk14 2.0.9 && \
    download_maven_artifact org.mariadb.jdbc mariadb-java-client 3.2.0

# Download main artifact
RUN download_maven_artifact eu.europa.ec.sante.ehdsi.openncp-protocol-terminators.openncp-ncp-server \
    openncp-ws-server \
    ${openncp_version} \
    war \
    ${openncp_repo} \
    webapps/openncp-ws-server.war
