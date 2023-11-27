package dk.nsp.epps.service;

import dk.dkma.medicinecard.xml_schema._2015._06._01.e6.GetPrescriptionResponseType;
import dk.nsp.epps.ncp.api.EpsosDocumentDto;
import dk.nsp.epps.service.client.FmkClient;
import dk.nsp.epps.service.mapping.EPrescriptionMapper;
import freemarker.template.TemplateException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class GetPrescriptionService {
    private final FmkClient fmkClient;
    private final EPrescriptionMapper ePrescriptionMapper;

    public List<EpsosDocumentDto> getPrescriptions(String cpr) {
        try {
            log.debug("Looking up info for {}", cpr);
            GetPrescriptionResponseType fmkResponse = fmkClient.getPrescriptions(cpr);
            log.debug("Found {} prescriptions for {}", fmkResponse.getPrescription().size(), cpr);
            return ePrescriptionMapper.mapResponse(cpr, fmkResponse);
        } catch (IOException | TemplateException e) {
            throw new RuntimeException(e);
        }
    }
}
