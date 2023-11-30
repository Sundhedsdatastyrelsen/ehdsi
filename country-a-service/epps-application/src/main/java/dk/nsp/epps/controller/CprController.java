package dk.nsp.epps.controller;


import dk.nsp.epps.ncp.api.FindPatientsResponseDto;
import dk.nsp.epps.service.CprService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class CprController {
    private final CprService cprService;

    @GetMapping(path = "/api/find-patients/")
    public FindPatientsResponseDto findPatients(
        @NotNull @Valid @RequestParam(value = "patientIds", required = true) String patientIds
    ) {
        List<String> patientIdList = Arrays.stream(patientIds.split(","))
            .map(String::trim)
            .filter(s -> !s.isBlank())
            .toList();

        return cprService.findPatients(patientIdList);
    }
}
