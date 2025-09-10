package dk.sundhedsdatastyrelsen.ncpeh.authentication;

import dk.sundhedsdatastyrelsen.ncpeh.authentication.bootstraptoken.BootstrapTokenExchangeRequest;
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
import java.time.Instant;

public class SosiStsClientIdws {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(SosiStsClientIdws.class);
    private static final XPathWrapper xpath = new XPathWrapper(
        XmlNamespace.SAML,
        XmlNamespace.SOAP,
        XmlNamespace.WST13,
        XmlNamespace.WSA,
        XmlNamespace.WSP,
        XmlNamespace.WSU);

    private final URI serviceUri;
    private final HttpClient httpClient;

    public SosiStsClientIdws(URI serviceUri) {
        this.serviceUri = serviceUri;

        this.httpClient = HttpClient.newBuilder()
            .followRedirects(HttpClient.Redirect.NORMAL)
            .connectTimeout(Duration.ofSeconds(20))
            .build();
    }

    public EuropeanHcpIdwsToken exchangeBootstrapToken(BootstrapTokenExchangeRequest request) throws AuthenticationException {
        try {
            var requestXml = XmlUtils.writeDocumentToString(request.soapBody());
            var response = sendRequest(requestXml);
            return parseResponse(response);

        } catch (IOException e) {
            throw new AuthenticationException("SOSI STS request failed", e);
        } catch (TransformerException e) {
            throw new AuthenticationException("Malformed bootstrap token exchange request", e);
        }
    }

    private EuropeanHcpIdwsToken parseResponse(InputStream result) throws AuthenticationException {
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
            var requestSecurityTokenResponse = xpath.evalEl(
                "/soap:Envelope/soap:Body/wst13:RequestSecurityTokenResponseCollection" +
                    "/wst13:RequestSecurityTokenResponse",
                document);

            var assertion = xpath.evalEl("wst13:RequestedSecurityToken/saml:Assertion", requestSecurityTokenResponse);
            var created = xpath.evalString("wst13:Lifetime/wsu:Created", requestSecurityTokenResponse);
            var expires = xpath.evalString("wst13:Lifetime/wsu:Expires", requestSecurityTokenResponse);
            var audience = xpath.evalString("wsp:AppliesTo/wsa:EndpointReference/wsa:Address", requestSecurityTokenResponse);
            return new EuropeanHcpIdwsToken(
                assertion,
                audience,
                Instant.parse(created),
                Instant.parse(expires)
            );
        } catch (IOException e) {
            throw new AuthenticationException("Error reading SOSI STS response", e);
        } catch (XPathExpressionException e) {
            throw new AuthenticationException("Error parsing SOSI STS response", e);
        }
    }

    private InputStream sendRequest(String xml) throws IOException, AuthenticationException {
        var httpRequest = HttpRequest.newBuilder()
            .uri(serviceUri)
            .POST(HttpRequest.BodyPublishers.ofString(xml))
            .header("Content-Type", "text/xml; charset=utf-8")
            .headers("SOAPAction", "\"Issue\"")
            .build();

        try {
            log.debug("Sending SOAP request to SOSI STS at {}", serviceUri);
            var result = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofInputStream());
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
