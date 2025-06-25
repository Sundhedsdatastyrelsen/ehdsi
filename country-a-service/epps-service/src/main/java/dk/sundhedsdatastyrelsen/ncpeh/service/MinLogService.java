package dk.sundhedsdatastyrelsen.ncpeh.service;

import dk.nsp.test.idp.model.Identity;
import dk.sundhedsdatastyrelsen.minlog.xml_schema._2023._04._25.minlog2_registration.DestinationForEntryForRegistrationType;
import dk.sundhedsdatastyrelsen.minlog.xml_schema._2023._04._25.minlog2_registration.LogDataEntryForRegistrationType;
import dk.sundhedsdatastyrelsen.minlog.xml_schema._2023._04._25.minlog2_registration.PersonIdSourceType;
import dk.sundhedsdatastyrelsen.minlog.xml_schema._2023._04._25.minlog2_registration.RegistrationRequestType;
import dk.sundhedsdatastyrelsen.minlog.xml_schema._2023._04._25.minlog2_registration.SourceForEntryType;
import dk.sundhedsdatastyrelsen.minlog.xml_schema._2023._04._25.minlog2_registration.UserPersonIdSourceType;
import dk.sundhedsdatastyrelsen.ncpeh.client.MinLogClient;
import jakarta.xml.bind.JAXBException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.xml.datatype.XMLGregorianCalendar;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
public class MinLogService {
    private final MinLogClient minLogClient;

    public MinLogService(MinLogClient minLogClient) {
        this.minLogClient = minLogClient;
    }

    public void logEventOnPatient(
        String cpr,
        String eventText,
        Identity caller
    ) {
        var source = SourceForEntryType.builder().withSystemName("eHDSI").withSource().withSystemName("eHDSI").build();
        var destination = DestinationForEntryForRegistrationType.builder().withSystemName("TestSystem").withActivity(eventText).withDateTime(Utils.xmlGregorianCalendar(LocalDate.now())).withPersonIdentifier().withSource(PersonIdSourceType.CPR).withValue(cpr).end().withUserPersonIdentifier().withSource(UserPersonIdSourceType.CPR).withValue(cpr).end().build();
        var registration = RegistrationRequestType.builder().addLogDataEntry(LogDataEntryForRegistrationType.builder().withSource(source).withDestination(destination).build()).build();

        try {
            var response = minLogClient.register(registration,caller);
            var test = 12;
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }
}
