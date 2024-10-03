package dk.nsp.epps.script;

import dk.nsp.epps.integration.GenerateCdaDocument;
import dk.nsp.epps.testing.shared.TestingInput;
import dk.sds.ncp.cda.MapperException;
import freemarker.template.TemplateException;
import jakarta.xml.bind.JAXBException;

import java.io.IOException;

public class GenerateCdaDocumentUtil {
    public static void main(String[] args) throws TemplateException, JAXBException, MapperException, IOException {
        for (var cpr : TestingInput.testingCprs()) {
            GenerateCdaDocument.generateCdaDocumentForCpr(
                cpr,
                TestingInput.preparedFilesMark(),
                TestingInput.preparedFilesMark());
        }
    }
}
