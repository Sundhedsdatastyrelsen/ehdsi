#!/usr/bin/env bash
# Replay binary log files for point-in-time recovery on top of a full restore

source "$(dirname "$0")/../lib/common.sh"

BACKUP_DIR="$BACKUP_ROOT/mysql/binlog"

FROM_FILE=""
TO_FILE=""
STOP_DATETIME=""

# Parse arguments
while [[ $# -gt 0 ]]; do
    case "$1" in
        --from) FROM_FILE="$2"; shift 2 ;;
        --to) TO_FILE="$2"; shift 2 ;;
        --stop-datetime) STOP_DATETIME="$2"; shift 2 ;;
        *)
            echo "Usage: $0 [--from <binlog-file>] [--to <binlog-file>] [--stop-datetime 'YYYY-MM-DD HH:MM:SS']"
            exit 1
            ;;
    esac
done

assert_container_running "$MYSQL_CONTAINER"

ROOT_PASSWORD=$(read_secret "$NCP_DIR/db_root_password.txt")

# List available binlog files, sorted
AVAILABLE=$(find "$BACKUP_DIR" -maxdepth 1 -name 'binlog.*' -printf '%f\n' 2>/dev/null | sort)

if [[ -z "$AVAILABLE" ]]; then
    log "ERROR: No binlog files found in $BACKUP_DIR"
    exit 1
fi

# Filter range
IN_RANGE=false
if [[ -z "$FROM_FILE" ]]; then
    IN_RANGE=true
fi

REPLAY_FILES=()
while read -r logfile; do
    if [[ -n "$FROM_FILE" && "$logfile" == "$FROM_FILE" ]]; then
        IN_RANGE=true
    fi

    if [[ "$IN_RANGE" == true ]]; then
        REPLAY_FILES+=("$logfile")
    fi

    if [[ -n "$TO_FILE" && "$logfile" == "$TO_FILE" ]]; then
        break
    fi
done <<< "$AVAILABLE"

if [[ ${#REPLAY_FILES[@]} -eq 0 ]]; then
    log "ERROR: No binlog files matched the specified range"
    log "Available files:"
    echo "$AVAILABLE" | while read -r f; do log "  $f"; done
    exit 1
fi

log "Replaying ${#REPLAY_FILES[@]} binlog file(s): ${REPLAY_FILES[0]} ... ${REPLAY_FILES[-1]}"
if [[ -n "$STOP_DATETIME" ]]; then
    log "Stop datetime: $STOP_DATETIME"
fi

LAST_INDEX=$(( ${#REPLAY_FILES[@]} - 1 ))

for i in "${!REPLAY_FILES[@]}"; do
    logfile="${REPLAY_FILES[$i]}"
    log "Replaying: $logfile"

    # Copy binlog into the container
    docker cp "$BACKUP_DIR/$logfile" "$MYSQL_CONTAINER:/tmp/$logfile"

    # Build mysqlbinlog command
    BINLOG_CMD="mysqlbinlog /tmp/$logfile"

    # Apply stop-datetime only on the last file
    if [[ -n "$STOP_DATETIME" && "$i" -eq "$LAST_INDEX" ]]; then
        BINLOG_CMD="mysqlbinlog --stop-datetime='$STOP_DATETIME' /tmp/$logfile"
    fi

    # Replay
    docker exec "$MYSQL_CONTAINER" \
        bash -c "$BINLOG_CMD | mysql -u root -p'$ROOT_PASSWORD'" 2>/dev/null

    # Clean up
    docker exec "$MYSQL_CONTAINER" rm -f "/tmp/$logfile"
done

log "Incremental restore completed: replayed ${#REPLAY_FILES[@]} binlog file(s)"
