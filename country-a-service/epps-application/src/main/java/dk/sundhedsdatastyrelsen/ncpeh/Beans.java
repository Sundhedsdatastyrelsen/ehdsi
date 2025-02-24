package dk.sundhedsdatastyrelsen.ncpeh;

import dk.sundhedsdatastyrelsen.ncpeh.lms.LmsDataRepository;
import dk.sundhedsdatastyrelsen.ncpeh.service.exception.ErrorRecordingRepository;
import dk.sundhedsdatastyrelsen.ncpeh.service.undo.UndoDispensationRepository;
import dk.sundhedsdatastyrelsen.ncpeh.startup.FlywayConfigs;
import org.flywaydb.core.Flyway;
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

    @Bean
    @ConfigurationProperties("spring.lms-datasource")
    public DataSource lmsDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    @ConfigurationProperties("spring.error-datasource")
    public DataSource errorDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    public UndoDispensationRepository undoDispensationRepository() {
        return new UndoDispensationRepository(undoDataSource());
    }

    @Bean
    public LmsDataRepository lmsDataRepository() {
        return new LmsDataRepository(lmsDataSource());
    }

    @Bean
    public ErrorRecordingRepository errorRecordingRepository() {
        return new ErrorRecordingRepository(errorDataSource());
    }

    @Bean
    public Flyway undoFlywayMigration() {
        var flyway = FlywayConfigs.undoFlyway(undoDataSource());
        flyway.migrate();
        return flyway;
    }

    @Bean
    public Flyway errorFlywayMigration() {
        var flyway = FlywayConfigs.errorsFlyway(errorDataSource());
        flyway.migrate();
        return flyway;
    }

    @Bean
    public Clock clock() {
        return Clock.systemDefaultZone();
    }
}
