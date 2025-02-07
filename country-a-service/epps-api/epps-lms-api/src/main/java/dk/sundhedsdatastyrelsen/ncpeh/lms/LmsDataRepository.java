package dk.sundhedsdatastyrelsen.ncpeh.lms;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class LmsDataRepository {
    private final DataSource dataSource;

    public LmsDataRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    
}
