#!/usr/bin/env bash
# End-to-end test: SQLite snapshot cycle.
#
# SQLite has no point-in-time recovery between snapshots — each backup is a
# full copy and restoring goes back to exactly that copy. This test pins
# three properties:
#   1. A snapshot captures every row committed up to the moment.
#   2. Restoring an older snapshot discards writes made after it.
#   3. Restoring a newer snapshot brings those writes back.
#
# Everything runs in a temporary $NC_DIR + $EHDSI_BACKUP_DIR so the real
# data and backup directories are untouched.

# shellcheck source=SCRIPTDIR/../lib/common.sh
source "$(dirname "$0")/../lib/common.sh"

SCRIPT_DIR_ABS="$(cd "$(dirname "$0")" && pwd)"
SQLITE_SCRIPTS="$SCRIPT_DIR_ABS/../sqlite"
TEST_SCRIPTS="$SCRIPT_DIR_ABS"

if ! command -v sqlite3 &>/dev/null; then
    log "ERROR: sqlite3 is not installed on the host"
    exit 1
fi

# Isolated dirs. Re-export NC_DIR so child scripts (backup.sh, restore.sh,
# sentinel helpers) pick up the override when they source common.sh. Also
# point NC_CONTAINER at a bogus name so the restore script's stop/start
# fallbacks don't touch the real national-connector container.
TEST_ROOT=$(mktemp -d)
export NC_DIR="$TEST_ROOT/nc"
export EHDSI_BACKUP_DIR="$TEST_ROOT/backup"
export NC_CONTAINER="sqlite-test-no-such-container"
TARGET_DB="$NC_DIR/data/undo-db.sqlite"
mkdir -p "$NC_DIR/data"

RUN_ID=$(timestamp)
LABEL_A="A_${RUN_ID}"
LABEL_B="B_${RUN_ID}"
LABEL_C="C_${RUN_ID}"
FAILURES=0

# shellcheck disable=SC2317  # invoked via `trap cleanup EXIT`
cleanup() {
    rm -rf "$TEST_ROOT"
}
trap cleanup EXIT

assert_fail() {
    log "FAIL: $1"
    FAILURES=$((FAILURES + 1))
}

count_sentinel() {
    sqlite3 "$TARGET_DB" \
        "SELECT COUNT(*) FROM _sentinel WHERE label='$1';" 2>/dev/null \
        || echo 0
}

# ---------------------------------------------------------------------------
# Setup
# ---------------------------------------------------------------------------

log "Setup: seeding $TARGET_DB with real-looking schema"
sqlite3 "$TARGET_DB" <<'SQL'
CREATE TABLE IF NOT EXISTS undo_dispensation (
    cda_id_hash TEXT,
    timestamp TEXT,
    order_id TEXT,
    effectuation_id TEXT
);
INSERT INTO undo_dispensation VALUES ('hash1', '2026-01-01T00:00:00', 'ord1', 'eff1');
SQL

# ---------------------------------------------------------------------------
# Phase 1 — build snapshots
# ---------------------------------------------------------------------------

log "Step 1/7: Add sentinel A"
"$TEST_SCRIPTS/sqlite-add-sentinel.sh" "$LABEL_A"

log "Step 2/7: Take snapshot S1 (should capture A)"
"$SQLITE_SCRIPTS/backup.sh"
SNAPSHOT_1=$(find "$EHDSI_BACKUP_DIR/sqlite" -maxdepth 1 -mindepth 1 -type d | sort | tail -1)
log "  S1 = $SNAPSHOT_1"

# Ensure the next snapshot timestamp differs (backup dirs are named by the
# second; two backups within the same second would collide).
sleep 1

log "Step 3/7: Add sentinel B"
"$TEST_SCRIPTS/sqlite-add-sentinel.sh" "$LABEL_B"

log "Step 4/7: Take snapshot S2 (should capture A and B)"
"$SQLITE_SCRIPTS/backup.sh"
SNAPSHOT_2=$(find "$EHDSI_BACKUP_DIR/sqlite" -maxdepth 1 -mindepth 1 -type d | sort | tail -1)
log "  S2 = $SNAPSHOT_2"

if [[ "$SNAPSHOT_1" == "$SNAPSHOT_2" ]]; then
    log "ERROR: Both snapshots resolved to the same dir — timestamp collision"
    exit 1
fi

log "Step 5/7: Add sentinel C (after both snapshots — should never be restored)"
"$TEST_SCRIPTS/sqlite-add-sentinel.sh" "$LABEL_C"

# Confirm the live state has all three before we start restoring over it
A=$(count_sentinel "$LABEL_A")
B=$(count_sentinel "$LABEL_B")
C=$(count_sentinel "$LABEL_C")
log "  Live DB sentinels: A=$A B=$B C=$C (expect 1/1/1)"
[[ "$A" == "1" && "$B" == "1" && "$C" == "1" ]] \
    || assert_fail "Pre-restore state wrong: A=$A B=$B C=$C"

# ---------------------------------------------------------------------------
# Phase 2 — restore each snapshot and verify
# ---------------------------------------------------------------------------

log "Step 6/7: Restore S1 — expect A only"
"$SQLITE_SCRIPTS/restore.sh" "$SNAPSHOT_1" --yes

A=$(count_sentinel "$LABEL_A")
B=$(count_sentinel "$LABEL_B")
C=$(count_sentinel "$LABEL_C")
log "  After S1 restore: A=$A (expect 1)  B=$B (expect 0)  C=$C (expect 0)"
[[ "$A" == "1" ]] || assert_fail "A missing after S1 restore (got $A)"
[[ "$B" == "0" ]] || assert_fail "B leaked through S1 restore (got $B)"
[[ "$C" == "0" ]] || assert_fail "C leaked through S1 restore (got $C)"

log "Step 7/7: Restore S2 — expect A and B (not C)"
"$SQLITE_SCRIPTS/restore.sh" "$SNAPSHOT_2" --yes

A=$(count_sentinel "$LABEL_A")
B=$(count_sentinel "$LABEL_B")
C=$(count_sentinel "$LABEL_C")
log "  After S2 restore: A=$A (expect 1)  B=$B (expect 1)  C=$C (expect 0)"
[[ "$A" == "1" ]] || assert_fail "A missing after S2 restore (got $A)"
[[ "$B" == "1" ]] || assert_fail "B missing after S2 restore (got $B)"
[[ "$C" == "0" ]] || assert_fail "C leaked through S2 restore (got $C)"

echo ""
if (( FAILURES == 0 )); then
    log "SQLITE SNAPSHOT CYCLE TEST PASSED"
    exit 0
else
    log "$FAILURES CHECK(S) FAILED"
    exit 1
fi
