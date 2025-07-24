package dk.sundhedsdatastyrelsen.ncpeh.authentication;

import lombok.Builder;
import lombok.NonNull;
import org.w3c.dom.Node;

import java.security.cert.X509Certificate;

@Builder
public record BootstrapTokenParams(
    @NonNull X509Certificate certificate,
    @NonNull String nameId,
    @NonNull String nameIdFormat,
    Node attributeStatement,
    @NonNull String audience
) {}
