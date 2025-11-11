package dk.sundhedsdatastyrelsen.ncpeh.authentication;

import dk.sundhedsdatastyrelsen.ncpeh.authentication.bootstraptoken.BootstrapTokenExchangeRequest;
import dk.sundhedsdatastyrelsen.ncpeh.authentication.bootstraptoken.BootstrapTokenParams;
import dk.sundhedsdatastyrelsen.ncpeh.authentication.bootstraptoken.OpenNcpAssertions;
import dk.sundhedsdatastyrelsen.ncpeh.authentication.idcard.DgwsIdCardRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.util.Optional;

/**
 * The public API for authentication actions, such as bootstrap-to-IDWS exchanges.
 */
public class AuthenticationService implements AuthenticationServiceInterface {
    private static final Logger log = LoggerFactory.getLogger(AuthenticationService.class);

    private final IdwsConfiguration idwsConfiguration;
    private final SosiStsClientIdws sosiStsClientIdws;
    private final DgwsIdCardRequest.Configuration dgwsConfiguration;

    public AuthenticationService(IdwsConfiguration idwsConfiguration, DgwsIdCardRequest.Configuration dgwsConfiguration) {
        this.idwsConfiguration = idwsConfiguration;
        this.dgwsConfiguration = dgwsConfiguration;
        // Nullable for easier testing of just one half.
        sosiStsClientIdws = Optional.ofNullable(idwsConfiguration)
            .map(IdwsConfiguration::uri)
            .map(SosiStsClientIdws::new)
            .orElse(null);
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
    @Override
    public EuropeanHcpIdwsToken xcaSoapHeaderToIdwsToken(String soapHeader, String audience) throws AuthenticationException {
        var openNcpAssertions = OpenNcpAssertions.fromSoapHeader(soapHeader);
        var bstParams = BootstrapTokenParams.fromOpenNcpAssertions(
            openNcpAssertions,
            audience,
            idwsConfiguration.issuer());
        log.info("Requesting IDWS token from SOSI STS for {}", audience);
        // we use the same certificate for the bootstrap tokens and the soap envelopes
        var bstRequest = BootstrapTokenExchangeRequest.of(bstParams, idwsConfiguration.certificateAndKey(), idwsConfiguration.certificateAndKey());
        return sosiStsClientIdws.exchangeBootstrapToken(bstRequest);
    }

    /**
     * Exchange an organization ID card for a DGWS assertion we can use to call Nsp services that only need an
     * organization identity.
     *
     * @param identity the identity to exchange to a dgws assertion.
     * @return an assertion we can use to call NSP services that require an organization identity.
     */
    @Override
    public DgwsAssertion nspDgwsIdentityToAssertion(NspDgwsIdentity identity) throws AuthenticationException {
        DgwsIdCardRequest request;
        if (identity instanceof NspDgwsIdentity.ReplaceWithIdws replaceIdentity) {
            // For integration tests or for development while we wait for external dependencies to be ready with IDWS.
            request = DgwsIdCardRequest.ofEmployeeIdentity(replaceIdentity, Instant.now(), dgwsConfiguration);
        } else {
            request = DgwsIdCardRequest.of(identity.systemCertificate(), Instant.now(), dgwsConfiguration);
        }
        return SosiStsClientDgws.exchangeIdCard(request, identity.stsUri());
    }
}
