#!/usr/bin/env bash

set -o errexit
set -o nounset
set -o pipefail


export MARIADB_PASSWORD; MARIADB_PASSWORD=$(<"${MARIADB_PASSWORD_FILE}")
export MARIADB_USERNAME; MARIADB_USERNAME=$(<"${MARIADB_USERNAME_FILE}")
export CTS_PASSWORD; CTS_PASSWORD=$(<"${CTS_PASSWORD_FILE}")
export CTS_USERNAME; CTS_USERNAME=$(<"${CTS_USERNAME_FILE}")


(
    cd openncp-tsam-sync
    java -jar ../lib/openncp-tsam-sync-*.jar
)
sleep 5
(
    cd openncp-tsam-exporter
    java -jar ../lib/openncp-tsamexporter-*.jar
)
