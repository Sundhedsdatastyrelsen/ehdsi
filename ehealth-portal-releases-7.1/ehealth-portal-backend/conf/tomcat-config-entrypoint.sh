#!/bin/sh

# envsubst assumes the image has the full GNU tool set (which is not the case in Alpine based images) if not use SED instead
envsubst < /usr/local/tomcat/conf/server.template.xml > /usr/local/tomcat/conf/server.xml

# Expose localhost:2443, since docker hostnames don't seem to work with SSL
socat TCP-LISTEN:2443,fork TCP:host.docker.internal:2443 &

# Run the standard container command.
exec "$@"
