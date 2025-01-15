The service metadata provider/locator service (SMP/SML) is a centralised service managed by EC which provide metadata for the different NCP services.  The NCP clients (country B) use this metadata to locate the services and the patient search masks.

Any NCP deployment that should be reachable by a client needs to be registered in a SMP. There are three environments, training, acceptance, production, and as such it is only possible to have three usable deployments at any given time (unless we create our own SMP).

To configure an NCP server (country A) we need to upload signed metadata to the SMP corresponding to our desired environment.  The "SEAL" certificate is used for signing the metadata. The TLS certificate needs to be registered in the SMP before we can upload the data. (Request a new test certificate at https://gazelle.ehdsi.eu/gss/home.seam, since self-signed certificates will not work (despite being accepted by the SMP site)).

To install the certificate first log in to the SMP UI *with Google Chrome* (the webapp sometimes hide the relevant buttons in Safari and Firefox). The training environment SMP UI is available at: https://smp-ehealth-trn.acc.edelivery.tech.ec.europa.eu/ui. The login credentials are currently stored by CFB and HBG.  Go to Profile -> Certificate and ensure that the relevant TLS certificate is uploaded and marked as "Active".  We have had trouble with having more than one certificate marked as active, so make sure that only 1 certificate is active at a time.

Once the TLS certificate has been activated, we can start generating and uploading the service metadata.  This can be done through the "web manager" interface. For the training environment, this should be accessible at https://ncp-training.ehdsi.sundhedsdata.dk:1442/openncp-web-manager/. (Remember that the DKSUND wifi does not allow access to "non-standard" ports, so go through a phone connection.) Generate, sign, and upload the file via the SMP Editor.


## Training environment

https://smp-ehealth-trn.acc.edelivery.tech.ec.europa.eu/ui


## Debugging SSL issues
Extract the certificate from the keystore, along with the private key, and use the following command to verify the certificate.
```
openssl s_client \
  -connect yourserver.example.com:443 \
  -cert client.crt \
  -key client.key \
  -CAfile trusted-ca-chain.pem \
  -showcerts
```
The CA file can just be downloaded from the SMP site, by trying to access the endpoint URI, with the command
```

```
