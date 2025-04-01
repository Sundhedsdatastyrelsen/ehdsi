package dk.sundhedsdatastyrelsen.ncpeh.nationalconnector.fhir;
import eu.europa.ec.sante.openncp.core.common.fhir.context.EuRequestDetails;
import eu.europa.ec.sante.openncp.core.common.fhir.services.DispatchingService;
import org.hl7.fhir.instance.model.api.IBaseResource;
import org.springframework.stereotype.Service;
@Service
public class DenmarkDispatchingService implements DispatchingService {
 @Override
 public <T extends IBaseResource> T dispatchSearch(EuRequestDetails euRequestDetails, String s) {
 throw new UnsupportedOperationException("Not implemented");
 }
  @Override
 public <T extends IBaseResource> T dispatchRead(EuRequestDetails euRequestDetails, String s) {
 throw new UnsupportedOperationException("Not implemented");
 }
}
