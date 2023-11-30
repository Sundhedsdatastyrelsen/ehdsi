package dk.nsp.epps.mocks.cpr.ws;

import dk.nsp.epps.mocks.cpr.data.CprMockDataFactory;
import jakarta.xml.bind.JAXBElement;
import lombok.extern.slf4j.Slf4j;
import oio.medcom.cprservice._1_0.GetPersonInformationIn;
import oio.medcom.cprservice._1_0.GetPersonInformationOut;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Slf4j
@Endpoint
public class CprEndpoint {
    @PayloadRoot(namespace = CprMockDataFactory.NAMESPACE_URI, localPart = "getPersonInformationIn")
    @ResponsePayload
    public JAXBElement<GetPersonInformationOut> getPersonInformation(@RequestPayload GetPersonInformationIn request) {
        log.info("Returning mock result");
        return CprMockDataFactory.getPersonInformationOutAsJaxb();
    }
}
