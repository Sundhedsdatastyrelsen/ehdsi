package dk.sundhedsdatastyrelsen.ncpeh.config;

import dk.sundhedsdatastyrelsen.ncpeh.service.SigningCertificate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public record AuthenticationServiceConfig(
    @Value("${app.sosi.endpoint.url}") String sosiStsUri,
    SigningCertificate signingCertificate,
    @Value("${app.sosi.issuer}") String issuer,
    @Value("${app.sosi-dgws.cvr}") String dgwsCvr,
    @Value("${app.sosi-dgws.issuer}") String dgwsIssuer,
    @Value("${app.sosi-dgws.it-provider}") String dgwsItProvider,
    @Value("${app.sosi-dgws.care-provider}") String dgwsCareProvider
) {}
