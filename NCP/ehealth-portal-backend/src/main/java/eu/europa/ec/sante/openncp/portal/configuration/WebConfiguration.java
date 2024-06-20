package eu.europa.ec.sante.openncp.portal.configuration;

import eu.europa.ec.sante.openncp.common.Constant;
import eu.europa.ec.sante.openncp.common.configuration.ConfigurationManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Configuration
@EnableWebMvc
public class WebConfiguration implements WebMvcConfigurer {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebConfiguration.class);

    @Autowired
    public ConfigurationManager configurationManager;

    @PostConstruct
    public void addSSLProperties() {
        LOGGER.info("Setting SSL System Properties");
        System.setProperty("javax.net.ssl.trustStore", configurationManager.getProperty(Constant.TRUSTSTORE_PATH));
        System.setProperty("javax.net.ssl.trustStorePassword", configurationManager.getProperty(Constant.TRUSTSTORE_PASSWORD));
        System.setProperty("javax.net.ssl.trustStoreType", "JKS");
        System.setProperty("javax.net.ssl.keyStore", configurationManager.getProperty(Constant.SC_KEYSTORE_PATH));
        System.setProperty("javax.net.ssl.keyStorePassword", configurationManager.getProperty(Constant.SC_KEYSTORE_PASSWORD));
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
