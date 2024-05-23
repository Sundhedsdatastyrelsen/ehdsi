#!/usr/bin/env bash

### Docker entrypoint for configuration-synchronizer

set -o errexit
set -o nounset
set -o pipefail


export MARIADB_PASSWORD; MARIADB_PASSWORD=$(<"${MARIADB_PASSWORD_FILE}")
export MARIADB_USERNAME; MARIADB_USERNAME=$(<"${MARIADB_USERNAME_FILE}")
export TLS_KEYSTORE_PASSWORD; TLS_KEYSTORE_PASSWORD=$(<"${TLS_KEYSTORE_PASSWORD_FILE}")
export SEAL_KEYSTORE_PASSWORD; SEAL_KEYSTORE_PASSWORD=$(<"${SEAL_KEYSTORE_PASSWORD_FILE}")
export TLS_TRUSTSTORE_PASSWORD; TLS_TRUSTSTORE_PASSWORD=$(<"${TLS_TRUSTSTORE_PASSWORD_FILE}")

java -jar configuration-synchronizer.jar
