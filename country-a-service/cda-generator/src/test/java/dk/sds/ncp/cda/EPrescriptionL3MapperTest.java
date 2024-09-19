package dk.sds.ncp.cda;

import dk.nsp.epps.testing.shared.FmkResponseStorage;
import dk.sds.ncp.cda.model.EPrescriptionL3;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class EPrescriptionL3MapperTest {
    static EPrescriptionL3 getModel() {
        try {
            var response = FmkResponseStorage.storedPrescriptions(FmkResponseStorage.testCprs().get(2));

            String cda = EPrescriptionL3Generator.generate(response, 0);

            return EPrescriptionL3Mapper.model(response, 0);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void model()  {
           Assertions.assertNotNull(getModel());
    }

    @Test
    void entryTextTest() {
        var model = getModel();
        Assertions.assertEquals("1 dosis daglig", model.getEntryText());
    }

    /// etc....
}
