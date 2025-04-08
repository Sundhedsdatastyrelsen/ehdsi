package dk.sundhedsdatastyrelsen.ncpeh.service.mapping;

import dk.dkma.medicinecard.xml_schema._2015._06._01.ATCCodeType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.ATCType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.DrugType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e6.GetPrescriptionResponseType;
import dk.sundhedsdatastyrelsen.ncpeh.cda.EPrescriptionDocumentIdMapper;
import dk.sundhedsdatastyrelsen.ncpeh.cda.EPrescriptionL3Mapper;
import dk.sundhedsdatastyrelsen.ncpeh.cda.MapperException;
import dk.sundhedsdatastyrelsen.ncpeh.cda.Oid;
import dk.sundhedsdatastyrelsen.ncpeh.cda.model.DocumentLevel;
import dk.sundhedsdatastyrelsen.ncpeh.lms.formats.Lms02Data;
import dk.sundhedsdatastyrelsen.ncpeh.ncp.api.ClassCodeDto;
import dk.sundhedsdatastyrelsen.ncpeh.ncp.api.ConfidentialityMetadataDto;
import dk.sundhedsdatastyrelsen.ncpeh.ncp.api.DocumentAssociationForEPrescriptionDocumentMetadataDto;
import dk.sundhedsdatastyrelsen.ncpeh.ncp.api.DocumentFormatDto;
import dk.sundhedsdatastyrelsen.ncpeh.ncp.api.EPrescriptionDocumentMetadataDto;
import dk.sundhedsdatastyrelsen.ncpeh.service.DispensationAllowed;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.time.OffsetDateTime;
import java.util.Optional;

@Slf4j
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
        String strength,
        boolean dispensable
    ) {
    }

    public static DocumentAssociationForEPrescriptionDocumentMetadataDto mapMeta(String patientId, GetPrescriptionResponseType prescriptions, int prescriptionIndex, Lms02Data lms02Entry) {
        try {
            var model = makeModel(patientId, prescriptions, prescriptionIndex, lms02Entry);
            var l3Meta = generateMeta(patientId, model, DocumentLevel.LEVEL3);
            var l1Meta = generateMeta(patientId, model, DocumentLevel.LEVEL1);
            return new DocumentAssociationForEPrescriptionDocumentMetadataDto(l3Meta, l1Meta);
        } catch (Exception e) {
            log.error("An error occurred while mapping metadata.", e);
            return null;
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
        meta.setDispensable(model.dispensable());
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
    private static MetaModel makeModel(String patientId, GetPrescriptionResponseType prescriptions, int prescriptionIndex, Lms02Data lms02Entry) throws MapperException {
        var prescription = prescriptions.getPrescription().get(prescriptionIndex);
        if (prescription == null) {
            throw new MapperException("Missing prescription");
        }
        var atc = Optional.ofNullable(prescription.getDrug()).map(DrugType::getATC);
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
            atc.map(ATCType::getCode).map(ATCCodeType::getValue).orElse(null),
            atc.map(ATCType::getText).orElse(null),
            prescription.getDrug().getForm().getCode().getValue(),
            prescription.getDrug().getForm().getText(),
            EPrescriptionL3Mapper.drugStrengthText(prescription),
            DispensationAllowed.isDispensationAllowed(prescription, lms02Entry)
        );
    }
}
