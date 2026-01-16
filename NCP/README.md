# Introduction

Deployment of OpenNCP for Denmark.

The Danish OpenNCP setup includes a thin [national connector](./openncp-national-connector) which forwards requests to a separate national-connector via HTTP REST.

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

# Setting up a server environment
To ensure regular synchronization from the CTS, add the following line (edited appropriately for the path) to the cronfile using `crontab -e` (using the same user running the docker containers in general):
```bash
0 3 * * * cd /var/ehdsi/NCP && /usr/bin/docker compose run tsam-synchronizer
```

# How it's built
Take a look in the docker file for specifics, but we checkout the EHEALTH code from Europa, and then inject our own country-a code (in openncp-national-connector), and replace the pom in ncp_a with our pom (which is dependant on openncp-national-connector). We also change the logging by replacing their logback.xml

## Updating to newer NCP versions
The above means that when we update to newer NCP versions, we should do the following:
- compare the changes in `openncp-application/openncp-application-server/src/main/resources/logback.xml` from the previous version to the new one, and consider whether anything needs to be reflected in `common/logback.xml`.
- compare the configuration files in `atna-resources` with their counterparts. See `atna-resources/README.md`.

# Tests

The `test-tool` directory contains scripts for querying the SOAP endpoints, and a request builder tool for building valid SOAP request bodies with signed SAML2.0 assertions.

See `test-tool/README.md`.

# Generating self-signed test certificate

See [../docs/certificates.md](../docs/certificates.md).

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

