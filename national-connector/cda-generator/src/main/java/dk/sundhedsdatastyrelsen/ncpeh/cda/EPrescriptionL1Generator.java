package dk.sundhedsdatastyrelsen.ncpeh.cda;

import dk.sundhedsdatastyrelsen.ncpeh.cda.model.CdaId;
import dk.sundhedsdatastyrelsen.ncpeh.cda.model.EPrescriptionL3;
import freemarker.template.TemplateException;
import io.opentelemetry.instrumentation.annotations.WithSpan;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Base64;

public class EPrescriptionL1Generator {
    private EPrescriptionL1Generator() {}

    /// @throws MapperException if something goes wrong
    @WithSpan
    public static String generate(EPrescriptionInput input) {
        try {
            var dataModel = EPrescriptionL3Mapper.model(input);

            //remove L3 suffix and appends L1 instead.
            var modelWithL1Id = dataModel.withDocumentId(new CdaId(
                Oid.DK_EPRESCRIPTION_REPOSITORY_ID,
                DocumentIdMapper.level1DocumentId(dataModel.getPrescriptionId().getExtension())));

            var pdf = generatePdf(modelWithL1Id);
            var template = FreemarkerConfiguration.config().getTemplate("eprescription-cda-l1.ftlx");
            var writer = new StringWriter();

            var EPrescriptionL1 = EPrescriptionL1Generator.EPrescriptionL1.builder()
                .l3(modelWithL1Id)
                .base64EncodedDocument(pdf)
                .build();

            template.process(EPrescriptionL1, writer);
            return writer.toString();
        } catch (IOException | TemplateException e) {
            throw new MapperException("Could not generate L1 patient summary", e);
        }
    }

    /// @throws MapperException if something goes wrong
    private static String generatePdf(EPrescriptionL3 dataModel) {
        var pdfModel = EPrescriptionPdfMapper.map(dataModel);
        var pdf = EPrescriptionPdfGenerator.generate(pdfModel);
        return Base64.getEncoder().encodeToString(pdf);
    }

    @Value
    @Builder
    public static class EPrescriptionL1 {
        @NonNull String base64EncodedDocument;
        /**
         * The underlying structured document
         */
        @NonNull EPrescriptionL3 l3;
    }
}
