package dk.nsp.epps.testing.shared;

import jakarta.xml.bind.JAXBException;
import org.junit.jupiter.api.Test;

import static dk.nsp.epps.testing.shared.FmkResponseStorage.storedPrescriptions;

class FmkResponseStorageTest {
    @Test
    void validateStoredPrescriptions() throws JAXBException {
        for (var cpr : FmkResponseStorage.testCprs()) {
            storedPrescriptions(cpr);
        }
    }
}
