package dk.sundhedsdatastyrelsen.ncpeh.service;

import dk.dkma.medicinecard.xml_schema._2015._06._01.e2.GetMedicineCardRequestType;
import dk.sundhedsdatastyrelsen.ncpeh.authentication.EuropeanHcpIdwsToken;
import dk.sundhedsdatastyrelsen.ncpeh.base.utils.PublicException;
import dk.sundhedsdatastyrelsen.ncpeh.base.utils.XmlException;
import dk.sundhedsdatastyrelsen.ncpeh.cda.DocumentIdMapper;
import dk.sundhedsdatastyrelsen.ncpeh.cda.MapperException;
import dk.sundhedsdatastyrelsen.ncpeh.cda.Oid;
import dk.sundhedsdatastyrelsen.ncpeh.cda.PatientSummaryInput;
import dk.sundhedsdatastyrelsen.ncpeh.cda.PatientSummaryL1Generator;
import dk.sundhedsdatastyrelsen.ncpeh.cda.PatientSummaryL3Generator;
import dk.sundhedsdatastyrelsen.ncpeh.client.DdvClientIdws;
import dk.sundhedsdatastyrelsen.ncpeh.client.FmkClientIdws;
import dk.sundhedsdatastyrelsen.ncpeh.ncp.api.ClassCodeDto;
import dk.sundhedsdatastyrelsen.ncpeh.ncp.api.ConfidentialityMetadataDto;
import dk.sundhedsdatastyrelsen.ncpeh.ncp.api.DocumentAssociationForPatientSummaryDocumentMetadataDto;
import dk.sundhedsdatastyrelsen.ncpeh.ncp.api.DocumentFormatDto;
import dk.sundhedsdatastyrelsen.ncpeh.ncp.api.EpsosDocumentDto;
import dk.sundhedsdatastyrelsen.ncpeh.ncp.api.EpsosDocumentMetadataDto;
import dk.sundhedsdatastyrelsen.ncpeh.service.mapping.FskMapper;
import dk.sundhedsdatastyrelsen.ncpeh.service.mapping.PatientIdMapper;
import dk.vaccinationsregister.schemas._2013._12._01.GetVaccinationCardRequestType;
import io.opentelemetry.instrumentation.annotations.WithSpan;
import jakarta.xml.bind.JAXBException;
import lombok.extern.slf4j.Slf4j;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;

@Slf4j
public class PatientSummaryService {
    private final InformationCardService informationCardService;
    private final FmkClientIdws fmkServiceIdws;
    private final DdvClientIdws ddvClientIdws;

    public PatientSummaryService(InformationCardService informationCardService, FmkClientIdws fmkServiceIdws, DdvClientIdws ddvClientIdws) {
        this.informationCardService = informationCardService;
        this.fmkServiceIdws = fmkServiceIdws;
        this.ddvClientIdws = ddvClientIdws;
    }

    @WithSpan
    public DocumentAssociationForPatientSummaryDocumentMetadataDto getDocumentMetadata(String patientId) {
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

        Function<String, EpsosDocumentMetadataDto> commonMetadata = id -> {
            var md = new EpsosDocumentMetadataDto(id);
            md.setPatientId(patientId);
            md.setEffectiveTime(effectiveTime);
            md.setRepositoryId(Oid.DK_PATIENT_SUMMARY_REPOSITORY_ID.value);
            md.setAuthor("The Danish Health Data Authority");
            md.setTitle("Patient Summary");
            md.setClassCode(ClassCodeDto._60591_5); // Patient summary document
            md.setLanguage("da-DK"); //We always include Danish text in free-text
            md.setConfidentiality(new ConfidentialityMetadataDto().confidentialityCode("N")
                .confidentialityDisplay("Normal"));
            //The following data is set to this by convention to indicate a document generated on-demand
            // https://profiles.ihe.net/ITI/TF/Volume2/ITI-38.html
            md.setHash("da39a3ee5e6b4b0d3255bfef95601890afd80709"); //SHA1 hash of a zero length file
            md.setSize(0L);
            return md;
        };

        var l3 = commonMetadata.apply(l3Id);
        l3.setFormat(DocumentFormatDto.XML);

        var l1 = commonMetadata.apply(l1Id);
        l1.setFormat(DocumentFormatDto.PDF);

        return new DocumentAssociationForPatientSummaryDocumentMetadataDto(l3, l1);
    }

    @WithSpan
    public List<EpsosDocumentDto> getPatientSummary(
        String patientId,
        String rootedDocumentId,
        EuropeanHcpIdwsToken fmkToken,
        EuropeanHcpIdwsToken ddvToken
    ) {
        var input = assembleInput(patientId, rootedDocumentId, fmkToken, ddvToken);
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
    private PatientSummaryInput assembleInput(
        String patientId,
        String docId,
        EuropeanHcpIdwsToken fmkToken,
        EuropeanHcpIdwsToken ddvToken
    ) {
        var cpr = PatientIdMapper.toCpr(patientId);
        var availableInformationCards = informationCardService.findInformationCardDetails(patientId);
        var minLogHcpId = "%s - %s".formatted(fmkToken.countryOfTreatment(), fmkToken.subjectId());
        var informationCard = informationCardService.getInformationCard(availableInformationCards.getFirst(), patientId, minLogHcpId);

        var medicationCardRequest = GetMedicineCardRequestType.builder()
            .withPersonIdentifier().withSource("CPR").withValue(cpr).end()
            .withIncludePrescriptions(false)
            .withIncludeEffectuations(false)
            .build();

        var vaccineCard = GetVaccinationCardRequestType.builder()
            .withPersonCivilRegistrationIdentifier(cpr)
            .build();

        try {
            var fmkCard = fmkServiceIdws.getMedicineCard(medicationCardRequest, fmkToken);
            var ddvCard = ddvClientIdws.getVaccinationCard(vaccineCard, ddvToken);
            return new PatientSummaryInput(docId, FskMapper.preferredHealthProfessional(informationCard), FskMapper.patient((informationCard)), fmkCard, ddvCard);
        } catch (JAXBException e) {
            throw new PublicException(500, "Could not retrieve prescriptions.", e);
        }
    }
}
