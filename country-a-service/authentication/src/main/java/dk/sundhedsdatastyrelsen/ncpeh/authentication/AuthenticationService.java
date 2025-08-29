package dk.sundhedsdatastyrelsen.ncpeh.authentication;

import dk.sundhedsdatastyrelsen.ncpeh.authentication.bootstraptoken.BootstrapTokenExchangeRequest;
import dk.sundhedsdatastyrelsen.ncpeh.authentication.bootstraptoken.BootstrapTokenParams;
import dk.sundhedsdatastyrelsen.ncpeh.authentication.bootstraptoken.OpenNcpAssertions;
import dk.sundhedsdatastyrelsen.ncpeh.authentication.idcard.DgwsIdCardRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

/**
 * The public API for authentication actions, such as bootstrap-to-IDWS exchanges.
 */
public class AuthenticationService {
    private static final Logger log = LoggerFactory.getLogger(AuthenticationService.class);

    private final String issuer;
    private final SosiStsClientIdws sosiStsClientIdws;
    private final CertificateAndKey certificateAndKey;

    public AuthenticationService(URI sosiStsUri, CertificateAndKey signingKey, String issuer) {
        this.sosiStsClientIdws = new SosiStsClientIdws(sosiStsUri);
        this.certificateAndKey = signingKey;
        this.issuer = issuer;
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
            issuer);
        log.info("Requesting IDWS token from SOSI STS for {}", audience);
        // we use the same certificate for the bootstrap tokens and the soap envelopes
        var bstRequest = BootstrapTokenExchangeRequest.of(bstParams, certificateAndKey);
        return sosiStsClientIdws.exchangeBootstrapToken(bstRequest);
    }

    public static DgwsAssertion nspDgwsIdentityToAssertion(NspDgwsIdentity identity) throws AuthenticationException {
        var request = DgwsIdCardRequest.of(
            identity.systemCertificate(), Instant.now()
                .truncatedTo(ChronoUnit.SECONDS));
        return SosiStsClientDgws.exchangeIdCard(request, identity.stsUri());
    }
}
