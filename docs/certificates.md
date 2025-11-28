# Certificates

The certificate used for TLS between OpenNCP Server and National Connector needs to be updated once per year.

# One-liners

These are all relative to the root of the repository.

To generate a new certificate, use this:

```
openssl req -new -subj "/C=DK/CN=national-connector/O=Sundhedsdatastyrelsen" \
  -addext "subjectAltName = DNS:national-connector,DNS:ncp-training.ehdsi.sundhedsdata.dk" \
  -newkey rsa:2048 -noenc -x509 -days 365 -keyout nc.key -out nc.crt
```

Delete the previous certificate from the truststore:

```
keytool -keystore ./NCP/keystore/dev-truststore.jks -storepass changeit -delete -alias national-connector
```

Import the certificate into the OpenNCP server truststore:

```
keytool -keystore ./NCP/keystore/dev-truststore.jks -storepass changeit -import -file ./nc.crt -alias national-connector -noprompt
```

Create a pkcs12 keystore for the national connector:

```
openssl pkcs12 -export -in nc.crt -inkey nc.key -out ./national-connector/config/dev-keystore.jks -name national-connector -password pass:Test.1234
```

Done.
