package dk.sds.ncp.cda;

import dk.sds.ncp.cda.model.EPrescriptionL3;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.io.StringWriter;

public class EPrescriptionL3Generator {
    public static String generate(EPrescriptionL3 ep) throws IOException, TemplateException {
        var template = FreemarkerConfiguration.config().getTemplate("eprescription-cda-l3.ftlx");
        var writer = new StringWriter();
        template.process(ep, writer);
        return writer.toString();
    }
}
