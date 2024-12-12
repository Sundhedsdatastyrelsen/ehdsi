package dk.sundhedsdatastyrelsen.ncpeh.cda;

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
import java.util.Optional;

class EPrescriptionL3GeneratorTest {
    @Test
    void generateTest() throws TemplateException, IOException {
        var model = EPrescriptionL3MapperTest.getModel();
        var cda = EPrescriptionL3Generator.generate(model);
//        System.out.println(cda);
        Assertions.assertNotNull(cda);
    }

    @ParameterizedTest
    @ValueSource(strings = {"1111111118", "0201909309"})
    public void testCdaValidity(String cpr) throws Exception {
        var prescription = FmkResponseStorage.storedPrescriptions(cpr);
        var medication = FmkResponseStorage.storedDrugMedications(cpr);
        var xmlString = EPrescriptionL3Generator.generate(prescription, medication, 0); //TODO Needs a DrugMedication response

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

        // write to file for debugging:
//         java.nio.file.Files.writeString(
//             java.nio.file.Path.of("temp/cda-eprescription-" + cpr + ".xml"),
//             xmlString,
//             java.nio.file.StandardOpenOption.CREATE,
//             java.nio.file.StandardOpenOption.TRUNCATE_EXISTING
//         );
    }
}
