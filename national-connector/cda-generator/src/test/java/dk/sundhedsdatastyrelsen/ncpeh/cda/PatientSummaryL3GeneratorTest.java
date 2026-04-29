package dk.sundhedsdatastyrelsen.ncpeh.cda;

import dk.sundhedsdatastyrelsen.ncpeh.cda.model.Address;
import dk.sundhedsdatastyrelsen.ncpeh.cda.model.ActiveIngredient;
import dk.sundhedsdatastyrelsen.ncpeh.cda.model.CdaCode;
import dk.sundhedsdatastyrelsen.ncpeh.cda.model.CdaId;
import dk.sundhedsdatastyrelsen.ncpeh.cda.model.Dosage;
import dk.sundhedsdatastyrelsen.ncpeh.cda.model.MedicationSummary;
import dk.sundhedsdatastyrelsen.ncpeh.cda.model.MedicationSummary.MedicationItem;
import dk.sundhedsdatastyrelsen.ncpeh.cda.model.Name;
import dk.sundhedsdatastyrelsen.ncpeh.cda.model.PackageLayer;
import dk.sundhedsdatastyrelsen.ncpeh.cda.model.PackageUnit;
import dk.sundhedsdatastyrelsen.ncpeh.cda.model.Patient;
import dk.sundhedsdatastyrelsen.ncpeh.cda.model.PatientSummaryL3;
import dk.sundhedsdatastyrelsen.ncpeh.cda.model.PreferredHealthProfessional;
import dk.sundhedsdatastyrelsen.ncpeh.cda.model.Product;
import dk.sundhedsdatastyrelsen.ncpeh.cda.model.Telecom;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.xmlunit.builder.Input;
import org.xmlunit.xpath.JAXPXPathEngine;
import org.xmlunit.xpath.XPathEngine;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class PatientSummaryL3GeneratorTest {
    @Test
    void generateTest() {
        var oid = Oid.DK_PATIENT_SUMMARY;
        var extension = "1230879456";
        var title = "Generate PS L3 Document Title";
        var creationTimestamp = OffsetDateTime.now();
        var patient = Patient.builder()
            .id(new CdaId(Oid.DK_CPR, "1234567890"))
            .name(Name.fromFullName("Hans Christian Andersen"))
            .address(new Address(List.of("Overgaden Oven Vandet 10", "1."), "København K", "1415", "DK"))
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
            .address(new Address(List.of("Rundetårn", "Købmagergade 52A", "Kælderen"), "København K", "1150", "DK"))
            .build();

        var medication1 = MedicationItem.builder()
            .medicationId(new CdaId(Oid.DK_FMK_MEDICATION, "13143641280998"))
            .medicationStartTime(OffsetDateTime.parse("2025-01-10T00:00:00+01:00"))
            .medicationEndTime(OffsetDateTime.parse("2025-01-20T00:00:00+01:00"))
            .routeOfAdministration(CdaCode.builder()
                .codeSystem(Oid.DK_LMS11)
                .code("OR")
                .displayName("Oral use")
                .build())
            .dosage(new Dosage.PeriodicInterval(
                "1 tablet morgen og aften",
                true,
                new Dosage.Period.Simple("d", BigDecimal.ONE),
                new Dosage.Quantity(
                    BigDecimal.ONE,
                    new Dosage.Unit.Translated("tablet"),
                    null
                ),
                null
            ))
            .product(Product.builder()
                .drugId(CdaCode.builder()
                    .codeSystem(Oid.DK_DRUG_ID)
                    .code("28100902676")
                    .displayName("Primcillin")
                    .build())
                .name("Primcillin")
                .strength("250 mg")
                .formCode(CdaCode.builder()
                    .codeSystem(Oid.DK_LMS22)
                    .code("TABFILM")
                    .displayName("filmovertrukne tabletter")
                    .build())
                .atcCode(CdaCode.builder()
                    .codeSystem(Oid.ATC)
                    .code("J01CE02")
                    .displayName("Phenoxymethylpenicillin")
                    .build())
                .innermostPackageLayer(PackageLayer.builder()
                    .amount(BigDecimal.valueOf(20))
                    .unit(new PackageUnit.WithCode("1"))
                    .description("20 tablets")
                    .build())
                .manufacturerOrganizationName("Test Pharma A/S")
                .build())
            .activeIngredients(List.of(
                ActiveIngredient.builder()
                    .name("Phenoxymethylpenicillinkalium")
                    .build()))
            .unstructuredActiveIngredients("Phenoxymethylpenicillinkalium")
            .indicationText("behandling af infektion")
            .patientMedicationInstructions("Tag 2 tabletter om morgen og 2 tabletter om aftenen")
            .build();

        var medication2 = MedicationItem.builder()
            .medicationId(new CdaId(Oid.DK_FMK_MEDICATION, "13143641280999"))
            .medicationStartTime(OffsetDateTime.parse("2025-02-01T00:00:00+01:00"))
            .routeOfAdministration(CdaCode.builder()
                .codeSystem(Oid.DK_LMS11)
                .code("OR")
                .displayName("Oral use")
                .build())
            .dosage(new Dosage.Unstructured(
                "1 tablet dagligt",
                "Test dosage"))
            .product(Product.builder()
                .drugId(CdaCode.builder()
                    .codeSystem(Oid.DK_DRUG_ID)
                    .code("28107001111")
                    .displayName("Panodil")
                    .build())
                .name("Panodil")
                .strength("500 mg")
                .formCode(CdaCode.builder()
                    .codeSystem(Oid.DK_LMS22)
                    .code("TAB")
                    .displayName("tabletter")
                    .build())
                .atcCode(CdaCode.builder()
                    .codeSystem(Oid.ATC)
                    .code("N02BE01")
                    .displayName("Paracetamol")
                    .build())
                .innermostPackageLayer(PackageLayer.builder()
                    .amount(BigDecimal.valueOf(10))
                    .unit(new PackageUnit.WithCode("1"))
                    .description("10 tabletter")
                    .build())
                .manufacturerOrganizationName("Test Pharma A/S")
                .build())
            .activeIngredients(List.of(
                ActiveIngredient.builder()
                    .name("Paracetamol")
                    .build()))
            .unstructuredActiveIngredients("Paracetamol")
            .indicationText("Smertestillende")
            .patientMedicationInstructions("Tag 1 tablet dagligt")
            .build();

        var medicationSummary = MedicationSummary.builder()
            .items(List.of(medication1, medication2))
            .build();

        var model = PatientSummaryL3.builder()
            .documentId(new CdaId(oid, extension))
            .effectiveTime(creationTimestamp)
            .title(title)
            .patient(patient)
            .preferredHp(preferredHp)
            .medicationSummary(medicationSummary)
            .build();
        var cda = PatientSummaryL3Generator.generate(model);
        Assertions.assertNotNull(cda);

        //Initialize xPath Engine to read the data to validate it
        XPathEngine xpathEngine = new JAXPXPathEngine();
        Map<String, String> namespaces = new HashMap<>();
        namespaces.put("hl7", "urn:hl7-org:v3");
        xpathEngine.setNamespaceContext(namespaces);

        String oidPath = "/hl7:ClinicalDocument/hl7:id/@root";
        String idExtensionPath = "/hl7:ClinicalDocument/hl7:id/@extension";
        String titlePath = "/hl7:ClinicalDocument/hl7:title";
        String creationTimestampPath = "/hl7:ClinicalDocument/hl7:effectiveTime/@value";
        String patientBirthTimePath = "/hl7:ClinicalDocument/hl7:recordTarget//hl7:birthTime/@value";
        String softwareNamePath = "/hl7:ClinicalDocument/hl7:author//hl7:softwareName";

        var generatedCda = Input.fromString(cda).build();

        assertThat("oid matches", xpathEngine.evaluate(oidPath, generatedCda), is(oid.value));
        assertThat("extension matches", xpathEngine.evaluate(idExtensionPath, generatedCda), is(extension));
        assertThat("title matches", xpathEngine.evaluate(titlePath, generatedCda), is(title));
        assertThat(
            "creation time matches",
            xpathEngine.evaluate(creationTimestampPath, generatedCda),
            is(Utils.cdaZonedDateTime(creationTimestamp)));
        assertThat("patient birth time matches", xpathEngine.evaluate(patientBirthTimePath, generatedCda), is("19821103"));
        assertThat(
            "patient family name is correct",
            xpathEngine.evaluate("/hl7:ClinicalDocument/hl7:recordTarget//hl7:patient/hl7:name/hl7:family", generatedCda),
            is("Andersen"));
        assertThat(
            "patient first name is correct",
            xpathEngine.evaluate("(/hl7:ClinicalDocument/hl7:recordTarget//hl7:patient/hl7:name/hl7:given)[1]", generatedCda),
            is("Hans"));
        assertThat(
            "patient middle name is correct",
            xpathEngine.evaluate("(/hl7:ClinicalDocument/hl7:recordTarget//hl7:patient/hl7:name/hl7:given)[2]", generatedCda),
            is("Christian"));
        assertThat("software name is there", xpathEngine.evaluate(softwareNamePath, generatedCda), is("NCPeH Denmark"));
        assertThat(
            "preferred health professional phone is correct",
            xpathEngine.evaluate("/hl7:ClinicalDocument/hl7:participant//hl7:telecom/@value", generatedCda),
            is("tel:+4511111111"));
        assertThat(
            "there are two medication entries",
            xpathEngine.evaluate("count(/hl7:ClinicalDocument/hl7:component/hl7:structuredBody/hl7:component[1]/hl7:section/hl7:entry)", generatedCda),
            is("2"));

        assertThat(
            "first medication name is correct",
            xpathEngine.evaluate("(/hl7:ClinicalDocument//hl7:section[hl7:code/@code='10160-0']//hl7:manufacturedMaterial/hl7:name)[1]", generatedCda),
            is("Primcillin"));

        assertThat(
            "second medication name is correct",
            xpathEngine.evaluate("(/hl7:ClinicalDocument//hl7:section[hl7:code/@code='10160-0']//hl7:manufacturedMaterial/hl7:name)[2]", generatedCda),
            is("Panodil"));
    }

    @Test
    void noPreferredHpTest() {
        var oid = Oid.DK_PATIENT_SUMMARY;
        var extension = "1230879456";
        var title = "Generate PS L3 Document Title";
        var creationTimestamp = OffsetDateTime.now();
        var patient = Patient.builder()
            .id(new CdaId(Oid.DK_CPR, "1234567890"))
            .name(Name.fromFullName("Hans Christian Andersen"))
            .address(new Address(List.of("Overgaden Oven Vandet 10", "1."), "København K", "1415", "DK"))
            .genderCode(CdaCode.builder()
                .codeSystem(Oid.ADMINISTRATIVE_GENDER)
                .codeSystemVersion("913-20091020")
                .code("M")
                .displayName("Male")
                .build())
            .birthTime(LocalDate.of(1982, 11, 3))
            .build();

        var medication1 = MedicationItem.builder()
            .medicationId(new CdaId(Oid.DK_FMK_MEDICATION, "13143641280998"))
            .medicationStartTime(OffsetDateTime.parse("2025-01-10T00:00:00+01:00"))
            .medicationEndTime(OffsetDateTime.parse("2025-01-20T00:00:00+01:00"))
            .routeOfAdministration(CdaCode.builder()
                .codeSystem(Oid.DK_LMS11)
                .code("OR")
                .displayName("Oral use")
                .build())
            .dosage(new Dosage.PeriodicInterval(
                "1 tablet morgen og aften",
                true,
                new Dosage.Period.Simple("d", BigDecimal.ONE),
                new Dosage.Quantity(
                    BigDecimal.ONE,
                    new Dosage.Unit.Translated("tablet"),
                    null
                ),
                null
            ))
            .product(Product.builder()
                .drugId(CdaCode.builder()
                    .codeSystem(Oid.DK_DRUG_ID)
                    .code("28100902676")
                    .displayName("Primcillin")
                    .build())
                .name("Primcillin")
                .strength("250 mg")
                .formCode(CdaCode.builder()
                    .codeSystem(Oid.DK_LMS22)
                    .code("TABFILM")
                    .displayName("filmovertrukne tabletter")
                    .build())
                .atcCode(CdaCode.builder()
                    .codeSystem(Oid.ATC)
                    .code("J01CE02")
                    .displayName("Phenoxymethylpenicillin")
                    .build())
                .innermostPackageLayer(PackageLayer.builder()
                    .amount(BigDecimal.valueOf(20))
                    .unit(new PackageUnit.WithCode("1"))
                    .description("20 tablets")
                    .build())
                .manufacturerOrganizationName("Test Pharma A/S")
                .build())
            .activeIngredients(List.of(
                ActiveIngredient.builder()
                    .name("Phenoxymethylpenicillinkalium")
                    .build()))
            .unstructuredActiveIngredients("Phenoxymethylpenicillinkalium")
            .indicationText("behandling af infektion")
            .patientMedicationInstructions("Tag 2 tabletter om morgen og 2 tabletter om aftenen")
            .build();

        var medication2 = MedicationItem.builder()
            .medicationId(new CdaId(Oid.DK_FMK_MEDICATION, "13143641280999"))
            .medicationStartTime(OffsetDateTime.parse("2025-02-01T00:00:00+01:00"))
            .routeOfAdministration(CdaCode.builder()
                .codeSystem(Oid.DK_LMS11)
                .code("OR")
                .displayName("Oral use")
                .build())
            .dosage(new Dosage.Unstructured(
                "1 tablet dagligt",
                "Test dosage"))
            .product(Product.builder()
                .drugId(CdaCode.builder()
                    .codeSystem(Oid.DK_DRUG_ID)
                    .code("28107001111")
                    .displayName("Panodil")
                    .build())
                .name("Panodil")
                .strength("500 mg")
                .formCode(CdaCode.builder()
                    .codeSystem(Oid.DK_LMS22)
                    .code("TAB")
                    .displayName("tabletter")
                    .build())
                .atcCode(CdaCode.builder()
                    .codeSystem(Oid.ATC)
                    .code("N02BE01")
                    .displayName("Paracetamol")
                    .build())
                .innermostPackageLayer(PackageLayer.builder()
                    .amount(BigDecimal.valueOf(10))
                    .unit(new PackageUnit.WithCode("1"))
                    .description("10 tabletter")
                    .build())
                .manufacturerOrganizationName("Test Pharma A/S")
                .build())
            .activeIngredients(List.of(
                ActiveIngredient.builder()
                    .name("Paracetamol")
                    .build()))
            .unstructuredActiveIngredients("Paracetamol")
            .indicationText("Smertestillende")
            .patientMedicationInstructions("Tag 1 tablet dagligt")
            .build();

        var medicationSummary = MedicationSummary.builder()
            .items(List.of(medication1, medication2))
            .build();

        var model = PatientSummaryL3.builder()
            .documentId(new CdaId(oid, extension))
            .effectiveTime(creationTimestamp)
            .title(title)
            .patient(patient)
            .preferredHp(null)
            .medicationSummary(medicationSummary)
            .build();
        var cda = PatientSummaryL3Generator.generate(model);
        Assertions.assertNotNull(cda);
    }

    @Test
    void emptyCountryTest() {
        // It is an error if the country in an address is there but has no value.
        var oid = Oid.DK_PATIENT_SUMMARY;
        var extension = "1230879456";
        var title = "Generate PS L3 Document Title";
        var creationTimestamp = OffsetDateTime.now();
        var patient = Patient.builder()
            .id(new CdaId(Oid.DK_CPR, "1234567890"))
            .name(Name.fromFullName("Hans Christian Andersen"))
            .address(new Address(List.of("Overgaden Oven Vandet 10", "1."), "København K", "1415", ""))
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
            .address(new Address(List.of("Rundetårn", "Købmagergade 52A", "Kælderen"), "København K", "1150", ""))
            .build();

        var medication1 = MedicationItem.builder()
            .medicationId(new CdaId(Oid.DK_FMK_MEDICATION, "13143641280998"))
            .medicationStartTime(OffsetDateTime.parse("2025-01-10T00:00:00+01:00"))
            .medicationEndTime(OffsetDateTime.parse("2025-01-20T00:00:00+01:00"))
            .routeOfAdministration(CdaCode.builder()
                .codeSystem(Oid.DK_LMS11)
                .code("OR")
                .displayName("Oral use")
                .build())
            .dosage(new Dosage.PeriodicInterval(
                "1 tablet morgen og aften",
                true,
                new Dosage.Period.Simple("d", BigDecimal.ONE),
                new Dosage.Quantity(
                    BigDecimal.ONE,
                    new Dosage.Unit.Translated("tablet"),
                    null
                ),
                null
            ))
            .product(Product.builder()
                .drugId(CdaCode.builder()
                    .codeSystem(Oid.DK_DRUG_ID)
                    .code("28100902676")
                    .displayName("Primcillin")
                    .build())
                .name("Primcillin")
                .strength("250 mg")
                .formCode(CdaCode.builder()
                    .codeSystem(Oid.DK_LMS22)
                    .code("TABFILM")
                    .displayName("filmovertrukne tabletter")
                    .build())
                .atcCode(CdaCode.builder()
                    .codeSystem(Oid.ATC)
                    .code("J01CE02")
                    .displayName("Phenoxymethylpenicillin")
                    .build())
                .innermostPackageLayer(PackageLayer.builder()
                    .amount(BigDecimal.valueOf(20))
                    .unit(new PackageUnit.WithCode("1"))
                    .description("20 tablets")
                    .build())
                .manufacturerOrganizationName("Test Pharma A/S")
                .build())
            .activeIngredients(List.of(
                ActiveIngredient.builder()
                    .name("Phenoxymethylpenicillinkalium")
                    .build()))
            .unstructuredActiveIngredients("Phenoxymethylpenicillinkalium")
            .indicationText("behandling af infektion")
            .patientMedicationInstructions("Tag 2 tabletter om morgen og 2 tabletter om aftenen")
            .build();

        var medication2 = MedicationItem.builder()
            .medicationId(new CdaId(Oid.DK_FMK_MEDICATION, "13143641280999"))
            .medicationStartTime(OffsetDateTime.parse("2025-02-01T00:00:00+01:00"))
            .routeOfAdministration(CdaCode.builder()
                .codeSystem(Oid.DK_LMS11)
                .code("OR")
                .displayName("Oral use")
                .build())
            .dosage(new Dosage.Unstructured(
                "1 tablet dagligt",
                "Test dosage"))
            .product(Product.builder()
                .drugId(CdaCode.builder()
                    .codeSystem(Oid.DK_DRUG_ID)
                    .code("28107001111")
                    .displayName("Panodil")
                    .build())
                .name("Panodil")
                .strength("500 mg")
                .formCode(CdaCode.builder()
                    .codeSystem(Oid.DK_LMS22)
                    .code("TAB")
                    .displayName("tabletter")
                    .build())
                .atcCode(CdaCode.builder()
                    .codeSystem(Oid.ATC)
                    .code("N02BE01")
                    .displayName("Paracetamol")
                    .build())
                .innermostPackageLayer(PackageLayer.builder()
                    .amount(BigDecimal.valueOf(10))
                    .unit(new PackageUnit.WithCode("1"))
                    .description("10 tabletter")
                    .build())
                .manufacturerOrganizationName("Test Pharma A/S")
                .build())
            .activeIngredients(List.of(
                ActiveIngredient.builder()
                    .name("Paracetamol")
                    .build()))
            .unstructuredActiveIngredients("Paracetamol")
            .indicationText("Smertestillende")
            .patientMedicationInstructions("Tag 1 tablet dagligt")
            .build();

        var medicationSummary = MedicationSummary.builder()
            .items(List.of(medication1, medication2))
            .build();

        var model = PatientSummaryL3.builder()
            .documentId(new CdaId(oid, extension))
            .effectiveTime(creationTimestamp)
            .title(title)
            .patient(patient)
            .preferredHp(preferredHp)
            .medicationSummary(medicationSummary)
            .build();
        var cda = PatientSummaryL3Generator.generate(model);

        //Initialize xPath Engine to read the data to validate it
        XPathEngine xpathEngine = new JAXPXPathEngine();
        Map<String, String> namespaces = new HashMap<>();
        namespaces.put("hl7", "urn:hl7-org:v3");
        xpathEngine.setNamespaceContext(namespaces);

        var generatedCda = Input.fromString(cda).build();

        var patientAddressPath = "/hl7:ClinicalDocument/hl7:recordTarget/hl7:patientRole/hl7:addr";
        var preferredHealthProfessionalAddressPath = "/hl7:ClinicalDocument/hl7:participant/hl7:associatedEntity/hl7:addr";

        assertThat(
            "patient address is not null",
            xpathEngine.selectNodes(patientAddressPath, generatedCda),
            is(not(emptyIterable())));
        assertThat(
            "patient country code is not there if it's empty",
            xpathEngine.selectNodes(patientAddressPath + "/hl7:country", generatedCda),
            is(emptyIterable()));
        assertThat(
            "preferred health professional's address is not null",
            xpathEngine.selectNodes(preferredHealthProfessionalAddressPath, generatedCda),
            is(not(emptyIterable())));
        assertThat(
            "preferred health professional's country code is not there if it's empty",
            xpathEngine.selectNodes(preferredHealthProfessionalAddressPath + "/hl7:country", generatedCda),
            is(emptyIterable()));
    }
}
