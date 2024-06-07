package dk.sds.ncp.cda;

import dk.dkma.medicinecard.xml_schema._2015._06._01.e6.GetPrescriptionResponseType;
import dk.sds.ncp.cda.model.EPrescriptionL3;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.io.StringWriter;

public class EPrescriptionL3Generator {

    public static String generate(EPrescriptionL3 dataModel) throws IOException, TemplateException {
        var template = FreemarkerConfiguration.config().getTemplate("eprescription-cda-l3.ftlx");
        var writer = new StringWriter();
        template.process(dataModel, writer);
        return writer.toString();
    }

    public static String generate(GetPrescriptionResponseType response, int prescriptionIndex)
        throws TemplateException, IOException, MapperException {

        var dataModel = EPrescriptionL3Mapper.model(response, prescriptionIndex);
        return generate(dataModel);
    }
}
