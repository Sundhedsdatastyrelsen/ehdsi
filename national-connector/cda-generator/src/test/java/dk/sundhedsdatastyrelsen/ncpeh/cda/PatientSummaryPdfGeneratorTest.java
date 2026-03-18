package dk.sundhedsdatastyrelsen.ncpeh.cda;

import dk.sundhedsdatastyrelsen.ncpeh.cda.model.Address;
import dk.sundhedsdatastyrelsen.ncpeh.cda.model.CdaCode;
import dk.sundhedsdatastyrelsen.ncpeh.cda.model.CdaId;
import dk.sundhedsdatastyrelsen.ncpeh.cda.model.Name;
import dk.sundhedsdatastyrelsen.ncpeh.cda.model.Patient;
import dk.sundhedsdatastyrelsen.ncpeh.cda.model.PatientSummaryL3;
import dk.sundhedsdatastyrelsen.ncpeh.cda.model.PreferredHealthProfessional;
import dk.sundhedsdatastyrelsen.ncpeh.cda.model.Telecom;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;

class PatientSummaryPdfGeneratorTest {
    static PatientSummaryL3 getModel() {
        var patient = Patient.builder()
            .id(new CdaId(Oid.DK_CPR, "1234567890"))
            .name(Name.fromFullName("Hans Christian Andersen"))
            .genderCode(CdaCode.builder()
                .codeSystem(Oid.ADMINISTRATIVE_GENDER)
                .codeSystemVersion("913-20091020")
                .code("M")
                .displayName("Male")
                .build())
            .birthTime(LocalDate.of(1982, 11, 3))
            .build();
        var preferredHp = PreferredHealthProfessional.builder()
            .name(Name.fromFullName("Tycho Brahe"))
            .telecoms(List.of(Telecom.builder().use(Telecom.Use.WORK_PLACE).value("tel:+4511111111").build()))
            .address(new Address(List.of("Rundetårn", "Købmagergade 52A"), "København K", "1150", "DK"))
            .build();
        return PatientSummaryL3.builder()
            .documentId(new CdaId(Oid.DK_PATIENT_SUMMARY_REPOSITORY_ID,
                DocumentIdMapper.level1DocumentId("test-base-id")))
            .effectiveTime(OffsetDateTime.now())
            .title("Patient Summary")
            .patient(patient)
            .preferredHp(preferredHp)
            .build();
    }

    @Test
    void generateTest() {
        var pdf = PatientSummaryPdfGenerator.generate(getModel());
        Assertions.assertNotNull(pdf);
        Assertions.assertTrue(pdf.length > 0);

//        try {
//            java.nio.file.Files.write(
//                java.nio.file.Path.of("cda-eprescription-l1-" + getModel().getPatient().getId().getExtension() + ".pdf"),
//                pdf,
//                java.nio.file.StandardOpenOption.CREATE,
//                java.nio.file.StandardOpenOption.TRUNCATE_EXISTING
//            );
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
    }

    @Test
    void sameFileDifferentGenerations() {
        var model = getModel();
        var pdf = PatientSummaryPdfGenerator.generate(model);
        var secondPdf = PatientSummaryPdfGenerator.generate(model);

        Assertions.assertNotNull(pdf);
        Assertions.assertNotNull(secondPdf);
        Assertions.assertArrayEquals(pdf, secondPdf);
    }

    @Test
    void generateWithoutPreferredHp() {
        var model = getModel().withPreferredHp(null);
        var pdf = PatientSummaryPdfGenerator.generate(model);
        Assertions.assertNotNull(pdf);
        Assertions.assertTrue(pdf.length > 0);
    }
}
