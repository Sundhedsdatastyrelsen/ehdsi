package dk.sundhedsdatastyrelsen.ncpeh.service;

import dk.sundhedsdatastyrelsen.ncpeh.authentication.AuthenticationException;
import dk.sundhedsdatastyrelsen.ncpeh.authentication.CertificateAndKey;
import dk.sundhedsdatastyrelsen.ncpeh.authentication.CertificateUtils;

import java.nio.file.Path;

public class SigningCertificate {
    private final CertificateAndKey certificateAndKey;

    public SigningCertificate(
        String keystorePath,
        String keystoreAlias,
        String keystorePassword
    ) throws AuthenticationException {
        this.certificateAndKey = CertificateUtils.loadCertificateFromKeystore(
            Path.of(keystorePath),
            keystoreAlias,
            keystorePassword);
    }

    public SigningCertificate(CertificateAndKey certificateAndKey) {
        this.certificateAndKey = certificateAndKey;
    }

    public CertificateAndKey getCertificateAndKey() {
        return certificateAndKey;
    }
}
