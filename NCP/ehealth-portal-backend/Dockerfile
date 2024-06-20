FROM tomcat:9-jre11-temurin-jammy

ARG openncp_version
ARG openncp_repo="https://code.europa.eu/api/v4/projects/347/packages/maven/"

RUN  apt-get update \
  && apt-get install -y wget \
  && apt-get install -y gettext \
  && apt-get install -y socat \
  && rm -rf /var/lib/apt/lists/* 

COPY ./common/bin/download_maven_artifact /usr/local/bin/download_maven_artifact

# Download main artifact
RUN download_maven_artifact \
    eu.europa.ec.sante.ehdsi.openncp-protocol-terminators.ehealth-portal-backend \
    ehealth-portal-backend \
    ${openncp_version} \
    war \
    ${openncp_repo} \
    webapps/ehealth-portal-backend.war


WORKDIR /usr/local/tomcat/webapps
ENV EPSOS_PROPS_PATH=/opt/openncp-configuration/

COPY ehealth-portal-backend/target/*.war /usr/local/tomcat/webapps/ehealth-portal-backend.war
COPY ehealth-portal-backend/conf/tm.properties /opt/openncp-configuration/
COPY ehealth-portal-backend/conf/tomcat-config-entrypoint.sh /usr/local/tomcat/bin/
COPY ehealth-portal-backend/conf/tomcat/* /usr/local/tomcat/conf/
COPY ehealth-portal-backend/lib/* /usr/local/tomcat/lib/
COPY ehealth-portal-backend/certificates/* /opt/openncp-configuration/
RUN mkdir /opt/openncp-configuration/EpsosRepository

RUN mkdir /opt/openncp-configuration/validation
COPY ehealth-portal-backend/conf/gazelle.ehdsi.properties /opt/openncp-configuration/validation/

RUN chmod 777 /usr/local/tomcat/bin/tomcat-config-entrypoint.sh

ENTRYPOINT ["tomcat-config-entrypoint.sh"]

# Start the tomcat service
CMD ["catalina.sh", "run"]
