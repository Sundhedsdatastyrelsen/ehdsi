package dk.nsp.epps.controller;

import dk.nsp.epps.ncp.api.FindPatientsResponseDto;
import dk.nsp.epps.ncp.api.PostFindPatientsRequestDto;
import dk.nsp.epps.service.CprService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CprController {
    private final CprService cprService;

    public CprController(CprService cprService) {
        this.cprService = cprService;
    }

    @PostMapping(path = "/api/find-patients/")
    public FindPatientsResponseDto findPatients(
        @Valid @RequestBody PostFindPatientsRequestDto params
    ) {
        return cprService.findPatients(params.getPatientIds());
    }
}
