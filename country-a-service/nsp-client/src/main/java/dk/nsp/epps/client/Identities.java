package dk.nsp.epps.client;

import dk.nsp.test.idp.model.Identity;
import dk.nsp.test.idp.vault.RemoteCredentialVault;

import java.net.URI;
import java.util.UUID;

import static dk.nsp.test.idp.builder.Builders.*;

public class Identities {
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
}
