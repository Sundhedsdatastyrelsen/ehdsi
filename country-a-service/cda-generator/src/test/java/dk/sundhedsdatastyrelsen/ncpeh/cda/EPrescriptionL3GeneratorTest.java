package dk.sundhedsdatastyrelsen.ncpeh.cda;

import dk.dkma.medicinecard.xml_schema._2015._06._01.e6.PrescriptionType;
import dk.nsi._2024._01._05.stamdataauthorization.AuthorizationType;
import dk.sundhedsdatastyrelsen.ncpeh.testing.shared.FmkResponseStorage;
import freemarker.template.TemplateException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.SchemaFactory;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

class EPrescriptionL3GeneratorTest {
    @Test
    void generateTest() throws TemplateException, IOException {
        var model = EPrescriptionL3MapperTest.getModel();
        var cda = EPrescriptionL3Generator.generate(model);
        Assertions.assertNotNull(cda);
    }

    @Test
    void testNullProductPackageCodeValue() throws Exception {
        var cpr = "0201909309"; //Test person with packagecode correctly filled in
        var prescription = FmkResponseStorage.storedPrescriptions(cpr);
        var medication = FmkResponseStorage.storedDrugMedications(cpr);
        Assertions.assertFalse(prescription.getPrescription().isEmpty());
        var input = new EPrescriptionL3Input(prescription, 0, medication, "FIN", "1", "Manufacturer");
        var epL3 = EPrescriptionL3Mapper.model(input);

        //Generate prescription without null in packageCode
        var xmlString = EPrescriptionL3Generator.generate(epL3);
        Assertions.assertNotNull(xmlString);
        Assertions.assertFalse(xmlString.isBlank());

        //Set packageCode to null, and regenerate
        var productWithNullPackageCode = epL3.getProduct()
            .withPackageInfo(epL3.getProduct().getPackageInfo().withPackageFormCode(null));
        var epL3NullPackageCode = epL3.withProduct(productWithNullPackageCode);
        var xmlStringWithNullPackageCode = EPrescriptionL3Generator.generate(epL3NullPackageCode);
        Assertions.assertNotNull(xmlStringWithNullPackageCode);
        Assertions.assertFalse(xmlStringWithNullPackageCode.isBlank());

        Assertions.assertNotEquals(xmlString, xmlStringWithNullPackageCode);
    }

    static List<String> e2eTestCprs() {
        return FmkResponseStorage.e2eTestCprs();
    }

    @ParameterizedTest
    @MethodSource("e2eTestCprs")
    public void testCdaValidity(String cpr) throws Exception {
        var prescription = FmkResponseStorage.getTestPrescriptions(cpr);
        var medication = FmkResponseStorage.getTestMedications(cpr);
        Assertions.assertFalse(prescription.getPrescription().isEmpty());
        var prescriptions = prescription.getPrescription();
        for (int prescriptionindex = 0; prescriptionindex < prescriptions.size(); prescriptionindex++) {
            var drugInfo = getDrugInfo(prescriptions.get(prescriptionindex));
            var input = new EPrescriptionL3Input(
                prescription,
                prescriptionindex,
                medication,
                List.of(AuthorizationType.builder()
                    .withSpeciale1("Kirurgi")
                    .withSpeciale2("Børnepsykiatri")
                    .withUddannelsesKode("7170")
                    .withAutorisationGyldig("1")
                    .build()),
                drugInfo.packageFormCode(),
                drugInfo.numberOfSubPackages(),
                drugInfo.manufacturerOrganization());
            var xmlString = EPrescriptionL3Generator.generate(input);

            // 1. Test if well-formed XML (can be parsed)
            var documentBuilder = DocumentBuilderFactory.newDefaultNSInstance().newDocumentBuilder();
            Assertions.assertDoesNotThrow(() ->
                documentBuilder.parse(new ByteArrayInputStream(xmlString.getBytes(StandardCharsets.UTF_8)))
            );

            // 2. Test if valid against HL7 CDA schema
            var schemaUrl = this.getClass().getClassLoader().getResource("cda-schema/CDA_Pharma.xsd");
            Assertions.assertNotNull(schemaUrl);
            var schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            var schema = schemaFactory.newSchema(schemaUrl);
            var validator = schema.newValidator();

            validator.validate(new StreamSource(new ByteArrayInputStream(xmlString.getBytes(StandardCharsets.UTF_8))));

            // 3. Test model/schematron via gazelle
            // TODO?

//        //write to file for debugging:
//            java.nio.file.Path debugFilePath = java.nio.file.Path.of("temp/cda-eprescription-" + cpr + "-" + prescriptionindex + ".xml");
//            java.nio.file.Files.createDirectories(debugFilePath.getParent());
//            java.nio.file.Files.writeString(
//                debugFilePath,
//                xmlString,
//                java.nio.file.StandardOpenOption.CREATE,
//                java.nio.file.StandardOpenOption.TRUNCATE_EXISTING
//            );
        }
    }

    record DrugInfo(String packageFormCode, String numberOfSubPackages, String manufacturerOrganization) {
    }

    static DrugInfo getDrugInfo(PrescriptionType prescription) {
        return switch (prescription.getPackageRestriction().getPackageNumber().getValue()) {
            case "448060" -> new DrugInfo("INP", "5", "Eli Lilly");
            default -> new DrugInfo("FIN", "1", "Manufacturer");
        };
    }
}
