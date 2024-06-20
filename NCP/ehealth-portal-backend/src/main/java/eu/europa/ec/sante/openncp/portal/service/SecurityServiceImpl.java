package eu.europa.ec.sante.openncp.portal.service;

import eu.europa.ec.sante.openncp.portal.model.Concept;
import eu.europa.ec.sante.openncp.portal.model.NextOfKinTrait;
import eu.europa.ec.sante.openncp.portal.util.AssertionUtil;
import eu.europa.ec.sante.openncp.application.client.connector.assertion.AssertionRequest;
import eu.europa.ec.sante.openncp.application.client.connector.assertion.AssertionService;
import eu.europa.ec.sante.openncp.core.client.ObjectFactory;
import eu.europa.ec.sante.openncp.core.client.PatientId;
import eu.europa.ec.sante.openncp.common.security.key.KeyStoreManager;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.saml.saml2.core.Assertion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SecurityServiceImpl implements SecurityService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SecurityServiceImpl.class);

    private final ObjectFactory objectFactory = new ObjectFactory();

    private final AssertionService assertionService;
    private final KeyStoreManager keyStoreManager;

    public SecurityServiceImpl(AssertionService assertionService, KeyStoreManager keyStoreManager) {
        this.assertionService = Validate.notNull(assertionService);
        this.keyStoreManager = keyStoreManager;
    }

    @Override
    public Assertion generateClinicianToken(String username, String fullName, String email, Concept role,
                                            String organization, String organizationId, String facilityType, String purposeOfUse,
                                            String locality, List<String> permissions, String onBehalf) {

        LOGGER.info("[Security Service] Generate HCP Assertion");
        Assertion assertion = AssertionUtil.createHCPAssertion(keyStoreManager, username, fullName, email, role, organization, organizationId,
                facilityType, purposeOfUse, locality, permissions, null);
        try {
            final String documentAsXml = AssertionUtil.getDocumentAsXml(assertion, false);
            LOGGER.info("TRC Assertion: '{}'\n'{}'", assertion.getID(), documentAsXml);
        } catch (MarshallingException e) {
            throw new RuntimeException("Error while marshalling the HCP Assertion", e);
        }
        //EventLog.createEventLogNOKA();

        return assertion;
    }

    @Override
    public Assertion generateNextOfKinToken(Assertion clinicianAssertion, NextOfKinTrait nextOfKinTrait) {
        return AssertionUtil.createNextOfKin(clinicianAssertion, nextOfKinTrait);
    }

    @Override
    public Assertion generateTreatmentToken(Assertion clinicianAssertion, String patientIdentifier, String purposeOfUse) {

        LOGGER.info("[Security Service] Generate TRC Assertion without eP identifiers");
        return generateTreatmentToken(clinicianAssertion, patientIdentifier, purposeOfUse, null, null);
    }

    @Override
    public Assertion generateTreatmentToken(Assertion clinicianAssertion, String patientIdentifier, String purposeOfUse,
                                            String prescriptionId, String dispensePinCode) {

        LOGGER.info("[Security Service] Generate TRC Assertion");
        //"'" + id + "^^^&" + homeCommunityId + "&" + "ISO'")
        String[] ids = StringUtils.split(patientIdentifier, "^^^&");
        PatientId id = objectFactory.createPatientId();
        id.setRoot(StringUtils.remove(ids[1], "&ISO"));
        id.setExtension(ids[0]);
        try {
            AssertionRequest assertionRequest = AssertionUtil.createPatientConfirmationPlain(clinicianAssertion, id, purposeOfUse, prescriptionId, dispensePinCode);
            Assertion assertion = assertionService.request(assertionRequest);
            String documentAsXml = AssertionUtil.getDocumentAsXml(assertion, false);
            LOGGER.info("TRC Assertion: '{}'\n'{}'", assertion.getID(), documentAsXml);
            return assertion;

        } catch (Exception e) {
            throw new RuntimeException("Error while creating TRC Assertion", e);
        }
    }
}
