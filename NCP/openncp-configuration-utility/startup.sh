#!/usr/bin/env bash

set -o errexit
set -o nounset
set -o pipefail


export MARIADB_PASSWORD; MARIADB_PASSWORD=$(<"${MARIADB_PASSWORD_FILE}")
export MARIADB_USERNAME; MARIADB_USERNAME=$(<"${MARIADB_USERNAME_FILE}")
export CTS_PASSWORD; CTS_PASSWORD=$(<"${CTS_PASSWORD_FILE}")
export CTS_USERNAME; CTS_USERNAME=$(<"${CTS_USERNAME_FILE}")
export TLS_PASSWORD; TLS_PASSWORD=$(<"${TLS_PASSWORD_FILE}")
export TRUSTSTORE_PASSWORD; TRUSTSTORE_PASSWORD=$(<"${TRUSTSTORE_PASSWORD_FILE}")

if ${LOAD_PROPERTIES}; then
  cd /opt/openncp-configuration-utility/
  java -jar lib/openncp-configuration-utility-*.jar
else
  echo 'LOAD_PROPERTIES Not SET, hence skipped loading application.properties to database'
fi

if ${RUN_TSAM}; then
  cd /opt/openncp-configuration-utility/openncp-tsam-sync/
  java -jar ../lib/openncp-tsam-sync-*.jar

  sleep 10

  cd /opt/openncp-configuration-utility/openncp-tsam-exporter/
  java -jar ../lib/openncp-tsamexporter-*.jar
else
  echo 'RUN_TSAM Not SET, hence skipped running tsam sync and tsam exporter'
fi
