package eu.europa.ec.sante.openncp.portal.mock;

import eu.europa.ec.sante.openncp.portal.model.Concept;
import eu.europa.ec.sante.openncp.portal.service.SecurityService;
import org.opensaml.saml.saml2.core.Assertion;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MockService {

    private final SecurityService securityService;

    boolean mockEnabled = true;

    public MockService(SecurityService securityService) {
        this.securityService = securityService;
    }


    public boolean isMockEnabled() {
        return mockEnabled;
    }


    public boolean isPatientWithNoAssertion(String patientID) {
        return mockEnabled && patientID.equals("NO-SAML-ASSERTION");
    }

    public Assertion generateClinicianToken() {
        if (mockEnabled) {
            List<String> permissions = new ArrayList<>();
            permissions.add("urn:oasis:names:tc:xspa:1.0:subject:hl7:permission:PRD-003");
            permissions.add("urn:oasis:names:tc:xspa:1.0:subject:hl7:permission:PRD-004");
            permissions.add("urn:oasis:names:tc:xspa:1.0:subject:hl7:permission:PRD-005");
            permissions.add("urn:oasis:names:tc:xspa:1.0:subject:hl7:permission:PRD-006");
            permissions.add("urn:oasis:names:tc:xspa:1.0:subject:hl7:permission:PRD-010");
            permissions.add("urn:oasis:names:tc:xspa:1.0:subject:hl7:permission:PRD-016");
            permissions.add("urn:oasis:names:tc:xspa:1.0:subject:hl7:permission:PPD-032");
            permissions.add("urn:oasis:names:tc:xspa:1.0:subject:hl7:permission:PPD-033");
            permissions.add("urn:oasis:names:tc:xspa:1.0:subject:hl7:permission:PPD-046");

            Concept conceptRole = new Concept();
            conceptRole.setCode("221");
            conceptRole.setCodeSystemId("2.16.840.1.113883.2.9.6.2.7");
            conceptRole.setCodeSystemName("ISCO");
            conceptRole.setDisplayName("Medical Doctors");

            return securityService.generateClinicianToken("Doctor House", "John House",
                    "house@ehdsi.eu", conceptRole,
                    "eHealth OpenNCP EU Portal", "urn:hl7ii:1.2.3.4:ABCD", "Resident Physician",
                    "TREATMENT", "eHDSI EU Testing MedCare Center", permissions, null);
        }

        return null;
    }


}
