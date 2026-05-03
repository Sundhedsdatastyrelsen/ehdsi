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
TARGET_DIR=$(dirname "$TARGET_DB")

if [[ ! -f "$TARGET_DB" ]]; then
    log "ERROR: Target database not found: $TARGET_DB"
    exit 1
fi

# SQLite needs to write to both the DB file (for the data) and its parent
# directory (for the rollback journal / WAL). In production the data dir is
# owned by uid 10001 (the NC container's user); from the host, cbrams gets
# read-only, which produces a confusing "attempt to write a readonly
# database (8)" error. Detect that up front.
if [[ ! -w "$TARGET_DB" || ! -w "$TARGET_DIR" ]]; then
    log "ERROR: $TARGET_DB is not writable as $(id -un) (uid $(id -u))"
    log "       File owner: $(stat -c '%U:%G (uid %u)' "$TARGET_DB" 2>/dev/null)"
    log ""
    log "       The data dir is typically owned by uid 10001 (the NC container user)."
    log "       Either:"
    log "         (a) sudo $0 $LABEL"
    log "         (b) sudo chown -R \$(id -u):\$(id -g) $TARGET_DIR  # run test, then chown back"
    log "         (c) ./backups/test/test-sqlite-snapshot-cycle.sh   # automated, uses temp dir"
    exit 1
fi

TS=$(date '+%Y-%m-%dT%H:%M:%S')

sqlite3 "$TARGET_DB" <<SQL
CREATE TABLE IF NOT EXISTS _sentinel (label TEXT, ts TEXT);
INSERT INTO _sentinel (label, ts) VALUES ('$LABEL', '$TS');
SQL

log "Added sentinel to $TARGET_DB: $LABEL = $TS"
