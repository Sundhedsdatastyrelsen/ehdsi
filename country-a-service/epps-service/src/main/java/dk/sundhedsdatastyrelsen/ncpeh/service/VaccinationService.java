package dk.sundhedsdatastyrelsen.ncpeh.service;

import dk.nsp.test.idp.EmployeeIdentities;
import dk.sundhedsdatastyrelsen.ncpeh.client.DdvClient;
import dk.vaccinationsregister.schemas._2013._12._01.GetVaccinationCardRequestType;
import dk.vaccinationsregister.schemas._2013._12._01.VaccinationType;
import jakarta.xml.bind.JAXBException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class VaccinationService {
    private final DdvClient ddvClient;

    public List<VaccinationType> getVaccinationsForCpr(String cpr) throws JAXBException {
        var vaccinationRequest = GetVaccinationCardRequestType.builder()
            .withPersonCivilRegistrationIdentifier(cpr)
            .build();

        var vaccinationCards = ddvClient.getVaccinationCard(vaccinationRequest, EmployeeIdentities.lægeCharlesBabbage());

        return vaccinationCards.getVaccination();
    }
}
