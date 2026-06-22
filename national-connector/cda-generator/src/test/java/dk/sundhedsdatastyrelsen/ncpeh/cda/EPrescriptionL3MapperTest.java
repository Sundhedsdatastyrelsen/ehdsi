package dk.sundhedsdatastyrelsen.ncpeh.cda;

import dk.sundhedsdatastyrelsen.ncpeh.cda.model.EPrescriptionL3;
import dk.sundhedsdatastyrelsen.ncpeh.testing.shared.FmkResponseStorage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class EPrescriptionL3MapperTest {
    static EPrescriptionL3 getModel() {
        try {
            var cpr = "0201909309";
            var response = FmkResponseStorage.getTestPrescriptions(cpr);
            var medicationResponse = FmkResponseStorage.getTestDrugMedications(cpr);

            return EPrescriptionL3Mapper.model(new EPrescriptionL3Input(response, 0, medicationResponse, "FIN", 1, "Manufacturer", "2025-01"));
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

    @Test
    void nullSubpackagesIsHandled() throws Exception {
        var cpr = "0201909309";
        var model = EPrescriptionL3Mapper.model(
            new EPrescriptionL3Input(
                FmkResponseStorage.getTestPrescriptions(cpr),
                0,
                FmkResponseStorage.getTestDrugMedications(cpr),
                "FIN",
                null,
                "Manufacturer",
                "2025-01"));
        assertThat(model.getProduct().getInnermostPackageLayer().getWrappedIn(), is(nullValue()));
        assertThat(model.getProduct().getInnermostPackageLayer().getAmount(), is("100"));
    }
}
