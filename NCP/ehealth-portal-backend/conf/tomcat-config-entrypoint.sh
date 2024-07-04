#!/bin/sh

echo "THIS IS SPARTA - $(cat "${DB_USER_FILE}")"



DB_USER="$(cat "${DB_USER_FILE}")"
DB_PASSWORD="$(cat "${DB_PASSWORD_FILE}")"
TLS_KEYSTORE_PWD="$(cat "${TLS_KEYSTORE_PWD_FILE}")"
TLS_TRUSTSTORE_PWD="$(cat "${TLS_TRUSTSTORE_PWD_FILE}")"
KEYSTORE_PASSWORD="$(cat "${KEYSTORE_PASSWORD_FILE}")"
TRUSTSTORE_PASSWORD="$(cat "${TRUSTSTORE_PASSWORD_FILE}")"
DB_ROOT_PASSWORD="$(cat "${DB_ROOT_PASSWORD_FILE}")"

export DB_USER
export DB_PASSWORD
export TLS_KEYSTORE_PWD
export TLS_TRUSTSTORE_PWD
export KEYSTORE_PASSWORD
export TRUSTSTORE_PASSWORD
export DB_ROOT_PASSWORD

# envsubst assumes the image has the full GNU tool set (which is not the case in Alpine based images) if not use SED instead
envsubst < /usr/local/tomcat/conf/server.template.xml > /usr/local/tomcat/conf/server.xml

# Expose localhost:2443, since docker hostnames don't seem to work with SSL
socat TCP-LISTEN:2443,fork TCP:host.docker.internal:2443 &

# Run the standard container command.
exec "$@"
