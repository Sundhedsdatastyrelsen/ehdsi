package dk.sundhedsdatastyrelsen.ncpeh;

import dk.sundhedsdatastyrelsen.ncpeh.locallms.DataProvider;
import dk.sundhedsdatastyrelsen.ncpeh.locallms.FtpConnection;
import dk.sundhedsdatastyrelsen.ncpeh.locallms.LocalLmsLoader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;

@Service
@Slf4j
public class LocalLmsSynchronizer {
    private final FtpConnection.ServerInfo serverInfo;
    private final DataProvider dataProvider;
    private final DataSource dataSource;
    private final boolean skipSync;

    public LocalLmsSynchronizer(
        @Value("${lmsftp.server:}")
        String server,
        @Value("${lmsftp.port:}")
        int port,
        @Value("${lmsftp.username:}")
        String username,
        @Value("${lmsftp.password:}")
        String password,
        @Qualifier("localLmsDataSource") DataSource dataSource,
        @Value("${lmsftp.skipSync:false}") boolean skipSync
        ) {
        this.serverInfo = new FtpConnection.ServerInfo(server, port, username, password);
        this.dataSource = dataSource;
        this.dataProvider = new DataProvider(dataSource);
        this.skipSync = skipSync;
            initialize();
    }

    private void initialize() {
        if (dataProvider.lastImport().isEmpty()) {
            log.info("The local LMS database is unavailable. Attempting to create it.");
            if (skipSync) {
                log.warn("Sync job skipped. No data will be loaded into the local LMS database.");
                return;
            }
            try {
                LocalLmsLoader.fetchData(serverInfo, dataSource);
            } catch (IOException | SQLException e) {
                log.error("Cannot create local LMS database. To avoid this error, either fix the underlying problem, or use a copy of the database from another source");
                throw new IllegalStateException(e);
            }
        }
    }

    @Scheduled(cron = "0 0 3 * * *") // Run at 03:00 daily
    public void nightlySync() {
        if (skipSync) {
            log.warn("Sync job skipped");
            return;
        }
        log.info("Synchronizing local LMS database");
        try {
            LocalLmsLoader.fetchData(serverInfo, dataSource);
        } catch (IOException | SQLException e) {
            log.error("Cannot synchronize local LMS database.", e);
        }
    }
}
