#!/usr/bin/env bash
# Copies rotated MySQL binary logs out of the container.

# shellcheck source=SCRIPTDIR/../lib/common.sh
source "$(dirname "$0")/../lib/common.sh"

BACKUP_DIR="$BACKUP_ROOT/mysql/binlog"
ensure_dir "$BACKUP_DIR"

assert_container_running "$MYSQL_CONTAINER"

ROOT_PASSWORD=$(read_mysql_password)

log "Starting binlog backup..."

# Force rotation so the previously-active log becomes safe to copy.
docker exec "$MYSQL_CONTAINER" \
    mysql -u root -p"$ROOT_PASSWORD" -e "FLUSH BINARY LOGS;" 2>/dev/null

BINLOGS=$(docker exec "$MYSQL_CONTAINER" \
    mysql -u root -p"$ROOT_PASSWORD" \
    --batch --skip-column-names \
    -e "SHOW BINARY LOGS;" 2>/dev/null)

if [[ -z "$BINLOGS" ]]; then
    log "WARNING: No binary logs found. Is binary logging enabled?"
    exit 0
fi

# Last entry from SHOW BINARY LOGS is the currently-active file; mysqld
# is still writing to it, so skip.
ACTIVE_BINLOG=$(echo "$BINLOGS" | tail -n 1 | awk '{print $1}')

RETENTION_CUTOFF=$(date -d "$BINLOG_RETAIN_DAYS days ago" '+%s')

COPIED=0
SKIPPED_OLD=0
while IFS=$'\t' read -r logfile _size _encrypted; do
    if [[ "$logfile" == "$ACTIVE_BINLOG" ]]; then
        continue
    fi
    if [[ -f "$BACKUP_DIR/$logfile" ]]; then
        continue
    fi

    FILE_EPOCH=$(docker exec "$MYSQL_CONTAINER" \
        stat -c '%Y' "$MYSQL_DATA_DIR_IN_CONTAINER/$logfile" 2>/dev/null) || continue
    if (( FILE_EPOCH < RETENTION_CUTOFF )); then
        SKIPPED_OLD=$((SKIPPED_OLD + 1))
        continue
    fi

    log "Copying: $logfile"
    docker cp "$MYSQL_CONTAINER:$MYSQL_DATA_DIR_IN_CONTAINER/$logfile" "$BACKUP_DIR/"
    COPIED=$((COPIED + 1))
done <<< "$BINLOGS"

if (( COPIED == 0 )); then
    log "No new binlog files to back up"
else
    log "Copied $COPIED binlog file(s)"
fi

if (( SKIPPED_OLD > 0 )); then
    log "Skipped $SKIPPED_OLD binlog file(s) older than $BINLOG_RETAIN_DAYS days"
fi

cleanup_old_backups_by_age "$BACKUP_DIR" "$BINLOG_RETAIN_DAYS"

log "Binlog backup finished successfully"
