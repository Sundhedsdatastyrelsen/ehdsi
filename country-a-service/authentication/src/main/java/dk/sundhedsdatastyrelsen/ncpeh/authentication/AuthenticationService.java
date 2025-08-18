package dk.sundhedsdatastyrelsen.ncpeh.authentication;

import dk.sundhedsdatastyrelsen.ncpeh.authentication.bootstraptoken.BootstrapTokenExchangeRequest;
import dk.sundhedsdatastyrelsen.ncpeh.authentication.bootstraptoken.BootstrapTokenParams;
import dk.sundhedsdatastyrelsen.ncpeh.authentication.bootstraptoken.OpenNcpAssertions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

/**
 * The public API for authentication actions, such as bootstrap-to-IDWS exchanges.
 */
public class AuthenticationService {
    private static final Logger log = LoggerFactory.getLogger(AuthenticationService.class);

    public interface Config {
        URI sosiStsUri();

        InputStream keystore();

        String keyAlias();

        String keystorePassword();

        String issuer();
    }

    private final Config config;
    private final SosiStsClient sosiStsClient;
    private final CertificateAndKey certificateAndKey;

    public AuthenticationService(Config config) {
        this.config = config;
        this.sosiStsClient = new SosiStsClient(config.sosiStsUri());
        try (var is = config.keystore()) {
            this.certificateAndKey = CertificateUtils.loadCertificateFromKeystore(
                is,
                config.keyAlias(),
                config.keystorePassword());
        } catch (AuthenticationException | IOException e) {
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
        log.info("Requesting IDWS token from SOSI STS for {}", audience);
        // we use the same certificate for the bootstrap tokens and the soap envelopes
        var bstRequest = BootstrapTokenExchangeRequest.of(bstParams, certificateAndKey);
        return sosiStsClient.exchangeBootstrapToken(bstRequest);
    }
}
