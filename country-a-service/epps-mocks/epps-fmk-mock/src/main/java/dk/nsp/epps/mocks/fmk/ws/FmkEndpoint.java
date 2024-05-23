package dk.nsp.epps.mocks.fmk.ws;

import dk.nsp.epps.Utils;
import dk.nsp.epps.mocks.fmk.data.FmkMockDataFactory;
import dk.sosi.seal.SOSIFactory;
import dk.sosi.seal.model.Reply;
import dk.sosi.seal.model.Request;
import dk.sosi.seal.model.SignatureUtil;
import dk.sosi.seal.model.constants.FlowStatusValues;
import dk.sosi.seal.pki.SOSITestFederation;
import dk.sosi.seal.vault.EmptyCredentialVault;
import dk.sosi.seal.xml.XmlUtil;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.JAXBException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ws.context.MessageContext;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.soap.SoapVersion;
import org.springframework.ws.soap.saaj.SaajSoapMessageFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.xml.transform.dom.DOMResult;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.function.Function;

@Slf4j
@Endpoint
public class FmkEndpoint {
    static final String rootNamespace = "http://www.dkma.dk/medicinecard/xml.schema/2015/06/01";
    static final String e2Namespace = rootNamespace + "/E2";
    static final String e5Namespace = rootNamespace + "/E5";
    private final dk.dkma.medicinecard.xml_schema._2015._06._01.e6.ObjectFactory e6Factory = new dk.dkma.medicinecard.xml_schema._2015._06._01.e6.ObjectFactory();

    private final SOSIFactory sosiFactory;
    private final JAXBContext jaxbContext;
    private final SaajSoapMessageFactory soapMessageFactory;

    FmkEndpoint() throws JAXBException {
        final var properties = SignatureUtil.setupCryptoProviderForJVM();
        final var federation = new SOSITestFederation(properties);
        final var credentialVault = new EmptyCredentialVault();
        sosiFactory = new SOSIFactory(federation, credentialVault, properties);

        jaxbContext = JAXBContext.newInstance(
            "dk.dkma.medicinecard.xml_schema._2015._06._01"
                + ":dk.dkma.medicinecard.xml_schema._2015._06._01.e5"
                + ":dk.dkma.medicinecard.xml_schema._2015._06._01.e6"
                + ":dk.sdsd.dgws._2012._06"
        );

        soapMessageFactory = new SaajSoapMessageFactory();
        soapMessageFactory.setSoapVersion(SoapVersion.SOAP_11);
        soapMessageFactory.afterPropertiesSet();
    }

    private <T> Element toElement(JAXBElement<T> jaxbElement) {
        DOMResult res = new DOMResult();
        try {
            jaxbContext.createMarshaller().marshal(jaxbElement, res);
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
        return ((Document) res.getNode()).getDocumentElement();
    }

    private String getRequest(MessageContext messageContext) {
        final var baos = new ByteArrayOutputStream();
        try {
            messageContext.getRequest().writeTo(baos);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return baos.toString(StandardCharsets.UTF_8);
    }

    private void sosiHandler(MessageContext messageContext, Function<Request, Reply> handler) {
        final var request = sosiFactory.deserializeRequest(getRequest(messageContext));
        final var reply = handler.apply(request);

        final var response = soapMessageFactory.createWebServiceMessage();
        response.setDocument(reply.serialize2DOMDocument());
        log.debug("Response document: {}", XmlUtil.node2String(response.getDocument()));
        messageContext.setResponse(response);
    }


    private Reply handleGetPrescription(Request request) {
        var result = FmkMockDataFactory.getPrescriptionResponse();
        var response = e6Factory.createGetPrescriptionResponse(result);

        var reply = sosiFactory.createNewReply(request, null);

        reply.setIDCard(request.getIDCard());
        reply.setBody(toElement(response));
        reply.setFlowStatus(FlowStatusValues.FLOW_FINALIZED_SUCCESFULLY);
        return reply;
    }

    @PayloadRoot(namespace = rootNamespace, localPart = "GetPrescriptionRequest")
    public void getPrescription(MessageContext messageContext) {
        sosiHandler(messageContext, this::handleGetPrescription);
    }

    private static Element xmlFromResource(String resourceName) {
        try {
            return Utils.readXml(Utils.resourceAsStream(resourceName));
        } catch (IOException | SAXException e) {
            throw new RuntimeException(e);
        }
    }

    private Reply handleStartEffectuation(Request request) {
        var reply = sosiFactory.createNewReply(request, null);
        reply.setIDCard(request.getIDCard());
        reply.setFlowStatus(FlowStatusValues.FLOW_FINALIZED_SUCCESFULLY);
        reply.setBody(xmlFromResource("StartEffectuationResponse_example.xml"));
        return reply;
    }

    @PayloadRoot(namespace = e5Namespace, localPart = "StartEffectuationRequest")
    public void startEffectuationE5(MessageContext messageContext) {
        sosiHandler(messageContext, this::handleStartEffectuation);
    }

    @PayloadRoot(namespace = rootNamespace, localPart = "StartEffectuationRequest")
    public void startEffectuation(MessageContext messageContext) {
        sosiHandler(messageContext, this::handleStartEffectuation);
    }

    private Reply handleCreatePharmacyEffectuation(Request request) {
        var reply = sosiFactory.createNewReply(request, FlowStatusValues.FLOW_FINALIZED_SUCCESFULLY);
        reply.setIDCard(request.getIDCard());

        reply.setBody(xmlFromResource("CreatePharmacyEffectuationResponse_example.xml"));
        return reply;
    }

    @PayloadRoot(namespace = e2Namespace, localPart = "CreatePharmacyEffectuationRequest")
    public void createPharmacyEffectuation(MessageContext messageContext) {
        sosiHandler(messageContext, this::handleCreatePharmacyEffectuation);
    }
}
