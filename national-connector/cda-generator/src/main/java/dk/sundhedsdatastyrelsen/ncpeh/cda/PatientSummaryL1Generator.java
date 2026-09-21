package dk.sundhedsdatastyrelsen.ncpeh.cda;

import dk.sundhedsdatastyrelsen.ncpeh.cda.model.PatientSummaryL1;
import freemarker.template.TemplateException;
import io.opentelemetry.instrumentation.annotations.WithSpan;

import java.io.IOException;
import java.io.StringWriter;

public class PatientSummaryL1Generator {
    private PatientSummaryL1Generator() {
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

    /// @throws MapperException if something goes wrong
    @WithSpan
    public static String generate(PatientSummaryInput input) {
        var dataModel = PatientSummaryL1Mapper.model(input);
        return generate(dataModel);
    }
}
