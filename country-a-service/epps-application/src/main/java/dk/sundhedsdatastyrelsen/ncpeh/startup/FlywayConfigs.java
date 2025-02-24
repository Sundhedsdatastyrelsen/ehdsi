package dk.sundhedsdatastyrelsen.ncpeh.startup;

import dk.sundhedsdatastyrelsen.ncpeh.Beans;
import org.flywaydb.core.Flyway;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;

public class FlywayConfigs {
    public static Flyway undoFlyway(DataSource undoDatasource) {
        return Flyway.configure().dataSource(undoDatasource).locations("classpath:db/migration/undo").load();
    }

    public static Flyway errorsFlyway(DataSource errorDatasource) {
        return Flyway.configure().dataSource(errorDatasource).locations("classpath:db/migration/errors").load();
    }
}
