package dk.sundhedsdatastyrelsen.ncpeh.service.mapping;

import dk.dkma.medicinecard.xml_schema._2015._06._01.ATCCodeType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.ATCType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.DrugFormCodeType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.DrugFormType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e6.GetPrescriptionResponseType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e6.PrescriptionType;
import dk.sundhedsdatastyrelsen.ncpeh.cda.EPrescriptionDocumentIdMapper;
import dk.sundhedsdatastyrelsen.ncpeh.cda.EPrescriptionL3Mapper;
import dk.sundhedsdatastyrelsen.ncpeh.cda.MapperException;
import dk.sundhedsdatastyrelsen.ncpeh.cda.Oid;
import dk.sundhedsdatastyrelsen.ncpeh.cda.Utils;
import dk.sundhedsdatastyrelsen.ncpeh.cda.model.DocumentLevel;
import dk.sundhedsdatastyrelsen.ncpeh.locallms.PackageInfo;
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
public class EPrescriptionMetadataMapper {

    private EPrescriptionMetadataMapper() {
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

    public static DocumentAssociationForEPrescriptionDocumentMetadataDto mapMeta(String patientId, GetPrescriptionResponseType prescriptions, int prescriptionIndex, PackageInfo packageInfo) {
        try {
            var model = makeModel(patientId, prescriptions, prescriptionIndex, packageInfo);
            var l3Meta = generateMeta(model, DocumentLevel.LEVEL3);
            var l1Meta = generateMeta(model, DocumentLevel.LEVEL1);
            return new DocumentAssociationForEPrescriptionDocumentMetadataDto(l3Meta, l1Meta);
        } catch (Exception e) {
            log.error("An error occurred while mapping metadata.", e);
            return null;
        }
    }

    private static EPrescriptionDocumentMetadataDto generateMeta(MetaModel model, DocumentLevel documentLevel) {
        if (!documentLevel.equals(DocumentLevel.LEVEL1) && !documentLevel.equals(DocumentLevel.LEVEL3)) {
            throw new IllegalArgumentException("Does not support documentLevel: " + documentLevel);
        }

        String documentId = switch (documentLevel) {
            case DocumentLevel.LEVEL1 ->
                toRootedId(EPrescriptionDocumentIdMapper.level1DocumentId(model.prescriptionId()));
            case DocumentLevel.LEVEL3 ->
                toRootedId(EPrescriptionDocumentIdMapper.level3DocumentId(model.prescriptionId()));
        };

        DocumentFormatDto documentFormat = switch (documentLevel) {
            case DocumentLevel.LEVEL1 -> DocumentFormatDto.PDF;
            case DocumentLevel.LEVEL3 -> DocumentFormatDto.XML;
        };

        var meta = new EPrescriptionDocumentMetadataDto(documentId);
        meta.setPatientId(model.patientId());
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
    private static MetaModel makeModel(String patientId, GetPrescriptionResponseType prescriptions, int prescriptionIndex, PackageInfo packageInfo) throws MapperException {
        var prescription = prescriptions.getPrescription().get(prescriptionIndex);
        if (prescription == null) {
            throw new MapperException("Missing prescription");
        }
        var atc = Optional.ofNullable(prescription.getDrug().getATC());
        var drugForm = Optional.ofNullable(prescription.getDrug().getForm());
        return new MetaModel(
            patientId,
            // "Date and time when the ePrescription was created" 06.02
            Utils.convertToOffsetDateTime(prescription.getCreated().getDateTime()),
            Long.toString(prescription.getIdentifier()),
            // "Name of the prescriber" 06.02
            EPrescriptionL3Mapper.getAuthorizedHealthcareProfessional(prescription).getName(),
            EPrescriptionL3Mapper.makeTitle(prescriptions, prescription),
            description(prescription),
            prescription.getPackageRestriction().getPackageNumber().getValue(),
            prescription.getDrug().getName(),
            atc.map(ATCType::getCode).map(ATCCodeType::getValue).orElse(null),
            // "When communicated, the ATC text must be either in English or in (one of) the Country of affiliation national language(s)." 06.02
            atc.map(ATCType::getText).orElse(null),
            // This drug form code is in the local national code system, but the 06.02 requirements only say:
            // "NCPeH of Country of affiliation **has the possibility** to provide English designations of the included coded data (based on the MTC)."
            // and "has the possibility" is weaker than "shall", so we should be ok.
            drugForm.map(DrugFormType::getCode).map(DrugFormCodeType::getValue).orElse(null),
            // "When communicated, the Dose form text must be either in English or in (one of) the Country of affiliation national language(s)." 06.02
            drugForm.map(DrugFormType::getText).orElse(null),
            EPrescriptionL3Mapper.drugStrengthText(prescription),
            DispensationAllowed.isDispensationAllowed(prescription, packageInfo)
        );
    }

    static String description(PrescriptionType prescription) {
        // "Textual description that identifies the prescribed product, such as the product name or generic name (list of active ingredients)"
        // Note: "Unless the extended fields are populated with relevant data, the description field should include as
        // precise as possible a description of the prescribed product (strength, dose form, ...). If the ATC code is
        // not provided in a separate field, the description field must start with the ATC code."
        // from Requirements Catalogue 06.02
        // https://webgate.ec.europa.eu/fpfis/wikis/pages/viewpage.action?pageId=888398311&spaceKey=EHDSI&title=06.02.%2BTranscode%2Btranslate%2Band%2Bexchange%2Bcross-border%2Bthe%2BePrescription%2Bs

        // Since we do populate the extended fields, we should not include the data in the description field.
        // We do not support generic ordination in Denmark (see https://laegemiddelstyrelsen.dk/da/nyheder/2025/analyse-af-fordele-og-ulemper-ved-indfoerelse-af-generisk-ordination-i-danmark/)
        // so we can always use the product name.  We also include the generic name/ATC code text, because the product
        // names vary between countries, so the generic name might be more immediately useful for the user.
        // For example: "Pinex (Paracetamol)"
        return "%s (%s)".formatted(
            prescription.getDrug().getName(),
            Optional.ofNullable(prescription.getDrug().getATC()).map(ATCType::getText).orElse("no ATC code")
        );
    }

    /// We found during the Spring test of 2025 that the countries that were already in production expected us to pass
    /// rooted ePrescription ids - so we do that now. The format is not documented anywhere I can find.
    public static String toRootedId(String id) {
        return String.format("%s^%s", Oid.DK_EPRESCRIPTION_REPOSITORY_ID.value, id);
    }

    /// Try to get an id from a string also containing the correct root "1.2.208.176.7.2.3^123456L3" -> "123456L3". If
    /// it cannot be parsed, null is returned. See also the comment on [EPrescriptionMetadataMapper#toRootedId].
    public static String fromRootedId(String input) {
        if (input == null) {
            return null;
        }
        var parts = input.split("\\^", 2);
        if (parts.length != 2) {
            return null;
        }

        var oidString = parts[0];
        var id = parts[1];
        if (id.trim().isEmpty() || !oidString.equals(Oid.DK_EPRESCRIPTION_REPOSITORY_ID.value)) {
            return null;
        }

        return id;
    }
}
