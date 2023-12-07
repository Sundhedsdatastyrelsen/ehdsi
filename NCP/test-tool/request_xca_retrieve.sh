#!/usr/bin/env bash

set -o errexit
set -o nounset
set -o pipefail

clojure -X request-builder/build \
        :hcp-template     templates/xca/retrieve/hcp.xml \
        :trc-template     templates/xca/retrieve/trc.xml \
        :request-template templates/xca/retrieve/request.xml \
        :private-key      client-dk.key \
        :cert             client-dk.crt
