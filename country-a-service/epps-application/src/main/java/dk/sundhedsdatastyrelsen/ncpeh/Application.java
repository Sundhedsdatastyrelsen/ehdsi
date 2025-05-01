package dk.sundhedsdatastyrelsen.ncpeh;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import java.util.logging.Logger;

@SpringBootApplication
@EnableScheduling
public class Application {
    private static final Logger logger = Logger.getLogger(Application.class.getName());
    private static final String[] REQUIRED_ENV_VARS = {
        "LMSFTP_USERNAME",
        "LMSFTP_PASSWORD",
        "COUNTRY_A_KEYSTORE_PASSWORD",
        "COUNTRY_A_CERTIFICATE_ALIAS",
        "COUNTRY_A_KEY_PASSWORD",
        "COUNTRY_A_KEYSTORE_LOCATION"
    };

    private static void validateEnvironmentVariables() {
        boolean hasMissingVars = false;
        for (String envVar : REQUIRED_ENV_VARS) {
            String value = System.getenv(envVar);
            if (value == null || value.trim().isEmpty()) {
                logger.severe("Required environment variable " + envVar + " is not set or empty");
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
