# Certificates

In general, we try to use separate certificates for each connection we have.
This makes it easier to update a certificate (the "NC->OpenNCP TLS" certificate,
for example), without needing to also update the "NC->SOSI STS" certificate,
which has additional requirements (must be OCES3, must be whitelisted).

We realize that this introduces more complexity in management, and will pursue
automation to mitigate this.

## Production

Where there's an arrow, the "from" needs the private key and the certificate and
the "to" just needs the certificate.

- "EU TLS". A certificate used for communication with the other EU contact
  points. It needs to be acquired through the service provider. See
  [./SMP.md](./SMP.md) for how to update that.
- "EU SEAL". A certificate used for signing the metadata that we upload to the
  service provider. See [./SMP.md](./SMP.md) for more information. Not to be
  confused with java.seal, a library for communicating with DGWS services.
- "NC->OpenNCP TLS". For OpenNCP to be able to call the national connector. This
  certificate is self-signed and added to the OpenNCP truststore manually, see
  below.
- "NC<->Opt-Out mTLS". This is two certificates. We use mutual TLS between the
  National Connector and the Opt Out service, as they are on separate hosts,
  because the Opt Out service needs to be publicly accessible. These
  certificates are self-signed and manually added to the truststores on both
  sides.
- "Opt-Out Public TLS". The user-facing website of the opt-out service uses a
  certificate for https. This is handled by LetsEncrypt.
- "NC->SOSI STS". An OCES-3 certificate used for communicating with the national
  health infrastructure through Sosi STS. We use the same certificate for
  service identification and identity provider. We get this from SIT, and it
  needs to be whitelisted by SOSI.

## Test and development

In test and development environments, we need separate versions of the
production certificates, as well as the following:

- "NC DGWS" and "NC DGWS IDP". Two test certificates for employee DGWS calls to
  the national health infrastructure, for example for creating prescriptions. We
  use the ones provided by the NSP test library.

## Updating NC->OpenNCP TLS

Go to the root of the repository and run the following to generate a new
certificate, valid for almost 10 years:

```
openssl req -new -subj "/C=DK/CN=national-connector/O=Sundhedsdatastyrelsen" \
  -addext "subjectAltName = DNS:national-connector,DNS:ncp-training.ehdsi.sundhedsdata.dk" \
  -newkey rsa:2048 -noenc -x509 -days 3650 -keyout nc.key -out nc.crt \
&& keytool -keystore ./NCP/keystore/dev-truststore.jks -storepass changeit -delete -alias national-connector \
&& keytool -keystore ./NCP/keystore/dev-truststore.jks -storepass changeit -import -file ./nc.crt -alias national-connector -noprompt \
&& cp ./nc.crt ./national-connector/secrets/NC_NCP_CERTIFICATE \
&& cp ./nc.key ./national-connector/secrets/NC_NCP_PRIVATE_KEY \
&& mv ./nc.crt ./national-connector/config/dev-ncp-ssl.crt \
&& mv ./nc.key ./national-connector/config/dev-ncp-ssl.key
```

This creates a new certificate with `openssl`, removes the previous one from the
dev truststore in OpenNCP with `keytool`, imports the new one, and moves and
copies the private key and certificate to where they are needed in the national
connector. This makes it all work for local development and running the services
with docker.

If you're updating the certificate in a deployment, you need to update the
`national-connector/secrets/...` files and the keystore used by that deployment
(`NCP/keystore/training-truststore.jks` in training for example). If `keytool`
is not available on the deployment machine, download the truststore, make the
changes locally, and reupload the truststore.

Finally, you will need to restart the OpenNCP server and national connector
docker containers. Make sure you restart with the same image as they're already
running with - and `docker restart` is not enough. Some variant of
`cd NCP && docker compose up -d tomcat_node_a:VERSION` should do it, and similar
for the national connector.

## Updating NC<->Opt-Out mTLS in development

The dev environment runs this repository and the FSEU repository directly, so
the mTLS pair lives in committed keystores on both sides:

- `national-connector/config/dev-opt-out-keystore.p12` (NC client certificate)
  and `dev-opt-out-truststore.p12` (trusts the FSEU server certificate)
- `<FSEU>/config/dev-server-keystore.p12` (FSEU server certificate) and
  `dev-server-truststore.p12` (trusts the NC client certificate and the curl
  client `<FSEU>/dev-client.crt`)

[`regenerate-dev-certificates.sh`](../regenerate-dev-certificates.sh) rebuilds
all four from scratch with the `changeit` password the dev configs expect:

```
./regenerate-dev-certificates.sh /path/to/FSEU
```

The server certificate's CN must equal the hostname the national connector
dials (`opt-out.host` in `national-connector/config/application.yml`), because
the Java client verifies the CN and ignores SANs. The default is `localhost`;
set `SERVER_CN` if the dev host dials something else. See the script header for
the other overrides.

Commit the changed keystores in both repositories, pull on the dev host, and
recreate the `fseu` and national connector containers with the images they
already run (`docker compose up -d`, not `docker restart`). The cert-exporter
picks up the new expiry dates within a minute.

## Monitoring and alerting

The certificates can be monitored in Grafana via the cert-exporter dashboard.
The dashboard is based on Prometheus data exposed by the cert-exporter service.

Grafana can be used for alerting, e.g. by notifying when a certificate is about to expire.
