#!/usr/bin/env bash

set -o errexit
set -o nounset
set -o pipefail

SCRIPT_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

"$SCRIPT_DIR/run.sh" \
    -X request-builder/build \
    :hcp-template     "\"$SCRIPT_DIR/templates/xca/retrieve/hcp.xml\"" \
    :trc-template     "\"$SCRIPT_DIR/templates/xca/retrieve/trc.xml\"" \
    :request-template "\"$SCRIPT_DIR/templates/xca/retrieve/request.xml\"" \
    :private-key      "\"$SCRIPT_DIR/client-dk.key\"" \
    :cert             "\"$SCRIPT_DIR/client-dk.crt\""
