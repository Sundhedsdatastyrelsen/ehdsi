package dk.sds.ncp.cda;

import dk.nsp.epps.test.FmkResponseStorage;
import dk.sds.ncp.cda.model.EPrescriptionL3;
import jakarta.xml.bind.JAXBException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class EPrescriptionL3MapperTest {
    static EPrescriptionL3 getModel() {
        try {
            var response = FmkResponseStorage.storedPrescriptions(FmkResponseStorage.testCprs().get(2));
            return EPrescriptionL3Mapper.model(response, 0);
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
