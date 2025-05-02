package dk.sundhedsdatastyrelsen.ncpeh;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.List;

@SpringBootApplication
@EnableScheduling
public class Application {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(Application.class);
    private static final List<String> REQUIRED_ENV_VARS = List.of(
        "LMSFTP_USERNAME",
        "LMSFTP_PASSWORD",
        "COUNTRY_A_KEYSTORE_PASSWORD",
        "COUNTRY_A_CERTIFICATE_ALIAS",
        "COUNTRY_A_KEY_PASSWORD",
        "COUNTRY_A_KEYSTORE_LOCATION");

    private static void validateEnvironmentVariables() {
        boolean hasMissingVars = false;
        for (String envVar : REQUIRED_ENV_VARS) {
            String value = System.getenv(envVar);
            if (value == null || value.trim().isEmpty()) {
                log.error("Required environment variable {} is not set or empty", envVar);
                hasMissingVars = true;
            }
        }
        if (hasMissingVars) {
            throw new IllegalStateException("Missing required environment variables. Check the logs for details. Required environmental variables are outlined in .env.defaults");
        }
    }

    public static void main(String[] args) {
        validateEnvironmentVariables();
        SpringApplication.run(Application.class, args);
    }
}
