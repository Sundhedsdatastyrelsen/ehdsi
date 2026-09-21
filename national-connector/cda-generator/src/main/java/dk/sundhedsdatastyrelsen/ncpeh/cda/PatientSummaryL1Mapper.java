package dk.sundhedsdatastyrelsen.ncpeh.cda;

import dk.sundhedsdatastyrelsen.ncpeh.cda.model.PatientSummaryL1;
import dk.sundhedsdatastyrelsen.ncpeh.cda.model.PatientSummaryL3;

import java.util.Base64;

public final class PatientSummaryL1Mapper {
    private PatientSummaryL1Mapper() {
    }

    /// @throws MapperException if something goes wrong
    public static PatientSummaryL1 model(PatientSummaryInput input) {
        return model(PatientSummaryL3Mapper.model(input));
    }

    /// @throws MapperException if something goes wrong
    public static PatientSummaryL1 model(PatientSummaryL3 l3Model) {
        var relatedL3DocumentId = l3Model.getDocumentId();

        //remove L3 suffix and appends L1 instead.
        var modelWithL1Id = l3Model.withDocumentId(DocumentIdMapper.addL1DocumentId(DocumentIdMapper.removeDocumentIdSuffix(l3Model.getDocumentId())));

        var pdfModel = PatientSummaryPdfMapper.map(modelWithL1Id);
        var pdf = PatientSummaryPdfGenerator.generate(pdfModel);
        var base64Pdf = Base64.getEncoder().encodeToString(pdf);
        return PatientSummaryL1.builder()
            .modelData(modelWithL1Id)
            .base64EncodedDocument(base64Pdf)
            .relatedL3DocumentId(relatedL3DocumentId)
            .build();
    }


}
