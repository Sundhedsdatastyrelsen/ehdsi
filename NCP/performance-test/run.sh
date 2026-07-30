#!/usr/bin/env bash

## Builds the k6 bundle and runs one of the scenarios in src/main.js.
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

if [ ! -d node_modules ]; then
    npm ci
fi

npm run build

exec k6 run -e "SCENARIO=$SCENARIO" "$@" dist/main.js
