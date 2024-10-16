#!/bin/bash

# Export secrets as environment variables
export TLS_KEYSTORE_PASSWORD=$(cat /run/secrets/tls_keystore_password)
export TLS_TRUSTSTORE_PASSWORD=$(cat /run/secrets/tls_truststore_password)
export DB_USERNAME=$(cat /run/secrets/db_username)
export DB_PASSWORD=$(cat /run/secrets/db_password)

# Execute the original command
exec "$@"

