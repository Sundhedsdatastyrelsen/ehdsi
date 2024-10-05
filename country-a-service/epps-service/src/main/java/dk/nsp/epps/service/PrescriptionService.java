package dk.nsp.epps.service;

import dk.dkma.medicinecard.xml_schema._2015._06._01.GetPrescriptionRequestType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.UndoEffectuationRequestType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e6.GetPrescriptionResponseType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e6.PrescriptionType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e6.StartEffectuationResponseType;
import dk.nsp.epps.client.FmkClient;
import dk.nsp.epps.client.TestIdentities;
import dk.nsp.epps.ncp.api.DocumentAssociationForEPrescriptionDocumentMetadataDto;
import dk.nsp.epps.ncp.api.EpsosDocumentDto;
import dk.nsp.epps.service.exception.CountryAException;
import dk.nsp.epps.service.mapping.DispensationMapper;
import dk.nsp.epps.service.mapping.EPrescriptionMapper;
import dk.nsp.epps.service.mapping.PatientIdMapper;
import dk.sds.ncp.cda.EPrescriptionDocumentIdMapper;
import dk.sds.ncp.cda.MapperException;
import jakarta.xml.bind.JAXBException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;

import javax.xml.datatype.XMLGregorianCalendar;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.IntStream;

@Slf4j
@Service
@RequiredArgsConstructor
public class PrescriptionService {
    private final FmkClient fmkClient;
    private final EPrescriptionMapper ePrescriptionMapper;
    private final DispensationMapper dispensationMapper;

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
            return (documentId == null || EPrescriptionDocumentIdMapper.possibleIds(String.valueOf(prescription.getIdentifier())).contains(documentId))
                && (createdBefore == null || authorisationDateTime.isBefore(createdBefore))
                && (createdAfter == null || authorisationDateTime.isAfter(createdAfter));
        }

        private OffsetDateTime toOffsetDateTime(@NonNull XMLGregorianCalendar xml) {
            return xml.toGregorianCalendar().toZonedDateTime().toOffsetDateTime();
        }
    }

    public List<DocumentAssociationForEPrescriptionDocumentMetadataDto> findEPrescriptionDocuments(
        String patientId,
        PrescriptionFilter filter
    ) throws JAXBException {
        String cpr = PatientIdMapper.toCpr(patientId);
        final var request = GetPrescriptionRequestType.builder()
            .withPersonIdentifier().withSource("CPR").withValue(cpr).end()
            .withIncludeOpenPrescriptions().end()
            .build();
        log.debug("Looking up info for {}", cpr);
        GetPrescriptionResponseType fmkResponse = fmkClient.getPrescription(request, TestIdentities.apotekerJeppeMoeller);
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
        GetPrescriptionResponseType fmkResponse = fmkClient.getPrescription(request, TestIdentities.apotekerJeppeMoeller);
        log.debug("Found {} prescriptions for {}", fmkResponse.getPrescription().size(), cpr);
        return ePrescriptionMapper.mapResponse(cpr, filter, fmkResponse);
    }

    public void submitDispensation(@NonNull String patientId, @NonNull Document dispensationCda) throws MapperException {
        StartEffectuationResponseType response;
        try {
            response = fmkClient.startEffectuation(
                dispensationMapper.startEffectuationRequest(patientId, dispensationCda),
                TestIdentities.apotekerJeppeMoeller);
        } catch (JAXBException e) {
            throw new CountryAException(HttpStatus.INTERNAL_SERVER_ERROR, "StartEffectuation failed", e);
        }

        try {
            var effectuationResponse = fmkClient.createPharmacyEffectuation(
                dispensationMapper.createPharmacyEffectuationRequest(
                    patientId,
                    dispensationCda,
                    response),
                TestIdentities.apotekerJeppeMoeller);
        } catch (JAXBException e) {
            // TODO: Cancel effectuation flow
            throw new CountryAException(HttpStatus.INTERNAL_SERVER_ERROR, "CreatePharmacyEffectuation failed", e);
        }
    }

    public void undoDispensation(@NonNull String patientId, String prescriptionIdentifier, String orderIdentifier, String effectuationIdentifier){
        UndoEffectuationRequestType
    }
}
