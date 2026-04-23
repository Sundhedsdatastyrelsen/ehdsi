#!/usr/bin/env bash
# Automated tests for gap-detection logic in restore-incremental.sh.
#
# Builds a synthetic $EHDSI_BACKUP_DIR containing empty binlog files and a
# hand-crafted dump header, then runs the real script against it. The gap
# check runs purely on filenames, so empty files are sufficient. Tests that
# proceed past the check may fail downstream on the bogus files — we assert
# only on the gap-check behaviour.

source "$(dirname "$0")/../lib/common.sh"

SCRIPT_DIR_ABS="$(cd "$(dirname "$0")" && pwd)"
RESTORE_SCRIPT="$(cd "$SCRIPT_DIR_ABS/../mysql" && pwd)/restore-incremental.sh"

TEST_ROOT=$(mktemp -d)
BINLOG_DIR="$TEST_ROOT/mysql/binlog"
FULL_DIR="$TEST_ROOT/mysql/full"
mkdir -p "$BINLOG_DIR" "$FULL_DIR"
FAKE_DUMP="$FULL_DIR/fake-dump.sql.gz"

cleanup() {
    rm -rf "$TEST_ROOT"
    # Tests 1 and 4 proceed past the gap check and leak scratch files into
    # /tmp inside the container before dying on the bogus binlog contents.
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

contains() { grep -qF "$1" <<< "$LAST_OUTPUT"; }
not_contains() { ! grep -qF "$1" <<< "$LAST_OUTPUT"; }
exit_was() { [[ "$LAST_EXIT" == "$1" ]]; }

check() {
    local label="$1"; shift
    if "$@"; then
        echo "  PASS: $label"
    else
        echo "  FAIL: $label"
        echo "$LAST_OUTPUT" | sed 's/^/    | /'
        FAILURES=$((FAILURES + 1))
    fi
}

assert_container_running "$MYSQL_CONTAINER"

# ---------------------------------------------------------------------------

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
# The marker "Replaying N binlog file(s):" prints only if the gap check
# passed and the replay loop was about to start.
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
touch "$BINLOG_DIR/binlog.000051.bak"  # should NOT count as binlog.000051
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
