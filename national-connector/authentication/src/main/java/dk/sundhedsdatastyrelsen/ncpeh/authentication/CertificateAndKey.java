package dk.sundhedsdatastyrelsen.ncpeh.authentication;

import lombok.NonNull;

import java.security.PrivateKey;
import java.security.cert.X509Certificate;

public record CertificateAndKey(
    @NonNull X509Certificate certificate,
    @NonNull PrivateKey privateKey
) {}
