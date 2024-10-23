#!/bin/sh

envsubst < /opt/ehealth-openncp/openncp-configuration-utility/openncp-configuration.template.properties > \
/opt/ehealth-openncp/openncp-configuration-utility/openncp-configuration.properties

# Run the standard container command.
exec "$@"
