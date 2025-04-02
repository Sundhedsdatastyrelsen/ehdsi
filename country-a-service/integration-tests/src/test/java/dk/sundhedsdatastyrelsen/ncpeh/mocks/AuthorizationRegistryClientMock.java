package dk.sundhedsdatastyrelsen.ncpeh.mocks;

import dk.nsi._2024._01._05.stamdataauthorization.AuthorizationResponseType;
import dk.nsi._2024._01._05.stamdataauthorization.AuthorizationType;
import dk.nsp.test.idp.model.Identity;
import dk.sundhedsdatastyrelsen.ncpeh.client.AuthorizationRegistryClient;
import jakarta.xml.bind.JAXBException;

public class AuthorizationRegistryClientMock extends AuthorizationRegistryClient {
    public AuthorizationRegistryClientMock() {
        super("");
    }

    @Override
    public AuthorizationResponseType requestByAuthorizationCode(String authorizationCode, Identity caller) throws JAXBException {
        // 7170 is "Læge", doctor.
        return AuthorizationResponseType.builder()
            .withAutorisation(AuthorizationType.builder()
                .withUddannelsesKode("7170")
                .withSpeciale1("Radiologi")
                .build())
            .build();
    }
}
