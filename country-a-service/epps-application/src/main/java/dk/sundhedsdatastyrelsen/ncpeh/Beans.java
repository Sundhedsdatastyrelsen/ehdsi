package dk.sundhedsdatastyrelsen.ncpeh;

import dk.nsp.test.idp.OrganizationIdentities;
import dk.nsp.test.idp.model.OrganizationIdentity;
import dk.sundhedsdatastyrelsen.ncpeh.service.undo.UndoDispensationRepository;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
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

    @Bean
    public UndoDispensationRepository undoDispensationRepository() {
        return new UndoDispensationRepository(undoDataSource());
    }

    /**
     * This object is used to identify the system for services expecting FOCES/VOCES certificates, as opposed to
     * IDWS or DGWS services that require personal access tokens representing the foreign healthcare professional.
     * For now, we use dk.nsp.test.idp.model.OrganizationIdentity.
     * This should be changed so we can phase out this dependency.
     */
    @Bean
    public OrganizationIdentity systemIdentity() {
        return OrganizationIdentities.sundhedsdatastyrelsen();
    }

    @Bean
    public Clock clock() {
        return Clock.systemDefaultZone();
    }
}
