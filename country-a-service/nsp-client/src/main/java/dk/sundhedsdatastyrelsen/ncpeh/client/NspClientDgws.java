package dk.sundhedsdatastyrelsen.ncpeh.client;

import dk.sundhedsdatastyrelsen.ncpeh.authentication.AuthenticationException;
import dk.sundhedsdatastyrelsen.ncpeh.authentication.AuthenticationService;
import dk.sundhedsdatastyrelsen.ncpeh.authentication.NspDgwsIdentity;
import dk.sundhedsdatastyrelsen.ncpeh.authentication.XmlNamespace;
import dk.sundhedsdatastyrelsen.ncpeh.base.utils.XPathWrapper;
import dk.sundhedsdatastyrelsen.ncpeh.base.utils.XmlUtils;
import org.slf4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/// Some Nsp services require us to use our system's identity instead of a foreign healthcare professional. These
/// calls are made through this class.
///
/// It also serves as a temporary place to call Nsp services that can't yet understand IDWS tokens, for use in
/// development and in testing, eg. to create prescriptions, which foreign HPs can't.
public class NspClientDgws {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(NspClientDgws.class);
    private static final XPathWrapper xpath = new XPathWrapper(XmlNamespace.SOAP, XmlNamespace.WST);

    private NspClientDgws() {}

    /// Send a SOAP request to an NSP service.
    public static Element request(URI uri, Element soapBody, String soapAction, NspDgwsIdentity caller, Element... extraHeaders) {
        try {
            var idCard = AuthenticationService.nspDgwsIdentityToAssertion(caller);
            var envelope = makeRequest(
                soapBody, idCard.assertion(), Instant.now()
                    .truncatedTo(ChronoUnit.SECONDS), extraHeaders);
            var requestString = XmlUtils.writeDocumentToString(envelope);
            try (var httpClient = HttpClient.newBuilder().version(HttpClient.Version.HTTP_1_1).build()) {
                var request = HttpRequest.newBuilder(uri)
                    .header("Content-Type", "text/xml;charset=utf-8")
                    .header("SOAPAction", soapAction)
                    .POST(HttpRequest.BodyPublishers.ofString(requestString))
                    .build();
                var res = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
                var fullText = res.body();

                // Check if response is MIME multipart. Some services (DDV is one) return as multipart.
                if (fullText.contains("--uuid:")) {
                    fullText = extractSoapFromMime(fullText);
                }

                var responseDoc = XmlUtils.parse(fullText);
                if (res.statusCode() >= 400) {
                    throw new NspClientException(String.format("Request failed with message: %s", xpath.evalString("/soap:Envelope/soap:Body/soap:Fault/faultstring", responseDoc)));
                }
                return xpath.evalElement("/soap:Envelope/soap:Body/*[1]", responseDoc);
            }
        } catch (IOException | XPathExpressionException | TransformerException | AuthenticationException e) {
            throw new NspClientException("Nsp call failed.", e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new NspClientException("Nsp call failed, thread interrupted.", e);
        }
    }

    /// Extract SOAP message from MIME multipart response.
    private static String extractSoapFromMime(String mimeResponse) {
        // Find the boundary marker
        Pattern boundaryPattern = Pattern.compile("--(uuid:[^\\r\\n]+)");
        Matcher boundaryMatcher = boundaryPattern.matcher(mimeResponse);
        if (!boundaryMatcher.find()) {
            throw new NspClientException("Could not find MIME boundary in response");
        }
        String boundary = boundaryMatcher.group(1);

        // Extract content between boundaries
        Pattern contentPattern = Pattern.compile(
            "--" + Pattern.quote(boundary) + "\\r?\\n" +
                "([\\s\\S]*?)" +
                "--" + Pattern.quote(boundary) + "--",
            Pattern.MULTILINE
        );

        Matcher contentMatcher = contentPattern.matcher(mimeResponse);
        if (contentMatcher.find()) {
            String part = contentMatcher.group(1);
            // Skip headers and get content after double newline
            int contentStart = part.indexOf("\r\n\r\n");
            if (contentStart == -1) {
                contentStart = part.indexOf("\n\n");
            }
            if (contentStart == -1) {
                throw new NspClientException("Could not find content in MIME part");
            }
            return part.substring(contentStart + 2).trim();
        }

        throw new NspClientException("Could not extract content from MIME response");
    }

    /// Make the document that should be sent to a DGWS NSP webservice.
    ///
    /// See seal-to-cpr-organization-request.xml in the test resources to see what this should end up looking like.
    private static Document makeRequest(Element soapBody, Element idCardAssertion, Instant now, Element[] extraHeaders) {
        // Structure

        var requestDocument = XmlUtils.newDocument();
        var envelope = XmlUtils.appendChild(requestDocument, XmlNamespace.SOAP, "Envelope");
        // The CPR service at least requires MEDCOM_LEGACY.
        XmlUtils.declareNamespaces(
            envelope, XmlNamespace.SOAP, XmlNamespace.DS, XmlNamespace.MEDCOM_LEGACY, XmlNamespace.SAML, XmlNamespace.SOSI,
            XmlNamespace.WSSE, XmlNamespace.WSU);
        var header = XmlUtils.appendChild(envelope, XmlNamespace.SOAP, "Header");
        var body = XmlUtils.appendChild(envelope, XmlNamespace.SOAP, "Body");

        // Add the passed in body.
        body.appendChild(requestDocument.importNode(soapBody, true));

        // Header
        // Security

        var security = XmlUtils.appendChild(header, XmlNamespace.WSSE, "Security");

        var ts = XmlUtils.appendChild(security, XmlNamespace.WSU, "Timestamp");
        XmlUtils.appendChild(ts, XmlNamespace.WSU, "Created", DateTimeFormatter.ISO_INSTANT.format(now));

        security.appendChild(requestDocument.importNode(idCardAssertion, true));

        // Add the medcom header
        var medcomHeader = XmlUtils.appendChild(header, XmlNamespace.MEDCOM_LEGACY, "Header");
        // In some requests from seal, this was 4 - specifically person-requests to CPR. It doesn't seem to matter.
        XmlUtils.appendChild(medcomHeader, XmlNamespace.MEDCOM_LEGACY, "SecurityLevel", "3");
        var medcomLinking = XmlUtils.appendChild(medcomHeader, XmlNamespace.MEDCOM_LEGACY, "Linking");
        var flowId = UUID.randomUUID();
        var messageId = UUID.randomUUID();
        XmlUtils.appendChild(medcomLinking, XmlNamespace.MEDCOM_LEGACY, "FlowID", messageId.toString());
        XmlUtils.appendChild(medcomLinking, XmlNamespace.MEDCOM_LEGACY, "MessageID", flowId.toString());
        XmlUtils.appendChild(medcomHeader, XmlNamespace.MEDCOM_LEGACY, "RequireNonRepudiationReceipt", "no");

        // Add any extra headers
        Arrays.stream(extraHeaders).map(eh -> requestDocument.importNode(eh, true)).forEach(header::appendChild);

        return requestDocument;
    }
}
