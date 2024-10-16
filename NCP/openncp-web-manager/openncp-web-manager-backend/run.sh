#!/bin/bash

# Export secrets as environment variables
export TLS_KEYSTORE_PASSWORD=$(cat /run/secrets/tls_keystore_password)
export TLS_TRUSTSTORE_PASSWORD=$(cat /run/secrets/tls_truststore_password)
export KEYSTORE_PASSWORD=$(cat /run/secrets/tls_keystore_password)  #Duplicate since it is unclear which one is used
export TRUSTSTORE_PASSWORD=$(cat /run/secrets/tls_truststore_password)
export DB_USER=$(cat /run/secrets/db_username)
export DB_PWD=$(cat /run/secrets/db_password)

# Execute the original command
exec "$@"

