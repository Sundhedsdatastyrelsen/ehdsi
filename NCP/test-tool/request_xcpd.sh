#!/usr/bin/env bash

set -o errexit
set -o nounset
set -o pipefail

SCRIPT_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

"$SCRIPT_DIR/run.sh" \
    -X request-builder/build \
    :hcp-template     "\"$SCRIPT_DIR/templates/xcpd/hcp.xml\"" \
    :request-template "\"$SCRIPT_DIR/templates/xcpd/request.xml\"" \
    :private-key      "\"$SCRIPT_DIR/testcert.p8.pem\"" \
    :cert             "\"$SCRIPT_DIR/testcert.cer\""
