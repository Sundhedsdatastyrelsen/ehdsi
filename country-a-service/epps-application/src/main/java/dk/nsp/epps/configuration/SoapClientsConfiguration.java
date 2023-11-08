package dk.nsp.epps.configuration;

import dk.nsp.epps.service.client.FmkClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

@Configuration
public class SoapClientsConfiguration {

    @Bean
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        // this package must match the package in the <generatePackage> specified in
        // pom.xml
        marshaller.setContextPaths("dk.dkma.medicinecard.xml_schema._2015._06._01");
        return marshaller;
    }

    @Bean
    public FmkClient fmkClient(Jaxb2Marshaller marshaller) {
        FmkClient client = new FmkClient();
        client.setDefaultUri("");
        client.setMarshaller(marshaller);
        client.setUnmarshaller(marshaller);
        return client;
    }
}
