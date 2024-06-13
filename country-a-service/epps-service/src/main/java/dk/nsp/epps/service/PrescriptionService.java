package dk.nsp.epps.service;

import dk.dkma.medicinecard.xml_schema._2015._06._01.GetPrescriptionRequestType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e6.GetPrescriptionResponseType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e6.PrescriptionType;
import dk.nsp.epps.client.FmkClient;
import dk.nsp.epps.client.Identities;
import dk.nsp.epps.ncp.api.DocumentAssociationForEPrescriptionDocumentMetadataDto;
import dk.nsp.epps.ncp.api.EpsosDocumentDto;
import dk.nsp.epps.service.mapping.EPrescriptionMapper;
import dk.nsp.epps.service.mapping.PatientIdMapper;
import dk.sds.ncp.cda.EPrescriptionDocumentIdMapper;
import jakarta.xml.bind.JAXBException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.xml.datatype.XMLGregorianCalendar;
import java.io.IOException;
import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

@Slf4j
@Service
@RequiredArgsConstructor
public class PrescriptionService {
    private final FmkClient fmkClient;
    private final EPrescriptionMapper ePrescriptionMapper;

    public record PrescriptionFilter(
        String documentId,
        OffsetDateTime createdBefore,
        OffsetDateTime createdAfter
    ) {
        public static PrescriptionFilter none() {
            return new PrescriptionFilter(null, null, null);
        }

        public IntStream validPrescriptionIndexes(@NonNull List<PrescriptionType> list) {
            return IntStream.range(0, list.size())
                .filter(idx -> apply(list.get(idx)));
        }

        private boolean apply(PrescriptionType prescription) {
            var authorisationDateTime = toOffsetDateTime(prescription.getAuthorisationDateTime());
            return (documentId == null || Arrays.asList(EPrescriptionDocumentIdMapper.PossibleIds(String.valueOf(prescription.getIdentifier()))).contains(documentId))
                && (createdBefore == null || authorisationDateTime.isBefore(createdBefore))
                && (createdAfter == null || authorisationDateTime.isAfter(createdAfter));
        }

        private OffsetDateTime toOffsetDateTime(@NonNull XMLGregorianCalendar xml) {
            return xml.toGregorianCalendar().toZonedDateTime().toOffsetDateTime();
        }
    }

    public List<DocumentAssociationForEPrescriptionDocumentMetadataDto> findEPrescriptionDocuments(String patientId, PrescriptionFilter filter) throws JAXBException, IOException, InterruptedException {
        String cpr = PatientIdMapper.toCpr(patientId);
        final var request = GetPrescriptionRequestType.builder()
            .withPersonIdentifier().withSource("CPR").withValue(cpr).end()
            .withIncludeOpenPrescriptions().end()
            .build();
        log.debug("Looking up info for {}", cpr);
        GetPrescriptionResponseType fmkResponse = fmkClient.getPrescription(request, Identities.apotekerJeppeMoeller);
        log.debug("Found {} prescriptions for {}", fmkResponse.getPrescription().size(), cpr);
        return ePrescriptionMapper.mapMeta(cpr, filter, fmkResponse);
    }

    public List<EpsosDocumentDto> getPrescriptions(String patientId, PrescriptionFilter filter) throws JAXBException {
        String cpr = PatientIdMapper.toCpr(patientId);
        final var request = GetPrescriptionRequestType.builder()
            .withPersonIdentifier().withSource("CPR").withValue(cpr).end()
            .withIncludeOpenPrescriptions().end()
            .build();
        log.debug("Looking up info for {}", cpr);
        GetPrescriptionResponseType fmkResponse = fmkClient.getPrescription(request, Identities.apotekerJeppeMoeller);
        log.debug("Found {} prescriptions for {}", fmkResponse.getPrescription().size(), cpr);
        return ePrescriptionMapper.mapResponse(cpr, filter, fmkResponse);
    }
}
