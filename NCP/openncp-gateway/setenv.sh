# See: https://tomcat.apache.org/tomcat-7.0-doc/RUNNING.txt#:~:text=(3.4)

export CATALINA_HOME=/usr/local/tomcat
export CATALINA_BASE=/usr/local/tomcat

MARIADB_USERNAME="$(cat "${MARIADB_USERNAME_FILE}")"
MARIADB_PASSWORD="$(cat "${MARIADB_PASSWORD_FILE}")"
TLS_KEYSTORE_PASSWORD="$(cat "${TLS_KEYSTORE_PASSWORD_FILE}")"

CATALINA_OPTS="$CATALINA_OPTS -DopenATNA.properties.path=file:$EPSOS_PROPS_PATH/ATNA_resources/openatna.properties"
CATALINA_OPTS="$CATALINA_OPTS -Ddb.host=\"${MARIADB_HOST}\""
CATALINA_OPTS="$CATALINA_OPTS -Ddb.port=\"${MARIADB_PORT}\""
CATALINA_OPTS="$CATALINA_OPTS -Ddb.username=\"${MARIADB_USERNAME}\""
CATALINA_OPTS="$CATALINA_OPTS -Ddb.password=\"${MARIADB_PASSWORD}\""
CATALINA_OPTS="$CATALINA_OPTS -Dtls.keystore.alias=\"${TLS_KEYSTORE_ALIAS}\""
CATALINA_OPTS="$CATALINA_OPTS -Dtls.keystore.file=\"${TLS_KEYSTORE_FILE}\""
CATALINA_OPTS="$CATALINA_OPTS -Dtls.keystore.password=\"${TLS_KEYSTORE_PASSWORD}\""

# See: https://webgate.ec.europa.eu/fpfis/wikis/display/EHDSI/OpenNCP+Installation+Manual#OpenNCPInstallationManual-4.1.1OpenNCPGateway
export spring_config_location="$EPSOS_PROPS_PATH/gateway/application.yml"
