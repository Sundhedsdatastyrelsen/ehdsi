package dk.sundhedsdatastyrelsen.ncpeh.authentication;

import dk.sundhedsdatastyrelsen.ncpeh.authentication.idcard.DgwsIdCardRequest;
import dk.sundhedsdatastyrelsen.ncpeh.base.utils.XPathWrapper;
import dk.sundhedsdatastyrelsen.ncpeh.base.utils.XmlException;
import dk.sundhedsdatastyrelsen.ncpeh.base.utils.XmlNamespace;
import dk.sundhedsdatastyrelsen.ncpeh.base.utils.XmlUtils;
import org.slf4j.Logger;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Instant;

public class SosiStsClientDgws {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(SosiStsClientDgws.class);
    private static final XPathWrapper xpath = new XPathWrapper(XmlNamespace.SOAP, XmlNamespace.SAML);

    private SosiStsClientDgws() {
    }

    /// @throws AuthenticationException if something goes wrong
    public static DgwsAssertion exchangeIdCard(DgwsIdCardRequest request, URI stsUri) {
        try {
            var requestXml = XmlUtils.writeDocumentToString(request.soapBody());
            var response = sendRequest(requestXml, stsUri);
            return parseResponse(response);

        } catch (IOException e) {
            throw new AuthenticationException("SOSI STS request failed", e);
        } catch (XmlException e) {
            throw new AuthenticationException("Malformed id card exchange request", e);
        }
    }

    private static DgwsAssertion parseResponse(String result) {
        try {
            var document = XmlUtils.parse(result);

            // Was it an error response?
            var fault = xpath.evalElement("/soap:Envelope/soap:Body/soap:Fault", document);
            if (fault != null) {
                throw new AuthenticationException.SosiStsException(
                    xpath.evalString("faultcode", fault),
                    xpath.evalString("faultstring", fault),
                    xpath.evalString("faultactor", fault)
                );
            }

            // Otherwise we assume it was a success
            var idCard = xpath.evalElement("//saml:Assertion[@id='IDCard']", document);
            return new DgwsAssertion(
                idCard,
                Instant.parse(xpath.evalString("saml:Conditions/@NotOnOrAfter", idCard)));
        } catch (XmlException e) {
            throw new AuthenticationException("Error parsing SOSI STS response", e);
        }
    }

    private static String sendRequest(String xml, URI uri) throws IOException {
        try (
            var httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .build();
        ) {
            var request = HttpRequest.newBuilder(uri)
                .header("Content-Type", "application/soap+xml; charset=utf-8")
                .POST(HttpRequest.BodyPublishers.ofString(xml))
                .build();
            // If I use .ofInputStream, it never terminates.
            var result = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            // the service should return 500 and an error response on failure, but it currently returns 200 and an
            // error response on failure.
            if (result.statusCode() != 200 && result.statusCode() != 500) {
                throw new AuthenticationException("Unexpected HTTP status code from SOSI STS: " + result.statusCode());
            }
            // we do not yet know whether the request failed, this is indicated in the body.
            return result.body();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new AuthenticationException("Thread interrupted", e);
        }
    }
}
