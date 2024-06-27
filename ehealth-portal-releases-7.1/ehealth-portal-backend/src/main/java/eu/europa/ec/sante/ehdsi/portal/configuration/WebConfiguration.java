package eu.europa.ec.sante.ehdsi.portal.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import tr.com.srdc.epsos.util.Constants;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Configuration
@EnableWebMvc
public class WebConfiguration implements WebMvcConfigurer {

    static {
        System.setProperty("javax.net.ssl.trustStore", Constants.TRUSTSTORE_PATH);
        System.setProperty("javax.net.ssl.trustStorePassword", Constants.TRUSTSTORE_PASSWORD);
        System.setProperty("javax.net.ssl.trustStoreType", "JKS");
        System.setProperty("javax.net.ssl.keyStore", Constants.SC_KEYSTORE_PATH);
        System.setProperty("javax.net.ssl.keyStorePassword", Constants.SC_KEYSTORE_PASSWORD);
    }

//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**")
//                .allowedHeaders("Access-Control-Allow-Origin", "Access-Control-Allow-Headers")
//                .allowedOrigins("http://localhost:8181");
//        WebMvcConfigurer.super.addCorsMappings(registry);
//    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        var stringHttpMessageConverter = new StringHttpMessageConverter(StandardCharsets.UTF_8);
        converters.add(stringHttpMessageConverter);
    }
}
