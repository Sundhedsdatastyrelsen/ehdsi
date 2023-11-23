# See: https://tomcat.apache.org/tomcat-7.0-doc/RUNNING.txt#:~:text=(3.4)

export CATALINA_HOME=/usr/local/tomcat
export CATALINA_BASE=/usr/local/tomcat

MARIADB_USERNAME="$(cat "${MARIADB_USERNAME_FILE}")"
MARIADB_PASSWORD="$(cat "${MARIADB_PASSWORD_FILE}")"
TLS_KEYSTORE_PASSWORD="$(cat "${TLS_KEYSTORE_PASSWORD_FILE}")"
TLS_TRUSTSTORE_PASSWORD="$(cat "${TLS_TRUSTSTORE_PASSWORD_FILE}")"

# Used in server.xml:
JAVA_OPTS="$JAVA_OPTS -Ddb.host=\"${MARIADB_HOST}\""
JAVA_OPTS="$JAVA_OPTS -Ddb.port=\"${MARIADB_PORT}\""
JAVA_OPTS="$JAVA_OPTS -Ddb.username=\"${MARIADB_USERNAME}\""
JAVA_OPTS="$JAVA_OPTS -Ddb.password=\"${MARIADB_PASSWORD}\""
JAVA_OPTS="$JAVA_OPTS -Dtls.keystore.alias=\"${TLS_KEYSTORE_ALIAS}\""
JAVA_OPTS="$JAVA_OPTS -Dtls.keystore.file=\"${TLS_KEYSTORE_FILE}\""
JAVA_OPTS="$JAVA_OPTS -Dtls.keystore.password=\"${TLS_KEYSTORE_PASSWORD}\""
JAVA_OPTS="$JAVA_OPTS -Dtls.truststore.file=\"${TLS_TRUSTSTORE_FILE}\""
JAVA_OPTS="$JAVA_OPTS -Dtls.truststore.password=\"${TLS_TRUSTSTORE_PASSWORD}\""

CATALINA_OPTS="$CATALINA_OPTS -DopenATNA.properties.path=file:/opt/openncp-configuration/ATNA_resources/openatna.properties -Dfile.encoding=UTF-8"
