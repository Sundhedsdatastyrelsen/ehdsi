package eu.europa.ec.sante.ehdsi.portal.service;

import epsos.openncp.protocolterminator.ClientConnectorConsumer;
import epsos.openncp.protocolterminator.ClientConnectorConsumerException;
import epsos.openncp.protocolterminator.clientconnector.PatientDemographics;
import epsos.openncp.protocolterminator.clientconnector.PatientId;
import eu.europa.ec.sante.ehdsi.constant.assertion.AssertionEnum;
import eu.europa.ec.sante.ehdsi.openncp.configmanager.ConfigurationManagerFactory;
import eu.europa.ec.sante.ehdsi.portal.mock.MockService;
import eu.europa.ec.sante.ehdsi.portal.model.PatientDetail;
import eu.europa.ec.sante.ehdsi.portal.model.PatientIdentityTrait;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.opensaml.saml.saml2.core.Assertion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;
import tr.com.srdc.epsos.util.Constants;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Service
public class PatientServiceImpl extends PortalService implements PatientService {

    private static final ClientConnectorConsumer CLIENT_CONNECTOR;

    static {
        CLIENT_CONNECTOR = new ClientConnectorConsumer(
                ConfigurationManagerFactory.getConfigurationManager().getProperty("PORTAL_CLIENT_CONNECTOR_URL"));
    }

    private final MockService mockService;
    private final Logger logger = LoggerFactory.getLogger(PatientServiceImpl.class);

    public PatientServiceImpl(MockService mockService) {
        this.mockService = mockService;
    }

    private static String createFullId(final String root, final String extension) {
        return extension + "^^^&" + root + "&ISO";
    }

    public ClientConnectorConsumer getClientConnectorConsumer() {
        return CLIENT_CONNECTOR;
    }

    @Override
    public PatientDetail findPatientByTraits(PatientIdentityTrait patientIdentityTrait, String countryCode,
                                             Assertion... assertions) throws ClientConnectorConsumerException {

        logger.info("Server IP: '{}'", Constants.SERVER_IP);
        var stopWatch = new StopWatch();
        stopWatch.start();
        Map<AssertionEnum, Assertion> assertionMap = new EnumMap<>(AssertionEnum.class);
        PatientDemographics demographics = setPatientIdentityTraits(patientIdentityTrait);

        var clinicianAssertion = assertions[0];
        assertionMap.put(AssertionEnum.CLINICIAN, clinicianAssertion);

        if (assertions.length > 1) {
            assertionMap.put(AssertionEnum.NEXT_OF_KIN, assertions[1]);
        }

        if (mockService.isPatientWithNoAssertion(patientIdentityTrait.getLivingSubjectIds().get(0).getExtension())) {
            assertionMap.remove(AssertionEnum.CLINICIAN);
        }

        List<PatientDemographics> demographicsList = CLIENT_CONNECTOR.queryPatient(assertionMap, countryCode, demographics);
        for (PatientDemographics patientDemographics : demographicsList) {

            var patientDetail = new PatientDetail();
            if (assertionMap.containsKey(AssertionEnum.NEXT_OF_KIN)) {
                patientDetail.setNextOfKin(true);
            }
            patientDetail.setRoot(patientDemographics.getPatientIdArray()[0].getRoot());
            patientDetail.setExtension(patientDemographics.getPatientIdArray()[0].getExtension());
            patientDetail.setIdentifier(createFullId(patientDemographics.getPatientIdArray()[0].getRoot(),
                    patientDemographics.getPatientIdArray()[0].getExtension()));
            patientDetail.setFirstName(patientDemographics.getGivenName());
            patientDetail.setFamilyName(patientDemographics.getFamilyName());
            patientDetail.setGender(patientDemographics.getAdministrativeGender());
            patientDetail.setBirthDate(patientDemographics.getBirthDate().getTime());
            patientDetail.setAddressStreet(patientDemographics.getStreetAddress());
            patientDetail.setAddressPostalCode(patientDemographics.getPostalCode());
            patientDetail.setAddressCity(patientDemographics.getCity());
            patientDetail.setAddressCountry(patientDemographics.getCountry());
            patientDetail.setPatientDemographics(patientDemographics.toString());
            stopWatch.stop();
            logger.info("Patient found in {} s", stopWatch.getTotalTimeSeconds());
            return patientDetail;
        }

        return null;
    }

    private PatientDemographics setPatientIdentityTraits(PatientIdentityTrait patientIdentityTrait) {

        PatientDemographics demographics = PatientDemographics.Factory.newInstance();
        var idArray = new PatientId[patientIdentityTrait.getLivingSubjectIds().size()];
        for (var i = 0; i < patientIdentityTrait.getLivingSubjectIds().size(); i++) {
            PatientId id = PatientId.Factory.newInstance();
            id.setRoot(patientIdentityTrait.getLivingSubjectIds().get(i).getRoot());
            id.setExtension(patientIdentityTrait.getLivingSubjectIds().get(i).getExtension());
            idArray[i] = id;
        }
        demographics.setPatientIdArray(idArray);
        if (!StringUtils.isBlank(patientIdentityTrait.getFamilyName())) {
            demographics.setFamilyName(patientIdentityTrait.getFamilyName());
        }
        if (!StringUtils.isBlank(patientIdentityTrait.getFirstName())) {
            demographics.setGivenName(patientIdentityTrait.getFirstName());
        }
        if (!StringUtils.isBlank(patientIdentityTrait.getAddressCity())) {
            demographics.setCity(patientIdentityTrait.getAddressCity());
        }
        if (!StringUtils.isBlank(patientIdentityTrait.getAddressCountry())) {
            demographics.setCountry(patientIdentityTrait.getAddressCountry());
        }
        if (!StringUtils.isBlank(patientIdentityTrait.getAddressPostalCode())) {
            demographics.setPostalCode(patientIdentityTrait.getAddressPostalCode());
        }
        if (!StringUtils.isBlank(patientIdentityTrait.getAddressStreet())) {
            demographics.setStreetAddress(patientIdentityTrait.getAddressStreet());
        }
        if (patientIdentityTrait.getBirthDate() != null) {
            demographics.setBirthDate(DateUtils.toCalendar(patientIdentityTrait.getBirthDate()));
        }
        if (!StringUtils.isBlank(patientIdentityTrait.getGender())) {
            demographics.setAdministrativeGender(patientIdentityTrait.getGender());
        }
        return demographics;
    }
}
