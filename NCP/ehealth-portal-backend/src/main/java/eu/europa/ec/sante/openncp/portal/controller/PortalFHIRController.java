package eu.europa.ec.sante.openncp.portal.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import eu.europa.ec.sante.openncp.portal.configuration.ApplicationProperties;
import eu.europa.ec.sante.openncp.portal.mock.MockService;
import net.shibboleth.utilities.java.support.xml.SerializeSupport;
import org.apache.commons.lang.time.DateUtils;
import org.apache.http.client.utils.URIBuilder;
import org.opensaml.saml.saml2.core.Assertion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.PathParam;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.Enumeration;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/fhir")
public class PortalFHIRController {


    private final Logger logger = LoggerFactory.getLogger(PortalFHIRController.class);

    private String externalApiUrl;

    private final String PATIENT_ENDPOINT="Patient";

    private final String DOCUMENT_REFERENCE_ENDPOINT="DocumentReference";

    private final String COMPOSITION_ENDPOINT="Composition";

    private final String BUNDLE_ENDPOINT="Bundle";

    private final String DIAGNOSTIC_REPORT_ENDPOINT="DiagnosticReport";

    private String secret;

    private final RestTemplate restTemplate;

    private final MockService mockService;

    private final ApplicationProperties applicationProperties;

    public PortalFHIRController(RestTemplate restTemplate, MockService mockService, ApplicationProperties applicationProperties) {
        this.restTemplate = restTemplate;
        this.mockService = mockService;
        this.applicationProperties = applicationProperties;
    }

    @PostConstruct
    public void init() {
        secret = applicationProperties.getSecurity().getJwt().getSecret();
        externalApiUrl = applicationProperties.getApi().getExternalUrl();
    }

    @GetMapping("/Patient")
    public ResponseEntity<?> proxyPatientRequest(HttpServletRequest request,
                                                 @RequestParam Map<String, String> params) {
        return proxyRequest(request, params, PATIENT_ENDPOINT);
    }

    @GetMapping("/Bundle")
    public ResponseEntity<?> proxyBundleRequest(HttpServletRequest request,  @RequestParam Map<String, String> params) {
        return proxyRequest(request, params, BUNDLE_ENDPOINT);
    }

    @GetMapping("/Bundle/{id}")
    public ResponseEntity<?> proxyBundleRequest(HttpServletRequest request, @PathVariable("id") final String id) {
        return proxyRequest(request, id, BUNDLE_ENDPOINT);
    }

    @GetMapping("/Composition")
    public ResponseEntity<?> proxyCompositionRequest(HttpServletRequest request,
                                                     @RequestParam Map<String, String> params) {
        return proxyRequest(request, params, COMPOSITION_ENDPOINT);
    }

    @GetMapping("/DocumentReference")
    public ResponseEntity<?> proxyDocumentReferenceRequest(HttpServletRequest request,
                                                           @RequestParam Map<String, String> params) {
        ResponseEntity<?> responseEntity = proxyRequest(request, params, DOCUMENT_REFERENCE_ENDPOINT);
        String body = (String) responseEntity.getBody();
        body = body.replaceAll("/openncp-client-connector", request.getContextPath());
        return ResponseEntity.status(responseEntity.getStatusCode()).body(body);
    }

    @GetMapping("/DiagnosticReport")
    public ResponseEntity<?> proxyDiagnosticReportRequest(HttpServletRequest request,
                                                          @RequestParam Map<String, String> params) {
        return proxyRequest(request, params, DIAGNOSTIC_REPORT_ENDPOINT);
    }

    public ResponseEntity<?> proxyRequest(HttpServletRequest request,
                                          Map<String, String> params,
                                          String endpoint){

        // Create headers and add JWT token
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + generateJwtToken());

        // Copy original request headers
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            headers.set(headerName, request.getHeader(headerName));
        }

        // Create new HttpEntity
        HttpEntity<Map<String, Object>> newRequest;
        newRequest = new HttpEntity<>(headers);

        logger.info("Requesting Patient data from external API");
        logger.info(newRequest.toString());

        // Construct new URL with parameters
        String urlWithParams = externalApiUrl + endpoint + "?" + params.entrySet()
                .stream()
                .map(entry -> entry.getKey() + "=" + entry.getValue())
                .reduce((param1, param2) -> param1 + "&" + param2)
                .orElse("");

        // Send new request
        ResponseEntity<String> response = restTemplate.exchange(urlWithParams, HttpMethod.GET, newRequest, String.class);

        return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
    }

    public ResponseEntity<?> proxyRequest(HttpServletRequest request,
                                          final String id,
                                          String endpoint){

        // Create headers and add JWT token
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + generateJwtToken());

        // Copy original request headers
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            headers.set(headerName, request.getHeader(headerName));
        }

        // Create new HttpEntity
        HttpEntity<Map<String, Object>> newRequest;
        newRequest = new HttpEntity<>(headers);

        logger.info("Requesting Patient data from external API");
        logger.info(newRequest.toString());

        // Construct new URL with parameters
        String urlWithParams = externalApiUrl + endpoint + "/" + id;

        // Send new request
        ResponseEntity<String> response = restTemplate.exchange(urlWithParams, HttpMethod.GET, newRequest, String.class);

        return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
    }

    private String generateJwtToken() {
        // Implement JWT token generation logic
        Assertion assertion  =mockService.generateClinicianToken();
        if(assertion == null){
            throw new RuntimeException("Error generating SAML assertion");
        }
        String saml = SerializeSupport.prettyPrintXML(assertion.getDOM());

        Base64.Encoder encoder = Base64.getEncoder();

        return JWT.create()
                .withSubject("user")
                .withClaim("saml", encoder.encodeToString(saml.getBytes()))
                .withExpiresAt(DateUtils.addHours(new Date(), 24))
                .sign(Algorithm.HMAC512(secret));
    }
}
