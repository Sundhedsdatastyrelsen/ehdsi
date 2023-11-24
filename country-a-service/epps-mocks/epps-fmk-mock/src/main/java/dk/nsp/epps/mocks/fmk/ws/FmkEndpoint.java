package dk.nsp.epps.mocks.fmk.ws;

import dk.dkma.medicinecard.xml_schema._2015._06._01.GetPrescriptionRequestType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e6.GetPrescriptionResponseType;
import dk.nsp.epps.mocks.fmk.data.FmkMockDataFactory;
import jakarta.xml.bind.JAXBElement;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.namespace.QName;

@Endpoint
public class FmkEndpoint {
    public static final String NAMESPACE_URI = "http://www.dkma.dk/medicinecard/xml.schema/2015/06/01";

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "GetPrescriptionRequest")
    @ResponsePayload
    public JAXBElement<GetPrescriptionResponseType> getPrescription(@RequestPayload GetPrescriptionRequestType request) throws DatatypeConfigurationException {
        var result = FmkMockDataFactory.getPrescriptionResponse();
        return new JAXBElement<>(new QName(NAMESPACE_URI, "GetPrescriptionResponse"), GetPrescriptionResponseType.class, result);
    }
}
