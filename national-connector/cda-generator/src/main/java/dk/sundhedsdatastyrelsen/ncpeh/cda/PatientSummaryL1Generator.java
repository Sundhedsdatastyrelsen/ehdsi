package dk.sundhedsdatastyrelsen.ncpeh.cda;

import dk.sundhedsdatastyrelsen.ncpeh.cda.model.CdaId;
import dk.sundhedsdatastyrelsen.ncpeh.cda.model.PatientSummaryL1;
import freemarker.template.TemplateException;
import io.opentelemetry.instrumentation.annotations.WithSpan;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Base64;

public class PatientSummaryL1Generator {
    private PatientSummaryL1Generator() {
    }

    /// @throws MapperException if something goes wrong
    @WithSpan
    public static String generate(PatientSummaryInput input) {
        return generate(inputToModel(input));
    }

    /// @throws MapperException if something goes wrong
    public static String generate(PatientSummaryL1 dataModel) {
        try {
            var template = FreemarkerConfiguration.config().getTemplate("patient-summary-cda-l1.ftlx");
            var writer = new StringWriter();
            template.process(dataModel, writer);
            return writer.toString();
        } catch (IOException | TemplateException e) {
            throw new MapperException("Could not generate L1 patient summary", e);
        }
    }

    private static PatientSummaryL1 inputToModel(PatientSummaryInput input) {
        var l3Model = PatientSummaryL3Generator.inputToModel(input);
        var relatedL3DocumentId = deriveL3DocumentId(l3Model.getDocumentId());
        var pdf = PatientSummaryPdfGenerator.generate(l3Model);
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
