FROM eclipse-temurin:11-jre-jammy

RUN apt-get update && apt-get -qq -y install default-mysql-client

ENV EPSOS_PROPS_PATH=/opt/openncp-configuration/
COPY ./openncp-configuration/ $EPSOS_PROPS_PATH

WORKDIR /usr/local/openncp-tsam-sync
COPY /target/utilities/openncp-tsam-sync.jar /usr/local/openncp-tsam-sync
COPY /openncp-tsam-sync/* /usr/local/openncp-tsam-sync

CMD ["java", "-jar", "openncp-tsam-sync.jar"]
