#!/bin/bash

# Export secrets as environment variables
export TLS_KEYSTORE_PASSWORD=$(cat /run/secrets/tls_keystore_password)
export TLS_TRUSTSTORE_PASSWORD=$(cat /run/secrets/tls_truststore_password)

# Execute the next command in the chain
exec "$@"

