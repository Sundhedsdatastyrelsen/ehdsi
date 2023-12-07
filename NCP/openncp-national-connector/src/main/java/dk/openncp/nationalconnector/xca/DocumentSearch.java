package dk.openncp.nationalconnector.xca;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import eu.epsos.protocolterminators.ws.server.common.NationalConnectorInterface;
import eu.epsos.protocolterminators.ws.server.exception.NIException;
import eu.epsos.protocolterminators.ws.server.xca.DocumentSearchInterface;
import eu.europa.ec.sante.ehdsi.constant.ClassCode;
import eu.europa.ec.sante.ehdsi.constant.error.OpenNCPErrorCode;
import eu.europa.ec.sante.ehdsi.openncp.assertionvalidator.exceptions.InsufficientRightsException;
import fi.kela.se.epsos.data.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;
import tr.com.srdc.epsos.util.XMLUtil;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class DocumentSearch implements NationalConnectorInterface, DocumentSearchInterface {
    private static final Logger logger = LoggerFactory.getLogger(DocumentSearch.class);

    /**
     * For use when logging clinical and personal data
     */
    private static final Logger loggerClinical = LoggerFactory.getLogger("LOGGER_CLINICAL");

    private Element soapHeader;

    @Override
    public DocumentAssociation<PSDocumentMetaData> getPSDocumentList(SearchCriteria searchCriteria) throws NIException, InsufficientRightsException {
        throw new UnsupportedOperationException();
    }

    @Override
    public DocumentAssociation<MroDocumentMetaData> getMroDocumentList(SearchCriteria searchCriteria) throws NIException, InsufficientRightsException {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<DocumentAssociation<EPDocumentMetaData>> getEPDocumentList(SearchCriteria searchCriteria) throws NIException, InsufficientRightsException {
        logger.info("Hit getEPDocumentList endpoint");
        throw new UnsupportedOperationException();
    }

    @Override
    public List<OrCDDocumentMetaData> getOrCDLaboratoryResultsDocumentList(SearchCriteria searchCriteria) throws NIException, InsufficientRightsException {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<OrCDDocumentMetaData> getOrCDHospitalDischargeReportsDocumentList(SearchCriteria searchCriteria) throws NIException, InsufficientRightsException {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<OrCDDocumentMetaData> getOrCDMedicalImagingReportsDocumentList(SearchCriteria searchCriteria) throws NIException, InsufficientRightsException {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<OrCDDocumentMetaData> getOrCDMedicalImagesDocumentList(SearchCriteria searchCriteria) throws NIException, InsufficientRightsException {
        throw new UnsupportedOperationException();
    }

    private static String countryAHost = System.getenv().getOrDefault("NC_DK_COUNTRY_A_HOST", "http://localhost:8180");

    private static boolean isValidDocResponse(JsonNode doc) {
        return Stream.of("patientId", "classCode", "document")
                .allMatch(field -> doc.hasNonNull(field) && doc.get(field).isTextual());
    }

    private static String lowercaseFirstLetter(String s) {
        return Character.toLowerCase(s.charAt(0)) + s.substring(1);
    }

    private static String queryString(SearchCriteria searchCriteria) {
        return Arrays.stream(SearchCriteria.Criteria.values())
                .flatMap(c -> {
                    var criteriaValue = searchCriteria.getCriteriaValue(c);
                    if (criteriaValue != null) {
                        return Stream.of(lowercaseFirstLetter(c.value) + "=" + URLEncoder.encode(criteriaValue, StandardCharsets.UTF_8));
                    }
                    return Stream.empty();
                })
                .reduce((p1, p2) -> p1 + "&" + p2)
                .orElse("");
    }
    private static EPSOSDocument getDocumentFromService(SearchCriteria searchCriteria) throws NIException {
        try {
            var uri = new URI(countryAHost + "/api/documents/?" + queryString(searchCriteria));

            var request = HttpRequest.newBuilder(uri).GET().build();
            var response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 400) {
                return null;
            }
            if (response.statusCode() != 200) {
                throw new NIException(OpenNCPErrorCode.ERROR_GENERIC, String.format("Bad response from Country A service, status: %s, body: %s",
                        response.statusCode(),
                        response.body()
                ));
            }
            var body = new ObjectMapper().readTree(response.body());

            var doc = body.get(0);
            if (doc == null) {
                throw new NIException(OpenNCPErrorCode.ERROR_DOCUMENT_NOT_FOUND, "Not found");
            }
            if (!isValidDocResponse(doc)) {
                throw new NIException(OpenNCPErrorCode.ERROR_GENERIC, "Malformed response from Country A service");
            }

            logger.info("Received document from Country A service");
            return DocumentFactory.createEPSOSDocument(
                    doc.get("patientId").asText(),
                    ClassCode.getByCode(doc.get("classCode").asText()),
                    XMLUtil.parseContent(doc.get("document").asText())
            );
        } catch (URISyntaxException  | InterruptedException | ParserConfigurationException | SAXException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new NIException(OpenNCPErrorCode.ERROR_GENERIC, "Could not reach Country A service");
        }
    }

    @Override
    public EPSOSDocument getDocument(SearchCriteria searchCriteria) throws NIException, InsufficientRightsException {
        logger.info("Retrieving document from Country A service...");
        return getDocumentFromService(searchCriteria);
    }

    @Override
    public void setSOAPHeader(Element soapHeader) {
        this.soapHeader = soapHeader;
    }

    /**
     * For testing/debugging in IDE
     */
    public static void main(String[] args) throws NIException, InsufficientRightsException {
        var sc = new SearchCriteriaImpl();
        sc.addPatientId("DKCPR^^^1234567890");
        sc.add(SearchCriteria.Criteria.DOCUMENT_ID, "21298478");
        getDocumentFromService(sc);
    }
}
