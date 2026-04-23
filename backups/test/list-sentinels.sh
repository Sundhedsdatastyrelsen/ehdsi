#!/usr/bin/env bash
# List all sentinel rows (NAME LIKE '_sentinel_%') currently visible in
# ehealth_properties.EHNCP_PROPERTY.
#
# Respects $MYSQL_CONTAINER and $MYSQL_ROOT_PASSWORD — point at the live
# source DB (default) or a restored test container to inspect state.

source "$(dirname "$0")/../lib/common.sh"

assert_container_running "$MYSQL_CONTAINER"
ROOT_PASSWORD=$(read_mysql_password)

log "Sentinels in $MYSQL_CONTAINER:"

# The underscore in LIKE is technically a single-char wildcard; using ESCAPE
# to make _sentinel_ a literal match and avoid accidental matches on other
# keys with similar shape.
docker exec "$MYSQL_CONTAINER" \
    mysql -u root -p"$ROOT_PASSWORD" \
    -e "SELECT NAME, VALUE FROM ehealth_properties.EHNCP_PROPERTY
        WHERE NAME LIKE '\\_sentinel\\_%' ESCAPE '\\\\'
        ORDER BY NAME;" 2>/dev/null
