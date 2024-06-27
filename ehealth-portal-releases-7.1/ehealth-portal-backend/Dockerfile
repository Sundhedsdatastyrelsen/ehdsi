FROM maven:3-eclipse-temurin-21 as builder

WORKDIR /usr/src/app
COPY . .

RUN mvn compile

#FROM tomcat:9-jre11-temurin-jammy
#
#RUN  apt-get update \
#  && apt-get install -y wget \
#  && apt-get install -y gettext \
#  && apt-get install -y socat \
#  && rm -rf /var/lib/apt/lists/*
#
#WORKDIR /usr/local/tomcat/webapps
#ENV EPSOS_PROPS_PATH=/opt/openncp-configuration/
#
#COPY --from=builder target/*.war /usr/local/tomcat/webapps/openncp-portal-backend.war
#COPY conf/configmanager.cfg.xml /opt/openncp-configuration/
#COPY conf/tomcat-config-entrypoint.sh /usr/local/tomcat/bin/
#COPY conf/tomcat/* /usr/local/tomcat/conf/
#COPY lib/* /usr/local/tomcat/lib/
#COPY certificates/* /opt/openncp-configuration/
#RUN mkdir /opt/openncp-configuration/EpsosRepository
#
#RUN chmod 777 /usr/local/tomcat/bin/tomcat-config-entrypoint.sh
#
#ENTRYPOINT ["tomcat-config-entrypoint.sh"]
#
## Start the tomcat service
#CMD ["catalina.sh", "run"]
