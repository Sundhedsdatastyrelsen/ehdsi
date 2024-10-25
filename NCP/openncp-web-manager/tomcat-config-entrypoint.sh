#!/bin/sh

# envsubst assumes the image has the full GNU tool set (which is not the case in Alpine based images) if not use SED instead
envsubst < /usr/local/tomcat/conf/server.template.xml > /usr/local/tomcat/conf/server.xml
#envsubst < /opt/openncp-configuration-utility/application.template.yaml > \
#/opt/openncp-configuration-utility/application.yaml
#envsubst < /opt/openncp-configuration-utility/openncp-configuration.template.properties > \
#/opt/openncp-configuration-utility/openncp-configuration.properties

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
# Same as the above but for the openatna server, since docker hostnames don't seem to work with SSL
socat TCP-LISTEN:3443,fork TCP:host.docker.internal:3443 &

# Run the standard container command.
exec "$@"
