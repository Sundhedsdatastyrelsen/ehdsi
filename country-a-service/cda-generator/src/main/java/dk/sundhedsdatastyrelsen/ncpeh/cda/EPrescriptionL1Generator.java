package dk.sundhedsdatastyrelsen.ncpeh.cda;

import dk.sundhedsdatastyrelsen.ncpeh.cda.model.EPrescriptionL1;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.io.StringWriter;

public class EPrescriptionL1Generator {
    private EPrescriptionL1Generator() {}

    /// @throws MapperException if something goes wrong
    public static String generate(EPrescriptionL1 dataModel) {
        try {

            var template = FreemarkerConfiguration.config().getTemplate("eprescription-cda-l1.ftlx");
            var writer = new StringWriter();
            template.process(dataModel, writer);
            return writer.toString();
        } catch (IOException | TemplateException e) {
            throw new MapperException("Couldn't map L1 ePrescription", e);
        }
    }

    /// @throws MapperException if something goes wrong
    public static String generate(EPrescriptionL3Input input) {
        var dataModel = EPrescriptionL1Mapper.model(input);
        return generate(dataModel);
    }
}
