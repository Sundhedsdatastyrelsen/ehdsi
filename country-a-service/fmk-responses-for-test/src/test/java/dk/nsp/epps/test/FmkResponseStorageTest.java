package dk.nsp.epps.test;

import jakarta.xml.bind.JAXBException;
import org.junit.jupiter.api.Test;

import static dk.nsp.epps.test.FmkResponseStorage.storedPrescriptions;
import static org.junit.jupiter.api.Assertions.*;

class FmkResponseStorageTest {
    @Test
    void validateStoredPrescriptions() throws JAXBException {
        for (var cpr : FmkResponseStorage.testCprs()) {
            storedPrescriptions(cpr);
        }
    }
}
