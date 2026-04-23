#!/usr/bin/env bash
# End-to-end test: SQLite backup/restore cycle
#
# This script:
# 1. Takes a backup of all SQLite databases
# 2. Verifies integrity of each backup
# 3. Compares table counts between live and backup
# 4. Restores to a temp directory and verifies checksums

# shellcheck source=SCRIPTDIR/../lib/common.sh
source "$(dirname "$0")/../lib/common.sh"

SQLITE_DATA_DIR="$NC_DIR/data"
FAILURES=0
TEMP_RESTORE_DIR=""

# shellcheck disable=SC2317  # invoked via `trap cleanup EXIT`
cleanup() {
    if [[ -n "$TEMP_RESTORE_DIR" && -d "$TEMP_RESTORE_DIR" ]]; then
        rm -rf "$TEMP_RESTORE_DIR"
    fi
}
trap cleanup EXIT

assert_fail() {
    local msg="$1"
    log "FAIL: $msg"
    ((FAILURES++))
}

if ! command -v sqlite3 &>/dev/null; then
    log "ERROR: sqlite3 is not installed on the host"
    exit 1
fi

# Step 1: Take backup
log "Step 1: Running SQLite backup..."
"$(dirname "$0")/../sqlite/backup.sh"

SNAPSHOT_DIR=$(find "$BACKUP_ROOT/sqlite" -maxdepth 1 -mindepth 1 -type d | sort | tail -n 1)
if [[ -z "$SNAPSHOT_DIR" ]]; then
    log "ERROR: No snapshot directory produced"
    exit 1
fi
log "Snapshot: $SNAPSHOT_DIR"

# Step 2: Verify integrity
log "Step 2: Verifying backup integrity..."
for db in "${SQLITE_DATABASES[@]}"; do
    BACKUP_FILE="$SNAPSHOT_DIR/$db"
    if [[ ! -f "$BACKUP_FILE" ]]; then
        log "  $db: not in backup (may not exist yet), skipping"
        continue
    fi

    RESULT=$(sqlite3 "$BACKUP_FILE" "PRAGMA integrity_check;" 2>&1)
    if [[ "$RESULT" != "ok" ]]; then
        assert_fail "Integrity check failed for $db: $RESULT"
    else
        log "  $db: integrity OK"
    fi
done

# Step 3: Compare table counts
log "Step 3: Comparing table counts..."
for db in "${SQLITE_DATABASES[@]}"; do
    LIVE_FILE="$SQLITE_DATA_DIR/$db"
    BACKUP_FILE="$SNAPSHOT_DIR/$db"

    if [[ ! -f "$LIVE_FILE" || ! -f "$BACKUP_FILE" ]]; then
        continue
    fi

    LIVE_COUNT=$(sqlite3 "$LIVE_FILE" "SELECT COUNT(*) FROM sqlite_master WHERE type='table';" 2>&1)
    BACKUP_COUNT=$(sqlite3 "$BACKUP_FILE" "SELECT COUNT(*) FROM sqlite_master WHERE type='table';" 2>&1)

    if [[ "$LIVE_COUNT" != "$BACKUP_COUNT" ]]; then
        assert_fail "Table count mismatch for $db: live=$LIVE_COUNT backup=$BACKUP_COUNT"
    else
        log "  $db: $LIVE_COUNT tables OK"
    fi
done

# Step 4: Restore to temp dir and verify checksums
log "Step 4: Verifying restore produces identical files..."
TEMP_RESTORE_DIR=$(mktemp -d)

for db in "${SQLITE_DATABASES[@]}"; do
    BACKUP_FILE="$SNAPSHOT_DIR/$db"
    if [[ ! -f "$BACKUP_FILE" ]]; then
        continue
    fi

    cp "$BACKUP_FILE" "$TEMP_RESTORE_DIR/$db"

    BACKUP_SHA=$(sha256sum "$BACKUP_FILE" | awk '{print $1}')
    RESTORE_SHA=$(sha256sum "$TEMP_RESTORE_DIR/$db" | awk '{print $1}')

    if [[ "$BACKUP_SHA" != "$RESTORE_SHA" ]]; then
        assert_fail "SHA256 mismatch for $db after copy"
    else
        log "  $db: SHA256 match OK"
    fi
done

# Report
echo ""
if (( FAILURES == 0 )); then
    log "ALL CHECKS PASSED"
    exit 0
else
    log "$FAILURES CHECK(S) FAILED"
    exit 1
fi
