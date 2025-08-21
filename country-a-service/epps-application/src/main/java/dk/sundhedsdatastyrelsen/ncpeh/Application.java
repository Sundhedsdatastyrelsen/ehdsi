package dk.sundhedsdatastyrelsen.ncpeh;

import org.apache.ws.security.WSSConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class Application {
    static {
        WSSConfig.init();
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
