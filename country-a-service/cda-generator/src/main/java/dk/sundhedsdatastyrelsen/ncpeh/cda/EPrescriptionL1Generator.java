package dk.sundhedsdatastyrelsen.ncpeh.cda;

import dk.dkma.medicinecard.xml_schema._2015._06._01.e6.GetPrescriptionResponseType;
import dk.sundhedsdatastyrelsen.ncpeh.cda.interfaces.ReferenceDataLookupService;
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

    public static String generate(GetPrescriptionResponseType response, int prescriptionIndex, ReferenceDataLookupService mappingService)
        throws TemplateException, IOException, MapperException {
        var dataModel = EPrescriptionL1Mapper.model(response, prescriptionIndex, mappingService);
        return generate(dataModel);
    }
}
