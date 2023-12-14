package dk.nsp.epps.service.client;

import dk.dkma.medicinecard.xml_schema._2015._06._01.GetPrescriptionRequestType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.PersonIdentifierType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e6.GetPrescriptionResponseType;
import jakarta.xml.bind.JAXBElement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

import javax.xml.namespace.QName;
import java.util.function.Consumer;

@Slf4j
public class FmkClient extends WebServiceGatewaySupport {
    public static final String NAMESPACE_URI = "http://www.dkma.dk/medicinecard/xml.schema/2015/06/01";

    @Value("${app.fmk.endpoint.url}")
    private String fmkEndpointUrl;

    public GetPrescriptionResponseType getPrescriptions(String cpr) {
        log.info("Calling '{}' with a GetPrescriptionRequestType", fmkEndpointUrl);
        var response = (JAXBElement<GetPrescriptionResponseType>) getWebServiceTemplate()
            .marshalSendAndReceive(fmkEndpointUrl, getPrescriptionRequest(cpr));
        return response.getValue();
    }

    private JAXBElement<GetPrescriptionRequestType> getPrescriptionRequest(String cpr) {
        return new JAXBElement<>(
            new QName(NAMESPACE_URI, "GetPrescriptionRequest"),
            GetPrescriptionRequestType.class,
            apply(new GetPrescriptionRequestType(), request -> {
                request.setPersonIdentifier(apply(new PersonIdentifierType(), id -> {
                    id.setSource("CPR");
                    id.setValue(cpr);
                }));
                request.getIdentifier().add(21298478L);
                request.setIncludeEffectuations(true);
            }));
    }

    /**
     * Utility function to make it possible to in-line initialize builder-less nested classes.
     * Basically a poor man's version of kotlin's apply method.
     */
    private <T> T apply(T value, Consumer<T> initializer) {
        initializer.accept(value);
        return value;
    }
}
