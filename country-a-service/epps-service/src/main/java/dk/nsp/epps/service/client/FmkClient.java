package dk.nsp.epps.service.client;

import dk.dkma.medicinecard.xml_schema._2015._06._01.GetPrescriptionRequestType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.PersonIdentifierType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e6.GetPrescriptionResponseType;
import dk.sdsd.dgws._2010._08.NameFormat;
import dk.sdsd.dgws._2010._08.OrgUsingID;
import dk.sdsd.dgws._2012._06.ObjectFactory;
import dk.sdsd.dgws._2012._06.WhitelistingHeader;
import dk.sosi.seal.model.Reply;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.JAXBException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.namespace.QName;
import javax.xml.transform.dom.DOMResult;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.function.Consumer;

@Slf4j
@Component
public class FmkClient {
    private static final String NAMESPACE_URI = "http://www.dkma.dk/medicinecard/xml.schema/2015/06/01";
    private final URI serviceUri;
    private final SosiClient sosiClient;
    private final JAXBContext jaxbContext;

    public FmkClient(@Value("${app.fmk.endpoint.url}") String fmkEndpointUrl, SosiClient sosiClient) throws URISyntaxException, JAXBException {
        this.serviceUri = new URI(fmkEndpointUrl);
        this.sosiClient = sosiClient;
        this.jaxbContext = JAXBContext.newInstance(
            "dk.dkma.medicinecard.xml_schema._2015._06._01"
                + ":dk.dkma.medicinecard.xml_schema._2015._06._01.e6"
                + ":dk.sdsd.dgws._2012._06"
        );
    }

    private <T> Element toElement(JAXBElement<T> jaxbElement) throws JAXBException {
        DOMResult res = new DOMResult();
        jaxbContext.createMarshaller().marshal(jaxbElement, res);
        return ((Document) res.getNode()).getDocumentElement();
    }

    private JAXBElement<WhitelistingHeader> getWhitelistingHeader() {
        final var whitelistingHeader = new WhitelistingHeader();
        whitelistingHeader.setSystemName("ePPS PoC");
        whitelistingHeader.setSystemOwnerName("Sundhedsdatastyrelsen");
        whitelistingHeader.setSystemVersion("0.1.0");
        whitelistingHeader.setOrgResponsibleName("Sundhedsdatastyrelsen");
        whitelistingHeader.setOrgUsingName("Sundhedsdatastyrelsen");
        whitelistingHeader.setOrgUsingID(apply(new OrgUsingID(), orgUsingID -> {
            orgUsingID.setNameFormat(NameFormat.MEDCOM_LOCATIONNUMBER);
            // TODO: Don't use Region Hovedstaden's location number
            orgUsingID.setValue("5790000120512");
        }));
        whitelistingHeader.setRequestedRole("Farmaceut");
        final var factory = new ObjectFactory();
        return factory.createWhitelistingHeader(whitelistingHeader);
    }

    public GetPrescriptionResponseType getPrescriptions(String cpr) throws JAXBException, IOException, InterruptedException {
        final var requestBody = getPrescriptionRequest(cpr);
        final Reply response;
        try {
            log.info("Calling '{}' with a GetPrescriptionRequestType", serviceUri);
            response = sosiClient.sendNspRequest(
                serviceUri,
                toElement(requestBody),
                "http://www.dkma.dk/medicinecard/xml.schema/2015/06/01/E6#GetPrescription",
                List.of(toElement(getWhitelistingHeader()))
            );
        } catch (ServiceResponseException e) {
            throw new RuntimeException(e);
        }
        final var jaxbResponse = jaxbContext.createUnmarshaller()
            .unmarshal(response.getBody(), GetPrescriptionResponseType.class);
        return jaxbResponse.getValue();
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
