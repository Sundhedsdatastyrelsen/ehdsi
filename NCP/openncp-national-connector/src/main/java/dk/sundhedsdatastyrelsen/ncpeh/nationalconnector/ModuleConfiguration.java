package dk.sundhedsdatastyrelsen.ncpeh.nationalconnector;
import dk.sundhedsdatastyrelsen.ncpeh.nationalconnector.fhir.DenmarkDispatchingService;
import dk.sundhedsdatastyrelsen.ncpeh.nationalconnector.xca.DocumentSearch;
import dk.sundhedsdatastyrelsen.ncpeh.nationalconnector.xcpd.PatientSearch;
import dk.sundhedsdatastyrelsen.ncpeh.nationalconnector.xdr.DocumentSubmit;
import eu.europa.ec.sante.openncp.core.common.fhir.services.DispatchingService;
import eu.europa.ec.sante.openncp.core.server.api.ihe.xca.DocumentSearchInterface;
import eu.europa.ec.sante.openncp.core.server.api.ihe.xcpd.PatientSearchInterface;
import eu.europa.ec.sante.openncp.core.server.api.ihe.xdr.DocumentSubmitInterface;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
@Configuration
public class ModuleConfiguration {
 @Bean
 @Primary
 public DispatchingService dispatchingServiceDk() {
 return new DenmarkDispatchingService();
 }
  @Bean
 @Primary
 public DocumentSearchInterface documentSearchDk() {
 return new DocumentSearch();
 }
  @Bean
 @Primary
 public PatientSearchInterface patientSearchDk() {
 return new PatientSearch();
 }
  @Bean
 @Primary
 public DocumentSubmitInterface documentSubmitDk() {
 return new DocumentSubmit();
 }
}
