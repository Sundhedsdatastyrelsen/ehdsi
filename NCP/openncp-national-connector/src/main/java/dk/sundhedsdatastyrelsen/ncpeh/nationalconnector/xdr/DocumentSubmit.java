package dk.sundhedsdatastyrelsen.ncpeh.nationalconnector.xdr;


import dk.sundhedsdatastyrelsen.ncpeh.ApiException;
import dk.sundhedsdatastyrelsen.ncpeh.api.model.ClassCode;
import dk.sundhedsdatastyrelsen.ncpeh.api.model.DiscardDispensationRequest;
import dk.sundhedsdatastyrelsen.ncpeh.api.model.EpsosDocument;
import dk.sundhedsdatastyrelsen.ncpeh.api.model.SubmitDispensationRequest;
import dk.sundhedsdatastyrelsen.ncpeh.nationalconnector.NationalConnectorService;
import dk.sundhedsdatastyrelsen.ncpeh.nationalconnector.Utils;
import dk.sundhedsdatastyrelsen.ncpeh.nationalconnector.xca.DocumentSearch;
import eu.europa.ec.sante.openncp.core.common.ihe.NationalConnectorInterface;
import eu.europa.ec.sante.openncp.core.common.ihe.exception.NIException;
import eu.europa.ec.sante.openncp.core.server.api.ihe.xdr.DocumentSubmitInterface;
import eu.europa.ec.sante.openncp.common.error.OpenNCPErrorCode;
import eu.europa.ec.sante.openncp.core.common.ihe.assertionvalidator.exceptions.InsufficientRightsException;
import eu.europa.ec.sante.openncp.core.common.ihe.datamodel.DiscardDispenseDetails;
import eu.europa.ec.sante.openncp.core.common.ihe.datamodel.xds.EPSOSDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.w3c.dom.Element;

@Service
public class DocumentSubmit implements NationalConnectorInterface, DocumentSubmitInterface {
    private static final Logger logger = LoggerFactory.getLogger(DocumentSearch.class);

    private Element soapHeader;

    @Override
    public void submitDispensation(EPSOSDocument epsosDocument) throws NIException, InsufficientRightsException {
        try {
            NationalConnectorService.api().submitDispensation(new SubmitDispensationRequest()
                    .soapHeader(Utils.elementToString(soapHeader))
                    .patientId(epsosDocument.getPatientId())
                    .classCode(Utils.classCode(epsosDocument.getClassCode()))
                    .document(Utils.elementToString(epsosDocument.getDocument().getDocumentElement())));
        } catch (ApiException e) {
            throw new NIException(OpenNCPErrorCode.ERROR_ED_GENERIC, String.format("Dispensation failed with error: %s",e.getResponseBody()));
        }
    }

    private static dk.sundhedsdatastyrelsen.ncpeh.api.model.DiscardDispenseDetails apiModel(DiscardDispenseDetails discardDispenseDetails) {
        return new dk.sundhedsdatastyrelsen.ncpeh.api.model.DiscardDispenseDetails()
                .discardId(discardDispenseDetails.getDiscardId())
                .dispenseId(discardDispenseDetails.getDispenseId())
                .discardDate(Utils.dateToUtcOffsetDateTime(discardDispenseDetails.getDiscardDate()))
                .patientId(discardDispenseDetails.getPatientId())
                .healthCareProviderId(discardDispenseDetails.getHealthCareProviderId())
                .healthCareProvider(discardDispenseDetails.getHealthCareProvider())
                .healthCareProviderFacility(discardDispenseDetails.getHealthCareProviderFacility())
                .healthCareProviderOrganizationId(discardDispenseDetails.getHealthCareProviderOrganizationId())
                .healthCareProviderOrganization(discardDispenseDetails.getHealthCareProviderOrganization());
    }

    private static EpsosDocument apiModel(EPSOSDocument epsosDocument) {
        return new EpsosDocument()
                .classCode(Utils.classCode(epsosDocument.getClassCode()))
                .patientId(epsosDocument.getPatientId())
                .document(Utils.elementToString(epsosDocument.getDocument().getDocumentElement()));
    }

    @Override
    public void cancelDispensation(DiscardDispenseDetails discardDispenseDetails, EPSOSDocument epsosDocument) throws NIException, InsufficientRightsException {
        try {
            NationalConnectorService.api().discardDispensation(new DiscardDispensationRequest()
                    .soapHeader(Utils.elementToString(soapHeader))
                    .discardDispenseDetails(apiModel(discardDispenseDetails))
                    .dispensationToDiscard(apiModel(epsosDocument)));
        } catch (ApiException e) {
            throw new NIException(OpenNCPErrorCode.ERROR_ED_DISCARD_FAILED, String.format("Dispensation discard failed with error: %s",e.getResponseBody()));
        }
    }

    @Override
    public void setSOAPHeader(Element soapHeader) {
        this.soapHeader = soapHeader;
    }

    public static void main(String[] args) {
        try {
            var res = NationalConnectorService.api().submitDispensationWithHttpInfo(new SubmitDispensationRequest()
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
