package dk.sundhedsdatastyrelsen.ncpeh.cda;

import dk.sundhedsdatastyrelsen.ncpeh.cda.model.Address;
import dk.sundhedsdatastyrelsen.ncpeh.cda.model.CdaCode;
import dk.sundhedsdatastyrelsen.ncpeh.cda.model.CdaId;
import dk.sundhedsdatastyrelsen.ncpeh.cda.model.Name;
import dk.sundhedsdatastyrelsen.ncpeh.cda.model.Patient;
import dk.sundhedsdatastyrelsen.ncpeh.cda.model.PatientSummaryL3;
import dk.sundhedsdatastyrelsen.ncpeh.cda.model.PreferredHealthProfessional;
import dk.sundhedsdatastyrelsen.ncpeh.cda.model.Telecom;
import dk.sundhedsdatastyrelsen.ncpeh.testing.shared.DdvResponseStorage;
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
            var cpr = "0410009234";
            var fmkresponse = FmkResponseStorage.getTestMedicineCards(cpr);

            var patient = Patient.builder()
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

            var preferredHp = PreferredHealthProfessional.builder()
                .name(Name.fromFullName("Tycho Brahe"))
                .telecoms(List.of(Telecom.builder().use(Telecom.Use.WORK_PLACE).value("tel:+4511111111").build()))
                .address(new Address(List.of("Rundetårn", "Købmagergade 52A", "Kælderen"), "København K", "1150", "DK"))
                .build();

            var input = new PatientSummaryInput(
                "test-document-id",
                preferredHp,
                patient,
                fmkresponse,
                null
            );

            return PatientSummaryL3Mapper.model(input);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    static PatientSummaryL3 getModelWithEmptyMedicineCard() {
        try {
            var cpr = "1004219992";
            var response = FmkResponseStorage.getTestMedicineCards(cpr);

            var patient = Patient.builder()
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

            var preferredHp = PreferredHealthProfessional.builder()
                .name(Name.fromFullName("Tycho Brahe"))
                .telecoms(List.of(Telecom.builder().use(Telecom.Use.WORK_PLACE).value("tel:+4511111111").build()))
                .address(new Address(List.of("Rundetårn", "Købmagergade 52A", "Kælderen"), "København K", "1150", "DK"))
                .build();

            var input = new PatientSummaryInput(
                "test-document-id",
                preferredHp,
                patient,
                response,
                null
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
    void modelEmptyMedicationCard() {
        var model = getModelWithEmptyMedicineCard();

        assertThat(model.getMedicationSummary().getItems(), is(empty()));
    }

}
