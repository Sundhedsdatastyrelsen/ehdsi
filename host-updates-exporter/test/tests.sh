#!/usr/bin/env bash
# Tests for host-updates-exporter against a canned `apt-get --simulate
# dist-upgrade`, Docker API, endoflife.date/GitHub responses and host root
# (all in fixtures/). Needs no root, apt, docker or network; run by the
# Dockerfile's test stage.
#
# Usage:
#   ./test/tests.sh

set -o errexit
set -o nounset
set -o pipefail

TEST_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
TMP="$(mktemp --directory)"
trap 'rm --recursive --force "$TMP"' EXIT

export HOST_UPDATES_SIMULATION_FILE="$TEST_DIR/fixtures/simulation.txt"
export HOST_UPDATES_HOST_ROOT="$TEST_DIR/fixtures/host"
export HOST_UPDATES_METRICS_DIR="$TMP/metrics"
export HOST_UPDATES_STATE_DIR="$TMP/state"
export HOST_UPDATES_REBOOT_FILE="$TMP/reboot-required"

failures=0
check() {
    local name="$1" expected="$2" actual="$3"
    if [[ "$expected" == "$actual" ]]; then
        echo "ok   $name"
    else
        echo "FAIL $name"
        diff <(echo "$expected") <(echo "$actual") | sed 's/^/     /' || true
        failures=$(( failures + 1 ))
    fi
}

# ── parse_simulation ──
# shellcheck source=SCRIPTDIR/../lib/common.sh
source "$TEST_DIR/../lib/common.sh"
check "parse_simulation classifies owner, security and new packages" \
"libc6|2.39-0ubuntu8.6|2.39-0ubuntu8.9|Ubuntu|true|hosting
tzdata|2025b-0ubuntu0.24.04|2025b-0ubuntu0.24.04.1|Ubuntu|false|hosting
linux-image-6.8.0-90-generic||6.8.0-90.91|Ubuntu|true|hosting
docker-ce|5:28.5.1-1~ubuntu.24.04~noble|5:29.0.2-1~ubuntu.24.04~noble|Docker CE|false|ours
containerd.io|1.7.27-1|1.7.28-1|Docker CE|false|ours" \
"$(parse_simulation < "$HOST_UPDATES_SIMULATION_FILE")"

# ── collect.sh ──
prom="$HOST_UPDATES_METRICS_DIR/host_updates.prom"
metric() { grep "^$1[{ ]" "$prom" | grep -F "$2" | awk '{print $NF}'; }

"$TEST_DIR/../collect.sh" --no-refresh 2>/dev/null
check "counts hosting security (incl. new kernel)" 2 "$(metric host_updates_pending_packages 'owner="hosting",security="true"')"
check "counts hosting other"     1 "$(metric host_updates_pending_packages 'owner="hosting",security="false"')"
check "counts ours"              2 "$(metric host_updates_pending_packages 'owner="ours",security="false"')"
check "one series per package"   5 "$(grep -c '^host_updates_package_pending_since_timestamp_seconds{' "$prom")"
check "no reboot required"       0 "$(metric host_updates_reboot_required '')"
check "refresh skipped counts as success" 1 "$(metric host_updates_refresh_success '')"
check "host's hostname on every series" 0 "$(grep '^host_updates' "$prom" | grep --count --invert-match 'hostname="testhost"' || true)"

first_since="$(metric host_updates_package_pending_since_timestamp_seconds 'package="docker-ce"')"
echo "docker-ce|1000" > "$HOST_UPDATES_STATE_DIR/pending-since"
touch "$HOST_UPDATES_REBOOT_FILE"
printf 'linux-base\nlibc6\nlinux-base\n' > "$HOST_UPDATES_REBOOT_FILE.pkgs"
"$TEST_DIR/../collect.sh" --no-refresh 2>/dev/null
check "pending-since survives reruns" 1000 "$(metric host_updates_package_pending_since_timestamp_seconds 'package="docker-ce"')"
check "new packages start pending now" true \
    "$( (( $(metric host_updates_package_pending_since_timestamp_seconds 'package="libc6"') >= first_since )) && echo true)"
check "reboot required"          1 "$(metric host_updates_reboot_required '')"
check "reboot packages deduped"  2 "$(grep -c '^host_updates_reboot_required_package{' "$prom")"
check "state drops packages no longer pending" 5 "$(wc -l < "$HOST_UPDATES_STATE_DIR/pending-since")"
check "no temp file left behind" "" "$(find "$HOST_UPDATES_METRICS_DIR" -name '.*')"

# No package lists fetched yet (fresh volume, refresh failing): no pending
# counts rather than a false "0 pending", and the pending-since state is kept.
cp "$HOST_UPDATES_STATE_DIR/pending-since" "$TMP/pending-since.before"
HOST_UPDATES_SIMULATION_FILE="" "$TEST_DIR/../collect.sh" --no-refresh 2>/dev/null
check "no lists: no pending counts"      "" "$(grep '^host_updates_pending_packages' "$prom" || true)"
check "no lists: refresh not successful" 0 "$(metric host_updates_refresh_success '')" 
check "no lists: pending-since kept"     "" "$(diff "$TMP/pending-since.before" "$HOST_UPDATES_STATE_DIR/pending-since")"

# ── pending.sh ──
check "pending.sh lists our packages" "docker-ce containerd.io" \
    "$("$TEST_DIR/../pending.sh" --owner ours --no-refresh | cut -d'|' -f1 | xargs)"

# ── eol.sh ──
export HOST_UPDATES_DOCKER_SOCKET=""
export HOST_UPDATES_DOCKER_API="file://$TEST_DIR/fixtures/docker"
export HOST_UPDATES_EOL_API="file://$TEST_DIR/fixtures/eol"
export HOST_UPDATES_GITHUB_API="file://$TEST_DIR/fixtures/github"
prom="$HOST_UPDATES_METRICS_DIR/host_software.prom"
# "<label>=<value>" of the info series for one piece of software
info() { grep "^host_updates_software_info{.*name=\"$1\"" "$prom" | grep --only-matching "[{,]$2=\"[^\"]*\"" | cut -d'"' -f2; }
eol() { grep "^host_updates_software_eol_timestamp_seconds{.*name=\"$1\"" "$prom" | awk '{print $NF}'; }

"$TEST_DIR/../eol.sh" 2>/dev/null
check "own images skipped"                 "" "$(grep national-connector "$prom" || true)"
check "containers grouped per image (by created-from ref, not moved tag)" "grafana,grafana-2" "$(info grafana/grafana containers)"
check "version from the tag"               "12.0.2|tag" "$(info grafana/grafana version)|$(info grafana/grafana version_source)"
check "cycle matched and latest reported"  "12.0|12.0.10|12.1.10" \
    "$(info grafana/grafana cycle)|$(info grafana/grafana latest_in_cycle)|$(info grafana/grafana latest)"
check "EOL date as timestamp"              "$(date --date=2026-02-05 +%s)" "$(eol grafana/grafana)"
check "version from <NAME>_VERSION env refines a major tag" "9.7.2|env|9.7" \
    "$(info mysql version)|$(info mysql version_source)|$(info mysql cycle)"
check "label contradicting the tag ignored" "1.12.2|tag" "$(info grafana/alloy version)|$(info grafana/alloy version_source)"
check "GitHub latest release for products endoflife.date lacks" "1.20.1|ok" \
    "$(info grafana/alloy latest)|$(info grafana/alloy status)"
check "version label used for :latest"      "2.1.1|label|2.1" \
    "$(info hashicorp/vault version)|$(info hashicorp/vault version_source)|$(info hashicorp/vault cycle)"
check "update: patch in cycle / newer cycle / floating unknown / current" "patch|newer|none|patch" \
    "$(info grafana/grafana update)|$(info grafana/alloy update)|$(info hashicorp/vault update)|$(info docker-engine update)"
check "no EOL series without a date"        "" "$(eol hashicorp/vault)"
check "unmapped image is untracked"         "untracked" "$(info busybox status)"
check "host OS"                             "24.04|$(date --date=2029-05-31 +%s)" "$(info ubuntu cycle)|$(eol ubuntu)"
check "Docker Engine on an EOL major"       "28|$(date --date=2026-05-13 +%s)" "$(info docker-engine cycle)|$(eol docker-engine)"

cached_ok="$(info grafana/grafana status)"
HOST_UPDATES_EOL_API="file:///nonexistent" "$TEST_DIR/../eol.sh" 2>/dev/null
check "unreachable API falls back to the cache" "$cached_ok|12.0" "$(info grafana/grafana status)|$(info grafana/grafana cycle)"
rm --recursive --force "$HOST_UPDATES_STATE_DIR/eol-cache"
HOST_UPDATES_EOL_API="file:///nonexistent" "$TEST_DIR/../eol.sh" 2>/dev/null
check "unreachable API without cache"      "lookup_failed" "$(info grafana/grafana status)"

echo ""
if (( failures > 0 )); then
    echo "$failures test(s) failed"
    exit 1
fi
echo "All tests passed"
