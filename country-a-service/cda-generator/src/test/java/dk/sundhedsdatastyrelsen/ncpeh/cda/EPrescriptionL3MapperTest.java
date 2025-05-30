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
            var response = FmkResponseStorage.storedPrescriptions(FmkResponseStorage.rawResponseCprs().get(2));
            var medicationResponse = FmkResponseStorage.storedDrugMedications(FmkResponseStorage.rawResponseCprs()
                .get(2));

            return EPrescriptionL3Mapper.model(new EPrescriptionL3Input(response, 0, medicationResponse, "FIN", "1", "Manufacturer"));
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
        var cpr = FmkResponseStorage.rawResponseCprs().get(2);
        var model = EPrescriptionL3Mapper.model(
            new EPrescriptionL3Input(
                FmkResponseStorage.storedPrescriptions(cpr),
                0,
                FmkResponseStorage.storedDrugMedications(cpr),
                "FIN",
                null,
                "Manufacturer"));
        assertThat(model.getProduct().getPackageInfo().getWrappedIn(), is(nullValue()));
        assertThat(model.getProduct().getPackageInfo().getValue(), is("100"));
    }

    @Test
    void emptySubpackagesIsHandled() throws Exception {
        var cpr = FmkResponseStorage.rawResponseCprs().get(2);
        var model = EPrescriptionL3Mapper.model(
            new EPrescriptionL3Input(
                FmkResponseStorage.storedPrescriptions(cpr),
                0,
                FmkResponseStorage.storedDrugMedications(cpr),
                "FIN",
                "",
                "Manufacturer"));
        assertThat(model.getProduct().getPackageInfo().getWrappedIn(), is(nullValue()));
        assertThat(model.getProduct().getPackageInfo().getValue(), is("100"));
    }
}
