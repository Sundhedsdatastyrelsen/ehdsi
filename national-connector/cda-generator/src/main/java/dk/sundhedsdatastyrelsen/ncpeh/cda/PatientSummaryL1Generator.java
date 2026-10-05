package dk.sundhedsdatastyrelsen.ncpeh.cda;

import dk.sundhedsdatastyrelsen.ncpeh.cda.model.CdaId;
import dk.sundhedsdatastyrelsen.ncpeh.cda.model.PatientSummaryL3;
import freemarker.template.TemplateException;
import io.opentelemetry.instrumentation.annotations.WithSpan;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Base64;

public class PatientSummaryL1Generator {
    private PatientSummaryL1Generator() {
    }

    /// @throws MapperException if something goes wrong
    @WithSpan
    public static String generate(PatientSummaryInput input) {
        try {
            var dataModel = PatientSummaryL3Mapper.model(input);
            var relatedDocumentId = dataModel.getDocumentId();

            //remove L3 suffix and appends L1 instead.
            var modelWithL1Id = dataModel.withDocumentId(DocumentIdMapper.addL1DocumentId(DocumentIdMapper.removeDocumentIdSuffix(dataModel.getDocumentId())));

            var pdf = generatePdf(modelWithL1Id);
            var template = FreemarkerConfiguration.config().getTemplate("patient-summary-cda-l1.ftlx");
            var writer = new StringWriter();

            var patientSummaryL1 = PatientSummaryL1.builder().modelData(modelWithL1Id).base64EncodedDocument(pdf).relatedL3DocumentId(relatedDocumentId).build();
            template.process(patientSummaryL1, writer);
            return writer.toString();
        } catch (IOException | TemplateException e) {
            throw new MapperException("Could not generate L1 patient summary", e);
        }
   }

    /// @throws MapperException if something goes wrong
    private static String generatePdf(PatientSummaryL3 dataModel) {
        var pdfModel = PatientSummaryPdfMapper.map(dataModel);
        var pdf = PatientSummaryPdfGenerator.generate(pdfModel);
        return Base64.getEncoder().encodeToString(pdf);
    }

    @Value
    @Builder
    public static class PatientSummaryL1 {
        @NonNull String base64EncodedDocument;
        /**
         * The underlying structured document, with the L1 document ID applied.
         */
        @NonNull PatientSummaryL3 modelData;
        /**
         * The document ID of the corresponding L3 document, used in the relatedDocument element.
         */
        @NonNull CdaId relatedL3DocumentId;
    }
}
