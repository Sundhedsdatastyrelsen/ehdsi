package dk.sundhedsdatastyrelsen.ncpeh.cda;

import dk.sundhedsdatastyrelsen.ncpeh.cda.model.EPrescriptionL1;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.io.StringWriter;

public class EPrescriptionL1Generator {
    public static String generate(EPrescriptionL1 dataModel) throws IOException, TemplateException {
        var template = FreemarkerConfiguration.config().getTemplate("eprescription-cda-l1.ftlx");
        var writer = new StringWriter();
        template.process(dataModel, writer);
        return writer.toString();
    }

    public static String generate(EPrescriptionL3Input input)
        throws TemplateException, IOException, MapperException {
        var dataModel = EPrescriptionL1Mapper.model(input);
        return generate(dataModel);
    }
}
