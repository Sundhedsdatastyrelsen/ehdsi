#!/usr/bin/env bash
# Install systemd timers that run the backup scripts on a schedule.
#
# Three timer pairs:
#   <prefix>mysql-full-backup     daily full mysqldump
#   <prefix>mysql-binlog-backup   incremental: rotate + copy binlogs
#   <prefix>sqlite-backup         online sqlite3 .backup snapshots
#
# Usage:
#   sudo ./install.sh install [options]   Write units to /etc/systemd/system/,
#                                         daemon-reload, enable + start timers.
#   sudo ./install.sh uninstall           Stop, disable, and remove the units.
#        ./install.sh status              Show timer + service state (no root).
#        ./install.sh -h                  Show usage.
#
# Options for install:
#   --user <name>      Run scripts as this user (default: $SUDO_USER)
#   --full <calendar>  OnCalendar for MySQL full backup     (default: *-*-* 03:00:00)
#   --binlog <calendar>  OnCalendar for binlog backup       (default: *:0/15)
#   --sqlite <calendar>  OnCalendar for SQLite backup       (default: *-*-* 0/6:00:00)
#   --prefix <prefix>  Unit name prefix (default: ehdsi-)
#   --no-start         Install and enable, but don't start now
#   --dry-run          Print the unit files without writing or applying them

set -o errexit
set -o nounset
set -o pipefail

PREFIX="ehdsi-"
RUN_USER="${SUDO_USER:-$(id -un)}"
FULL_CAL="*-*-* 03:00:00"
BINLOG_CAL="*:0/15"
SQLITE_CAL="*-*-* 0/6:00:00"
NO_START=false
DRY_RUN=false

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
REPO_ROOT="$(cd "$SCRIPT_DIR/../.." && pwd)"
BACKUPS_DIR="$REPO_ROOT/backups"
SYSTEMD_DIR="/etc/systemd/system"

usage() {
    sed -n '2,/^$/p' "$0" | sed 's/^# \?//'
}

require_root() {
    if [[ $EUID -ne 0 ]]; then
        echo "ERROR: '$1' requires root. Re-run with sudo." >&2
        exit 1
    fi
}

check_systemd() {
    if ! command -v systemctl &>/dev/null; then
        echo "ERROR: systemctl not found. This installer is Linux/systemd only." >&2
        exit 1
    fi
}

# Names for each pair: <kind> <script-relative-to-backups>.
# These three lines define everything that gets installed.
units() {
    cat <<'EOF'
mysql-full-backup    mysql/full-backup.sh        FULL
mysql-binlog-backup  mysql/binlog-backup.sh      BINLOG
sqlite-backup        sqlite/backup.sh            SQLITE
EOF
}

# Resolve the OnCalendar for a unit by its tag (FULL/BINLOG/SQLITE).
calendar_for() {
    case "$1" in
        FULL)   echo "$FULL_CAL" ;;
        BINLOG) echo "$BINLOG_CAL" ;;
        SQLITE) echo "$SQLITE_CAL" ;;
    esac
}

# Pretty description used by the [Unit] block.
description_for() {
    case "$1" in
        mysql-full-backup)   echo "MySQL full backup (mysqldump)" ;;
        mysql-binlog-backup) echo "MySQL binlog backup (incremental)" ;;
        sqlite-backup)       echo "SQLite backup (online snapshot)" ;;
    esac
}

render_service() {
    local kind="$1" script_rel="$2"
    cat <<EOF
[Unit]
Description=$(description_for "$kind")
Wants=docker.service
After=docker.service network-online.target

[Service]
Type=oneshot
User=${RUN_USER}
WorkingDirectory=${REPO_ROOT}
ExecStart=${BACKUPS_DIR}/${script_rel}
# Logs go to journalctl -u ${PREFIX}${kind}.service
StandardOutput=journal
StandardError=journal
EOF
}

render_timer() {
    local kind="$1" tag="$2"
    cat <<EOF
[Unit]
Description=Run $(description_for "$kind") on schedule

[Timer]
OnCalendar=$(calendar_for "$tag")
Persistent=true
Unit=${PREFIX}${kind}.service

[Install]
WantedBy=timers.target
EOF
}

write_unit() {
    local path="$1" content="$2"
    if $DRY_RUN; then
        echo "----- $path -----"
        echo "$content"
        echo ""
    else
        printf '%s\n' "$content" > "$path"
        echo "wrote $path"
    fi
}

cmd_install() {
    if ! $DRY_RUN; then
        require_root install
        check_systemd
    fi

    if ! $DRY_RUN && ! id -u "$RUN_USER" &>/dev/null; then
        echo "ERROR: user '$RUN_USER' does not exist" >&2
        exit 1
    fi

    if [[ ! -x "$BACKUPS_DIR/mysql/full-backup.sh" ]]; then
        echo "ERROR: $BACKUPS_DIR/mysql/full-backup.sh not executable. Did the repo get checked out without +x?" >&2
        exit 1
    fi

    echo "Installing units to $SYSTEMD_DIR (prefix: $PREFIX, user: $RUN_USER)"
    while read -r kind script_rel tag; do
        [[ -z "$kind" ]] && continue
        local svc_path="$SYSTEMD_DIR/${PREFIX}${kind}.service"
        local tmr_path="$SYSTEMD_DIR/${PREFIX}${kind}.timer"
        write_unit "$svc_path" "$(render_service "$kind" "$script_rel")"
        write_unit "$tmr_path" "$(render_timer "$kind" "$tag")"
    done < <(units)

    if $DRY_RUN; then
        echo "(dry-run: skipping daemon-reload, enable, and start)"
        return 0
    fi

    systemctl daemon-reload
    while read -r kind _script_rel _tag; do
        [[ -z "$kind" ]] && continue
        systemctl enable "${PREFIX}${kind}.timer"
        if $NO_START; then
            echo "enabled (not started): ${PREFIX}${kind}.timer"
        else
            systemctl start "${PREFIX}${kind}.timer"
            echo "started: ${PREFIX}${kind}.timer"
        fi
    done < <(units)

    echo ""
    echo "Done. Inspect with:"
    echo "  systemctl list-timers '${PREFIX}*'"
    echo "  journalctl -u ${PREFIX}mysql-full-backup.service"
    echo "  systemctl edit ${PREFIX}mysql-full-backup.timer   # change schedule via drop-in"
}

cmd_uninstall() {
    require_root uninstall
    check_systemd

    while read -r kind _script_rel _tag; do
        [[ -z "$kind" ]] && continue
        systemctl stop    "${PREFIX}${kind}.timer"   2>/dev/null || true
        systemctl disable "${PREFIX}${kind}.timer"   2>/dev/null || true
        rm -f "$SYSTEMD_DIR/${PREFIX}${kind}.service"
        rm -f "$SYSTEMD_DIR/${PREFIX}${kind}.timer"
        echo "removed: ${PREFIX}${kind}.{service,timer}"
    done < <(units)

    systemctl daemon-reload
    systemctl reset-failed 2>/dev/null || true
    echo "Done."
}

cmd_status() {
    check_systemd
    local any=false
    while read -r kind _script_rel _tag; do
        [[ -z "$kind" ]] && continue
        local timer="${PREFIX}${kind}.timer"
        if systemctl list-unit-files "$timer" --no-legend 2>/dev/null | grep -q .; then
            any=true
            echo "=== $timer ==="
            systemctl --no-pager --full status "$timer" 2>&1 | head -10
            echo ""
        fi
    done < <(units)
    if ! $any; then
        echo "No ${PREFIX}* timers installed."
        return 0
    fi
    echo "=== upcoming runs ==="
    systemctl list-timers "${PREFIX}*" --no-pager 2>/dev/null || true
}

# ── arg parsing ──

action=""
while [[ $# -gt 0 ]]; do
    case "$1" in
        install|uninstall|status) action="$1"; shift ;;
        --user)        RUN_USER="$2"; shift 2 ;;
        --full)        FULL_CAL="$2"; shift 2 ;;
        --binlog)      BINLOG_CAL="$2"; shift 2 ;;
        --sqlite)      SQLITE_CAL="$2"; shift 2 ;;
        --prefix)      PREFIX="$2"; shift 2 ;;
        --no-start)    NO_START=true; shift ;;
        --dry-run)     DRY_RUN=true; shift ;;
        -h|--help)     usage; exit 0 ;;
        *)             echo "Unknown argument: $1" >&2; usage >&2; exit 1 ;;
    esac
done

case "$action" in
    install)   cmd_install ;;
    uninstall) cmd_uninstall ;;
    status)    cmd_status ;;
    "")        usage; exit 1 ;;
esac
