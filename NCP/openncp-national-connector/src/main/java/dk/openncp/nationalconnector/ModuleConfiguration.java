package dk.openncp.nationalconnector;

import dk.openncp.nationalconnector.fhir.DenmarkDispatchingService;
import dk.openncp.nationalconnector.xca.DocumentSearch;
import dk.openncp.nationalconnector.xcpd.PatientSearch;
import dk.openncp.nationalconnector.xdr.DocumentSubmit;
import eu.europa.ec.sante.openncp.core.common.fhir.services.DispatchingService;
import eu.europa.ec.sante.openncp.core.server.api.ihe.xca.DocumentSearchInterface;
import eu.europa.ec.sante.openncp.core.server.api.ihe.xcpd.PatientSearchInterface;
import eu.europa.ec.sante.openncp.core.server.api.ihe.xdr.DocumentSubmitInterface;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModuleConfiguration {
    @Bean
    public DispatchingService dispatchingServiceDk() {
        return new DenmarkDispatchingService();
    }

    @Bean
    public DocumentSearchInterface documentSearchDk() {
        return new DocumentSearch();
    }

    @Bean
    public PatientSearchInterface patientSearchDk() {
        return new PatientSearch();
    }

    @Bean
    public DocumentSubmitInterface documentSubmitDk() {
        return new DocumentSubmit();
    }
}
