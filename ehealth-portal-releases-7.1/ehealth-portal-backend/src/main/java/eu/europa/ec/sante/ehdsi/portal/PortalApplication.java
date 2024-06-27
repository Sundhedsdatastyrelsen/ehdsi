package eu.europa.ec.sante.ehdsi.portal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class PortalApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(PortalApplication.class, args);
    }

//    @Bean
//    public FilterRegistrationBean simpleCorsFilter() {
//
//        var urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
//        var corsConfiguration = new CorsConfiguration();
//        corsConfiguration.setAllowCredentials(true);
//        //  URL below needs to match the Vue client URL and port
//        List<String> origins = new ArrayList<>();
//        origins.add("http://92.158.26.104:8181");
//        origins.add("http://localhost:8181");
//        origins.add("*");
//        //corsConfiguration.setAllowedOrigins(origins);
//        corsConfiguration.setAllowedOrigins(Collections.singletonList("*"));
//        corsConfiguration.setAllowedMethods(Collections.singletonList("*"));
//        corsConfiguration.setAllowedHeaders(Collections.singletonList("*"));
//        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
//
//        var filterFilterRegistrationBean = new FilterRegistrationBean<>(new CorsFilter(urlBasedCorsConfigurationSource));
//        filterFilterRegistrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE);
//
//        return filterFilterRegistrationBean;
//    }
}
