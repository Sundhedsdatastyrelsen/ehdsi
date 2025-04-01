package dk.sds.ncp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
public record Configuration(
String dbHost,
String dbPort,
String dbDatabase,
String dbUser,
String dbPassword,
String openNcpConfigurationFile
) {
 private static final Logger log = LoggerFactory.getLogger(Configuration.class);
  public static Configuration fromEnv() {
 return new Configuration(
 envOrDefault("MARIADB_HOST", "localhost"),
  envOrDefault("MARIADB_PORT", "3306"),
 "ehealth_properties",
 env("MARIADB_USERNAME"),
 env("MARIADB_PASSWORD"),
 env("OPENNCP_CONFIGURATION_FILE"));
 }
  private static String env(String key) {
 var res = System.getenv(key);
 if (res == null) {
  log.error("Environment variable: {} not set, aborting.", key);
  throw new RuntimeException("Missing environment variable: " + key);
 }
 return res;
 }
  private static String envOrDefault(String key, String def) {
 var res = System.getenv(key);
 if (res == null) {
  log.warn("Environment variable: {} not set, using default value: \"{}\" instead", key, def);
  return def;
 }
 return res;
 }
}
