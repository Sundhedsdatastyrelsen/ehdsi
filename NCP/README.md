# Introduction

Deployment of OpenNCP for Denmark.

# Getting Started

Dependencies:
 - Docker
 - Bash

Get the password for the CTS user (see https://dev.azure.com/globeteam/ePrescription/_wiki/wikis/ePrescription.wiki/917/NCP-credentials)
and place it in `cts_password.txt`.

Run `./run.sh up` to start the system.

# Tests

The `test` folder contains curl scripts for hitting the SOAP endpoints.
The curl commands can be imported into e.g. Postman, or run from the commandline.

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
