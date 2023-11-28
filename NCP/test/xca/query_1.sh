curl --insecure \
     --cert test/client-dk.crt --key test/client-dk.key \
     --location 'https://localhost:6443/openncp-ws-server/services/XCA_Service/' \
     --header 'Content-Type: application/soap+xml' \
     --header 'SOAPAction: urn:ihe:iti:2007:CrossGatewayRetrieve' \
     --data-binary '@test/xca/query_1_body.xml'
