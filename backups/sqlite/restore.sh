#!/usr/bin/env bash

# shellcheck source=SCRIPTDIR/../lib/common.sh
source "$(dirname "$0")/../lib/common.sh"

AUTO_YES=false
SNAPSHOT_DIR=""

while [[ $# -gt 0 ]]; do
    case "$1" in
        --yes) AUTO_YES=true; shift ;;
        *) SNAPSHOT_DIR="$1"; shift ;;
    esac
done

if [[ -z "$SNAPSHOT_DIR" ]]; then
    SNAPSHOT_DIR=$(find "$BACKUP_ROOT/sqlite" -maxdepth 1 -mindepth 1 -type d | sort | tail -n 1)
    if [[ -z "$SNAPSHOT_DIR" ]]; then
        log "ERROR: No snapshot directories found in $BACKUP_ROOT/sqlite/"
        exit 1
    fi
    log "Auto-selected latest snapshot: $SNAPSHOT_DIR"
fi

if [[ ! -d "$SNAPSHOT_DIR" ]]; then
    log "ERROR: Snapshot directory not found: $SNAPSHOT_DIR"
    exit 1
fi

if ! command -v sqlite3 &>/dev/null; then
    log "ERROR: sqlite3 is not installed on the host. Install it with: apt install sqlite3"
    exit 1
fi

RESTORE_FILES=()
for db in "${SQLITE_DATABASES[@]}"; do
    if [[ -f "$SNAPSHOT_DIR/$db" ]]; then
        RESTORE_FILES+=("$db")
    fi
done

if [[ ${#RESTORE_FILES[@]} -eq 0 ]]; then
    log "ERROR: No database files found in snapshot: $SNAPSHOT_DIR"
    exit 1
fi

if [[ "$AUTO_YES" != true ]]; then
    echo "This will OVERWRITE the following databases:"
    printf "  - %s\n" "${RESTORE_FILES[@]}"
    echo ""
    echo "From snapshot: $SNAPSHOT_DIR"
    echo "The $SQLITE_CONTAINER container will be stopped during restore."
    read -r -p "Continue? [y/N] " response
    if [[ ! "$response" =~ ^[Yy]$ ]]; then
        log "Restore cancelled by user"
        exit 0
    fi
fi

log "Verifying backup integrity..."
for db in "${RESTORE_FILES[@]}"; do
    RESULT=$(sqlite3 "$SNAPSHOT_DIR/$db" "PRAGMA integrity_check;" 2>&1)
    if [[ "$RESULT" != "ok" ]]; then
        log "ERROR: Integrity check failed for $SNAPSHOT_DIR/$db: $RESULT"
        exit 1
    fi
done

log "Stopping $SQLITE_CONTAINER container..."
if [[ -n "$SQLITE_COMPOSE_FILE" && -f "$SQLITE_COMPOSE_FILE" ]]; then
    docker compose -f "$SQLITE_COMPOSE_FILE" stop "$SQLITE_COMPOSE_SERVICE" 2>/dev/null || \
        docker stop "$SQLITE_CONTAINER" 2>/dev/null || true
else
    docker stop "$SQLITE_CONTAINER" 2>/dev/null || true
fi

for db in "${RESTORE_FILES[@]}"; do
    log "Restoring: $db"
    cp "$SNAPSHOT_DIR/$db" "$SQLITE_DATA_DIR/$db"
    # Carry over WAL/SHM if the snapshot has them; otherwise clear stale ones
    # so SQLite does not reuse a pre-restore journal against the new file.
    for suffix in -wal -shm; do
        if [[ -f "$SNAPSHOT_DIR/${db}${suffix}" ]]; then
            cp "$SNAPSHOT_DIR/${db}${suffix}" "$SQLITE_DATA_DIR/${db}${suffix}"
        else
            rm -f "$SQLITE_DATA_DIR/${db}${suffix}"
        fi
    done
done

# Best-effort: in dev (non-root) the chown fails and the container's init
# step re-chowns on startup, so we only warn.
if [[ -n "$SQLITE_DATA_OWNER" ]]; then
    chown -R "$SQLITE_DATA_OWNER" "$SQLITE_DATA_DIR" 2>/dev/null \
        || log "NOTE: chown $SQLITE_DATA_OWNER skipped (not root?); container init will fix on start"
fi

log "Starting $SQLITE_CONTAINER container..."
if [[ -n "$SQLITE_COMPOSE_FILE" && -f "$SQLITE_COMPOSE_FILE" ]]; then
    docker compose -f "$SQLITE_COMPOSE_FILE" start "$SQLITE_COMPOSE_SERVICE" 2>/dev/null || \
        docker start "$SQLITE_CONTAINER" 2>/dev/null || true
else
    docker start "$SQLITE_CONTAINER" 2>/dev/null || true
fi

log "SQLite restore completed successfully from: $SNAPSHOT_DIR"
