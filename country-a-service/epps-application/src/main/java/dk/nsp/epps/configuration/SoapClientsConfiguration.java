package dk.nsp.epps.configuration;

import dk.nsp.epps.service.client.CprClient;
import dk.nsp.epps.service.client.FmkClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

@Configuration
public class SoapClientsConfiguration {

    @Bean(name = "fmkMarshaller")
    public Jaxb2Marshaller fmkMarshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPaths(
            "dk.dkma.medicinecard.xml_schema._2015._06._01",
            "dk.dkma.medicinecard.xml_schema._2015._06._01.e6"
        );
        return marshaller;
    }

    @Bean
    public FmkClient fmkClient(@Qualifier("fmkMarshaller") Jaxb2Marshaller marshaller) {
        FmkClient client = new FmkClient();
        client.setDefaultUri("");
        client.setMarshaller(marshaller);
        client.setUnmarshaller(marshaller);
        return client;
    }
}
