package dk.sundhedsdatastyrelsen.ncpeh;

import dk.sundhedsdatastyrelsen.ncpeh.authentication.AuthenticationException;
import dk.sundhedsdatastyrelsen.ncpeh.authentication.AuthenticationService;
import dk.sundhedsdatastyrelsen.ncpeh.authentication.CachedAuthenticationService;
import dk.sundhedsdatastyrelsen.ncpeh.authentication.NspDgwsIdentity;
import dk.sundhedsdatastyrelsen.ncpeh.authentication.idcard.DgwsIdCardRequest;
import dk.sundhedsdatastyrelsen.ncpeh.client.AuthorizationRegistryClient;
import dk.sundhedsdatastyrelsen.ncpeh.client.CprClient;
import dk.sundhedsdatastyrelsen.ncpeh.client.FskClient;
import dk.sundhedsdatastyrelsen.ncpeh.client.MinLogClient;
import dk.sundhedsdatastyrelsen.ncpeh.client.NspClientDgws;
import dk.sundhedsdatastyrelsen.ncpeh.config.AuthenticationServiceConfig;
import dk.sundhedsdatastyrelsen.ncpeh.service.CprService;
import dk.sundhedsdatastyrelsen.ncpeh.service.InformationCardService;
import dk.sundhedsdatastyrelsen.ncpeh.service.MinLogService;
import dk.sundhedsdatastyrelsen.ncpeh.service.PatientSummaryService;
import dk.sundhedsdatastyrelsen.ncpeh.service.PrescriptionService;
import dk.sundhedsdatastyrelsen.ncpeh.service.SigningCertificate;
import dk.sundhedsdatastyrelsen.ncpeh.service.undo.UndoDispensationRepository;
import jakarta.xml.bind.JAXBException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.time.Clock;

@Configuration
public class Beans {

    @Bean
    @Primary
    @ConfigurationProperties("spring.undo-datasource")
    public DataSource undoDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean("localLmsDataSource")
    @ConfigurationProperties("spring.local-lms-datasource")
    public DataSource localLmsDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean("jobQueueDataSource")
    @ConfigurationProperties("spring.job-queue-datasource")
    public DataSource jobQueueDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    public UndoDispensationRepository undoDispensationRepository() {
        return new UndoDispensationRepository(undoDataSource());
    }

    /**
     * This object is used to identify the system for services expecting FOCES/VOCES certificates, as opposed to
     * IDWS or DGWS services that require personal access tokens representing the foreign healthcare professional.
     */
    @Bean
    public NspDgwsIdentity.System systemIdentity(
        @Value("${app.sosi-dgws.endpoint.url}") String systemStsUri,
        SigningCertificate signingCertificate
    ) {
        return new NspDgwsIdentity.System(URI.create(systemStsUri), signingCertificate.getCertificateAndKey());
    }

    @Bean
    public Clock clock() {
        return Clock.systemDefaultZone();
    }

    @Bean
    public PrescriptionService prescriptionService(
        @Value("${app.fmk.endpoint.url}") String fmkEndpointUrl,
        SigningCertificate signingCertificate,
        UndoDispensationRepository undoDispensationRepository,
        @Qualifier("localLmsDataSource") DataSource lmsDataSource,
        AuthorizationRegistryClient authorizationRegistry,
        NspDgwsIdentity.System systemIdentity
    ) {
        return new PrescriptionService(
            fmkEndpointUrl,
            signingCertificate,
            undoDispensationRepository,
            lmsDataSource,
            authorizationRegistry,
            systemIdentity);
    }

    @Bean
    public SigningCertificate signingCertificate(
        @Value("${signing-certificate-keystore.path}") String keystorePath,
        @Value("${signing-certificate-keystore.alias}") String keystoreAlias,
        @Value("${signing-certificate-keystore.password}") String keystorePassword
    ) throws AuthenticationException {
        return new SigningCertificate(keystorePath, keystoreAlias, keystorePassword);
    }

    @Bean
    public AuthorizationRegistryClient authorizationRegistryClient(
        @Value("${app.authorization-registry.endpoint.url}") String serviceUri,
        NspClientDgws nspClientDgws
    ) {
        return new AuthorizationRegistryClient(serviceUri, nspClientDgws);
    }

    @Bean
    public PatientSummaryService patientSummaryService(
        NspDgwsIdentity.System systemCaller,
        @Value("${app.fsk.endpoint.url}") String fskEndpointUrl,
        @Qualifier("jobQueueDataSource") DataSource jobQueueDataSource,
        @Value("${app.minlog.max-attempts:3}") int maxAttempts,
        @Value("${app.minlog.endpoint.url}") String minlogEndpointUrl,
        NspClientDgws nspClientDgws
    ) throws JAXBException, URISyntaxException, SQLException {
        var minLogClient = new MinLogClient(minlogEndpointUrl, nspClientDgws);
        var fskClient = new FskClient(fskEndpointUrl, nspClientDgws);
        var minLogService = new MinLogService(minLogClient, systemCaller, jobQueueDataSource, maxAttempts);
        var informationCardService = new InformationCardService(fskClient, minLogService, systemCaller);
        return new PatientSummaryService(informationCardService);
    }

    @Bean
    public CprService cprService(
        NspDgwsIdentity.System systemCaller,
        @Value("${app.cpr.endpoint.url}") String serviceUri,
        NspClientDgws nspClientDgws
    ) throws JAXBException, URISyntaxException {
        var cprClient = new CprClient(serviceUri, nspClientDgws);
        return new CprService(cprClient, systemCaller);
    }

    @Bean
    public AuthenticationService authenticationService(
        AuthenticationServiceConfig authServiceConfig
    ) {
        return new CachedAuthenticationService(
            new AuthenticationService.IdwsConfiguration(
                URI.create(authServiceConfig.sosiStsUri()),
                authServiceConfig.signingCertificate().getCertificateAndKey(),
                authServiceConfig.issuer()),
            new DgwsIdCardRequest.Configuration(
                authServiceConfig.dgwsCvr(),
                authServiceConfig.dgwsIssuer(),
                authServiceConfig.dgwsItProvider(),
                authServiceConfig.dgwsCareProvider())
        );
    }

    @Bean
    public NspClientDgws nspClientDgws(AuthenticationService authenticationService) {
        return new NspClientDgws(authenticationService);
    }
}
