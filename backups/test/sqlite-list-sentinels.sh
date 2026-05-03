#!/usr/bin/env bash
# List all sentinel rows currently in \$NC_DIR/data/undo-db.sqlite.

# shellcheck source=SCRIPTDIR/../lib/common.sh
source "$(dirname "$0")/../lib/common.sh"

if ! command -v sqlite3 &>/dev/null; then
    log "ERROR: sqlite3 is not installed on the host"
    exit 1
fi

TARGET_DB="$NC_DIR/data/undo-db.sqlite"

if [[ ! -f "$TARGET_DB" ]]; then
    log "ERROR: Target database not found: $TARGET_DB"
    exit 1
fi

log "Sentinels in $TARGET_DB:"

# Detect table presence explicitly so we distinguish "empty table" from
# "table doesn't exist yet". sqlite3 returns empty output for SELECT against
# a missing table AND for an empty table.
HAS_TABLE=$(sqlite3 "$TARGET_DB" \
    "SELECT 1 FROM sqlite_master WHERE type='table' AND name='_sentinel';")

if [[ -z "$HAS_TABLE" ]]; then
    log "  (no _sentinel table yet)"
    exit 0
fi

sqlite3 -header -column "$TARGET_DB" \
    "SELECT label, ts FROM _sentinel ORDER BY label;"
