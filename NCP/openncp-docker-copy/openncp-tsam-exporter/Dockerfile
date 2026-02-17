FROM eclipse-temurin:11-jre-jammy

RUN apt-get update

WORKDIR /usr/local/openncp-tsam-exporter
COPY /target/utilities/openncp-tsamexporter.jar /usr/local/openncp-tsam-exporter
COPY /openncp-tsam-exporter/* /usr/local/openncp-tsam-exporter

CMD ["java", "-jar", "openncp-tsamexporter.jar"]
