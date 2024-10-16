#!/bin/bash
set -o errexit
set -o nounset
set -o pipefail

# Export secrets as environment variables
export KEYSTORE_PASSWORD=$(cat /run/secrets/tls_keystore_password)  
export TRUSTSTORE_PASSWORD=$(cat /run/secrets/tls_truststore_password)
export DB_USER=$(cat /run/secrets/db_username)
export DB_PWD=$(cat /run/secrets/db_password)

# Execute the original command
exec "$@"
