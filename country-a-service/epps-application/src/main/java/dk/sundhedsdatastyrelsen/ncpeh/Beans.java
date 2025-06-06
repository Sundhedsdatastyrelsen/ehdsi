package dk.sundhedsdatastyrelsen.ncpeh;

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

    @Bean
    public Clock clock() {
        return Clock.systemDefaultZone();
    }
}
