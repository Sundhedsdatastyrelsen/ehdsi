import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application-test.properties")
@ComponentScan(basePackages = "dk.sundhedsdatastyrelsen.ncpeh.lms") // where your service resides
public class LmsTestConfig {
}
