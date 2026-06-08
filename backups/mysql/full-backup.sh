#!/usr/bin/env bash

# shellcheck source=SCRIPTDIR/../lib/common.sh
source "$(dirname "$0")/../lib/common.sh"

BACKUP_DIR="$BACKUP_ROOT/mysql/full"
ensure_dir "$BACKUP_DIR"

assert_container_running "$MYSQL_CONTAINER"

ROOT_PASSWORD=$(read_mysql_password)
TS=$(timestamp)
OUTPUT_FILE="$BACKUP_DIR/mysql-full-${TS}.sql.gz"

log "Starting full MySQL backup..."
log "Databases: ${MYSQL_DATABASES[*]}"

# --source-data=2 embeds the binlog coordinate as a comment in the dump,
# which restore-incremental.sh later parses to find the PITR replay start.
# When binary logging is disabled, fall back to a plain dump — PITR is then
# unavailable but the full snapshot still restores cleanly.
mysqldump_args=(--user=root --password="$ROOT_PASSWORD" --single-transaction --routines --triggers --events)
if is_binary_logging_enabled "$MYSQL_CONTAINER" "$ROOT_PASSWORD"; then
    mysqldump_args+=(--source-data=2)
else
    log "Binary logging disabled — producing dump without PITR coordinate"
fi
mysqldump_args+=(--databases "${MYSQL_DATABASES[@]}")

docker exec "$MYSQL_CONTAINER" mysqldump "${mysqldump_args[@]}" \
    | gzip > "$OUTPUT_FILE"

if ! gzip --test "$OUTPUT_FILE" 2>/dev/null; then
    log "ERROR: Backup file failed gzip validation: $OUTPUT_FILE"
    rm --force "$OUTPUT_FILE"
    exit 1
fi

SIZE=$(du --human-readable "$OUTPUT_FILE" | cut --fields=1)
log "Full backup completed: $OUTPUT_FILE ($SIZE)"

cleanup_old_backups "$BACKUP_DIR" "$FULL_BACKUP_RETAIN"

log "Full MySQL backup finished successfully"
