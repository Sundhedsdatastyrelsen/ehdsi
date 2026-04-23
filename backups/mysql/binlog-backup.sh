#!/usr/bin/env bash
# Incremental backup: copy rotated MySQL binary log files to host

source "$(dirname "$0")/../lib/common.sh"

BACKUP_DIR="$BACKUP_ROOT/mysql/binlog"
ensure_dir "$BACKUP_DIR"

assert_container_running "$MYSQL_CONTAINER"

ROOT_PASSWORD=$(read_mysql_password)

log "Starting binlog backup..."

# Flush binary logs to rotate the current one
docker exec "$MYSQL_CONTAINER" \
    mysql -u root -p"$ROOT_PASSWORD" -e "FLUSH BINARY LOGS;" 2>/dev/null

# List all binary logs (name, size, encrypted)
BINLOGS=$(docker exec "$MYSQL_CONTAINER" \
    mysql -u root -p"$ROOT_PASSWORD" \
    --batch --skip-column-names \
    -e "SHOW BINARY LOGS;" 2>/dev/null)

if [[ -z "$BINLOGS" ]]; then
    log "WARNING: No binary logs found. Is binary logging enabled?"
    exit 0
fi

# Get the last line (active binlog) — we skip this one
ACTIVE_BINLOG=$(echo "$BINLOGS" | tail -n 1 | awk '{print $1}')

# Calculate the cutoff timestamp for retention (skip files older than this)
RETENTION_CUTOFF=$(date -d "$BINLOG_RETAIN_DAYS days ago" '+%s')

COPIED=0
SKIPPED_OLD=0
while IFS=$'\t' read -r logfile _size _encrypted; do
    # Skip the currently active binlog
    if [[ "$logfile" == "$ACTIVE_BINLOG" ]]; then
        continue
    fi

    # Skip if already backed up
    if [[ -f "$BACKUP_DIR/$logfile" ]]; then
        continue
    fi

    # Check file age inside the container — skip if older than retention period
    FILE_EPOCH=$(docker exec "$MYSQL_CONTAINER" \
        stat -c '%Y' "/var/lib/mysql/$logfile" 2>/dev/null) || continue
    if (( FILE_EPOCH < RETENTION_CUTOFF )); then
        SKIPPED_OLD=$((SKIPPED_OLD + 1))
        continue
    fi

    log "Copying: $logfile"
    docker cp "$MYSQL_CONTAINER:/var/lib/mysql/$logfile" "$BACKUP_DIR/"
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
