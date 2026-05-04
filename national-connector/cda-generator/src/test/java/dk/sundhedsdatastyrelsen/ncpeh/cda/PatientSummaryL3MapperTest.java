package dk.sundhedsdatastyrelsen.ncpeh.cda;

import dk.sundhedsdatastyrelsen.ncpeh.cda.model.Address;
import dk.sundhedsdatastyrelsen.ncpeh.cda.model.CdaCode;
import dk.sundhedsdatastyrelsen.ncpeh.cda.model.CdaId;
import dk.sundhedsdatastyrelsen.ncpeh.cda.model.MedicationSummary;
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

            var patient = validPatient(cpr);

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

    private static Patient validPatient(String cpr) {
        return Patient.builder()
            .id(new CdaId(Oid.DK_CPR, cpr))
            .name(new Name("Bach", List.of("Sofie")))
            .genderCode(CdaCode.builder()
                .codeSystem(Oid.ADMINISTRATIVE_GENDER)
                .codeSystemVersion("913-20091020")
                .code("F")
                .displayName("Female")
                .build())
            .birthTime(LocalDate.of(2000, 10, 4))
            .address(new Address(
                List.of("Morbærvej 200"),
                "København K",
                "1362",
                "DK"
            ))
            .build();
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
    void getPreferredHealthProfessional() {
        var model = getModel();

        var preferredHealthProfessional = model.getPreferredHp();

        Assertions.assertNotNull(preferredHealthProfessional);
        Assertions.assertNotNull(preferredHealthProfessional.getName());
    }

    @Test
    void nullPreferredHealthProfessionalIsHandled() throws Exception {
        var cpr = FmkResponseStorage.rawResponseCprs().get(3);
        var response = FmkResponseStorage.storedMedicineCards(cpr);

        var input = new PatientSummaryInput(
            "test-document-id",
            null,
            validPatient(cpr),
            response
        );

        var model = PatientSummaryL3Mapper.model(input);

        assertThat(model.getPreferredHp(), is(nullValue()));
    }

    @Test
    void mapsAllMedicationEntriesFromMedicineCard() {
        var model = getModel();

        assertThat(model.getMedicationSummary().getItems(), hasSize(5));
    }

    @Test
    void mapsPatientFromInput() {
        var patient = getModel().getPatient();

        assertThat(patient.getId().getExtension(), is("0410009234"));
        assertThat(patient.getName().getFamily(), is("Bach"));
        assertThat(patient.getName().getGivens(), contains("Sofie"));
        assertThat(patient.getBirthTime(), is("20001004"));
        assertThat(patient.getGenderCode().getCode(), is("F"));
    }

    @Test
    void medicationEntriesHaveActiveIngredientText() {
        var ingredients = getModel().getMedicationSummary().getItems().stream()
            .map(MedicationSummary.MedicationItem::getUnstructuredActiveIngredients)
            .toList();

        assertThat(ingredients, hasItem(containsString("IBUPROFEN")));
        assertThat(ingredients, hasItem(containsString("PARACETAMOL")));
        assertThat(ingredients, hasItem(containsString("SILDENAFILCITRAT")));
    }

    @Test
    void patientInputReflectsMedicineCardXmlPatient() {
        var patient = getModel().getPatient();

        assertThat(patient.getId().getExtension(), is("0410009234"));
        assertThat(patient.getName().getFamily(), is("Bach"));
        assertThat(patient.getName().getGivens(), contains("Sofie"));
        assertThat(patient.getBirthTime(), is("20001004"));
        assertThat(patient.getGenderCode().getCode(), is("F"));
        assertThat(patient.getAddress().getStreetAddressLines(), contains("Morbærvej 200"));
        assertThat(patient.getAddress().getCity(), is("København K"));
        assertThat(patient.getAddress().getPostalCode(), is("1362"));
        assertThat(patient.getAddress().getCountryCode(), is("DK"));
    }

    //TODO: Negative tests for e.g nullPatientThrowsException, nullFmkResponseThrowsException, Empty medicine card (Should ever happen, but if it does), All optional inputs null except required (only have for medication right now)
}
