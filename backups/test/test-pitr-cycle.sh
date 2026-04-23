#!/usr/bin/env bash
# End-to-end Point-In-Time Recovery (PITR) test.
#
# Runs against $MYSQL_CONTAINER (the dev database) — this is DESTRUCTIVE:
# the test's full-restore step drops and recreates every database in
# $MYSQL_DATABASES. Only run in dev.
#
# Flow:
#   1. Sentinel A → source DB
#   2. Full backup                                 (captures A)
#   3. Sentinel B → source DB
#   4. Binlog backup                               (captures B's writes)
#   5. Record cutoff timestamp
#   6. Sentinel C → source DB                      (after cutoff)
#   7. Binlog backup                               (captures C's writes)
#   8. Restore full backup                         → expect only A
#   9. Restore incremental --stop-datetime=cutoff  → expect A and B, not C

source "$(dirname "$0")/../lib/common.sh"

RUN_ID=$(timestamp)
LABEL_A="A_${RUN_ID}"
LABEL_B="B_${RUN_ID}"
LABEL_C="C_${RUN_ID}"
FAILURES=0

SCRIPT_DIR_ABS="$(cd "$(dirname "$0")" && pwd)"
MYSQL_DIR="$SCRIPT_DIR_ABS/../mysql"
TEST_DIR="$SCRIPT_DIR_ABS"

ROOT_PASSWORD=""

cleanup() {
    log "Cleaning up test sentinels..."
    if [[ -n "$ROOT_PASSWORD" ]]; then
        docker exec "$MYSQL_CONTAINER" \
            mysql -u root -p"$ROOT_PASSWORD" \
            -e "DELETE FROM ehealth_properties.EHNCP_PROPERTY
                WHERE NAME IN ('_sentinel_${LABEL_A}',
                               '_sentinel_${LABEL_B}',
                               '_sentinel_${LABEL_C}');" 2>/dev/null || true
    fi
}
trap cleanup EXIT

assert_fail() {
    log "FAIL: $1"
    ((FAILURES++))
}

count_sentinel() {
    local label="$1"
    docker exec "$MYSQL_CONTAINER" \
        mysql -u root -p"$ROOT_PASSWORD" \
        --batch --skip-column-names \
        -e "SELECT COUNT(*) FROM ehealth_properties.EHNCP_PROPERTY
            WHERE NAME='_sentinel_${label}';" 2>/dev/null
}

assert_container_running "$MYSQL_CONTAINER"
ROOT_PASSWORD=$(read_mysql_password)

# ---------------------------------------------------------------------------
# Phase 1 — produce data + backups
# ---------------------------------------------------------------------------

log "Step 1/9: Adding sentinel A (label=$LABEL_A)"
"$TEST_DIR/add-sentinel.sh" "$LABEL_A"

log "Step 2/9: Running full backup"
"$MYSQL_DIR/full-backup.sh"
FULL_BACKUP=$(find "$BACKUP_ROOT/mysql/full" -maxdepth 1 -name '*.sql.gz' | sort | tail -1)
if [[ -z "$FULL_BACKUP" ]]; then
    log "ERROR: No full backup file produced"
    exit 1
fi
log "  Full backup: $FULL_BACKUP"

log "Step 3/9: Adding sentinel B (label=$LABEL_B) — should be recovered via binlog replay"
"$TEST_DIR/add-sentinel.sh" "$LABEL_B"

log "Step 4/9: Running binlog backup"
"$MYSQL_DIR/binlog-backup.sh"

# Breathing room — MySQL binlog event timestamps have 1-second resolution,
# and --stop-datetime is inclusive of matching seconds.
sleep 2
# Capture cutoff in the CONTAINER's timezone — mysqlbinlog interprets
# --stop-datetime in its own local timezone, which may differ from the host.
STOP_AT=$(docker exec "$MYSQL_CONTAINER" date '+%Y-%m-%d %H:%M:%S')
log "Step 5/9: Cutoff recorded at $STOP_AT (container-local time)"
sleep 2

log "Step 6/9: Adding sentinel C (label=$LABEL_C) — AFTER cutoff, should NOT be restored"
"$TEST_DIR/add-sentinel.sh" "$LABEL_C"

log "Step 7/9: Running binlog backup (captures C's writes on host)"
"$MYSQL_DIR/binlog-backup.sh"

# ---------------------------------------------------------------------------
# Phase 2 — restore and verify
# ---------------------------------------------------------------------------

log "Step 8/9: Restoring full backup → expect only sentinel A visible"
"$MYSQL_DIR/restore-full.sh" "$FULL_BACKUP" --yes

A=$(count_sentinel "$LABEL_A")
B=$(count_sentinel "$LABEL_B")
C=$(count_sentinel "$LABEL_C")
log "  After full restore — A=$A (expect 1)  B=$B (expect 0)  C=$C (expect 0)"
[[ "$A" == "1" ]] || assert_fail "A missing after full restore (got $A)"
[[ "$B" == "0" ]] || assert_fail "B leaked into full restore (got $B)"
[[ "$C" == "0" ]] || assert_fail "C leaked into full restore (got $C)"

log "Step 9/9: Replaying binlogs with --stop-datetime='$STOP_AT' → expect A and B, NOT C"
"$MYSQL_DIR/restore-incremental.sh" \
    --after "$FULL_BACKUP" \
    --stop-datetime "$STOP_AT"

A=$(count_sentinel "$LABEL_A")
B=$(count_sentinel "$LABEL_B")
C=$(count_sentinel "$LABEL_C")
log "  After incremental restore — A=$A (expect 1)  B=$B (expect 1)  C=$C (expect 0)"
[[ "$A" == "1" ]] || assert_fail "A missing after incremental restore (got $A)"
[[ "$B" == "1" ]] || assert_fail "B missing after incremental restore (got $B)"
[[ "$C" == "0" ]] || assert_fail "C was replayed past the cutoff (got $C)"

echo ""
if (( FAILURES == 0 )); then
    log "PITR CYCLE TEST PASSED"
    exit 0
else
    log "$FAILURES CHECK(S) FAILED"
    exit 1
fi
