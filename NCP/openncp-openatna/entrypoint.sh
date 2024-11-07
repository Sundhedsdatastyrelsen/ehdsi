#!/bin/bash
set -o errexit
set -o nounset
set -o pipefail

if [ ! -w "/opt/openncp-configuration/obligations" ] || [ ! -w "/opt/openncp-configuration/validation" ]
then
    echo "/opt/openncp-configuration/obligations or /opt/openncp-configuration/validation are not writable. Exiting."
    exit 1
fi

# Export secrets as environment variables
export TLS_KEYSTORE_PASSWORD; TLS_KEYSTORE_PASSWORD=$(cat /run/secrets/tls_keystore_password)
export TLS_TRUSTSTORE_PASSWORD; TLS_TRUSTSTORE_PASSWORD=$(cat /run/secrets/tls_truststore_password)
export KEYSTORE_PASSWORD; KEYSTORE_PASSWORD=$(cat /run/secrets/tls_keystore_password)
export TRUSTSTORE_PASSWORD; TRUSTSTORE_PASSWORD=$(cat /run/secrets/tls_truststore_password)
export DB_USER; DB_USER=$(cat /run/secrets/db_username)
export DB_PWD; DB_PWD=$(cat /run/secrets/db_password)

envsubst \
    < /opt/atna-resources/ArrConnections.template.xml \
    > /opt/atna-resources/ArrConnections.xml

# Execute the original command
exec "$@"
