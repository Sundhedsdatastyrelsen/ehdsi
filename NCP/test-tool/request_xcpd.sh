#!/usr/bin/env bash

set -o errexit
set -o nounset
set -o pipefail

clojure -X request-builder/build \
        :hcp-template     templates/xcpd/hcp.xml \
        :request-template templates/xcpd/request.xml \
        :private-key      client-dk.key \
        :cert             client-dk.crt
