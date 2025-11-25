package dk.sundhedsdatastyrelsen.ncpeh.cda;

import dk.sundhedsdatastyrelsen.ncpeh.cda.model.EPrescriptionL3;
import freemarker.template.TemplateException;
import io.opentelemetry.instrumentation.annotations.WithSpan;

import java.io.IOException;
import java.io.StringWriter;

public class EPrescriptionL3Generator {
    private EPrescriptionL3Generator() {}

    /// @throws MapperException if something goes wrong
    public static String generate(EPrescriptionL3 dataModel) {
        try {
            var template = FreemarkerConfiguration.config().getTemplate("eprescription-cda-l3.ftlx");
            var writer = new StringWriter();
            template.process(dataModel, writer);
            return writer.toString();
        } catch (IOException | TemplateException e) {
            throw new MapperException("Couldn't map L3 ePrescription", e);
        }
    }

    /// @throws MapperException if something goes wrong
    @WithSpan
    public static String generate(EPrescriptionL3Input input) {
        var model = EPrescriptionL3Mapper.model(input);
        return generate(model);
    }
}
