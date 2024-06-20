package eu.europa.ec.sante.openncp.portal.service;

import eu.europa.ec.dynamicdiscovery.DynamicDiscovery;
import eu.europa.ec.dynamicdiscovery.core.locator.dns.impl.DefaultDNSLookup;
import eu.europa.ec.dynamicdiscovery.core.locator.impl.DefaultBDXRLocator;
import eu.europa.ec.dynamicdiscovery.core.reader.impl.DefaultBDXRReader;
import eu.europa.ec.dynamicdiscovery.core.security.impl.DefaultSignatureValidator;
import eu.europa.ec.dynamicdiscovery.exception.TechnicalException;
import eu.europa.ec.sante.openncp.common.configuration.ConfigurationManagerFactory;
import eu.europa.ec.sante.openncp.common.configuration.StandardProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

public class DynamicDiscoveryClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(DynamicDiscoveryClient.class);

    private static DynamicDiscovery instance = null;

    private DynamicDiscoveryClient() {
    }

    public static synchronized DynamicDiscovery getInstance() throws KeyStoreException, IOException, CertificateException,
            NoSuchAlgorithmException, TechnicalException {

        LOGGER.info("[Gateway] DynamicDiscovery getInstance()");
        if (instance == null) {
            LOGGER.debug("Instantiating new instance of DynamicDiscovery");
            var trustStore = KeyStore.getInstance("JKS");
            trustStore.load(new FileInputStream(ConfigurationManagerFactory.getConfigurationManager().getProperty(ServiceMetadataProviderProperties.GTW_TRUSTSTORE_PATH)),
                    ConfigurationManagerFactory.getConfigurationManager().getProperty(ServiceMetadataProviderProperties.GTW_TRUSTSTORE_PWD).toCharArray());

            var dynamicDiscoveryBuilder = ConfigurationManagerFactory.getConfigurationManager().initializeDynamicDiscoveryFetcher()
                    .locator(new DefaultBDXRLocator(ConfigurationManagerFactory.getConfigurationManager()
                            .getProperty(StandardProperties.SMP_SML_DNS_DOMAIN), new DefaultDNSLookup()))
                    .reader(new DefaultBDXRReader(new DefaultSignatureValidator(trustStore)));
            instance = dynamicDiscoveryBuilder.build();
        }
        return instance;
    }
}
