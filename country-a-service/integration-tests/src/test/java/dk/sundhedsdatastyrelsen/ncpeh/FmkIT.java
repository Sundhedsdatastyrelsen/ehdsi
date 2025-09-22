package dk.sundhedsdatastyrelsen.ncpeh;

import dk.dkma.medicinecard.xml_schema._2015._06._01.GetPrescriptionRequestType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e6.PrescriptionType;
import dk.sundhedsdatastyrelsen.ncpeh.cda.Oid;
import dk.sundhedsdatastyrelsen.ncpeh.client.AuthorizationRegistryClient;
import dk.sundhedsdatastyrelsen.ncpeh.locallms.DataProvider;
import dk.sundhedsdatastyrelsen.ncpeh.locallms.FtpConnection;
import dk.sundhedsdatastyrelsen.ncpeh.locallms.LocalLmsLoader;
import dk.sundhedsdatastyrelsen.ncpeh.mocks.AuthorizationRegistryClientMock;
import dk.sundhedsdatastyrelsen.ncpeh.script.FmkPrescriptionCreator;
import dk.sundhedsdatastyrelsen.ncpeh.service.PrescriptionService;
import dk.sundhedsdatastyrelsen.ncpeh.service.SigningCertificate;
import dk.sundhedsdatastyrelsen.ncpeh.service.undo.UndoDispensationRepository;
import dk.sundhedsdatastyrelsen.ncpeh.testing.shared.Fmk;
import dk.sundhedsdatastyrelsen.ncpeh.testing.shared.Sosi;
import dk.sundhedsdatastyrelsen.ncpeh.testing.shared.TestIdentities;
import org.apache.commons.lang3.tuple.Pair;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import javax.sql.DataSource;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;
import java.util.Objects;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.io.FileMatchers.aReadableFile;

class FmkIT {
    private final DataSource lmsDataSource = lmsDataSource();
    private final PrescriptionService prescriptionService = prescriptionService(lmsDataSource);

    private static DataSource lmsDataSource() {
        return new SingleConnectionDataSource("jdbc:sqlite:./local-lms-db-it.sqlite", true);
    }

    private static PrescriptionService prescriptionService(DataSource lmsDataSource) {
        return new PrescriptionService(
            Fmk.FMK_IDWS_ENDPOINT_URI,
            new SigningCertificate(Fmk.getSigningKey()),
            undoDispensationRepository(),
            lmsDataSource,
            authorizationRegistryClient(),
            TestIdentities.systemIdentity);
    }

    @BeforeAll
    static void initialiseLmsData() throws SQLException, IOException {
        var ds = lmsDataSource();
        if (new DataProvider(ds).lastImport().isEmpty()) {
            LocalLmsLoader.fetchData(lmsServerInfo(), ds);
        }
    }

    private static FtpConnection.ServerInfo lmsServerInfo() {
        var user = Objects.requireNonNull(System.getenv("LMSFTP_USERNAME"), "envvar LMSFTP_USERNAME is not set");
        var password = Objects.requireNonNull(System.getenv("LMSFTP_PASSWORD"), "envvar LMSFTP_PASSWORD is not set");
        return new FtpConnection.ServerInfo("ftp.medicinpriser.dk", 21, user, password);
    }

    /**
     * This test simply checks that we can connect and get an answer on the data.
     *
     * @throws Exception
     */
    @Test
    void getPrescriptionAndMedicationTest() throws Exception {
        var getPrescriptionRequest = GetPrescriptionRequestType.builder()
            .withPersonIdentifier()
            .withSource("CPR")
            .withValue(Fmk.cprHelleReadOnly)
            .end()
            .withIncludeOpenPrescriptions()
            .end()
            .build();

        var token = Sosi.getToken();

        var prescriptions = Fmk.idwsApiClient()
            .getPrescription(getPrescriptionRequest, token);

        var validPrescriptions = PrescriptionService.PrescriptionFilter.none()
            .validPrescriptionIndexes(prescriptions.getPrescription())
            .map(Pair::getRight);

        var drugMedicationIds = validPrescriptions
            .map(PrescriptionType::getAttachedToDrugMedicationIdentifier)
            .toList();

        var drugMedications = prescriptionService.getDrugMedicationResponse(Fmk.cprHelleReadOnly, drugMedicationIds, token);
        assertThat(prescriptions.getPatient().getPerson().getName().getGivenName(), is("Helle"));
        assertThat(drugMedications.getPersonIdentifier().getValue(), is(Fmk.cprHelleReadOnly));
    }

    @Test
    void getValidDrugMedications() throws Exception {
        var getPrescriptionRequest = GetPrescriptionRequestType.builder()
            .withPersonIdentifier()
            .withSource("CPR")
            .withValue(Fmk.cprKarl)
            .end()
            .withIncludeOpenPrescriptions()
            .end()
            .build();

        var token = Sosi.getToken();

        var prescriptions = Fmk.idwsApiClient()
            .getPrescription(getPrescriptionRequest, token);

        var validPrescriptions = PrescriptionService.PrescriptionFilter.none()
            .validPrescriptionIndexes(prescriptions.getPrescription())
            .map(Pair::getRight)
            .toList();

        var drugMedicationIds = validPrescriptions.stream()
            .map(PrescriptionType::getAttachedToDrugMedicationIdentifier)
            .toList();

        var drugMedications = prescriptionService.getDrugMedicationResponse(Fmk.cprKarl, drugMedicationIds, token);
        assertThat(validPrescriptions.size(), is(drugMedications.getDrugMedication().size()));
    }

    private static String patientId(String cpr) {
        return String.format("%s^^^&%s&ISO", cpr, Oid.DK_CPR.value);
    }

    private static UndoDispensationRepository undoDispensationRepository() {
        var dataSource = new SingleConnectionDataSource("jdbc:sqlite::memory:", true);
        // perform db migrations
        Flyway.configure().dataSource(dataSource).load().migrate();
        return new UndoDispensationRepository(dataSource);
    }

    private static AuthorizationRegistryClient authorizationRegistryClient() {
        return new AuthorizationRegistryClientMock();
    }

    /**
     * The dispensation test case is complex because:
     * - There has to be a valid prescription in the test environment to dispense for.
     * - We need a valid eDispensation CDA input document which references the prescription.
     * So this test can only run if the prerequisite scripts have run, and an eDispensation CDA
     * is available at the path given by the system property eDispensationITPath.
     */
    @Test
    void submitDispensationTest() throws Exception {
        var cpr = Fmk.cprKarl;
        var eDispensationRawPath = System.getProperty("eDispensationITPath");
        assertThat(
            "Java system property eDispensationITPath must be set",
            eDispensationRawPath,
            is(not(anyOf(nullValue(), blankString()))));
        var eDispensationPath = Path.of(eDispensationRawPath);
        assertThat(
            "Cannot find eDispensation CDA at " + eDispensationPath,
            eDispensationPath.toFile(),
            is(aReadableFile()));
        var eDispensation = Utils.readXmlDocument(Files.newInputStream(eDispensationPath));
        var token = Sosi.getToken();

        // shouldn't throw:
        prescriptionService.submitDispensation(
            patientId(cpr),
            eDispensation,
            token);

        // shouldn't throw:
        prescriptionService.undoDispensation(patientId(cpr), eDispensation, token);

        // we perform the dispensation again to clean up after ourselves:
        prescriptionService.submitDispensation(
            patientId(cpr),
            eDispensation,
            token);
    }

    @Test
    void simpleDispensationTest() throws Exception {
        var cpr = Fmk.cprKarl;
        var prescription = FmkPrescriptionCreator.createNewPrecriptionForCpr(cpr);
        var prescriptionId = Long.toString(prescription.getPrescription()
            .getFirst()
            .getPrescriptionIdentifier()
            .getFirst());
        var eDispensation = Utils.readXmlDocument(
            DISPENSATION_CDA.replaceAll(DISPENSATION_CDA_CPR, cpr)
                .replaceAll(DISPENSATION_CDA_PRESCRIPTION_ID, prescriptionId));

        var token = Sosi.getToken();

        // shouldn't throw:
        prescriptionService.submitDispensation(
            patientId(cpr),
            eDispensation,
            token);

        // shouldn't throw:
        prescriptionService.undoDispensation(patientId(cpr), eDispensation, token);

        // we perform the dispensation again to clean up after ourselves:
        prescriptionService.submitDispensation(patientId(cpr), eDispensation, token);
    }

    // These are the CPR number and prescription id used in the dispensation CDA below.
    private static final String DISPENSATION_CDA_CPR = "1903098089";
    private static final String DISPENSATION_CDA_PRESCRIPTION_ID = "495186474925101";

    /// This is a dispensation as generated for one of our test patients by the test country B portal published by
    /// EU. The prescription is for 100 paracetamol 500 mg tablets.
    private static final String DISPENSATION_CDA = """
        <hl7:ClinicalDocument xmlns:hl7="urn:hl7-org:v3">
            <hl7:realmCode code="DK" />
            <hl7:typeId extension="POCD_HD000040" root="2.16.840.1.113883.1.3" />
            <hl7:templateId root="1.3.6.1.4.1.12559.11.10.1.3.1.1.2" />
            <hl7:id extension="Bb3qpk9uftdXyQMD" root="1.3.6.1.4.1.48336" />
            <hl7:code code="60593-1" codeSystem="2.16.840.1.113883.6.1" codeSystemName="LOINC"
                codeSystemVersion="2.59" displayName="Medication dispensed.extended Document" />
            <hl7:title>Medication dispensed</hl7:title>
            <hl7:effectiveTime value="20250910064843+0000" />
            <hl7:confidentialityCode code="N" codeSystem="2.16.840.1.113883.5.25"
                codeSystemName="Confidentiality" codeSystemVersion="913-20091020" displayName="normal" />
            <hl7:languageCode code="da-DK" />
            <hl7:recordTarget contextControlCode="OP" typeCode="RCT">
                <patientRole xmlns="urn:hl7-org:v3" classCode="PAT">
                    <id extension="1903098089" root="1.2.208.176.1.2" />
                    <addr>
                        <streetAddressLine>Parallelvej 270</streetAddressLine>
                        <city>København K</city>
                        <postalCode>1416</postalCode>
                    </addr>
                    <telecom nullFlavor="NI" />
                    <patient classCode="PSN" determinerCode="INSTANCE">
                        <name>
                            <given>Mads</given>
                            <given>E2E_2608</given>
                            <family>Bonde</family>
                        </name>
                        <administrativeGenderCode code="M" codeSystem="2.16.840.1.113883.5.1"
                            codeSystemName="AdministrativeGender" codeSystemVersion="913-20091020"
                            displayName="Male" />
                        <birthTime value="20090319" />
                    </patient>
                </patientRole>
            </hl7:recordTarget>
            <hl7:author contextControlCode="OP" typeCode="AUT">
                <hl7:functionCode code="2262" codeSystem="2.16.840.1.113883.2.9.6.2.7" codeSystemName="ISCO"
                    codeSystemVersion="2008" displayName="Pharmacists" />
                <hl7:time value="20250910064843+0000" />
                <hl7:assignedAuthor classCode="ASSIGNED">
                    <hl7:id extension="extension" root="1.3.6.1.4.1.48336" />
                    <hl7:addr>
                        <hl7:streetAddressLine>streetAddressLine</hl7:streetAddressLine>
                        <hl7:postalCode>postalCode</hl7:postalCode>
                        <hl7:city>city</hl7:city>
                        <hl7:country>country</hl7:country>
                    </hl7:addr>
                    <hl7:telecom use="WP" value="tel:123456789" />
                    <hl7:assignedPerson>
                        <hl7:name>
                            <hl7:family>familyName</hl7:family>
                            <hl7:given>givenName</hl7:given>
                            <hl7:suffix>suffix</hl7:suffix>
                        </hl7:name>
                    </hl7:assignedPerson>
                    <hl7:representedOrganization>
                        <hl7:id root="root" />
                        <hl7:name>name</hl7:name>
                        <hl7:telecom use="WP" value="tel:123456789" />
                        <hl7:addr>
                            <hl7:streetAddressLine>streetAddressLine</hl7:streetAddressLine>
                            <hl7:postalCode>postalCode</hl7:postalCode>
                            <hl7:city>city</hl7:city>
                            <hl7:country>country</hl7:country>
                        </hl7:addr>
                    </hl7:representedOrganization>
                </hl7:assignedAuthor>
            </hl7:author>
            <hl7:custodian>
                <hl7:assignedCustodian classCode="ASSIGNED">
                    <hl7:representedCustodianOrganization classCode="ORG" determinerCode="INSTANCE">
                        <hl7:id root="Denmark" />
                        <hl7:name>1.3.6.1.4.1.48336</hl7:name>
                        <hl7:telecom nullFlavor="NI" />
                        <hl7:addr>
                            <hl7:country>DK</hl7:country>
                        </hl7:addr>
                    </hl7:representedCustodianOrganization>
                </hl7:assignedCustodian>
            </hl7:custodian>
            <hl7:legalAuthenticator>
                <hl7:time value="20250910064843+0000" />
                <hl7:signatureCode code="S" />
                <hl7:assignedEntity>
                    <hl7:id root="1.3.6.1.4.1.48336" />
                    <hl7:addr>
                        <hl7:streetAddressLine>4, Breydel Street</hl7:streetAddressLine>
                        <hl7:postalCode>B-1000</hl7:postalCode>
                        <hl7:city>Brussels</hl7:city>
                        <hl7:country>BE</hl7:country>
                    </hl7:addr>
                    <hl7:telecom nullFlavor="NI" />
                    <hl7:assignedPerson>
                        <hl7:name>
                            <hl7:family>Authenticator</hl7:family>
                            <hl7:given>Legal</hl7:given>
                        </hl7:name>
                    </hl7:assignedPerson>
                    <hl7:representedOrganization>
                        <hl7:id root="1.3.6.1.4.1.48336" />
                        <hl7:name>eHDSI Solution Provider</hl7:name>
                        <hl7:telecom nullFlavor="NI" />
                        <hl7:addr>
                            <hl7:streetAddressLine>N/A</hl7:streetAddressLine>
                            <hl7:postalCode>BE-1040</hl7:postalCode>
                            <hl7:city>Brussels</hl7:city>
                            <hl7:country>DK</hl7:country>
                        </hl7:addr>
                    </hl7:representedOrganization>
                </hl7:assignedEntity>
            </hl7:legalAuthenticator>
            <hl7:inFulfillmentOf>
                <hl7:order moodCode="RQO">
                    <hl7:id extension="495186474925101" root="1.2.208.176.7.2.2" />
                </hl7:order>
            </hl7:inFulfillmentOf>
            <hl7:component>
                <hl7:structuredBody classCode="DOCBODY">
                    <hl7:component typeCode="COMP">
                        <hl7:section classCode="DOCSECT" moodCode="EVN">
                            <hl7:templateId root="1.3.6.1.4.1.12559.11.10.1.3.1.2.2" />
                            <hl7:id extension="495186474925101L3" root="1.2.208.176.7.2.3" />
                            <hl7:code code="60590-7" codeSystem="2.16.840.1.113883.6.1"
                                codeSystemName="LOINC" codeSystemVersion="2.59"
                                displayName="Medication dispensed.brief Document">
                                <hl7:translation code="60590-7" codeSystem="2.16.840.1.113883.6.1"
                                    codeSystemName="LOINC" displayName="Medication dispensed" />
                            </hl7:code>
                            <hl7:title>Dispensation: 495186474925101L3</hl7:title>
                            <hl7:text>
                                <content xmlns="urn:hl7-org:v3" ID="entry-text">Pinex
                                    mod smerter
                                    ½-1 tablet 3 times a day
                                    500 mg; PARACETAMOL

                                    ½-1 tablet 3 times a day</content>
                                <content xmlns="urn:hl7-org:v3" ID="product-name">Pinex</content>
                                <content xmlns="urn:hl7-org:v3" ID="indication-text">mod smerter</content>
                                <content xmlns="urn:hl7-org:v3" ID="patient-medication-instructions">½-1
                                    tablet 3 times a day</content>
                            </hl7:text>
                            <hl7:entry typeCode="COMP">
                                <hl7:supply classCode="SPLY" moodCode="EVN">
                                    <hl7:templateId root="1.3.6.1.4.1.12559.11.10.1.3.1.3.3" />
                                    <hl7:templateId root="1.3.6.1.4.1.19376.1.5.3.1.4.7.3" />
                                    <hl7:templateId root="2.16.840.1.113883.10.20.1.34" />
                                    <hl7:id extension="extension" root="root" />
                                    <hl7:quantity unit="1" value="1" />
                                    <hl7:product>
                                        <hl7:manufacturedProduct classCode="MANU">
                                            <templateId xmlns="urn:hl7-org:v3"
                                                root="1.3.6.1.4.1.12559.11.10.1.3.1.3.29" />
                                            <manufacturedMaterial xmlns="urn:hl7-org:v3" classCode="MMAT"
                                                determinerCode="KIND">
                                                <templateId root="1.3.6.1.4.1.12559.11.10.1.3.1.3.30" />
                                                <code code="28103909506" codeSystem="1.2.208.176.3.1"
                                                    codeSystemName="Lægemiddelstyrelsens DrugID"
                                                    displayName="Pinex" />
                                                <name>Pinex</name>
                                                <pharm:desc xmlns:pharm="urn:hl7-org:pharm">500 mg</pharm:desc>
                                                <pharm:formCode xmlns:pharm="urn:hl7-org:pharm"
                                                    code="10219000" codeSystem="0.4.0.127.0.16.1.1.2.1"
                                                    codeSystemName="EDQM" codeSystemVersion="2025-01-07"
                                                    displayName="Tablet">
                                                    <hl7:translation code="TAB"
                                                        codeSystem="1.2.208.176.3.22" codeSystemName="LMS22"
                                                        displayName="tabletter" />
                                                </pharm:formCode>
                                                <pharm:asContent xmlns:pharm="urn:hl7-org:pharm"
                                                    classCode="CONT">
                                                    <pharm:quantity unit="1" value="100">
                                                        <hl7:translation>
                                                            <originalText>stk.</originalText>
                                                        </hl7:translation>
                                                    </pharm:quantity>
                                                    <pharm:containerPackagedProduct classCode="CONT"
                                                        determinerCode="KIND">
                                                        <pharm:code code="580984"
                                                            codeSystem="1.2.208.176.3.2"
                                                            codeSystemName="Varenumre på lægemiddelpakninger" />
                                                        <pharm:name>Pinex, tabletter, 500 mg, 100 stk.
                                                            (blister)</pharm:name>
                                                        <pharm:formCode code="30007000"
                                                            codeSystem="0.4.0.127.0.16.1.1.2.1"
                                                            codeSystemName="EDQM"
                                                            codeSystemVersion="2025-01-07"
                                                            displayName="Blister">
                                                            <hl7:translation code="BLI"
                                                                codeSystem="1.2.208.176.3.14"
                                                                codeSystemName="LMS14" />
                                                        </pharm:formCode>
                                                    </pharm:containerPackagedProduct>
                                                </pharm:asContent>
                                                <pharm:asSpecializedKind xmlns:pharm="urn:hl7-org:pharm"
                                                    classCode="GRIC">
                                                    <pharm:generalizedMaterialKind classCode="MMAT"
                                                        determinerCode="KIND">
                                                        <pharm:code code="N02BE01"
                                                            codeSystem="2.16.840.1.113883.6.73"
                                                            codeSystemName="Anatomical Therapeutic Chemical"
                                                            codeSystemVersion="2025-01"
                                                            displayName="paracetamol">
                                                            <hl7:translation code="N02BE01"
                                                                codeSystem="2.16.840.1.113883.6.73"
                                                                codeSystemName="Anatomical Therapeutic Chemical"
                                                                displayName="Paracetamol" />
                                                        </pharm:code>
                                                    </pharm:generalizedMaterialKind>
                                                </pharm:asSpecializedKind>
                                                <pharm:ingredient xmlns:pharm="urn:hl7-org:pharm"
                                                    classCode="ACTI">
                                                    <pharm:quantity>
                                                        <numerator
                                                            xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                                                            unit="mg" value="500" xsi:type="hl7:PQ" />
                                                        <denominator
                                                            xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                                                            unit="1" value="1" xsi:type="hl7:PQ" />
                                                    </pharm:quantity>
                                                    <pharm:ingredientSubstance classCode="MMAT"
                                                        determinerCode="KIND">
                                                        <pharm:code nullFlavor="UNK" />
                                                        <pharm:name>PARACETAMOL</pharm:name>
                                                    </pharm:ingredientSubstance>
                                                </pharm:ingredient>
                                            </manufacturedMaterial>
                                            <manufacturerOrganization xmlns="urn:hl7-org:v3" classCode="ORG"
                                                determinerCode="INSTANCE">
                                                <name>Vitabalans</name>
                                            </manufacturerOrganization>
                                        </hl7:manufacturedProduct>
                                    </hl7:product>
                                    <hl7:performer typeCode="PRF">
                                        <hl7:time value="20250910064843+0000" />
                                        <hl7:assignedEntity>
                                            <hl7:id extension="extension" root="root" />
                                            <hl7:addr>
                                                <hl7:streetAddressLine>streetAddressLine</hl7:streetAddressLine>
                                                <hl7:postalCode>postalCode</hl7:postalCode>
                                                <hl7:city>city</hl7:city>
                                                <hl7:country>country</hl7:country>
                                            </hl7:addr>
                                            <hl7:telecom use="WP" value="tel:123456789" />
                                            <hl7:assignedPerson>
                                                <hl7:name>
                                                    <hl7:family>familyName</hl7:family>
                                                    <hl7:given>givenName</hl7:given>
                                                    <hl7:suffix>suffix</hl7:suffix>
                                                </hl7:name>
                                            </hl7:assignedPerson>
                                            <hl7:representedOrganization>
                                                <hl7:id root="root" />
                                                <hl7:name>name</hl7:name>
                                                <hl7:telecom use="WP" value="tel:123456789" />
                                                <hl7:addr>
                                                    <hl7:streetAddressLine>streetAddressLine</hl7:streetAddressLine>
                                                    <hl7:postalCode>postalCode</hl7:postalCode>
                                                    <hl7:city>city</hl7:city>
                                                    <hl7:country>country</hl7:country>
                                                </hl7:addr>
                                            </hl7:representedOrganization>
                                        </hl7:assignedEntity>
                                    </hl7:performer>
                                    <hl7:participant contextControlCode="OP" typeCode="PRF">
                                        <hl7:participantRole classCode="LIC">
                                            <hl7:id root="entryOid" />
                                            <hl7:scopingEntity classCode="ORG">
                                                <hl7:id root="entryOid" />
                                            </hl7:scopingEntity>
                                        </hl7:participantRole>
                                    </hl7:participant>
                                    <hl7:entryRelationship typeCode="REFR">
                                        <hl7:substanceAdministration classCode="SBADM" moodCode="INT">
                                            <hl7:id extension="495186474925101" root="1.2.208.176.7.2.2" />
                                            <hl7:consumable>
                                                <hl7:manufacturedProduct classCode="MANU">
                                                    <templateId xmlns="urn:hl7-org:v3"
                                                        root="1.3.6.1.4.1.12559.11.10.1.3.1.3.29" />
                                                    <manufacturedMaterial xmlns="urn:hl7-org:v3"
                                                        classCode="MMAT" determinerCode="KIND">
                                                        <templateId
                                                            root="1.3.6.1.4.1.12559.11.10.1.3.1.3.30" />
                                                        <code code="28103909506"
                                                            codeSystem="1.2.208.176.3.1"
                                                            codeSystemName="Lægemiddelstyrelsens DrugID" />
                                                        <name>Pinex</name>
                                                        <pharm:desc xmlns:pharm="urn:hl7-org:pharm">500 mg</pharm:desc>
                                                        <pharm:formCode xmlns:pharm="urn:hl7-org:pharm"
                                                            code="10219000"
                                                            codeSystem="0.4.0.127.0.16.1.1.2.1"
                                                            codeSystemName="EDQM"
                                                            codeSystemVersion="2025-01-07"
                                                            displayName="Tablet">
                                                            <hl7:translation code="TAB"
                                                                codeSystem="1.2.208.176.3.22"
                                                                codeSystemName="LMS22"
                                                                displayName="tabletter" />
                                                        </pharm:formCode>
                                                        <pharm:asContent xmlns:pharm="urn:hl7-org:pharm"
                                                            classCode="CONT">
                                                            <pharm:quantity unit="1" value="100">
                                                                <hl7:translation>
                                                                    <originalText>stk.</originalText>
                                                                </hl7:translation>
                                                            </pharm:quantity>
                                                            <pharm:containerPackagedProduct classCode="CONT"
                                                                determinerCode="KIND">
                                                                <pharm:code code="580984"
                                                                    codeSystem="1.2.208.176.3.2"
                                                                    codeSystemName="Varenumre på lægemiddelpakninger" />
                                                                <pharm:name>Pinex, tabletter, 500 mg, 100 stk.
                                                                    (blister)</pharm:name>
                                                                <pharm:formCode code="30007000"
                                                                    codeSystem="0.4.0.127.0.16.1.1.2.1"
                                                                    codeSystemName="EDQM"
                                                                    codeSystemVersion="2025-01-07"
                                                                    displayName="Blister">
                                                                    <hl7:translation code="BLI"
                                                                        codeSystem="1.2.208.176.3.14"
                                                                        codeSystemName="LMS14" />
                                                                </pharm:formCode>
                                                            </pharm:containerPackagedProduct>
                                                        </pharm:asContent>
                                                        <pharm:asSpecializedKind
                                                            xmlns:pharm="urn:hl7-org:pharm" classCode="GRIC">
                                                            <pharm:generalizedMaterialKind classCode="MMAT"
                                                                determinerCode="KIND">
                                                                <pharm:code code="N02BE01"
                                                                    codeSystem="2.16.840.1.113883.6.73"
                                                                    codeSystemName="Anatomical Therapeutic Chemical"
                                                                    codeSystemVersion="2025-01"
                                                                    displayName="paracetamol">
                                                                    <hl7:translation code="N02BE01"
                                                                        codeSystem="2.16.840.1.113883.6.73"
                                                                        codeSystemName="Anatomical Therapeutic Chemical"
                                                                        displayName="Paracetamol" />
                                                                </pharm:code>
                                                            </pharm:generalizedMaterialKind>
                                                        </pharm:asSpecializedKind>
                                                        <pharm:ingredient xmlns:pharm="urn:hl7-org:pharm"
                                                            classCode="ACTI">
                                                            <pharm:quantity>
                                                                <numerator
                                                                    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                                                                    unit="mg" value="500" xsi:type="hl7:PQ" />
                                                                <denominator
                                                                    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                                                                    unit="1" value="1" xsi:type="hl7:PQ" />
                                                            </pharm:quantity>
                                                            <pharm:ingredientSubstance classCode="MMAT"
                                                                determinerCode="KIND">
                                                                <pharm:code nullFlavor="UNK" />
                                                                <pharm:name>PARACETAMOL</pharm:name>
                                                            </pharm:ingredientSubstance>
                                                        </pharm:ingredient>
                                                    </manufacturedMaterial>
                                                    <manufacturerOrganization xmlns="urn:hl7-org:v3"
                                                        classCode="ORG" determinerCode="INSTANCE">
                                                        <name>Vitabalans</name>
                                                    </manufacturerOrganization>
                                                </hl7:manufacturedProduct>
                                            </hl7:consumable>
                                        </hl7:substanceAdministration>
                                    </hl7:entryRelationship>
                                </hl7:supply>
                            </hl7:entry>
                        </hl7:section>
                    </hl7:component>
                </hl7:structuredBody>
            </hl7:component>
        </hl7:ClinicalDocument>
        """;
}
