package eu.europa.ec.sante.ehdsi.portal.service;

import epsos.openncp.protocolterminator.clientconnector.PatientId;
import eu.europa.ec.sante.ehdsi.portal.model.Concept;
import eu.europa.ec.sante.ehdsi.portal.model.NextOfKinTrait;
import eu.europa.ec.sante.ehdsi.portal.util.AssertionUtil;
import org.apache.commons.lang3.StringUtils;
import org.opensaml.saml.saml2.core.Assertion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SecurityServiceImpl implements SecurityService {

    private final Logger logger = LoggerFactory.getLogger(SecurityServiceImpl.class);

    @Override
    public Assertion generateClinicianToken(String username, String fullName, String email, Concept role,
                                            String organization, String organizationId, String facilityType, String purposeOfUse,
                                            String locality, List<String> permissions, String onBehalf) {

        logger.info("[Security Service] Generate HCP Assertion");
        Assertion assertion = AssertionUtil.createHCPAssertion(username, fullName, email, role, organization, organizationId,
                facilityType, purposeOfUse, locality, permissions, null);
        //EventLog.createEventLogNOKA();
        return assertion;
    }

    @Override
    public Assertion generateNextOfKinToken(Assertion clinicianAssertion, NextOfKinTrait nextOfKinTrait) {
        return AssertionUtil.createNextOfKin(clinicianAssertion, nextOfKinTrait);
    }

    @Override
    public Assertion generateTreatmentToken(Assertion clinicianAssertion, String patientIdentifier, String purposeOfUse) {

        logger.info("[Security Service] Generate TRC Assertion without eP identifiers");
        return generateTreatmentToken(clinicianAssertion, patientIdentifier, purposeOfUse, null, null);
    }

    @Override
    public Assertion generateTreatmentToken(Assertion clinicianAssertion, String patientIdentifier, String purposeOfUse,
                                            String prescriptionId, String dispensePinCode) {

        logger.info("[Security Service] Generate TRC Assertion");
        //"'" + id + "^^^&" + homeCommunityId + "&" + "ISO'")
        String[] ids = StringUtils.split(patientIdentifier, "^^^&");
        PatientId id = PatientId.Factory.newInstance();
        id.setRoot(StringUtils.remove(ids[1], "&ISO"));
        id.setExtension(ids[0]);
        try {
            return AssertionUtil.createPatientConfirmationPlain(clinicianAssertion, id, purposeOfUse, prescriptionId, dispensePinCode);
        } catch (Exception e) {
            logger.error("Error while creating PatientConfirmation Plain", e);
            return null;
        }
    }
}
