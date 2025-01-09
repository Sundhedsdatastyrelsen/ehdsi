package dk.sundhedsdatastyrelsen.ncpeh.service;

import dk.sundhedsdatastyrelsen.ncpeh.client.FmkClient;
import dk.sundhedsdatastyrelsen.ncpeh.service.undo.UndoDispensationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class VaccinationService {
    private final FmkClient fmkClient;


}
