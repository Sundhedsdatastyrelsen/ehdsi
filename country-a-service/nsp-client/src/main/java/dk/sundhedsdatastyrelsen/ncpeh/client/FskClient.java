package dk.sundhedsdatastyrelsen.ncpeh.client;

import dk.dkma.medicinecard.xml_schema._2015._06._01.ConsentHeaderType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.CreateDrugMedicationResponseType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.CreatePharmacyEffectuationResponseType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.CreatePrescriptionResponseType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.GetDrugMedicationRequestType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.GetPrescriptionRequestType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.UndoEffectuationResponseType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e2.CreateDrugMedicationRequestType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e2.CreatePharmacyEffectuationRequestType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e2.CreatePrescriptionRequestType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e2.GetDrugMedicationResponseType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e2.GetMedicineCardRequestType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e2.GetMedicineCardResponseType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e5.StartEffectuationRequestType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e5.UndoEffectuationRequestType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e6.GetPrescriptionResponseType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e6.StartEffectuationResponseType;
import dk.nsp.test.idp.model.Identity;
import dk.sdsd.dgws._2010._08.NameFormat;
import dk.sdsd.dgws._2010._08.PredefinedRequestedRole;
import dk.sdsd.dgws._2012._06.WhitelistingHeader;
import dk.sosi.seal.model.Reply;
import dk.sundhedsdatastyrelsen.ncpeh.client.utils.ClientUtils;
import ihe.iti.xds_b._2007.ObjectFactory;
import ihe.iti.xds_b._2007.RetrieveDocumentSetRequestType;
import ihe.iti.xds_b._2007.RetrieveDocumentSetResponseType;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.JAXBException;
import oasis.names.tc.ebxml_regrep.xsd.query._3.AdhocQueryRequest;
import oasis.names.tc.ebxml_regrep.xsd.query._3.AdhocQueryResponse;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.w3c.dom.Element;

import javax.xml.namespace.QName;
import java.net.URI;
import java.net.URISyntaxException;

@Component
public class FskClient {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(FskClient.class);

    private static final oasis.names.tc.ebxml_regrep.xsd.query._3.ObjectFactory fac =
        new oasis.names.tc.ebxml_regrep.xsd.query._3.ObjectFactory();

    private static final ihe.iti.xds_b._2007.ObjectFactory factory = new ObjectFactory();

    private final URI serviceUri;
    private final JAXBContext jaxbContext;

    public FskClient(@Value("${app.fsk.endpoint.url}") String fskEndpointUrl) throws URISyntaxException, JAXBException {
        this.serviceUri = new URI(fskEndpointUrl);
        this.jaxbContext = JAXBContext.newInstance(
            "oasis.names.tc.ebxml_regrep.xsd.query._3"
                + ":dk.sdsd.dgws._2012._06"
                + ":ihe.iti.xds_b._2007"
        );
    }

    public AdhocQueryResponse list(
        AdhocQueryRequest request,
        Identity caller
    ) throws JAXBException {
        QName qname = new QName("urn:oasis:names:tc:ebxml-regrep:xsd:query:3.0", "AdhocQueryRequest");

        JAXBElement<AdhocQueryRequest> jaxbElement = new JAXBElement<>(
            qname,
            AdhocQueryRequest.class,
            request
        );

        return makeFskRequest(
            "/nspservices/sfskreg",
            jaxbElement,
            "urn:ihe:iti:2007:RegistryStoredQuery",
            AdhocQueryResponse.class,
            caller
        );
    }

    public RetrieveDocumentSetResponseType getDocument(
        RetrieveDocumentSetRequestType request,
        Identity caller
    ) throws JAXBException {
        QName qname = new QName("urn:ihe:iti:xds-b:2007", "RetrieveDocumentSetRequest");

        JAXBElement<RetrieveDocumentSetRequestType> jaxbElement = new JAXBElement<>(
            qname,
            RetrieveDocumentSetRequestType.class,
            request
        );

        return makeFskRequest(
            "/nspservices/sfskrep",
            factory.createRetrieveDocumentSetRequest(request),
            "urn:ihe:iti:2007:RetrieveDocumentSet",
            RetrieveDocumentSetResponseType.class,
            caller
        );
    }

    /**********************
     * Private
     ***********************/
    private <RequestType, ResponseType> ResponseType makeFskRequest(
        String specificEndpoint,
        JAXBElement<RequestType> request,
        String soapAction,
        Class<ResponseType> clazz,
        Identity caller
    ) throws JAXBException {
        final Reply reply;
        try {
            var fullUri = new URI(new StringBuilder().append(serviceUri).append(specificEndpoint).toString());
            log.info("Calling '{}' with a SOAP action '{}'", fullUri, soapAction);
            reply = NspClient.request(
                fullUri,
                ClientUtils.toElement(jaxbContext, request),
                soapAction,
                caller
            );
        } catch (Exception e) {
            throw new NspClientException("FSK request failed", e);
        }
        return jaxbContext.createUnmarshaller().unmarshal(reply.getBody(), clazz).getValue();
    }
}
