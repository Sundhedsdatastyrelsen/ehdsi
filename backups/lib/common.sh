#!/usr/bin/env bash

set -o errexit
set -o nounset
set -o pipefail

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[1]}")" && pwd)"
REPO_ROOT="${REPO_ROOT:-$(cd "$SCRIPT_DIR/../.." && pwd)}"
export REPO_ROOT

# shellcheck source=SCRIPTDIR/config.sh
source "$(dirname "${BASH_SOURCE[0]}")/config.sh"

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

# Honours $MYSQL_ROOT_PASSWORD so disposable test containers can be restored
# into without touching the on-disk secret.
read_mysql_password() {
    if [[ -n "${MYSQL_ROOT_PASSWORD:-}" ]]; then
        printf '%s' "$MYSQL_ROOT_PASSWORD"
    else
        read_secret "$MYSQL_PASSWORD_FILE"
    fi
}

ensure_dir() {
    local dir="$1"
    if [[ ! -d "$dir" ]]; then
        mkdir -p "$dir"
        log "Created directory: $dir"
    fi
}

# Sorts entries by name; correct because backup names are timestamp-prefixed.
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
