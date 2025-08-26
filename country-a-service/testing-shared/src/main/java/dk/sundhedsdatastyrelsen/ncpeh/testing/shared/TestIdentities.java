package dk.sundhedsdatastyrelsen.ncpeh.testing.shared;

import dk.sundhedsdatastyrelsen.ncpeh.authentication.AuthenticationException;
import dk.sundhedsdatastyrelsen.ncpeh.authentication.CertificateUtils;
import dk.sundhedsdatastyrelsen.ncpeh.client.NspDgwsIdentity;

import java.net.URI;

public class TestIdentities {
    private TestIdentities() {}

    public static final NspDgwsIdentity.User apotekerJeppeMoeller;

    public static final NspDgwsIdentity.User apotekerChrisChristoffersen;

    public static final NspDgwsIdentity.System systemIdentity;

    static {
        try {
            apotekerChrisChristoffersen = new NspDgwsIdentity.User(
                URI.create("http://test1.ekstern-test.nspop.dk:8080/sts/services/BST2SOSI"),
                CertificateUtils.loadCertificateFromKeystore(
                    TestIdentities.class.getResourceAsStream("NSP_Test_Service_Consumer_sds.p12"),
                    "",
                    "Test1234"));
            apotekerJeppeMoeller = new NspDgwsIdentity.User(
                URI.create("http://test1.ekstern-test.nspop.dk:8080/sts/services/BST2SOSI"),
                CertificateUtils.loadCertificateFromKeystore(
                    TestIdentities.class.getResourceAsStream("NSP_Test_Service_Consumer_sds.p12"),
                    "",
                    "Test1234"));
            systemIdentity = new NspDgwsIdentity.System(
                // TODO verify params for this.
                URI.create("http://test1.ekstern-test.nspop.dk:8080/sts/services/BST2SOSI"),
                CertificateUtils.loadCertificateFromKeystore(
                    TestIdentities.class.getResourceAsStream("NSP_Test_Service_Consumer_sds.p12"),
                    "",
                    "Test1234"));
        } catch (AuthenticationException e) {
            throw new RuntimeException(e);
        }
    }
//        employeeIdentity()
//        .representing(employee()
//            .identifiedBy(UUID.fromString("00798849-effe-4733-bcc4-670093830511")))
//        .employedAt(organization()
//            .named("Sundhedsdatastyrelsen")
//            .identifiedBy("33257872"))
//        .usedBy(serviceConsumer()
//            .named("Service Consumer Test")
//            .identifiedBy(new RemoteCredentialVault(URI.create("https://www.nspop.dk/download/attachments/190481050/NSP_Test_Service_Consumer_sds.p12"), "Test1234".toCharArray(), true)))
//        .verifiedBy(identityProvider()
//            .named("https://idp.test.nspop.dk")
//            .identifiedBy(new RemoteCredentialVault(URI.create("https://www.nspop.dk/download/attachments/190481050/NSP_Test_Identity_Provider_sds.p12"), "Test1234".toCharArray(), true)))
//        .using(securityTokenService()
//            .at(URI.create("http://test1.ekstern-test.nspop.dk:8080/sts/services/BST2SOSI")))
//        .build();
    /// Chris Christoffersen, apoteker, CPR: 3001010033
//    public static final Identity apotekerChrisChristoffersen = employeeIdentity()
//        .representing(employee()
//            .identifiedBy(UUID.fromString("1fdff71e-2697-4f84-8611-e890a422cef8")))
//        .employedAt(organization()
//            .named("Sundhedsdatastyrelsen")
//            .identifiedBy("33257872"))
//        .usedBy(serviceConsumer()
//            .named("Service Consumer Test")
//            .identifiedBy(new RemoteCredentialVault(URI.create("https://www.nspop.dk/download/attachments/190481050/NSP_Test_Service_Consumer_sds.p12"), "Test1234".toCharArray(), true)))
//        .verifiedBy(identityProvider()
//            .named("https://idp.test.nspop.dk")
//            .identifiedBy(new RemoteCredentialVault(URI.create("https://www.nspop.dk/download/attachments/190481050/NSP_Test_Identity_Provider_sds.p12"), "Test1234".toCharArray(), true)))
//        .using(securityTokenService()
//            .at(URI.create("http://test1.ekstern-test.nspop.dk:8080/sts/services/BST2SOSI")))
//        .build();


}
