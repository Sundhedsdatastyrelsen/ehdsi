package dk.sundhedsdatastyrelsen.ncpeh.service;

import dk.sundhedsdatastyrelsen.ncpeh.authentication.NspDgwsIdentity;
import dk.sundhedsdatastyrelsen.ncpeh.cda.MapperException;
import dk.sundhedsdatastyrelsen.ncpeh.cda.Oid;
import dk.sundhedsdatastyrelsen.ncpeh.cda.PatientSummaryInput;
import dk.sundhedsdatastyrelsen.ncpeh.cda.PatientSummaryL3Generator;
import dk.sundhedsdatastyrelsen.ncpeh.ncp.api.ClassCodeDto;
import dk.sundhedsdatastyrelsen.ncpeh.ncp.api.ConfidentialityMetadataDto;
import dk.sundhedsdatastyrelsen.ncpeh.ncp.api.DocumentAssociationForPatientSummaryDocumentMetadataDto;
import dk.sundhedsdatastyrelsen.ncpeh.ncp.api.DocumentFormatDto;
import dk.sundhedsdatastyrelsen.ncpeh.ncp.api.EpsosDocumentDto;
import dk.sundhedsdatastyrelsen.ncpeh.ncp.api.EpsosDocumentMetadataDto;
import dk.sundhedsdatastyrelsen.ncpeh.service.exception.CountryAException;
import dk.sundhedsdatastyrelsen.ncpeh.service.mapping.FskMapper;
import freemarker.template.TemplateException;
import io.opentelemetry.instrumentation.annotations.WithSpan;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class PatientSummaryService {
    private final InformationCardService informationCardService;

    public PatientSummaryService(
        InformationCardService informationCardService
    ) {
        this.informationCardService = informationCardService;
    }

    @WithSpan
    public DocumentAssociationForPatientSummaryDocumentMetadataDto getDocumentMetadata(String patientId, NspDgwsIdentity identity) {
        // We generate a new id every time, as we cannot tell when the underlying data is updated. The drawback is that
        // if clients ask for an old ID, they will still get the most recent data. Other options were considered.
        // We could have returned the same id every time, and just used the patient id in the request to figure out who
        // the document was for. But that doesn't reflect the fact that the document might have changed and every
        // patient's document would have the same id.
        // We could have used the patient ID for document ID, but that would put the patient ID in more places where it
        // should not be and still has the problem of the same id for a document that might have changed.
        // We could have introduced a table to remember ids, but that's more work, more data we have to secure, and we
        // don't know when there is new data.
        var id = Oid.DK_PATIENT_SUMMARY_REPOSITORY_ID.value + "^" + UUID.randomUUID();
        var l3 = new EpsosDocumentMetadataDto(id);

        l3.setPatientId(patientId);
        l3.setEffectiveTime(OffsetDateTime.now());
        l3.setRepositoryId(Oid.DK_PATIENT_SUMMARY_REPOSITORY_ID.value);
        // TODO who's the author?
        l3.setAuthor("Not implemented");
        // TODO what should the title be?
        l3.setTitle("Not implemented");
        l3.setClassCode(ClassCodeDto._60591_5); // Patient summary document
        // TODO change to PDF for L1
        l3.setFormat(DocumentFormatDto.XML);
        // TODO Double check these values
        l3.setLanguage("da-DK"); //We always include danish text in free-text
        l3.setConfidentiality(new ConfidentialityMetadataDto().confidentialityCode("N")
            .confidentialityDisplay("Normal"));

        //The following data is set to this by convention to indicate a document generated on-demand
        // https://profiles.ihe.net/ITI/TF/Volume2/ITI-38.html
        l3.setHash("da39a3ee5e6b4b0d3255bfef95601890afd80709"); //SHA1 hash of a zero length file
        l3.setSize(0L);

        EpsosDocumentMetadataDto l1 = null;
        return new DocumentAssociationForPatientSummaryDocumentMetadataDto(l3, l1);
    }

    // TODO Left in the caller, because I think we will need it later.
    @WithSpan
    public List<EpsosDocumentDto> getPatientSummary(String patientId, String rootedDocumentId, NspDgwsIdentity system, String europeanHealthProfessionalId) {
        var input = assembleInput(patientId, system, europeanHealthProfessionalId, rootedDocumentId);
        try {
            // TODO Commented out, as we're focusing on the L3 model first.
//            var documentLevel = EPrescriptionDocumentIdMapper.parseDocumentLevel(rootedDocumentId);
//            var cda = switch (documentLevel) {
//                case LEVEL3 -> EPrescriptionL3Generator.generate(input);
//                case LEVEL1 -> EPrescriptionL1Generator.generate(input);
//            };
            var cda = PatientSummaryL3Generator.generate(input);

            return List.of(new EpsosDocumentDto(patientId, cda, ClassCodeDto._60591_5));
        } catch (MapperException | TemplateException | IOException e) {
            throw new CountryAException(HttpStatus.INTERNAL_SERVER_ERROR, e);
        }
    }

    @WithSpan
    private PatientSummaryInput assembleInput(String patientId, NspDgwsIdentity system, String europeanHealthProfessionalId, String docId) {
        var availableInformationCards = informationCardService.findInformationCardDetails(patientId);
        var informationCard = informationCardService.getInformationCard(availableInformationCards.getFirst(),patientId, europeanHealthProfessionalId);
        var xpath = FskMapper.getXpath();
        try {
            return new PatientSummaryInput(docId, FskMapper.preferredHealthProfessional(xpath, informationCard));
        } catch (XPathExpressionException e) {
            // TODO better exception.
            throw new RuntimeException(e);
        }
    }
}
