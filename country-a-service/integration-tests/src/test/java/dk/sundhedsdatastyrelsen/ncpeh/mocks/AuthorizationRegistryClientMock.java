package dk.sundhedsdatastyrelsen.ncpeh.mocks;
import dk.nsi.__.stamdata._3.AuthorizationResponseType;
import dk.nsi.__.stamdata._3.AuthorizationType;
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
 .withAuthorization(AuthorizationType.builder().withAuthorizationCode("7170").build())
 .build();
 }
}
