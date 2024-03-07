FROM eclipse-temurin:21-jre

ARG openncp_version
ARG openncp_repo="https://code.europa.eu/api/v4/projects/347/packages/maven/"

COPY ./common/bin/download_maven_artifact /usr/local/bin/download_maven_artifact

# Download OpenNCP jars

WORKDIR /opt/openncp-configuration-utility

RUN download_maven_artifact \
    eu.europa.ec.sante.ehdsi \
    openncp-configuration-utility \
    ${openncp_version} \
    jar \
    ${openncp_repo} && \
    \
    download_maven_artifact \
    eu.europa.ec.sante.ehdsi \
    openncp-tsam-sync \
    ${openncp_version} \
    jar \
    ${openncp_repo} && \
    \
    download_maven_artifact \
    eu.europa.ec.sante.ehdsi.openncp-cdadisplaytool \
    openncp-tsamexporter \
    ${openncp_version} \
    jar \
    ${openncp_repo}

COPY ./openncp-configuration-utility/application.yml .
COPY ./openncp-configuration-utility/openncp-tsam-sync/application.yml ./openncp-tsam-sync/
COPY ./openncp-configuration-utility/openncp-tsam-exporter/application.yml ./openncp-tsam-exporter/
COPY --chmod=0755 ./openncp-configuration-utility/startup.sh .

CMD /opt/openncp-configuration-utility/startup.sh
