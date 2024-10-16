#!/bin/bash
set -o errexit
set -o nounset
set -o pipefail

# Export secrets as environment variables
export KEYSTORE_PASSWORD=$(cat /run/secrets/keystore_password_file)
export TRUSTSTORE_PASSWORD=$(cat /run/secrets/truststore_password_file)

# Execute the original command
exec "$@"
