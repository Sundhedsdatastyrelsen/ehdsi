package dk.sundhedsdatastyrelsen.ncpeh.service;

import dk.sundhedsdatastyrelsen.ncpeh.authentication.NspDgwsIdentity;
import dk.sundhedsdatastyrelsen.ncpeh.client.CprClient;
import dk.sundhedsdatastyrelsen.ncpeh.client.NspClientException;
import dk.sundhedsdatastyrelsen.ncpeh.ncp.api.FindPatientsResponseDto;
import dk.sundhedsdatastyrelsen.ncpeh.ncp.api.PatientDemographicsDto;
import dk.sundhedsdatastyrelsen.ncpeh.service.exception.CountryAException;
import dk.sundhedsdatastyrelsen.ncpeh.service.mapping.CrossGatewayPatientDiscoveryMapper;
import dk.sundhedsdatastyrelsen.ncpeh.service.mapping.PatientIdMapper;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

@Slf4j
public class CprService {
    private final CprClient cprClient;
    private final NspDgwsIdentity.System systemIdentity;
    private final Pattern invalidIdentifierPattern = Pattern.compile("The value '[^']+' of element 'PersonCivilRegistrationIdentifier' is not valid.");
    private final Pattern noDataFoundPattern = Pattern.compile("Ingen data fundet");

    public CprService(CprClient cprClient, NspDgwsIdentity.System systemIdentity) {
        this.cprClient = cprClient;
        this.systemIdentity = systemIdentity;
    }

    private @Nullable PatientDemographicsDto retrievePerson(String patientId) {
        try {
            var response = cprClient.getPersonInformation(
                PatientIdMapper.toCpr(patientId),
                systemIdentity);
            return CrossGatewayPatientDiscoveryMapper.mapResponse(response);
        } catch (NspClientException e) {
            var e2 = e.getCause();
            if (e2 instanceof NspClientException) {
                if (invalidIdentifierPattern.matcher(e2.getMessage()).find()) {
                    log.info("Invalid patient identifier");
                    return null;
                }
                if (noDataFoundPattern.matcher(e2.getMessage()).find()) {
                    log.info("No data found");
                    return null;
                }
            }
            throw new CountryAException(503, "CPR service unavailable", e);
        } catch (Exception e) {
            throw new CountryAException(503, "CPR service unavailable", e);
        }
    }

    public FindPatientsResponseDto findPatients(List<String> patientIds) {
        log.info("Retrieving CPR information for {} citizen(s)", patientIds.size());
        var patients = patientIds.stream()
            .map(this::retrievePerson)
            .filter(Objects::nonNull)
            .toList();
        return new FindPatientsResponseDto(patients);
    }
}
