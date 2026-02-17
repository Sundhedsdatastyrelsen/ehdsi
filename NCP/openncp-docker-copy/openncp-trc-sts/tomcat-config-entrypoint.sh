#!/bin/sh

# envsubst assumes the image has the full GNU tool set (which is not the case in Alpine based images) if not use SED instead
envsubst < /usr/local/tomcat/conf/server.template.xml > /usr/local/tomcat/conf/server.xml
envsubst < /opt/ehealth-openncp/openncp-configuration-utility/application.template.yaml > \
/opt/ehealth-openncp/openncp-configuration-utility/application.yaml
envsubst < /opt/ehealth-openncp/openncp-configuration-utility/openncp-configuration.template.properties > \
/opt/ehealth-openncp/openncp-configuration-utility/openncp-configuration.properties

# Run the standard container command.
exec "$@"
