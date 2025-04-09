package dk.sundhedsdatastyrelsen.ncpeh.service;

import dk.sundhedsdatastyrelsen.ncpeh.locallms.DataProvider;
import dk.sundhedsdatastyrelsen.ncpeh.locallms.FtpConnection;
import dk.sundhedsdatastyrelsen.ncpeh.locallms.LocalLmsLoader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.SQLException;

@Service
@Slf4j
public class LocalLmsSynchronizer {
    private final FtpConnection.ServerInfo serverInfo;
    private final String jdbcUrl;
    private final DataProvider dataProvider;

    public LocalLmsSynchronizer(
        @Value("${local-lms.server:}")
        String server,
        @Value("${local-lms.port:}")
        int port,
        @Value("${local-lms.username:}")
        String username,
        @Value("${local-lms.password:}")
        String password,
        @Value("${local-lms.jdbcUrl:}")
        String jdbcUrl
    ) {
        this.serverInfo = new FtpConnection.ServerInfo(server, port, username, password);
        this.jdbcUrl = jdbcUrl;
        this.dataProvider = new DataProvider(jdbcUrl);
        initialize();
    }

    private void initialize() {
        if (dataProvider.lastImport().isEmpty()) {
            log.info("The local LMS database is unavailable. Attempting to create it.");
            try {
                LocalLmsLoader.fetchData(jdbcUrl, serverInfo);
            } catch (IOException | SQLException e) {
                log.error("Cannot create local LMS database. To avoid this error, either fix the underlying problem, or place a copy of the database from another source at: {}", jdbcUrl);
                throw new IllegalStateException(e);
            }
        }
    }

    @Scheduled(cron = "0 0 3 * * *") // Run at 03:00 daily
    public void nightlySync() {
        log.info("Synchronizing local LMS database");
        try {
            LocalLmsLoader.fetchData(jdbcUrl, serverInfo);
        } catch (IOException | SQLException e) {
            log.error("Cannot synchronize local LMS database.", e);
        }
    }
}
