package dk.sundhedsdatastyrelsen.ncpeh.service.mapping;

import dk.sundhedsdatastyrelsen.ncpeh.cda.EPrescriptionDocumentIdMapper;
import dk.sundhedsdatastyrelsen.ncpeh.cda.EPrescriptionL3Input;
import dk.sundhedsdatastyrelsen.ncpeh.cda.EPrescriptionL3Mapper;
import dk.sundhedsdatastyrelsen.ncpeh.cda.MapperException;
import dk.sundhedsdatastyrelsen.ncpeh.cda.Oid;
import dk.sundhedsdatastyrelsen.ncpeh.cda.model.DocumentLevel;
import dk.sundhedsdatastyrelsen.ncpeh.cda.model.EPrescriptionL3;
import dk.sundhedsdatastyrelsen.ncpeh.ncp.api.ClassCodeDto;
import dk.sundhedsdatastyrelsen.ncpeh.ncp.api.ConfidentialityMetadataDto;
import dk.sundhedsdatastyrelsen.ncpeh.ncp.api.DocumentAssociationForEPrescriptionDocumentMetadataDto;
import dk.sundhedsdatastyrelsen.ncpeh.ncp.api.DocumentFormatDto;
import dk.sundhedsdatastyrelsen.ncpeh.ncp.api.EPrescriptionDocumentMetadataDto;
import dk.sundhedsdatastyrelsen.ncpeh.service.exception.CountryAException;
import org.springframework.http.HttpStatus;

public class EPrescriptionMapper {

    private EPrescriptionMapper() {
    }

    public static DocumentAssociationForEPrescriptionDocumentMetadataDto mapMeta(String patientId, EPrescriptionL3Input generatorInput) {
        try {
            var dataModel = EPrescriptionL3Mapper.model(generatorInput);

            var l3Meta = generateMeta(patientId, dataModel, DocumentLevel.LEVEL3);

            var l1Meta = generateMeta(patientId, dataModel, DocumentLevel.LEVEL1);

            return new DocumentAssociationForEPrescriptionDocumentMetadataDto(l3Meta, l1Meta);

        } catch (MapperException e) {
            throw new CountryAException(HttpStatus.INTERNAL_SERVER_ERROR, e);
        }
    }

    private static EPrescriptionDocumentMetadataDto generateMeta(String patientId, EPrescriptionL3 model, DocumentLevel documentLevel) {
        if (!documentLevel.equals(DocumentLevel.LEVEL1) && !documentLevel.equals(DocumentLevel.LEVEL3)) {
            throw new IllegalArgumentException("Does not support documentLevel: " + documentLevel.toString());
        }

        String documentId = switch (documentLevel) {
            case DocumentLevel.LEVEL1 ->
                EPrescriptionDocumentIdMapper.level1DocumentId(model.getPrescriptionId().getExtension());
            case DocumentLevel.LEVEL3 ->
                EPrescriptionDocumentIdMapper.level3DocumentId(model.getPrescriptionId().getExtension());
        };

        DocumentFormatDto documentFormat = switch (documentLevel) {
            case DocumentLevel.LEVEL1 -> DocumentFormatDto.PDF;
            case DocumentLevel.LEVEL3 -> DocumentFormatDto.XML;
        };

        var meta = new EPrescriptionDocumentMetadataDto(documentId);
        meta.setPatientId(patientId);
        meta.setEffectiveTime(model.getEffectiveTimeOffsetDateTime());
        meta.setRepositoryId(Oid.DK_EPRESCRIPTION_REPOSITORY_ID.value);
        meta.setAuthor(model.getAuthor().getName().getFullName());
        meta.setTitle(model.getTitle());
        meta.setDescription(model.getIndicationText());
        meta.setProductCode(model.getProduct().getPackageCode().getCode());
        meta.setAtcCode(model.getProduct().getAtcCode().getCode());
        meta.setAtcName(model.getProduct().getAtcCode().getDisplayName());
        meta.setClassCode(ClassCodeDto._57833_6); // Prescription for medication
        meta.setDispensable(true); //This should always be true, we don't return non-dispensable prescriptions
        meta.setFormat(documentFormat);
        meta.setLanguage("da-DK"); //We always include danish text in free-text, so
        meta.setProductCode(model.getProduct()
            .getPackageCode()
            .getCode()); //Varenummer
        meta.setProductName(model.getProduct().getName());
        meta.setDoseFormCode(model.getProduct()
            .getFormCode()
            .getCode());
        meta.setDoseFormName(model.getProduct().getFormCode().getDisplayName());
        meta.setConfidentiality(new ConfidentialityMetadataDto().confidentialityCode("N")
            .confidentialityDisplay("Normal"));
        meta.setStrength(model.getProduct().getStrength());

        //The following data is set to this by convention to indicate a document generated on-demand
        // https://profiles.ihe.net/ITI/TF/Volume2/ITI-38.html
        meta.setHash("da39a3ee5e6b4b0d3255bfef95601890afd80709"); //SHA1 hash of a zero length file
        meta.setSize(0L);

        // TODO Missing metadata fields as of now
        /*
        String substitutionCode;
        String substitutionDisplayName;
         */

        return meta;
    }
}
