package dk.nsp.epps.service.client;

import dk.sosi.seal.SOSIFactory;
import dk.sosi.seal.model.*;
import dk.sosi.seal.model.constants.SubjectIdentifierTypeValues;
import dk.sosi.seal.pki.SOSITestFederation;
import dk.sosi.seal.vault.CredentialPair;
import dk.sosi.seal.vault.CredentialVault;
import dk.sosi.seal.vault.GenericCredentialVault;
import dk.sosi.seal.xml.XmlUtil;
import jakarta.annotation.Nullable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Component;
import org.w3c.dom.Element;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Collections;
import java.util.Optional;
import java.util.Properties;

@Slf4j
@Component
public class SosiClient {
    private final URI stsUri;
    private final CredentialVault credentialVault;
    private final Properties props = SignatureUtil.setupCryptoProviderForJVM();
    private final SOSIFactory sosiFactory;
    private final CareProvider careProvider =
        new CareProvider(SubjectIdentifierTypeValues.CVR_NUMBER, "33257872", "Sundhedsdatastyrelsen");

    private IDCard idCard;

    public SosiClient(
        @Value("${app.sts.endpoint.url}") String stsUri,
        @Value("${app.sts.keystore.path}") String keystorePath,
        @Value("${app.sts.keystore.password}") String keystorePassword,
        @Value("${app.sts.keystore.alias}") String keystoreAlias
    ) throws URISyntaxException, IOException, CertificateException,
        NoSuchAlgorithmException, KeyStoreException, UnrecoverableKeyException {
        this.stsUri = new URI(stsUri);
        credentialVault = makeCredentialVault(keystorePath, keystorePassword, keystoreAlias);
        // TODO: shouldn't only support test federation
        final var federation = new SOSITestFederation(props);
        sosiFactory = new SOSIFactory(federation, credentialVault, props);
    }

    private CredentialVault makeCredentialVault(String keystorePath, String keystorePassword, String keystoreAlias) throws IOException, CertificateException, NoSuchAlgorithmException, KeyStoreException, UnrecoverableKeyException {
        final var credentialVault = new GenericCredentialVault(props, keystorePassword);
        try (var is = new FileInputStream(keystorePath)) {
            credentialVault.getKeyStore().load(is, keystorePassword.toCharArray());
        }
        final var certificate = (X509Certificate) credentialVault.getKeyStore().getCertificate(keystoreAlias);
        final var privateKey = (PrivateKey) credentialVault.getKeyStore().getKey(keystoreAlias,
            keystorePassword.toCharArray());
        credentialVault.setSystemCredentialPair(new CredentialPair(certificate, privateKey));

        return credentialVault;
    }

    private IDCard freshIdCard() throws IOException, InterruptedException {
        final var requestIdCard = sosiFactory.createNewSystemIDCard(
            "ePPS - NSP testmiljø",
            careProvider,
            AuthenticationLevel.VOCES_TRUSTED_SYSTEM,
            null,
            null,
            credentialVault.getSystemCredentialPair().getCertificate(),
            null
        );

        final var request = sosiFactory.createNewSecurityTokenRequest();
        request.setIDCard(requestIdCard);
        final SecurityTokenResponse response;
        try {
            response = sosiFactory.deserializeSecurityTokenResponse(sendRequest(stsUri, request, null));
        } catch (ServiceResponseException e) {
            throw new RuntimeException(e);
        }
        return response.getIDCard();
    }

    public IDCard getIdCard() throws IOException, InterruptedException {
        if (idCard == null || !idCard.isValidInTime()) {
            log.info("Retrieving fresh ID card from STS service.");
            idCard = freshIdCard();
        }
        return idCard;
    }

    private String sendRequest(URI uri, Message request, @Nullable String soapAction) throws IOException,
        InterruptedException, ServiceResponseException {
        final var client = HttpClient.newHttpClient();
        final var requestString = XmlUtil.node2String(request.serialize2DOMDocument());
        log.info("Sending request to uri {}: {}", uri, requestString);
        final var r = HttpRequest.newBuilder(uri)
            .header("SOAPAction", String.format("\"%s\"", Optional.ofNullable(soapAction).orElse("")))
            .header("Content-Type", "text/xml; charset=utf-8")
            .POST(HttpRequest.BodyPublishers.ofString(requestString))
            .build();
        final var response = client.send(r, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200) {
            throw new ServiceResponseException(response.statusCode(), response.body());
        }
        return response.body();
    }


    private Jaxb2Marshaller cprMarshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPaths(
            "oio.medcom.cprservice._1_0"
        );
        return marshaller;
    }

    public Reply sendNspRequest(URI uri, Element soapBody, String soapAction) throws IOException,
        InterruptedException, ServiceResponseException {
        return sendNspRequest(uri, soapBody, soapAction, Collections.emptyList());
    }

    public Reply sendNspRequest(URI uri, Element soapBody, String soapAction, Iterable<Element> extraHeaders) throws IOException,
        InterruptedException, ServiceResponseException {
        final var request = sosiFactory.createNewRequest(false, null);
        request.setIDCard(getIdCard());
        request.setBody(soapBody);
        for (var extraHeader : extraHeaders) {
            request.addNonSOSIHeader(extraHeader);
        }

        final var response = sendRequest(uri, request, soapAction);
        return sosiFactory.deserializeReply(response);
    }

    /**
     * Only used for development and testing.
     */
    public static void main(String[] args) throws Exception {
        var sosiClient = new SosiClient(
            "http://test2.ekstern-test.nspop.dk:8080/sts/services/NewSecurityTokenService",
            "epps-test-cert.p12",
            System.getenv("EPPS_TEST_CERT_PASSWORD"),
            "epps - nsp testmiljø - 3/11/2026"
        );

//        var cprClient = new CprClient(
//            "http://test2.ekstern-test.nspop.dk:8080/stamdata-cpr-ws/service/DetGodeCPROpslag-1.0.4",
//            sosiClient
//        );
//        log.info(cprClient.getPersonInformation("0101011234")
//            .getPersonInformationStructure().getRegularCPRPerson().getPersonNameForAddressingName());

        var fmkClient = new FmkClient("http://localhost:8080/ws/", sosiClient);
//        var fmkClient = new FmkClient("https://test2.fmk.netic.dk/fmk12/ws/MedicineCard", sosiClient);
        log.info(fmkClient.getPrescriptions("1111111118").getPrescription().get(0).getDrug().getName());
    }
}
