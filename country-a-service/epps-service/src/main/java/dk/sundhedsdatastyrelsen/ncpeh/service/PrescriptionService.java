package dk.sundhedsdatastyrelsen.ncpeh.service;

import dk.dkma.medicinecard.xml_schema._2015._06._01.CreatePharmacyEffectuationResponseType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.GetDrugMedicationRequestType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.GetPrescriptionRequestType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.UndoEffectuationResponseType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e2.GetDrugMedicationResponseType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e5.UndoEffectuationRequestType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e6.GetPrescriptionResponseType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e6.PrescriptionType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e6.StartEffectuationResponseType;
import dk.sundhedsdatastyrelsen.ncpeh.cda.Utils;
import dk.sundhedsdatastyrelsen.ncpeh.client.FmkClient;
import dk.sundhedsdatastyrelsen.ncpeh.ncp.api.DocumentAssociationForEPrescriptionDocumentMetadataDto;
import dk.sundhedsdatastyrelsen.ncpeh.ncp.api.EpsosDocumentDto;
import dk.sundhedsdatastyrelsen.ncpeh.service.exception.CountryAException;
import dk.sundhedsdatastyrelsen.ncpeh.service.exception.DataRequirementException;
import dk.sundhedsdatastyrelsen.ncpeh.service.mapping.DispensationMapper;
import dk.sundhedsdatastyrelsen.ncpeh.service.mapping.EPrescriptionMapper;
import dk.sundhedsdatastyrelsen.ncpeh.service.mapping.EPrescriptionMappingService;
import dk.sundhedsdatastyrelsen.ncpeh.service.mapping.PatientIdMapper;
import dk.sundhedsdatastyrelsen.ncpeh.service.undo.UndoDispensationRepository;
import dk.sundhedsdatastyrelsen.ncpeh.service.undo.UndoDispensationRow;
import dk.nsp.test.idp.model.Identity;
import dk.sundhedsdatastyrelsen.ncpeh.cda.EPrescriptionDocumentIdMapper;
import dk.sundhedsdatastyrelsen.ncpeh.cda.MapperException;
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
    private static final String MAPPING_ERROR_MESSAGE = "Error mapping eDispensation CDA to request: %s";
    private final FmkClient fmkClient;
    private final UndoDispensationRepository undoDispensationRepository;
    private final EPrescriptionMappingService ePrescriptionMappingService;

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
            var authorisationDateTime = Utils.convertToOffsetDateTime(prescription.getAuthorisationDateTime());
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
            return EPrescriptionMapper.mapMeta(cpr, filter, fmkResponse, ePrescriptionMappingService);
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

            var prescriptions = filter.validPrescriptionIndexes(fmkResponse.getPrescription())
                .mapToObj(idx -> fmkResponse.getPrescription().get(idx))
                .toList();

            var drugMedicationIds = prescriptions.stream()
                .map(PrescriptionType::getAttachedToDrugMedicationIdentifier)
                .toList();

            var drugMedications = getDrugMedicationResponse(cpr, drugMedicationIds, caller);

            return EPrescriptionMapper.mapResponse(cpr, filter, fmkResponse, drugMedications, ePrescriptionMappingService);
        } catch (JAXBException e) {
            throw new CountryAException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not retrieve prescriptions from FMK");
        }

    }

    public void submitDispensation(@NonNull String patientId, @NonNull Document dispensationCda, Identity caller) {
        StartEffectuationResponseType response;
        var dispensationMapper = new DispensationMapper();
        String eDispensationCdaId;
        try {
            eDispensationCdaId = dispensationMapper.cdaId(dispensationCda);
        } catch (MapperException e) {
            throw new DataRequirementException("Invalid CDA ID value", e);
        }
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

    public void undoDispensation(@NonNull String patientId, Document cdaToDiscard, Identity caller) {
        var dispensationMapper = new DispensationMapper();
        String eDispensationCdaId;
        try {
            eDispensationCdaId = dispensationMapper.cdaId(cdaToDiscard);
        } catch (MapperException e) {
            throw new DataRequirementException("Invalid CDA ID value", e);
        }
        var undoInfo = undoDispensationRepository.findByCdaId(eDispensationCdaId);

        if (undoInfo == null) {
            throw new CountryAException(HttpStatus.NOT_FOUND, "No undo information found for given eDispensation CDA id");
        }

        UndoEffectuationRequestType undoEffectuationRequest;
        try {
            undoEffectuationRequest = dispensationMapper.createUndoEffectuationRequest(
                patientId,
                cdaToDiscard,
                undoInfo.orderId(),
                undoInfo.effectuationId()
            );
        } catch (MapperException e) {
            throw new DataRequirementException(MAPPING_ERROR_MESSAGE, e);
        }
        log.info("Requesting effectuation undo");
        UndoEffectuationResponseType undoResponse;
        try {
            undoResponse = fmkClient.undoEffectuation(undoEffectuationRequest, caller);
        } catch (JAXBException e) {
            throw new CountryAException(HttpStatus.INTERNAL_SERVER_ERROR, "UndoEffectuation call to FMK failed", e);
        }

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

    public GetDrugMedicationResponseType getDrugMedicationResponse(String cpr, List<Long> drugMedicationId, Identity caller) throws JAXBException {
        var drugMedicationRequest = GetDrugMedicationRequestType.builder()
            .withPersonIdentifier().withSource("CPR").withValue(cpr).end()
            .withIdentifier(drugMedicationId)
            .withIncludePrescriptions(false)
            .withIncludeEffectuations(false)
            .build();

        log.debug("Looking up DrugMedication  info for {}", cpr);
        GetDrugMedicationResponseType fmkResponse = fmkClient.getDrugMedication(drugMedicationRequest, caller);
        log.debug("Found {} prescriptions for drug medication ID {}", fmkResponse.getDrugMedication()
            .size(), drugMedicationId);
        return fmkResponse;
    }

    public GetPrescriptionResponseType getPrescriptionResponse(String cpr, Identity caller) throws JAXBException {
        final var request = GetPrescriptionRequestType.builder()
            .withPersonIdentifier().withSource("CPR").withValue(cpr).end()
            .withIncludeOpenPrescriptions().end()
            .build();
        log.debug("Looking up info for {}", cpr);
        GetPrescriptionResponseType fmkResponse = fmkClient.getPrescription(request, caller);
        log.debug("Found {} prescriptions for {}", fmkResponse.getPrescription().size(), cpr);
        return fmkResponse;
    }
}
