package dk.nsp.epps.service.mapping;

import dk.nsp.epps.service.PrescriptionService.PrescriptionFilter;
import dk.nsp.epps.test.FmkResponseStorage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.SchemaFactory;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

public class EPrescriptionMapperTest {
    private static EPrescriptionMapper mapper;

    @BeforeAll
    public static void setup() throws Exception {
        mapper = new EPrescriptionMapper("repoid");
    }

    @Test
    public void testExpectedNumberOfEpsosDocuments() throws Exception {
        var response = FmkResponseStorage.storedPrescriptions("1111111118");
        var result = mapper.mapResponse("1111111118^^^&2.16.17.710.802.1000.990.1.500&ISO", new PrescriptionFilter( null, null, null, null), response);
        Assertions.assertEquals(1, result.size());
    }

    @ParameterizedTest
    @ValueSource(strings = {"1111111118", "0201909309"})
    public void testCdaValidity(String cpr) throws Exception {
        var response = FmkResponseStorage.storedPrescriptions(cpr);
        var result = mapper.mapResponse(
            cpr + "^^^&2.16.17.710.802.1000.990.1.500&ISO",
            new PrescriptionFilter( null, null, null, null),
            response);
        Assertions.assertNotNull(result.getFirst());

        var xmlString = result.getFirst().getDocument();

        // 1. Test if well-formed XML (can be parsed)
        var documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
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

//        // write to file for debugging:
//         java.nio.file.Files.writeString(
//             java.nio.file.Path.of("temp/cda-eprescription1-" + cpr + ".xml"),
//             xmlString,
//             java.nio.file.StandardOpenOption.CREATE,
//             java.nio.file.StandardOpenOption.TRUNCATE_EXISTING
//         );
    }
}
