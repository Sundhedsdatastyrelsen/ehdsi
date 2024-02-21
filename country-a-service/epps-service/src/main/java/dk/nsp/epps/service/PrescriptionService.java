package dk.nsp.epps.service;

import dk.dkma.medicinecard.xml_schema._2015._06._01.GetPrescriptionRequestType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e6.GetPrescriptionResponseType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e6.PrescriptionType;
import dk.nsp.epps.ncp.api.EPrescriptionDocumentMetadataDto;
import dk.nsp.epps.ncp.api.EpsosDocumentDto;
import dk.nsp.epps.service.client.FmkClient;
import dk.nsp.epps.service.client.Identities;
import dk.nsp.epps.service.exception.CountryAException;
import dk.nsp.epps.service.mapping.EPrescriptionMapper;
import dk.nsp.epps.service.mapping.PatientIdMapper;
import freemarker.template.TemplateException;
import jakarta.xml.bind.JAXBException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.xml.datatype.XMLGregorianCalendar;
import java.io.IOException;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Stream;

@Slf4j
@Service
@RequiredArgsConstructor
public class PrescriptionService {
    private final FmkClient fmkClient;
    private final EPrescriptionMapper ePrescriptionMapper;

    public record PrescriptionFilter(
        String documentId,
        Long maximumSize,
        OffsetDateTime createdBefore,
        OffsetDateTime createdAfter
    ) {
        public Stream<PrescriptionType> applyTo(List<PrescriptionType> list) {
            if (list == null || list.isEmpty()) {
                return Stream.empty();
            }

            Stream<PrescriptionType> stream = list.stream();

            if (documentId != null) {
                stream = stream.filter(prescription -> documentId.equals(String.valueOf(prescription.getIdentifier())));
            }
            if (createdBefore != null) {
                stream = stream.filter(prescription -> {
                    OffsetDateTime authorisationDateTime = toOffsetDateTime(prescription.getAuthorisationDateTime());
                    return authorisationDateTime == null || authorisationDateTime.isBefore(createdBefore);
                });
            }
            if (createdAfter != null) {
                stream = stream.filter(prescription -> {
                    OffsetDateTime authorisationDateTime = toOffsetDateTime(prescription.getAuthorisationDateTime());
                    return authorisationDateTime == null || authorisationDateTime.isAfter(createdAfter);
                });
            }
            if (maximumSize != null) {
                stream = stream.limit(maximumSize);
            }
            return stream;
        }

        private OffsetDateTime toOffsetDateTime(XMLGregorianCalendar xml) {
            return xml != null ? xml.toGregorianCalendar().toZonedDateTime().toOffsetDateTime() : null;
        }
    }

    public List<EPrescriptionDocumentMetadataDto> findEPrescriptionDocuments(String patientId, PrescriptionFilter filter) throws JAXBException, IOException, InterruptedException {
        String cpr = PatientIdMapper.toCpr(patientId);
        final var request = GetPrescriptionRequestType.builder()
            .withPersonIdentifier().withSource("CPR").withValue(cpr).end()
            .withIncludeAllPrescriptions().end()
            .build();
        log.debug("Looking up info for {}", cpr);
        GetPrescriptionResponseType fmkResponse = fmkClient.getPrescription(request, Identities.apotekerChrisChristoffersen);
        log.debug("Found {} prescriptions for {}", fmkResponse.getPrescription().size(), cpr);
        return ePrescriptionMapper.mapMeta(PatientIdMapper.toPatientId(cpr), filter, fmkResponse);
    }

    public List<EpsosDocumentDto> getPrescriptions(String patientId, PrescriptionFilter filter) throws JAXBException, InterruptedException {
        try {
            String cpr = PatientIdMapper.toCpr(patientId);
            final var request = GetPrescriptionRequestType.builder()
                .withPersonIdentifier().withSource("CPR").withValue(cpr).end()
                .withIncludeAllPrescriptions().end()
                .build();
            log.debug("Looking up info for {}", cpr);
            GetPrescriptionResponseType fmkResponse = fmkClient.getPrescription(request, Identities.apotekerChrisChristoffersen);
            log.debug("Found {} prescriptions for {}", fmkResponse.getPrescription().size(), cpr);
            return ePrescriptionMapper.mapResponse(PatientIdMapper.toPatientId(cpr), filter, fmkResponse);
        } catch (IOException | TemplateException e) {
            throw new CountryAException(HttpStatus.INTERNAL_SERVER_ERROR, e);
        }
    }
}
