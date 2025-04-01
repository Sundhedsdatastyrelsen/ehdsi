package dk.sundhedsdatastyrelsen.ncpeh.lms;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import javax.sql.DataSource;
@Configuration
@PropertySource("classpath:application-test.properties")
@ComponentScan(basePackages = "dk.sundhedsdatastyrelsen.ncpeh.lms") // where your service resides
public class LmsTestConfig {
 @Bean
 public DataSource dataSource() {
 // Create an in-memory SQLite DataSource
 SingleConnectionDataSource ds = new SingleConnectionDataSource("jdbc:sqlite::memory:", true);
 // Ensures the single connection is never truly closed, so we don't lose the in-memory DB
 ds.setSuppressClose(true);
 return ds;
 }
  @Bean
 public LmsDataRepository lmsDataRepository() {
 return new LmsDataRepository(dataSource());
 }
}
