package dk.sundhedsdatastyrelsen.ncpeh.authentication;

import lombok.Builder;
import lombok.NonNull;
import org.w3c.dom.Node;

import java.security.cert.X509Certificate;
import java.util.List;

@Builder
public record BootstrapTokenParams(
    @NonNull X509Certificate certificate,
    @NonNull String nameId,
    @NonNull String nameIdFormat,
    @NonNull String issuerUri,
    @NonNull List<Attribute> attributes,
    @NonNull String audience
) {
    public record Attribute(
        @NonNull String name,
        @NonNull String friendlyName,
        @NonNull List<AttributeValue> values
    ) {}

    public sealed interface AttributeValue {
        record Text(String value) implements AttributeValue {}
        record XmlNode(Node value) implements AttributeValue {}
    }

    public static BootstrapTokenParamsBuilder builderWithDefaults() {
        return BootstrapTokenParams.builder()
            .nameIdFormat("urn:oasis:names:tc:SAML:1.1:nameid-format:unspecified")
            .issuerUri("https://ehdsi-idp.testkald.nspop.dk")
            .attributes(List.of());
    }
}
