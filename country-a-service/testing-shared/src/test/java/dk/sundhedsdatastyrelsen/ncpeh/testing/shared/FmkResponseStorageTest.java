package dk.sundhedsdatastyrelsen.ncpeh.testing.shared;
import jakarta.xml.bind.JAXBException;
import org.junit.jupiter.api.Test;
import static dk.sundhedsdatastyrelsen.ncpeh.testing.shared.FmkResponseStorage.storedPrescriptions;
class FmkResponseStorageTest {
 @Test
 void validateStoredPrescriptions() throws JAXBException {
 for (var cpr : FmkResponseStorage.rawResponseCprs()) {
  storedPrescriptions(cpr);
 }
 }
}
