#!/usr/bin/env bash
# Write pending-update metrics for the host.
#
# Refreshes the package lists from the host's apt sources, simulates a
# dist-upgrade against the host's installed packages and writes
# host_updates.prom to METRICS_DIR (lib/config.sh). Installs nothing. Run by
# run.sh on start and every HOST_UPDATES_COLLECT_INTERVAL seconds.
#
# Usage:
#   ./collect.sh [--no-refresh]
#
#   --no-refresh   Use the current package lists instead of running apt-get update

# shellcheck source=SCRIPTDIR/lib/common.sh
source "$(dirname "${BASH_SOURCE[0]}")/lib/common.sh"

REFRESH=true
REBOOT_FILE="${HOST_UPDATES_REBOOT_FILE:-$HOST_ROOT/run/reboot-required}"

while [[ $# -gt 0 ]]; do
    case "$1" in
        --no-refresh) REFRESH=false; shift ;;
        -h|--help)    sed --quiet '2,/^$/p' "$0" | sed 's/^# \?//'; exit 0 ;;
        *)            echo "Unknown argument: $1" >&2; exit 1 ;;
    esac
done

HOSTNAME_LABEL="$(host_name)"
NOW="$(date +%s)"

refresh_success=1
if $REFRESH; then
    if ! refresh_package_lists; then
        log "WARNING: apt-get update failed; reporting from the existing package lists"
        refresh_success=0
    fi
fi

lists_present=true
if package_lists_present; then
    pending="$(simulate_upgrade | parse_simulation)"
else
    log "ERROR: no package lists fetched yet; not reporting pending packages"
    lists_present=false
    refresh_success=0
    pending=""
fi

# pending_since: package -> epoch it was first seen pending. Keyed on the
# package only, so a newer candidate arriving doesn't reset the clock.
mkdir --parents "$STATE_DIR"
state_file="$STATE_DIR/pending-since"
declare -A since=()
if [[ -f "$state_file" ]]; then
    while IFS='|' read -r pkg ts; do
        [[ -n "$pkg" ]] && since[$pkg]=$ts
    done < "$state_file"
fi

declare -A counts=([hosting,true]=0 [hosting,false]=0 [ours,true]=0 [ours,false]=0)
package_lines=""
new_state=""
while IFS='|' read -r pkg current candidate origin security owner; do
    [[ -z "$pkg" ]] && continue
    ts="${since[$pkg]:-$NOW}"
    new_state+="$pkg|$ts"$'\n'
    counts[$owner,$security]=$(( counts[$owner,$security] + 1 ))
    package_lines+="host_updates_package_pending_since_timestamp_seconds{hostname=\"$(esc "$HOSTNAME_LABEL")\",package=\"$(esc "$pkg")\",current_version=\"$(esc "$current")\",candidate_version=\"$(esc "$candidate")\",origin=\"$(esc "$origin")\",owner=\"$owner\",security=\"$security\"} $ts"$'\n'
done <<< "$pending"

if $lists_present; then
    printf '%s' "$new_state" > "$state_file.tmp"
    mv "$state_file.tmp" "$state_file"
fi

reboot_required=0
reboot_since=""
reboot_packages=""
if [[ -f "$REBOOT_FILE" ]]; then
    reboot_required=1
    reboot_since="$(stat --format=%Y "$REBOOT_FILE")"
    if [[ -f "$REBOOT_FILE.pkgs" ]]; then
        while IFS= read -r pkg; do
            [[ -n "$pkg" ]] && reboot_packages+="host_updates_reboot_required_package{hostname=\"$(esc "$HOSTNAME_LABEL")\",package=\"$(esc "$pkg")\"} 1"$'\n'
        done < <(sort --unique "$REBOOT_FILE.pkgs")
    fi
fi

# shellcheck disable=SC1091
os_pretty="$(. "${HOST_UPDATES_OS_RELEASE:-$HOST_ROOT/etc/os-release}" && echo "${PRETTY_NAME:-unknown}")"
boot_time="$(awk '/^btime/ {print $2}' /proc/stat)"
dpkg_changed="$(stat --format=%Y "$HOST_ROOT/var/lib/dpkg/status" 2>/dev/null || echo 0)"
h="hostname=\"$(esc "$HOSTNAME_LABEL")\""

{
    if $lists_present; then
        echo "# HELP host_updates_package_pending_since_timestamp_seconds When the package was first seen with an update pending; one series per pending package."
        echo "# TYPE host_updates_package_pending_since_timestamp_seconds gauge"
        printf '%s' "$package_lines"
        echo "# HELP host_updates_pending_packages Number of packages with an update pending, by who patches them (owner) and whether a security update is among them."
        echo "# TYPE host_updates_pending_packages gauge"
        for owner in hosting ours; do
            for security in true false; do
                echo "host_updates_pending_packages{$h,owner=\"$owner\",security=\"$security\"} ${counts[$owner,$security]}"
            done
        done
    fi
    echo "# HELP host_updates_reboot_required 1 if /var/run/reboot-required exists."
    echo "# TYPE host_updates_reboot_required gauge"
    echo "host_updates_reboot_required{$h} $reboot_required"
    if [[ -n "$reboot_since" ]]; then
        echo "# HELP host_updates_reboot_required_since_timestamp_seconds When a reboot first became required."
        echo "# TYPE host_updates_reboot_required_since_timestamp_seconds gauge"
        echo "host_updates_reboot_required_since_timestamp_seconds{$h} $reboot_since"
    fi
    if [[ -n "$reboot_packages" ]]; then
        echo "# HELP host_updates_reboot_required_package Packages that asked for the pending reboot."
        echo "# TYPE host_updates_reboot_required_package gauge"
        printf '%s' "$reboot_packages"
    fi
    echo "# HELP host_updates_os_info Operating system of the host."
    echo "# TYPE host_updates_os_info gauge"
    echo "host_updates_os_info{$h,pretty_name=\"$(esc "$os_pretty")\",kernel=\"$(esc "$(uname -r)")\"} 1"
    echo "# HELP host_updates_boot_timestamp_seconds When the host last booted."
    echo "# TYPE host_updates_boot_timestamp_seconds gauge"
    echo "host_updates_boot_timestamp_seconds{$h} $boot_time"
    echo "# HELP host_updates_dpkg_changed_timestamp_seconds When packages were last installed, upgraded or removed (by anyone)."
    echo "# TYPE host_updates_dpkg_changed_timestamp_seconds gauge"
    echo "host_updates_dpkg_changed_timestamp_seconds{$h} $dpkg_changed"
    echo "# HELP host_updates_refresh_success 1 if the last apt-get update fetched every source."
    echo "# TYPE host_updates_refresh_success gauge"
    echo "host_updates_refresh_success{$h} $refresh_success"
    echo "# HELP host_updates_last_run_timestamp_seconds When collect.sh last wrote these metrics."
    echo "# TYPE host_updates_last_run_timestamp_seconds gauge"
    echo "host_updates_last_run_timestamp_seconds{$h} $NOW"
} | write_metrics host_updates.prom

log "Pending: hosting $(( counts[hosting,true] + counts[hosting,false] )) (${counts[hosting,true]} security), ours $(( counts[ours,true] + counts[ours,false] )) (${counts[ours,true]} security); reboot required: $reboot_required"
