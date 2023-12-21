package dk.nsp.epps.mocks.fmk.ws;

import dk.dkma.medicinecard.xml_schema._2015._06._01.GetPrescriptionRequestType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e6.GetPrescriptionResponseType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e6.PrescriptionType;
import dk.nsp.epps.mocks.fmk.data.FmkMockDataFactory;
import jakarta.xml.bind.JAXBElement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import javax.xml.namespace.QName;
import java.util.stream.Collectors;

@Slf4j
@Endpoint
public class FmkEndpoint {
    public static final String REQUEST_NAMESPACE_URI = "http://www.dkma.dk/medicinecard/xml.schema/2015/06/01";
    public static final String RESPONSE_NAMESPACE_URI = "http://www.dkma.dk/medicinecard/xml.schema/2015/06/01/E6";

    @PayloadRoot(namespace = REQUEST_NAMESPACE_URI, localPart = "GetPrescriptionRequest")
    @ResponsePayload
    public JAXBElement<GetPrescriptionResponseType> getPrescription(@RequestPayload GetPrescriptionRequestType request) {
        var result = FmkMockDataFactory.getPrescriptionResponse();
        log.info(
            "Returning mock prescription(s): {}",
            result.getPrescription().stream()
                .map(PrescriptionType::getIdentifier)
                .map(Object::toString)
                .collect(Collectors.joining(","))
        );
        return new JAXBElement<>(new QName(RESPONSE_NAMESPACE_URI, "GetPrescriptionResponse"),
            GetPrescriptionResponseType.class, result);
    }
}
