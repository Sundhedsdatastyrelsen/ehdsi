package dk.nsp.epps;

import dk.nsp.epps.mock.model.DispenseRequest;
import dk.nsp.epps.mock.model.PackageSize;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;

public class CdaUtilTest {
    @Test
    public void  testGenerateDispensation() throws ParserConfigurationException, IOException, SAXException {
        InputStream is = getClass().getClassLoader().getResourceAsStream("ep.xml");
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true);
        DocumentBuilder builder = dbf.newDocumentBuilder();
        Document epDocument = builder.parse(new InputSource(is));
        DispenseRequest dispenseRequest = new DispenseRequest();
        dispenseRequest.setCountryCode("EU");
        dispenseRequest.setNumberOfPackage(1);
        dispenseRequest.setPrescriptionId("prescriptionId");
        dispenseRequest.setProductName("test product");
        dispenseRequest.setSubstitution(true);
        PackageSize packageSize = new PackageSize();
        packageSize.setPackageSizeL1("1");
        packageSize.setPackageSizeL2("2");
        packageSize.setPackageSizeL3(null);
        packageSize.setQuantity(null);
        dispenseRequest.setPackageSize(packageSize);
        byte[] dispensationDocument = CDAUtil.generateDispensationDocument(dispenseRequest, epDocument, generateIdentifierExtension());
        Assertions.assertNotNull(dispensationDocument);
    }
}
