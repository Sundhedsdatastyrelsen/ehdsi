#!/usr/bin/env bash
# Insert a labeled sentinel row into the _sentinel table of a SQLite
# database for backup/restore testing. Creates the table if missing.
#
# Respects $NC_DIR (indirectly via common.sh) so it can target a temporary
# data directory in tests.

# shellcheck source=SCRIPTDIR/../lib/common.sh
source "$(dirname "$0")/../lib/common.sh"

LABEL="${1:-}"
if [[ -z "$LABEL" ]]; then
    echo "Usage: $0 <label>" >&2
    echo "  Inserts a row (label, ts) into _sentinel in \$NC_DIR/data/undo-db.sqlite" >&2
    exit 1
fi

# Restrict label charset so direct SQL interpolation is safe
if [[ ! "$LABEL" =~ ^[A-Za-z0-9_-]+$ ]]; then
    echo "ERROR: Label must match ^[A-Za-z0-9_-]+$ (got: '$LABEL')" >&2
    exit 1
fi

if ! command -v sqlite3 &>/dev/null; then
    log "ERROR: sqlite3 is not installed on the host"
    exit 1
fi

TARGET_DB="$NC_DIR/data/undo-db.sqlite"

if [[ ! -f "$TARGET_DB" ]]; then
    log "ERROR: Target database not found: $TARGET_DB"
    exit 1
fi

TS=$(date '+%Y-%m-%dT%H:%M:%S')

sqlite3 "$TARGET_DB" <<SQL
CREATE TABLE IF NOT EXISTS _sentinel (label TEXT, ts TEXT);
INSERT INTO _sentinel (label, ts) VALUES ('$LABEL', '$TS');
SQL

log "Added sentinel to $TARGET_DB: $LABEL = $TS"
