#!/usr/bin/env bash

set -o errexit
set -o nounset
set -o pipefail

clojure -X request-builder/build \
        :hcp-template     templates/xca/query/hcp.xml \
        :trc-template     templates/xca/query/trc.xml \
        :request-template templates/xca/query/request.xml \
        :private-key      client-dk.key \
        :cert             client-dk.crt
