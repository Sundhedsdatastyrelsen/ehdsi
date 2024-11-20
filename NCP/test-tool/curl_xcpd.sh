#!/usr/bin/env bash

set -o errexit
set -o nounset
set -o pipefail

curl --insecure \
     --cert testcert.cer --key testcert.p8.pem \
     --location 'https://localhost:8443/openncp-ws-server/services/XCPD_Service/' \
     --header 'Content-Type: application/soap+xml' \
     --header 'SOAPAction: urn:hl7-org:v3:PRPA_IN201305UV02:CrossGatewayPatientDiscovery' \
     --data-binary '@-'
