#!/usr/bin/env bash
# Insert a labeled sentinel row into ehealth_properties.EHNCP_PROPERTY
# for backup/restore testing.
#
# Respects $MYSQL_CONTAINER and $MYSQL_ROOT_PASSWORD env vars, so it can
# target a disposable test container as well as the live source DB.

source "$(dirname "$0")/../lib/common.sh"

LABEL="${1:-}"
if [[ -z "$LABEL" ]]; then
    echo "Usage: $0 <label>" >&2
    echo "  Inserts a row with NAME='_sentinel_<label>' into ehealth_properties.EHNCP_PROPERTY" >&2
    exit 1
fi

# Restrict label charset so direct SQL interpolation is safe
if [[ ! "$LABEL" =~ ^[A-Za-z0-9_-]+$ ]]; then
    echo "ERROR: Label must match ^[A-Za-z0-9_-]+$ (got: '$LABEL')" >&2
    exit 1
fi

assert_container_running "$MYSQL_CONTAINER"
ROOT_PASSWORD=$(read_mysql_password)

SENTINEL_KEY="_sentinel_${LABEL}"
SENTINEL_VALUE="inserted_at_$(date '+%Y-%m-%dT%H:%M:%S')"

docker exec "$MYSQL_CONTAINER" \
    mysql -u root -p"$ROOT_PASSWORD" \
    -e "INSERT INTO ehealth_properties.EHNCP_PROPERTY (NAME, VALUE)
        VALUES ('$SENTINEL_KEY', '$SENTINEL_VALUE');" 2>/dev/null

log "Added sentinel in $MYSQL_CONTAINER: $SENTINEL_KEY = $SENTINEL_VALUE"
