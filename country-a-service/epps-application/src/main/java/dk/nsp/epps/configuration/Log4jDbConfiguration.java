package dk.nsp.epps.configuration;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.AbstractLifeCycle;
import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.core.appender.db.jdbc.ColumnConfig;
import org.apache.logging.log4j.core.appender.db.jdbc.ConnectionSource;
import org.apache.logging.log4j.core.appender.db.jdbc.JdbcAppender;
import org.apache.logging.log4j.core.filter.ThresholdFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * After start up add a database logger.
 */
@Profile("!disabledblogging")
@Slf4j
@Configuration
public class Log4jDbConfiguration {
    @Autowired
    private Environment env;

    @Autowired
    private DataSource dataSource;

    @RequiredArgsConstructor
    private static class JdbcConnectionSource extends AbstractLifeCycle implements ConnectionSource {
        private final DataSource dataSource;
        private final String url;
        private final String username;
        private final String password;

        @Override
        public Connection getConnection() throws SQLException {
            try {
                return dataSource.getConnection();
            } catch (SQLException e) {
                // During shutdown the datasource might be closed so fallback to manual connection.
                return DriverManager.getConnection(url, username, password);
            }
        }

        @Override
        public String toString() {
            return dataSource.toString();
        }
    }

    @EventListener(ApplicationReadyEvent.class)
    public void afterStartup() {
        log.info("Initializing log4j2 database logging");
        String url = env.getProperty("spring.datasource.url");
        String username = env.getProperty("spring.datasource.username");
        String password = env.getProperty("spring.datasource.password");

        JdbcAppender appender = JdbcAppender.newBuilder()
            .setName("jdbcAppender")
            .setTableName("application_log")
            .setBufferSize(1)
            .setColumnConfigs(
                ColumnConfig.newBuilder().setName("log_time").setEventTimestamp(true).setUnicode(false).build(),
                ColumnConfig.newBuilder().setName("request_id").setPattern("%mdc{requestId}").setUnicode(false).build(),
                ColumnConfig.newBuilder().setName("log_level").setPattern("%level").setUnicode(false).build(),
                ColumnConfig.newBuilder().setName("logger").setPattern("%logger").setUnicode(false).build(),
                ColumnConfig.newBuilder().setName("message").setPattern("%message").setUnicode(false).build(),
                ColumnConfig.newBuilder().setName("stacktrace").setPattern("%ex{full}").setUnicode(false).build()
            )
            .setConnectionSource(new JdbcConnectionSource(dataSource, url, username, password))
            .setIgnoreExceptions(true)
            .setFilter(ThresholdFilter.createFilter(Level.INFO, null, null))
            .build();

        appender.start();
        ((Logger) LogManager.getRootLogger()).addAppender(appender);
    }
}
