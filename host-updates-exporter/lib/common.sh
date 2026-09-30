#!/usr/bin/env bash

set -o errexit
set -o nounset
set -o pipefail

HOST_UPDATES_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"

# shellcheck source=SCRIPTDIR/config.sh
source "$HOST_UPDATES_DIR/lib/config.sh"

log() {
    echo "[$(date '+%Y-%m-%d %H:%M:%S')] $*" >&2
}

# Package lists live in the state volume, so they survive image upgrades.
APT_LISTS_DIR="$STATE_DIR/apt-lists"

# apt-get with the host's configuration (/etc/apt is the host's, mounted) and
# the host's installed packages, but this container's own package lists.
host_apt_get() {
    mkdir --parents "$APT_LISTS_DIR/partial"
    LC_ALL=C timeout 600 apt-get -c "$HOST_UPDATES_DIR/lib/apt-no-hooks.conf" \
        -o Dir::State::status="$HOST_ROOT/var/lib/dpkg/status" \
        -o Dir::State::Lists="$APT_LISTS_DIR" "$@"
}

# Refreshes the package lists. Fails if any source could not be fetched, so
# stale lists are reported instead of silently used.
refresh_package_lists() {
    host_apt_get update -qq --error-on=any
}

# False until a refresh has fetched package indexes at least once; without
# them a simulated upgrade finds nothing and would read as "all up to date".
package_lists_present() {
    [[ -n "${HOST_UPDATES_SIMULATION_FILE:-}" ]] \
        || compgen -G "$APT_LISTS_DIR/*_Packages*" > /dev/null
}

# `apt-get --simulate dist-upgrade` against the host, or the file in
# $HOST_UPDATES_SIMULATION_FILE (used by the tests).
simulate_upgrade() {
    if [[ -n "${HOST_UPDATES_SIMULATION_FILE:-}" ]]; then
        cat "$HOST_UPDATES_SIMULATION_FILE"
    else
        host_apt_get --simulate -o Debug::NoLocking=1 dist-upgrade
    fi
}

# GET on the Docker Engine API, e.g. docker_api /containers/json.
docker_api() {
    curl --silent --show-error --fail ${DOCKER_SOCKET:+--unix-socket "$DOCKER_SOCKET"} "$DOCKER_API$1"
}

# The host's name, which every series carries: hosts in the same environment
# otherwise share job/instance labels.
host_name() {
    if [[ -s "$HOST_ROOT/etc/hostname" ]]; then
        tr --delete '[:space:]' < "$HOST_ROOT/etc/hostname"
    else
        hostname
    fi
}

is_own_origin() {
    local label="$1" o
    for o in "${OWN_ORIGINS[@]}"; do
        [[ "$label" == "$o" ]] && return 0
    done
    return 1
}

# Reads `apt-get --simulate dist-upgrade` output on stdin and prints one
# '|'-separated line per package that would be upgraded or newly installed:
#   package|current|candidate|origin|security|owner
# current is empty for new packages (e.g. a new kernel); origin is the label
# of the first repo offering the candidate; security is true when any of
# those repos is a -security suite; owner is "ours" or "hosting".
parse_simulation() {
    # Inst <pkg> [<current>] (<candidate> <Label>:<Version>/<Suite>, ... [<arch>]) ...
    local re='^Inst ([^ ]+) (\[([^]]*)\] )?\(([^ ]+) (.*) \[[^]]*\]\)'
    local line pkg current candidate origins origin security owner part label
    local -a parts
    while IFS= read -r line; do
        [[ $line =~ $re ]] || continue
        pkg=${BASH_REMATCH[1]}
        current=${BASH_REMATCH[3]}
        candidate=${BASH_REMATCH[4]}
        origins=${BASH_REMATCH[5]}

        origin="" security=false owner=hosting
        IFS=',' read -ra parts <<< "$origins"
        for part in "${parts[@]}"; do
            part=${part# }
            label=""
            [[ $part == *:* ]] && label=${part%%:*}
            [[ -z $origin ]] && origin=$label
            [[ $part == *-security ]] && security=true
            is_own_origin "$label" && owner=ours
        done
        printf '%s|%s|%s|%s|%s|%s\n' "$pkg" "$current" "$candidate" "$origin" "$security" "$owner"
    done
}

# Prometheus label value escaping: backslash, double quote, newline.
esc() {
    local v="${1//\\/\\\\}"
    v="${v//\"/\\\"}"
    printf '%s' "${v//$'\n'/\\n}"
}

# Atomically replaces $METRICS_DIR/$1 with stdin, so /metrics never serves a
# half-written file.
write_metrics() {
    mkdir --parents "$METRICS_DIR"
    cat > "$METRICS_DIR/.$1.tmp"
    mv "$METRICS_DIR/.$1.tmp" "$METRICS_DIR/$1"
}
