#!/usr/bin/env bash
# Replay binary log files for point-in-time recovery on top of a full restore

# shellcheck source=SCRIPTDIR/../lib/common.sh
source "$(dirname "$0")/../lib/common.sh"

BACKUP_DIR="$BACKUP_ROOT/mysql/binlog"

AFTER_DUMP=""
FROM_FILE=""
TO_FILE=""
START_POS=""
STOP_DATETIME=""
ALLOW_GAPS=false

usage() {
    cat <<EOF
Usage: $0 [options]

Options:
  --after <dump-file>        Derive start file + position from a mysqldump
                             produced with --source-data=2. Recommended.
  --from <binlog-file>       First binlog file to replay (manual override).
  --start-position <N>       Byte offset within the first file (manual override).
  --to <binlog-file>         Last binlog file to replay.
  --stop-datetime 'Y-m-d H:M:S'
                             Stop replay at the given timestamp in the last file.
  --allow-gaps               Proceed even if the binlog sequence has gaps.
                             Default is to abort — a gap means silent data loss.
EOF
}

# Parse arguments
while [[ $# -gt 0 ]]; do
    case "$1" in
        --after)          AFTER_DUMP="$2"; shift 2 ;;
        --from)           FROM_FILE="$2"; shift 2 ;;
        --start-position) START_POS="$2"; shift 2 ;;
        --to)             TO_FILE="$2"; shift 2 ;;
        --stop-datetime)  STOP_DATETIME="$2"; shift 2 ;;
        --allow-gaps)     ALLOW_GAPS=true; shift ;;
        -h|--help)        usage; exit 0 ;;
        *)                usage; exit 1 ;;
    esac
done

assert_container_running "$MYSQL_CONTAINER"

ROOT_PASSWORD=$(read_mysql_password)

# If --after was given, extract the binlog coordinate from the dump header.
# This requires the dump to have been produced with --source-data=2.
if [[ -n "$AFTER_DUMP" ]]; then
    if [[ ! -f "$AFTER_DUMP" ]]; then
        log "ERROR: Dump file not found: $AFTER_DUMP"
        exit 1
    fi

    # Subshell locally disables pipefail because head may exit before gunzip finishes.
    COORD_LINE=$(
        set +o pipefail
        gunzip -c "$AFTER_DUMP" 2>/dev/null | head -c 4096 \
            | grep -m1 "CHANGE REPLICATION SOURCE TO SOURCE_LOG_FILE="
    ) || true

    if [[ -z "$COORD_LINE" ]]; then
        log "ERROR: No binlog coordinate in $AFTER_DUMP"
        log "       Dump must be produced with --source-data=2"
        exit 1
    fi

    FROM_FILE=$(printf '%s' "$COORD_LINE" | grep -oE "SOURCE_LOG_FILE='[^']+'" | cut -d\' -f2)
    START_POS=$(printf '%s' "$COORD_LINE" | grep -oE "SOURCE_LOG_POS=[0-9]+" | cut -d= -f2)

    if [[ -z "$FROM_FILE" || -z "$START_POS" ]]; then
        log "ERROR: Failed to parse coordinate from: $COORD_LINE"
        exit 1
    fi

    log "Derived from dump: start at $FROM_FILE position $START_POS"
fi

# List available binlog files, sorted. Regex (not glob) to restrict to the
# canonical binlog.<digits> form and exclude any .bak/.tmp/etc. siblings.
AVAILABLE=$(find "$BACKUP_DIR" -maxdepth 1 -regextype posix-extended \
    -regex '.*/binlog\.[0-9]+' -printf '%f\n' 2>/dev/null | sort)

if [[ -z "$AVAILABLE" ]]; then
    log "ERROR: No binlog files found in $BACKUP_DIR"
    exit 1
fi

# If the caller named a specific start file, it must actually exist on disk.
# The most common reason this fails is retention purging: the full backup is
# older than the binlog retention window, so the binlog the dump references
# is already gone and recovery is impossible from this dump.
if [[ -n "$FROM_FILE" ]] && ! grep -qxF "$FROM_FILE" <<< "$AVAILABLE"; then
    log "ERROR: Required start binlog '$FROM_FILE' is not in $BACKUP_DIR"
    log "       The binlog was either never backed up or has been purged by retention."
    log "       Recovery from the chosen dump is not possible without this file."
    log "Available files:"
    echo "$AVAILABLE" | while read -r f; do log "  $f"; done
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
    log "       Requested from: ${FROM_FILE:-(start)}  to: ${TO_FILE:-(end)}"
    log "Available files:"
    echo "$AVAILABLE" | while read -r f; do log "  $f"; done
    exit 1
fi

# Continuity check. MySQL binlogs are named <prefix>.<zero-padded-seq> (e.g.
# binlog.000050). A missing number in the sequence means writes between the
# surrounding files are lost — replay would apply later events as if nothing
# were missing, producing an inconsistent recovery target. Abort by default.
if [[ "$ALLOW_GAPS" != true && ${#REPLAY_FILES[@]} -gt 1 ]]; then
    MISSING=()
    PREV_NUM=""
    for logfile in "${REPLAY_FILES[@]}"; do
        if [[ "$logfile" =~ ^(.+\.)(0*)([0-9]+)$ ]]; then
            PREFIX="${BASH_REMATCH[1]}"
            NUM_STR="${BASH_REMATCH[3]}"
            WIDTH=$(( ${#BASH_REMATCH[2]} + ${#NUM_STR} ))
            NUM=$((10#$NUM_STR))
        else
            log "WARNING: Cannot parse sequence number from '$logfile' — skipping gap check"
            MISSING=()
            break
        fi
        if [[ -n "$PREV_NUM" ]]; then
            expected=$((PREV_NUM + 1))
            while (( NUM > expected )); do
                MISSING+=("$(printf "%s%0${WIDTH}d" "$PREFIX" "$expected")")
                expected=$((expected + 1))
            done
        fi
        PREV_NUM="$NUM"
    done
    if (( ${#MISSING[@]} > 0 )); then
        log "ERROR: Binlog sequence has ${#MISSING[@]} gap(s) in $BACKUP_DIR:"
        for m in "${MISSING[@]}"; do log "  missing: $m"; done
        log "Replaying across a gap will silently lose all writes from the missing file(s)."
        log "If the gap is intentional (e.g. a known-corrupt file you want to skip),"
        log "re-run with --allow-gaps."
        exit 1
    fi
fi

log "Replaying ${#REPLAY_FILES[@]} binlog file(s): ${REPLAY_FILES[0]} ... ${REPLAY_FILES[-1]}"
[[ -n "$START_POS" ]] && log "Start position: $START_POS (first file only)"
[[ -n "$STOP_DATETIME" ]] && log "Stop datetime: $STOP_DATETIME (last file only)"

# mysqlbinlog ships under /usr/libexec/mysqlsh/ in the MySQL 9 image and is
# not on $PATH, so the bare name cannot be used with docker exec.
MYSQLBINLOG=/usr/libexec/mysqlsh/mysqlbinlog

LAST_INDEX=$(( ${#REPLAY_FILES[@]} - 1 ))

for i in "${!REPLAY_FILES[@]}"; do
    logfile="${REPLAY_FILES[$i]}"
    log "Replaying: $logfile"

    # Copy binlog into the container
    docker cp "$BACKUP_DIR/$logfile" "$MYSQL_CONTAINER:/tmp/$logfile"

    # Build mysqlbinlog argv. --disable-log-bin prevents the replay from
    # generating new binlogs on the target.
    mysqlbinlog_args=(--disable-log-bin)
    if [[ -n "$START_POS" && "$i" -eq 0 ]]; then
        mysqlbinlog_args+=(--start-position="$START_POS")
    fi
    if [[ -n "$STOP_DATETIME" && "$i" -eq "$LAST_INDEX" ]]; then
        mysqlbinlog_args+=(--stop-datetime="$STOP_DATETIME")
    fi
    mysqlbinlog_args+=("/tmp/$logfile")

    # Two separate docker execs connected by a host-side pipe — avoids
    # `bash -c` string interpolation so $STOP_DATETIME and $ROOT_PASSWORD
    # pass through as real argv entries.
    docker exec "$MYSQL_CONTAINER" "$MYSQLBINLOG" "${mysqlbinlog_args[@]}" \
        | docker exec -i "$MYSQL_CONTAINER" \
            mysql -u root -p"$ROOT_PASSWORD"

    # Clean up
    docker exec "$MYSQL_CONTAINER" rm -f "/tmp/$logfile"
done

log "Incremental restore completed: replayed ${#REPLAY_FILES[@]} binlog file(s)"
