#!/usr/bin/env bash
# Container entrypoint: serve /metrics (serve.py) and refresh the metrics on
# a schedule — collect.sh every HOST_UPDATES_COLLECT_INTERVAL seconds
# (default 6 h), eol.sh every HOST_UPDATES_EOL_INTERVAL seconds (default
# 24 h), both right away on start. A failing run is logged and retried at
# the next interval; the last good metrics keep being served, and their
# *_last_run_timestamp_seconds tells Grafana how old they are.

# shellcheck source=SCRIPTDIR/lib/common.sh
source "$(dirname "${BASH_SOURCE[0]}")/lib/common.sh"

COLLECT_INTERVAL="${HOST_UPDATES_COLLECT_INTERVAL:-21600}"
EOL_INTERVAL="${HOST_UPDATES_EOL_INTERVAL:-86400}"

python3 "$HOST_UPDATES_DIR/serve.py" &
server=$!
trap 'kill "$server" 2>/dev/null; exit 0' TERM INT

next_collect=0
next_eol=0
while kill -0 "$server" 2>/dev/null; do
    now="$(date +%s)"
    if (( now >= next_collect )); then
        "$HOST_UPDATES_DIR/collect.sh" || log "ERROR: collect.sh failed"
        next_collect=$(( now + COLLECT_INTERVAL ))
    fi
    if (( now >= next_eol )); then
        "$HOST_UPDATES_DIR/eol.sh" || log "ERROR: eol.sh failed"
        next_eol=$(( now + EOL_INTERVAL ))
    fi
    # In the background so the TERM trap runs without waiting out the sleep.
    sleep 60 &
    wait $! || true
done

log "ERROR: metrics server exited"
exit 1
