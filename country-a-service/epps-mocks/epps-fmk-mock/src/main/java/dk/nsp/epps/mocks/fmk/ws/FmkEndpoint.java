package dk.nsp.epps.mocks.fmk.ws;

import dk.dkma.medicinecard.xml_schema._2015._06._01.GetPrescriptionRequestType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e6.GetPrescriptionResponseType;
import dk.nsp.epps.mocks.fmk.data.FmkMockDataFactory;
import jakarta.xml.bind.JAXBElement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import javax.xml.namespace.QName;

@Slf4j
@Endpoint
public class FmkEndpoint {
    public static final String REQUEST_NAMESPACE_URI = "http://www.dkma.dk/medicinecard/xml.schema/2015/06/01";
    public static final String RESPONSE_NAMESPACE_URI = "http://www.dkma.dk/medicinecard/xml.schema/2015/06/01/E6";

    @PayloadRoot(namespace = REQUEST_NAMESPACE_URI, localPart = "GetPrescriptionRequest")
    @ResponsePayload
    public JAXBElement<GetPrescriptionResponseType> getPrescription(@RequestPayload GetPrescriptionRequestType request) {
        log.info("Returning mock result");
        var result = FmkMockDataFactory.getPrescriptionResponse();
        return new JAXBElement<>(new QName(RESPONSE_NAMESPACE_URI, "GetPrescriptionResponse"), GetPrescriptionResponseType.class, result);
    }
}
