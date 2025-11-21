package dk.sundhedsdatastyrelsen.ncpeh.service;

import dk.sundhedsdatastyrelsen.ncpeh.authentication.CertificateAndKey;
import dk.sundhedsdatastyrelsen.ncpeh.authentication.CertificateUtils;

import java.nio.file.Path;

public class SigningCertificate {
    private final CertificateAndKey certificateAndKey;

    /// @throws dk.sundhedsdatastyrelsen.ncpeh.authentication.AuthenticationException if certificate couldn't be loaded
    public SigningCertificate(
        String keystorePath,
        String keystoreAlias,
        String keystorePassword
    ) {
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
