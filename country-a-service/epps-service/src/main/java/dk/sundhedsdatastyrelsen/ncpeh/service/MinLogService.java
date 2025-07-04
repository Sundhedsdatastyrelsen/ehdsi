package dk.sundhedsdatastyrelsen.ncpeh.service;

import dk.nsp.test.idp.model.OrganizationIdentity;
import dk.sundhedsdatastyrelsen.minlog.xml_schema._2025._03._12.minlog2_registration.DestinationForEntryForRegistrationType;
import dk.sundhedsdatastyrelsen.minlog.xml_schema._2025._03._12.minlog2_registration.LogDataEntryForRegistrationType;
import dk.sundhedsdatastyrelsen.minlog.xml_schema._2025._03._12.minlog2_registration.PersonIdSourceType;
import dk.sundhedsdatastyrelsen.minlog.xml_schema._2025._03._12.minlog2_registration.RegistrationRequestType;
import dk.sundhedsdatastyrelsen.minlog.xml_schema._2025._03._12.minlog2_registration.SourceForEntryType;
import dk.sundhedsdatastyrelsen.minlog.xml_schema._2025._03._12.minlog2_registration.UserPersonIdSourceType;
import dk.sundhedsdatastyrelsen.ncpeh.client.EuropeanHealthcareProfessional;
import dk.sundhedsdatastyrelsen.ncpeh.client.MinLogClient;
import jakarta.xml.bind.JAXBException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;

@Slf4j
@Service
public class MinLogService {
    private final MinLogClient minLogClient;
    /**
     * The system (FOCES/VOCES) which makes the service request, i.e., certificates representing the NCPeH DK.
     * This is not the same as the parent request caller, i.e., the foreign healthcare professional.
     */
    private final OrganizationIdentity systemCaller;

    public MinLogService(MinLogClient minLogClient, OrganizationIdentity systemCaller) {
        this.minLogClient = minLogClient;
        this.systemCaller = systemCaller;
    }

    public void logEventOnPatient(
        String cpr,
        String eventText,
        EuropeanHealthcareProfessional europeanHealthcareProfessional
    ) {
        var source = SourceForEntryType.builder().withSystemName("eHDSI").withSource().withSystemName("eHDSI").build();
        var destination = DestinationForEntryForRegistrationType.builder()
            .withSystemName("TestSystem")
            .withActivity(eventText)
            .withDateTime(Utils.xmlGregorianCalendar(ZonedDateTime.now()))
            .withPersonIdentifier()
            .withSource(PersonIdSourceType.CPR)
            .withValue(cpr)
            .end()
            .withUserPersonName("TODO") // TODO include name which must be max 50 chars long (bytes? unicode code points?)
            .withUserPersonIdentifier()
            .withSource(UserPersonIdSourceType.EUROPEAN_HEALTHCARE_PROFESSIONAL)
            .withValue(europeanHealthcareProfessional.europeanHealthcareProfessionalId()) // TODO react to id's exceeding the 200 char limit
            .end()
            .build();
        var registration = RegistrationRequestType.builder()
            .addLogDataEntry(LogDataEntryForRegistrationType.builder()
                .withSource(source)
                .withDestination(destination)
                .build())
            .build();

        try {
            minLogClient.register(registration, systemCaller);
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }
}
