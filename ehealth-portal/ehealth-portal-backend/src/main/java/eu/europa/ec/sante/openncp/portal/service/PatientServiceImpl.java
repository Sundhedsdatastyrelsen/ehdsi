package eu.europa.ec.sante.openncp.portal.service;

import eu.europa.ec.sante.openncp.portal.mock.MockService;
import eu.europa.ec.sante.openncp.portal.model.LivingSubjectId;
import eu.europa.ec.sante.openncp.portal.model.PatientDetail;
import eu.europa.ec.sante.openncp.portal.model.PatientIdentityTrait;
import eu.europa.ec.sante.openncp.application.client.connector.ClientConnectorException;
import eu.europa.ec.sante.openncp.application.client.connector.ClientConnectorService;
import eu.europa.ec.sante.openncp.common.configuration.util.Constants;
import eu.europa.ec.sante.openncp.common.util.DateUtil;
import eu.europa.ec.sante.openncp.core.client.ObjectFactory;
import eu.europa.ec.sante.openncp.core.client.PatientDemographics;
import eu.europa.ec.sante.openncp.core.client.PatientId;
import eu.europa.ec.sante.openncp.core.common.ihe.assertionvalidator.constants.AssertionEnum;
import org.apache.commons.lang3.StringUtils;
import org.opensaml.saml.saml2.core.Assertion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Service
public class PatientServiceImpl extends PortalService implements PatientService {

    private final MockService mockService;
    private final ClientConnectorService clientConnectorService;

    private ObjectFactory objectFactory = new ObjectFactory();

    private final Logger logger = LoggerFactory.getLogger(PatientServiceImpl.class);

    public PatientServiceImpl(final MockService mockService, final ClientConnectorService clientConnectorService) {
        this.mockService = mockService;
        this.clientConnectorService = clientConnectorService;
    }

    private static String createFullId(final String root, final String extension) {
        return extension + "^^^&" + root + "&ISO";
    }

    @Override
    public PatientDetail findPatientByTraits(PatientIdentityTrait patientIdentityTrait, String countryCode,
                                             Assertion... assertions) throws ClientConnectorException {

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

        List<PatientDemographics> demographicsList = clientConnectorService.queryPatient(assertionMap, countryCode, demographics);
        for (PatientDemographics patientDemographics : demographicsList) {

            var patientDetail = new PatientDetail();
            if (assertionMap.containsKey(AssertionEnum.NEXT_OF_KIN)) {
                patientDetail.setNextOfKin(true);
            }
            patientDetail.setRoot(patientDemographics.getPatientId().get(0).getRoot());
            patientDetail.setExtension(patientDemographics.getPatientId().get(0).getExtension());
            patientDetail.setIdentifier(createFullId(patientDemographics.getPatientId().get(0).getRoot(),
                    patientDemographics.getPatientId().get(0).getExtension()));
            patientDetail.setFirstName(patientDemographics.getGivenName());
            patientDetail.setFamilyName(patientDemographics.getFamilyName());
            patientDetail.setGender(patientDemographics.getAdministrativeGender());
            patientDetail.setBirthDate(patientDemographics.getBirthDate().getTime());
            patientDetail.setAddressStreet(patientDemographics.getStreetAddress());
            patientDetail.setAddressPostalCode(patientDemographics.getPostalCode());
            patientDetail.setAddressCity(patientDemographics.getCity());
            patientDetail.setAddressCountry(patientDemographics.getCountry());
            patientDetail.setPatientDemographics(patientDemographics);
            stopWatch.stop();
            logger.info("Patient found in {} s", stopWatch.getTotalTimeSeconds());
            return patientDetail;
        }

        return null;
    }

    private PatientDemographics setPatientIdentityTraits(PatientIdentityTrait patientIdentityTrait) {

        PatientDemographics demographics = objectFactory.createPatientDemographics();
        var idArray = new PatientId[patientIdentityTrait.getLivingSubjectIds().size()];
        for (LivingSubjectId patientId : patientIdentityTrait.getLivingSubjectIds()) {
            PatientId id = objectFactory.createPatientId();
            id.setRoot(patientId.getRoot());
            id.setExtension(patientId.getExtension());
            demographics.getPatientId().add(id);
        }

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
            demographics.setBirthDate(DateUtil.toCalendar(patientIdentityTrait.getBirthDate()));
        }
        if (!StringUtils.isBlank(patientIdentityTrait.getGender())) {
            demographics.setAdministrativeGender(patientIdentityTrait.getGender());
        }
        return demographics;
    }
}
