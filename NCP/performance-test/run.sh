#!/usr/bin/env bash

## Builds the k6 bundle and runs one of the scenarios in src/main.js. Each run writes
## results/<scenario>-<date>/ -- see the "Results" section of the README.
##
##   ./run.sh smoke
##   ./run.sh load
##   RATE=3 DURATION=2m ./run.sh load
##   BASE_URL=https://ncp.example.dk ./run.sh smoke

set -o errexit
set -o nounset
set -o pipefail

cd "$(dirname "${BASH_SOURCE[0]}")"

SCENARIO="${1:-smoke}"
shift || true

RUN_DATE="$(date +%Y-%m-%d)"
RESULTS_DIR="results/${SCENARIO}-${RUN_DATE}"
mkdir -p "$RESULTS_DIR"

# info.txt is the only part of a result a machine cannot know: which host generated the
# load, and what the target was actually running. It is written once and then left
# alone, so re-running on the same day cannot silently discard hand-written notes.
if [ ! -f "$RESULTS_DIR/info.txt" ]; then
    cat > "$RESULTS_DIR/info.txt" <<EOF
Fill in the "..." lines before committing this result. Without them the numbers are
not comparable to another run: a laptop against Docker Desktop is a different system
than a workstation against the training server, however similar the figures look.

Load generated on: $(uname -srm) / $(sysctl -n machdep.cpu.brand_string 2>/dev/null || echo "unknown CPU"), $(getconf _NPROCESSORS_ONLN) cores
k6:                $(k6 version 2>/dev/null | head -1)
Target:            ${BASE_URL:-https://localhost:8443}
System under test: ...
Notes:             ...
EOF
fi

if [ ! -d node_modules ]; then
    npm ci
fi

npm run build

exec k6 run \
    -e "SCENARIO=$SCENARIO" \
    -e "RESULTS_DIR=$RESULTS_DIR" \
    -e "RUN_DATE=$RUN_DATE" \
    -e "GIT_COMMIT=$(git rev-parse --short HEAD 2>/dev/null || echo unknown)$(git diff --quiet HEAD 2>/dev/null || echo '-dirty')" \
    "$@" dist/main.js
