package dk.sundhedsdatastyrelsen.ncpeh.cda;

import dk.sundhedsdatastyrelsen.ncpeh.cda.model.PatientSummaryL3;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.io.StringWriter;

public class PatientSummaryL3Generator {
    private PatientSummaryL3Generator() {
    }

    public static String generate(PatientSummaryL3 dataModel) throws IOException, TemplateException {
        var template = FreemarkerConfiguration.config().getTemplate("patient-summary-cda-l3.ftlx");
        var writer = new StringWriter();
        template.process(dataModel, writer);
        return writer.toString();
    }
}
