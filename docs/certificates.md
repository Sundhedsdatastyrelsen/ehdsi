# Certificates

We use the following certificates in production:

- The certificate used for communication within EU, this needs to be signed by
  the service provider.
- Both sides of the mTLS communication between OpenNCP Server and the national
  connector. These we generate.
- Both sides of the mTLS communication between the national connector and the
  opt out service. These we generate.
- The user-facing website of the opt-out service uses a certificate for https.
  This is handled by LetsEncrypt.
- An OCES-3 certificate used for communicating with the national health
  infrastructure through Sosi STS. We use the same certificate for service
  identification and identity provider. We get this from SIT.

In test and development environments, we need copies of the production
certificates, as well as the following:

- Test certificates for employee DGWS calls to the national health
  infrastructure, for example for creating prescriptions. We use the ones
  provided by the NSP test library.

# Updating the national connector -> openncp certificate

These are all relative to the root of the repository.

Generate a new certificate, valid for about 10 years:

```
openssl req -new -subj "/C=DK/CN=national-connector/O=Sundhedsdatastyrelsen" \
  -addext "subjectAltName = DNS:national-connector,DNS:ncp-training.ehdsi.sundhedsdata.dk" \
  -newkey rsa:2048 -noenc -x509 -days 3650 -keyout nc.key -out nc.crt
```

Delete the previous certificate from the truststore:

```
keytool -keystore ./NCP/keystore/dev-truststore.jks -storepass changeit -delete -alias national-connector
```

Import the certificate into the OpenNCP server truststore:

```
keytool -keystore ./NCP/keystore/dev-truststore.jks -storepass changeit -import -file ./nc.crt -alias national-connector -noprompt
```

Move the key and certificate to the national connector, so it can use them:

```
mv ./nc.crt ./national-connector/config/dev-ncp-ssl.crt && mv ./nc.key ./national-connector/config/dev-ncp-ssl.key
```

Done.
