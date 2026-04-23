#!/usr/bin/env bash
# Full backup of all MySQL databases via mysqldump

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

docker exec "$MYSQL_CONTAINER" \
    mysqldump \
        -u root -p"$ROOT_PASSWORD" \
        --single-transaction \
        --source-data=2 \
        --routines \
        --triggers \
        --events \
        --databases "${MYSQL_DATABASES[@]}" \
    | gzip > "$OUTPUT_FILE"

# Validate the gzip file
if ! gzip -t "$OUTPUT_FILE" 2>/dev/null; then
    log "ERROR: Backup file failed gzip validation: $OUTPUT_FILE"
    rm -f "$OUTPUT_FILE"
    exit 1
fi

SIZE=$(du -h "$OUTPUT_FILE" | cut -f1)
log "Full backup completed: $OUTPUT_FILE ($SIZE)"

cleanup_old_backups "$BACKUP_DIR" "$FULL_BACKUP_RETAIN"

log "Full MySQL backup finished successfully"
