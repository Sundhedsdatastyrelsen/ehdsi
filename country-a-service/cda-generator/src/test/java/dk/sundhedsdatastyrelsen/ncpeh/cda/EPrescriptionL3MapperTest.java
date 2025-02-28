package dk.sundhedsdatastyrelsen.ncpeh.cda;

import dk.sundhedsdatastyrelsen.ncpeh.cda.mocks.referenceDataLookupServiceMock;
import dk.sundhedsdatastyrelsen.ncpeh.testing.shared.FmkResponseStorage;
import dk.sundhedsdatastyrelsen.ncpeh.cda.model.EPrescriptionL3;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class EPrescriptionL3MapperTest {
    static EPrescriptionL3 getModel() {
        try {
            var response = FmkResponseStorage.storedPrescriptions(FmkResponseStorage.rawResponseCprs().get(2));
            var medicationResponse = FmkResponseStorage.storedDrugMedications(FmkResponseStorage.rawResponseCprs().get(2));

            return EPrescriptionL3Mapper.model(response, 0, medicationResponse, new referenceDataLookupServiceMock());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void model() {
        Assertions.assertNotNull(getModel());
    }

    @Test
    void getPatientMedicationInstructions() {
        var model = getModel();
        Assertions.assertNotNull(model.getPatientMedicationInstructions());
    }

    @Test
    void getMedicationStartDate() {

        var model = getModel();
        Assertions.assertNotNull(model.getMedicationStartTime());
    }


    /// etc....
}
