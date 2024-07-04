FROM tomcat:9-jre11-temurin-jammy

RUN  apt-get update \
  && apt-get install -y wget \
  && apt-get install -y gettext \
  && apt-get install -y socat \
  && apt-get install -y maven \
  && rm -rf /var/lib/apt/lists/*

WORKDIR /usr/local/tomcat/webapps
ENV EPSOS_PROPS_PATH=/opt/openncp-configuration/

COPY target/*.war /usr/local/tomcat/webapps/ehealth-portal-backend.war
COPY conf/tm.properties /opt/openncp-configuration/
COPY conf/tomcat-config-entrypoint.sh /usr/local/tomcat/bin/
COPY conf/tomcat/* /usr/local/tomcat/conf/
COPY lib/* /usr/local/tomcat/lib/
COPY certificates/* /opt/openncp-configuration/
RUN mkdir /opt/openncp-configuration/EpsosRepository

RUN mkdir /opt/openncp-configuration/validation
COPY conf/gazelle.ehdsi.properties /opt/openncp-configuration/validation/

RUN chmod 777 /usr/local/tomcat/bin/tomcat-config-entrypoint.sh

ENTRYPOINT ["tomcat-config-entrypoint.sh"]

# Start the tomcat service
CMD ["catalina.sh", "run"]
