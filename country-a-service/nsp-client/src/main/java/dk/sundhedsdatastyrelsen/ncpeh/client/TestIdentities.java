package dk.sundhedsdatastyrelsen.ncpeh.client;

import dk.dkma.medicinecard.xml_schema._2015._06._01.OrganisationIdentifierPredefinedSourceType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.OrganisationIdentifierType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.OrganisationType;
import dk.nsp.test.idp.model.Identity;
import dk.nsp.test.idp.vault.RemoteCredentialVault;

import java.net.URI;
import java.util.UUID;

import static dk.nsp.test.idp.builder.Builders.*;

public class TestIdentities {
    public static Identity apotekerJeppeMoeller = employeeIdentity()
        .representing(employee()
            .identifiedBy(UUID.fromString("00798849-effe-4733-bcc4-670093830511")))
        .employedAt(organization()
            .named("Sundhedsdatastyrelsen")
            .identifiedBy("33257872"))
        .usedBy(serviceConsumer()
            .named("Service Consumer Test")
            .identifiedBy(new RemoteCredentialVault(URI.create("https://www.nspop.dk/download/attachments/190481050/NSP_Test_Service_Consumer_sds.p12"), "Test1234".toCharArray(), true)))
        .verifiedBy(identityProvider()
            .named("https://idp.test.nspop.dk")
            .identifiedBy(new RemoteCredentialVault(URI.create("https://www.nspop.dk/download/attachments/190481050/NSP_Test_Identity_Provider_sds.p12"), "Test1234".toCharArray(), true)))
        .using(securityTokenService()
            .at(URI.create("http://test1.ekstern-test.nspop.dk:8080/sts/services/BST2SOSI")))
        .build();

    /**
     * Chris Christoffersen, apoteker, CPR: 3001010033
     */
    public static Identity apotekerChrisChristoffersen = employeeIdentity()
        .representing(employee()
            .identifiedBy(UUID.fromString("1fdff71e-2697-4f84-8611-e890a422cef8")))
        .employedAt(organization()
            .named("Sundhedsdatastyrelsen")
            .identifiedBy("33257872"))
        .usedBy(serviceConsumer()
            .named("Service Consumer Test")
            .identifiedBy(new RemoteCredentialVault(URI.create("https://www.nspop.dk/download/attachments/190481050/NSP_Test_Service_Consumer_sds.p12"), "Test1234".toCharArray(), true)))
        .verifiedBy(identityProvider()
            .named("https://idp.test.nspop.dk")
            .identifiedBy(new RemoteCredentialVault(URI.create("https://www.nspop.dk/download/attachments/190481050/NSP_Test_Identity_Provider_sds.p12"), "Test1234".toCharArray(), true)))
        .using(securityTokenService()
            .at(URI.create("http://test1.ekstern-test.nspop.dk:8080/sts/services/BST2SOSI")))
        .build();

    /**
     * Delivery site - for effectuating a prescription
     */
    public static OrganisationType deliverySiteRyApotek =
        OrganisationType.builder()
            .withName("Ry Apoteksudsalg")
            .withType("Apotek")
            .withIdentifier()
            .withSource("CVR-P")
            .withValue("1008648049")
            .end()
            .build();
    public static OrganisationIdentifierType skanderborgApotek =
        OrganisationIdentifierType.builder()
                                  .withSource(OrganisationIdentifierPredefinedSourceType.EAN_LOKATIONSNUMMER.value())
                                  .withValue("5790000170609") //This is a test value found on wiki.fmk-teknik.dk
                                  .build();


}
