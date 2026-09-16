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
import jakarta.xml.bind.JAXBException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

class PatientSummaryPdfGeneratorTest {

    @Test
    void generateTest() throws JAXBException {
        var model = model();
        var pdfModel = PatientSummaryPdfMapper.map(model);
        var pdf = PatientSummaryPdfGenerator.generate(pdfModel);

        Assertions.assertNotNull(pdf);
        Assertions.assertTrue(pdf.length > 0);
    }

    @Test
    void sameFileDifferentGenerations() throws JAXBException {
        var model = model();

        var pdfModel = PatientSummaryPdfMapper.map(model);
        var pdf = PatientSummaryPdfGenerator.generate(pdfModel);
        var secondPdf = PatientSummaryPdfGenerator.generate(pdfModel);

        Assertions.assertNotNull(pdf);
        Assertions.assertNotNull(secondPdf);
        Assertions.assertArrayEquals(pdf, secondPdf);
    }

    @Test
    void generateWithoutPreferredHp() throws JAXBException {
        var model = model().withPreferredHp(null);

        var pdfModel = PatientSummaryPdfMapper.map(model);
        var pdf = PatientSummaryPdfGenerator.generate(pdfModel);

        Assertions.assertNotNull(pdf);
        Assertions.assertTrue(pdf.length > 0);
    }

    private static PatientSummaryL3 model() throws JAXBException {
        var cpr = "0410009234";

        var patient = patient(cpr);
        var preferredHp = preferredHp();
        var medicationSummary = FmkResponseStorage.getTestMedicineCards(cpr);

        var input = new PatientSummaryL3Input(
            "test-base-id",
            preferredHp,
            patient,
            medicationSummary,
            null
        );

        return PatientSummaryL3Mapper.model(input);
    }

    private static Patient patient(String cpr) {
        return Patient.builder()
            .id(new CdaId(Oid.DK_CPR, cpr))
            .name(Name.fromFullName("Hans Christian Andersen"))
            .genderCode(CdaCode.builder()
                .codeSystem(Oid.ADMINISTRATIVE_GENDER)
                .codeSystemVersion("913-20091020")
                .code("M")
                .displayName("Male")
                .build())
            .birthTime(LocalDate.of(1982, 11, 3))
            .build();
    }

    private static PreferredHealthProfessional preferredHp() {
        return PreferredHealthProfessional.builder()
            .name(Name.fromFullName("Tycho Brahe"))
            .telecoms(List.of(Telecom.builder()
                .use(Telecom.Use.WORK_PLACE)
                .value("tel:+4511111111")
                .build()))
            .address(new Address(
                List.of("Rundetårn", "Købmagergade 52A"),
                "København K",
                "1150",
                "DK"))
            .build();
    }
}
