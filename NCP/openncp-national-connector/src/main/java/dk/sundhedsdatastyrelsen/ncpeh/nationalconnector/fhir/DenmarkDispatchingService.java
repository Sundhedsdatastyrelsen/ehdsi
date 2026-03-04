package dk.sundhedsdatastyrelsen.ncpeh.nationalconnector.fhir;

import ca.uhn.fhir.rest.api.MethodOutcome;
import eu.europa.ec.sante.openncp.core.common.fhir.ReadResult;
import eu.europa.ec.sante.openncp.core.common.fhir.SearchResult;
import eu.europa.ec.sante.openncp.core.common.fhir.WriteResult;
import eu.europa.ec.sante.openncp.core.common.fhir.context.DispatchContext;
import eu.europa.ec.sante.openncp.core.common.fhir.services.FhirDispatchingService;

import org.hl7.fhir.instance.model.api.IBaseResource;
import org.springframework.stereotype.Service;

@Service
public class DenmarkDispatchingService implements FhirDispatchingService {
    @Override
    public SearchResult dispatchSearch(DispatchContext dispatchContext) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public ReadResult dispatchRead(DispatchContext dispatchContext) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public WriteResult dispatchWrite(DispatchContext dispatchContext, IBaseResource iBaseResource) {
        throw new UnsupportedOperationException("Not implemented");
    }
}
