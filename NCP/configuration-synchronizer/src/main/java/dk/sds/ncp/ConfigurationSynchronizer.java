package dk.sds.ncp;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchKey;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class ConfigurationSynchronizer {
    private static final Logger log = LoggerFactory.getLogger(ConfigurationSynchronizer.class);
    private static final Configuration configuration = Configuration.fromEnv();

    private static Map<String, String> readConfigFile(File configFile) {
        // We use Apache Commons Configuration to allow for interpolation (e.g. ${env:FOO_BAR})
        var m = new HashMap<String, String>();
        try {
            var c = new PropertiesConfiguration(configFile);

            c.setIncludesAllowed(true);
            c.getKeys().forEachRemaining(key -> {
                var val = c.getString(key);
                if (val.contains("${")) {
                    log.warn("Unresolved value: {}", val);
                }
                m.put(key, val);
            });
        } catch (ConfigurationException e) {
            throw new RuntimeException(e);
        }
        return Map.copyOf(m);

    }

    private static Map<String, String> readOpenNcpConfigurationFile() {
        return readConfigFile(new File(configuration.openNcpConfigurationFile()));
    }

    private static String connectionString() {
        return String.format(
                "jdbc:mariadb://%s:%s/%s?useSSL=false&allowPublicKeyRetrieval=true",
                configuration.dbHost(),
                configuration.dbPort(),
                configuration.dbDatabase());
    }

    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(connectionString(), configuration.dbUser(), configuration.dbPassword());
    }

    /**
     * Upserts the key/value pairs of {@code configMap} into the EHNCP_PROPERTY table.
     * Does not commit the database transaction.
     * @param conn Database connection
     * @param configMap Key/value pairs to upsert into EHNCP_PROPERTY.
     * @throws SQLException
     */
    private static void insertConfigMap(Connection conn, Map<String, String> configMap) throws SQLException {
        try (var ps = conn.prepareStatement(
                "INSERT INTO EHNCP_PROPERTY VALUES (?, ?, 0) " +
                        "ON DUPLICATE KEY UPDATE VALUE=?")) {
            for (var entry : configMap.entrySet()) {
                String k = entry.getKey();
                String v = entry.getValue();
                ps.setString(1, k);
                ps.setString(2, v);
                ps.setString(3, v);
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private static Map<String, String> readDbConfigMap(Connection conn) throws SQLException {
        final var m = new HashMap<String, String>();
        final var q = "SELECT NAME, VALUE FROM EHNCP_PROPERTY WHERE IS_SMP = 0";
        try (var ps = conn.prepareStatement(q)) {
            var rs = ps.executeQuery();
            while (rs.next()) {
                m.put(rs.getString("NAME"), rs.getString("VALUE"));
            }
        }
        return Map.copyOf(m);
    }

    public static void doUpdateConfigMap() {
        var newConf = readOpenNcpConfigurationFile();
        try (var conn = getConnection()) {
            conn.setAutoCommit(false);
            var before = readDbConfigMap(conn);
            insertConfigMap(conn, newConf);
            var after = readDbConfigMap(conn);
            conn.commit();
            var changeCount = after.entrySet().stream()
                    .filter(kv -> !kv.getValue().equals(before.get(kv.getKey())))
                    .count();
            log.info("Updated {} configuration values.", changeCount);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Watches a specific file for changes and runs {@code action} callback on each change.
     * Blocks indefinitely.
     */
    private static void watchFile(Path filePath, Runnable action) throws IOException, InterruptedException {
        try (var watchService = FileSystems.getDefault().newWatchService()) {
            var dirPath = filePath.getParent();
            var fileName = filePath.getFileName();
            dirPath.register(watchService, StandardWatchEventKinds.ENTRY_MODIFY);

            WatchKey key;
            while ((key = watchService.take()) != null) {
                for (var event : key.pollEvents()) {
                    log.debug("Event kind: {}. File affected: {}.", event.kind(), event.context());
                    if (event.kind().equals(StandardWatchEventKinds.ENTRY_MODIFY)) {
                        final Path changed = (Path) event.context();
                        if (changed.equals(fileName)) {
                            action.run();
                        }
                    }
                }
                key.reset();
            }
        }
    }

    public static void main(String[] args) throws Exception {
        log.info("Updating configuration on startup.");
        doUpdateConfigMap();
        log.info("Starting watch of {}", configuration.openNcpConfigurationFile());
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            log.info("Stopping watch and shutting down.");
        }));
        watchFile(Path.of(configuration.openNcpConfigurationFile()), () -> {
            log.info("Configuration file updated.");
            doUpdateConfigMap();
        });
    }
}
