package dk.sundhedsdatastyrelsen.ncpeh.service.exception;

import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

public class ErrorRecordingRepository {
    private final JdbcTemplate jdbcTemplate;

    public ErrorRecordingRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    
}
