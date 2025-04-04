package dk.sundhedsdatastyrelsen.ncpeh.service.mapping;

import dk.dkma.medicinecard.xml_schema._2015._06._01.e6.GetPrescriptionResponseType;
import dk.sundhedsdatastyrelsen.ncpeh.cda.EPrescriptionDocumentIdMapper;
import dk.sundhedsdatastyrelsen.ncpeh.cda.EPrescriptionL3Mapper;
import dk.sundhedsdatastyrelsen.ncpeh.cda.MapperException;
import dk.sundhedsdatastyrelsen.ncpeh.cda.Oid;
import dk.sundhedsdatastyrelsen.ncpeh.cda.model.DocumentLevel;
import dk.sundhedsdatastyrelsen.ncpeh.ncp.api.ClassCodeDto;
import dk.sundhedsdatastyrelsen.ncpeh.ncp.api.ConfidentialityMetadataDto;
import dk.sundhedsdatastyrelsen.ncpeh.ncp.api.DocumentAssociationForEPrescriptionDocumentMetadataDto;
import dk.sundhedsdatastyrelsen.ncpeh.ncp.api.DocumentFormatDto;
import dk.sundhedsdatastyrelsen.ncpeh.ncp.api.EPrescriptionDocumentMetadataDto;
import dk.sundhedsdatastyrelsen.ncpeh.service.exception.CountryAException;
import lombok.NonNull;
import org.springframework.http.HttpStatus;

import java.time.OffsetDateTime;
import java.util.Optional;

public class EPrescriptionMapper {

    private EPrescriptionMapper() {
    }

    private record MetaModel(
        String patientId,
        OffsetDateTime effectiveTime,
        String prescriptionId,
        String authorName,
        String title,
        String description,
        String productCode,
        String productName,
        String atcCode,
        String atcName,
        String doseFormCode,
        String doseFormName,
        String strength
    ) {
    }

    public static DocumentAssociationForEPrescriptionDocumentMetadataDto mapMeta(String patientId, GetPrescriptionResponseType prescriptions, int prescriptionIndex) {
        try {
            var model = makeModel(patientId, prescriptions, prescriptionIndex);
            var l3Meta = generateMeta(patientId, model, DocumentLevel.LEVEL3);
            var l1Meta = generateMeta(patientId, model, DocumentLevel.LEVEL1);
            return new DocumentAssociationForEPrescriptionDocumentMetadataDto(l3Meta, l1Meta);
        } catch (MapperException e) {
            // TODO I don't think we should throw here? Just return the ones that work.
            throw new CountryAException(HttpStatus.INTERNAL_SERVER_ERROR, e);
        }
    }

    private static EPrescriptionDocumentMetadataDto generateMeta(String patientId, MetaModel model, DocumentLevel documentLevel) {
        if (!documentLevel.equals(DocumentLevel.LEVEL1) && !documentLevel.equals(DocumentLevel.LEVEL3)) {
            throw new IllegalArgumentException("Does not support documentLevel: " + documentLevel);
        }

        String documentId = switch (documentLevel) {
            case DocumentLevel.LEVEL1 -> EPrescriptionDocumentIdMapper.level1DocumentId(model.prescriptionId());
            case DocumentLevel.LEVEL3 -> EPrescriptionDocumentIdMapper.level3DocumentId(model.prescriptionId());
        };

        DocumentFormatDto documentFormat = switch (documentLevel) {
            case DocumentLevel.LEVEL1 -> DocumentFormatDto.PDF;
            case DocumentLevel.LEVEL3 -> DocumentFormatDto.XML;
        };

        var meta = new EPrescriptionDocumentMetadataDto(documentId);
        meta.setPatientId(patientId);
        meta.setEffectiveTime(model.effectiveTime());
        meta.setRepositoryId(Oid.DK_EPRESCRIPTION_REPOSITORY_ID.value);
        meta.setAuthor(model.authorName());
        meta.setTitle(model.title());
        meta.setDescription(model.description());
        meta.setAtcCode(model.atcCode());
        meta.setAtcName(model.atcName());
        meta.setClassCode(ClassCodeDto._57833_6); // Prescription for medication
        meta.setDispensable(true); //This should always be true, we don't return non-dispensable prescriptions
        meta.setFormat(documentFormat);
        meta.setLanguage("da-DK"); //We always include danish text in free-text
        meta.setProductCode(model.productCode()); //Varenummer
        meta.setProductName(model.productName());
        meta.setDoseFormCode(model.doseFormCode());
        meta.setDoseFormName(model.doseFormName());
        meta.setConfidentiality(new ConfidentialityMetadataDto().confidentialityCode("N")
            .confidentialityDisplay("Normal"));
        meta.setStrength(model.strength());

        //The following data is set to this by convention to indicate a document generated on-demand
        // https://profiles.ihe.net/ITI/TF/Volume2/ITI-38.html
        meta.setHash("da39a3ee5e6b4b0d3255bfef95601890afd80709"); //SHA1 hash of a zero length file
        meta.setSize(0L);

        // TODO Missing metadata fields as of 2025-04-01
        /*
        String substitutionCode;
        String substitutionDisplayName;
         */

        return meta;
    }

    @NonNull
    private static MetaModel makeModel(String patientId, GetPrescriptionResponseType prescriptions, int prescriptionIndex) throws MapperException {
        var prescription = prescriptions.getPrescription().get(prescriptionIndex);
        return new MetaModel(
            patientId,
            OffsetDateTime.now(),
            Long.toString(prescription.getIdentifier()),
            EPrescriptionL3Mapper.getAuthorizedHealthcareProfessional(prescription).getName(),
            EPrescriptionL3Mapper.makeTitle(prescriptions, prescription),
            Optional.ofNullable(prescription.getIndication().getFreeText())
                .orElse(prescription.getIndication().getText()),
            prescription.getPackageRestriction().getPackageNumber().getValue(),
            prescription.getDrug().getName(),
            prescription.getDrug().getATC().getCode().getValue(),
            prescription.getDrug().getATC().getText(),
            prescription.getDrug().getForm().getCode().getValue(),
            prescription.getDrug().getForm().getText(),
            EPrescriptionL3Mapper.drugStrengthText(prescription)
        );
    }
}
