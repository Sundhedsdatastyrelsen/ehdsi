package dk.sds.ncp.cda;

import dk.nsp.epps.test.FmkResponseStorage;
import dk.sds.ncp.cda.model.EPrescriptionL3;
import freemarker.template.TemplateException;
import jakarta.xml.bind.JAXBException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

class EPrescriptionL3MapperTest {
    static EPrescriptionL3 getModel() {
        try {
            var response = FmkResponseStorage.storedPrescriptions(FmkResponseStorage.testCprs().get(2));

            String cda = EPrescriptionL3Generator.generate(response, 0);

            return EPrescriptionL3Mapper.model(response, 0,cda);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void model() throws JAXBException {
           Assertions.assertNotNull(getModel());
    }

    @Test
    void entryTextTest() {
        var model = getModel();
        Assertions.assertEquals("1 dosis daglig", model.getEntryText());
    }

    /// etc....
}
