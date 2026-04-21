#!/usr/bin/env bash
# Run all backup/restore tests and report results

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
FAILURES=0

echo "=========================================="
echo "  EHDSI Backup/Restore Test Suite"
echo "=========================================="
echo ""

echo "--- MySQL Backup/Restore Cycle ---"
if "$SCRIPT_DIR/test-mysql-cycle.sh"; then
    echo "MySQL: PASSED"
else
    echo "MySQL: FAILED"
    ((FAILURES++))
fi

echo ""
echo "--- SQLite Backup/Restore Cycle ---"
if "$SCRIPT_DIR/test-sqlite-cycle.sh"; then
    echo "SQLite: PASSED"
else
    echo "SQLite: FAILED"
    ((FAILURES++))
fi

echo ""
echo "=========================================="
if (( FAILURES == 0 )); then
    echo "  ALL TESTS PASSED"
    echo "=========================================="
    exit 0
else
    echo "  $FAILURES TEST SUITE(S) FAILED"
    echo "=========================================="
    exit 1
fi
