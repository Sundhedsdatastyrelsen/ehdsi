#!/usr/bin/env bash
# End-to-end test: MySQL full backup/restore cycle
#
# This script:
# 1. Inserts a sentinel row into the live database
# 2. Takes a full backup
# 3. Spins up a temporary MySQL container
# 4. Restores the backup into it
# 5. Verifies databases, tables, and sentinel row
# 6. Cleans up

# shellcheck source=SCRIPTDIR/../lib/common.sh
source "$(dirname "$0")/../lib/common.sh"

TEST_CONTAINER="backup-test-mysql"
TEST_PORT=13306
TEST_ROOT_PASSWORD="test-backup-root"
SENTINEL_KEY="_backup_test_$(timestamp)"
FAILURES=0

# shellcheck disable=SC2317  # invoked via `trap cleanup EXIT`
cleanup() {
    log "Cleaning up..."
    docker rm -f "$TEST_CONTAINER" 2>/dev/null || true

    # Remove sentinel row from source
    if [[ -n "${ROOT_PASSWORD:-}" ]]; then
        docker exec "$MYSQL_CONTAINER" \
            mysql -u root -p"$ROOT_PASSWORD" \
            -e "DELETE FROM ehealth_properties.EHNCP_PROPERTY WHERE NAME='$SENTINEL_KEY';" 2>/dev/null || true
    fi
}
trap cleanup EXIT

assert_fail() {
    local msg="$1"
    log "FAIL: $msg"
    ((FAILURES++))
}

assert_container_running "$MYSQL_CONTAINER"

ROOT_PASSWORD=$(read_secret "$NCP_DIR/db_root_password.txt")

# Step 1: Insert sentinel row
log "Step 1: Inserting sentinel row (key=$SENTINEL_KEY)..."
docker exec "$MYSQL_CONTAINER" \
    mysql -u root -p"$ROOT_PASSWORD" \
    -e "INSERT INTO ehealth_properties.EHNCP_PROPERTY (NAME, VALUE) VALUES ('$SENTINEL_KEY', 'backup_test_value');" 2>/dev/null

# Step 2: Take full backup
log "Step 2: Running full backup..."
"$(dirname "$0")/../mysql/full-backup.sh"

BACKUP_FILE=$(find "$BACKUP_ROOT/mysql/full" -maxdepth 1 -name '*.sql.gz' | sort | tail -n 1)
if [[ -z "$BACKUP_FILE" ]]; then
    log "ERROR: No backup file produced"
    exit 1
fi
log "Backup file: $BACKUP_FILE"

# Step 3: Spin up temporary MySQL container
log "Step 3: Starting temporary MySQL container..."
docker rm -f "$TEST_CONTAINER" 2>/dev/null || true
docker run -d \
    --name "$TEST_CONTAINER" \
    -e MYSQL_ROOT_PASSWORD="$TEST_ROOT_PASSWORD" \
    -p "$TEST_PORT:3306" \
    mysql:9

# Wait for MySQL to be ready
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

# Step 4: Restore backup into temporary container
log "Step 4: Restoring backup into temporary container..."
gunzip -c "$BACKUP_FILE" | \
    docker exec -i "$TEST_CONTAINER" \
        mysql -u root -p"$TEST_ROOT_PASSWORD" 2>/dev/null

# Step 5: Verify
log "Step 5: Verifying restoration..."

# Check all databases exist
for db in "${MYSQL_DATABASES[@]}"; do
    EXISTS=$(docker exec "$TEST_CONTAINER" \
        mysql -u root -p"$TEST_ROOT_PASSWORD" \
        --batch --skip-column-names \
        -e "SELECT SCHEMA_NAME FROM information_schema.SCHEMATA WHERE SCHEMA_NAME='$db';" 2>/dev/null)
    if [[ "$EXISTS" != "$db" ]]; then
        assert_fail "Database '$db' not found in restored backup"
    fi
done

# Compare table counts between source and restored
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

# Check sentinel row
SENTINEL_VALUE=$(docker exec "$TEST_CONTAINER" \
    mysql -u root -p"$TEST_ROOT_PASSWORD" \
    --batch --skip-column-names \
    -e "SELECT VALUE FROM ehealth_properties.EHNCP_PROPERTY WHERE NAME='$SENTINEL_KEY';" 2>/dev/null)

if [[ "$SENTINEL_VALUE" != "backup_test_value" ]]; then
    assert_fail "Sentinel row not found in restored backup (expected 'backup_test_value', got '$SENTINEL_VALUE')"
else
    log "  Sentinel row: OK"
fi

# Report
echo ""
if (( FAILURES == 0 )); then
    log "ALL CHECKS PASSED"
    exit 0
else
    log "$FAILURES CHECK(S) FAILED"
    exit 1
fi
