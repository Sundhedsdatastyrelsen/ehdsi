package dk.sundhedsdatastyrelsen.ncpeh.cda;

import dk.sundhedsdatastyrelsen.ncpeh.cda.mocks.referenceDataLookupServiceMock;
import dk.sundhedsdatastyrelsen.ncpeh.cda.model.EPrescriptionL3;
import dk.sundhedsdatastyrelsen.ncpeh.cda.model.Product;
import dk.sundhedsdatastyrelsen.ncpeh.testing.shared.FmkResponseStorage;
import freemarker.template.TemplateException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.SchemaFactory;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

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
        var epL3 = EPrescriptionL3Mapper.model(prescription, 0, medication, new referenceDataLookupServiceMock());

        //Generate prescription without null in packageCode
        var xmlString = EPrescriptionL3Generator.generate(epL3);
        Assertions.assertNotNull(xmlString);
        Assertions.assertFalse(xmlString.isBlank());

        //Set packageCode to null, and regenerate
        var productWithNullPackageCode = Product.builder()
            .packageCode(null)
            .atcCode(epL3.getProduct().getAtcCode())
            .formCode(epL3.getProduct().getFormCode())
            .description(epL3.getProduct().getDescription())
            .name(epL3.getProduct().getName())
            .size(epL3.getProduct().getSize())
            .build();
        var epL3NullPackageCode = epL3.toBuilder().product(productWithNullPackageCode).build();
        var xmlStringWithNullPackageCode = EPrescriptionL3Generator.generate(epL3NullPackageCode);
        Assertions.assertNotNull(xmlStringWithNullPackageCode);
        Assertions.assertFalse(xmlStringWithNullPackageCode.isBlank());

        Assertions.assertNotEquals(xmlString, xmlStringWithNullPackageCode);
    }

    @ParameterizedTest
    @ValueSource(strings = {"0201909309"})
    public void testCdaValidity(String cpr) throws Exception {
        var prescription = FmkResponseStorage.storedPrescriptions(cpr);
        var medication = FmkResponseStorage.storedDrugMedications(cpr);
        Assertions.assertFalse(prescription.getPrescription().isEmpty());
        var xmlString = EPrescriptionL3Generator.generate(prescription, medication, 0, new referenceDataLookupServiceMock());

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
//        java.nio.file.Path debugFilePath = java.nio.file.Path.of("temp/cda-eprescription-" + cpr + ".xml");
//        java.nio.file.Files.createDirectories(debugFilePath.getParent());
//        java.nio.file.Files.writeString(
//            debugFilePath,
//            xmlString,
//            java.nio.file.StandardOpenOption.CREATE,
//            java.nio.file.StandardOpenOption.TRUNCATE_EXISTING
//        );
    }
}
