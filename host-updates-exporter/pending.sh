#!/usr/bin/env bash
# List the host's pending package updates, one per line:
#   package|current|candidate|origin|security|owner
# Used by the ops repos' update-packages.sh, which runs on the host and
# upgrades the ones we own:
#   docker exec host-updates-exporter /app/pending.sh --owner ours
#
# Usage:
#   ./pending.sh [--owner ours|hosting] [--no-refresh]

# shellcheck source=SCRIPTDIR/lib/common.sh
source "$(dirname "${BASH_SOURCE[0]}")/lib/common.sh"

OWNER=""
REFRESH=true
while [[ $# -gt 0 ]]; do
    case "$1" in
        --owner)      OWNER="$2"; shift 2 ;;
        --no-refresh) REFRESH=false; shift ;;
        -h|--help)    sed --quiet '2,/^$/p' "$0" | sed 's/^# \?//'; exit 0 ;;
        *)            echo "Unknown argument: $1" >&2; exit 1 ;;
    esac
done

if $REFRESH; then
    refresh_package_lists || log "WARNING: apt-get update failed for some sources; continuing with the lists we have"
fi

simulate_upgrade | parse_simulation | while IFS='|' read -r pkg current candidate origin security owner; do
    [[ -z $OWNER || $owner == "$OWNER" ]] || continue
    printf '%s|%s|%s|%s|%s|%s\n' "$pkg" "$current" "$candidate" "$origin" "$security" "$owner"
done
