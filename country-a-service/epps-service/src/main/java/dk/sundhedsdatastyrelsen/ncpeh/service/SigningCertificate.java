package dk.sundhedsdatastyrelsen.ncpeh.service;

import dk.sundhedsdatastyrelsen.ncpeh.authentication.AuthenticationException;
import dk.sundhedsdatastyrelsen.ncpeh.authentication.CertificateAndKey;
import dk.sundhedsdatastyrelsen.ncpeh.authentication.CertificateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.file.Path;

@Component
public class SigningCertificate {
    private final CertificateAndKey certificateAndKey;

    @Autowired
    public SigningCertificate(
        @Value("${signing-certificate-keystore.path}") String keystorePath,
        @Value("${signing-certificate-keystore.alias}") String keystoreAlias,
        @Value("${signing-certificate-keystore.password}") String keystorePassword
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
