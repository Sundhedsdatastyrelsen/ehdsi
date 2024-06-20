package eu.europa.ec.sante.openncp.portal.util;

import eu.europa.ec.sante.openncp.portal.model.DispenseRequest;
import eu.europa.ec.sante.openncp.portal.model.PackageSize;
import eu.europa.ec.sante.openncp.portal.util.cda.util.CDAUtil;
import eu.europa.ec.sante.openncp.common.configuration.ConfigurationManager;
import eu.europa.ec.sante.openncp.common.configuration.ConfigurationManagerFactory;
import eu.europa.ec.sante.openncp.common.property.PropertyEntity;
import org.apache.commons.codec.binary.Base64;
import org.hibernate.cfg.Configuration;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.security.SecureRandom;
import java.util.Random;

public class CDAUtilTest {

    @BeforeClass
    public static void beforeTest() {
        Configuration CONFIGURATION = new Configuration()
                .addAnnotatedClass(PropertyEntity.class)
                .setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect")
                .setProperty("hibernate.connection.url", "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1")
                .setProperty("hibernate.current_session_context_class", "thread")
                .setProperty("hibernate.show_sql", "true")
                .setProperty("hibernate.hbm2ddl.auto", "create");
        ConfigurationManager configurationManager = ConfigurationManagerFactory.getConfigurationManager();

        configurationManager.setProperty("PORTAL_PHARMACIST_OID", "1.2.3");
        configurationManager.setProperty("PORTAL_CUSTODIAN_OID", "1.2.3");
        configurationManager.setProperty("PORTAL_CUSTODIAN_NAME", "Custodian Name");
        configurationManager.setProperty("ncp.country", "BE");
        configurationManager.setProperty("PORTAL_DISPENSATION_OID", "1.2.3");
        configurationManager.setProperty("PORTAL_LEGAL_AUTHENTICATOR_PERSON_OID", "1.2.3");
        configurationManager.setProperty("PORTAL_LEGAL_AUTHENTICATOR_FIRSTNAME", "First name");
        configurationManager.setProperty("PORTAL_LEGAL_AUTHENTICATOR_LASTNAME", "Last name");
        configurationManager.setProperty("PORTAL_LEGAL_AUTHENTICATOR_ORG_OID", "1.2.3");
        configurationManager.setProperty("PORTAL_LEGAL_AUTHENTICATOR_POSTALCODE", "1000");
        configurationManager.setProperty("PORTAL_LEGAL_AUTHENTICATOR_CITY", "Brussels");
        configurationManager.setProperty("LANGUAGE_CODE", "nl-BE");
    }

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
        Assert.assertNotNull(dispensationDocument);
    }

    private String generateIdentifierExtension() {

        Random secureRandom = new SecureRandom();
        var bytes = new byte[16];
        secureRandom.nextBytes(bytes);
        var extension = Base64.encodeBase64String(bytes);
        return extension.substring(0, 16);
    }

}
