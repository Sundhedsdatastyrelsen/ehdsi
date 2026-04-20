package dk.sundhedsdatastyrelsen.ncpeh.cda;

import dk.sundhedsdatastyrelsen.ncpeh.cda.model.PatientSummaryL3;
import freemarker.template.TemplateException;
import io.opentelemetry.instrumentation.annotations.WithSpan;

import java.io.IOException;
import java.io.StringWriter;

public class PatientSummaryL3Generator {
    private PatientSummaryL3Generator() {
    }

    /// @throws MapperException if something goes wrong
    public static String generate(PatientSummaryL3 dataModel) {
        try {
            var template = FreemarkerConfiguration.config().getTemplate("patient-summary-cda-l3.ftlx");
            var writer = new StringWriter();
            template.process(dataModel, writer);
            return writer.toString();
        } catch (IOException | TemplateException e) {
            throw new MapperException("Could not generate L3 patient summary", e);
        }
    }

    /// @throws MapperException if something goes wrong
    @WithSpan
    public static String generate(PatientSummaryInput input) {
        var model = inputToModel(input);
        return generate(model);
    }

    static PatientSummaryL3 inputToModel(PatientSummaryInput input) {
        return PatientSummaryL3Mapper.model(input);
    }
}
