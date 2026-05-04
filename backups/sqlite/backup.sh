#!/usr/bin/env bash
# Online-safe (.backup) snapshot of all configured SQLite databases.

# shellcheck source=SCRIPTDIR/../lib/common.sh
source "$(dirname "$0")/../lib/common.sh"

TS=$(timestamp)
BACKUP_DIR="$BACKUP_ROOT/sqlite/$TS"
ensure_dir "$BACKUP_DIR"

if ! command -v sqlite3 &>/dev/null; then
    log "ERROR: sqlite3 is not installed on the host. Install it with: apt install sqlite3"
    exit 1
fi

if [[ ! -d "$SQLITE_DATA_DIR" ]]; then
    log "ERROR: SQLite data directory not found: $SQLITE_DATA_DIR"
    exit 1
fi

log "Starting SQLite backup to $BACKUP_DIR"

FAILED=0
for db in "${SQLITE_DATABASES[@]}"; do
    SRC="$SQLITE_DATA_DIR/$db"
    DEST="$BACKUP_DIR/$db"

    if [[ ! -f "$SRC" ]]; then
        log "WARNING: Database not found, skipping: $SRC"
        continue
    fi

    log "Backing up: $db"
    sqlite3 "$SRC" ".backup '$DEST'"

    RESULT=$(sqlite3 "$DEST" "PRAGMA integrity_check;" 2>&1)
    if [[ "$RESULT" != "ok" ]]; then
        log "ERROR: Integrity check failed for $DEST: $RESULT"
        FAILED=1
    else
        SIZE=$(du -h "$DEST" | cut -f1)
        log "Verified: $db ($SIZE)"
    fi
done

if (( FAILED )); then
    log "ERROR: One or more backups failed integrity check"
    exit 1
fi

cleanup_old_backups "$BACKUP_ROOT/sqlite" "$SQLITE_BACKUP_RETAIN"

log "SQLite backup finished successfully: $BACKUP_DIR"
