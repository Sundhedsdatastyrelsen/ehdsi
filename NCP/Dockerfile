FROM maven:3.9-eclipse-temurin-11 as builder

ARG OPENNCP_BRANCH="releases/W8"

WORKDIR /usr/src/app

RUN --mount=type=cache,target=/root/.m2 \
    git clone --branch ${OPENNCP_BRANCH} --depth 1 https://code.europa.eu/ehdsi/ehealth.git && \
    cd ehealth && \
    mvn -DskipTests install

COPY openncp-national-connector openncp-national-connector
COPY ncp_a/pom.xml openncp-server/pom.xml

RUN --mount=type=cache,target=/root/.m2 \
    cd openncp-national-connector && \
    mvn install && \
    cd ../openncp-server && \
    mvn install

# Base Tomcat image for most services
FROM tomcat:9-jdk11-temurin as tomcat-base

RUN apt-get update \
    && apt-get install -y \
    gettext \
    socat \
    && rm -rf /var/lib/apt/lists/*

# https://github.com/hexops-graveyard/dockerfile#do-not-use-a-uid-below-10000
RUN groupadd -g 10001 tomcat && \
    useradd -r -u 10001 -g tomcat tomcat

ENV EPSOS_PROPS_PATH=/opt/openncp-configuration/
COPY ./openncp-configuration/ $EPSOS_PROPS_PATH
COPY ./atna-resources/ /opt/atna-resources

RUN chown -R tomcat:tomcat . $EPSOS_PROPS_PATH /opt/atna-resources


# TSAM Exporter
FROM eclipse-temurin:11-jre-jammy as tsam-exporter

RUN apt-get update

WORKDIR /usr/local/openncp-tsam-exporter
COPY --from=builder /usr/src/app/ehealth/openncp-docker/target/utilities/openncp-tsamexporter.jar /usr/local/openncp-tsam-exporter
COPY /openncp-tsam-exporter/* /usr/local/openncp-tsam-exporter

RUN chmod +x /usr/local/openncp-tsam-exporter/run.sh

ENTRYPOINT ["/bin/sh", "-c", "/usr/local/openncp-tsam-exporter/run.sh && java -jar openncp-tsamexporter.jar"]

# Node A
FROM tomcat-base as node-a

COPY --from=builder \
    /usr/src/app/openncp-server/target/openncp-server-*.war \
    webapps/openncp-ws-server.war

COPY --from=builder \
    /usr/src/app/ehealth/openncp-docker/target/classes/lib \
    lib/

# COPY ./ncp_a/tomcat-config-entrypoint.sh /usr/local/tomcat/bin/
COPY ./ncp_a/config conf/
COPY ./ncp_a/entrypoint.sh bin/

RUN chown -R tomcat:tomcat .

# Make it possible to use environment variables in server.xml:
RUN echo \
    "org.apache.tomcat.util.digester.PROPERTY_SOURCE=org.apache.tomcat.util.digester.EnvironmentPropertySource" \
    >> conf/catalina.properties

# Take the logging down a notch by default
ENV LOGGING_LEVEL_ROOT=INFO

ENTRYPOINT ["entrypoint.sh"]
CMD ["catalina.sh", "run"]

USER tomcat

# Node B
FROM tomcat-base as node-b

COPY --from=builder /usr/src/app/ehealth/openncp-docker/target/classes/webapp/openncp-client-connector.war /usr/local/tomcat/webapps/
COPY --from=builder /usr/src/app/ehealth/openncp-docker/target/classes/lib /usr/local/tomcat/lib/

COPY ./ncp_b/tomcat-config-entrypoint.sh /usr/local/tomcat/bin/
COPY ./ncp_b/config /usr/local/tomcat/conf/
COPY ./ncp_b/run.sh /usr/local/tomcat/bin/

RUN chmod 777 /usr/local/tomcat/bin/tomcat-config-entrypoint.sh
RUN chmod 777 /usr/local/tomcat/bin/run.sh

RUN chown -R tomcat:tomcat .

ENTRYPOINT ["/usr/local/tomcat/bin/run.sh", "/usr/local/tomcat/bin/tomcat-config-entrypoint.sh"]

USER tomcat

CMD ["catalina.sh", "run"]

# TRC-STS
FROM tomcat-base as trc-sts

COPY --from=builder /usr/src/app/ehealth/openncp-docker/target/classes/webapp/TRC-STS.war /usr/local/tomcat/webapps/
COPY --from=builder /usr/src/app/ehealth/openncp-docker/target/classes/lib /usr/local/tomcat/lib/

COPY ./openncp-trc-sts/tomcat-config-entrypoint.sh /usr/local/tomcat/bin/
COPY ./openncp-trc-sts/config /usr/local/tomcat/conf/
COPY ./openncp-trc-sts/run.sh /usr/local/tomcat/bin/

RUN chmod 777 /usr/local/tomcat/bin/tomcat-config-entrypoint.sh
RUN chmod 777 /usr/local/tomcat/bin/run.sh

RUN chown -R tomcat:tomcat .

ENTRYPOINT ["/usr/local/tomcat/bin/run.sh", "tomcat-config-entrypoint.sh"]

USER tomcat

CMD ["catalina.sh", "run"]

# OpenATNA
FROM tomcat-base as openatna

COPY --from=builder /usr/src/app/ehealth/openncp-docker/target/classes/webapp/openatna-web.war /usr/local/tomcat/webapps/
COPY --from=builder /usr/src/app/ehealth/openncp-docker/target/classes/lib /usr/local/tomcat/lib/

COPY ./openncp-openatna/tomcat-config-entrypoint.sh /usr/local/tomcat/bin/
COPY ./openncp-openatna/config /usr/local/tomcat/conf/
COPY ./openncp-openatna/run.sh /usr/local/tomcat/bin/

RUN chmod 777 /usr/local/tomcat/bin/tomcat-config-entrypoint.sh
RUN chmod 777 /usr/local/tomcat/bin/run.sh

RUN chown -R tomcat:tomcat .

ENTRYPOINT ["/usr/local/tomcat/bin/run.sh", "tomcat-config-entrypoint.sh"]

USER tomcat

CMD ["catalina.sh", "run"]

# Web Manager Backend
FROM tomcat-base as web-manager-backend

COPY --from=builder /usr/src/app/ehealth/openncp-docker/target/classes/webapp/openncp-gateway-backend.war /usr/local/tomcat/webapps/
COPY --from=builder /usr/src/app/ehealth/openncp-docker/target/classes/lib /usr/local/tomcat/lib/

COPY openncp-web-manager/openncp-web-manager-backend/tomcat-config-entrypoint.sh /usr/local/tomcat/bin/
COPY openncp-web-manager/openncp-web-manager-backend/config /usr/local/tomcat/conf/
COPY openncp-web-manager/openncp-web-manager-backend/application-docker.yml /opt/config/
COPY openncp-web-manager/openncp-web-manager-backend/run.sh /usr/local/tomcat/bin/

RUN chmod 777 /usr/local/tomcat/bin/tomcat-config-entrypoint.sh
RUN chmod 777 /usr/local/tomcat/bin/run.sh

RUN chown -R tomcat:tomcat .

ENTRYPOINT ["/usr/local/tomcat/bin/run.sh", "tomcat-config-entrypoint.sh"]

USER tomcat

CMD ["catalina.sh", "run"]

# Translations and Mappings
FROM tomcat-base as translations-and-mappings

COPY --from=builder /usr/src/app/ehealth/openncp-docker/target/classes/webapp/translations-and-mappings-ws.war /usr/local/tomcat/webapps/
COPY --from=builder /usr/src/app/ehealth/openncp-docker/target/classes/lib /usr/local/tomcat/lib/

COPY ./openncp-translations-and-mappings/tomcat-config-entrypoint.sh /usr/local/tomcat/bin/
COPY ./openncp-translations-and-mappings/config /usr/local/tomcat/conf/
COPY ./openncp-translations-and-mappings/run.sh /usr/local/tomcat/bin/

RUN chmod 777 /usr/local/tomcat/bin/tomcat-config-entrypoint.sh
RUN chmod 777 /usr/local/tomcat/bin/run.sh

RUN chown -R tomcat:tomcat .

ENTRYPOINT ["/usr/local/tomcat/bin/run.sh", "tomcat-config-entrypoint.sh"]

USER tomcat

CMD ["catalina.sh", "run"]

# TSAM Sync
FROM eclipse-temurin:11-jre-jammy as tsam-sync

RUN apt-get update && apt-get -qq -y install default-mysql-client

ENV EPSOS_PROPS_PATH=/opt/openncp-configuration/
COPY ./openncp-configuration/ $EPSOS_PROPS_PATH

WORKDIR /usr/local/openncp-tsam-sync
COPY --from=builder /usr/src/app/ehealth/openncp-docker/target/utilities/openncp-tsam-sync.jar /usr/local/openncp-tsam-sync
COPY /openncp-tsam-sync/* /usr/local/openncp-tsam-sync

CMD ["java", "-jar", "openncp-tsam-sync.jar"]

# Configuration Utility
FROM eclipse-temurin:11-jre-jammy as configuration-utility

WORKDIR /usr/local/openncp-configuration-utility
COPY --from=builder /usr/src/app/ehealth/openncp-docker/target/utilities/openncp-configuration-utility.jar /usr/local/openncp-configuration-utility
COPY /openncp-configuration-utility/* /usr/local/openncp-configuration-utility

CMD ["java", "-jar", "openncp-configuration-utility.jar"]

# Web Manager Frontend
FROM node:14.21-alpine as web-manager-frontend

RUN apk add --update python3 make g++ && rm -rf /var/cache/apk/*

RUN npm install -g http-server

WORKDIR /app

COPY --from=builder /usr/src/app/ehealth/openncp-web-manager/openncp-web-manager-frontend/package*.json ./

RUN npm install

COPY --from=builder /usr/src/app/ehealth/openncp-web-manager/openncp-web-manager-frontend .

ARG GATEWAY_BACKEND_URL
RUN sed -i -e "/^VUE_APP_SERVER_URL/s/=.*$/=${GATEWAY_BACKEND_URL}/" .env.production
RUN sed -i '/publicPath/d' vue.config.js

RUN npm run build

EXPOSE 8080
CMD [ "http-server", "dist"]
