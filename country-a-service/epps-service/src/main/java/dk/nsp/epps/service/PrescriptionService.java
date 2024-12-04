package dk.nsp.epps.service;

import dk.dkma.medicinecard.xml_schema._2015._06._01.CreatePharmacyEffectuationResponseType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.GetPrescriptionRequestType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e6.GetPrescriptionResponseType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e6.PrescriptionType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e6.StartEffectuationResponseType;
import dk.nsp.epps.client.FmkClient;
import dk.nsp.epps.ncp.api.DocumentAssociationForEPrescriptionDocumentMetadataDto;
import dk.nsp.epps.ncp.api.EpsosDocumentDto;
import dk.nsp.epps.service.exception.CountryAException;
import dk.nsp.epps.service.exception.DataRequirementException;
import dk.nsp.epps.service.mapping.DispensationMapper;
import dk.nsp.epps.service.mapping.EPrescriptionMapper;
import dk.nsp.epps.service.mapping.PatientIdMapper;
import dk.nsp.epps.service.undo.UndoDispensationRepository;
import dk.nsp.epps.service.undo.UndoDispensationRow;
import dk.nsp.test.idp.model.Identity;
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
    private static final String MAPPING_ERROR_MESSAGE = "Error mapping dispensation to request: %s";
    private final FmkClient fmkClient;
    private final UndoDispensationRepository undoDispensationRepository;

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
            return (documentId == null || EPrescriptionDocumentIdMapper.possibleIds(String.valueOf(prescription.getIdentifier()))
                .contains(documentId))
                && (createdBefore == null || authorisationDateTime.isBefore(createdBefore))
                && (createdAfter == null || authorisationDateTime.isAfter(createdAfter));
        }

        private OffsetDateTime toOffsetDateTime(@NonNull XMLGregorianCalendar xml) {
            return xml.toGregorianCalendar().toZonedDateTime().toOffsetDateTime();
        }
    }

    public List<DocumentAssociationForEPrescriptionDocumentMetadataDto> findEPrescriptionDocuments(
        String patientId,
        PrescriptionFilter filter,
        Identity caller
    ) {
        String cpr = PatientIdMapper.toCpr(patientId);
        final var request = GetPrescriptionRequestType.builder()
            .withPersonIdentifier().withSource("CPR").withValue(cpr).end()
            .withIncludeOpenPrescriptions().end()
            .build();
        log.debug("undoDispensation: looking up prescription information");
        try {
            GetPrescriptionResponseType fmkResponse = fmkClient.getPrescription(request, caller);
            log.debug("undoDispensation: Found {} prescriptions", fmkResponse.getPrescription().size());
            return EPrescriptionMapper.mapMeta(cpr, filter, fmkResponse);
        } catch (JAXBException e) {
            throw new CountryAException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not retrieve list of prescriptions from FMK");
        }

    }

    public List<EpsosDocumentDto> getPrescriptions(String patientId, PrescriptionFilter filter, Identity caller) {
        String cpr = PatientIdMapper.toCpr(patientId);
        final var request = GetPrescriptionRequestType.builder()
            .withPersonIdentifier().withSource("CPR").withValue(cpr).end()
            .withIncludeOpenPrescriptions().end()
            .build();
        log.debug("Looking up info for {}", cpr);

        try {
            GetPrescriptionResponseType fmkResponse = fmkClient.getPrescription(request, caller);
            log.debug("Found {} prescriptions for {}", fmkResponse.getPrescription().size(), cpr);
            return EPrescriptionMapper.mapResponse(cpr, filter, fmkResponse);
        } catch (JAXBException e) {
            throw new CountryAException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not retrieve prescriptions from FMK");
        }

    }

    public void submitDispensation(@NonNull String patientId, @NonNull Document dispensationCda, Identity caller) {
        StartEffectuationResponseType response;
        var dispensationMapper = new DispensationMapper();
        try {
            log.info("Start FMK effectuation");
            response = fmkClient.startEffectuation(
                dispensationMapper.startEffectuationRequest(patientId, dispensationCda),
                caller);
        } catch (JAXBException e) {
            throw new CountryAException(HttpStatus.INTERNAL_SERVER_ERROR, "StartEffectuation failed", e);
        } catch (MapperException e) {
            throw new DataRequirementException(String.format(MAPPING_ERROR_MESSAGE, e.getMessage()), e);
        }

        CreatePharmacyEffectuationResponseType effectuationResponse;
        try {
            log.info("Create FMK pharmacy effectuation");
            effectuationResponse = fmkClient.createPharmacyEffectuation(
                dispensationMapper.createPharmacyEffectuationRequest(
                    patientId,
                    dispensationCda,
                    response),
                caller);
        } catch (JAXBException e) {
            throw new CountryAException(HttpStatus.INTERNAL_SERVER_ERROR, "CreatePharmacyEffectuation failed", e);
        } catch (MapperException e) {
            throw new DataRequirementException(String.format(MAPPING_ERROR_MESSAGE, e.getMessage()), e);
        }

        var eDispensationCdaId = dispensationMapper.cdaId(dispensationCda);
        for (var effectuation : effectuationResponse.getEffectuation()) {
            // There should only be one in our case, but FMK supports multiple effectuations per request
            log.info("Store effectuation undo information");
            try {
                undoDispensationRepository.insert(UndoDispensationRow.fromCdaId(
                    eDispensationCdaId,
                    effectuation.getEffectuationIdentifier(),
                    effectuation.getOrderIdentifier()
                ));
            } catch (Exception e) {
                // We should not fail the submitDispensation request here because of a database error,
                // because the dispensation has been recorded in FMK at this point.
                log.error("Storing undo information for dispensation failed", e);
            }
        }
    }

    public void undoDispensation(@NonNull String patientId, Document cdaToDiscard, Identity caller) throws JAXBException, MapperException {
        var dispensationMapper = new DispensationMapper();
        var eDispensationCdaId = dispensationMapper.cdaId(cdaToDiscard);
        var undoInfo = undoDispensationRepository.findByCdaId(eDispensationCdaId);

        if (undoInfo == null) {
            throw new CountryAException(HttpStatus.NOT_FOUND, "No undo information found for given eDispensation CDA id");
        }

        var undoEffectuationRequest = dispensationMapper.createUndoEffectuationRequest(
            patientId,
            cdaToDiscard,
            undoInfo.orderId(),
            undoInfo.effectuationId()
        );
        log.info("Requesting effectuation undo");
        var undoResponse = fmkClient.undoEffectuation(undoEffectuationRequest, caller);

        var cancelledEffectuationCount = undoResponse.getPrescription()
            .stream()
            .flatMap(p -> p.getOrder().stream())
            .mapToLong(o -> o.getEffectuation().size())
            .sum();
        if (cancelledEffectuationCount < 1) {
            throw new CountryAException(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Error cancelling effectuation, nothing was cancelled");
        }
        if (cancelledEffectuationCount > 1) {
            log.error(
                "More than one ({}) FMK effectuations was cancelled where it should be exactly one",
                cancelledEffectuationCount);
        }
        log.info("Undo effectuation successful, removing undo information");
        undoDispensationRepository.deleteByCdaId(eDispensationCdaId);
    }
}
