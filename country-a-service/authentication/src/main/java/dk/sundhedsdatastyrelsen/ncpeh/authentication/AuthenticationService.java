package dk.sundhedsdatastyrelsen.ncpeh.authentication;

import dk.sundhedsdatastyrelsen.ncpeh.authentication.bootstraptoken.BootstrapTokenExchangeRequest;
import dk.sundhedsdatastyrelsen.ncpeh.authentication.bootstraptoken.BootstrapTokenParams;
import dk.sundhedsdatastyrelsen.ncpeh.authentication.bootstraptoken.OpenNcpAssertions;

import java.net.URI;
import java.nio.file.Path;

/**
 * The public API for authentication actions, such as bootstrap-to-IDWS exchanges.
 */
public class AuthenticationService {
    public record Config(
            URI sosiStsUri,
            Path keystorePath,
            String keyAlias,
            String keystorePassword,
            String issuer
    ) {}

    private final Config config;
    private final SosiStsClient sosiStsClient;
    private final CertificateAndKey certificateAndKey;

    public AuthenticationService(Config config) {
        this.config = config;
        this.sosiStsClient = new SosiStsClient(config.sosiStsUri());
        try {
            this.certificateAndKey = CertificateUtils.loadCertificateFromKeystore(
                    config.keystorePath(),
                    config.keyAlias(),
                    config.keystorePassword());
        } catch (AuthenticationException e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * Retrieve an eHDSI IDWS XUA token from the SOSI STS based on the values in the HCP and TRC assertions
     * in the SOAP header of an XCA request.
     *
     * @param soapHeader the SOAP header from OpenNCP. We trust that its signatures are verified by OpenNCP.
     * @param audience   what we want access to, e.g. "https://fmk".
     * @return a token which can be used to gain access to the requested service
     * @throws AuthenticationException if something goes wrong
     */
    public EuropeanHcpIdwsToken xcaSoapHeaderToIdwsToken(String soapHeader, String audience) throws AuthenticationException {
        var openNcpAssertions = OpenNcpAssertions.fromSoapHeader(soapHeader);
        var bstParams = BootstrapTokenParams.fromOpenNcpAssertions(
                openNcpAssertions,
                certificateAndKey,
                audience,
                config.issuer());
        // we use the same certificate for the bootstrap tokens and the soap envelopes
        var bstRequest = BootstrapTokenExchangeRequest.of(bstParams, certificateAndKey);
        return sosiStsClient.exchangeBootstrapToken(bstRequest);
    }
}
