package dk.openncp.nationalconnector.xdr;

import dk.nsp.epps.ApiException;
import dk.nsp.epps.api.model.ClassCode;
import dk.nsp.epps.api.model.DisardDispensationRequest;
import dk.nsp.epps.api.model.EpsosDocument;
import dk.nsp.epps.api.model.SubmitDispensationRequest;
import dk.openncp.nationalconnector.CountryAService;
import dk.openncp.nationalconnector.Utils;
import dk.openncp.nationalconnector.xca.DocumentSearch;
import eu.epsos.protocolterminators.ws.server.common.NationalConnectorInterface;
import eu.epsos.protocolterminators.ws.server.exception.NIException;
import eu.epsos.protocolterminators.ws.server.xdr.DocumentProcessingException;
import eu.epsos.protocolterminators.ws.server.xdr.DocumentSubmitInterface;
import eu.europa.ec.sante.ehdsi.constant.error.OpenNCPErrorCode;
import eu.europa.ec.sante.ehdsi.openncp.assertionvalidator.exceptions.InsufficientRightsException;
import eu.europa.ec.sante.ehdsi.openncp.model.DiscardDispenseDetails;
import fi.kela.se.epsos.data.model.ConsentDocumentMetaData;
import fi.kela.se.epsos.data.model.DocumentAssociation;
import fi.kela.se.epsos.data.model.EPSOSDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;

public class DocumentSubmit implements NationalConnectorInterface, DocumentSubmitInterface {
    private static final Logger logger = LoggerFactory.getLogger(DocumentSearch.class);

    private Element soapHeader;

    @Override
    public void submitDispensation(EPSOSDocument epsosDocument) throws NIException, InsufficientRightsException {
        try {
            CountryAService.api().submitDispensation(new SubmitDispensationRequest()
                    .soapHeader(Utils.elementToString(soapHeader))
                    .patientId(epsosDocument.getPatientId())
                    .classCode(Utils.classCode(epsosDocument.getClassCode()))
                    .document(Utils.elementToString(epsosDocument.getDocument().getDocumentElement())));
        } catch (ApiException e) {
            throw new NIException(OpenNCPErrorCode.ERROR_ED_GENERIC, "Dispensation failed.");
        }
    }

    @Override
    public void cancelDispensation(DiscardDispenseDetails discardDispenseDetails, EPSOSDocument epsosDocument) throws NIException, InsufficientRightsException {
        var ddd = new dk.nsp.epps.api.model.DiscardDispenseDetails()
                .discardId(discardDispenseDetails.getDiscardId())
                .dispenseId(discardDispenseDetails.getDispenseId())
                .discardDate(Utils.dateToUtcOffsetDateTime(discardDispenseDetails.getDiscardDate()))
                .patientId(discardDispenseDetails.getPatientId())
                .healthCareProviderId(discardDispenseDetails.getHealthCareProviderId())
                .healthCareProvider(discardDispenseDetails.getHealthCareProvider())
                .healthCareProviderFacility(discardDispenseDetails.getHealthCareProviderFacility())
                .healthCareProviderOrganizationId(discardDispenseDetails.getHealthCareProviderOrganizationId())
                .healthCareProviderOrganization(discardDispenseDetails.getHealthCareProviderOrganization());

        var dtd = new EpsosDocument()
                .classCode(Utils.classCode(epsosDocument.getClassCode()))
                .patientId(epsosDocument.getPatientId())
                .document(Utils.elementToString(epsosDocument.getDocument().getDocumentElement()));

        try {
            CountryAService.api().disardDispensation(new DisardDispensationRequest()
                    .soapHeader(Utils.elementToString(soapHeader))
                    .disardDispenseDetails(ddd)
                    .dispensationToDiscard(dtd));
        } catch (ApiException e) {
            throw new NIException(OpenNCPErrorCode.ERROR_ED_DISCARD_FAILED, "Dispensation discard failed.");
        }
    }

    @Override
    public void submitPatientConsent(EPSOSDocument epsosDocument) throws NIException, InsufficientRightsException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void cancelConsent(DocumentAssociation<ConsentDocumentMetaData> documentAssociation) throws NIException, InsufficientRightsException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void submitHCER(EPSOSDocument epsosDocument) throws DocumentProcessingException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setSOAPHeader(Element soapHeader) {
        this.soapHeader = soapHeader;
    }

    public static void main(String[] args) {
        try {
            var res = CountryAService.api().submitDispensationWithHttpInfo(new SubmitDispensationRequest()
                    .patientId("0101010101")
                    .classCode(ClassCode._60593_1)
                    .document("<dummy/>")
                    .soapHeader("<Header/>"));

            res.getStatusCode();

        } catch (ApiException e) {
            throw new RuntimeException(e);
        }
    }
}
