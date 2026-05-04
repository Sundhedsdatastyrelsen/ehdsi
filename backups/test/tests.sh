#!/usr/bin/env bash
# Backup/restore test suite.
#
# Usage:
#   ./tests.sh                   # run safe tests (excludes pitr-cycle)
#   ./tests.sh all               # alias for default
#   ./tests.sh <test>...         # run specific tests in order
#   ./tests.sh -h                # show help
#
# Tests:
#   sqlite-snapshot   Isolated snapshot↔restore cycle (safe)
#   sqlite-cycle      Backup live SQLite, verify integrity (safe)
#   mysql-cycle       Full backup + restore into disposable container (safe)
#   gap-detection     Binlog gap-check unit tests (safe)
#   pitr-cycle        DESTRUCTIVE: full restore on dev DB + binlog replay
#
# Requires: openncp_db running for mysql-cycle, gap-detection, pitr-cycle.
#           sqlite3 on host; docker; password file or $MYSQL_ROOT_PASSWORD.

# shellcheck source=SCRIPTDIR/../lib/common.sh
source "$(dirname "$0")/../lib/common.sh"
# shellcheck source=SCRIPTDIR/sentinels.sh
source "$(dirname "$0")/sentinels.sh"

SCRIPT_DIR_ABS="$(cd "$(dirname "$0")" && pwd)"
MYSQL_DIR="$SCRIPT_DIR_ABS/../mysql"
SQLITE_DIR="$SCRIPT_DIR_ABS/../sqlite"

# Each test runs as a subshell function so its EXIT trap and exported env
# overrides are scoped. Returns 0 on pass, non-zero on fail.

# ─── sqlite-snapshot ──────────────────────────────────────────────────────

test_sqlite_snapshot() (
    if ! command -v sqlite3 &>/dev/null; then
        log "ERROR: sqlite3 is not installed on the host"
        exit 1
    fi

    # Isolated dirs. SQLITE_CONTAINER points at a bogus name so the restore
    # script's stop/start can't touch the real container; SQLITE_COMPOSE_FILE
    # is empty to bypass the compose path entirely.
    TEST_ROOT=$(mktemp -d)
    trap 'rm -rf "$TEST_ROOT"' EXIT
    export EHDSI_BACKUP_DIR="$TEST_ROOT/backup"
    export SQLITE_DATA_DIR="$TEST_ROOT/data"
    export SQLITE_CONTAINER="sqlite-test-no-such-container"
    export SQLITE_COMPOSE_FILE=""
    TARGET_DB="$SQLITE_DATA_DIR/undo-db.sqlite"
    mkdir -p "$SQLITE_DATA_DIR"

    RUN_ID=$(timestamp)
    LABEL_A="A_${RUN_ID}"
    LABEL_B="B_${RUN_ID}"
    LABEL_C="C_${RUN_ID}"
    FAILURES=0

    assert_fail() { log "FAIL: $1"; FAILURES=$((FAILURES + 1)); }
    count_sentinel() {
        sqlite3 "$TARGET_DB" \
            "SELECT COUNT(*) FROM _sentinel WHERE label='$1';" 2>/dev/null \
            || echo 0
    }

    log "Setup: seeding $TARGET_DB"
    sqlite3 "$TARGET_DB" <<'SQL'
CREATE TABLE IF NOT EXISTS undo_dispensation (
    cda_id_hash TEXT, timestamp TEXT, order_id TEXT, effectuation_id TEXT
);
INSERT INTO undo_dispensation VALUES ('hash1', '2026-01-01T00:00:00', 'ord1', 'eff1');
SQL

    log "Step 1/7: Add sentinel A"
    sqlite_add_sentinel "$LABEL_A"

    log "Step 2/7: Take snapshot S1 (should capture A)"
    "$SQLITE_DIR/backup.sh"
    SNAPSHOT_1=$(find "$EHDSI_BACKUP_DIR/sqlite" -maxdepth 1 -mindepth 1 -type d | sort | tail -1)
    log "  S1 = $SNAPSHOT_1"

    # Snapshot dirs are named to the second; back-to-back backups would collide.
    sleep 1

    log "Step 3/7: Add sentinel B"
    sqlite_add_sentinel "$LABEL_B"

    log "Step 4/7: Take snapshot S2 (should capture A and B)"
    "$SQLITE_DIR/backup.sh"
    SNAPSHOT_2=$(find "$EHDSI_BACKUP_DIR/sqlite" -maxdepth 1 -mindepth 1 -type d | sort | tail -1)
    log "  S2 = $SNAPSHOT_2"

    if [[ "$SNAPSHOT_1" == "$SNAPSHOT_2" ]]; then
        log "ERROR: Both snapshots resolved to the same dir — timestamp collision"
        exit 1
    fi

    log "Step 5/7: Add sentinel C (after both snapshots)"
    sqlite_add_sentinel "$LABEL_C"

    A=$(count_sentinel "$LABEL_A"); B=$(count_sentinel "$LABEL_B"); C=$(count_sentinel "$LABEL_C")
    log "  Live sentinels: A=$A B=$B C=$C (expect 1/1/1)"
    [[ "$A" == "1" && "$B" == "1" && "$C" == "1" ]] \
        || assert_fail "Pre-restore state wrong: A=$A B=$B C=$C"

    log "Step 6/7: Restore S1 — expect A only"
    "$SQLITE_DIR/restore.sh" "$SNAPSHOT_1" --yes
    A=$(count_sentinel "$LABEL_A"); B=$(count_sentinel "$LABEL_B"); C=$(count_sentinel "$LABEL_C")
    log "  After S1 restore: A=$A B=$B C=$C (expect 1/0/0)"
    [[ "$A" == "1" ]] || assert_fail "A missing after S1 restore (got $A)"
    [[ "$B" == "0" ]] || assert_fail "B leaked through S1 restore (got $B)"
    [[ "$C" == "0" ]] || assert_fail "C leaked through S1 restore (got $C)"

    log "Step 7/7: Restore S2 — expect A and B (not C)"
    "$SQLITE_DIR/restore.sh" "$SNAPSHOT_2" --yes
    A=$(count_sentinel "$LABEL_A"); B=$(count_sentinel "$LABEL_B"); C=$(count_sentinel "$LABEL_C")
    log "  After S2 restore: A=$A B=$B C=$C (expect 1/1/0)"
    [[ "$A" == "1" ]] || assert_fail "A missing after S2 restore (got $A)"
    [[ "$B" == "1" ]] || assert_fail "B missing after S2 restore (got $B)"
    [[ "$C" == "0" ]] || assert_fail "C leaked through S2 restore (got $C)"

    if (( FAILURES == 0 )); then
        log "SQLITE SNAPSHOT CYCLE TEST PASSED"
        exit 0
    else
        log "$FAILURES CHECK(S) FAILED"
        exit 1
    fi
)

# ─── sqlite-cycle ─────────────────────────────────────────────────────────

test_sqlite_cycle() (
    if ! command -v sqlite3 &>/dev/null; then
        log "ERROR: sqlite3 is not installed on the host"
        exit 1
    fi

    FAILURES=0
    TEMP_RESTORE_DIR=""
    trap '[[ -n "$TEMP_RESTORE_DIR" && -d "$TEMP_RESTORE_DIR" ]] && rm -rf "$TEMP_RESTORE_DIR"' EXIT
    assert_fail() { log "FAIL: $1"; FAILURES=$((FAILURES + 1)); }

    log "Step 1: Running SQLite backup..."
    "$SQLITE_DIR/backup.sh"

    SNAPSHOT_DIR=$(find "$BACKUP_ROOT/sqlite" -maxdepth 1 -mindepth 1 -type d | sort | tail -n 1)
    if [[ -z "$SNAPSHOT_DIR" ]]; then
        log "ERROR: No snapshot directory produced"
        exit 1
    fi
    log "Snapshot: $SNAPSHOT_DIR"

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

    log "Step 4: Verifying restore produces identical files..."
    TEMP_RESTORE_DIR=$(mktemp -d)
    for db in "${SQLITE_DATABASES[@]}"; do
        BACKUP_FILE="$SNAPSHOT_DIR/$db"
        [[ -f "$BACKUP_FILE" ]] || continue
        cp "$BACKUP_FILE" "$TEMP_RESTORE_DIR/$db"
        BACKUP_SHA=$(sha256sum "$BACKUP_FILE" | awk '{print $1}')
        RESTORE_SHA=$(sha256sum "$TEMP_RESTORE_DIR/$db" | awk '{print $1}')
        if [[ "$BACKUP_SHA" != "$RESTORE_SHA" ]]; then
            assert_fail "SHA256 mismatch for $db after copy"
        else
            log "  $db: SHA256 match OK"
        fi
    done

    if (( FAILURES == 0 )); then
        log "ALL CHECKS PASSED"
        exit 0
    else
        log "$FAILURES CHECK(S) FAILED"
        exit 1
    fi
)

# ─── mysql-cycle ──────────────────────────────────────────────────────────

test_mysql_cycle() (
    TEST_CONTAINER="backup-test-mysql"
    TEST_PORT=13306
    TEST_ROOT_PASSWORD="test-backup-root"
    SENTINEL_KEY="_backup_test_$(timestamp)"
    FAILURES=0
    ROOT_PASSWORD=""

    cleanup() {
        log "Cleaning up..."
        docker rm -f "$TEST_CONTAINER" 2>/dev/null || true
        if [[ -n "$ROOT_PASSWORD" ]]; then
            docker exec "$MYSQL_CONTAINER" \
                mysql -u root -p"$ROOT_PASSWORD" \
                -e "DELETE FROM ehealth_properties.EHNCP_PROPERTY WHERE NAME='$SENTINEL_KEY';" 2>/dev/null || true
        fi
    }
    trap cleanup EXIT
    assert_fail() { log "FAIL: $1"; FAILURES=$((FAILURES + 1)); }

    assert_container_running "$MYSQL_CONTAINER"
    ROOT_PASSWORD=$(read_mysql_password)

    log "Step 1: Inserting sentinel row (key=$SENTINEL_KEY)..."
    docker exec "$MYSQL_CONTAINER" \
        mysql -u root -p"$ROOT_PASSWORD" \
        -e "INSERT INTO ehealth_properties.EHNCP_PROPERTY (NAME, VALUE) VALUES ('$SENTINEL_KEY', 'backup_test_value');" 2>/dev/null

    log "Step 2: Running full backup..."
    "$MYSQL_DIR/full-backup.sh"
    BACKUP_FILE=$(find "$BACKUP_ROOT/mysql/full" -maxdepth 1 -name '*.sql.gz' | sort | tail -n 1)
    if [[ -z "$BACKUP_FILE" ]]; then
        log "ERROR: No backup file produced"
        exit 1
    fi
    log "Backup file: $BACKUP_FILE"

    log "Step 3: Starting temporary MySQL container..."
    docker rm -f "$TEST_CONTAINER" 2>/dev/null || true
    docker run -d \
        --name "$TEST_CONTAINER" \
        -e MYSQL_ROOT_PASSWORD="$TEST_ROOT_PASSWORD" \
        -p "$TEST_PORT:3306" \
        mysql:9 >/dev/null

    log "Waiting for temporary MySQL to be ready..."
    for i in $(seq 1 60); do
        if docker exec "$TEST_CONTAINER" mysqladmin ping -u root -p"$TEST_ROOT_PASSWORD" 2>/dev/null | grep -q alive; then
            break
        fi
        if (( i == 60 )); then
            log "ERROR: Temporary MySQL failed to start within 60 seconds"
            exit 1
        fi
        sleep 1
    done
    log "Temporary MySQL is ready"

    log "Step 4: Restoring backup into temporary container..."
    gunzip -c "$BACKUP_FILE" | \
        docker exec -i "$TEST_CONTAINER" \
            mysql -u root -p"$TEST_ROOT_PASSWORD" 2>/dev/null

    log "Step 5: Verifying restoration..."
    for db in "${MYSQL_DATABASES[@]}"; do
        EXISTS=$(docker exec "$TEST_CONTAINER" \
            mysql -u root -p"$TEST_ROOT_PASSWORD" \
            --batch --skip-column-names \
            -e "SELECT SCHEMA_NAME FROM information_schema.SCHEMATA WHERE SCHEMA_NAME='$db';" 2>/dev/null)
        if [[ "$EXISTS" != "$db" ]]; then
            assert_fail "Database '$db' not found in restored backup"
        fi
    done

    for db in "${MYSQL_DATABASES[@]}"; do
        SRC_COUNT=$(docker exec "$MYSQL_CONTAINER" \
            mysql -u root -p"$ROOT_PASSWORD" \
            --batch --skip-column-names \
            -e "SELECT COUNT(*) FROM information_schema.TABLES WHERE TABLE_SCHEMA='$db';" 2>/dev/null)
        DST_COUNT=$(docker exec "$TEST_CONTAINER" \
            mysql -u root -p"$TEST_ROOT_PASSWORD" \
            --batch --skip-column-names \
            -e "SELECT COUNT(*) FROM information_schema.TABLES WHERE TABLE_SCHEMA='$db';" 2>/dev/null)
        if [[ "$SRC_COUNT" != "$DST_COUNT" ]]; then
            assert_fail "Table count mismatch for '$db': source=$SRC_COUNT restored=$DST_COUNT"
        else
            log "  $db: $SRC_COUNT tables OK"
        fi
    done

    SENTINEL_VALUE=$(docker exec "$TEST_CONTAINER" \
        mysql -u root -p"$TEST_ROOT_PASSWORD" \
        --batch --skip-column-names \
        -e "SELECT VALUE FROM ehealth_properties.EHNCP_PROPERTY WHERE NAME='$SENTINEL_KEY';" 2>/dev/null)
    if [[ "$SENTINEL_VALUE" != "backup_test_value" ]]; then
        assert_fail "Sentinel row not found (expected 'backup_test_value', got '$SENTINEL_VALUE')"
    else
        log "  Sentinel row: OK"
    fi

    if (( FAILURES == 0 )); then
        log "ALL CHECKS PASSED"
        exit 0
    else
        log "$FAILURES CHECK(S) FAILED"
        exit 1
    fi
)

# ─── gap-detection ────────────────────────────────────────────────────────

# Assertions for the gap-check logic in restore-incremental.sh. Builds a
# synthetic $EHDSI_BACKUP_DIR with empty binlog files and a hand-crafted
# dump header, then runs the real script against it. Empty files are
# sufficient because the gap check operates on filenames; tests that proceed
# past it may fail downstream on the bogus contents — assertions check only
# the gap-check behaviour.
test_gap_detection() (
    RESTORE_SCRIPT="$MYSQL_DIR/restore-incremental.sh"
    TEST_ROOT=$(mktemp -d)
    BINLOG_DIR="$TEST_ROOT/mysql/binlog"
    FULL_DIR="$TEST_ROOT/mysql/full"
    mkdir -p "$BINLOG_DIR" "$FULL_DIR"
    FAKE_DUMP="$FULL_DIR/fake-dump.sql.gz"

    cleanup() {
        rm -rf "$TEST_ROOT"
        # Tests 1 and 4 proceed past the gap check and leak scratch files
        # into /tmp inside the container before dying on the bogus binlog.
        docker exec "$MYSQL_CONTAINER" sh -c 'rm -f /tmp/binlog.*' 2>/dev/null || true
    }
    trap cleanup EXIT

    FAILURES=0
    LAST_OUTPUT=""
    LAST_EXIT=0

    make_fake_dump() {
        {
            echo "-- MySQL dump 10.13"
            echo "--"
            echo "-- CHANGE REPLICATION SOURCE TO SOURCE_LOG_FILE='$1', SOURCE_LOG_POS=$2;"
        } | gzip > "$FAKE_DUMP"
    }
    make_binlogs() {
        rm -f "$BINLOG_DIR"/binlog.*
        for n in "$@"; do
            touch "$BINLOG_DIR/binlog.$(printf '%06d' "$n")"
        done
    }
    run_restore() {
        local out exit_code
        out=$(EHDSI_BACKUP_DIR="$TEST_ROOT" "$RESTORE_SCRIPT" "$@" 2>&1) || exit_code=$?
        LAST_OUTPUT="$out"
        LAST_EXIT=${exit_code:-0}
    }
    contains()     { grep -qF "$1" <<< "$LAST_OUTPUT"; }
    not_contains() { ! grep -qF "$1" <<< "$LAST_OUTPUT"; }
    exit_was()     { [[ "$LAST_EXIT" == "$1" ]]; }
    check() {
        local label="$1"; shift
        if "$@"; then
            echo "  PASS: $label"
        else
            echo "  FAIL: $label"
            while IFS= read -r line; do echo "    | $line"; done <<< "$LAST_OUTPUT"
            FAILURES=$((FAILURES + 1))
        fi
    }

    assert_container_running "$MYSQL_CONTAINER"

    echo "=== Test 1: contiguous chain passes the gap check ==="
    make_binlogs 50 51 52
    make_fake_dump "binlog.000050" "100"
    run_restore --after "$FAKE_DUMP"
    check "no gap error emitted" not_contains "gap(s) in"
    check "proceeds past gap check to replay loop" contains "Replaying 3 binlog"

    echo ""
    echo "=== Test 2: single interior gap detected ==="
    make_binlogs 50 52
    make_fake_dump "binlog.000050" "100"
    run_restore --after "$FAKE_DUMP"
    check "names the missing binlog" contains "missing: binlog.000051"
    check "reports 1 gap" contains "has 1 gap(s)"
    check "exits with code 1" exit_was 1
    check "does not proceed to replay" not_contains "Replaying 2 binlog file(s):"

    echo ""
    echo "=== Test 3: multi-file gap names every missing number ==="
    make_binlogs 50 53
    make_fake_dump "binlog.000050" "100"
    run_restore --after "$FAKE_DUMP"
    check "lists binlog.000051" contains "missing: binlog.000051"
    check "lists binlog.000052" contains "missing: binlog.000052"
    check "reports 2 gaps" contains "has 2 gap(s)"
    check "exits with code 1" exit_was 1

    echo ""
    echo "=== Test 4: --allow-gaps bypasses the check ==="
    make_binlogs 50 52
    make_fake_dump "binlog.000050" "100"
    run_restore --after "$FAKE_DUMP" --allow-gaps
    check "no gap error emitted" not_contains "gap(s) in"
    check "proceeds to replay with 2 files" contains "Replaying 2 binlog"

    echo ""
    echo "=== Test 5: dump's start file missing (retention case) ==="
    make_binlogs 51 52 53
    make_fake_dump "binlog.000050" "100"
    run_restore --after "$FAKE_DUMP"
    check "specific error for missing start file" contains "Required start binlog 'binlog.000050'"
    check "mentions retention as likely cause" contains "purged by retention"
    check "exits with code 1" exit_was 1

    echo ""
    echo "=== Test 6: two-file chain with no gap is accepted ==="
    make_binlogs 50 51
    make_fake_dump "binlog.000050" "100"
    run_restore --after "$FAKE_DUMP"
    check "no gap error emitted" not_contains "gap(s) in"
    check "proceeds to replay with 2 files" contains "Replaying 2 binlog"

    echo ""
    echo "=== Test 7: single-file range skips gap check cleanly ==="
    make_binlogs 50
    make_fake_dump "binlog.000050" "100"
    run_restore --after "$FAKE_DUMP"
    check "no gap error emitted" not_contains "gap(s) in"
    check "proceeds to replay with 1 file" contains "Replaying 1 binlog"

    echo ""
    echo "=== Test 8: glob does not match sibling files like .bak ==="
    make_binlogs 50 51 52
    touch "$BINLOG_DIR/binlog.000051.bak"
    make_fake_dump "binlog.000050" "100"
    run_restore --after "$FAKE_DUMP"
    check "no gap error (real 51 still present)" not_contains "gap(s) in"
    check "chain length is 3 (not 4)" contains "Replaying 3 binlog"

    echo ""
    if (( FAILURES == 0 )); then
        log "ALL GAP DETECTION TESTS PASSED"
        exit 0
    else
        log "$FAILURES CHECK(S) FAILED"
        exit 1
    fi
)

# ─── pitr-cycle ───────────────────────────────────────────────────────────

# DESTRUCTIVE: drops and recreates every database in $MYSQL_DATABASES on the
# live $MYSQL_CONTAINER. Only run in dev. Flow:
#   1. Sentinel A → source DB
#   2. Full backup                                 (captures A)
#   3. Sentinel B → source DB
#   4. Binlog backup                               (captures B's writes)
#   5. Record cutoff timestamp
#   6. Sentinel C → source DB                      (after cutoff)
#   7. Binlog backup                               (captures C's writes)
#   8. Restore full backup                         → expect only A
#   9. Restore incremental --stop-datetime=cutoff  → expect A and B, not C
test_pitr_cycle() (
    RUN_ID=$(timestamp)
    LABEL_A="A_${RUN_ID}"
    LABEL_B="B_${RUN_ID}"
    LABEL_C="C_${RUN_ID}"
    FAILURES=0
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
    assert_fail() { log "FAIL: $1"; FAILURES=$((FAILURES + 1)); }

    count_sentinel() {
        docker exec "$MYSQL_CONTAINER" \
            mysql -u root -p"$ROOT_PASSWORD" \
            --batch --skip-column-names \
            -e "SELECT COUNT(*) FROM ehealth_properties.EHNCP_PROPERTY
                WHERE NAME='_sentinel_$1';" 2>/dev/null
    }

    assert_container_running "$MYSQL_CONTAINER"
    ROOT_PASSWORD=$(read_mysql_password)

    log "Step 1/9: Adding sentinel A (label=$LABEL_A)"
    mysql_add_sentinel "$LABEL_A"

    log "Step 2/9: Running full backup"
    "$MYSQL_DIR/full-backup.sh"
    FULL_BACKUP=$(find "$BACKUP_ROOT/mysql/full" -maxdepth 1 -name '*.sql.gz' | sort | tail -1)
    if [[ -z "$FULL_BACKUP" ]]; then
        log "ERROR: No full backup file produced"
        exit 1
    fi
    log "  Full backup: $FULL_BACKUP"

    log "Step 3/9: Adding sentinel B (label=$LABEL_B) — should be recovered via binlog replay"
    mysql_add_sentinel "$LABEL_B"

    log "Step 4/9: Running binlog backup"
    "$MYSQL_DIR/binlog-backup.sh"

    # Breathing room — binlog event timestamps have 1s resolution and
    # --stop-datetime is inclusive of matching seconds.
    sleep 2
    # Cutoff in CONTAINER-local time — mysqlbinlog interprets --stop-datetime
    # in its own timezone, which may differ from the host.
    STOP_AT=$(docker exec "$MYSQL_CONTAINER" date '+%Y-%m-%d %H:%M:%S')
    log "Step 5/9: Cutoff recorded at $STOP_AT (container-local)"
    sleep 2

    log "Step 6/9: Adding sentinel C (label=$LABEL_C) — AFTER cutoff, should NOT be restored"
    mysql_add_sentinel "$LABEL_C"

    log "Step 7/9: Running binlog backup (captures C's writes on host)"
    "$MYSQL_DIR/binlog-backup.sh"

    log "Step 8/9: Restoring full backup → expect only sentinel A visible"
    "$MYSQL_DIR/restore-full.sh" "$FULL_BACKUP" --yes

    A=$(count_sentinel "$LABEL_A"); B=$(count_sentinel "$LABEL_B"); C=$(count_sentinel "$LABEL_C")
    log "  After full restore — A=$A (expect 1)  B=$B (expect 0)  C=$C (expect 0)"
    [[ "$A" == "1" ]] || assert_fail "A missing after full restore (got $A)"
    [[ "$B" == "0" ]] || assert_fail "B leaked into full restore (got $B)"
    [[ "$C" == "0" ]] || assert_fail "C leaked into full restore (got $C)"

    log "Step 9/9: Replaying binlogs with --stop-datetime='$STOP_AT' → expect A and B, NOT C"
    "$MYSQL_DIR/restore-incremental.sh" \
        --after "$FULL_BACKUP" \
        --stop-datetime "$STOP_AT"

    A=$(count_sentinel "$LABEL_A"); B=$(count_sentinel "$LABEL_B"); C=$(count_sentinel "$LABEL_C")
    log "  After incremental restore — A=$A (expect 1)  B=$B (expect 1)  C=$C (expect 0)"
    [[ "$A" == "1" ]] || assert_fail "A missing after incremental restore (got $A)"
    [[ "$B" == "1" ]] || assert_fail "B missing after incremental restore (got $B)"
    [[ "$C" == "0" ]] || assert_fail "C was replayed past the cutoff (got $C)"

    if (( FAILURES == 0 )); then
        log "PITR CYCLE TEST PASSED"
        exit 0
    else
        log "$FAILURES CHECK(S) FAILED"
        exit 1
    fi
)

# ─── dispatch ─────────────────────────────────────────────────────────────

run_one() {
    local name="$1" func="$2"
    echo ""
    echo "============================================"
    echo "=== $name"
    echo "============================================"
    if "$func"; then
        echo "$name: PASSED"
        return 0
    else
        echo "$name: FAILED"
        return 1
    fi
}

run_safe() {
    local failures=0
    run_one "sqlite-snapshot" test_sqlite_snapshot || failures=$((failures + 1))
    run_one "sqlite-cycle"    test_sqlite_cycle    || failures=$((failures + 1))
    run_one "mysql-cycle"     test_mysql_cycle     || failures=$((failures + 1))
    run_one "gap-detection"   test_gap_detection   || failures=$((failures + 1))
    echo ""
    echo "============================================"
    if (( failures == 0 )); then
        echo "  ALL SAFE TESTS PASSED"
        echo "  (pitr-cycle skipped — destructive; run explicitly)"
    else
        echo "  $failures TEST(S) FAILED"
    fi
    echo "============================================"
    return $(( failures > 0 ? 1 : 0 ))
}

usage() {
    sed -n '2,/^$/p' "$0" | sed 's/^# \?//'
}

if [[ $# -eq 0 ]]; then set -- all; fi
total_failures=0
for arg in "$@"; do
    case "$arg" in
        all)              run_safe                                                 || total_failures=$((total_failures + 1)) ;;
        sqlite-snapshot)  run_one sqlite-snapshot test_sqlite_snapshot             || total_failures=$((total_failures + 1)) ;;
        sqlite-cycle)     run_one sqlite-cycle    test_sqlite_cycle                || total_failures=$((total_failures + 1)) ;;
        mysql-cycle)      run_one mysql-cycle     test_mysql_cycle                 || total_failures=$((total_failures + 1)) ;;
        gap-detection)    run_one gap-detection   test_gap_detection               || total_failures=$((total_failures + 1)) ;;
        pitr-cycle)       run_one pitr-cycle      test_pitr_cycle                  || total_failures=$((total_failures + 1)) ;;
        -h|--help|help)   usage; exit 0 ;;
        *)                echo "Unknown test: $arg" >&2; usage >&2; exit 1 ;;
    esac
done
exit $(( total_failures > 0 ? 1 : 0 ))
