#!/usr/bin/env bash
# Restore SQLite databases from a backup snapshot

# shellcheck source=SCRIPTDIR/../lib/common.sh
source "$(dirname "$0")/../lib/common.sh"

AUTO_YES=false
SNAPSHOT_DIR=""

# Parse arguments
while [[ $# -gt 0 ]]; do
    case "$1" in
        --yes) AUTO_YES=true; shift ;;
        *) SNAPSHOT_DIR="$1"; shift ;;
    esac
done

# If no snapshot specified, use the latest
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

SQLITE_DATA_DIR="$NC_DIR/data"

if ! command -v sqlite3 &>/dev/null; then
    log "ERROR: sqlite3 is not installed on the host. Install it with: apt install sqlite3"
    exit 1
fi

# List files to restore
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

# Confirmation prompt
if [[ "$AUTO_YES" != true ]]; then
    echo "This will OVERWRITE the following databases:"
    printf "  - %s\n" "${RESTORE_FILES[@]}"
    echo ""
    echo "From snapshot: $SNAPSHOT_DIR"
    echo "The national-connector container will be stopped during restore."
    read -r -p "Continue? [y/N] " response
    if [[ ! "$response" =~ ^[Yy]$ ]]; then
        log "Restore cancelled by user"
        exit 0
    fi
fi

# Verify backup integrity before restoring
log "Verifying backup integrity..."
for db in "${RESTORE_FILES[@]}"; do
    RESULT=$(sqlite3 "$SNAPSHOT_DIR/$db" "PRAGMA integrity_check;" 2>&1)
    if [[ "$RESULT" != "ok" ]]; then
        log "ERROR: Integrity check failed for $SNAPSHOT_DIR/$db: $RESULT"
        exit 1
    fi
done

# Stop the national-connector
log "Stopping national-connector container..."
docker compose -f "$NC_DIR/docker-compose.yml" stop national-connector 2>/dev/null || \
    docker stop "$NC_CONTAINER" 2>/dev/null || true

# Copy backup files
for db in "${RESTORE_FILES[@]}"; do
    log "Restoring: $db"
    cp "$SNAPSHOT_DIR/$db" "$SQLITE_DATA_DIR/$db"
    # Also copy WAL and SHM files if they exist in snapshot, otherwise remove stale ones
    for suffix in -wal -shm; do
        if [[ -f "$SNAPSHOT_DIR/${db}${suffix}" ]]; then
            cp "$SNAPSHOT_DIR/${db}${suffix}" "$SQLITE_DATA_DIR/${db}${suffix}"
        else
            rm -f "$SQLITE_DATA_DIR/${db}${suffix}"
        fi
    done
done

# Fix ownership to match the init container's chown
chown -R 10001:10001 "$SQLITE_DATA_DIR"

# Restart national-connector
log "Starting national-connector container..."
docker compose -f "$NC_DIR/docker-compose.yml" start national-connector 2>/dev/null || \
    docker start "$NC_CONTAINER" 2>/dev/null || true

log "SQLite restore completed successfully from: $SNAPSHOT_DIR"
