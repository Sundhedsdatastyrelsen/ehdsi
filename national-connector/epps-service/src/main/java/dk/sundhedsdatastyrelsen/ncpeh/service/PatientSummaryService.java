package dk.sundhedsdatastyrelsen.ncpeh.service;

import dk.dkma.medicinecard.xml_schema._2015._06._01.e2.GetMedicineCardRequestType;
import dk.sdsd.dgws._2010._08.PredefinedRequestedRole;
import dk.sundhedsdatastyrelsen.ncpeh.authentication.EuropeanHcpIdwsToken;
import dk.sundhedsdatastyrelsen.ncpeh.authentication.NspDgwsIdentity;
import dk.sundhedsdatastyrelsen.ncpeh.base.utils.PublicException;
import dk.sundhedsdatastyrelsen.ncpeh.base.utils.XmlException;
import dk.sundhedsdatastyrelsen.ncpeh.cda.MapperException;
import dk.sundhedsdatastyrelsen.ncpeh.cda.Oid;
import dk.sundhedsdatastyrelsen.ncpeh.cda.DocumentIdMapper;
import dk.sundhedsdatastyrelsen.ncpeh.cda.PatientSummaryInput;
import dk.sundhedsdatastyrelsen.ncpeh.cda.PatientSummaryL1Generator;
import dk.sundhedsdatastyrelsen.ncpeh.cda.PatientSummaryL3Generator;
import dk.sundhedsdatastyrelsen.ncpeh.client.FmkClientDgws;
import dk.sundhedsdatastyrelsen.ncpeh.ncp.api.ClassCodeDto;
import dk.sundhedsdatastyrelsen.ncpeh.ncp.api.ConfidentialityMetadataDto;
import dk.sundhedsdatastyrelsen.ncpeh.ncp.api.DocumentAssociationForPatientSummaryDocumentMetadataDto;
import dk.sundhedsdatastyrelsen.ncpeh.ncp.api.DocumentFormatDto;
import dk.sundhedsdatastyrelsen.ncpeh.ncp.api.EpsosDocumentDto;
import dk.sundhedsdatastyrelsen.ncpeh.ncp.api.EpsosDocumentMetadataDto;
import dk.sundhedsdatastyrelsen.ncpeh.service.mapping.FskMapper;
import io.opentelemetry.instrumentation.annotations.WithSpan;
import jakarta.xml.bind.JAXBException;
import lombok.extern.slf4j.Slf4j;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
public class PatientSummaryService {
    private final InformationCardService informationCardService;
    private final FmkClientDgws fmkServiceDgws;

    public PatientSummaryService(InformationCardService informationCardService, FmkClientDgws fmkServiceDgws) {
        this.informationCardService = informationCardService;
        this.fmkServiceDgws = fmkServiceDgws;
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
        var baseId = UUID.randomUUID().toString();
        var l3Id = Oid.DK_PATIENT_SUMMARY_REPOSITORY_ID.value + "^" + DocumentIdMapper.level3DocumentId(baseId);
        var l1Id = Oid.DK_PATIENT_SUMMARY_REPOSITORY_ID.value + "^" + DocumentIdMapper.level1DocumentId(baseId);

        var effectiveTime = OffsetDateTime.now();

        var l3 = new EpsosDocumentMetadataDto(l3Id);
        l3.setPatientId(patientId);
        l3.setEffectiveTime(effectiveTime);
        l3.setRepositoryId(Oid.DK_PATIENT_SUMMARY_REPOSITORY_ID.value);
        // TODO who's the author?
        l3.setAuthor("Not implemented");
        // TODO what should the title be?
        l3.setTitle("Not implemented");
        l3.setClassCode(ClassCodeDto._60591_5); // Patient summary document
        l3.setFormat(DocumentFormatDto.XML);
        // TODO Double check these values
        l3.setLanguage("da-DK"); //We always include danish text in free-text
        l3.setConfidentiality(new ConfidentialityMetadataDto().confidentialityCode("N")
            .confidentialityDisplay("Normal"));
        //The following data is set to this by convention to indicate a document generated on-demand
        // https://profiles.ihe.net/ITI/TF/Volume2/ITI-38.html
        l3.setHash("da39a3ee5e6b4b0d3255bfef95601890afd80709"); //SHA1 hash of a zero length file
        l3.setSize(0L);

        var l1 = new EpsosDocumentMetadataDto(l1Id);
        l1.setPatientId(patientId);
        l1.setEffectiveTime(effectiveTime);
        l1.setRepositoryId(Oid.DK_PATIENT_SUMMARY_REPOSITORY_ID.value);
        l1.setAuthor("Not implemented");
        l1.setTitle("Not implemented");
        l1.setClassCode(ClassCodeDto._60591_5);
        l1.setFormat(DocumentFormatDto.PDF);
        l1.setLanguage("da-DK");
        l1.setConfidentiality(new ConfidentialityMetadataDto().confidentialityCode("N")
            .confidentialityDisplay("Normal"));
        l1.setHash("da39a3ee5e6b4b0d3255bfef95601890afd80709");
        l1.setSize(0L);

        return new DocumentAssociationForPatientSummaryDocumentMetadataDto(l3, l1);
    }

    // TODO Left in the caller, because I think we will need it later.
    @WithSpan
    public List<EpsosDocumentDto> getPatientSummary(String patientId, String rootedDocumentId, NspDgwsIdentity system, EuropeanHcpIdwsToken token, String europeanHealthProfessionalId) {
        var input = assembleInput(patientId, system, europeanHealthProfessionalId, rootedDocumentId, token);
        try {
            var documentLevel = DocumentIdMapper.parseDocumentLevel(rootedDocumentId);
            var cda = switch (documentLevel) {
                case LEVEL3 -> PatientSummaryL3Generator.generate(input);
                case LEVEL1 -> PatientSummaryL1Generator.generate(input);
            };

            return List.of(new EpsosDocumentDto(patientId, cda, ClassCodeDto._60591_5));
        } catch (MapperException | XmlException e) {
            throw new PublicException(500, "Failed to get Patient Summary.", e);
        }
    }

    @WithSpan
    private PatientSummaryInput assembleInput(String patientId, NspDgwsIdentity system, String europeanHealthProfessionalId, String docId, EuropeanHcpIdwsToken token) {
        var availableInformationCards = informationCardService.findInformationCardDetails(patientId);
        var informationCard = informationCardService.getInformationCard(availableInformationCards.getFirst(), patientId, europeanHealthProfessionalId);

        var medicationCardRequest = GetMedicineCardRequestType.builder()
            .withPersonIdentifier().withSource("CPR").withValue(patientId).end()
            .withIncludePrescriptions(false)
            .withIncludeEffectuations(false)
            .build();

        try {
            var fmkCard = fmkServiceDgws.getMedicineCard(medicationCardRequest, system, PredefinedRequestedRole.LÆGE);
            return new PatientSummaryInput(docId, FskMapper.preferredHealthProfessional(informationCard), FskMapper.patient((informationCard)), fmkCard);
        }  catch (JAXBException e) {
            throw new PublicException(500, "Could not retrieve prescriptions.", e);
        }
    }
}
