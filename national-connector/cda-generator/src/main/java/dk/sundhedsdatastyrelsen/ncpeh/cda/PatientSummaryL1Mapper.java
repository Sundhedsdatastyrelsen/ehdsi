package dk.sundhedsdatastyrelsen.ncpeh.cda;

import dk.sundhedsdatastyrelsen.ncpeh.cda.model.CdaId;
import dk.sundhedsdatastyrelsen.ncpeh.cda.model.PatientSummaryL1;
import dk.sundhedsdatastyrelsen.ncpeh.cda.model.PatientSummaryL3;

import java.util.Base64;

public final class PatientSummaryL1Mapper {
    private PatientSummaryL1Mapper() {
    }

    /// @throws MapperException if something goes wrong
    public static PatientSummaryL1 model(PatientSummaryL3Input input) {
        return model(PatientSummaryL3Mapper.model(input));
    }

    /// @throws MapperException if something goes wrong
    public static PatientSummaryL1 model(PatientSummaryL3 l3Model) {
        var relatedL3DocumentId = deriveL3DocumentId(l3Model.getDocumentId());
        var pdfModel = PatientSummaryPdfMapper.map(l3Model);
        var pdf = PatientSummaryPdfGenerator.generate(pdfModel);
        var base64Pdf = Base64.getEncoder().encodeToString(pdf);
        return PatientSummaryL1.builder()
            .modelData(l3Model)
            .base64EncodedDocument(base64Pdf)
            .relatedL3DocumentId(relatedL3DocumentId)
            .build();
    }

    private static CdaId deriveL3DocumentId(CdaId l1DocumentId) {
        var extension = l1DocumentId.getExtension();
        var baseId = extension != null && extension.endsWith("L1")
            ? extension.substring(0, extension.length() - 2)
            : extension;
        return new CdaId(Oid.DK_PATIENT_SUMMARY_REPOSITORY_ID, DocumentIdMapper.level3DocumentId(baseId));
    }
}
