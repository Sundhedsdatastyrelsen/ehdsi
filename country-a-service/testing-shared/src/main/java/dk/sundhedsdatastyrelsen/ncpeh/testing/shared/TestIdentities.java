package dk.sundhedsdatastyrelsen.ncpeh.testing.shared;

import dk.sundhedsdatastyrelsen.ncpeh.authentication.AuthenticationException;
import dk.sundhedsdatastyrelsen.ncpeh.authentication.CertificateUtils;
import dk.sundhedsdatastyrelsen.ncpeh.authentication.NspDgwsIdentity;

import java.util.UUID;

public class TestIdentities {
    private TestIdentities() {}

    ///  Jeppe Møller, apoteker, CPR:
    public static final NspDgwsIdentity.ReplaceWithIdws apotekerJeppeMoeller;

    /// Chris Christoffersen, apoteker, CPR: 3001010033
    public static final NspDgwsIdentity.ReplaceWithIdws apotekerChrisChristoffersen;

    /// Charles Babbage, læge, CPR:
    public static final NspDgwsIdentity.ReplaceWithIdws lægeCharlesBabbage;

    public static final NspDgwsIdentity.System systemIdentity;

    public static String getPatientIdFromCpr(String cpr){
        var cprPart = cpr;
        if(!cpr.contains("-")){
            cprPart = cpr.substring(0,6)+"-"+cpr.substring(6,10);
        }
        var DK_CPR_OID = "1.2.208.176.1.2"; // testing-shared has no dependency on CDA Generator, so this is just copied.
        return cprPart+"^^^&"+ DK_CPR_OID+"&ISO";
    }

    static {
        try {
            var systemCert = CertificateUtils.loadCertificateFromKeystore(
                TestIdentities.class.getClassLoader().getResourceAsStream("NSP_Test_Service_Consumer_sds.p12"),
                "1",
                "Test1234");
            var idpCert = CertificateUtils.loadCertificateFromKeystore(
                TestIdentities.class.getClassLoader().getResourceAsStream("NSP_Test_Identity_Provider_sds.p12"),
                "1",
                "Test1234"
            );
            apotekerChrisChristoffersen = new NspDgwsIdentity.ReplaceWithIdws(
                Sosi.sosiPersonalDgwsUri,
                systemCert,
                UUID.fromString("1fdff71e-2697-4f84-8611-e890a422cef8"),
                idpCert);
            apotekerJeppeMoeller = new NspDgwsIdentity.ReplaceWithIdws(
                Sosi.sosiPersonalDgwsUri,
                systemCert,
                UUID.fromString("00798849-effe-4733-bcc4-670093830511"),
                idpCert
            );
            lægeCharlesBabbage = new NspDgwsIdentity.ReplaceWithIdws(
                Sosi.sosiPersonalDgwsUri,
                systemCert,
                UUID.fromString("46559bb9-d720-48b7-b9bd-c280915768d0"),
                idpCert
            );
            systemIdentity = new NspDgwsIdentity.System(
                Sosi.sosiOrganisationDgwsUri,
                systemCert);
        } catch (AuthenticationException e) {
            throw new RuntimeException(e);
        }
    }
}
