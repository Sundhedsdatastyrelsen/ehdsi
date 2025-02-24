package dk.sundhedsdatastyrelsen.ncpeh;

import dk.sundhedsdatastyrelsen.ncpeh.lms.LmsDataRepository;
import dk.sundhedsdatastyrelsen.ncpeh.service.mapping.LmsDataLookupService;
import dk.sundhedsdatastyrelsen.ncpeh.service.undo.UndoDispensationRepository;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
public class Beans {
    @Bean(name = "undo-database")
    @ConfigurationProperties("spring.undo-datasource")
    @Primary
    public DataSource undoDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "lms-database")
    @ConfigurationProperties("spring.lms-datasource")
    public DataSource lmsDataSource() {
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


}
