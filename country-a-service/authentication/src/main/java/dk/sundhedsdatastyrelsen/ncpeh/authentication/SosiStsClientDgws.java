package dk.sundhedsdatastyrelsen.ncpeh.authentication;

import dk.sundhedsdatastyrelsen.ncpeh.authentication.idcard.DgwsIdCardRequest;
import dk.sundhedsdatastyrelsen.ncpeh.shared.XPathWrapper;
import dk.sundhedsdatastyrelsen.ncpeh.shared.XmlUtils;
import org.slf4j.Logger;

import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class SosiStsClientDgws {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(SosiStsClientDgws.class);
    private static final XPathWrapper xpath = new XPathWrapper(XmlNamespace.SOAP);

    private SosiStsClientDgws() {
    }

    public static DgwsAssertion exchangeIdCard(DgwsIdCardRequest request, URI stsUri) throws AuthenticationException {
        try {
            var requestXml = XmlUtils.writeDocumentToString(request.soapBody());
            var response = sendRequest(requestXml, stsUri);
            return parseResponse(response);

        } catch (IOException e) {
            throw new AuthenticationException("SOSI STS request failed", e);
        } catch (TransformerException e) {
            throw new AuthenticationException("Malformed id card exchange request", e);
        }
    }

    private static DgwsAssertion parseResponse(String result) throws AuthenticationException {
        try {
            var document = XmlUtils.parse(result);

            // Was it an error response?
            var fault = xpath.evalEl("/soap:Envelope/soap:Body/soap:Fault", document);
            if (fault != null) {
                throw new AuthenticationException.SosiStsException(
                    xpath.evalString("faultcode", fault),
                    xpath.evalString("faultstring", fault),
                    xpath.evalString("faultactor", fault)
                );
            }

            // Otherwise we assume it was a success
            return new DgwsAssertion(
                xpath.evalEl(
                    "//*[@id='IDCard']",
                    document));
        } catch (XPathExpressionException e) {
            throw new AuthenticationException("Error parsing SOSI STS response", e);
        }
    }

    private static String sendRequest(String xml, URI uri) throws IOException, AuthenticationException {
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
