# Integration test tooling for OpenNCP server

The purpose of this tool is to construct valid SOAP-requests for testing the NCP server endpoints.

The requests need to contain various SAML 2.0 assertions with limited lifetimes, which is why we cannot simply store the requests but need to generate them on the fly.

The tool needs a certificate and a private key for signing the SAML assertions.
For the requests to work against an OpenNCP server then that server needs to trust the certificate, i.e., it must be added to the relevant truststore.

The repo contains the self-signed certificate `testcert`.
This certificate is put in `keystore/dev-truststore.jks` so it can be used if this is the truststore used by the server.
Obviously this is *not* the truststore that will be used in prod.

## Dependencies

To use the request builder it is sufficient to have a working Docker installation.

## Usage

There are three convenience scripts for building requests for different endpoints:

 - `request_xcpd.sh` for building a patient discovery request
 - `request_xca_query.sh` for building a document query request
 - `request_xca_retrieve.sh` for building a document retrieve request.

Running each of these should generate valid SOAP requests to stdout assembled from the templates in the `templates` directory.

Additionally, there are corresponding Curl scripts

 - `curl_xcpd.sh`
 - `curl_xca_query.sh`
 - `curl_xca_retrieve.sh`

containing the proper flags and headers to make a request to the server.
The hostname in those scripts might need to be adjusted to the hostname of the test server.
It is also possible to configure a tool like Postman with the same headers.

The Curl scripts take the request body from stdin, which means we can pipe the result from the request builder into the Curl script like so:

    ./request_xcpd.sh | ./curl_xcpd.sh

This command will send an XCPD request to the NCP server and print the response.
