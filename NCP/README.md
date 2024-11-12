# Introduction

Deployment of OpenNCP for Denmark.

The Danish OpenNCP setup includes a thin [national connector](./openncp-national-connector) which forwards requests to a separate country-a-service via HTTP REST.

# Getting Started

Dependencies:
 - Docker
 - Bash

Get the password for the CTS user (see https://dev.azure.com/globeteam/ePrescription/_wiki/wikis/ePrescription.wiki/917/NCP-credentials)
and place it in `cts_password.txt`.

Run `./run.sh up` to start the system.

## URL
This is a list of URLs that are used in the NCP setup:
- http://192.168.104.80:8097/openncp-gateway-backend/
- http://192.168.104.80:8097/openncp-web-manager/#/login

# Tests

The `test-tool` directory contains scripts for querying the SOAP endpoints, and a request builder tool for building valid SOAP request bodies with signed SAML2.0 assertions.

See `test-tool/README.md`.

# Generating self-signed test certificate

In order to connect with the NCP server via TLS we need to have our certificate in the trust store.
First, we need to generate a certificate with a private key:

    openssl req -newkey rsa:2048 -nodes -keyout test/client-dk.key -x509 -days 365 -out test/client-dk.crt

It will ask some questions about who will own the certificate.
The country code will be used by the NCP server to determine NCP-B country.

**Important note (2023-11-28):** There seem to be a bug in the NCP where it sometimes confuses the "distinguished name" with the "common name" of the certificate.
That means the services will not recognize that the client certificate comes from a certain country because it looks for "C=" within the common name part, and then perform an alternative search for the country code in the SOAP header signature certificate.
To avoid this, make sure that the common name includes (but does not begin with!) the string "C=DK" (or whatever country code that you want to test).
Due to another bug "C=" must not be at the beginning of the string - so it can look something like "Test, C=DK".
This mean that the distinguished name end up looking something like this: CN="Test, C=DK", O=SDS, ST=Copenhagen, C=DK.


The certificate will need to be imported into the truststore:

    keytool -import -trustcacerts -keystore openncp-configuration/cert/test-truststore.jks -alias client-dk -file test/client-dk.crt

Connect with curl using the new certificate:

    curl --cert test/client-dk.crt --key test/client-dk.key https://localhost:6443

# Connecting a debugger

The IntelliJ debugger can be connected to the server process like this:

In `docker-compose.yml`, under the container you want to debug (e.g. `openncp-server`), add the following line to the environment map:

    JAVA_TOOL_OPTIONS: "-agentlib:jdwp=transport=dt_socket,address=*:5005,server=y,suspend=n"

Additionally, make the port available by adding the following line to "ports":

    - "5005:5005"


Restart the service.

In IntelliJ, open the project that you are running in the container (e.g. the ehealth project: https://code.europa.eu/ehdsi/ehealth) and open Run > Edit Configurations...
Add a new "Remote JVM Debug configuration" and ensure that host and port are correct.
You should now be able to start the debugger and add breakpoints.


# SMP Data
(To check that the services exist on the current testa server, use the local url http://{{IP}}:8090 and add ?wsdl to see that it is running correctly)
## Request of Data Query
- Endpoint: https://ncp-ppt.dk.ehealth.testa.eu/openncp-ws-server/services/XCA_Service
- Description: XCA Service - Request of Data Query
- Technical Contact Url: [sundhedsdatastyrelsen.dk](https://sundhedsdatastyrelsen.dk)
- Technical Information URL: [sundhedsdatastyrelsen.dk](https://sundhedsdatastyrelsen.dk)

## Request of Data Retrieve
- Endpoint: https://ncp-ppt.dk.ehealth.testa.eu/openncp-ws-server/services/XCA_Service
- Description: XCA Service - Request of Data Retrieve
- Technical Contact Url: [sundhedsdatastyrelsen.dk](https://sundhedsdatastyrelsen.dk)
- Technical Information URL: [sundhedsdatastyrelsen.dk](https://sundhedsdatastyrelsen.dk)

## Patient Identification and Authentication
- Endpoint: https://ncp-ppt.dk.ehealth.testa.eu/openncp-ws-server/services/XCPD_Service
- Description: XCPD Service - Patient Identification and Authentication
- Technical Contact Url: [sundhedsdatastyrelsen.dk](https://sundhedsdatastyrelsen.dk)
- Technical Information URL: [sundhedsdatastyrelsen.dk](https://sundhedsdatastyrelsen.dk)

## Provisioning of Data - Provide
- Endpoint: https://ncp-ppt.dk.ehealth.testa.eu/openncp-ws-server/services/XDR_Service
- Description: XDR Service - Provisioning of Data - Provide
- Technical Contact Url: [sundhedsdatastyrelsen.dk](https://sundhedsdatastyrelsen.dk)
- Technical Information URL: [sundhedsdatastyrelsen.dk](https://sundhedsdatastyrelsen.dk)

## International Search Mask
- Description: International Search Mask
- Technical Contact Url: [sundhedsdatastyrelsen.dk](https://sundhedsdatastyrelsen.dk)
- Technical Information URL: [sundhedsdatastyrelsen.dk](https://sundhedsdatastyrelsen.dk)
- Extension: InternationalSearch_DK file from SMP-files folder

