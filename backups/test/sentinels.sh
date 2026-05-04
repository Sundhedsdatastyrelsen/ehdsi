#!/usr/bin/env bash
# Sentinel helpers for backup/restore testing. Use as a script with
# subcommands or source it to expose the functions to another script.
#
# Subcommands:
#   mysql-add <label>    Insert _sentinel_<label> in ehealth_properties.EHNCP_PROPERTY
#   mysql-list           List rows where NAME LIKE '_sentinel_%'
#   sqlite-add <label>   Insert (label, ts) into _sentinel in $SQLITE_DATA_DIR/undo-db.sqlite
#   sqlite-list          Show all _sentinel rows
#
# Functions exposed when sourced:
#   mysql_add_sentinel <label>
#   mysql_list_sentinels
#   sqlite_add_sentinel <label>
#   sqlite_list_sentinels

# shellcheck source=SCRIPTDIR/../lib/common.sh
source "$(dirname "${BASH_SOURCE[0]}")/../lib/common.sh"

# Restrict label charset so direct SQL interpolation is safe.
_sentinel_check_label() {
    if [[ ! "$1" =~ ^[A-Za-z0-9_-]+$ ]]; then
        echo "ERROR: Label must match ^[A-Za-z0-9_-]+$ (got: '$1')" >&2
        return 1
    fi
}

mysql_add_sentinel() {
    local label="${1:-}"
    if [[ -z "$label" ]]; then
        echo "Usage: mysql_add_sentinel <label>" >&2
        return 1
    fi
    _sentinel_check_label "$label" || return 1

    assert_container_running "$MYSQL_CONTAINER"
    local pw key value
    pw=$(read_mysql_password)
    key="_sentinel_${label}"
    value="inserted_at_$(date '+%Y-%m-%dT%H:%M:%S')"

    docker exec "$MYSQL_CONTAINER" \
        mysql -u root -p"$pw" \
        -e "INSERT INTO ehealth_properties.EHNCP_PROPERTY (NAME, VALUE)
            VALUES ('$key', '$value');" 2>/dev/null

    log "Added sentinel in $MYSQL_CONTAINER: $key = $value"
}

mysql_list_sentinels() {
    assert_container_running "$MYSQL_CONTAINER"
    local pw
    pw=$(read_mysql_password)
    log "Sentinels in $MYSQL_CONTAINER:"
    # ESCAPE forces the literal underscore to match — bare `_` in LIKE is a
    # single-char wildcard.
    docker exec "$MYSQL_CONTAINER" \
        mysql -u root -p"$pw" \
        -e "SELECT NAME, VALUE FROM ehealth_properties.EHNCP_PROPERTY
            WHERE NAME LIKE '\\_sentinel\\_%' ESCAPE '\\\\'
            ORDER BY NAME;" 2>/dev/null
}

sqlite_add_sentinel() {
    local label="${1:-}"
    if [[ -z "$label" ]]; then
        echo "Usage: sqlite_add_sentinel <label>" >&2
        echo "  Inserts a row into _sentinel in \$SQLITE_DATA_DIR/undo-db.sqlite" >&2
        return 1
    fi
    _sentinel_check_label "$label" || return 1

    if ! command -v sqlite3 &>/dev/null; then
        log "ERROR: sqlite3 is not installed on the host"
        return 1
    fi

    local target_db target_dir ts
    target_db="$SQLITE_DATA_DIR/undo-db.sqlite"
    target_dir=$(dirname "$target_db")

    if [[ ! -f "$target_db" ]]; then
        log "ERROR: Target database not found: $target_db"
        return 1
    fi

    # SQLite needs to write both the DB file and its parent directory (for
    # the rollback journal / WAL). The data dir is typically owned by uid
    # 10001 in production, which from the host produces a confusing
    # "attempt to write a readonly database (8)" error. Detect up front.
    if [[ ! -w "$target_db" || ! -w "$target_dir" ]]; then
        log "ERROR: $target_db is not writable as $(id -un) (uid $(id -u))"
        log "       File owner: $(stat -c '%U:%G (uid %u)' "$target_db" 2>/dev/null)"
        log "       Either:"
        log "         (a) sudo $0 sqlite-add $label"
        log "         (b) sudo chown -R \$(id -u):\$(id -g) $target_dir  # then chown back"
        log "         (c) ./tests.sh sqlite-snapshot   # automated, uses temp dir"
        return 1
    fi

    ts=$(date '+%Y-%m-%dT%H:%M:%S')
    sqlite3 "$target_db" <<SQL
CREATE TABLE IF NOT EXISTS _sentinel (label TEXT, ts TEXT);
INSERT INTO _sentinel (label, ts) VALUES ('$label', '$ts');
SQL

    log "Added sentinel to $target_db: $label = $ts"
}

sqlite_list_sentinels() {
    if ! command -v sqlite3 &>/dev/null; then
        log "ERROR: sqlite3 is not installed on the host"
        return 1
    fi

    local target_db has_table
    target_db="$SQLITE_DATA_DIR/undo-db.sqlite"

    if [[ ! -f "$target_db" ]]; then
        log "ERROR: Target database not found: $target_db"
        return 1
    fi

    log "Sentinels in $target_db:"
    # Distinguish "table missing" from "table empty" — both produce empty
    # output from a plain SELECT.
    has_table=$(sqlite3 "$target_db" \
        "SELECT 1 FROM sqlite_master WHERE type='table' AND name='_sentinel';")
    if [[ -z "$has_table" ]]; then
        log "  (no _sentinel table yet)"
        return 0
    fi

    sqlite3 -header -column "$target_db" \
        "SELECT label, ts FROM _sentinel ORDER BY label;"
}

# Subcommand dispatch when invoked as a script.
if [[ "${BASH_SOURCE[0]}" == "${0}" ]]; then
    case "${1:-}" in
        mysql-add)    shift; mysql_add_sentinel "$@" ;;
        mysql-list)   mysql_list_sentinels ;;
        sqlite-add)   shift; sqlite_add_sentinel "$@" ;;
        sqlite-list)  sqlite_list_sentinels ;;
        ""|-h|--help|help)
            cat <<'EOF' >&2
Usage: sentinels.sh <subcommand> [args]

Subcommands:
  mysql-add <label>    Insert _sentinel_<label> in ehealth_properties.EHNCP_PROPERTY
  mysql-list           List MySQL sentinels
  sqlite-add <label>   Insert (label, ts) in $SQLITE_DATA_DIR/undo-db.sqlite
  sqlite-list          List SQLite sentinels
EOF
            [[ -z "${1:-}" ]] && exit 1 || exit 0
            ;;
        *)
            echo "Unknown subcommand: $1" >&2
            exec "$0" --help
            ;;
    esac
fi
