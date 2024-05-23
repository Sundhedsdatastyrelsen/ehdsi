#!/usr/bin/env bash

set -o errexit
set -o nounset
set -o pipefail

curl --insecure \
     --cert client-dk.crt --key client-dk.key \
     --location 'https://localhost:6443/openncp-ws-server/services/XCPD_Service/' \
     --header 'Content-Type: application/soap+xml' \
     --header 'SOAPAction: urn:hl7-org:v3:PRPA_IN201305UV02:CrossGatewayPatientDiscovery' \
     --data-binary '@-'
