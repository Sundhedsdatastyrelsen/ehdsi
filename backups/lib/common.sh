#!/usr/bin/env bash
# Shared library for EHDSI backup scripts

set -o errexit
set -o nounset
set -o pipefail

# Paths
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[1]}")" && pwd)"
REPO_ROOT="$(cd "$SCRIPT_DIR/../.." && pwd)"
BACKUP_ROOT="${EHDSI_BACKUP_DIR:-/opt/backup}"

NCP_DIR="$REPO_ROOT/NCP"
NC_DIR="$REPO_ROOT/national-connector"

# Container names
MYSQL_CONTAINER="${MYSQL_CONTAINER:-openncp_db}"
NC_CONTAINER="${NC_CONTAINER:-national-connector}"

# Database lists
MYSQL_DATABASES=(ehealth_properties ehealth_atna ehealth_ltrdb ehealth_eadc)
SQLITE_DATABASES=(undo-db.sqlite local-lms-db.sqlite job-queue.sqlite)

# Retention defaults
FULL_BACKUP_RETAIN="${FULL_BACKUP_RETAIN:-7}"
BINLOG_RETAIN_DAYS="${BINLOG_RETAIN_DAYS:-14}"
SQLITE_BACKUP_RETAIN="${SQLITE_BACKUP_RETAIN:-7}"

log() {
    echo "[$(date '+%Y-%m-%d %H:%M:%S')] $*" >&2
}

timestamp() {
    date '+%Y%m%d-%H%M%S'
}

read_secret() {
    local secret_file="$1"
    if [[ ! -f "$secret_file" ]]; then
        log "ERROR: Secret file not found: $secret_file"
        return 1
    fi
    tr -d '[:space:]' < "$secret_file"
}

# MySQL root password resolver: honours $MYSQL_ROOT_PASSWORD env var if set
# (useful for restoring into disposable test containers), otherwise reads
# the NCP secret file.
read_mysql_password() {
    if [[ -n "${MYSQL_ROOT_PASSWORD:-}" ]]; then
        printf '%s' "$MYSQL_ROOT_PASSWORD"
    else
        read_secret "$NCP_DIR/db_root_password.txt"
    fi
}

ensure_dir() {
    local dir="$1"
    if [[ ! -d "$dir" ]]; then
        mkdir -p "$dir"
        log "Created directory: $dir"
    fi
}

# Remove all but the N most recent entries (files or directories) in a directory.
# Entries are sorted by name (which sorts chronologically due to timestamp naming).
cleanup_old_backups() {
    local dir="$1"
    local keep_count="$2"

    local entries
    entries=$(find "$dir" -maxdepth 1 -mindepth 1 | sort)
    local total
    total=$(echo "$entries" | grep -c . || true)

    if (( total > keep_count )); then
        local to_remove
        to_remove=$(echo "$entries" | head -n $(( total - keep_count )))
        echo "$to_remove" | while read -r entry; do
            rm -rf "$entry"
            log "Removed old backup: $entry"
        done
    fi
}

# Remove files in a directory older than N days.
cleanup_old_backups_by_age() {
    local dir="$1"
    local max_age_days="$2"

    find "$dir" -maxdepth 1 -mindepth 1 -mtime +"$max_age_days" -print0 \
        | while IFS= read -r -d '' entry; do
            rm -rf "$entry"
            log "Removed old backup (age): $entry"
        done
}

assert_container_running() {
    local container="$1"
    if ! docker inspect --format='{{.State.Running}}' "$container" 2>/dev/null | grep -q true; then
        log "ERROR: Container '$container' is not running"
        return 1
    fi
}
