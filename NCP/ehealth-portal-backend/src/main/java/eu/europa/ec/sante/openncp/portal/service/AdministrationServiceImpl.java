package eu.europa.ec.sante.openncp.portal.service;

import eu.europa.ec.dynamicdiscovery.DynamicDiscovery;
import eu.europa.ec.dynamicdiscovery.exception.TechnicalException;
import eu.europa.ec.dynamicdiscovery.model.DocumentIdentifier;
import eu.europa.ec.dynamicdiscovery.model.ParticipantIdentifier;
import eu.europa.ec.sante.openncp.portal.model.SearchMaskResponse;
import eu.europa.ec.sante.openncp.common.configuration.ConfigurationManagerException;
import eu.europa.ec.sante.openncp.common.configuration.ConfigurationManagerFactory;
import eu.europa.ec.sante.openncp.common.configuration.RegisteredService;
import eu.europa.ec.sante.openncp.common.util.XMLUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.xerces.dom.ElementNSImpl;
import org.oasis_open.docs.bdxr.ns.smp._2016._05.EndpointType;
import org.oasis_open.docs.bdxr.ns.smp._2016._05.ExtensionType;
import org.oasis_open.docs.bdxr.ns.smp._2016._05.ProcessType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;

import javax.xml.transform.TransformerException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.IOException;
import java.io.StringWriter;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdministrationServiceImpl implements AdministrationService {

    //  Static constants for SMP identifiers
    private static final String PARTICIPANT_IDENTIFIER_SCHEME = "ehealth-participantid-qns";
    private static final String PARTICIPANT_IDENTIFIER_VALUE = "urn:ehealth:%2s:ncp-idp";
    private static final String DOCUMENT_IDENTIFIER_SCHEME = "ehealth-resid-qns";
    private static final String URN_EHDSI_ISM = "http://ec.europa.eu/sante/ehncp/ism";
    private final Logger logger = LoggerFactory.getLogger(AdministrationServiceImpl.class);

    public String fetchInternationalSearchMask(String countryCode) {

        try {
            var participantIdentifierValue = String.format(PARTICIPANT_IDENTIFIER_VALUE, countryCode);
            logger.info("[Gateway] Querying ISM for participant identifier {}", participantIdentifierValue);
            var participantIdentifier = new ParticipantIdentifier(participantIdentifierValue, PARTICIPANT_IDENTIFIER_SCHEME);
            var documentIdentifier = new DocumentIdentifier(RegisteredService.EHEALTH_107.getUrn(), DOCUMENT_IDENTIFIER_SCHEME);
            DynamicDiscovery smpClient = DynamicDiscoveryClient.getInstance();
            var serviceMetadata = smpClient.getServiceMetadata(participantIdentifier, documentIdentifier);

            List<ProcessType> processTypes = serviceMetadata.getOriginalServiceMetadata().getServiceMetadata()
                    .getServiceInformation().getProcessList().getProcess();

            if (!processTypes.isEmpty()) {

                List<EndpointType> endpointTypes = processTypes.get(0).getServiceEndpointList().getEndpoint();
                if (!endpointTypes.isEmpty()) {

                    List<ExtensionType> extensionTypes = endpointTypes.get(0).getExtension();
                    if (!extensionTypes.isEmpty()) {

                        Document document = ((ElementNSImpl) extensionTypes.get(0).getAny()).getOwnerDocument();
                        DOMSource source = new DOMSource(document.getElementsByTagNameNS(URN_EHDSI_ISM, "searchFields").item(0));
                        //String outPath = APPLICATION_BASE_DIR + "InternationalSearch_" + StringUtils.upperCase(countryCode) + ".xml";
//                        if (logger.isDebugEnabled()) {
//                            logger.debug("International Search Mask Path: '{}", outPath);
//                        }
                        var stringWriter = new StringWriter();
                        StreamResult streamResult = new StreamResult(stringWriter);
                        XMLUtil.transformDocument(source, streamResult);
                        return stringWriter.toString();
                    }
                }
            }
            //  Audit variables
//            URI smpURI = smpClient.getService().getMetadataLocator().lookup(participantIdentifier);
//            URI serviceMetadataUri = smpClient.getService().getMetadataProvider().resolveServiceMetadata(smpURI, participantIdentifier, documentIdentifier);
//            byte[] encodedObjectID = Base64.encodeBase64(serviceMetadataUri.toASCIIString().getBytes());
//            if (logger.isInfoEnabled()) {
//                logger.info("[Gateway] SMP Query: '{}'", serviceMetadataUri.toASCIIString());
//            }
//            AuditManager.handleDynamicDiscoveryQuery(smpURI.toASCIIString(), new String(encodedObjectID), null, null);

        } catch (IOException | CertificateException | KeyStoreException | TechnicalException | TransformerException | NoSuchAlgorithmException e) {
            //TODO: [Specification] Analyze if an audit message is required in case of error.
            throw new ConfigurationManagerException("An internal error occurred while retrieving the International Search Mask from " + countryCode, e);
        }
        return StringUtils.EMPTY;
    }

    @Override
    public List<SearchMaskResponse> fetchAllInternationalSearchMask() {

        String countryList = ConfigurationManagerFactory.getConfigurationManager().getProperty("ncp.countries");
        logger.info("countryList : " + countryList);
        String[] countries = StringUtils.split(countryList, ",");
        logger.info("countries : " + String.join(",", countries));
        countries = StringUtils.stripAll(countries);
        List<SearchMaskResponse> synchronizedCountry = new ArrayList<>();

        for (String countryCode : countries) {
            try {
                String form = fetchInternationalSearchMask(countryCode);
                SearchMaskResponse searchMaskResponse = new SearchMaskResponse();
                searchMaskResponse.setCountryCode(countryCode);
                searchMaskResponse.setForm(form);
                synchronizedCountry.add(searchMaskResponse);

            } catch (ConfigurationManagerException e) {
                logger.error(String.format("ConfigurationManagerException: '%s'", e.getMessage()), e);
                SearchMaskResponse searchMaskResponse = new SearchMaskResponse();
                searchMaskResponse.setCountryCode(countryCode);
                searchMaskResponse.setForm(e.getLocalizedMessage() + " " + e.getMessage());
                synchronizedCountry.add(searchMaskResponse);
            }
        }
        return synchronizedCountry;
    }
}
