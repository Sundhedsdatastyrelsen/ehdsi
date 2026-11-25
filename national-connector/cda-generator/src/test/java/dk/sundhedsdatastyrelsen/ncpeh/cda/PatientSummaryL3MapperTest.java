package dk.sundhedsdatastyrelsen.ncpeh.cda;

import dk.sundhedsdatastyrelsen.ncpeh.cda.model.Address;
import dk.sundhedsdatastyrelsen.ncpeh.cda.model.CdaCode;
import dk.sundhedsdatastyrelsen.ncpeh.cda.model.CdaId;
import dk.sundhedsdatastyrelsen.ncpeh.cda.model.Name;
import dk.sundhedsdatastyrelsen.ncpeh.cda.model.Patient;
import dk.sundhedsdatastyrelsen.ncpeh.cda.model.PatientSummaryL3;
import dk.sundhedsdatastyrelsen.ncpeh.cda.model.PreferredHealthProfessional;
import dk.sundhedsdatastyrelsen.ncpeh.cda.model.RetrievalFailure;
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
            var ddvresponse = DdvResponseStorage.getTestVaccination(cpr);

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
                ddvresponse
            );

            return PatientSummaryL3Mapper.model(input);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    static PatientSummaryL3 getModelWithEmptyMedicineCardAndVaccinationCard() {
        try {
            var cpr = "1004219992";
            var fmkResponse = FmkResponseStorage.getTestMedicineCards(cpr);
            var ddvresponse = DdvResponseStorage.getTestVaccination(cpr);

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
                fmkResponse,
                ddvresponse
            );

            return PatientSummaryL3Mapper.model(input);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }



    static PatientSummaryL3 getModelWithFailedFMKService() {
        try {
            var cpr = "1004219992";
            var ddvResponse = DdvResponseStorage.getTestVaccination(cpr);

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
                null,
                ddvResponse
            );

            return PatientSummaryL3Mapper.model(input);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    static PatientSummaryL3 getModelWithFailedDDVService() {
        try {
            var cpr = "1004219992";
            var fmkResponse = FmkResponseStorage.getTestMedicineCards(cpr);

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
                fmkResponse,
                null
            );

            return PatientSummaryL3Mapper.model(input);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    static PatientSummaryL3 getModelWithFailedAllServices() {
        try {
            var cpr = "1004219992";

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
                null,
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
        Assertions.assertNotNull(model.getMedicationSummary().getRight().getItems());
        Assertions.assertNotNull(model.getImmunizations().getRight().getItems());
        assertThat(model.getMedicationSummary().getRight().getItems(), is(not(empty())));
    }

    @Test
    void modelEmptyMedicationCardAndVaccinationCard() {
        var model = getModelWithEmptyMedicineCardAndVaccinationCard();

        assertThat(model.getMedicationSummary().getRight().getItems(), is(empty()));
        assertThat(model.getImmunizations().getRight().getItems(), is(empty()));
    }


    @Test
    void modelFailedFMKServce() {
        var model = getModelWithFailedFMKService();

        assertThat(model.getMedicationSummary().isLeft(),is(true));
        assertThat(model.getMedicationSummary().getLeft(), is(RetrievalFailure.UNAVAILABLE));

        assertThat(model.getImmunizations().isRight(), is(true));
        assertThat(model.getImmunizations().getRight().getItems(), is(empty()));
    }

    @Test
    void modelFailedDDVService() {
        var model = getModelWithFailedDDVService();

        assertThat(model.getMedicationSummary().isRight(), is(true));
        assertThat(model.getMedicationSummary().getRight().getItems(), is(empty()));

        assertThat(model.getImmunizations().isLeft(), is(true));
        assertThat(model.getImmunizations().getLeft(), is(RetrievalFailure.UNAVAILABLE));
    }

    @Test
    void modelFailedAllServices() {
        var model = getModelWithFailedAllServices();

        assertThat(model.getMedicationSummary().isLeft(), is(true));
        assertThat(model.getMedicationSummary().getLeft(), is(RetrievalFailure.UNAVAILABLE));

        assertThat(model.getImmunizations().isLeft(), is(true));
        assertThat(model.getImmunizations().getLeft(), is(RetrievalFailure.UNAVAILABLE));
    }

}
