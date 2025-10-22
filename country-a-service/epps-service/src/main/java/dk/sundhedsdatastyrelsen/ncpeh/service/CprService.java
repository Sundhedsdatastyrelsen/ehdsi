package dk.sundhedsdatastyrelsen.ncpeh.service;

import dk.sundhedsdatastyrelsen.ncpeh.authentication.NspDgwsIdentity;
import dk.sundhedsdatastyrelsen.ncpeh.client.CprClient;
import dk.sundhedsdatastyrelsen.ncpeh.ncp.api.FindPatientsResponseDto;
import dk.sundhedsdatastyrelsen.ncpeh.ncp.api.PatientDemographicsDto;
import dk.sundhedsdatastyrelsen.ncpeh.service.mapping.CrossGatewayPatientDiscoveryMapper;
import dk.sundhedsdatastyrelsen.ncpeh.service.mapping.PatientIdMapper;
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
    private final NspDgwsIdentity.System systemIdentity;

    public FindPatientsResponseDto findPatients(List<String> patientIds) {
        final List<PatientDemographicsDto> found = new ArrayList<>();
        final List<String> notFound = new ArrayList<>();

        log.info("Retrieving citizen(s): {}", String.join(",", patientIds));
        patientIds.forEach(patientId -> {
            try {
                GetPersonInformationOut response = cprClient.getPersonInformation(
                    PatientIdMapper.toCpr(patientId),
                    systemIdentity);
                found.add(CrossGatewayPatientDiscoveryMapper.mapResponse(response));
            } catch (Exception e) {
                log.warn(e.getMessage(), e);
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
