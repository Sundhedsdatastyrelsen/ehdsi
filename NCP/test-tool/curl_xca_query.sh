#!/usr/bin/env bash

set -o errexit
set -o nounset
set -o pipefail

curl --insecure \
     --cert client-dk.crt --key client-dk.key \
     --location 'https://localhost:6443/openncp-ws-server/services/XCA_Service/' \
     --header 'Content-Type: application/soap+xml' \
     --header 'SOAPAction: urn:ihe:iti:2007:CrossGatewayQuery' \
     --data-binary '@-'
