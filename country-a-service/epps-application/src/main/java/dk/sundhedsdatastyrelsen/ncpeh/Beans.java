package dk.sundhedsdatastyrelsen.ncpeh;

import dk.sundhedsdatastyrelsen.ncpeh.client.NspDgwsIdentity;
import dk.sundhedsdatastyrelsen.ncpeh.service.SigningCertificate;
import dk.sundhedsdatastyrelsen.ncpeh.service.undo.UndoDispensationRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.net.URI;
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
        @Value("app.sosi.endpoint.url") String systemStsUri,
        SigningCertificate signingCertificate
    ) {
        return new NspDgwsIdentity.System(URI.create(systemStsUri), signingCertificate.getCertificateAndKey());
    }

    @Bean
    public Clock clock() {
        return Clock.systemDefaultZone();
    }
}
