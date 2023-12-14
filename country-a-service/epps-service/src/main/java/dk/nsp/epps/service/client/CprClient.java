package dk.nsp.epps.service.client;

import jakarta.xml.bind.JAXBElement;
import lombok.extern.slf4j.Slf4j;
import oio.medcom.cprservice._1_0.GetPersonInformationIn;
import oio.medcom.cprservice._1_0.GetPersonInformationOut;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

import javax.xml.namespace.QName;
import java.util.function.Consumer;

@Slf4j
public class CprClient extends WebServiceGatewaySupport {
    public static final String NAMESPACE_URI = "urn:oio:medcom:cprservice:1.0.4";

    @Value("${app.cpr.endpoint.url}")
    private String cprEndpointUrl;

    public GetPersonInformationOut getPersonInformation(String cpr) {
        log.info("Calling '{}' with a GetPersonInformationIn", cprEndpointUrl);
        return (GetPersonInformationOut) getWebServiceTemplate()
            .marshalSendAndReceive(cprEndpointUrl, getPersonInformationIn(cpr));
    }

    private JAXBElement<GetPersonInformationIn> getPersonInformationIn(String cpr) {
        return new JAXBElement<>(
            new QName(NAMESPACE_URI, "getPersonInformationIn"),
            GetPersonInformationIn.class,
            apply(new GetPersonInformationIn(), request -> {
                request.setPersonCivilRegistrationIdentifier(cpr);
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
