package dk.sundhedsdatastyrelsen.ncpeh.mocks;

import dk.nsi._2024._01._05.stamdataauthorization.AuthorizationResponseType;
import dk.nsi._2024._01._05.stamdataauthorization.AuthorizationType;
import dk.sundhedsdatastyrelsen.ncpeh.authentication.AuthenticationService;
import dk.sundhedsdatastyrelsen.ncpeh.authentication.NspDgwsIdentity;
import dk.sundhedsdatastyrelsen.ncpeh.authentication.idcard.DgwsIdCardRequest;
import dk.sundhedsdatastyrelsen.ncpeh.client.AuthorizationRegistryClient;
import dk.sundhedsdatastyrelsen.ncpeh.client.NspClientDgws;
import jakarta.xml.bind.JAXBException;

import java.net.URI;

public class AuthorizationRegistryClientMock extends AuthorizationRegistryClient {
    public AuthorizationRegistryClientMock() {
        super(
            "",
            new NspClientDgws(
                new AuthenticationService(
                    URI.create("https://ncp"),
                    null,
                    "",
                    new DgwsIdCardRequest.Configuration(
                        "11111111",
                        "issuer",
                        "itprovider",
                        "careprovider"))));
    }

    @Override
    public AuthorizationResponseType requestByAuthorizationCode(String authorizationCode, NspDgwsIdentity caller) throws JAXBException {
        // 7170 is "Læge", doctor.
        return AuthorizationResponseType.builder()
            .withAutorisation(AuthorizationType.builder()
                .withUddannelsesKode("7170")
                .withSpeciale1("Radiologi")
                .build())
            .build();
    }
}
