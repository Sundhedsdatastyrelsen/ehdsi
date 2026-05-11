package dk.sundhedsdatastyrelsen.ncpeh.cda;

import dk.sundhedsdatastyrelsen.ncpeh.cda.model.Address;
import dk.sundhedsdatastyrelsen.ncpeh.cda.model.CdaCode;
import dk.sundhedsdatastyrelsen.ncpeh.cda.model.CdaId;
import dk.sundhedsdatastyrelsen.ncpeh.cda.model.Name;
import dk.sundhedsdatastyrelsen.ncpeh.cda.model.Patient;
import dk.sundhedsdatastyrelsen.ncpeh.cda.model.PreferredHealthProfessional;
import dk.sundhedsdatastyrelsen.ncpeh.cda.model.Telecom;
import dk.sundhedsdatastyrelsen.ncpeh.testing.shared.FmkResponseStorage;
import jakarta.xml.bind.JAXBException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.xmlunit.builder.Input;
import org.xmlunit.xpath.JAXPXPathEngine;
import org.xmlunit.xpath.XPathEngine;

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
        var model = PatientSummaryL3MapperTest.getModel();
        var cda = PatientSummaryL3Generator.generate(model);
        Assertions.assertNotNull(cda);
    }

    @Test
    void testMultipleMedicationSummary() throws JAXBException {
        var oid = Oid.DK_PATIENT_SUMMARY_REPOSITORY_ID;
        var extension = "testtestL3";
        var documentId = "testtest";
        var creationTimestamp = OffsetDateTime.now();
        var title = "epSOS Patient Summary Hans Christian Andersen - testtest";
        var cpr = "0410009234";

        var preferredHP = preferredHp("DK");
        var patient = patient("DK");
        var medicationSummary = FmkResponseStorage.getTestMedicineCards(cpr);

        var input = new PatientSummaryInput(documentId, preferredHP, patient, medicationSummary);
        var psL3 = PatientSummaryL3Mapper.model(input);
        var cda = PatientSummaryL3Generator.generate(psL3);

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
            "there are five medication entries",
            xpathEngine.evaluate("count(/hl7:ClinicalDocument/hl7:component/hl7:structuredBody/hl7:component[1]/hl7:section/hl7:entry)", generatedCda),
            is("5"));

        assertThat(
            "first medication name is correct",
            xpathEngine.evaluate("(/hl7:ClinicalDocument//hl7:section[hl7:code/@code='10160-0']//hl7:manufacturedMaterial/hl7:name)[1]", generatedCda),
            is("Brufen"));

        assertThat(
            "second medication name is correct",
            xpathEngine.evaluate("(/hl7:ClinicalDocument//hl7:section[hl7:code/@code='10160-0']//hl7:manufacturedMaterial/hl7:name)[2]", generatedCda),
            is("Brufen"));

        assertThat(
            "third medication name is correct",
            xpathEngine.evaluate("(/hl7:ClinicalDocument//hl7:section[hl7:code/@code='10160-0']//hl7:manufacturedMaterial/hl7:name)[3]", generatedCda),
            is("Arax"));

        assertThat(
            "fourth medication name is correct",
            xpathEngine.evaluate("(/hl7:ClinicalDocument//hl7:section[hl7:code/@code='10160-0']//hl7:manufacturedMaterial/hl7:name)[4]", generatedCda),
            is("Pinex"));

        assertThat(
            "fifth medication name is correct",
            xpathEngine.evaluate("(/hl7:ClinicalDocument//hl7:section[hl7:code/@code='10160-0']//hl7:manufacturedMaterial/hl7:name)[5]", generatedCda),
            is("Viagra"));
    }

    @Test
    void testEmptyMedicationSummary() throws JAXBException {
        var oid = Oid.DK_PATIENT_SUMMARY_REPOSITORY_ID;
        var extension = "testtestL3";
        var documentId = "testtest";
        var creationTimestamp = OffsetDateTime.now();
        var title = "epSOS Patient Summary Hans Christian Andersen - testtest";
        var cpr = "1004219992";

        var preferredHP = preferredHp("DK");
        var patient = patient("DK");
        var medicationSummary = FmkResponseStorage.getTestMedicineCards(cpr);

        var input = new PatientSummaryInput(documentId, preferredHP, patient, medicationSummary);
        var psL3 = PatientSummaryL3Mapper.model(input);
        var cda = PatientSummaryL3Generator.generate(psL3);

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
            "there are 1 empty medication entry",
            xpathEngine.evaluate("count(/hl7:ClinicalDocument/hl7:component/hl7:structuredBody/hl7:component[1]/hl7:section/hl7:entry)", generatedCda),
            is("1"));

        assertThat(
            "No information about medications entry is present",
            xpathEngine.evaluate("(/hl7:ClinicalDocument//hl7:section[hl7:code/@code='10160-0']//hl7:manufacturedMaterial/hl7:name)[1]", generatedCda),
            is("No information about medications"));

    }

    @Test
    void noPreferredHpTest() throws JAXBException {

        var documentId = "testtest";
        var creationTimestamp = OffsetDateTime.now();
        var cpr = "0410009234";
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

        var medicationSummary = FmkResponseStorage.getTestMedicineCards(cpr);
        var input = new PatientSummaryInput(documentId, null, patient, medicationSummary);
        var psL3 = PatientSummaryL3Mapper.model(input);
        var cda = PatientSummaryL3Generator.generate(psL3);

        Assertions.assertNotNull(cda);
        Assertions.assertFalse(cda.isBlank());
    }

    @Test
    void emptyCountryTest() throws JAXBException {
        var cpr = "0410009234";
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

        var medicationSummary = FmkResponseStorage.getTestMedicineCards(cpr);
        var input = new PatientSummaryInput("testtest", preferredHp, patient, medicationSummary);
        var psL3 = PatientSummaryL3Mapper.model(input);
        var cda = PatientSummaryL3Generator.generate(psL3);

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

    private static Patient patient(String country) {
        return Patient.builder()
            .id(new CdaId(Oid.DK_CPR, "1234567890"))
            .name(Name.fromFullName("Hans Christian Andersen"))
            .address(new Address(List.of("Overgaden Oven Vandet 10", "1."), "København K", "1415", country))
            .genderCode(CdaCode.builder()
                .codeSystem(Oid.ADMINISTRATIVE_GENDER)
                .codeSystemVersion("913-20091020")
                .code("M")
                .displayName("Male")
                .build())
            .birthTime(LocalDate.of(1982, 11, 3))
            .build();
    }

    private static PreferredHealthProfessional preferredHp(String country) {
        return PreferredHealthProfessional.builder()
            .name(Name.fromFullName("Tycho Brahe"))
            .telecoms(List.of(Telecom.builder()
                .use(Telecom.Use.WORK_PLACE)
                .value("tel:+4511111111")
                .build()))
            .address(new Address(
                List.of("Rundetårn", "Købmagergade 52A", "Kælderen"),
                "København K",
                "1150",
                country))
            .build();
    }
}
