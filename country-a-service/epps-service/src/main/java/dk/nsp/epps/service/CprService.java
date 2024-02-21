package dk.nsp.epps.service;

import dk.nsp.epps.ncp.api.FindPatientsResponseDto;
import dk.nsp.epps.ncp.api.PatientDemographicsDto;
import dk.nsp.epps.service.client.CprClient;
import dk.nsp.epps.service.mapping.CrossGatewayPatientDiscoveryMapper;
import dk.nsp.epps.service.mapping.PatientIdMapper;
import dk.nsp.test.idp.OrganizationIdentities;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import oio.medcom.cprservice._1_0.GetPersonInformationOut;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CprService {
    private final CprClient cprClient;

    public FindPatientsResponseDto findPatients(List<String> patientIds) {
        final List<PatientDemographicsDto> found = new ArrayList<>();
        final List<String> notFound = new ArrayList<>();

        log.info("Retrieving citizen(s): {}", String.join(",", patientIds));
        patientIds.forEach(patientId -> {
            try {
                GetPersonInformationOut response = cprClient.getPersonInformation(
                    PatientIdMapper.toCpr(patientId),
                    OrganizationIdentities.sundhedsdatastyrelsen());
                found.add(CrossGatewayPatientDiscoveryMapper.mapResponse(response));
            } catch (Exception e) {
                log.warn(e.getMessage(), e);
                // TODO what really happens when the cpr is not found??
                notFound.add(patientId);
            }
        });
        log.info(
            "Found CPR information for: {}",
            found.stream().flatMap(i -> i.getIdList().stream()).collect(Collectors.joining(","))
        );

        return new FindPatientsResponseDto(found, notFound);
    }
}
