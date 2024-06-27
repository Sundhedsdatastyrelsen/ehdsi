package eu.europa.ec.sante.ehdsi.portal.util;

import epsos.ccd.netsmart.securitymanager.key.KeyStoreManager;
import epsos.ccd.netsmart.securitymanager.key.impl.DefaultKeyStoreManager;
import epsos.ccd.netsmart.securitymanager.sts.client.NextOfKinAssertionRequest;
import epsos.ccd.netsmart.securitymanager.sts.client.TRCAssertionRequest;
import epsos.openncp.protocolterminator.clientconnector.PatientId;
import eu.europa.ec.sante.ehdsi.openncp.configmanager.ConfigurationManagerFactory;
import eu.europa.ec.sante.ehdsi.portal.model.Concept;
import eu.europa.ec.sante.ehdsi.portal.model.NextOfKinTrait;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.lang3.StringUtils;
import org.opensaml.core.config.InitializationException;
import org.opensaml.core.config.InitializationService;
import org.opensaml.core.xml.XMLObjectBuilder;
import org.opensaml.core.xml.XMLObjectBuilderFactory;
import org.opensaml.core.xml.config.XMLObjectProviderRegistrySupport;
import org.opensaml.core.xml.schema.XSAny;
import org.opensaml.core.xml.schema.XSString;
import org.opensaml.core.xml.schema.XSURI;
import org.opensaml.saml.common.SAMLObjectBuilder;
import org.opensaml.saml.common.SAMLVersion;
import org.opensaml.saml.common.SignableSAMLObject;
import org.opensaml.saml.saml2.core.*;
import org.opensaml.saml.saml2.core.impl.AssertionMarshaller;
import org.opensaml.saml.saml2.core.impl.IssuerBuilder;
import org.opensaml.security.credential.CredentialSupport;
import org.opensaml.security.x509.BasicX509Credential;
import org.opensaml.xmlsec.signature.KeyInfo;
import org.opensaml.xmlsec.signature.Signature;
import org.opensaml.xmlsec.signature.X509Data;
import org.opensaml.xmlsec.signature.support.SignatureException;
import org.opensaml.xmlsec.signature.support.Signer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import tr.com.srdc.epsos.util.Constants;

import javax.xml.XMLConstants;
import javax.xml.namespace.QName;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

public class AssertionUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(AssertionUtil.class);

    static {
        try {
            InitializationService.initialize();
        } catch (InitializationException e) {
            LOGGER.error("InitializationException: '{}'", e.getMessage());
        }
    }

    private AssertionUtil() {
        //  Empty private constructor preventing instantiation.
    }

    public static Assertion createHCPAssertion(String username, String fullName, String email, Concept role,
                                               String organization, String organizationId, String facilityType, String purposeOfUse,
                                               String locality, List<String> permissions, String onBehalfId) {

        Assertion assertion = null;
        try {

            XMLObjectBuilderFactory builderFactory = XMLObjectProviderRegistrySupport.getBuilderFactory();

            // Create the NameIdentifier
            SAMLObjectBuilder nameIdBuilder = (SAMLObjectBuilder) builderFactory.getBuilder(NameID.DEFAULT_ELEMENT_NAME);
            var nameId = (NameID) nameIdBuilder.buildObject();
            nameId.setValue(email);
            nameId.setFormat(NameID.EMAIL);

            assertion = create(Assertion.class, Assertion.DEFAULT_ELEMENT_NAME);
            var issuedInstant = Instant.now();
            String assId = "_" + UUID.randomUUID();
            assertion.setID(assId);
            assertion.setVersion(SAMLVersion.VERSION_20);
            assertion.setIssueInstant(issuedInstant);

            Subject subject = create(Subject.class, Subject.DEFAULT_ELEMENT_NAME);
            assertion.setSubject(subject);
            subject.setNameID(nameId);

            // Create and add Subject Confirmation
            SubjectConfirmation subjectConf = create(SubjectConfirmation.class, SubjectConfirmation.DEFAULT_ELEMENT_NAME);
            subjectConf.setMethod(SubjectConfirmation.METHOD_SENDER_VOUCHES);
            assertion.getSubject().getSubjectConfirmations().add(subjectConf);

            // Create and add conditions
            Conditions conditions = create(Conditions.class, Conditions.DEFAULT_ELEMENT_NAME);

            conditions.setNotBefore(issuedInstant);

            AudienceRestriction audienceRestriction = create(AudienceRestriction.class, AudienceRestriction.DEFAULT_ELEMENT_NAME);
            Audience audience = create(Audience.class, Audience.DEFAULT_ELEMENT_NAME);
            audience.setURI("urn:ehdsi:assertions.audience:x-border");
            audienceRestriction.getAudiences().add(audience);
            conditions.getAudienceRestrictions().add(audienceRestriction);

            // According to Spec
            conditions.setNotOnOrAfter(issuedInstant.plus(Duration.ofHours(4)));
            assertion.setConditions(conditions);

            var issuer = new IssuerBuilder().buildObject();
            String countryCode = ConfigurationManagerFactory.getConfigurationManager().getProperty("COUNTRY_CODE");
            issuer.setValue("urn:idp:" + countryCode + ":countryB");
            issuer.setNameQualifier("urn:ehdsi:assertions:hcp");
            assertion.setIssuer(issuer);

            // Add and create the authentication statement
            AuthnStatement authStmt = create(AuthnStatement.class, AuthnStatement.DEFAULT_ELEMENT_NAME);
            authStmt.setAuthnInstant(issuedInstant);
            assertion.getAuthnStatements().add(authStmt);

            // Create and add AuthnContext
            AuthnContext authnContext = create(AuthnContext.class, AuthnContext.DEFAULT_ELEMENT_NAME);
            AuthnContextClassRef authnContextClassRef = create(AuthnContextClassRef.class, AuthnContextClassRef.DEFAULT_ELEMENT_NAME);
            //  Based on National Requirements and implementation this value might need to be updated.
            authnContextClassRef.setURI(AuthnContext.SMARTCARD_PKI_AUTHN_CTX);
            authnContext.setAuthnContextClassRef(authnContextClassRef);
            authStmt.setAuthnContext(authnContext);

            AttributeStatement attrStmt = create(AttributeStatement.class, AttributeStatement.DEFAULT_ELEMENT_NAME);

            // Set HC Identifier
            var attrHCID = createAttribute(builderFactory, "HCI Identifier",
                    "urn:ihe:iti:xca:2010:homeCommunityId", "urn:oid:" + Constants.HOME_COMM_ID, "", "");
            attrStmt.getAttributes().add(attrHCID);

            // Set NP Identifier
            var attrNPID = createAttribute(builderFactory, "NPI Identifier",
                    "urn:oasis:names:tc:xspa:1.0:subject:npi", Constants.COUNTRY_NAME, "", "");
            attrStmt.getAttributes().add(attrNPID);

            // XSPA Subject
            var attrPID = createAttribute(builderFactory, "XSPA Subject",
                    "urn:oasis:names:tc:xspa:1.0:subject:subject-id", fullName, "", "");
            attrStmt.getAttributes().add(attrPID);

            // XSPA Role
            var structuralRole = createAttributeXSPARole(builderFactory, "XSPA Role",
                    "urn:oasis:names:tc:xacml:2.0:subject:role", role, "", "");
            attrStmt.getAttributes().add(structuralRole);

//            var attrFunctionalRole = createAttribute(builderFactory, "XSPA Functional Role",
//                    "urn:oasis:names:tc:xspa:1.0:subject:functional-role", functionalRole, "", "");
//            attrStmt.getAttributes().add(attrFunctionalRole);

            // XSPA Organization - Optional Field (eHDSI SAML Profile 2.2.0)
            if (StringUtils.isNotBlank(organization)) {
                var attrPID3 = createAttribute(builderFactory, "XSPA Organization",
                        "urn:oasis:names:tc:xspa:1.0:subject:organization",
                        organization, "", "");
                attrStmt.getAttributes().add(attrPID3);
            }

            // XSPA Organization ID - Optional Field (eHDSI SAML Profile 2.2.0)
            if (StringUtils.isNotBlank(organizationId)) {
                var attrPID4 = createAttribute(builderFactory, "XSPA Organization ID",
                        "urn:oasis:names:tc:xspa:1.0:subject:organization-id", organizationId, "", "");
                attrStmt.getAttributes().add(attrPID4);
            }
            // // On behalf of
            if (StringUtils.isNotBlank(onBehalfId)) {
                var attrPID41 = createAttribute(builderFactory, "OnBehalfOf",
                        "urn:epsos:names:wp3.4:subject:on-behalf-of", onBehalfId, role.getDisplayName(), "");
                attrStmt.getAttributes().add(attrPID41);
                attrStmt.getAttributes().add(attrPID41);
            }

            // eHealth DSI Healthcare Facility Type
            //var attrPID5 = createAttribute(builderFactory, "eHealth DSI Healthcare Facility Type",
            //      "urn:epsos:names:wp3.4:subject:healthcare-facility-type", facilityType, "", "");
            var attrPID5 = createAttribute(builderFactory, "eHealth DSI Healthcare Facility Type",
                    "urn:ehdsi:names:subject:healthcare-facility-type", facilityType, "", "");
            attrStmt.getAttributes().add(attrPID5);

            // XSPA Purpose of Use
            var attrPID6 = createAttributePurposeOfUse(builderFactory, "XSPA Purpose Of Use",
                    "urn:oasis:names:tc:xspa:1.0:subject:purposeofuse", purposeOfUse, "", "");
            attrStmt.getAttributes().add(attrPID6);

            // XSPA Locality
            var attrPID7 = createAttribute(builderFactory, "XSPA Locality",
                    "urn:oasis:names:tc:xspa:1.0:environment:locality", locality, "", "");
            attrStmt.getAttributes().add(attrPID7);

            // HL7 Permissions
            var attrPID8 = createAttribute("Hl7 Permissions",
                    "urn:oasis:names:tc:xspa:1.0:subject:hl7:permission");
            for (Object permission : permissions) {
                attrPID8 = AddAttributeValue(builderFactory, attrPID8, permission.toString(), "", "");
            }
            attrStmt.getAttributes().add(attrPID8);

            assertion.getStatements().add(attrStmt);
            signSAMLAssertion(assertion, Constants.NCP_SIG_PRIVATEKEY_ALIAS);
            LOGGER.info("AssertionId: '{}'", assertion.getID());

            var marshaller = new AssertionMarshaller();
            Element element = marshaller.marshall(assertion);
            Document document = element.getOwnerDocument();
            String hcpa = getDocumentAsXml(document, false);
            LOGGER.info("#### HCPA Start\n '{}' \n#### HCPA End", hcpa);

        } catch (Exception e) {
            LOGGER.error(ExceptionUtils.getStackTrace(e));
        }
        return assertion;
    }

    public static Assertion createNextOfKin(Assertion assertionHCP, NextOfKinTrait nextOfKinTrait) {

        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("Requesting NextOfKin assertion for the following person:\n'{}'", nextOfKinTrait.toString());
        }
        try {
            var nextOfKinBuilder = new NextOfKinAssertionRequest.Builder(assertionHCP);
            if (CollectionUtils.isNotEmpty(nextOfKinTrait.getLivingSubjectIds())) {
                nextOfKinBuilder.nextOfKinId(nextOfKinTrait.getLivingSubjectIds().get(0).getExtension());
            }
            if (nextOfKinTrait.getBirthDate() != null) {
                nextOfKinBuilder.nextOfKinBirthdate(nextOfKinTrait.getBirthDate());
            }
            if (StringUtils.isNotBlank(nextOfKinTrait.getGender())) {
                nextOfKinBuilder.nextOfKinAdministrativeGender(nextOfKinTrait.getGender());
            }
            if (StringUtils.isNotBlank(nextOfKinTrait.getAddressCity())) {
                nextOfKinBuilder.nextOfKinAddressCity(nextOfKinTrait.getAddressCity());
            }
            if (StringUtils.isNotBlank(nextOfKinTrait.getAddressCountry())) {
                nextOfKinBuilder.nextOfKinAddressCountry(nextOfKinTrait.getAddressCountry());
            }
            if (StringUtils.isNotBlank(nextOfKinTrait.getAddressPostalCode())) {
                nextOfKinBuilder.nextOfKinAddressPostalCode(nextOfKinTrait.getAddressPostalCode());
            }
            if (StringUtils.isNotBlank(nextOfKinTrait.getAddressStreet())) {
                nextOfKinBuilder.nextOfKinAddressStreet(nextOfKinTrait.getAddressStreet());
            }
            if (StringUtils.isNotBlank(nextOfKinTrait.getFirstName())) {
                nextOfKinBuilder.nextOfKinFirstName(nextOfKinTrait.getFirstName());
            }
            if (StringUtils.isNotBlank(nextOfKinTrait.getFamilyName())) {
                nextOfKinBuilder.nextOfKinFamilyName(nextOfKinTrait.getFamilyName());
            }
            var assertionNOK = nextOfKinBuilder.build().request();
            var marshaller = new AssertionMarshaller();
            Element element = marshaller.marshall(assertionNOK);
            Document document = element.getOwnerDocument();

            LOGGER.info("NOK Assertion:\n'{}'", getDocumentAsXml(document, false));
            return assertionNOK;
        } catch (Exception e) {
            LOGGER.error(ExceptionUtils.getStackTrace(e));
            return null;
        }
    }

    public static Assertion createPatientConfirmationPlain(Assertion assertionHCP, PatientId patient, String purposeOfUse,
                                                           String prescriptionId, String dispensationPinCode) throws Exception {

        LOGGER.info("HCP Assertion ID: '{}'", assertionHCP.getID());
        String patientId = patient.getExtension() + "^^^&" + patient.getRoot() + "&ISO";

        LOGGER.info("Creates TRC Assertion with parameters -> Patient ID: '{}' - Prescription Id: '{}' - DispensationPinCode: '{}'",
                patientId, prescriptionId, dispensationPinCode);

        LOGGER.info("TRC-STS URL: '{}'", ConfigurationManagerFactory.getConfigurationManager().getProperty("secman.sts.url"));


//        SecurityTokenServiceClientImpl securityTokenServiceClient = new SecurityTokenServiceClientImpl();
//        Assertion a = securityTokenServiceClient.issueTreatmentConfirmationToken();

        var builder = new TRCAssertionRequest.Builder(assertionHCP, patientId).purposeOfUse(purposeOfUse);
        if (StringUtils.isNotBlank(prescriptionId)) {
            builder.prescriptionId(prescriptionId);
        }
        if (StringUtils.isNotBlank(dispensationPinCode)) {
            builder.dispensationPinCode(dispensationPinCode);
        }
        var assertionTRC = builder.build().request();
        var marshaller = new AssertionMarshaller();
        Element element = marshaller.marshall(assertionTRC);
        Document document = element.getOwnerDocument();
        LOGGER.info("TRC Assertion: '{}'\n'{}'", assertionTRC.getID(), getDocumentAsXml(document, false));
        return assertionTRC;
    }

    private static void signSAMLAssertion(SignableSAMLObject signableSAMLObject, String keyAlias) throws Exception {

        LOGGER.info("method signSAMLAssertion('{}')", keyAlias);

        String signatureKeystorePath = Constants.NCP_SIG_KEYSTORE_PATH;
        String signatureKeystorePassword = Constants.NCP_SIG_KEYSTORE_PASSWORD;
        String signatureKeyAlias = Constants.NCP_SIG_PRIVATEKEY_ALIAS;
        String signatureKeyPassword = Constants.NCP_SIG_PRIVATEKEY_PASSWORD;

        KeyStoreManager keyManager = new DefaultKeyStoreManager();
        X509Certificate signatureCertificate;
        PrivateKey privateKey = null;

        if (keyAlias == null) {
            signatureCertificate = (X509Certificate) keyManager.getDefaultCertificate();
        } else {
            var keyStore = KeyStore.getInstance("JKS");
            var file = new File(signatureKeystorePath);
            keyStore.load(new FileInputStream(file), signatureKeystorePassword.toCharArray());
            privateKey = (PrivateKey) keyStore.getKey(signatureKeyAlias, signatureKeyPassword.toCharArray());
            signatureCertificate = (X509Certificate) keyManager.getCertificate(keyAlias);
        }

        LOGGER.info("Keystore & Signature Certificate loaded: '{}'", signatureCertificate.getSerialNumber());

        Signature sig = (Signature) XMLObjectProviderRegistrySupport.getBuilderFactory()
                .getBuilder(Signature.DEFAULT_ELEMENT_NAME).buildObject(Signature.DEFAULT_ELEMENT_NAME);
        BasicX509Credential signingCredential = CredentialSupport.getSimpleCredential(signatureCertificate, privateKey);

        sig.setSigningCredential(signingCredential);
        sig.setSignatureAlgorithm("http://www.w3.org/2001/04/xmldsig-more#rsa-sha256");
        sig.setCanonicalizationAlgorithm("http://www.w3.org/2001/10/xml-exc-c14n#");


        var keyInfo = (KeyInfo) XMLObjectProviderRegistrySupport.getBuilderFactory().getBuilder(KeyInfo.DEFAULT_ELEMENT_NAME).buildObject(KeyInfo.DEFAULT_ELEMENT_NAME);
        X509Data data = (X509Data) XMLObjectProviderRegistrySupport.getBuilderFactory().getBuilder(X509Data.DEFAULT_ELEMENT_NAME).buildObject(X509Data.DEFAULT_ELEMENT_NAME);
        var x509Certificate = (org.opensaml.xmlsec.signature.X509Certificate) XMLObjectProviderRegistrySupport.getBuilderFactory()
                .getBuilder(org.opensaml.xmlsec.signature.X509Certificate.DEFAULT_ELEMENT_NAME).buildObject(org.opensaml.xmlsec.signature.X509Certificate.DEFAULT_ELEMENT_NAME);

        var value = Base64.encodeBase64String(signingCredential.getEntityCertificate().getEncoded());
        x509Certificate.setValue(value);
        data.getX509Certificates().add(x509Certificate);
        keyInfo.getX509Datas().add(data);
        sig.setKeyInfo(keyInfo);

        signableSAMLObject.setSignature(sig);
        XMLObjectProviderRegistrySupport.getMarshallerFactory().getMarshaller(signableSAMLObject).marshall(signableSAMLObject);

        try {
            Signer.signObject(sig);
        } catch (SignatureException e) {
            throw new Exception(e);
        }
    }

    private static <T> T create(Class<T> cls, QName qname) {

        return (T) (XMLObjectProviderRegistrySupport.getBuilderFactory().getBuilder(qname)).buildObject(qname);
    }

    private static Attribute createAttribute(String friendlyName, String oasisName) {

        Attribute attrPID = create(Attribute.class, Attribute.DEFAULT_ELEMENT_NAME);
        attrPID.setFriendlyName(friendlyName);
        attrPID.setName(oasisName);
        attrPID.setNameFormat(Attribute.URI_REFERENCE);
        return attrPID;
    }

    private static Attribute AddAttributeValue(XMLObjectBuilderFactory builderFactory, Attribute attribute, String value,
                                               String namespace, String xmlschema) {

        XMLObjectBuilder stringBuilder = builderFactory.getBuilder(XSString.TYPE_NAME);
        XSString attrValPID = (XSString) stringBuilder.buildObject(AttributeValue.DEFAULT_ELEMENT_NAME, XSString.TYPE_NAME);
        attrValPID.setValue(value);
        attribute.getAttributeValues().add(attrValPID);
        return attribute;
    }

    private static Attribute createAttribute(XMLObjectBuilderFactory builderFactory, String FriendlyName, String oasisName,
                                             String value, String namespace, String xmlschema) {

        Attribute attrPID = create(Attribute.class, Attribute.DEFAULT_ELEMENT_NAME);
        attrPID.setFriendlyName(FriendlyName);
        attrPID.setName(oasisName);
        attrPID.setNameFormat(Attribute.URI_REFERENCE);
        // Create and add the Attribute Value

        XMLObjectBuilder stringBuilder;

        if (StringUtils.isBlank(namespace)) {
            XSString attrValPID;
            stringBuilder = builderFactory.getBuilder(XSString.TYPE_NAME);
            attrValPID = (XSString) stringBuilder.buildObject(AttributeValue.DEFAULT_ELEMENT_NAME, XSString.TYPE_NAME);
            attrValPID.setValue(value);
            attrPID.getAttributeValues().add(attrValPID);
        } else {
            XSURI attrValPID;
            stringBuilder = builderFactory.getBuilder(XSURI.TYPE_NAME);
            attrValPID = (XSURI) stringBuilder.buildObject(AttributeValue.DEFAULT_ELEMENT_NAME, XSURI.TYPE_NAME);
            attrValPID.setURI(value);
            attrPID.getAttributeValues().add(attrValPID);
        }

        return attrPID;
    }

    private static Attribute createAttributeXSPARole(XMLObjectBuilderFactory builderFactory, String FriendlyName, String oasisName,
                                                     Concept conceptRole, String namespace, String xmlschema) {

        Attribute attrPID = create(Attribute.class, Attribute.DEFAULT_ELEMENT_NAME);
        attrPID.setFriendlyName(FriendlyName);
        attrPID.setName(oasisName);
        attrPID.setNameFormat(Attribute.URI_REFERENCE);
        // Create and add the Attribute Value

        XMLObjectBuilder<XSAny> xsAnyBuilder = (XMLObjectBuilder<XSAny>) builderFactory.getBuilder(XSAny.TYPE_NAME);
        XSAny role = xsAnyBuilder.buildObject("urn:hl7-org:v3", "Role", "");
        role.getUnknownAttributes().put(new QName("code"), conceptRole.getCode());
        role.getUnknownAttributes().put(new QName("codeSystem"), conceptRole.getCodeSystem());
        role.getUnknownAttributes().put(new QName("codeSystemName"), conceptRole.getCodeSystemName());
        role.getUnknownAttributes().put(new QName("displayName"), conceptRole.getDisplayName());
        //role.setTextContent(value);
        XSAny roleAttributeValue = xsAnyBuilder.buildObject(AttributeValue.DEFAULT_ELEMENT_NAME);
        roleAttributeValue.getUnknownXMLObjects().add(role);
        attrPID.getAttributeValues().add(roleAttributeValue);
        return attrPID;
    }

    private static Attribute createAttributePurposeOfUse(XMLObjectBuilderFactory builderFactory, String FriendlyName, String oasisName,
                                                         String value, String namespace, String xmlschema) {

        Attribute attrPID = create(Attribute.class, Attribute.DEFAULT_ELEMENT_NAME);
        attrPID.setFriendlyName(FriendlyName);
        attrPID.setName(oasisName);
        attrPID.setNameFormat(Attribute.URI_REFERENCE);
        // Create and add the Attribute Value

        XMLObjectBuilder<XSAny> xsAnyBuilder = (XMLObjectBuilder<XSAny>) builderFactory.getBuilder(XSAny.TYPE_NAME);
        XSAny pou = xsAnyBuilder.buildObject("urn:hl7-org:v3", "PurposeOfUse", "");
        pou.getUnknownAttributes().put(new QName("code"), value);
        pou.getUnknownAttributes().put(new QName("codeSystem"), "3bc18518-d305-46c2-a8d6-94bd59856e9e");
        pou.getUnknownAttributes().put(new QName("codeSystemName"), "eHDSI XSPA PurposeOfUse");
        pou.getUnknownAttributes().put(new QName("displayName"), value);
        //pou.setTextContent(value);
        XSAny pouAttributeValue = xsAnyBuilder.buildObject(AttributeValue.DEFAULT_ELEMENT_NAME);
        pouAttributeValue.getUnknownXMLObjects().add(pou);
        attrPID.getAttributeValues().add(pouAttributeValue);
        return attrPID;
    }

    public static String getDocumentAsXml(Document document, boolean header) {

        var response = "";
        try {
            DOMSource domSource = new DOMSource(document);
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            transformerFactory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            Transformer transformer = transformerFactory.newTransformer();
            String omit;
            if (header) {
                omit = "no";
            } else {
                omit = "yes";
            }
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, omit);
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            var stringWriter = new java.io.StringWriter();
            StreamResult sr = new StreamResult(stringWriter);
            transformer.transform(domSource, sr);
            response = stringWriter.toString();
        } catch (Exception e) {
            LOGGER.error(ExceptionUtils.getStackTrace(e));
        }
        return response;
    }
}
