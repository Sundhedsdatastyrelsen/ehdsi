#!/bin/bash
set -o errexit
set -o nounset
set -o pipefail

# Export secrets as environment variables
export TLS_KEYSTORE_PASSWORD; TLS_KEYSTORE_PASSWORD=$(cat /run/secrets/tls_keystore_password)
export TLS_TRUSTSTORE_PASSWORD; TLS_TRUSTSTORE_PASSWORD=$(cat /run/secrets/tls_truststore_password)
export KEYSTORE_PASSWORD; KEYSTORE_PASSWORD=$(cat /run/secrets/tls_keystore_password)  #Duplicate since it is unclear which one is used
export TRUSTSTORE_PASSWORD; TRUSTSTORE_PASSWORD=$(cat /run/secrets/tls_truststore_password)
export DB_USER; DB_USER=$(cat /run/secrets/db_username)
export DB_PWD; DB_PWD=$(cat /run/secrets/db_password)

envsubst \
    < /opt/atna-resources/ArrConnections.template.xml \
    > /opt/atna-resources/ArrConnections.xml

# The client will call the SMP to discover the correct openNCP server ip.
# The SMP we use has this hardcoded to (127.0.0.1:8843/openncp-ws-server/xxxx).
#
# A docker container resolves any call to localhost (or in this case 127.0.0.1) to itself instead of the network,
# meaning the SMP discovery URL to the server will fail.
# A common way to resolve localhost calls from inside a container to the another container is by using a HOST network instead of a BRIDGE network.
# However this creates other problems regarding port management and so on and is not a best practice.
# Mixing a bridge- and host network is not possible either.
# The socat command binds the call to localhost:8443 (TCP-LISTEN:8443) to the docker host ip (host.docker.internal) to be able to access the openncp server
# ip that comes back from the accept SMP discovery url (127.0.0.1:xxx/openncp-ws-server/xxxx)
socat TCP-LISTEN:8443,fork TCP:host.docker.internal:8443 &

# Execute the original entrypoint script
exec "$@"

