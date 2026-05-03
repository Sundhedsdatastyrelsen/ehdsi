package dk.sundhedsdatastyrelsen.ncpeh.cda;

import dk.sundhedsdatastyrelsen.ncpeh.cda.model.Address;
import dk.sundhedsdatastyrelsen.ncpeh.cda.model.CdaCode;
import dk.sundhedsdatastyrelsen.ncpeh.cda.model.CdaId;
import dk.sundhedsdatastyrelsen.ncpeh.cda.model.Name;
import dk.sundhedsdatastyrelsen.ncpeh.cda.model.Patient;
import dk.sundhedsdatastyrelsen.ncpeh.cda.model.PatientSummaryL3;
import dk.sundhedsdatastyrelsen.ncpeh.cda.model.PreferredHealthProfessional;
import dk.sundhedsdatastyrelsen.ncpeh.cda.model.Telecom;
import dk.sundhedsdatastyrelsen.ncpeh.testing.shared.FmkResponseStorage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class PatientSummaryL3MapperTest {

    static PatientSummaryL3 getModel() {
        try {
            var cpr = FmkResponseStorage.rawResponseCprs().get(3);
            var response = FmkResponseStorage.storedMedicineCards(cpr);

            var patient = Patient.builder()
                .id(new CdaId(Oid.DK_CPR, cpr))
                .name(new Name("Patient", List.of("Test")))
                .genderCode(CdaCode.builder()
                    .codeSystem(Oid.ADMINISTRATIVE_GENDER)
                    .codeSystemVersion("913-20091020")
                    .code("F")
                    .displayName("Female")
                    .build())
                .birthTime(LocalDate.of(1982, 11, 3))
                .address(new Address(
                    List.of("Testvej 1"),
                    "København",
                    "2100",
                    "DK"
                ))
                .build();

            var preferredHp = PreferredHealthProfessional.builder()
                .name(Name.fromFullName("Tycho Brahe"))
                .telecoms(List.of(Telecom.builder().use(Telecom.Use.WORK_PLACE).value("tel:+4511111111").build()))
                .address(new Address(List.of("Rundetårn", "Købmagergade 52A", "Kælderen"), "København K", "1150", "DK"))
                .build();


            var input = new PatientSummaryInput(
                "test-document-id",
                preferredHp,
                patient,
                response
            );

            return PatientSummaryL3Mapper.model(input);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void model() {
        var model = getModel();
        Assertions.assertNotNull(model);
        Assertions.assertNotNull(model.getPatient());
        Assertions.assertNotNull(model.getMedicationSummary());
        Assertions.assertNotNull(model.getMedicationSummary().getItems());
        assertThat(model.getMedicationSummary().getItems(), is(not(empty())));
    }

    @Test
    void medicationEntriesHaveRequiredCoreFields() {
        var model = getModel();

        for (var medication : model.getMedicationSummary().getItems()) {
            Assertions.assertNotNull(medication.getMedicationId());
            Assertions.assertNotNull(medication.getMedicationId().getRoot());
            Assertions.assertNotNull(medication.getMedicationId().getExtension());

            Assertions.assertNotNull(medication.getDosage());
            Assertions.assertNotNull(medication.getDosage().getTag());
            Assertions.assertNotNull(medication.getDosage().getUnstructuredText());

            Assertions.assertNotNull(medication.getActiveIngredients());
            Assertions.assertNotNull(medication.getUnstructuredActiveIngredients());
        }
    }


    @Test
    void getPatientMedicationInstructions() {
        var model = getModel();

        var firstMedication = model.getMedicationSummary().getItems().getFirst();

        Assertions.assertNotNull(firstMedication);
        Assertions.assertNotNull(firstMedication.getMedicationId());
        Assertions.assertNotNull(firstMedication.getDosage());
        Assertions.assertNotNull(firstMedication.getActiveIngredients());
    }

    @Test
    void getMedicationStartDate() {
        var model = getModel();

        var firstMedication = model.getMedicationSummary().getItems().getFirst();

        Assertions.assertNotNull(firstMedication);
        Assertions.assertNotNull(firstMedication.getMedicationStartTime());
    }


    @Test
    void getPreferredHealthProfessional() {
        var model = getModel();

        var preferredHealthProfessional = model.getPreferredHp();

        Assertions.assertNotNull(preferredHealthProfessional);
        Assertions.assertNotNull(preferredHealthProfessional.getName());
    }
}
