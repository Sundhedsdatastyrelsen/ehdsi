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

COPY ./tsam-synchronizer/openncp-tsam-sync/application.yml ./openncp-tsam-sync/
COPY ./tsam-synchronizer/openncp-tsam-exporter/application.yml ./openncp-tsam-exporter/
COPY ./tsam-synchronizer/run.sh .

CMD ["./run.sh"]
