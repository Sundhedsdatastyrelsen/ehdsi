package dk.sundhedsdatastyrelsen.ncpeh.authentication;

import dk.sundhedsdatastyrelsen.ncpeh.authentication.idcard.DgwsIdCardRequest;
import org.slf4j.Logger;

import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class SosiStsClientDgws {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(SosiStsClientDgws.class);
    private static final XPathWrapper xpath = new XPathWrapper(XmlNamespace.SOAP, XmlNamespace.WST);

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

    private static DgwsAssertion parseResponse(InputStream result) throws AuthenticationException {
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
                    "/soap:Envelope/soap:Body/wst:RequestSecurityTokenResponse/wst:RequestedSecurityToken/*[1]",
                    document));
        } catch (IOException e) {
            throw new AuthenticationException("Error reading SOSI STS response", e);
        } catch (XPathExpressionException e) {
            throw new AuthenticationException("Error parsing SOSI STS response", e);
        }
    }

    private static InputStream sendRequest(String xml, URI uri) throws IOException, AuthenticationException {
        try (
            var httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .followRedirects(HttpClient.Redirect.NORMAL)
                .connectTimeout(Duration.ofSeconds(20))
                .build();
        ) {
            var request = HttpRequest.newBuilder(uri)
                .header("Content-Type", "application/soap+xml; charset=utf-8")
                .POST(HttpRequest.BodyPublishers.ofString(xml))
                .build();
            var result = httpClient.send(request, HttpResponse.BodyHandlers.ofInputStream());
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
